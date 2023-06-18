package com.lframework.xingyun.basedata.bo.product.info;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.annotations.convert.EnumConvert;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.ProductBrand;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.service.product.ProductBrandService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryService;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryProductBo extends BaseBo<Product> {

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
   * 名称
   */
  @ApiModelProperty("简称")
  private String shortName;

  /**
   * SKU
   */
  @ApiModelProperty("SKU")
  private String skuCode;

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

  /**
   * 创建时间
   */
  @ApiModelProperty("创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  /**
   * 修改时间
   */
  @ApiModelProperty("修改时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime updateTime;

  public QueryProductBo() {

  }

  public QueryProductBo(Product dto) {

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
