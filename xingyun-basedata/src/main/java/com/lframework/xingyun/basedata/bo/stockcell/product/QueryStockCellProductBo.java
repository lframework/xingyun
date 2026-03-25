package com.lframework.xingyun.basedata.bo.stockcell.product;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.basedata.dto.stockcell.product.StockCellProductDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryStockCellProductBo extends BaseBo<StockCellProductDto> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 仓库ID
   */
  @ApiModelProperty("仓库ID")
  private String scId;

  /**
   * 仓库编号
   */
  @ApiModelProperty("仓库编号")
  private String scCode;

  /**
   * 仓库名称
   */
  @ApiModelProperty("仓库名称")
  private String scName;

  /**
   * 仓位ID
   */
  @ApiModelProperty("仓位ID")
  private String stockCellId;

  /**
   * 仓位编号
   */
  @ApiModelProperty("仓位编号")
  private String stockCellCode;

  /**
   * 仓位名称
   */
  @ApiModelProperty("仓位名称")
  private String stockCellName;

  /**
   * 仓位类型
   */
  @ApiModelProperty("仓位类型")
  private Integer stockCellType;

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
   * 商品规格
   */
  @ApiModelProperty("商品规格")
  private String spec;

  /**
   * 单位
   */
  @ApiModelProperty("单位")
  private String unit;

  /**
   * 分类ID
   */
  @ApiModelProperty("分类ID")
  private String categoryId;

  /**
   * 分类编号
   */
  @ApiModelProperty("分类编号")
  private String categoryCode;

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
   * 品牌编号
   */
  @ApiModelProperty("品牌编号")
  private String brandCode;

  /**
   * 品牌名称
   */
  @ApiModelProperty("品牌名称")
  private String brandName;

  public QueryStockCellProductBo() {

  }

  public QueryStockCellProductBo(StockCellProductDto dto) {

    super(dto);
  }

  @Override
  protected void afterInit(StockCellProductDto dto) {
  }
}
