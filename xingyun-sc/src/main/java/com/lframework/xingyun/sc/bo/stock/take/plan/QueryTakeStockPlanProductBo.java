package com.lframework.xingyun.sc.bo.stock.take.plan;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.ProductBrand;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.service.product.ProductBrandService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryService;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.sc.dto.stock.take.plan.QueryTakeStockPlanProductDto;
import com.lframework.xingyun.sc.entity.TakeStockConfig;
import com.lframework.xingyun.sc.service.stock.take.TakeStockConfigService;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryTakeStockPlanProductBo extends BaseBo<QueryTakeStockPlanProductDto> {

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
   * 分类名称
   */
  @ApiModelProperty("分类名称")
  private String categoryName;

  /**
   * 品牌名称
   */
  @ApiModelProperty("品牌名称")
  private String brandName;

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
   * 初始库存
   */
  @ApiModelProperty("初始库存")
  private Integer stockNum;

  public QueryTakeStockPlanProductBo(QueryTakeStockPlanProductDto dto) {

    super(dto);
  }

  @Override
  protected void afterInit(QueryTakeStockPlanProductDto dto) {

    ProductService productService = ApplicationUtil.getBean(ProductService.class);
    Product product = productService.findById(dto.getProductId());

    ProductCategoryService productCategoryService = ApplicationUtil.getBean(
        ProductCategoryService.class);
    ProductCategory productCategory = productCategoryService.findById(product.getCategoryId());

    ProductBrandService productBrandService = ApplicationUtil.getBean(ProductBrandService.class);
    ProductBrand productBrand = productBrandService.findById(product.getBrandId());

    this.productCode = product.getCode();
    this.productName = product.getName();
    this.categoryName = productCategory.getName();
    this.brandName = productBrand.getName();
    this.skuCode = product.getSkuCode();
    this.externalCode = product.getExternalCode();
    this.spec = product.getSpec();
    this.unit = product.getUnit();

    TakeStockConfigService takeStockConfigService = ApplicationUtil.getBean(
        TakeStockConfigService.class);
    TakeStockConfig config = takeStockConfigService.get();
    if (!config.getShowStock()) {
      this.stockNum = null;
    }
  }
}
