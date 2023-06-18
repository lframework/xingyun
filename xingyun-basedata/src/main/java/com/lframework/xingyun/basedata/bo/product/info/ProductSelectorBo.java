package com.lframework.xingyun.basedata.bo.product.info;

import com.lframework.starter.web.annotations.convert.EnumConvert;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.ProductBrand;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.service.product.ProductBrandService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryService;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductSelectorBo extends BaseBo<Product> {

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
   * SKU
   */
  @ApiModelProperty("SKU")
  private String skuCode;

  /**
   * 外部编号
   */
  @ApiModelProperty("外部编号")
  private String externalCode;

  /**
   * 类目ID
   */
  @ApiModelProperty("类目ID")
  private String categoryId;

  /**
   * 类目名称
   */
  @ApiModelProperty("类目名称")
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
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  public ProductSelectorBo() {

  }

  public ProductSelectorBo(Product dto) {

    super(dto);
  }

  @Override
  protected void afterInit(Product dto) {
    ProductCategoryService productCategoryService = ApplicationUtil.getBean(
        ProductCategoryService.class);
    ProductCategory productCategory = productCategoryService.findById(dto.getCategoryId());
    this.categoryName = productCategory.getName();

    ProductBrandService productBrandService = ApplicationUtil.getBean(ProductBrandService.class);
    ProductBrand brand = productBrandService.findById(dto.getBrandId());
    this.brandName = brand.getName();
  }
}
