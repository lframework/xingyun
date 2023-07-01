package com.lframework.xingyun.sc.bo.purchase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lframework.starter.common.functions.SFunction;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.xingyun.template.core.service.UserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.xingyun.template.core.dto.UserDto;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.entity.Supplier;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.basedata.service.supplier.SupplierService;
import com.lframework.xingyun.sc.dto.purchase.PurchaseOrderWithReceiveDto;
import com.lframework.xingyun.sc.dto.purchase.PurchaseProductDto;
import com.lframework.xingyun.sc.entity.ProductStock;
import com.lframework.xingyun.sc.service.purchase.PurchaseOrderService;
import com.lframework.xingyun.sc.service.stock.ProductStockService;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class PurchaseOrderWithReceiveBo extends BaseBo<PurchaseOrderWithReceiveDto> {

  /**
   * 订单ID
   */
  @ApiModelProperty("订单ID")
  private String id;

  /**
   * 仓库ID
   */
  @ApiModelProperty("仓库ID")
  private String scId;

  /**
   * 仓库名称
   */
  @ApiModelProperty("仓库名称")
  private String scName;

  /**
   * 供应商ID
   */
  @ApiModelProperty("供应商ID")
  private String supplierId;

  /**
   * 供应商名称
   */
  @ApiModelProperty("供应商名称")
  private String supplierName;

  /**
   * 采购员ID
   */
  @ApiModelProperty("采购员ID")
  private String purchaserId;

  /**
   * 采购员姓名
   */
  @ApiModelProperty("采购员姓名")
  private String purchaserName;

  /**
   * 订单明细
   */
  @ApiModelProperty("订单明细")
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
      UserService userService = ApplicationUtil.getBean(UserService.class);
      UserDto purchaser = userService.findById(dto.getPurchaserId());

      this.purchaserId = purchaser.getId();
      this.purchaserName = purchaser.getName();
    }

    if (!CollectionUtil.isEmpty(dto.getDetails())) {
      this.details = dto.getDetails().stream().map(t -> new DetailBo(this.scId, t)).collect(Collectors.toList());
    }
  }

  @Data
  public static class DetailBo extends BaseBo<PurchaseOrderWithReceiveDto.DetailDto> {

    /**
     * ID
     */
    @ApiModelProperty("ID")
    private String id;

    /**
     * 商品ID
     */
    @ApiModelProperty("商品ID")
    private String productId;

    /**
     * 商品编号
     */
    @ApiModelProperty("商品编号")
    private String productCode;

    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
    private String productName;

    /**
     * SKU编号
     */
    @ApiModelProperty("SKU编号")
    private String skuCode;

    /**
     * 外部编号
     */
    @ApiModelProperty("外部编号")
    private String externalCode;

    /**
     * 单位
     */
    @ApiModelProperty("单位")
    private String unit;

    /**
     * 规格
     */
    @ApiModelProperty("规格")
    private String spec;

    /**
     * 类目名称
     */
    @ApiModelProperty("类目名称")
    private String categoryName;

    /**
     * 品牌名称
     */
    @ApiModelProperty("品牌名称")
    private String brandName;

    /**
     * 采购数量
     */
    @ApiModelProperty("采购数量")
    private Integer orderNum;

    /**
     * 采购价
     */
    @ApiModelProperty("采购价")
    private BigDecimal purchasePrice;

    /**
     * 剩余收货数量
     */
    @ApiModelProperty("剩余收货数量")
    private Integer remainNum;

    /**
     * 是否赠品
     */
    @ApiModelProperty("是否赠品")
    private Boolean isGift;

    /**
     * 税率（%）
     */
    @ApiModelProperty("税率（%）")
    private BigDecimal taxRate;

    /**
     * 含税成本价
     */
    @ApiModelProperty("含税成本价")
    private BigDecimal taxCostPrice;

    /**
     * 库存数量
     */
    @ApiModelProperty("库存数量")
    private Integer stockNum;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String description;

    /**
     * 仓库ID
     */
    @ApiModelProperty(value = "仓库ID", hidden = true)
    @JsonIgnore
    private String scId;

    public DetailBo() {

    }

    public DetailBo(String scId, PurchaseOrderWithReceiveDto.DetailDto dto) {

      this.scId = scId;
      this.init(dto);
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

      PurchaseOrderService purchaseOrderService = ApplicationUtil.getBean(
          PurchaseOrderService.class);
      PurchaseProductDto product = purchaseOrderService.getPurchaseById(dto.getProductId());

      this.id = dto.getId();
      this.productId = product.getId();
      this.productCode = product.getCode();
      this.productName = product.getName();
      this.skuCode = product.getSkuCode();
      this.externalCode = product.getExternalCode();
      this.unit = product.getUnit();
      this.spec = product.getSpec();
      this.categoryName = product.getCategoryName();
      this.brandName = product.getBrandName();

      this.orderNum = dto.getOrderNum();
      this.purchasePrice = dto.getTaxPrice();
      this.remainNum = NumberUtil.sub(dto.getOrderNum(), dto.getReceiveNum()).intValue();
      this.isGift = dto.getIsGift();
      this.taxRate = dto.getTaxRate();
      this.description = dto.getDescription();

      ProductStockService productStockService = ApplicationUtil.getBean(
          ProductStockService.class);
      ProductStock productStock = productStockService.getByProductIdAndScId(
          this.getProductId(), this.getScId());
      this.taxCostPrice =
          productStock == null ? BigDecimal.ZERO
              : NumberUtil.getNumber(productStock.getTaxPrice(), 2);
      this.stockNum = productStock == null ? 0 : productStock.getStockNum();
    }
  }
}
