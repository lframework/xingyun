package com.lframework.xingyun.sc.bo.purchase;

import com.lframework.starter.common.functions.SFunction;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.entity.Supplier;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.basedata.service.supplier.SupplierService;
import com.lframework.starter.web.inner.entity.SysUser;
import com.lframework.xingyun.sc.dto.purchase.PurchaseOrderWithReceiveDto;
import com.lframework.starter.web.inner.service.system.SysUserService;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class PurchaseOrderWithReceiveBo extends BaseBo<PurchaseOrderWithReceiveDto> {

  /**
   * 订单ID
   */
  @Schema(description = "订单ID")
  private String id;

  /**
   * 仓库ID
   */
  @Schema(description = "仓库ID")
  private String scId;

  /**
   * 仓库名称
   */
  @Schema(description = "仓库名称")
  private String scName;

  /**
   * 供应商ID
   */
  @Schema(description = "供应商ID")
  private String supplierId;

  /**
   * 供应商名称
   */
  @Schema(description = "供应商名称")
  private String supplierName;

  /**
   * 采购员ID
   */
  @Schema(description = "采购员ID")
  private String purchaserId;

  /**
   * 采购员姓名
   */
  @Schema(description = "采购员姓名")
  private String purchaserName;

  /**
   * 订单明细
   */
  @Schema(description = "订单明细")
  private List<DetailBo> details;

  public PurchaseOrderWithReceiveBo() {

  }

  public PurchaseOrderWithReceiveBo(PurchaseOrderWithReceiveDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<PurchaseOrderWithReceiveDto> convert(PurchaseOrderWithReceiveDto dto) {

    return super.convert(dto, PurchaseOrderWithReceiveBo::getPurchaserId,
        PurchaseOrderWithReceiveBo::getDetails);
  }

  @Override
  protected void afterInit(PurchaseOrderWithReceiveDto dto) {

    StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
    StoreCenter sc = storeCenterService.findById(dto.getScId());
    this.scName = sc.getName();

    SupplierService supplierService = ApplicationUtil.getBean(SupplierService.class);
    Supplier supplier = supplierService.findById(dto.getSupplierId());
    this.supplierName = supplier.getName();

    if (!StringUtil.isBlank(dto.getPurchaserId())) {
      SysUserService userService = ApplicationUtil.getBean(SysUserService.class);
      SysUser purchaser = userService.findById(dto.getPurchaserId());

      this.purchaserId = purchaser.getId();
      this.purchaserName = purchaser.getName();
    }

    if (!CollectionUtil.isEmpty(dto.getDetails())) {
      this.details = dto.getDetails().stream().map(DetailBo::new)
          .collect(Collectors.toList());
    }
  }

  @Data
  public static class DetailBo extends BaseBo<PurchaseOrderWithReceiveDto.DetailDto> {

    /**
     * ID
     */
    @Schema(description = "ID")
    private String id;

    /**
     * 商品ID
     */
    @Schema(description = "商品ID")
    private String productId;

    /**
     * SKU ID
     */
    @Schema(description = "SKU ID")
    private String skuId;

    /**
     * 商品编号
     */
    @Schema(description = "商品编号")
    private String productCode;

    /**
     * SKU编号
     */
    @Schema(description = "SKU编号")
    private String skuCode;

    /**
     * 销售属性
     */
    @Schema(description = "销售属性")
    private String salePropertyText;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String productName;

    /**
     * 单位
     */
    @Schema(description = "单位")
    private String unit;

    /**
     * 规格
     */
    @Schema(description = "规格")
    private String spec;

    /**
     * 分类名称
     */
    @Schema(description = "分类名称")
    private String categoryName;

    /**
     * 品牌名称
     */
    @Schema(description = "品牌名称")
    private String brandName;

    /**
     * 采购数量
     */
    @Schema(description = "采购数量")
    private BigDecimal orderNum;

    /**
     * 采购价
     */
    @Schema(description = "采购价")
    private BigDecimal purchasePrice;

    /**
     * 剩余收货数量
     */
    @Schema(description = "剩余收货数量")
    private BigDecimal remainNum;

    /**
     * 是否赠品
     */
    @Schema(description = "是否赠品")
    private Boolean isGift;

    /**
     * 税率（%）
     */
    @Schema(description = "税率（%）")
    private BigDecimal taxRate;

    /**
     * 含税成本价
     */
    @Schema(description = "含税成本价")
    private BigDecimal taxCostPrice;

    /**
     * 库存数量
     */
    @Schema(description = "库存数量")
    private BigDecimal stockNum;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String description;

    public DetailBo() {

    }

    public DetailBo(PurchaseOrderWithReceiveDto.DetailDto dto) {

      super(dto);
    }

    @Override
    public BaseBo<PurchaseOrderWithReceiveDto.DetailDto> convert(
        PurchaseOrderWithReceiveDto.DetailDto dto) {

      return this;
    }

    @Override
    public <A> BaseBo<PurchaseOrderWithReceiveDto.DetailDto> convert(
        PurchaseOrderWithReceiveDto.DetailDto dto,
        SFunction<A, ?>... columns) {

      return this;
    }

    @Override
    protected void afterInit(PurchaseOrderWithReceiveDto.DetailDto dto) {

      this.purchasePrice = dto.getTaxPrice();
      BigDecimal receiveNum = dto.getReceiveNum() == null ? BigDecimal.ZERO : dto.getReceiveNum();
      this.remainNum = NumberUtil.sub(dto.getOrderNum(), receiveNum);

      this.taxCostPrice =
          dto.getTaxCostPrice() == null ? BigDecimal.ZERO
              : NumberUtil.getNumber(dto.getTaxCostPrice(), 2);
      this.stockNum = dto.getStockNum() == null ? BigDecimal.ZERO : dto.getStockNum();
    }
  }
}
