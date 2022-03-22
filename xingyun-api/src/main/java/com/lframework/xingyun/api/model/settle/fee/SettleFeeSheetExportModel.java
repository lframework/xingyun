package com.lframework.xingyun.api.model.settle.fee;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.DateUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.components.excel.ExcelModel;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.service.IUserService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.xingyun.basedata.dto.supplier.SupplierDto;
import com.lframework.xingyun.basedata.service.supplier.ISupplierService;
import com.lframework.xingyun.sc.enums.SettleStatus;
import com.lframework.xingyun.settle.dto.fee.SettleFeeSheetDto;
import com.lframework.xingyun.settle.enums.SettleFeeSheetStatus;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SettleFeeSheetExportModel extends BaseBo<SettleFeeSheetDto> implements ExcelModel {

  /**
   * 业务单据号
   */
  @ExcelProperty("业务单据号")
  private String code;

  /**
   * 供应商编号
   */
  @ExcelProperty("供应商编号")
  private String supplierCode;

  /**
   * 供应商名称
   */
  @ExcelProperty("供应商名称")
  private String supplierName;

  /**
   * 单据总金额
   */
  @ExcelProperty("单据总金额")
  private BigDecimal totalAmount;

  /**
   * 操作时间
   */
  @ExcelProperty("操作时间")
  @DateTimeFormat(StringPool.DATE_TIME_PATTERN)
  private Date createTime;

  /**
   * 操作人
   */
  @ExcelProperty("操作人")
  private String createBy;

  /**
   * 审核状态
   */
  @ExcelProperty("审核状态")
  private String status;

  /**
   * 审核时间
   */
  @ExcelProperty("审核时间")
  @DateTimeFormat(StringPool.DATE_TIME_PATTERN)
  private Date approveTime;

  /**
   * 审核人
   */
  @ExcelProperty("审核人")
  private String approveBy;

  /**
   * 结算状态
   */
  @ExcelProperty("结算状态")
  private String settleStatus;

  /**
   * 备注
   */
  @ExcelProperty("备注")
  private String description;

  public SettleFeeSheetExportModel() {

  }

  public SettleFeeSheetExportModel(SettleFeeSheetDto dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<SettleFeeSheetDto> convert(SettleFeeSheetDto dto) {

    return this;
  }

  @Override
  protected void afterInit(SettleFeeSheetDto dto) {

    ISupplierService supplierService = ApplicationUtil.getBean(ISupplierService.class);
    SupplierDto supplier = supplierService.getById(dto.getSupplierId());

    IUserService userService = ApplicationUtil.getBean(IUserService.class);
    UserDto createBy = userService.getById(dto.getCreateBy());
    UserDto approveBy = null;
    if (!StringUtil.isBlank(dto.getApproveBy())) {
      approveBy = userService.getById(dto.getApproveBy());
    }

    this.setCode(dto.getCode());
    this.setSupplierCode(supplier.getCode());
    this.setSupplierName(supplier.getName());
    this.setTotalAmount(dto.getTotalAmount());
    this.setCreateTime(DateUtil.toDate(dto.getCreateTime()));
    this.setCreateBy(createBy.getName());
    this.setStatus(EnumUtil.getDesc(SettleFeeSheetStatus.class, dto.getStatus()));
    if (approveBy != null) {
      this.setApproveBy(approveBy.getName());
    }

    if (dto.getApproveTime() != null) {
      this.setApproveTime(DateUtil.toDate(dto.getApproveTime()));
    }
    this.setSettleStatus(EnumUtil.getDesc(SettleStatus.class, dto.getSettleStatus()));
    this.setDescription(dto.getDescription());
  }
}
