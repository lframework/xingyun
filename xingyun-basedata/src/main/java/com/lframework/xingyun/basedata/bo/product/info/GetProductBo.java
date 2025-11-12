package com.lframework.xingyun.basedata.bo.product.info;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.annotations.convert.EnumConvert;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.product.ProductPropertyRelationDto;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.ProductBrand;
import com.lframework.xingyun.basedata.entity.ProductBundle;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.entity.ProductPurchase;
import com.lframework.xingyun.basedata.entity.ProductRetail;
import com.lframework.xingyun.basedata.entity.ProductSale;
import com.lframework.xingyun.basedata.enums.ColumnType;
import com.lframework.xingyun.basedata.enums.ProductType;
import com.lframework.xingyun.basedata.service.product.ProductBrandService;
import com.lframework.xingyun.basedata.service.product.ProductBundleService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryService;
import com.lframework.xingyun.basedata.service.product.ProductPropertyRelationService;
import com.lframework.xingyun.basedata.service.product.ProductPurchaseService;
import com.lframework.xingyun.basedata.service.product.ProductRetailService;
import com.lframework.xingyun.basedata.service.product.ProductSaleService;
import io.swagger.annotations.ApiModelProperty;
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
  @ApiModelProperty("ID")
  private String id;

  /**
   * 编号
   */
  @ApiModelProperty("编号")
  private String code;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 简称
   */
  @ApiModelProperty("简称")
  private String shortName;

  /**
   * SKU
   */
  @ApiModelProperty("SKU")
  private String skuCode;

  /**
   * 简码
   */
  @ApiModelProperty("简码")
  private String externalCode;

  /**
   * 分类ID
   */
  @ApiModelProperty("分类ID")
  private String categoryId;

  /**
   * 分类名称
   */
  @ApiModelProperty("分类名称")
  private String categoryName;

  /**
   * 品牌ID
   */
  @ApiModelProperty("品牌ID")
  private String brandId;

  /**
   * 品牌名称
   */
  @ApiModelProperty("品牌名称")
  private String brandName;

  /**
   * 重量（kg）
   */
  @ApiModelProperty("重量（kg）")
  private BigDecimal weight;

  /**
   * 体积（cm3）
   */
  @ApiModelProperty("体积（cm3）")
  private BigDecimal volume;

  /**
   * 进项税率（%）
   */
  @ApiModelProperty("进项税率（%）")
  private BigDecimal taxRate;

  /**
   * 销项税率（%）
   */
  @ApiModelProperty("销项税率（%）")
  private BigDecimal saleTaxRate;

  /**
   * 规格
   */
  @ApiModelProperty("规格")
  private String spec;

  /**
   * 单位
   */
  @ApiModelProperty("单位")
  private String unit;

  /**
   * 商品类型
   */
  @ApiModelProperty("商品类型")
  @EnumConvert
  private Integer productType;

  /**
   * 单品
   */
  @ApiModelProperty("单品")
  private List<ProductBundleBo> productBundles;

  /**
   * 采购价
   */
  @ApiModelProperty("采购价")
  private BigDecimal purchasePrice;

  /**
   * 销售价
   */
  @ApiModelProperty("销售价")
  private BigDecimal salePrice;

  /**
   * 零售价
   */
  @ApiModelProperty("零售价")
  private BigDecimal retailPrice;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  /**
   * 属性
   */
  @ApiModelProperty("属性")
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
      List<ProductBundle> bundles = productBundleService.getByMainProductId(dto.getId());
      this.productBundles = bundles.stream().map(ProductBundleBo::new).collect(Collectors.toList());
    }

    ProductPurchaseService productPurchaseService = ApplicationUtil.getBean(
        ProductPurchaseService.class);
    ProductPurchase productPurchase = productPurchaseService.getById(dto.getId());
    this.purchasePrice = productPurchase.getPrice();

    ProductSaleService productSaleService = ApplicationUtil.getBean(ProductSaleService.class);
    ProductSale productSale = productSaleService.getById(dto.getId());
    this.salePrice = productSale.getPrice();

    ProductRetailService productRetailService = ApplicationUtil.getBean(
        ProductRetailService.class);
    ProductRetail productRetail = productRetailService.getById(dto.getId());
    this.retailPrice = productRetail.getPrice();

    ProductPropertyRelationService productPropertyRelationService = ApplicationUtil.getBean(
        ProductPropertyRelationService.class);
    List<ProductPropertyRelationDto> propertyRelationDtos = productPropertyRelationService.getByProductId(
        dto.getId());
    if (!CollectionUtil.isEmpty(propertyRelationDtos)) {
      this.properties = new ArrayList<>();
      for (ProductPropertyRelationDto property : propertyRelationDtos) {
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
  }

  @Data
  public static class PropertyBo extends BaseBo<ProductPropertyRelationDto> {

    /**
     * 属性ID
     */
    @ApiModelProperty("属性ID")
    private String id;

    /**
     * 属性名
     */
    @ApiModelProperty("属性名")
    private String name;

    /**
     * 字段类型
     */
    @ApiModelProperty("字段类型")
    private Integer columnType;

    /**
     * 属性值
     */
    @ApiModelProperty("属性值")
    private String text;

    /**
     * 属性文本
     */
    @ApiModelProperty("属性文本")
    private String textStr;

    public PropertyBo() {

    }

    public PropertyBo(ProductPropertyRelationDto dto) {

      super(dto);
    }

    @Override
    public BaseBo<ProductPropertyRelationDto> convert(ProductPropertyRelationDto dto) {

      return super.convert(dto, PropertyBo::getColumnType);
    }

    @Override
    protected void afterInit(ProductPropertyRelationDto dto) {

      this.id = dto.getPropertyId();
      this.name = dto.getPropertyName();
      this.text = dto.getPropertyColumnType() == ColumnType.CUSTOM ? dto.getPropertyText()
          : dto.getPropertyItemId();
      this.textStr = dto.getPropertyText();
      this.columnType = dto.getPropertyColumnType().getCode();
    }
  }
}
