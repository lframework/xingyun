package com.lframework.xingyun.basedata.bo.product.info;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.ProductSku;
import com.lframework.xingyun.basedata.entity.ProductSkuPurchase;
import com.lframework.xingyun.basedata.entity.ProductSkuRetail;
import com.lframework.xingyun.basedata.entity.ProductSkuSale;
import com.lframework.xingyun.basedata.entity.ProductSkuSalePropertyRelation;
import com.lframework.xingyun.basedata.entity.ProductSalePropertyDefinition;
import com.lframework.xingyun.basedata.entity.ProductSalePropertyItem;
import com.lframework.xingyun.basedata.service.product.ProductSkuCodeService;
import com.lframework.xingyun.basedata.service.product.ProductSkuPurchaseService;
import com.lframework.xingyun.basedata.service.product.ProductSkuRetailService;
import com.lframework.xingyun.basedata.service.product.ProductSkuSaleService;
import com.lframework.xingyun.basedata.service.product.ProductSkuSalePropertyRelationService;
import com.lframework.xingyun.basedata.service.product.ProductSalePropertyItemService;
import com.lframework.xingyun.basedata.service.product.ProductSalePropertyDefinitionService;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class ProductSkuBo extends BaseBo<ProductSku> {

  @Schema(description = "SKU ID")
  private String id;

  @Schema(description = "商品ID")
  private String productId;

  @Schema(description = "SKU编号")
  private String code;

  @Schema(description = "是否一品多码")
  private Boolean multiCode;

  @Schema(description = "销售属性")
  private String salePropertyText;

  @Schema(description = "扩展编号")
  private List<String> multiCodes;

  @Schema(description = "SKU主图")
  private String mainImage;

  @Schema(description = "销售属性")
  private List<SalePropertyBo> saleProperties;

  @Schema(description = "采购价")
  private BigDecimal purchasePrice;

  @Schema(description = "销售价")
  private BigDecimal salePrice;

  @Schema(description = "零售价")
  private BigDecimal retailPrice;

  public ProductSkuBo() {
  }

  public ProductSkuBo(ProductSku dto) {

    super(dto);
  }

  @Override
  protected void afterInit(ProductSku dto) {

    ProductSkuCodeService productSkuCodeService = ApplicationUtil.getBean(
        ProductSkuCodeService.class);
    this.multiCodes = productSkuCodeService.getExtraCodes(dto.getId());

    ProductSkuPurchaseService productSkuPurchaseService = ApplicationUtil.getBean(
        ProductSkuPurchaseService.class);
    ProductSkuPurchase productSkuPurchase = productSkuPurchaseService.getById(dto.getId());
    if (productSkuPurchase != null) {
      this.purchasePrice = productSkuPurchase.getPrice();
    }

    ProductSkuSaleService productSkuSaleService = ApplicationUtil.getBean(
        ProductSkuSaleService.class);
    ProductSkuSale productSkuSale = productSkuSaleService.getById(dto.getId());
    if (productSkuSale != null) {
      this.salePrice = productSkuSale.getPrice();
    }

    ProductSkuRetailService productSkuRetailService = ApplicationUtil.getBean(
        ProductSkuRetailService.class);
    ProductSkuRetail productSkuRetail = productSkuRetailService.getById(dto.getId());
    if (productSkuRetail != null) {
      this.retailPrice = productSkuRetail.getPrice();
    }

    ProductSkuSalePropertyRelationService relationService = ApplicationUtil.getBean(
        ProductSkuSalePropertyRelationService.class);
    this.saleProperties = relationService.getBySkuId(dto.getId()).stream()
        .map(SalePropertyBo::new).collect(Collectors.toList());
  }

  @Data
  public static class SalePropertyBo extends BaseBo<ProductSkuSalePropertyRelation> {

    @Schema(description = "销售属性ID")
    private String propertyId;

    @Schema(description = "销售属性名称")
    private String propertyName;

    @Schema(description = "销售属性值ID")
    private String propertyItemId;

    @Schema(description = "销售属性值名称")
    private String propertyItemName;

    public SalePropertyBo() {
    }

    public SalePropertyBo(ProductSkuSalePropertyRelation dto) {

      super(dto);
    }

    @Override
    protected void afterInit(ProductSkuSalePropertyRelation dto) {

      ProductSalePropertyDefinitionService propertyService = ApplicationUtil.getBean(
          ProductSalePropertyDefinitionService.class);
      ProductSalePropertyDefinition property = propertyService.findById(dto.getPropertyId());
      if (property != null) {
        this.propertyName = property.getName();
      }

      ProductSalePropertyItemService itemService = ApplicationUtil.getBean(
          ProductSalePropertyItemService.class);
      ProductSalePropertyItem item = itemService.findById(dto.getPropertyItemId());
      if (item != null) {
        this.propertyItemName = item.getName();
      }
    }
  }
}
