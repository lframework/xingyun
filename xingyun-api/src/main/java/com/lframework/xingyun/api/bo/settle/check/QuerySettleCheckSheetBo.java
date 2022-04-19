package com.lframework.xingyun.api.bo.settle.check;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.DateUtil;
import com.lframework.common.utils.NumberUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.supplier.SupplierDto;
import com.lframework.xingyun.basedata.service.supplier.ISupplierService;
import com.lframework.xingyun.settle.dto.check.SettleCheckSheetDto;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QuerySettleCheckSheetBo extends BaseBo<SettleCheckSheetDto> {

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
   * 供应商ID
   */
  @ApiModelProperty("供应商ID")
  private String supplierId;

  /**
   * 供应商编号
   */
  @ApiModelProperty("供应商编号")
  private String supplierCode;

  /**
   * 供应商名称
   */
  @ApiModelProperty("供应商名称")
  private String supplierName;

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

  public QuerySettleCheckSheetBo() {

  }

  public QuerySettleCheckSheetBo(SettleCheckSheetDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<SettleCheckSheetDto> convert(SettleCheckSheetDto dto) {

    return super
        .convert(dto, QuerySettleCheckSheetBo::getStatus, QuerySettleCheckSheetBo::getSettleStatus);
  }

  @Override
  protected void afterInit(SettleCheckSheetDto dto) {

    ISupplierService supplierService = ApplicationUtil.getBean(ISupplierService.class);
    SupplierDto supplier = supplierService.getById(dto.getSupplierId());
    this.supplierCode = supplier.getCode();
    this.supplierName = supplier.getName();

    this.status = dto.getStatus().getCode();
    this.settleStatus = dto.getSettleStatus().getCode();

    this.totalUnPayAmount = NumberUtil
        .sub(dto.getTotalPayAmount(), dto.getTotalPayedAmount(), dto.getTotalDiscountAmount());

    IUserService userService = ApplicationUtil.getBean(IUserService.class);
    UserDto createBy = userService.getById(dto.getCreateBy());
    this.createBy = createBy.getName();

    if (!StringUtil.isBlank(dto.getApproveBy())) {
      this.approveBy = userService.getById(dto.getApproveBy()).getName();
    }

    this.startTime = DateUtil.toLocalDateTime(dto.getStartDate());
    this.endTime = DateUtil.toLocalDateTimeMax(dto.getEndDate());
  }
}
