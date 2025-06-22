package com.lframework.xingyun.settle.bo.check.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Customer;
import com.lframework.xingyun.basedata.service.customer.CustomerService;
import com.lframework.xingyun.settle.entity.CustomerSettleCheckSheet;
import com.lframework.starter.web.inner.service.system.SysUserService;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryCustomerSettleCheckSheetBo extends BaseBo<CustomerSettleCheckSheet> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 单号
   */
  @ApiModelProperty("单号")
  private String code;

  /**
   * 客户ID
   */
  @ApiModelProperty("客户ID")
  private String customerId;

  /**
   * 客户编号
   */
  @ApiModelProperty("客户编号")
  private String customerCode;

  /**
   * 客户名称
   */
  @ApiModelProperty("客户名称")
  private String customerName;

  /**
   * 总金额
   */
  @ApiModelProperty("总金额")
  private BigDecimal totalAmount;

  /**
   * 应付金额
   */
  @ApiModelProperty("应付金额")
  private BigDecimal totalPayAmount;

  /**
   * 已付金额
   */
  @ApiModelProperty("已付金额")
  private BigDecimal totalPayedAmount;

  /**
   * 优惠金额
   */
  @ApiModelProperty("优惠金额")
  private BigDecimal totalDiscountAmount;

  /**
   * 未付金额
   */
  @ApiModelProperty("未付金额")
  private BigDecimal totalUnPayAmount;

  /**
   * 起始时间
   */
  @ApiModelProperty("起始时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime startTime;

  /**
   * 截止时间
   */
  @ApiModelProperty("截止时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime endTime;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 创建人
   */
  @ApiModelProperty("创建人")
  private String createBy;

  /**
   * 创建时间
   */
  @ApiModelProperty("创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  /**
   * 审核人
   */
  @ApiModelProperty("审核人")
  private String approveBy;

  /**
   * 审核时间
   */
  @ApiModelProperty("审核时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime approveTime;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Integer status;

  /**
   * 结算状态
   */
  @ApiModelProperty("结算状态")
  private Integer settleStatus;

  public QueryCustomerSettleCheckSheetBo() {

  }

  public QueryCustomerSettleCheckSheetBo(CustomerSettleCheckSheet dto) {

    super(dto);
  }

  @Override
  public BaseBo<CustomerSettleCheckSheet> convert(CustomerSettleCheckSheet dto) {

    return super.convert(dto, QueryCustomerSettleCheckSheetBo::getStatus,
        QueryCustomerSettleCheckSheetBo::getSettleStatus);
  }

  @Override
  protected void afterInit(CustomerSettleCheckSheet dto) {

    CustomerService customerService = ApplicationUtil.getBean(CustomerService.class);
    Customer customer = customerService.findById(dto.getCustomerId());
    this.customerCode = customer.getCode();
    this.customerName = customer.getName();

    this.status = dto.getStatus().getCode();
    this.settleStatus = dto.getSettleStatus().getCode();

    this.totalUnPayAmount = NumberUtil.sub(dto.getTotalPayAmount(), dto.getTotalPayedAmount(),
        dto.getTotalDiscountAmount());

    SysUserService userService = ApplicationUtil.getBean(SysUserService.class);

    if (!StringUtil.isBlank(dto.getApproveBy())) {
      this.approveBy = userService.findById(dto.getApproveBy()).getName();
    }

    this.startTime = DateUtil.toLocalDateTime(dto.getStartDate());
    this.endTime = DateUtil.toLocalDateTimeMax(dto.getEndDate());
  }
}
