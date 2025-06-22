package com.lframework.xingyun.settle.bo.sheet.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Customer;
import com.lframework.xingyun.basedata.service.customer.CustomerService;
import com.lframework.xingyun.settle.dto.sheet.customer.CustomerSettleBizItemDto;
import com.lframework.xingyun.settle.dto.sheet.customer.CustomerSettleSheetFullDto;
import com.lframework.xingyun.settle.service.CustomerSettleSheetService;
import com.lframework.starter.web.inner.service.system.SysUserService;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class GetCustomerSettleSheetBo extends BaseBo<CustomerSettleSheetFullDto> {

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
   * 优惠金额
   */
  @ApiModelProperty("优惠金额")
  private BigDecimal totalDiscountAmount;

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
   * 创建人ID
   */
  @ApiModelProperty("创建人ID")
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
   * 拒绝原因
   */
  @ApiModelProperty("拒绝原因")
  private String refuseReason;

  @ApiModelProperty("明细")
  private List<SheetDetailBo> details;

  public GetCustomerSettleSheetBo() {

  }

  public GetCustomerSettleSheetBo(CustomerSettleSheetFullDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<CustomerSettleSheetFullDto> convert(CustomerSettleSheetFullDto dto) {

    return super.convert(dto, GetCustomerSettleSheetBo::getStatus,
        GetCustomerSettleSheetBo::getDetails);
  }

  @Override
  protected void afterInit(CustomerSettleSheetFullDto dto) {

    CustomerService customerService = ApplicationUtil.getBean(CustomerService.class);
    Customer customer = customerService.findById(dto.getCustomerId());
    this.customerCode = customer.getCode();
    this.customerName = customer.getName();

    this.status = dto.getStatus().getCode();

    SysUserService userService = ApplicationUtil.getBean(SysUserService.class);

    if (!StringUtil.isBlank(dto.getApproveBy())) {
      this.approveBy = userService.findById(dto.getApproveBy()).getName();
    }

    this.startTime = DateUtil.toLocalDateTime(dto.getStartDate());
    this.endTime = DateUtil.toLocalDateTimeMax(dto.getEndDate());

    if (!CollectionUtil.isEmpty(dto.getDetails())) {
      this.details = dto.getDetails().stream().map(SheetDetailBo::new)
          .collect(Collectors.toList());
    }
  }

  @Data
  public static class SheetDetailBo extends BaseBo<CustomerSettleSheetFullDto.SheetDetailDto> {

    /**
     * 明细ID
     */
    @ApiModelProperty("明细ID")
    private String id;

    /**
     * 单据ID
     */
    @ApiModelProperty("单据ID")
    private String bizId;

    /**
     * 单据号
     */
    @ApiModelProperty("单据号")
    private String bizCode;

    /**
     * 审核时间
     */
    @ApiModelProperty("审核时间")
    @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
    private LocalDateTime approveTime;

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
     * 已优惠金额
     */
    @ApiModelProperty("已优惠金额")
    private BigDecimal totalDiscountAmount;

    /**
     * 未付金额
     */
    @ApiModelProperty("未付金额")
    private BigDecimal totalUnPayAmount;

    /**
     * 实付金额
     */
    @ApiModelProperty("实付金额")
    private BigDecimal payAmount;

    /**
     * 优惠金额
     */
    @ApiModelProperty("优惠金额")
    private BigDecimal discountAmount;

    /**
     * 单据备注
     */
    @ApiModelProperty("单据备注")
    private String description;

    public SheetDetailBo() {

    }

    public SheetDetailBo(CustomerSettleSheetFullDto.SheetDetailDto dto) {

      super(dto);
    }

    @Override
    public <A> BaseBo<CustomerSettleSheetFullDto.SheetDetailDto> convert(
        CustomerSettleSheetFullDto.SheetDetailDto dto) {

      return super.convert(dto);
    }

    @Override
    protected void afterInit(CustomerSettleSheetFullDto.SheetDetailDto dto) {

      CustomerSettleSheetService settleSheetService = ApplicationUtil.getBean(
          CustomerSettleSheetService.class);
      CustomerSettleBizItemDto item = settleSheetService.getBizItem(dto.getBizId());
      this.bizCode = item.getCode();
      this.approveTime = item.getApproveTime();
      this.totalPayAmount = item.getTotalPayAmount();
      this.totalPayedAmount = item.getTotalPayedAmount();
      this.totalDiscountAmount = item.getTotalDiscountAmount();
      this.totalUnPayAmount = item.getTotalUnPayAmount();
    }
  }
}
