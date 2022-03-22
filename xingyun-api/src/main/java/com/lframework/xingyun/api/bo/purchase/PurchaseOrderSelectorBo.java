package com.lframework.xingyun.api.bo.purchase;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.service.IUserService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.storecenter.StoreCenterDto;
import com.lframework.xingyun.basedata.dto.supplier.SupplierDto;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.basedata.service.supplier.ISupplierService;
import com.lframework.xingyun.sc.dto.purchase.PurchaseOrderDto;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PurchaseOrderSelectorBo extends BaseBo<PurchaseOrderDto> {

  /**
   * ID
   */
  private String id;

  /**
   * 单号
   */
  private String code;

  /**
   * 仓库编号
   */
  private String scCode;

  /**
   * 仓库名称
   */
  private String scName;

  /**
   * 供应商编号
   */
  private String supplierCode;

  /**
   * 供应商名称
   */
  private String supplierName;

  /**
   * 创建人
   */
  private String createBy;

  /**
   * 创建时间
   */
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  /**
   * 状态
   */
  private Integer status;

  public PurchaseOrderSelectorBo() {

  }

  public PurchaseOrderSelectorBo(PurchaseOrderDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<PurchaseOrderDto> convert(PurchaseOrderDto dto) {

    return super.convert(dto, PurchaseOrderSelectorBo::getStatus);
  }

  @Override
  protected void afterInit(PurchaseOrderDto dto) {

    IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
    StoreCenterDto sc = storeCenterService.getById(dto.getScId());
    this.scCode = sc.getCode();
    this.scName = sc.getName();

    ISupplierService supplierService = ApplicationUtil.getBean(ISupplierService.class);
    SupplierDto supplier = supplierService.getById(dto.getSupplierId());
    this.supplierCode = supplier.getCode();
    this.supplierName = supplier.getName();

    IUserService userService = ApplicationUtil.getBean(IUserService.class);

    this.createBy = userService.getById(dto.getCreateBy()).getName();

    this.status = dto.getStatus().getCode();
  }
}
