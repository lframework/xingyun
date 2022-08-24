package com.lframework.xingyun.sc.api.bo.purchase;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.facade.StoreCenterFeignClient;
import com.lframework.xingyun.basedata.facade.SupplierFeignClient;
import com.lframework.xingyun.basedata.facade.entity.StoreCenter;
import com.lframework.xingyun.basedata.facade.entity.Supplier;
import com.lframework.xingyun.sc.facade.entity.PurchaseOrder;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PurchaseOrderSelectorBo extends BaseBo<PurchaseOrder> {

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
   * 状态
   */
  @ApiModelProperty("状态")
  private Integer status;

  public PurchaseOrderSelectorBo() {

  }

  public PurchaseOrderSelectorBo(PurchaseOrder dto) {

    super(dto);
  }

  @Override
  public BaseBo<PurchaseOrder> convert(PurchaseOrder dto) {

    return super.convert(dto, PurchaseOrderSelectorBo::getStatus);
  }

  @Override
  protected void afterInit(PurchaseOrder dto) {

    StoreCenterFeignClient storeCenterFeignClient = ApplicationUtil.getBean(
        StoreCenterFeignClient.class);
    StoreCenter sc = storeCenterFeignClient.findById(dto.getScId()).getData();
    this.scCode = sc.getCode();
    this.scName = sc.getName();

    SupplierFeignClient suppliesupplierFeignClientService = ApplicationUtil.getBean(
        SupplierFeignClient.class);
    Supplier supplier = suppliesupplierFeignClientService.findById(dto.getSupplierId()).getData();
    this.supplierCode = supplier.getCode();
    this.supplierName = supplier.getName();

    IUserService userService = ApplicationUtil.getBean(IUserService.class);

    this.createBy = userService.findById(dto.getCreateBy()).getName();

    this.status = dto.getStatus().getCode();
  }
}
