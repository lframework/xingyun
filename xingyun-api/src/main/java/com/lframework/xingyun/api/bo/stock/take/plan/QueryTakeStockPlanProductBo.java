package com.lframework.xingyun.api.bo.stock.take.plan;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.sc.dto.stock.take.config.TakeStockConfigDto;
import com.lframework.xingyun.sc.dto.stock.take.plan.QueryTakeStockPlanProductDto;
import com.lframework.xingyun.sc.service.stock.take.ITakeStockConfigService;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
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

    IProductService productService = ApplicationUtil.getBean(IProductService.class);
    ProductDto product = productService.getById(dto.getProductId());

    this.productCode = product.getCode();
    this.productName = product.getName();
    this.brandName = product.getPoly().getBrandName();
    this.categoryName = product.getPoly().getCategoryName();
    this.skuCode = product.getSkuCode();
    this.externalCode = product.getExternalCode();
    this.spec = product.getSpec();
    this.unit = product.getUnit();

    ITakeStockConfigService takeStockConfigService = ApplicationUtil
        .getBean(ITakeStockConfigService.class);
    TakeStockConfigDto config = takeStockConfigService.get();
    if (!config.getShowStock()) {
      this.stockNum = null;
    }
  }
}
