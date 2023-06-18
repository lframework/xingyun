package com.lframework.xingyun.settle.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.ClientException;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.exceptions.impl.InputErrorException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.components.permission.DataPermissionHandler;
import com.lframework.starter.mybatis.enums.DefaultOpLogType;
import com.lframework.starter.mybatis.components.permission.SysDataPermissionDataPermissionType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.service.GenerateCodeService;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.starter.web.common.security.AbstractUserDetails;
import com.lframework.starter.web.common.security.SecurityUtil;
import com.lframework.xingyun.core.annations.OrderTimeLineLog;
import com.lframework.xingyun.core.components.permission.DataPermissionPool;
import com.lframework.xingyun.core.enums.OrderTimeLineBizType;
import com.lframework.xingyun.settle.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.settle.dto.sheet.SettleBizItemDto;
import com.lframework.xingyun.settle.dto.sheet.SettleSheetFullDto;
import com.lframework.xingyun.settle.entity.SettleCheckSheet;
import com.lframework.xingyun.settle.entity.SettleSheet;
import com.lframework.xingyun.settle.entity.SettleSheetDetail;
import com.lframework.xingyun.settle.enums.SettleSheetStatus;
import com.lframework.xingyun.settle.mappers.SettleSheetMapper;
import com.lframework.xingyun.settle.service.SettleCheckSheetService;
import com.lframework.xingyun.settle.service.SettleSheetDetailService;
import com.lframework.xingyun.settle.service.SettleSheetService;
import com.lframework.xingyun.settle.vo.sheet.ApprovePassSettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.ApproveRefuseSettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.BatchApprovePassSettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.BatchApproveRefuseSettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.CreateSettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.QuerySettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.QueryUnSettleBizItemVo;
import com.lframework.xingyun.settle.vo.sheet.SettleSheetItemVo;
import com.lframework.xingyun.settle.vo.sheet.UpdateSettleSheetVo;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SettleSheetServiceImpl extends BaseMpServiceImpl<SettleSheetMapper, SettleSheet>
    implements SettleSheetService {

  @Autowired
  private SettleSheetDetailService settleSheetDetailService;

  @Autowired
  private GenerateCodeService generateCodeService;

  @Autowired
  private SettleCheckSheetService settleCheckSheetService;

  @Override
  public PageResult<SettleSheet> query(Integer pageIndex, Integer pageSize, QuerySettleSheetVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<SettleSheet> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<SettleSheet> query(QuerySettleSheetVo vo) {

    return getBaseMapper().query(vo,
        DataPermissionHandler.getDataPermission(DataPermissionPool.ORDER,
            Arrays.asList("order"), Arrays.asList("s")));
  }

  @Override
  public SettleSheetFullDto getDetail(String id) {

    return getBaseMapper().getDetail(id);
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "创建供应商结算单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = OrderTimeLineBizType.CREATE, orderId = "#_result", name = "创建结算单")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateSettleSheetVo vo) {

    SettleSheet sheet = new SettleSheet();

    sheet.setId(IdUtil.getId());
    sheet.setCode(generateCodeService.generate(GenerateCodeTypePool.SETTLE_SHEET));

    this.create(sheet, vo);

    sheet.setStatus(SettleSheetStatus.CREATED);

    OpLogUtil.setVariable("code", sheet.getCode());
    OpLogUtil.setExtra(vo);

    getBaseMapper().insert(sheet);

    return sheet.getId();
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "修改供应商结算单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = OrderTimeLineBizType.UPDATE, orderId = "#vo.id", name = "修改结算单")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateSettleSheetVo vo) {

    SettleSheet sheet = getBaseMapper().selectById(vo.getId());
    if (sheet == null) {
      throw new DefaultClientException("供应商结算单不存在！");
    }

    if (sheet.getStatus() != SettleSheetStatus.CREATED
        && sheet.getStatus() != SettleSheetStatus.APPROVE_REFUSE) {
      if (sheet.getStatus() == SettleSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("供应商结算单已审核通过，无法修改！");
      } else {
        throw new DefaultClientException("供应商结算单无法修改！");
      }
    }

    //将所有的单据的结算状态更新
    Wrapper<SettleSheetDetail> queryDetailWrapper = Wrappers.lambdaQuery(SettleSheetDetail.class)
        .eq(SettleSheetDetail::getSheetId, sheet.getId()).orderByAsc(SettleSheetDetail::getOrderNo);
    List<SettleSheetDetail> sheetDetails = settleSheetDetailService.list(queryDetailWrapper);
    for (SettleSheetDetail sheetDetail : sheetDetails) {
      this.setBizItemUnSettle(sheetDetail.getBizId());
    }

    // 删除明细
    Wrapper<SettleSheetDetail> deleteDetailWrapper = Wrappers.lambdaQuery(SettleSheetDetail.class)
        .eq(SettleSheetDetail::getSheetId, sheet.getId());
    settleSheetDetailService.remove(deleteDetailWrapper);

    this.create(sheet, vo);

    sheet.setStatus(SettleSheetStatus.CREATED);

    List<SettleSheetStatus> statusList = new ArrayList<>();
    statusList.add(SettleSheetStatus.CREATED);
    statusList.add(SettleSheetStatus.APPROVE_REFUSE);

    Wrapper<SettleSheet> updateWrapper = Wrappers.lambdaUpdate(SettleSheet.class)
        .set(SettleSheet::getApproveBy, null).set(SettleSheet::getApproveTime, null)
        .set(SettleSheet::getRefuseReason, StringPool.EMPTY_STR)
        .eq(SettleSheet::getId, sheet.getId())
        .in(SettleSheet::getStatus, statusList);
    if (getBaseMapper().updateAllColumn(sheet, updateWrapper) != 1) {
      throw new DefaultClientException("供应商结算单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("code", sheet.getCode());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "审核通过供应商结算单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_PASS, orderId = "#vo.id", name = "审核通过")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void approvePass(ApprovePassSettleSheetVo vo) {

    SettleSheet sheet = getBaseMapper().selectById(vo.getId());
    if (sheet == null) {
      throw new DefaultClientException("供应商结算单不存在！");
    }

    if (sheet.getStatus() != SettleSheetStatus.CREATED
        && sheet.getStatus() != SettleSheetStatus.APPROVE_REFUSE) {
      if (sheet.getStatus() == SettleSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("供应商结算单已审核通过，不允许继续执行审核！");
      }
      throw new DefaultClientException("供应商结算单无法审核通过！");
    }

    sheet.setStatus(SettleSheetStatus.APPROVE_PASS);
    sheet.setApproveBy(SecurityUtil.getCurrentUser().getId());
    sheet.setApproveTime(LocalDateTime.now());
    if (!StringUtil.isBlank(vo.getDescription())) {
      sheet.setDescription(vo.getDescription());
    }

    List<SettleSheetStatus> statusList = new ArrayList<>();
    statusList.add(SettleSheetStatus.CREATED);
    statusList.add(SettleSheetStatus.APPROVE_REFUSE);

    Wrapper<SettleSheet> updateWrapper = Wrappers.lambdaUpdate(SettleSheet.class)
        .eq(SettleSheet::getId, sheet.getId()).in(SettleSheet::getStatus, statusList);
    if (getBaseMapper().updateAllColumn(sheet, updateWrapper) != 1) {
      throw new DefaultClientException("供应商结算单信息已过期，请刷新重试！");
    }

    Wrapper<SettleSheetDetail> queryDetailWrapper = Wrappers.lambdaQuery(SettleSheetDetail.class)
        .eq(SettleSheetDetail::getSheetId, sheet.getId()).orderByAsc(SettleSheetDetail::getOrderNo);
    List<SettleSheetDetail> details = settleSheetDetailService.list(queryDetailWrapper);
    for (SettleSheetDetail detail : details) {
      settleCheckSheetService.setSettleAmount(detail.getBizId(), detail.getPayAmount(),
          detail.getDiscountAmount());
    }

    OpLogUtil.setVariable("code", sheet.getCode());
    OpLogUtil.setExtra(vo);
  }

  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_PASS, orderId = "#_result", name = "直接审核通过")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String directApprovePass(CreateSettleSheetVo vo) {

    SettleSheetService thisService = getThis(this.getClass());

    String id = thisService.create(vo);

    ApprovePassSettleSheetVo approveVo = new ApprovePassSettleSheetVo();
    approveVo.setId(id);

    thisService.approvePass(approveVo);

    return id;
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "审核拒绝供应商结算单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_RETURN, orderId = "#vo.id", name = "审核拒绝，拒绝理由：{}", params = "#vo.refuseReason")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void approveRefuse(ApproveRefuseSettleSheetVo vo) {

    SettleSheet sheet = getBaseMapper().selectById(vo.getId());
    if (sheet == null) {
      throw new DefaultClientException("供应商结算单不存在！");
    }

    if (sheet.getStatus() != SettleSheetStatus.CREATED) {
      if (sheet.getStatus() == SettleSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("供应商结算单已审核通过，不允许继续执行审核！");
      }
      if (sheet.getStatus() == SettleSheetStatus.APPROVE_REFUSE) {
        throw new DefaultClientException("供应商结算单已审核拒绝，不允许继续执行审核！");
      }
      throw new DefaultClientException("供应商结算单无法审核拒绝！");
    }

    sheet.setStatus(SettleSheetStatus.APPROVE_REFUSE);
    sheet.setApproveBy(SecurityUtil.getCurrentUser().getId());
    sheet.setApproveTime(LocalDateTime.now());
    sheet.setRefuseReason(vo.getRefuseReason());

    List<SettleSheetStatus> statusList = new ArrayList<>();
    statusList.add(SettleSheetStatus.CREATED);
    statusList.add(SettleSheetStatus.APPROVE_REFUSE);

    Wrapper<SettleSheet> updateWrapper = Wrappers.lambdaUpdate(SettleSheet.class)
        .eq(SettleSheet::getId, sheet.getId()).in(SettleSheet::getStatus, statusList);
    if (getBaseMapper().updateAllColumn(sheet, updateWrapper) != 1) {
      throw new DefaultClientException("供应商结算单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("code", sheet.getCode());
    OpLogUtil.setExtra(vo);
  }

  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_PASS, orderId = "#vo.ids", name = "审核通过")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void batchApprovePass(BatchApprovePassSettleSheetVo vo) {

    SettleSheetService thisService = getThis(this.getClass());
    int orderNo = 1;
    for (String id : vo.getIds()) {
      ApprovePassSettleSheetVo approveVo = new ApprovePassSettleSheetVo();
      approveVo.setId(id);
      try {
        thisService.approvePass(approveVo);
      } catch (ClientException e) {
        throw new DefaultClientException("第" + orderNo + "个供应商结算单审核通过失败，失败原因：" + e.getMsg());
      }
      orderNo++;
    }
  }

  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_RETURN, orderId = "#vo.ids", name = "审核拒绝，拒绝理由：{}", params = "#vo.refuseReason")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void batchApproveRefuse(BatchApproveRefuseSettleSheetVo vo) {

    SettleSheetService thisService = getThis(this.getClass());
    int orderNo = 1;
    for (String id : vo.getIds()) {
      ApproveRefuseSettleSheetVo approveVo = new ApproveRefuseSettleSheetVo();
      approveVo.setId(id);
      approveVo.setRefuseReason(vo.getRefuseReason());

      try {
        thisService.approveRefuse(approveVo);
      } catch (ClientException e) {
        throw new DefaultClientException("第" + orderNo + "个供应商结算单审核拒绝失败，失败原因：" + e.getMsg());
      }
      orderNo++;
    }
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "删除供应商结算单，单号：{}", params = "#code")
  @OrderTimeLineLog(orderId = "#id", delete = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {

    Assert.notBlank(id);
    SettleSheet sheet = getBaseMapper().selectById(id);
    if (sheet == null) {
      throw new InputErrorException("供应商结算单不存在！");
    }

    if (sheet.getStatus() != SettleSheetStatus.CREATED
        && sheet.getStatus() != SettleSheetStatus.APPROVE_REFUSE) {

      if (sheet.getStatus() == SettleSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("“审核通过”的供应商结算单不允许执行删除操作！");
      }

      throw new DefaultClientException("供应商结算单无法删除！");
    }

    //将所有的单据的结算状态更新
    Wrapper<SettleSheetDetail> queryDetailWrapper = Wrappers.lambdaQuery(SettleSheetDetail.class)
        .eq(SettleSheetDetail::getSheetId, sheet.getId()).orderByAsc(SettleSheetDetail::getOrderNo);
    List<SettleSheetDetail> sheetDetails = settleSheetDetailService.list(queryDetailWrapper);
    for (SettleSheetDetail sheetDetail : sheetDetails) {
      this.setBizItemUnSettle(sheetDetail.getBizId());
    }

    // 删除明细
    Wrapper<SettleSheetDetail> deleteDetailWrapper = Wrappers.lambdaQuery(SettleSheetDetail.class)
        .eq(SettleSheetDetail::getSheetId, sheet.getId());
    settleSheetDetailService.remove(deleteDetailWrapper);

    // 删除单据
    getBaseMapper().deleteById(id);

    OpLogUtil.setVariable("code", sheet.getCode());
  }

  @OrderTimeLineLog(orderId = "#ids", delete = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteByIds(List<String> ids) {

    if (!CollectionUtil.isEmpty(ids)) {
      int orderNo = 1;
      for (String id : ids) {

        try {
          SettleSheetService thisService = getThis(this.getClass());
          thisService.deleteById(id);
        } catch (ClientException e) {
          throw new DefaultClientException("第" + orderNo + "个供应商结算单删除失败，失败原因：" + e.getMsg());
        }

        orderNo++;
      }
    }
  }

  @Override
  public SettleBizItemDto getBizItem(String id) {

    SettleCheckSheet checkSheet = settleCheckSheetService.getById(id);

    SettleBizItemDto result = new SettleBizItemDto();
    result.setId(checkSheet.getId());
    result.setCode(checkSheet.getCode());
    result.setTotalPayAmount(checkSheet.getTotalPayAmount());
    result.setTotalPayedAmount(checkSheet.getTotalPayedAmount());
    result.setTotalDiscountAmount(checkSheet.getTotalDiscountAmount());
    result.setTotalUnPayAmount(
        NumberUtil.sub(checkSheet.getTotalPayAmount(), checkSheet.getTotalPayedAmount(),
            checkSheet.getTotalDiscountAmount()));
    result.setApproveTime(checkSheet.getApproveTime());

    return result;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void setBizItemUnSettle(String id) {

    SettleCheckSheet item = settleCheckSheetService.getById(id);
    int count = settleCheckSheetService.setUnSettle(id);
    if (count != 1) {
      throw new DefaultClientException("单号：" + item.getCode() + "已结算，业务无法进行！");
    }
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void setBizItemPartSettle(String id) {

    SettleCheckSheet item = settleCheckSheetService.getById(id);
    int count = settleCheckSheetService.setPartSettle(id);
    if (count != 1) {
      throw new DefaultClientException("单号：" + item.getCode() + "已结算，业务无法进行！");
    }
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void setBizItemSettled(String id) {

    SettleCheckSheet item = settleCheckSheetService.getById(id);
    int count = settleCheckSheetService.setSettled(id);
    if (count != 1) {
      throw new DefaultClientException("单号：" + item.getCode() + "已结算，无法重复结算！");
    }
  }

  @Override
  public List<SettleBizItemDto> getUnSettleBizItems(QueryUnSettleBizItemVo vo) {

    List<SettleBizItemDto> results = new ArrayList<>();

    List<SettleCheckSheet> sheetList = settleCheckSheetService.getApprovedList(vo.getSupplierId(),
        vo.getStartTime(), vo.getEndTime());

    if (!CollectionUtil.isEmpty(sheetList)) {
      for (SettleCheckSheet item : sheetList) {
        SettleBizItemDto result = new SettleBizItemDto();
        result.setId(item.getId());
        result.setCode(item.getCode());
        result.setTotalPayAmount(item.getTotalPayAmount());
        result.setTotalPayedAmount(item.getTotalPayedAmount());
        result.setTotalDiscountAmount(item.getTotalDiscountAmount());
        result.setTotalUnPayAmount(
            NumberUtil.sub(item.getTotalPayAmount(), item.getTotalPayedAmount(),
                item.getTotalDiscountAmount()));
        result.setApproveTime(item.getApproveTime());

        results.add(result);
      }
    }

    return results;
  }

  private void create(SettleSheet sheet, CreateSettleSheetVo vo) {

    BigDecimal totalAmount = BigDecimal.ZERO;
    BigDecimal totalDiscountAmount = BigDecimal.ZERO;

    int orderNo = 1;
    for (SettleSheetItemVo itemVo : vo.getItems()) {
      SettleBizItemDto item = this.getBizItem(itemVo.getId());
      if (item == null) {
        throw new DefaultClientException("第" + orderNo + "行业务单据不存在！");
      }

      if (NumberUtil.lt(item.getTotalPayAmount(), 0)) {
        if (NumberUtil.gt(itemVo.getPayAmount(), 0)) {
          throw new DefaultClientException("第" + orderNo + "行实付金额不允许大于0！");
        }

        if (NumberUtil.gt(itemVo.getDiscountAmount(), 0)) {
          throw new DefaultClientException("第" + orderNo + "行优惠金额不允许大于0！");
        }

        if (NumberUtil.equal(itemVo.getPayAmount(), 0) && NumberUtil.equal(
            itemVo.getDiscountAmount(), 0)) {
          throw new DefaultClientException("第" + orderNo + "行实付金额、优惠金额不能同时等于0！");
        }

        if (NumberUtil.gt(item.getTotalUnPayAmount(),
            NumberUtil.add(itemVo.getPayAmount(), itemVo.getDiscountAmount()))) {
          throw new DefaultClientException("第" + orderNo + "行实付金额与优惠金额相加不允许小于未付款金额！");
        }
      } else if (NumberUtil.gt(item.getTotalPayAmount(), 0)) {
        if (NumberUtil.lt(itemVo.getPayAmount(), 0)) {
          throw new DefaultClientException("第" + orderNo + "行实付金额不允许小于0！");
        }

        if (NumberUtil.lt(itemVo.getDiscountAmount(), 0)) {
          throw new DefaultClientException("第" + orderNo + "行优惠金额不允许小于0！");
        }

        if (NumberUtil.equal(itemVo.getPayAmount(), 0) && NumberUtil.equal(
            itemVo.getDiscountAmount(), 0)) {
          throw new DefaultClientException("第" + orderNo + "行实付金额、优惠金额不能同时等于0！");
        }
        if (NumberUtil.lt(item.getTotalUnPayAmount(),
            NumberUtil.add(itemVo.getPayAmount(), itemVo.getDiscountAmount()))) {
          throw new DefaultClientException("第" + orderNo + "行实付金额与优惠金额相加不允许大于未付款金额！");
        }
      } else {
        // 单据应付款等于0
        if (!NumberUtil.equal(itemVo.getPayAmount(), 0) || !NumberUtil.equal(
            itemVo.getDiscountAmount(), 0)) {
          throw new DefaultClientException("第" + orderNo + "行实付金额、优惠金额必须同时等于0！");
        }
      }

      SettleSheetDetail detail = new SettleSheetDetail();
      detail.setId(IdUtil.getId());
      detail.setSheetId(sheet.getId());
      detail.setBizId(itemVo.getId());
      detail.setPayAmount(itemVo.getPayAmount());
      detail.setDiscountAmount(itemVo.getDiscountAmount());
      detail.setDescription(itemVo.getDescription());
      detail.setOrderNo(orderNo);

      settleSheetDetailService.save(detail);

      totalAmount = NumberUtil.add(totalAmount, itemVo.getPayAmount());
      totalDiscountAmount = NumberUtil.add(totalDiscountAmount, itemVo.getDiscountAmount());

      //将所有的单据的结算状态更新
      this.setBizItemPartSettle(detail.getBizId());

      orderNo++;
    }

    AbstractUserDetails currentUser = SecurityUtil.getCurrentUser();

    sheet.setSupplierId(vo.getSupplierId());
    sheet.setTotalAmount(totalAmount);
    sheet.setTotalDiscountAmount(totalDiscountAmount);
    sheet.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());
    sheet.setRefuseReason(StringPool.EMPTY_STR);
    sheet.setStartDate(vo.getStartDate());
    sheet.setEndDate(vo.getEndDate());
  }
}
