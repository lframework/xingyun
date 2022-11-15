package com.lframework.xingyun.api.bo.settle.fee.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Customer;
import com.lframework.xingyun.basedata.service.customer.ICustomerService;
import com.lframework.xingyun.settle.dto.fee.customer.CustomerSettleFeeSheetFullDto;
import com.lframework.xingyun.settle.entity.SettleInItem;
import com.lframework.xingyun.settle.entity.SettleOutItem;
import com.lframework.xingyun.settle.enums.CustomerSettleFeeSheetType;
import com.lframework.xingyun.settle.service.ISettleInItemService;
import com.lframework.xingyun.settle.service.ISettleOutItemService;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetCustomerSettleFeeSheetBo extends BaseBo<CustomerSettleFeeSheetFullDto> {

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
   * 单据类型
   */
  @ApiModelProperty("单据类型")
  private Integer sheetType;

  /**
   * 总金额
   */
  @ApiModelProperty("总金额")
  private BigDecimal totalAmount;

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

  private List<SheetDetailBo> details;

  public GetCustomerSettleFeeSheetBo() {

  }

  public GetCustomerSettleFeeSheetBo(CustomerSettleFeeSheetFullDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<CustomerSettleFeeSheetFullDto> convert(CustomerSettleFeeSheetFullDto dto) {

    return super.convert(dto, GetCustomerSettleFeeSheetBo::getSheetType,
        GetCustomerSettleFeeSheetBo::getStatus,
        GetCustomerSettleFeeSheetBo::getDetails);
  }

  @Override
  protected void afterInit(CustomerSettleFeeSheetFullDto dto) {

    ICustomerService customerService = ApplicationUtil.getBean(ICustomerService.class);
    Customer customer = customerService.findById(dto.getCustomerId());
    this.customerCode = customer.getCode();
    this.customerName = customer.getName();

    this.sheetType = dto.getSheetType().getCode();
    this.status = dto.getStatus().getCode();

    IUserService userService = ApplicationUtil.getBean(IUserService.class);

    if (!StringUtil.isBlank(dto.getApproveBy())) {
      this.approveBy = userService.findById(dto.getApproveBy()).getName();
    }

    if (!CollectionUtil.isEmpty(dto.getDetails())) {
      this.details = dto.getDetails().stream().map(t -> new SheetDetailBo(t, dto.getSheetType()))
          .collect(Collectors.toList());
    }
  }

  @Data
  @EqualsAndHashCode(callSuper = true)
  public static class SheetDetailBo extends BaseBo<CustomerSettleFeeSheetFullDto.SheetDetailDto> {

    /**
     * 明细ID
     */
    @ApiModelProperty("明细ID")
    private String id;

    /**
     * 项目ID
     */
    @ApiModelProperty("项目ID")
    private String itemId;

    /**
     * 项目名称
     */
    @ApiModelProperty("项目名称")
    private String itemName;

    /**
     * 金额
     */
    @ApiModelProperty("金额")
    private BigDecimal amount;

    @JsonIgnore
    private CustomerSettleFeeSheetType sheetType;

    public SheetDetailBo(CustomerSettleFeeSheetFullDto.SheetDetailDto dto,
        CustomerSettleFeeSheetType sheetType) {

      this.sheetType = sheetType;

      if (dto != null) {
        this.convert(dto);

        this.afterInit(dto);
      }
    }

    @Override
    protected void afterInit(CustomerSettleFeeSheetFullDto.SheetDetailDto dto) {

      if (this.sheetType == CustomerSettleFeeSheetType.RECEIVE) {
        ISettleInItemService settleInItemService = ApplicationUtil.getBean(
            ISettleInItemService.class);
        SettleInItem item = settleInItemService.findById(dto.getItemId());
        this.itemName = item.getName();
      } else {
        ISettleOutItemService settleOutItemService = ApplicationUtil.getBean(
            ISettleOutItemService.class);
        SettleOutItem item = settleOutItemService.findById(dto.getItemId());
        this.itemName = item.getName();
      }
    }
  }
}
