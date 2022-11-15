package com.lframework.xingyun.settle.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.ClientException;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.exceptions.impl.InputErrorException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.NumberUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.service.IGenerateCodeService;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.web.common.security.AbstractUserDetails;
import com.lframework.web.common.security.SecurityUtil;
import com.lframework.xingyun.core.annations.OrderTimeLineLog;
import com.lframework.xingyun.core.enums.OrderTimeLineBizType;
import com.lframework.xingyun.sc.enums.SettleStatus;
import com.lframework.xingyun.settle.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.settle.dto.pre.customer.CustomerSettlePreSheetFullDto;
import com.lframework.xingyun.settle.entity.CustomerSettlePreSheet;
import com.lframework.xingyun.settle.entity.CustomerSettlePreSheetDetail;
import com.lframework.xingyun.settle.entity.SettleInItem;
import com.lframework.xingyun.settle.enums.CustomerSettlePreSheetStatus;
import com.lframework.xingyun.settle.mappers.CustomerSettlePreSheetMapper;
import com.lframework.xingyun.settle.service.ICustomerSettlePreSheetDetailService;
import com.lframework.xingyun.settle.service.ICustomerSettlePreSheetService;
import com.lframework.xingyun.settle.service.ISettleInItemService;
import com.lframework.xingyun.settle.vo.pre.customer.ApprovePassCustomerSettlePreSheetVo;
import com.lframework.xingyun.settle.vo.pre.customer.ApproveRefuseCustomerSettlePreSheetVo;
import com.lframework.xingyun.settle.vo.pre.customer.BatchApprovePassCustomerSettlePreSheetVo;
import com.lframework.xingyun.settle.vo.pre.customer.BatchApproveRefuseCustomerSettlePreSheetVo;
import com.lframework.xingyun.settle.vo.pre.customer.CreateCustomerSettlePreSheetVo;
import com.lframework.xingyun.settle.vo.pre.customer.CustomerSettlePreSheetItemVo;
import com.lframework.xingyun.settle.vo.pre.customer.QueryCustomerSettlePreSheetVo;
import com.lframework.xingyun.settle.vo.pre.customer.UpdateCustomerSettlePreSheetVo;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerSettlePreSheetServiceImpl extends
    BaseMpServiceImpl<CustomerSettlePreSheetMapper, CustomerSettlePreSheet>
    implements ICustomerSettlePreSheetService {

  @Autowired
  private ICustomerSettlePreSheetDetailService settlePreSheetDetailService;

  @Autowired
  private ISettleInItemService settleInItemService;

  @Autowired
  private IGenerateCodeService generateCodeService;

  @Override
  public PageResult<CustomerSettlePreSheet> query(Integer pageIndex, Integer pageSize,
      QueryCustomerSettlePreSheetVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<CustomerSettlePreSheet> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<CustomerSettlePreSheet> query(QueryCustomerSettlePreSheetVo vo) {

    return getBaseMapper().query(vo);
  }

  @Override
  public CustomerSettlePreSheetFullDto getDetail(String id) {

    return getBaseMapper().getDetail(id);
  }

  @OpLog(type = OpLogType.OTHER, name = "创建客户预付款单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = OrderTimeLineBizType.CREATE, orderId = "#_result", name = "创建预付款单")
  @Transactional
  @Override
  public String create(CreateCustomerSettlePreSheetVo vo) {

    CustomerSettlePreSheet sheet = new CustomerSettlePreSheet();

    sheet.setId(IdUtil.getId());
    sheet.setCode(generateCodeService.generate(GenerateCodeTypePool.CUSTOMER_SETTLE_PRE_SHEET));

    this.create(sheet, vo);

    sheet.setStatus(CustomerSettlePreSheetStatus.CREATED);

    OpLogUtil.setVariable("code", sheet.getCode());
    OpLogUtil.setExtra(vo);

    getBaseMapper().insert(sheet);

    return sheet.getId();
  }

  @OpLog(type = OpLogType.OTHER, name = "修改客户预付款单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = OrderTimeLineBizType.UPDATE, orderId = "#vo.id", name = "修改预付款单")
  @Transactional
  @Override
  public void update(UpdateCustomerSettlePreSheetVo vo) {

    CustomerSettlePreSheet sheet = getBaseMapper().selectById(vo.getId());
    if (sheet == null) {
      throw new DefaultClientException("客户预付款单不存在！");
    }

    if (sheet.getStatus() != CustomerSettlePreSheetStatus.CREATED
        && sheet.getStatus() != CustomerSettlePreSheetStatus.APPROVE_REFUSE) {
      if (sheet.getStatus() == CustomerSettlePreSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("客户预付款单已审核通过，无法修改！");
      } else {
        throw new DefaultClientException("客户预付款单无法修改！");
      }
    }

    // 删除明细
    Wrapper<CustomerSettlePreSheetDetail> deleteDetailWrapper = Wrappers.lambdaQuery(
            CustomerSettlePreSheetDetail.class)
        .eq(CustomerSettlePreSheetDetail::getSheetId, sheet.getId());
    settlePreSheetDetailService.remove(deleteDetailWrapper);

    this.create(sheet, vo);

    sheet.setStatus(CustomerSettlePreSheetStatus.CREATED);

    List<CustomerSettlePreSheetStatus> statusList = new ArrayList<>();
    statusList.add(CustomerSettlePreSheetStatus.CREATED);
    statusList.add(CustomerSettlePreSheetStatus.APPROVE_REFUSE);

    Wrapper<CustomerSettlePreSheet> updateWrapper = Wrappers.lambdaUpdate(
            CustomerSettlePreSheet.class)
        .set(CustomerSettlePreSheet::getApproveBy, null)
        .set(CustomerSettlePreSheet::getApproveTime, null)
        .set(CustomerSettlePreSheet::getRefuseReason, StringPool.EMPTY_STR)
        .eq(CustomerSettlePreSheet::getId, sheet.getId())
        .in(CustomerSettlePreSheet::getStatus, statusList);
    if (getBaseMapper().update(sheet, updateWrapper) != 1) {
      throw new DefaultClientException("客户预付款单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("code", sheet.getCode());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = OpLogType.OTHER, name = "审核通过客户预付款单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_PASS, orderId = "#vo.id", name = "审核通过")
  @Transactional
  @Override
  public void approvePass(ApprovePassCustomerSettlePreSheetVo vo) {

    CustomerSettlePreSheet sheet = getBaseMapper().selectById(vo.getId());
    if (sheet == null) {
      throw new DefaultClientException("客户预付款单不存在！");
    }

    if (sheet.getStatus() != CustomerSettlePreSheetStatus.CREATED
        && sheet.getStatus() != CustomerSettlePreSheetStatus.APPROVE_REFUSE) {
      if (sheet.getStatus() == CustomerSettlePreSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("客户预付款单已审核通过，不允许继续执行审核！");
      }
      throw new DefaultClientException("客户预付款单无法审核通过！");
    }

    sheet.setStatus(CustomerSettlePreSheetStatus.APPROVE_PASS);
    sheet.setApproveBy(SecurityUtil.getCurrentUser().getId());
    sheet.setApproveTime(LocalDateTime.now());
    if (!StringUtil.isBlank(vo.getDescription())) {
      sheet.setDescription(vo.getDescription());
    }

    List<CustomerSettlePreSheetStatus> statusList = new ArrayList<>();
    statusList.add(CustomerSettlePreSheetStatus.CREATED);
    statusList.add(CustomerSettlePreSheetStatus.APPROVE_REFUSE);

    Wrapper<CustomerSettlePreSheet> updateWrapper = Wrappers.lambdaUpdate(
            CustomerSettlePreSheet.class)
        .eq(CustomerSettlePreSheet::getId, sheet.getId())
        .in(CustomerSettlePreSheet::getStatus, statusList);
    if (getBaseMapper().update(sheet, updateWrapper) != 1) {
      throw new DefaultClientException("客户预付款单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("code", sheet.getCode());
    OpLogUtil.setExtra(vo);
  }

  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_PASS, orderId = "#_result", name = "直接审核通过")
  @Transactional
  @Override
  public String directApprovePass(CreateCustomerSettlePreSheetVo vo) {

    ICustomerSettlePreSheetService thisService = getThis(this.getClass());

    String id = thisService.create(vo);

    ApprovePassCustomerSettlePreSheetVo approveVo = new ApprovePassCustomerSettlePreSheetVo();
    approveVo.setId(id);

    thisService.approvePass(approveVo);

    return id;
  }

  @OpLog(type = OpLogType.OTHER, name = "审核拒绝客户预付款单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_RETURN, orderId = "#vo.id", name = "审核拒绝，拒绝理由：{}", params = "#vo.refuseReason")
  @Transactional
  @Override
  public void approveRefuse(ApproveRefuseCustomerSettlePreSheetVo vo) {

    CustomerSettlePreSheet sheet = getBaseMapper().selectById(vo.getId());
    if (sheet == null) {
      throw new DefaultClientException("客户预付款单不存在！");
    }

    if (sheet.getStatus() != CustomerSettlePreSheetStatus.CREATED) {
      if (sheet.getStatus() == CustomerSettlePreSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("客户预付款单已审核通过，不允许继续执行审核！");
      }
      if (sheet.getStatus() == CustomerSettlePreSheetStatus.APPROVE_REFUSE) {
        throw new DefaultClientException("客户预付款单已审核拒绝，不允许继续执行审核！");
      }
      throw new DefaultClientException("客户预付款单无法审核拒绝！");
    }

    sheet.setStatus(CustomerSettlePreSheetStatus.APPROVE_REFUSE);
    sheet.setApproveBy(SecurityUtil.getCurrentUser().getId());
    sheet.setApproveTime(LocalDateTime.now());
    sheet.setRefuseReason(vo.getRefuseReason());

    List<CustomerSettlePreSheetStatus> statusList = new ArrayList<>();
    statusList.add(CustomerSettlePreSheetStatus.CREATED);
    statusList.add(CustomerSettlePreSheetStatus.APPROVE_REFUSE);

    Wrapper<CustomerSettlePreSheet> updateWrapper = Wrappers.lambdaUpdate(
            CustomerSettlePreSheet.class)
        .eq(CustomerSettlePreSheet::getId, sheet.getId())
        .in(CustomerSettlePreSheet::getStatus, statusList);
    if (getBaseMapper().update(sheet, updateWrapper) != 1) {
      throw new DefaultClientException("客户预付款单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("code", sheet.getCode());
    OpLogUtil.setExtra(vo);
  }

  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_PASS, orderId = "#vo.ids", name = "审核通过")
  @Transactional
  @Override
  public void batchApprovePass(BatchApprovePassCustomerSettlePreSheetVo vo) {

    ICustomerSettlePreSheetService thisService = getThis(this.getClass());
    int orderNo = 1;
    for (String id : vo.getIds()) {
      ApprovePassCustomerSettlePreSheetVo approveVo = new ApprovePassCustomerSettlePreSheetVo();
      approveVo.setId(id);
      try {
        thisService.approvePass(approveVo);
      } catch (ClientException e) {
        throw new DefaultClientException("第" + orderNo + "个客户预付款单审核通过失败，失败原因：" + e.getMsg());
      }
      orderNo++;
    }
  }

  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_RETURN, orderId = "#vo.ids", name = "审核拒绝，拒绝理由：{}", params = "#vo.refuseReason")
  @Transactional
  @Override
  public void batchApproveRefuse(BatchApproveRefuseCustomerSettlePreSheetVo vo) {

    ICustomerSettlePreSheetService thisService = getThis(this.getClass());
    int orderNo = 1;
    for (String id : vo.getIds()) {
      ApproveRefuseCustomerSettlePreSheetVo approveVo = new ApproveRefuseCustomerSettlePreSheetVo();
      approveVo.setId(id);
      approveVo.setRefuseReason(vo.getRefuseReason());

      try {
        thisService.approveRefuse(approveVo);
      } catch (ClientException e) {
        throw new DefaultClientException("第" + orderNo + "个客户预付款单审核拒绝失败，失败原因：" + e.getMsg());
      }
      orderNo++;
    }
  }

  @OpLog(type = OpLogType.OTHER, name = "删除客户预付款单，单号：{}", params = "#code")
  @OrderTimeLineLog(orderId = "#id", delete = true)
  @Transactional
  @Override
  public void deleteById(String id) {

    Assert.notBlank(id);
    CustomerSettlePreSheet sheet = getBaseMapper().selectById(id);
    if (sheet == null) {
      throw new InputErrorException("客户预付款单不存在！");
    }

    if (sheet.getStatus() != CustomerSettlePreSheetStatus.CREATED
        && sheet.getStatus() != CustomerSettlePreSheetStatus.APPROVE_REFUSE) {

      if (sheet.getStatus() == CustomerSettlePreSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("“审核通过”的客户预付款单不允许执行删除操作！");
      }

      throw new DefaultClientException("客户预付款单无法删除！");
    }

    // 删除明细
    Wrapper<CustomerSettlePreSheetDetail> deleteDetailWrapper = Wrappers.lambdaQuery(
            CustomerSettlePreSheetDetail.class)
        .eq(CustomerSettlePreSheetDetail::getSheetId, sheet.getId());
    settlePreSheetDetailService.remove(deleteDetailWrapper);

    // 删除单据
    getBaseMapper().deleteById(id);

    OpLogUtil.setVariable("code", sheet.getCode());
  }

  @OrderTimeLineLog(orderId = "#ids", delete = true)
  @Transactional
  @Override
  public void deleteByIds(List<String> ids) {

    if (!CollectionUtil.isEmpty(ids)) {
      int orderNo = 1;
      for (String id : ids) {

        try {
          ICustomerSettlePreSheetService thisService = getThis(this.getClass());
          thisService.deleteById(id);
        } catch (ClientException e) {
          throw new DefaultClientException("第" + orderNo + "个客户预付款单删除失败，失败原因：" + e.getMsg());
        }

        orderNo++;
      }
    }
  }

  @Transactional
  @Override
  public int setUnSettle(String id) {

    Wrapper<CustomerSettlePreSheet> updateWrapper = Wrappers.lambdaUpdate(
            CustomerSettlePreSheet.class)
        .set(CustomerSettlePreSheet::getSettleStatus, SettleStatus.UN_SETTLE)
        .eq(CustomerSettlePreSheet::getId, id)
        .eq(CustomerSettlePreSheet::getSettleStatus, SettleStatus.PART_SETTLE);
    int count = getBaseMapper().update(updateWrapper);

    return count;
  }

  @Transactional
  @Override
  public int setPartSettle(String id) {

    Wrapper<CustomerSettlePreSheet> updateWrapper = Wrappers.lambdaUpdate(
            CustomerSettlePreSheet.class)
        .set(CustomerSettlePreSheet::getSettleStatus, SettleStatus.PART_SETTLE)
        .eq(CustomerSettlePreSheet::getId, id)
        .in(CustomerSettlePreSheet::getSettleStatus, SettleStatus.UN_SETTLE,
            SettleStatus.PART_SETTLE);
    int count = getBaseMapper().update(updateWrapper);

    return count;
  }

  @Transactional
  @Override
  public int setSettled(String id) {

    Wrapper<CustomerSettlePreSheet> updateWrapper = Wrappers.lambdaUpdate(
            CustomerSettlePreSheet.class)
        .set(CustomerSettlePreSheet::getSettleStatus, SettleStatus.SETTLED)
        .eq(CustomerSettlePreSheet::getId, id)
        .in(CustomerSettlePreSheet::getSettleStatus, SettleStatus.UN_SETTLE,
            SettleStatus.PART_SETTLE);
    int count = getBaseMapper().update(updateWrapper);

    return count;
  }

  @Override
  public List<CustomerSettlePreSheet> getApprovedList(String customerId, LocalDateTime startTime,
      LocalDateTime endTime,
      SettleStatus settleStatus) {

    return getBaseMapper().getApprovedList(customerId, startTime, endTime, settleStatus);
  }

  private void create(CustomerSettlePreSheet sheet, CreateCustomerSettlePreSheetVo vo) {

    BigDecimal totalAmount = BigDecimal.ZERO;

    int orderNo = 1;
    for (CustomerSettlePreSheetItemVo itemVo : vo.getItems()) {
      SettleInItem item = settleInItemService.findById(itemVo.getId());
      if (item == null) {
        throw new DefaultClientException("第" + orderNo + "行项目不存在！");
      }
      CustomerSettlePreSheetDetail detail = new CustomerSettlePreSheetDetail();
      detail.setId(IdUtil.getId());
      detail.setSheetId(sheet.getId());
      detail.setItemId(itemVo.getId());
      detail.setAmount(itemVo.getAmount());
      detail.setOrderNo(orderNo);

      settlePreSheetDetailService.save(detail);

      totalAmount = NumberUtil.add(totalAmount, detail.getAmount());

      orderNo++;
    }

    AbstractUserDetails currentUser = SecurityUtil.getCurrentUser();

    sheet.setCustomerId(vo.getCustomerId());
    sheet.setTotalAmount(totalAmount);
    sheet.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());
    sheet.setRefuseReason(StringPool.EMPTY_STR);
    sheet.setSettleStatus(SettleStatus.UN_SETTLE);
  }
}
