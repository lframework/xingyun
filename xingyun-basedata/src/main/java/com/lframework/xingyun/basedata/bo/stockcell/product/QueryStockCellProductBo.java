package com.lframework.xingyun.basedata.bo.stockcell.product;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.basedata.dto.stockcell.product.StockCellProductDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class QueryStockCellProductBo extends BaseBo<StockCellProductDto> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 仓库ID
   */
  @Schema(description = "仓库ID")
  private String scId;

  /**
   * 仓库编号
   */
  @Schema(description = "仓库编号")
  private String scCode;

  /**
   * 仓库名称
   */
  @Schema(description = "仓库名称")
  private String scName;

  /**
   * 仓位ID
   */
  @Schema(description = "仓位ID")
  private String stockCellId;

  /**
   * 仓位编号
   */
  @Schema(description = "仓位编号")
  private String stockCellCode;

  /**
   * 仓位名称
   */
  @Schema(description = "仓位名称")
  private String stockCellName;

  /**
   * 仓位类型
   */
  @Schema(description = "仓位类型")
  private Integer stockCellType;

  /**
   * 商品ID
   */
  @Schema(description = "商品ID")
  private String productId;

  /**
   * 商品编号
   */
  @Schema(description = "商品编号")
  private String productCode;

  /**
   * 商品名称
   */
  @Schema(description = "商品名称")
  private String productName;

  /**
   * 商品规格
   */
  @Schema(description = "商品规格")
  private String spec;

  /**
   * 单位
   */
  @Schema(description = "单位")
  private String unit;

  /**
   * 分类ID
   */
  @Schema(description = "分类ID")
  private String categoryId;

  /**
   * 分类编号
   */
  @Schema(description = "分类编号")
  private String categoryCode;

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
   * 品牌编号
   */
  @Schema(description = "品牌编号")
  private String brandCode;

  /**
   * 品牌名称
   */
  @Schema(description = "品牌名称")
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
