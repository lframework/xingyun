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
import com.lframework.xingyun.settle.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.settle.dto.sheet.customer.CustomerSettleBizItemDto;
import com.lframework.xingyun.settle.dto.sheet.customer.CustomerSettleSheetFullDto;
import com.lframework.xingyun.settle.entity.CustomerSettleCheckSheet;
import com.lframework.xingyun.settle.entity.CustomerSettleSheet;
import com.lframework.xingyun.settle.entity.CustomerSettleSheetDetail;
import com.lframework.xingyun.settle.enums.CustomerSettleSheetStatus;
import com.lframework.xingyun.settle.mappers.CustomerSettleSheetMapper;
import com.lframework.xingyun.settle.service.ICustomerSettleCheckSheetService;
import com.lframework.xingyun.settle.service.ICustomerSettleSheetDetailService;
import com.lframework.xingyun.settle.service.ICustomerSettleSheetService;
import com.lframework.xingyun.settle.vo.sheet.customer.ApprovePassCustomerSettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.customer.ApproveRefuseCustomerSettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.customer.BatchApprovePassCustomerSettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.customer.BatchApproveRefuseCustomerSettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.customer.CreateCustomerSettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.customer.CustomerSettleSheetItemVo;
import com.lframework.xingyun.settle.vo.sheet.customer.QueryCustomerSettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.customer.QueryCustomerUnSettleBizItemVo;
import com.lframework.xingyun.settle.vo.sheet.customer.UpdateCustomerSettleSheetVo;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerSettleSheetServiceImpl extends
    BaseMpServiceImpl<CustomerSettleSheetMapper, CustomerSettleSheet>
    implements ICustomerSettleSheetService {

  @Autowired
  private ICustomerSettleSheetDetailService customerSettleSheetDetailService;

  @Autowired
  private IGenerateCodeService generateCodeService;

  @Autowired
  private ICustomerSettleCheckSheetService customerSettleCheckSheetService;

  @Override
  public PageResult<CustomerSettleSheet> query(Integer pageIndex, Integer pageSize,
      QueryCustomerSettleSheetVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<CustomerSettleSheet> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<CustomerSettleSheet> query(QueryCustomerSettleSheetVo vo) {

    return getBaseMapper().query(vo);
  }

  @Override
  public CustomerSettleSheetFullDto getDetail(String id) {

    return getBaseMapper().getDetail(id);
  }

  @OpLog(type = OpLogType.OTHER, name = "创建客户结算单，单号：{}", params = "#code")
  @Transactional
  @Override
  public String create(CreateCustomerSettleSheetVo vo) {

    CustomerSettleSheet sheet = new CustomerSettleSheet();

    sheet.setId(IdUtil.getId());
    sheet.setCode(generateCodeService.generate(GenerateCodeTypePool.CUSTOMER_SETTLE_SHEET));

    this.create(sheet, vo);

    sheet.setStatus(CustomerSettleSheetStatus.CREATED);

    OpLogUtil.setVariable("code", sheet.getCode());
    OpLogUtil.setExtra(vo);

    getBaseMapper().insert(sheet);

    return sheet.getId();
  }

  @OpLog(type = OpLogType.OTHER, name = "修改客户结算单，单号：{}", params = "#code")
  @Transactional
  @Override
  public void update(UpdateCustomerSettleSheetVo vo) {

    CustomerSettleSheet sheet = getBaseMapper().selectById(vo.getId());
    if (sheet == null) {
      throw new DefaultClientException("客户结算单不存在！");
    }

    if (sheet.getStatus() != CustomerSettleSheetStatus.CREATED
        && sheet.getStatus() != CustomerSettleSheetStatus.APPROVE_REFUSE) {
      if (sheet.getStatus() == CustomerSettleSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("客户结算单已审核通过，无法修改！");
      } else {
        throw new DefaultClientException("客户结算单无法修改！");
      }
    }

    //将所有的单据的结算状态更新
    Wrapper<CustomerSettleSheetDetail> queryDetailWrapper = Wrappers.lambdaQuery(
            CustomerSettleSheetDetail.class)
        .eq(CustomerSettleSheetDetail::getSheetId, sheet.getId())
        .orderByAsc(CustomerSettleSheetDetail::getOrderNo);
    List<CustomerSettleSheetDetail> sheetDetails = customerSettleSheetDetailService.list(
        queryDetailWrapper);
    for (CustomerSettleSheetDetail sheetDetail : sheetDetails) {
      this.setBizItemUnSettle(sheetDetail.getBizId());
    }

    // 删除明细
    Wrapper<CustomerSettleSheetDetail> deleteDetailWrapper = Wrappers.lambdaQuery(
            CustomerSettleSheetDetail.class)
        .eq(CustomerSettleSheetDetail::getSheetId, sheet.getId());
    customerSettleSheetDetailService.remove(deleteDetailWrapper);

    this.create(sheet, vo);

    sheet.setStatus(CustomerSettleSheetStatus.CREATED);

    List<CustomerSettleSheetStatus> statusList = new ArrayList<>();
    statusList.add(CustomerSettleSheetStatus.CREATED);
    statusList.add(CustomerSettleSheetStatus.APPROVE_REFUSE);

    Wrapper<CustomerSettleSheet> updateWrapper = Wrappers.lambdaUpdate(CustomerSettleSheet.class)
        .set(CustomerSettleSheet::getApproveBy, null).set(CustomerSettleSheet::getApproveTime, null)
        .set(CustomerSettleSheet::getRefuseReason, StringPool.EMPTY_STR)
        .eq(CustomerSettleSheet::getId, sheet.getId())
        .in(CustomerSettleSheet::getStatus, statusList);
    if (getBaseMapper().update(sheet, updateWrapper) != 1) {
      throw new DefaultClientException("客户结算单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("code", sheet.getCode());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = OpLogType.OTHER, name = "审核通过客户结算单，单号：{}", params = "#code")
  @Transactional
  @Override
  public void approvePass(ApprovePassCustomerSettleSheetVo vo) {

    CustomerSettleSheet sheet = getBaseMapper().selectById(vo.getId());
    if (sheet == null) {
      throw new DefaultClientException("客户结算单不存在！");
    }

    if (sheet.getStatus() != CustomerSettleSheetStatus.CREATED
        && sheet.getStatus() != CustomerSettleSheetStatus.APPROVE_REFUSE) {
      if (sheet.getStatus() == CustomerSettleSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("客户结算单已审核通过，不允许继续执行审核！");
      }
      throw new DefaultClientException("客户结算单无法审核通过！");
    }

    sheet.setStatus(CustomerSettleSheetStatus.APPROVE_PASS);
    sheet.setApproveBy(SecurityUtil.getCurrentUser().getId());
    sheet.setApproveTime(LocalDateTime.now());
    if (!StringUtil.isBlank(vo.getDescription())) {
      sheet.setDescription(vo.getDescription());
    }

    List<CustomerSettleSheetStatus> statusList = new ArrayList<>();
    statusList.add(CustomerSettleSheetStatus.CREATED);
    statusList.add(CustomerSettleSheetStatus.APPROVE_REFUSE);

    Wrapper<CustomerSettleSheet> updateWrapper = Wrappers.lambdaUpdate(CustomerSettleSheet.class)
        .eq(CustomerSettleSheet::getId, sheet.getId())
        .in(CustomerSettleSheet::getStatus, statusList);
    if (getBaseMapper().update(sheet, updateWrapper) != 1) {
      throw new DefaultClientException("客户结算单信息已过期，请刷新重试！");
    }

    Wrapper<CustomerSettleSheetDetail> queryDetailWrapper = Wrappers.lambdaQuery(
            CustomerSettleSheetDetail.class)
        .eq(CustomerSettleSheetDetail::getSheetId, sheet.getId())
        .orderByAsc(CustomerSettleSheetDetail::getOrderNo);
    List<CustomerSettleSheetDetail> details = customerSettleSheetDetailService.list(
        queryDetailWrapper);
    for (CustomerSettleSheetDetail detail : details) {
      customerSettleCheckSheetService.setSettleAmount(detail.getBizId(), detail.getPayAmount(),
          detail.getDiscountAmount());
    }

    OpLogUtil.setVariable("code", sheet.getCode());
    OpLogUtil.setExtra(vo);
  }

  @Transactional
  @Override
  public void directApprovePass(CreateCustomerSettleSheetVo vo) {

    ICustomerSettleSheetService thisService = getThis(this.getClass());

    String id = thisService.create(vo);

    ApprovePassCustomerSettleSheetVo approveVo = new ApprovePassCustomerSettleSheetVo();
    approveVo.setId(id);

    thisService.approvePass(approveVo);
  }

  @OpLog(type = OpLogType.OTHER, name = "审核拒绝客户结算单，单号：{}", params = "#code")
  @Transactional
  @Override
  public void approveRefuse(ApproveRefuseCustomerSettleSheetVo vo) {

    CustomerSettleSheet sheet = getBaseMapper().selectById(vo.getId());
    if (sheet == null) {
      throw new DefaultClientException("客户结算单不存在！");
    }

    if (sheet.getStatus() != CustomerSettleSheetStatus.CREATED) {
      if (sheet.getStatus() == CustomerSettleSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("客户结算单已审核通过，不允许继续执行审核！");
      }
      if (sheet.getStatus() == CustomerSettleSheetStatus.APPROVE_REFUSE) {
        throw new DefaultClientException("客户结算单已审核拒绝，不允许继续执行审核！");
      }
      throw new DefaultClientException("客户结算单无法审核拒绝！");
    }

    sheet.setStatus(CustomerSettleSheetStatus.APPROVE_REFUSE);
    sheet.setApproveBy(SecurityUtil.getCurrentUser().getId());
    sheet.setApproveTime(LocalDateTime.now());
    sheet.setRefuseReason(vo.getRefuseReason());

    List<CustomerSettleSheetStatus> statusList = new ArrayList<>();
    statusList.add(CustomerSettleSheetStatus.CREATED);
    statusList.add(CustomerSettleSheetStatus.APPROVE_REFUSE);

    Wrapper<CustomerSettleSheet> updateWrapper = Wrappers.lambdaUpdate(CustomerSettleSheet.class)
        .eq(CustomerSettleSheet::getId, sheet.getId())
        .in(CustomerSettleSheet::getStatus, statusList);
    if (getBaseMapper().update(sheet, updateWrapper) != 1) {
      throw new DefaultClientException("客户结算单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("code", sheet.getCode());
    OpLogUtil.setExtra(vo);
  }

  @Transactional
  @Override
  public void batchApprovePass(BatchApprovePassCustomerSettleSheetVo vo) {

    ICustomerSettleSheetService thisService = getThis(this.getClass());
    int orderNo = 1;
    for (String id : vo.getIds()) {
      ApprovePassCustomerSettleSheetVo approveVo = new ApprovePassCustomerSettleSheetVo();
      approveVo.setId(id);
      try {
        thisService.approvePass(approveVo);
      } catch (ClientException e) {
        throw new DefaultClientException("第" + orderNo + "个客户结算单审核通过失败，失败原因：" + e.getMsg());
      }
      orderNo++;
    }
  }

  @Transactional
  @Override
  public void batchApproveRefuse(BatchApproveRefuseCustomerSettleSheetVo vo) {

    ICustomerSettleSheetService thisService = getThis(this.getClass());
    int orderNo = 1;
    for (String id : vo.getIds()) {
      ApproveRefuseCustomerSettleSheetVo approveVo = new ApproveRefuseCustomerSettleSheetVo();
      approveVo.setId(id);
      approveVo.setRefuseReason(vo.getRefuseReason());

      try {
        thisService.approveRefuse(approveVo);
      } catch (ClientException e) {
        throw new DefaultClientException("第" + orderNo + "个客户结算单审核拒绝失败，失败原因：" + e.getMsg());
      }
      orderNo++;
    }
  }

  @OpLog(type = OpLogType.OTHER, name = "删除客户结算单，单号：{}", params = "#code")
  @Transactional
  @Override
  public void deleteById(String id) {

    Assert.notBlank(id);
    CustomerSettleSheet sheet = getBaseMapper().selectById(id);
    if (sheet == null) {
      throw new InputErrorException("客户结算单不存在！");
    }

    if (sheet.getStatus() != CustomerSettleSheetStatus.CREATED
        && sheet.getStatus() != CustomerSettleSheetStatus.APPROVE_REFUSE) {

      if (sheet.getStatus() == CustomerSettleSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("“审核通过”的客户结算单不允许执行删除操作！");
      }

      throw new DefaultClientException("客户结算单无法删除！");
    }

    //将所有的单据的结算状态更新
    Wrapper<CustomerSettleSheetDetail> queryDetailWrapper = Wrappers.lambdaQuery(
            CustomerSettleSheetDetail.class)
        .eq(CustomerSettleSheetDetail::getSheetId, sheet.getId())
        .orderByAsc(CustomerSettleSheetDetail::getOrderNo);
    List<CustomerSettleSheetDetail> sheetDetails = customerSettleSheetDetailService.list(
        queryDetailWrapper);
    for (CustomerSettleSheetDetail sheetDetail : sheetDetails) {
      this.setBizItemUnSettle(sheetDetail.getBizId());
    }

    // 删除明细
    Wrapper<CustomerSettleSheetDetail> deleteDetailWrapper = Wrappers.lambdaQuery(
            CustomerSettleSheetDetail.class)
        .eq(CustomerSettleSheetDetail::getSheetId, sheet.getId());
    customerSettleSheetDetailService.remove(deleteDetailWrapper);

    // 删除单据
    getBaseMapper().deleteById(id);

    OpLogUtil.setVariable("code", sheet.getCode());
  }

  @Transactional
  @Override
  public void deleteByIds(List<String> ids) {

    if (!CollectionUtil.isEmpty(ids)) {
      int orderNo = 1;
      for (String id : ids) {

        try {
          ICustomerSettleSheetService thisService = getThis(this.getClass());
          thisService.deleteById(id);
        } catch (ClientException e) {
          throw new DefaultClientException("第" + orderNo + "个客户结算单删除失败，失败原因：" + e.getMsg());
        }

        orderNo++;
      }
    }
  }

  @Override
  public CustomerSettleBizItemDto getBizItem(String id) {

    CustomerSettleCheckSheet checkSheet = customerSettleCheckSheetService.getById(id);

    CustomerSettleBizItemDto result = new CustomerSettleBizItemDto();
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

  @Transactional
  @Override
  public void setBizItemUnSettle(String id) {

    CustomerSettleCheckSheet item = customerSettleCheckSheetService.getById(id);
    int count = customerSettleCheckSheetService.setUnSettle(id);
    if (count != 1) {
      throw new DefaultClientException("单号：" + item.getCode() + "已结算，业务无法进行！");
    }
  }

  @Transactional
  @Override
  public void setBizItemPartSettle(String id) {

    CustomerSettleCheckSheet item = customerSettleCheckSheetService.getById(id);
    int count = customerSettleCheckSheetService.setPartSettle(id);
    if (count != 1) {
      throw new DefaultClientException("单号：" + item.getCode() + "已结算，业务无法进行！");
    }
  }

  @Transactional
  @Override
  public void setBizItemSettled(String id) {

    CustomerSettleCheckSheet item = customerSettleCheckSheetService.getById(id);
    int count = customerSettleCheckSheetService.setSettled(id);
    if (count != 1) {
      throw new DefaultClientException("单号：" + item.getCode() + "已结算，无法重复结算！");
    }
  }

  @Override
  public List<CustomerSettleBizItemDto> getUnSettleBizItems(QueryCustomerUnSettleBizItemVo vo) {

    List<CustomerSettleBizItemDto> results = new ArrayList<>();

    List<CustomerSettleCheckSheet> sheetList = customerSettleCheckSheetService.getApprovedList(
        vo.getCustomerId(),
        vo.getStartTime(), vo.getEndTime());

    if (!CollectionUtil.isEmpty(sheetList)) {
      for (CustomerSettleCheckSheet item : sheetList) {
        CustomerSettleBizItemDto result = new CustomerSettleBizItemDto();
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

  private void create(CustomerSettleSheet sheet, CreateCustomerSettleSheetVo vo) {

    BigDecimal totalAmount = BigDecimal.ZERO;
    BigDecimal totalDiscountAmount = BigDecimal.ZERO;

    int orderNo = 1;
    for (CustomerSettleSheetItemVo itemVo : vo.getItems()) {
      CustomerSettleBizItemDto item = this.getBizItem(itemVo.getId());
      if (item == null) {
        throw new DefaultClientException("第" + orderNo + "行业务单据不存在！");
      }

      if (NumberUtil.lt(item.getTotalPayAmount(), 0)) {
        if (NumberUtil.gt(itemVo.getPayAmount(), 0)) {
          throw new DefaultClientException("第" + orderNo + "行实收金额不允许大于0！");
        }

        if (NumberUtil.gt(itemVo.getDiscountAmount(), 0)) {
          throw new DefaultClientException("第" + orderNo + "行优惠金额不允许大于0！");
        }

        if (NumberUtil.equal(itemVo.getPayAmount(), 0) && NumberUtil.equal(
            itemVo.getDiscountAmount(), 0)) {
          throw new DefaultClientException("第" + orderNo + "行实收金额、优惠金额不能同时等于0！");
        }

        if (NumberUtil.gt(item.getTotalUnPayAmount(),
            NumberUtil.add(itemVo.getPayAmount(), itemVo.getDiscountAmount()))) {
          throw new DefaultClientException("第" + orderNo + "行实收金额与优惠金额相加不允许小于未收款金额！");
        }
      } else if (NumberUtil.gt(item.getTotalPayAmount(), 0)) {
        if (NumberUtil.lt(itemVo.getPayAmount(), 0)) {
          throw new DefaultClientException("第" + orderNo + "行实收金额不允许小于0！");
        }

        if (NumberUtil.lt(itemVo.getDiscountAmount(), 0)) {
          throw new DefaultClientException("第" + orderNo + "行优惠金额不允许小于0！");
        }

        if (NumberUtil.equal(itemVo.getPayAmount(), 0) && NumberUtil.equal(
            itemVo.getDiscountAmount(), 0)) {
          throw new DefaultClientException("第" + orderNo + "行实收金额、优惠金额不能同时等于0！");
        }
        if (NumberUtil.lt(item.getTotalUnPayAmount(),
            NumberUtil.add(itemVo.getPayAmount(), itemVo.getDiscountAmount()))) {
          throw new DefaultClientException("第" + orderNo + "行实收金额与优惠金额相加不允许大于未收款金额！");
        }
      } else {
        // 单据应收款等于0
        if (!NumberUtil.equal(itemVo.getPayAmount(), 0) || !NumberUtil.equal(
            itemVo.getDiscountAmount(), 0)) {
          throw new DefaultClientException("第" + orderNo + "行实收金额、优惠金额必须同时等于0！");
        }
      }

      CustomerSettleSheetDetail detail = new CustomerSettleSheetDetail();
      detail.setId(IdUtil.getId());
      detail.setSheetId(sheet.getId());
      detail.setBizId(itemVo.getId());
      detail.setPayAmount(itemVo.getPayAmount());
      detail.setDiscountAmount(itemVo.getDiscountAmount());
      detail.setDescription(itemVo.getDescription());
      detail.setOrderNo(orderNo);

      customerSettleSheetDetailService.save(detail);

      totalAmount = NumberUtil.add(totalAmount, itemVo.getPayAmount());
      totalDiscountAmount = NumberUtil.add(totalDiscountAmount, itemVo.getDiscountAmount());

      //将所有的单据的结算状态更新
      this.setBizItemPartSettle(detail.getBizId());

      orderNo++;
    }

    AbstractUserDetails currentUser = SecurityUtil.getCurrentUser();

    sheet.setCustomerId(vo.getCustomerId());
    sheet.setTotalAmount(totalAmount);
    sheet.setTotalDiscountAmount(totalDiscountAmount);
    sheet.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());
    sheet.setCreateBy(currentUser.getId());
    sheet.setRefuseReason(StringPool.EMPTY_STR);
    sheet.setStartDate(vo.getStartDate());
    sheet.setEndDate(vo.getEndDate());
  }
}
