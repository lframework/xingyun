package com.lframework.xingyun.settle.bo.pre.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Customer;
import com.lframework.xingyun.basedata.service.customer.CustomerService;
import com.lframework.xingyun.settle.dto.pre.customer.CustomerSettlePreSheetFullDto;
import com.lframework.xingyun.settle.entity.SettleInItem;
import com.lframework.xingyun.settle.service.SettleInItemService;
import com.lframework.starter.web.inner.service.system.SysUserService;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class GetCustomerSettlePreSheetBo extends BaseBo<CustomerSettlePreSheetFullDto> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 单号
   */
  @Schema(description = "单号")
  private String code;

  /**
   * 客户ID
   */
  @Schema(description = "客户ID")
  private String customerId;

  /**
   * 客户编号
   */
  @Schema(description = "客户编号")
  private String customerCode;

  /**
   * 客户名称
   */
  @Schema(description = "客户名称")
  private String customerName;

  /**
   * 总金额
   */
  @Schema(description = "总金额")
  private BigDecimal totalAmount;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  /**
   * 创建人ID
   */
  @Schema(description = "创建人ID")
  private String createBy;

  /**
   * 创建时间
   */
  @Schema(description = "创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  /**
   * 审核人
   */
  @Schema(description = "审核人")
  private String approveBy;

  /**
   * 审核时间
   */
  @Schema(description = "审核时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime approveTime;

  /**
   * 状态
   */
  @Schema(description = "状态")
  private Integer status;

  /**
   * 拒绝原因
   */
  @Schema(description = "拒绝原因")
  private String refuseReason;

  @Schema(description = "明细")
  private List<SheetDetailBo> details;

  public GetCustomerSettlePreSheetBo() {

  }

  public GetCustomerSettlePreSheetBo(CustomerSettlePreSheetFullDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<CustomerSettlePreSheetFullDto> convert(CustomerSettlePreSheetFullDto dto) {

    return super.convert(dto, GetCustomerSettlePreSheetBo::getStatus,
        GetCustomerSettlePreSheetBo::getDetails);
  }

  @Override
  protected void afterInit(CustomerSettlePreSheetFullDto dto) {

    CustomerService customerService = ApplicationUtil.getBean(CustomerService.class);
    Customer customer = customerService.findById(dto.getCustomerId());
    this.customerCode = customer.getCode();
    this.customerName = customer.getName();

    this.status = dto.getStatus().getCode();

    SysUserService userService = ApplicationUtil.getBean(SysUserService.class);

    if (!StringUtil.isBlank(dto.getApproveBy())) {
      this.approveBy = userService.findById(dto.getApproveBy()).getName();
    }

    if (!CollectionUtil.isEmpty(dto.getDetails())) {
      this.details = dto.getDetails().stream().map(SheetDetailBo::new)
          .collect(Collectors.toList());
    }
  }

  @Data
  public static class SheetDetailBo extends BaseBo<CustomerSettlePreSheetFullDto.SheetDetailDto> {

    /**
     * 明细ID
     */
    @Schema(description = "明细ID")
    private String id;

    /**
     * 项目ID
     */
    @Schema(description = "项目ID")
    private String itemId;

    /**
     * 项目名称
     */
    @Schema(description = "项目名称")
    private String itemName;

    /**
     * 金额
     */
    @Schema(description = "金额")
    private BigDecimal amount;

    public SheetDetailBo() {

    }

    public SheetDetailBo(CustomerSettlePreSheetFullDto.SheetDetailDto dto) {

      super(dto);
    }

    @Override
    protected void afterInit(CustomerSettlePreSheetFullDto.SheetDetailDto dto) {

      SettleInItemService settleInItemService = ApplicationUtil.getBean(
          SettleInItemService.class);
      SettleInItem item = settleInItemService.findById(dto.getItemId());
      this.itemName = item.getName();
    }
  }
}
