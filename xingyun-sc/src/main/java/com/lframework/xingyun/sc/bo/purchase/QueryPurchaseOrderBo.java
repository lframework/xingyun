package com.lframework.xingyun.sc.bo.purchase;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.entity.Supplier;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.basedata.service.supplier.SupplierService;
import com.lframework.xingyun.sc.entity.PurchaseOrder;
import com.lframework.starter.web.inner.service.system.SysUserService;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryPurchaseOrderBo extends BaseBo<PurchaseOrder> {

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
   * 仓库编号
   */
  @ApiModelProperty("仓库编号")
  private String scCode;

  /**
   * 仓库名称
   */
  @ApiModelProperty("仓库名称")
  private String scName;

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
   * 采购员姓名
   */
  @ApiModelProperty("采购员姓名")
  private String purchaserName;

  /**
   * 预计到货日期
   */
  @ApiModelProperty("预计到货日期")
  @JsonFormat(pattern = StringPool.DATE_PATTERN)
  private LocalDate expectArriveDate;

  /**
   * 采购数量
   */
  @ApiModelProperty("采购数量")
  private Integer totalNum;

  /**
   * 赠品数量
   */
  @ApiModelProperty("赠品数量")
  private Integer totalGiftNum;

  /**
   * 采购金额
   */
  @ApiModelProperty("采购金额")
  private BigDecimal totalAmount;

  /**
   * 关联的审批流程ID
   */
  @ApiModelProperty("关联的审批流程ID")
  private String flowInstanceId;

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
   * 拒绝原因
   */
  @ApiModelProperty("拒绝原因")
  private String refuseReason;

  public QueryPurchaseOrderBo(PurchaseOrder dto) {

    super(dto);
  }

  @Override
  public BaseBo<PurchaseOrder> convert(PurchaseOrder dto) {

    return super.convert(dto, QueryPurchaseOrderBo::getStatus);
  }

  @Override
  protected void afterInit(PurchaseOrder dto) {

    StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
    StoreCenter sc = storeCenterService.findById(dto.getScId());
    this.scCode = sc.getCode();
    this.scName = sc.getName();

    SupplierService supplierService = ApplicationUtil.getBean(SupplierService.class);
    Supplier supplier = supplierService.findById(dto.getSupplierId());
    this.supplierCode = supplier.getCode();
    this.supplierName = supplier.getName();

    SysUserService userService = ApplicationUtil.getBean(SysUserService.class);
    if (!StringUtil.isBlank(dto.getPurchaserId())) {
      this.purchaserName = userService.findById(dto.getPurchaserId()).getName();
    }

    if (!StringUtil.isBlank(dto.getApproveBy())) {
      this.approveBy = userService.findById(dto.getApproveBy()).getName();
    }

    this.status = dto.getStatus().getCode();
  }
}
