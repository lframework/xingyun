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
import com.lframework.starter.web.common.security.AbstractUserDetails;
import com.lframework.starter.web.common.security.SecurityUtil;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.xingyun.template.core.service.GenerateCodeService;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.starter.web.utils.PageHelperUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.core.annations.OrderTimeLineLog;
import com.lframework.xingyun.core.enums.OrderTimeLineBizType;
import com.lframework.xingyun.sc.enums.SettleStatus;
import com.lframework.xingyun.settle.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.settle.dto.fee.customer.CustomerSettleFeeSheetFullDto;
import com.lframework.xingyun.settle.entity.CustomerSettleFeeSheet;
import com.lframework.xingyun.settle.entity.CustomerSettleFeeSheetDetail;
import com.lframework.xingyun.settle.entity.SettleInItem;
import com.lframework.xingyun.settle.entity.SettleOutItem;
import com.lframework.xingyun.settle.enums.CustomerSettleFeeSheetStatus;
import com.lframework.xingyun.settle.enums.CustomerSettleFeeSheetType;
import com.lframework.xingyun.settle.enums.SettleOpLogType;
import com.lframework.xingyun.settle.mappers.CustomerSettleFeeSheetMapper;
import com.lframework.xingyun.settle.service.CustomerSettleFeeSheetDetailService;
import com.lframework.xingyun.settle.service.CustomerSettleFeeSheetService;
import com.lframework.xingyun.settle.service.SettleInItemService;
import com.lframework.xingyun.settle.service.SettleOutItemService;
import com.lframework.xingyun.settle.vo.fee.customer.ApprovePassCustomerSettleFeeSheetVo;
import com.lframework.xingyun.settle.vo.fee.customer.ApproveRefuseCustomerSettleFeeSheetVo;
import com.lframework.xingyun.settle.vo.fee.customer.BatchApprovePassCustomerSettleFeeSheetVo;
import com.lframework.xingyun.settle.vo.fee.customer.BatchApproveRefuseCustomerSettleFeeSheetVo;
import com.lframework.xingyun.settle.vo.fee.customer.CreateCustomerSettleFeeSheetVo;
import com.lframework.xingyun.settle.vo.fee.customer.CustomerSettleFeeSheetItemVo;
import com.lframework.xingyun.settle.vo.fee.customer.QueryCustomerSettleFeeSheetVo;
import com.lframework.xingyun.settle.vo.fee.customer.UpdateCustomerSettleFeeSheetVo;
import com.lframework.xingyun.template.core.annotations.OpLog;
import com.lframework.xingyun.template.core.utils.OpLogUtil;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerSettleFeeSheetServiceImpl extends
    BaseMpServiceImpl<CustomerSettleFeeSheetMapper, CustomerSettleFeeSheet>
    implements CustomerSettleFeeSheetService {

  @Autowired
  private CustomerSettleFeeSheetDetailService customerSettleFeeSheetDetailService;

  @Autowired
  private SettleOutItemService settleOutItemService;

  @Autowired
  private SettleInItemService settleInItemService;

  @Autowired
  private GenerateCodeService generateCodeService;

  @Override
  public PageResult<CustomerSettleFeeSheet> query(Integer pageIndex, Integer pageSize,
      QueryCustomerSettleFeeSheetVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<CustomerSettleFeeSheet> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<CustomerSettleFeeSheet> query(QueryCustomerSettleFeeSheetVo vo) {

    return getBaseMapper().query(vo);
  }

  @Override
  public CustomerSettleFeeSheetFullDto getDetail(String id) {

    return getBaseMapper().getDetail(id);
  }

  @OpLog(type = SettleOpLogType.SETTLE, name = "创建客户费用单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = OrderTimeLineBizType.CREATE, orderId = "#_result", name = "创建费用单")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateCustomerSettleFeeSheetVo vo) {

    CustomerSettleFeeSheet sheet = new CustomerSettleFeeSheet();

    sheet.setId(IdUtil.getId());
    sheet.setCode(generateCodeService.generate(GenerateCodeTypePool.CUSTOMER_SETTLE_FEE_SHEET));

    this.create(sheet, vo);

    sheet.setStatus(CustomerSettleFeeSheetStatus.CREATED);

    OpLogUtil.setVariable("code", sheet.getCode());
    OpLogUtil.setExtra(vo);

    getBaseMapper().insert(sheet);

    return sheet.getId();
  }

  @OpLog(type = SettleOpLogType.SETTLE, name = "修改客户费用单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = OrderTimeLineBizType.UPDATE, orderId = "#vo.id", name = "修改费用单")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateCustomerSettleFeeSheetVo vo) {

    CustomerSettleFeeSheet sheet = getBaseMapper().selectById(vo.getId());
    if (sheet == null) {
      throw new DefaultClientException("客户费用单不存在！");
    }

    if (sheet.getStatus() != CustomerSettleFeeSheetStatus.CREATED
        && sheet.getStatus() != CustomerSettleFeeSheetStatus.APPROVE_REFUSE) {
      if (sheet.getStatus() == CustomerSettleFeeSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("客户费用单已审核通过，无法修改！");
      } else {
        throw new DefaultClientException("客户费用单无法修改！");
      }
    }

    // 删除明细
    Wrapper<CustomerSettleFeeSheetDetail> deleteDetailWrapper = Wrappers.lambdaQuery(
            CustomerSettleFeeSheetDetail.class)
        .eq(CustomerSettleFeeSheetDetail::getSheetId, sheet.getId());
    customerSettleFeeSheetDetailService.remove(deleteDetailWrapper);

    this.create(sheet, vo);

    sheet.setStatus(CustomerSettleFeeSheetStatus.CREATED);

    List<CustomerSettleFeeSheetStatus> statusList = new ArrayList<>();
    statusList.add(CustomerSettleFeeSheetStatus.CREATED);
    statusList.add(CustomerSettleFeeSheetStatus.APPROVE_REFUSE);

    Wrapper<CustomerSettleFeeSheet> updateWrapper = Wrappers.lambdaUpdate(
            CustomerSettleFeeSheet.class)
        .set(CustomerSettleFeeSheet::getApproveBy, null)
        .set(CustomerSettleFeeSheet::getApproveTime, null)
        .set(CustomerSettleFeeSheet::getRefuseReason, StringPool.EMPTY_STR)
        .eq(CustomerSettleFeeSheet::getId, sheet.getId())
        .in(CustomerSettleFeeSheet::getStatus, statusList);
    if (getBaseMapper().updateAllColumn(sheet, updateWrapper) != 1) {
      throw new DefaultClientException("客户费用单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("code", sheet.getCode());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = SettleOpLogType.SETTLE, name = "审核通过客户费用单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_PASS, orderId = "#vo.id", name = "审核通过")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void approvePass(ApprovePassCustomerSettleFeeSheetVo vo) {

    CustomerSettleFeeSheet sheet = getBaseMapper().selectById(vo.getId());
    if (sheet == null) {
      throw new DefaultClientException("客户费用单不存在！");
    }

    if (sheet.getStatus() != CustomerSettleFeeSheetStatus.CREATED
        && sheet.getStatus() != CustomerSettleFeeSheetStatus.APPROVE_REFUSE) {
      if (sheet.getStatus() == CustomerSettleFeeSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("客户费用单已审核通过，不允许继续执行审核！");
      }
      throw new DefaultClientException("客户费用单无法审核通过！");
    }

    sheet.setStatus(CustomerSettleFeeSheetStatus.APPROVE_PASS);
    sheet.setApproveBy(SecurityUtil.getCurrentUser().getId());
    sheet.setApproveTime(LocalDateTime.now());
    if (!StringUtil.isBlank(vo.getDescription())) {
      sheet.setDescription(vo.getDescription());
    }

    List<CustomerSettleFeeSheetStatus> statusList = new ArrayList<>();
    statusList.add(CustomerSettleFeeSheetStatus.CREATED);
    statusList.add(CustomerSettleFeeSheetStatus.APPROVE_REFUSE);

    Wrapper<CustomerSettleFeeSheet> updateWrapper = Wrappers.lambdaUpdate(
            CustomerSettleFeeSheet.class)
        .eq(CustomerSettleFeeSheet::getId, sheet.getId())
        .in(CustomerSettleFeeSheet::getStatus, statusList);
    if (getBaseMapper().updateAllColumn(sheet, updateWrapper) != 1) {
      throw new DefaultClientException("客户费用单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("code", sheet.getCode());
    OpLogUtil.setExtra(vo);
  }

  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_PASS, orderId = "#_result", name = "直接审核通过")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String directApprovePass(CreateCustomerSettleFeeSheetVo vo) {

    CustomerSettleFeeSheetService thisService = getThis(this.getClass());

    String id = thisService.create(vo);

    ApprovePassCustomerSettleFeeSheetVo approveVo = new ApprovePassCustomerSettleFeeSheetVo();
    approveVo.setId(id);

    thisService.approvePass(approveVo);

    return id;
  }

  @OpLog(type = SettleOpLogType.SETTLE, name = "审核拒绝客户费用单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_RETURN, orderId = "#vo.id", name = "审核拒绝，拒绝理由：{}", params = "#vo.refuseReason")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void approveRefuse(ApproveRefuseCustomerSettleFeeSheetVo vo) {

    CustomerSettleFeeSheet sheet = getBaseMapper().selectById(vo.getId());
    if (sheet == null) {
      throw new DefaultClientException("客户费用单不存在！");
    }

    if (sheet.getStatus() != CustomerSettleFeeSheetStatus.CREATED) {
      if (sheet.getStatus() == CustomerSettleFeeSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("客户费用单已审核通过，不允许继续执行审核！");
      }
      if (sheet.getStatus() == CustomerSettleFeeSheetStatus.APPROVE_REFUSE) {
        throw new DefaultClientException("客户费用单已审核拒绝，不允许继续执行审核！");
      }
      throw new DefaultClientException("客户费用单无法审核拒绝！");
    }

    sheet.setStatus(CustomerSettleFeeSheetStatus.APPROVE_REFUSE);
    sheet.setApproveBy(SecurityUtil.getCurrentUser().getId());
    sheet.setApproveTime(LocalDateTime.now());
    sheet.setRefuseReason(vo.getRefuseReason());

    List<CustomerSettleFeeSheetStatus> statusList = new ArrayList<>();
    statusList.add(CustomerSettleFeeSheetStatus.CREATED);
    statusList.add(CustomerSettleFeeSheetStatus.APPROVE_REFUSE);

    Wrapper<CustomerSettleFeeSheet> updateWrapper = Wrappers.lambdaUpdate(
            CustomerSettleFeeSheet.class)
        .eq(CustomerSettleFeeSheet::getId, sheet.getId())
        .in(CustomerSettleFeeSheet::getStatus, statusList);
    if (getBaseMapper().updateAllColumn(sheet, updateWrapper) != 1) {
      throw new DefaultClientException("客户费用单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("code", sheet.getCode());
    OpLogUtil.setExtra(vo);
  }

  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_PASS, orderId = "#vo.ids", name = "审核通过")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void batchApprovePass(BatchApprovePassCustomerSettleFeeSheetVo vo) {

    CustomerSettleFeeSheetService thisService = getThis(this.getClass());
    int orderNo = 1;
    for (String id : vo.getIds()) {
      ApprovePassCustomerSettleFeeSheetVo approveVo = new ApprovePassCustomerSettleFeeSheetVo();
      approveVo.setId(id);
      try {
        thisService.approvePass(approveVo);
      } catch (ClientException e) {
        throw new DefaultClientException(
            "第" + orderNo + "个客户费用单审核通过失败，失败原因：" + e.getMsg());
      }
      orderNo++;
    }
  }

  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_RETURN, orderId = "#vo.ids", name = "审核拒绝，拒绝理由：{}", params = "#vo.refuseReason")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void batchApproveRefuse(BatchApproveRefuseCustomerSettleFeeSheetVo vo) {

    CustomerSettleFeeSheetService thisService = getThis(this.getClass());
    int orderNo = 1;
    for (String id : vo.getIds()) {
      ApproveRefuseCustomerSettleFeeSheetVo approveVo = new ApproveRefuseCustomerSettleFeeSheetVo();
      approveVo.setId(id);
      approveVo.setRefuseReason(vo.getRefuseReason());

      try {
        thisService.approveRefuse(approveVo);
      } catch (ClientException e) {
        throw new DefaultClientException(
            "第" + orderNo + "个客户费用单审核拒绝失败，失败原因：" + e.getMsg());
      }
      orderNo++;
    }
  }

  @OpLog(type = SettleOpLogType.SETTLE, name = "删除客户费用单，单号：{}", params = "#code")
  @OrderTimeLineLog(orderId = "#id", delete = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {

    Assert.notBlank(id);
    CustomerSettleFeeSheet sheet = getBaseMapper().selectById(id);
    if (sheet == null) {
      throw new InputErrorException("客户费用单不存在！");
    }

    if (sheet.getStatus() != CustomerSettleFeeSheetStatus.CREATED
        && sheet.getStatus() != CustomerSettleFeeSheetStatus.APPROVE_REFUSE) {

      if (sheet.getStatus() == CustomerSettleFeeSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("“审核通过”的客户费用单不允许执行删除操作！");
      }

      throw new DefaultClientException("客户费用单无法删除！");
    }

    // 删除明细
    Wrapper<CustomerSettleFeeSheetDetail> deleteDetailWrapper = Wrappers.lambdaQuery(
            CustomerSettleFeeSheetDetail.class)
        .eq(CustomerSettleFeeSheetDetail::getSheetId, sheet.getId());
    customerSettleFeeSheetDetailService.remove(deleteDetailWrapper);

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
          CustomerSettleFeeSheetService thisService = getThis(this.getClass());
          thisService.deleteById(id);
        } catch (ClientException e) {
          throw new DefaultClientException(
              "第" + orderNo + "个客户费用单删除失败，失败原因：" + e.getMsg());
        }

        orderNo++;
      }
    }
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public int setUnSettle(String id) {

    Wrapper<CustomerSettleFeeSheet> updateWrapper = Wrappers.lambdaUpdate(
            CustomerSettleFeeSheet.class)
        .set(CustomerSettleFeeSheet::getSettleStatus, SettleStatus.UN_SETTLE)
        .eq(CustomerSettleFeeSheet::getId, id)
        .eq(CustomerSettleFeeSheet::getSettleStatus, SettleStatus.PART_SETTLE);
    int count = getBaseMapper().update(updateWrapper);

    return count;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public int setPartSettle(String id) {

    Wrapper<CustomerSettleFeeSheet> updateWrapper = Wrappers.lambdaUpdate(
            CustomerSettleFeeSheet.class)
        .set(CustomerSettleFeeSheet::getSettleStatus, SettleStatus.PART_SETTLE)
        .eq(CustomerSettleFeeSheet::getId, id)
        .in(CustomerSettleFeeSheet::getSettleStatus, SettleStatus.UN_SETTLE,
            SettleStatus.PART_SETTLE);
    int count = getBaseMapper().update(updateWrapper);

    return count;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public int setSettled(String id) {

    Wrapper<CustomerSettleFeeSheet> updateWrapper = Wrappers.lambdaUpdate(
            CustomerSettleFeeSheet.class)
        .set(CustomerSettleFeeSheet::getSettleStatus, SettleStatus.SETTLED)
        .eq(CustomerSettleFeeSheet::getId, id)
        .in(CustomerSettleFeeSheet::getSettleStatus, SettleStatus.UN_SETTLE,
            SettleStatus.PART_SETTLE);
    int count = getBaseMapper().update(updateWrapper);

    return count;
  }

  @Override
  public List<CustomerSettleFeeSheet> getApprovedList(String customerId, LocalDateTime startTime,
      LocalDateTime endTime,
      SettleStatus settleStatus) {

    return getBaseMapper().getApprovedList(customerId, startTime, endTime, settleStatus);
  }

  private void create(CustomerSettleFeeSheet sheet, CreateCustomerSettleFeeSheetVo vo) {

    BigDecimal totalAmount = BigDecimal.ZERO;

    int orderNo = 1;
    for (CustomerSettleFeeSheetItemVo itemVo : vo.getItems()) {
      if (vo.getSheetType() == CustomerSettleFeeSheetType.RECEIVE.getCode().intValue()) {
        SettleInItem item = settleInItemService.findById(itemVo.getId());
        if (item == null) {
          throw new DefaultClientException("第" + orderNo + "行项目不存在！");
        }
      } else {
        SettleOutItem item = settleOutItemService.findById(itemVo.getId());
        if (item == null) {
          throw new DefaultClientException("第" + orderNo + "行项目不存在！");
        }
      }
      CustomerSettleFeeSheetDetail detail = new CustomerSettleFeeSheetDetail();
      detail.setId(IdUtil.getId());
      detail.setSheetId(sheet.getId());
      detail.setItemId(itemVo.getId());
      detail.setAmount(itemVo.getAmount());
      detail.setOrderNo(orderNo);

      customerSettleFeeSheetDetailService.save(detail);

      totalAmount = NumberUtil.add(totalAmount, detail.getAmount());

      orderNo++;
    }

    AbstractUserDetails currentUser = SecurityUtil.getCurrentUser();

    sheet.setCustomerId(vo.getCustomerId());
    sheet.setSheetType(EnumUtil.getByCode(CustomerSettleFeeSheetType.class, vo.getSheetType()));
    sheet.setTotalAmount(totalAmount);
    sheet.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());
    sheet.setRefuseReason(StringPool.EMPTY_STR);
    sheet.setSettleStatus(SettleStatus.UN_SETTLE);
  }
}
