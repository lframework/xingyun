package com.lframework.xingyun.sc.impl.stock.take;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.ClientException;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.IdUtil;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.service.IGenerateCodeService;
import com.lframework.web.common.security.SecurityUtil;
import com.lframework.xingyun.core.events.stock.take.DeleteTakeStockPlanEvent;
import com.lframework.xingyun.sc.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.sc.dto.stock.take.sheet.TakeStockSheetFullDto;
import com.lframework.xingyun.sc.entity.TakeStockPlan;
import com.lframework.xingyun.sc.entity.TakeStockSheet;
import com.lframework.xingyun.sc.entity.TakeStockSheetDetail;
import com.lframework.xingyun.sc.enums.TakeStockPlanStatus;
import com.lframework.xingyun.sc.enums.TakeStockPlanType;
import com.lframework.xingyun.sc.enums.TakeStockSheetStatus;
import com.lframework.xingyun.sc.mappers.TakeStockSheetMapper;
import com.lframework.xingyun.sc.service.stock.take.ITakeStockPlanDetailService;
import com.lframework.xingyun.sc.service.stock.take.ITakeStockPlanService;
import com.lframework.xingyun.sc.service.stock.take.ITakeStockSheetDetailService;
import com.lframework.xingyun.sc.service.stock.take.ITakeStockSheetService;
import com.lframework.xingyun.sc.vo.stock.take.sheet.ApprovePassTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.sheet.ApproveRefuseTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.sheet.BatchApprovePassTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.sheet.BatchApproveRefuseTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.sheet.CreateTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.sheet.QueryTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.sheet.TakeStockSheetProductVo;
import com.lframework.xingyun.sc.vo.stock.take.sheet.UpdateTakeStockSheetVo;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TakeStockSheetServiceImpl extends
    BaseMpServiceImpl<TakeStockSheetMapper, TakeStockSheet>
    implements ITakeStockSheetService {

  @Autowired
  private ITakeStockSheetDetailService takeStockSheetDetailService;

  @Autowired
  private IGenerateCodeService generateCodeService;

  @Autowired
  private ITakeStockPlanService takeStockPlanService;

  @Autowired
  private ITakeStockPlanDetailService takeStockPlanDetailService;

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

  @OpLog(type = OpLogType.OTHER, name = "新增盘点单，ID：{}", params = {"#id"})
  @Transactional
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

  @OpLog(type = OpLogType.OTHER, name = "修改盘点单，ID：{}", params = {"#id"})
  @Transactional
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

  @OpLog(type = OpLogType.OTHER, name = "审核通过盘点单，ID：{}", params = {"#id"})
  @Transactional
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

  @Transactional
  @Override
  public void batchApprovePass(BatchApprovePassTakeStockSheetVo vo) {

    ITakeStockSheetService thisService = getThis(this.getClass());
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

  @Transactional
  @Override
  public void directApprovePass(CreateTakeStockSheetVo vo) {

    ITakeStockSheetService thisService = getThis(this.getClass());

    String id = thisService.create(vo);

    ApprovePassTakeStockSheetVo approveVo = new ApprovePassTakeStockSheetVo();
    approveVo.setId(id);

    thisService.approvePass(approveVo);
  }

  @OpLog(type = OpLogType.OTHER, name = "审核拒绝盘点单，ID：{}", params = {"#id"})
  @Transactional
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

  @Transactional
  @Override
  public void batchApproveRefuse(BatchApproveRefuseTakeStockSheetVo vo) {

    ITakeStockSheetService thisService = getThis(this.getClass());
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

  @OpLog(type = OpLogType.OTHER, name = "取消审核通过盘点单，ID：{}", params = {"#id"})
  @Transactional
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

  @OpLog(type = OpLogType.OTHER, name = "删除盘点单，ID：{}", params = {"#id"})
  @Transactional
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

  @Transactional
  @Override
  public void batchDelete(List<String> ids) {

    ITakeStockSheetService thisService = getThis(this.getClass());
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
  public void cleanCacheByKey(Serializable key) {

  }

  @Service
  public static class DeleteTakeStockPlanListener implements
      ApplicationListener<DeleteTakeStockPlanEvent> {

    @Autowired
    private ITakeStockSheetService takeStockSheetService;

    @Autowired
    private ITakeStockSheetDetailService takeStockSheetDetailService;

    @OpLog(type = OpLogType.OTHER, name = "删除库存盘点表，ID：{}", params = "#ids", loopFormat = true)
    @Transactional
    @Override
    public void onApplicationEvent(DeleteTakeStockPlanEvent deleteTakeStockPlanEvent) {

      Wrapper<TakeStockSheet> deleteWrapper = Wrappers.lambdaQuery(TakeStockSheet.class)
          .eq(TakeStockSheet::getPlanId, deleteTakeStockPlanEvent.getId());
      List<TakeStockSheet> sheets = takeStockSheetService.list(deleteWrapper);

      List<String> ids = Collections.EMPTY_LIST;

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
