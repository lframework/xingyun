package com.lframework.xingyun.basedata.bo.product.info;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.annotations.convert.EnumConvert;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.utils.JsonUtil;
import com.lframework.xingyun.basedata.dto.product.ProductCategoryPropertyValueRelationDto;
import com.lframework.xingyun.basedata.dto.product.ProductCategoryPropertyValueRelationDto;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.ProductBrand;
import com.lframework.xingyun.basedata.entity.ProductBundle;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.entity.ProductPurchase;
import com.lframework.xingyun.basedata.entity.ProductRetail;
import com.lframework.xingyun.basedata.entity.ProductSale;
import com.lframework.xingyun.basedata.entity.ProductSku;
import com.lframework.xingyun.basedata.entity.ProductSkuCode;
import com.lframework.xingyun.basedata.enums.ColumnType;
import com.lframework.xingyun.basedata.enums.ProductSkuCodeType;
import com.lframework.xingyun.basedata.enums.ProductType;
import com.lframework.xingyun.basedata.service.product.ProductBrandService;
import com.lframework.xingyun.basedata.service.product.ProductBundleService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryPropertyValueRelationService;
import com.lframework.xingyun.basedata.service.product.ProductPurchaseService;
import com.lframework.xingyun.basedata.service.product.ProductRetailService;
import com.lframework.xingyun.basedata.service.product.ProductSaleService;
import com.lframework.xingyun.basedata.service.product.ProductSkuCodeService;
import com.lframework.xingyun.basedata.service.product.ProductSkuService;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class GetProductBo extends BaseBo<Product> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 编号
   */
  @Schema(description = "编号")
  private String code;

  /**
   * 是否一品多码
   */
  @Schema(description = "是否一品多码")
  private Boolean multiCode;

  /**
   * 扩展编号
   */
  @Schema(description = "扩展编号")
  private List<String> multiCodes;

  /**
   * 扩展编号 - 字符串
   */
  @Schema(description = "扩展编号 - 字符串")
  private String multiCodesStr;

  /**
   * 名称
   */
  @Schema(description = "名称")
  private String name;

  /**
   * 简称
   */
  @Schema(description = "简称")
  private String shortName;

  /**
   * 分类ID
   */
  @Schema(description = "分类ID")
  private String categoryId;

  /**
   * 分类名称
   */
  @Schema(description = "分类名称")
  private String categoryName;

  /**
   * 品牌ID
   */
  @Schema(description = "品牌ID")
  private String brandId;

  /**
   * 品牌名称
   */
  @Schema(description = "品牌名称")
  private String brandName;

  /**
   * 重量（kg）
   */
  @Schema(description = "重量（kg）")
  private BigDecimal weight;

  /**
   * 体积（cm3）
   */
  @Schema(description = "体积（cm3）")
  private BigDecimal volume;

  /**
   * 进项税率（%）
   */
  @Schema(description = "进项税率（%）")
  private BigDecimal taxRate;

  /**
   * 销项税率（%）
   */
  @Schema(description = "销项税率（%）")
  private BigDecimal saleTaxRate;

  /**
   * 规格
   */
  @Schema(description = "规格")
  private String spec;

  /**
   * 单位
   */
  @Schema(description = "单位")
  private String unit;

  /**
   * 商品类型
   */
  @Schema(description = "商品类型")
  @EnumConvert
  private Integer productType;

  /**
   * 单品
   */
  @Schema(description = "单品")
  private List<ProductBundleBo> productBundles;

  /**
   * 采购价
   */
  @Schema(description = "采购价")
  private BigDecimal purchasePrice;

  /**
   * 销售价
   */
  @Schema(description = "销售价")
  private BigDecimal salePrice;

  /**
   * 零售价
   */
  @Schema(description = "零售价")
  private BigDecimal retailPrice;

  /**
   * SKU类型
   */
  @Schema(description = "SKU类型")
  private Integer skuType;

  /**
   * 详情图片
   */
  @Schema(description = "详情图片")
  private List<String> detailImages;

  /**
   * 商品主图
   */
  @Schema(description = "商品主图")
  private List<String> mainImage;

  /**
   * SKU列表
   */
  @Schema(description = "SKU列表")
  private List<ProductSkuBo> skus;

  /**
   * 属性
   */
  @Schema(description = "属性")
  private List<PropertyBo> properties;

  public GetProductBo() {

  }

  public GetProductBo(Product dto) {

    super(dto);
  }

  @Override
  public BaseBo<Product> convert(Product dto) {

    return super.convert(dto, GetProductBo::getProperties);
  }

  @Override
  protected void afterInit(Product dto) {

    ProductCategoryService productCategoryService = ApplicationUtil.getBean(
        ProductCategoryService.class);
    ProductCategory productCategory = productCategoryService.findById(dto.getCategoryId());
    this.categoryName = productCategory.getName();

    if (StringUtil.isNotBlank(dto.getBrandId())) {
      ProductBrandService productBrandService = ApplicationUtil.getBean(ProductBrandService.class);
      ProductBrand productBrand = productBrandService.findById(dto.getBrandId());
      this.brandName = productBrand.getName();
    }

    if (dto.getProductType() == ProductType.BUNDLE) {
      ProductBundleService productBundleService = ApplicationUtil.getBean(
          ProductBundleService.class);
      ProductSkuService productSkuService = ApplicationUtil.getBean(ProductSkuService.class);
      List<ProductSku> productSkus = productSkuService.getAvailableByProductId(dto.getId());
      String mainSkuId = CollectionUtil.isEmpty(productSkus) ? dto.getId()
          : productSkus.get(0).getId();
      List<ProductBundle> bundles = productBundleService.getByMainProductId(mainSkuId);
      this.productBundles = bundles.stream().map(ProductBundleBo::new).collect(Collectors.toList());
    }

    ProductSkuService productSkuService = ApplicationUtil.getBean(ProductSkuService.class);
    List<ProductSku> productSkus = productSkuService.getAvailableByProductId(dto.getId());
    this.skus = productSkus.stream().map(ProductSkuBo::new).collect(Collectors.toList());
    this.skuType = dto.getSkuType() == null ? null : dto.getSkuType().getCode();
    this.mainImage = StringUtil.isBlank(dto.getMainImage()) ? CollectionUtil.emptyList()
        : JsonUtil.parseList(dto.getMainImage(), String.class);
    this.detailImages = StringUtil.isBlank(dto.getDetailImages()) ? CollectionUtil.emptyList()
        : JsonUtil.parseList(dto.getDetailImages(), String.class);

    ProductPurchaseService productPurchaseService = ApplicationUtil.getBean(
        ProductPurchaseService.class);
    String singleSkuId = CollectionUtil.isEmpty(productSkus) ? dto.getId()
        : productSkus.get(0).getId();
    ProductPurchase productPurchase = productPurchaseService.getById(singleSkuId);
    if (productPurchase != null) {
      this.purchasePrice = productPurchase.getPrice();
    }

    ProductSaleService productSaleService = ApplicationUtil.getBean(ProductSaleService.class);
    ProductSale productSale = productSaleService.getById(singleSkuId);
    if (productSale != null) {
      this.salePrice = productSale.getPrice();
    }

    ProductRetailService productRetailService = ApplicationUtil.getBean(
        ProductRetailService.class);
    ProductRetail productRetail = productRetailService.getById(singleSkuId);
    if (productRetail != null) {
      this.retailPrice = productRetail.getPrice();
    }

    ProductCategoryPropertyValueRelationService ProductCategoryPropertyValueRelationService = ApplicationUtil.getBean(
        ProductCategoryPropertyValueRelationService.class);
    List<ProductCategoryPropertyValueRelationDto> propertyRelationDtos = ProductCategoryPropertyValueRelationService.getByProductId(
        dto.getId());
    if (!CollectionUtil.isEmpty(propertyRelationDtos)) {
      this.properties = new ArrayList<>();
      for (ProductCategoryPropertyValueRelationDto property : propertyRelationDtos) {
        if (property.getPropertyColumnType() == ColumnType.MULTIPLE) {
          PropertyBo propertyBo = this.properties.stream()
              .filter(t -> t.getId().equals(property.getPropertyId())).findFirst().orElse(null);
          if (propertyBo == null) {
            this.properties.add(new PropertyBo(property));
          } else {
            propertyBo.setText(propertyBo.getText().concat(StringPool.STR_SPLIT)
                .concat(property.getPropertyItemId()));
            propertyBo.setTextStr(propertyBo.getTextStr().concat(StringPool.STR_SPLIT_CN)
                .concat(property.getPropertyText()));
          }
        } else {
          this.properties.add(new PropertyBo(property));
        }
      }
    }

    ProductSkuCodeService productSkuCodeService = ApplicationUtil.getBean(
        ProductSkuCodeService.class);
    List<ProductSkuCode> productCodes = productSkuCodeService.list(
        Wrappers.lambdaQuery(ProductSkuCode.class).eq(ProductSkuCode::getSkuId, singleSkuId));
    this.multiCode = productSkus.stream().findFirst().map(ProductSku::getMultiCode).orElse(false);
    this.multiCodes = productCodes.stream()
        .filter(t -> t.getCodeType() == ProductSkuCodeType.EXTRA_CODE).map(ProductSkuCode::getCode)
        .collect(Collectors.toList());
    this.multiCodesStr = StringUtil.join(StringPool.STR_SPLIT_CN, this.multiCodes);
  }

  @Data
  public static class PropertyBo extends BaseBo<ProductCategoryPropertyValueRelationDto> {

    /**
     * 属性ID
     */
    @Schema(description = "属性ID")
    private String id;

    /**
     * 属性名
     */
    @Schema(description = "属性名")
    private String name;

    /**
     * 字段类型
     */
    @Schema(description = "字段类型")
    private Integer columnType;

    /**
     * 属性值
     */
    @Schema(description = "属性值")
    private String text;

    /**
     * 属性文本
     */
    @Schema(description = "属性文本")
    private String textStr;

    public PropertyBo() {

    }

    public PropertyBo(ProductCategoryPropertyValueRelationDto dto) {

      super(dto);
    }

    @Override
    public BaseBo<ProductCategoryPropertyValueRelationDto> convert(ProductCategoryPropertyValueRelationDto dto) {

      return super.convert(dto, PropertyBo::getColumnType);
    }

    @Override
    protected void afterInit(ProductCategoryPropertyValueRelationDto dto) {

      this.id = dto.getPropertyId();
      this.name = dto.getPropertyName();
      this.text = dto.getPropertyColumnType() == ColumnType.CUSTOM ? dto.getPropertyText()
          : dto.getPropertyItemId();
      this.textStr = dto.getPropertyText();
      this.columnType = dto.getPropertyColumnType().getCode();
    }
  }
}
