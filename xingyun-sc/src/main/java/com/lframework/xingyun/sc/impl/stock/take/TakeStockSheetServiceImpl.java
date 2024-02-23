package com.lframework.xingyun.sc.impl.stock.take;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.ClientException;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.common.security.SecurityUtil;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.service.GenerateCodeService;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.starter.web.utils.PageHelperUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.core.annations.OrderTimeLineLog;
import com.lframework.xingyun.core.enums.OrderTimeLineBizType;
import com.lframework.xingyun.core.events.stock.take.DeleteTakeStockPlanEvent;
import com.lframework.xingyun.sc.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.sc.dto.stock.take.sheet.TakeStockSheetFullDto;
import com.lframework.xingyun.sc.dto.stock.take.sheet.TakeStockSheetProductDto;
import com.lframework.xingyun.sc.entity.TakeStockPlan;
import com.lframework.xingyun.sc.entity.TakeStockSheet;
import com.lframework.xingyun.sc.entity.TakeStockSheetDetail;
import com.lframework.xingyun.sc.enums.ScOpLogType;
import com.lframework.xingyun.sc.enums.TakeStockPlanStatus;
import com.lframework.xingyun.sc.enums.TakeStockPlanType;
import com.lframework.xingyun.sc.enums.TakeStockSheetStatus;
import com.lframework.xingyun.sc.mappers.TakeStockSheetMapper;
import com.lframework.xingyun.sc.service.stock.take.TakeStockPlanDetailService;
import com.lframework.xingyun.sc.service.stock.take.TakeStockPlanService;
import com.lframework.xingyun.sc.service.stock.take.TakeStockSheetDetailService;
import com.lframework.xingyun.sc.service.stock.take.TakeStockSheetService;
import com.lframework.xingyun.sc.vo.stock.take.sheet.ApprovePassTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.sheet.ApproveRefuseTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.sheet.BatchApprovePassTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.sheet.BatchApproveRefuseTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.sheet.CreateTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.sheet.QueryTakeStockSheetProductVo;
import com.lframework.xingyun.sc.vo.stock.take.sheet.QueryTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.sheet.TakeStockSheetProductVo;
import com.lframework.xingyun.sc.vo.stock.take.sheet.UpdateTakeStockSheetVo;
import com.lframework.xingyun.template.core.annotations.OpLog;
import com.lframework.xingyun.template.core.utils.OpLogUtil;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TakeStockSheetServiceImpl extends
    BaseMpServiceImpl<TakeStockSheetMapper, TakeStockSheet>
    implements TakeStockSheetService {

  @Autowired
  private TakeStockSheetDetailService takeStockSheetDetailService;

  @Autowired
  private GenerateCodeService generateCodeService;

  @Autowired
  private TakeStockPlanService takeStockPlanService;

  @Autowired
  private TakeStockPlanDetailService takeStockPlanDetailService;

  @Override
  public PageResult<TakeStockSheet> query(Integer pageIndex, Integer pageSize,
      QueryTakeStockSheetVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<TakeStockSheet> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<TakeStockSheet> query(QueryTakeStockSheetVo vo) {

    return getBaseMapper().query(vo);
  }

  @Override
  public TakeStockSheetFullDto getDetail(String id) {

    TakeStockSheetFullDto data = getBaseMapper().getDetail(id);
    if (data == null) {
      throw new DefaultClientException("盘点单不存在！");
    }

    return data;
  }

  @OpLog(type = ScOpLogType.TAKE_STOCK, name = "新增盘点单，ID：{}", params = {"#id"})
  @OrderTimeLineLog(type = OrderTimeLineBizType.CREATE, orderId = "#_result", name = "创建盘点单")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateTakeStockSheetVo vo) {

    TakeStockSheet data = new TakeStockSheet();
    data.setId(IdUtil.getId());
    data.setCode(generateCodeService.generate(GenerateCodeTypePool.TAKE_STOCK_SHEET));
    data.setPlanId(vo.getPlanId());
    data.setPreSheetId(vo.getPreSheetId());

    TakeStockPlan takeStockPlan = takeStockPlanService.getById(vo.getPlanId());
    if (takeStockPlan == null) {
      throw new DefaultClientException("盘点任务不存在！");
    }

    if (takeStockPlan.getTakeStatus() != TakeStockPlanStatus.CREATED) {
      throw new DefaultClientException("关联盘点任务的盘点状态已改变，不允许进行新增！");
    }

    data.setScId(takeStockPlan.getScId());
    data.setStatus(TakeStockSheetStatus.CREATED);
    data.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    getBaseMapper().insert(data);

    int orderNo = 1;
    for (TakeStockSheetProductVo product : vo.getProducts()) {
      TakeStockSheetDetail detail = new TakeStockSheetDetail();
      detail.setId(IdUtil.getId());
      detail.setSheetId(data.getId());
      detail.setProductId(product.getProductId());
      detail.setTakeNum(product.getTakeNum());
      detail.setDescription(
          StringUtil.isBlank(product.getDescription()) ? StringPool.EMPTY_STR
              : product.getDescription());
      detail.setOrderNo(orderNo++);

      takeStockSheetDetailService.save(detail);
    }

    // 盘点任务如果是单品盘点
    if (takeStockPlan.getTakeType() == TakeStockPlanType.SIMPLE) {
      takeStockPlanDetailService.savePlanDetailBySimple(takeStockPlan.getId(),
          vo.getProducts().stream().map(TakeStockSheetProductVo::getProductId)
              .collect(Collectors.toList()));
    }

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = ScOpLogType.TAKE_STOCK, name = "修改盘点单，ID：{}", params = {"#id"})
  @OrderTimeLineLog(type = OrderTimeLineBizType.UPDATE, orderId = "#_result", name = "修改盘点单")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateTakeStockSheetVo vo) {

    TakeStockSheet data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("盘点单不存在！");
    }

    TakeStockPlan takeStockPlan = takeStockPlanService.getById(data.getPlanId());
    if (takeStockPlan == null) {
      throw new DefaultClientException("盘点任务不存在！");
    }

    if (takeStockPlan.getTakeStatus() != TakeStockPlanStatus.CREATED) {
      throw new DefaultClientException("关联盘点任务的盘点状态已改变，不允许进行修改！");
    }

    LambdaUpdateWrapper<TakeStockSheet> updateWrapper = Wrappers.lambdaUpdate(TakeStockSheet.class)
        .set(TakeStockSheet::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .set(TakeStockSheet::getApproveBy, null).set(TakeStockSheet::getApproveTime, null)
        .set(TakeStockSheet::getRefuseReason, null)
        .set(TakeStockSheet::getStatus, TakeStockSheetStatus.CREATED)
        .eq(TakeStockSheet::getId, vo.getId())
        .in(TakeStockSheet::getStatus, TakeStockSheetStatus.CREATED,
            TakeStockSheetStatus.APPROVE_REFUSE);

    if (getBaseMapper().update(updateWrapper) != 1) {
      throw new DefaultClientException("盘点单信息已过期，请刷新重试！");
    }

    // 删除明细
    Wrapper<TakeStockSheetDetail> deleteDetailWrapper = Wrappers.lambdaQuery(
            TakeStockSheetDetail.class)
        .eq(TakeStockSheetDetail::getSheetId, data.getId());
    takeStockSheetDetailService.remove(deleteDetailWrapper);

    int orderNo = 1;
    for (TakeStockSheetProductVo product : vo.getProducts()) {
      TakeStockSheetDetail detail = new TakeStockSheetDetail();
      detail.setId(IdUtil.getId());
      detail.setSheetId(data.getId());
      detail.setProductId(product.getProductId());
      detail.setTakeNum(product.getTakeNum());
      detail.setDescription(
          StringUtil.isBlank(product.getDescription()) ? StringPool.EMPTY_STR
              : product.getDescription());
      detail.setOrderNo(orderNo++);

      takeStockSheetDetailService.save(detail);
    }

    // 盘点任务如果是单品盘点
    if (takeStockPlan.getTakeType() == TakeStockPlanType.SIMPLE) {
      takeStockPlanDetailService.savePlanDetailBySimple(takeStockPlan.getId(),
          vo.getProducts().stream().map(TakeStockSheetProductVo::getProductId)
              .collect(Collectors.toList()));
    }

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = ScOpLogType.TAKE_STOCK, name = "审核通过盘点单，ID：{}", params = {"#id"})
  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_PASS, orderId = "#vo.id", name = "审核通过")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void approvePass(ApprovePassTakeStockSheetVo vo) {

    TakeStockSheet data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("盘点单不存在！");
    }

    TakeStockPlan takeStockPlan = takeStockPlanService.getById(data.getPlanId());
    if (takeStockPlan == null) {
      throw new DefaultClientException("盘点任务不存在！");
    }

    if (takeStockPlan.getTakeStatus() != TakeStockPlanStatus.CREATED) {
      throw new DefaultClientException("关联盘点任务的盘点状态已改变，不允许继续执行审核！");
    }

    Wrapper<TakeStockSheet> updateWrapper = Wrappers.lambdaUpdate(TakeStockSheet.class)
        .set(TakeStockSheet::getApproveBy, SecurityUtil.getCurrentUser().getId())
        .set(TakeStockSheet::getApproveTime, LocalDateTime.now())
        .set(TakeStockSheet::getStatus, TakeStockSheetStatus.APPROVE_PASS)
        .eq(TakeStockSheet::getId, data.getId())
        .in(TakeStockSheet::getStatus, TakeStockSheetStatus.CREATED,
            TakeStockSheetStatus.APPROVE_REFUSE);
    if (getBaseMapper().update(updateWrapper) != 1) {
      throw new DefaultClientException("盘点单信息已过期，请刷新重试！");
    }

    Wrapper<TakeStockSheetDetail> queryDetailWrapper = Wrappers.lambdaQuery(
            TakeStockSheetDetail.class)
        .eq(TakeStockSheetDetail::getSheetId, data.getId())
        .orderByAsc(TakeStockSheetDetail::getOrderNo);
    List<TakeStockSheetDetail> details = takeStockSheetDetailService.list(queryDetailWrapper);
    for (TakeStockSheetDetail detail : details) {
      takeStockPlanDetailService.updateOriTakeNum(data.getPlanId(), detail.getProductId(),
          detail.getTakeNum());
    }

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setExtra(vo);
  }

  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_PASS, orderId = "#vo.ids", name = "审核通过")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void batchApprovePass(BatchApprovePassTakeStockSheetVo vo) {

    TakeStockSheetService thisService = getThis(this.getClass());
    int orderNo = 1;
    for (String id : vo.getIds()) {
      ApprovePassTakeStockSheetVo approveVo = new ApprovePassTakeStockSheetVo();
      approveVo.setId(id);
      try {
        thisService.approvePass(approveVo);
      } catch (ClientException e) {
        throw new DefaultClientException("第" + orderNo + "个库存盘点单审核通过失败，失败原因：" + e.getMsg());
      }
      orderNo++;
    }
  }

  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_PASS, orderId = "#_result", name = "直接审核通过")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String directApprovePass(CreateTakeStockSheetVo vo) {

    TakeStockSheetService thisService = getThis(this.getClass());

    String id = thisService.create(vo);

    ApprovePassTakeStockSheetVo approveVo = new ApprovePassTakeStockSheetVo();
    approveVo.setId(id);

    thisService.approvePass(approveVo);

    return id;
  }

  @OpLog(type = ScOpLogType.TAKE_STOCK, name = "审核拒绝盘点单，ID：{}", params = {"#id"})
  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_RETURN, orderId = "#vo.id", name = "审核拒绝，拒绝理由：{}", params = "#vo.refuseReason")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void approveRefuse(ApproveRefuseTakeStockSheetVo vo) {

    TakeStockSheet data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("盘点单不存在！");
    }

    TakeStockPlan takeStockPlan = takeStockPlanService.getById(data.getPlanId());
    if (takeStockPlan == null) {
      throw new DefaultClientException("盘点任务不存在！");
    }

    if (takeStockPlan.getTakeStatus() != TakeStockPlanStatus.CREATED) {
      throw new DefaultClientException("关联盘点任务的盘点状态已改变，不允许继续执行审核！");
    }

    Wrapper<TakeStockSheet> updateWrapper = Wrappers.lambdaUpdate(TakeStockSheet.class)
        .set(TakeStockSheet::getApproveBy, SecurityUtil.getCurrentUser().getId())
        .set(TakeStockSheet::getApproveTime, LocalDateTime.now())
        .set(TakeStockSheet::getRefuseReason,
            StringUtil.isBlank(vo.getRefuseReason()) ? StringPool.EMPTY_STR : vo.getRefuseReason())
        .set(TakeStockSheet::getStatus, TakeStockSheetStatus.APPROVE_REFUSE)
        .eq(TakeStockSheet::getId, data.getId())
        .eq(TakeStockSheet::getStatus, TakeStockSheetStatus.CREATED);
    if (getBaseMapper().update(updateWrapper) != 1) {
      throw new DefaultClientException("盘点单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setExtra(vo);
  }

  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_RETURN, orderId = "#vo.ids", name = "审核拒绝，拒绝理由：{}", params = "#vo.refuseReason")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void batchApproveRefuse(BatchApproveRefuseTakeStockSheetVo vo) {

    TakeStockSheetService thisService = getThis(this.getClass());
    int orderNo = 1;
    for (String id : vo.getIds()) {
      ApproveRefuseTakeStockSheetVo approveVo = new ApproveRefuseTakeStockSheetVo();
      approveVo.setId(id);
      approveVo.setRefuseReason(vo.getRefuseReason());
      try {
        thisService.approveRefuse(approveVo);
      } catch (ClientException e) {
        throw new DefaultClientException("第" + orderNo + "个库存盘点单审核拒绝失败，失败原因：" + e.getMsg());
      }
      orderNo++;
    }
  }

  @OrderTimeLineLog(type = OrderTimeLineBizType.CANCEL_APPROVE, orderId = "#id", name = "取消审核")
  @OpLog(type = ScOpLogType.TAKE_STOCK, name = "取消审核通过盘点单，ID：{}", params = {"#id"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void cancelApprovePass(String id) {

    TakeStockSheet data = getBaseMapper().selectById(id);
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("盘点单不存在！");
    }

    TakeStockPlan takeStockPlan = takeStockPlanService.getById(data.getPlanId());
    if (takeStockPlan == null) {
      throw new DefaultClientException("盘点任务不存在！");
    }

    if (takeStockPlan.getTakeStatus() != TakeStockPlanStatus.CREATED) {
      throw new DefaultClientException("关联盘点任务的盘点状态已改变，不允许执行取消审核！");
    }

    LambdaUpdateWrapper<TakeStockSheet> updateWrapper = Wrappers.lambdaUpdate(TakeStockSheet.class)
        .set(TakeStockSheet::getApproveBy, null).set(TakeStockSheet::getApproveTime, null)
        .set(TakeStockSheet::getRefuseReason, null)
        .set(TakeStockSheet::getStatus, TakeStockSheetStatus.CREATED)
        .eq(TakeStockSheet::getId, data.getId())
        .eq(TakeStockSheet::getStatus, TakeStockSheetStatus.APPROVE_PASS);

    if (getBaseMapper().update(updateWrapper) != 1) {
      throw new DefaultClientException("盘点单信息已过期，请刷新重试！");
    }

    Wrapper<TakeStockSheetDetail> queryDetailWrapper = Wrappers.lambdaQuery(
            TakeStockSheetDetail.class)
        .eq(TakeStockSheetDetail::getSheetId, data.getId())
        .orderByAsc(TakeStockSheetDetail::getOrderNo);
    List<TakeStockSheetDetail> details = takeStockSheetDetailService.list(queryDetailWrapper);
    for (TakeStockSheetDetail detail : details) {
      takeStockPlanDetailService.updateOriTakeNum(data.getPlanId(), detail.getProductId(),
          -detail.getTakeNum());
    }

    OpLogUtil.setVariable("id", data.getId());
  }

  @OpLog(type = ScOpLogType.TAKE_STOCK, name = "删除盘点单，ID：{}", params = {"#id"})
  @OrderTimeLineLog(orderId = "#id", delete = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {

    TakeStockSheet data = getBaseMapper().selectById(id);
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("盘点单不存在！");
    }

    Wrapper<TakeStockSheet> deleteWrapper = Wrappers.lambdaQuery(TakeStockSheet.class)
        .eq(TakeStockSheet::getId, id)
        .in(TakeStockSheet::getStatus, TakeStockSheetStatus.CREATED,
            TakeStockSheetStatus.APPROVE_REFUSE);
    if (getBaseMapper().delete(deleteWrapper) != 1) {
      throw new DefaultClientException("盘点单信息已过期，请刷新重试！");
    }

    Wrapper<TakeStockSheetDetail> deleteDetailWrapper = Wrappers.lambdaQuery(
            TakeStockSheetDetail.class)
        .eq(TakeStockSheetDetail::getSheetId, data.getId());
    takeStockSheetDetailService.remove(deleteDetailWrapper);
  }

  @OrderTimeLineLog(orderId = "#ids", delete = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void batchDelete(List<String> ids) {

    TakeStockSheetService thisService = getThis(this.getClass());
    int orderNo = 1;
    for (String id : ids) {
      try {
        thisService.deleteById(id);
      } catch (ClientException e) {
        throw new DefaultClientException("第" + orderNo + "个库存盘点单删除失败，失败原因：" + e.getMsg());
      }
      orderNo++;
    }
  }

  @Override
  public Boolean hasRelatePreTakeStockSheet(String preSheetId) {

    return getBaseMapper().hasRelatePreTakeStockSheet(preSheetId);
  }

  @Override
  public Boolean hasUnApprove(String planId) {

    return getBaseMapper().hasUnApprove(planId);
  }

  @Override
  public PageResult<TakeStockSheetProductDto> queryTakeStockByCondition(Integer pageIndex,
      Integer pageSize, String planId, String condition) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<TakeStockSheetProductDto> datas = getBaseMapper().queryTakeStockByCondition(planId,
        condition);
    PageResult<TakeStockSheetProductDto> pageResult = PageResultUtil.convert(new PageInfo<>(datas));

    return pageResult;
  }

  @Override
  public PageResult<TakeStockSheetProductDto> queryTakeStockList(Integer pageIndex,
      Integer pageSize, QueryTakeStockSheetProductVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<TakeStockSheetProductDto> datas = getBaseMapper().queryTakeStockList(vo);
    PageResult<TakeStockSheetProductDto> pageResult = PageResultUtil.convert(new PageInfo<>(datas));

    return pageResult;
  }

  @Override
  public void cleanCacheByKey(Serializable key) {

  }

  @Service
  public static class DeleteTakeStockPlanListener implements
      ApplicationListener<DeleteTakeStockPlanEvent> {

    @Autowired
    private TakeStockSheetService takeStockSheetService;

    @Autowired
    private TakeStockSheetDetailService takeStockSheetDetailService;

    @OpLog(type = ScOpLogType.TAKE_STOCK, name = "删除库存盘点表，ID：{}", params = "#ids", loopFormat = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void onApplicationEvent(DeleteTakeStockPlanEvent deleteTakeStockPlanEvent) {

      Wrapper<TakeStockSheet> deleteWrapper = Wrappers.lambdaQuery(TakeStockSheet.class)
          .eq(TakeStockSheet::getPlanId, deleteTakeStockPlanEvent.getId());
      List<TakeStockSheet> sheets = takeStockSheetService.list(deleteWrapper);

      List<String> ids = CollectionUtil.emptyList();

      if (!CollectionUtil.isEmpty(sheets)) {
        ids = sheets.stream().map(TakeStockSheet::getId).collect(Collectors.toList());
        Wrapper<TakeStockSheetDetail> deleteDetailWrapper = Wrappers.lambdaQuery(
                TakeStockSheetDetail.class)
            .in(TakeStockSheetDetail::getSheetId, ids);
        takeStockSheetDetailService.remove(deleteDetailWrapper);
      }

      takeStockSheetService.remove(deleteWrapper);

      OpLogUtil.setVariable("ids", ids);
    }
  }
}
