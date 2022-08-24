package com.lframework.xingyun.basedata.api.bo.product.poly;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.biz.service.product.IProductBrandService;
import com.lframework.xingyun.basedata.biz.service.product.IProductCategoryService;
import com.lframework.xingyun.basedata.facade.entity.ProductBrand;
import com.lframework.xingyun.basedata.facade.entity.ProductCategory;
import com.lframework.xingyun.basedata.facade.entity.ProductPoly;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商品SPU QueryBo
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryProductPolyBo extends BaseBo<ProductPoly> {

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
   * 是否多规格
   */
  @ApiModelProperty("是否多规格")
  private Boolean multiSaleProp;

  /**
   * 进项税率（%）
   */
  @ApiModelProperty("进项税率（%）")
  private BigDecimal taxRate;

  /**
   * 销项税率
   */
  @ApiModelProperty("销项税率")
  private BigDecimal saleTaxRate;

  public QueryProductPolyBo() {

  }

  public QueryProductPolyBo(ProductPoly dto) {

    super(dto);
  }

  @Override
  protected void afterInit(ProductPoly dto) {

    IProductCategoryService productCategoryService = ApplicationUtil.getBean(
        IProductCategoryService.class);
    ProductCategory category = productCategoryService.findById(dto.getCategoryId());
    this.categoryName = category.getName();

    IProductBrandService productBrandService = ApplicationUtil.getBean(IProductBrandService.class);
    ProductBrand brand = productBrandService.findById(dto.getBrandId());
    this.brandName = brand.getName();
  }
}
