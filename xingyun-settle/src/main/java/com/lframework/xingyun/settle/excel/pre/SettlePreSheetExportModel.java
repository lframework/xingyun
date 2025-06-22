package com.lframework.xingyun.settle.excel.pre;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.components.excel.ExcelModel;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.utils.EnumUtil;
import com.lframework.xingyun.basedata.entity.Supplier;
import com.lframework.xingyun.basedata.service.supplier.SupplierService;
import com.lframework.starter.web.inner.entity.SysUser;
import com.lframework.xingyun.sc.enums.SettleStatus;
import com.lframework.xingyun.settle.entity.SettlePreSheet;
import com.lframework.xingyun.settle.enums.SettlePreSheetStatus;
import com.lframework.starter.web.inner.service.system.SysUserService;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class SettlePreSheetExportModel extends BaseBo<SettlePreSheet> implements ExcelModel {

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

  public SettlePreSheetExportModel() {

  }

  public SettlePreSheetExportModel(SettlePreSheet dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<SettlePreSheet> convert(SettlePreSheet dto) {

    return this;
  }

  @Override
  protected void afterInit(SettlePreSheet dto) {

    SupplierService supplierService = ApplicationUtil.getBean(SupplierService.class);
    Supplier supplier = supplierService.findById(dto.getSupplierId());

    SysUserService userService = ApplicationUtil.getBean(SysUserService.class);
    SysUser approveBy = null;
    if (!StringUtil.isBlank(dto.getApproveBy())) {
      approveBy = userService.findById(dto.getApproveBy());
    }

    this.setCode(dto.getCode());
    this.setSupplierCode(supplier.getCode());
    this.setSupplierName(supplier.getName());
    this.setTotalAmount(dto.getTotalAmount());
    this.setCreateTime(DateUtil.toDate(dto.getCreateTime()));
    this.setStatus(EnumUtil.getDesc(SettlePreSheetStatus.class, dto.getStatus()));
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
