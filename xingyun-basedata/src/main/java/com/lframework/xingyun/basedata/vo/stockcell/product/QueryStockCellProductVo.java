package com.lframework.xingyun.basedata.vo.stockcell.product;

import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.starter.web.core.vo.SortPageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;

@Data
public class QueryStockCellProductVo extends SortPageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

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
   * 仓位类别
   */
  @Schema(description = "仓位类别")
  private Integer stockCellType;

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
   * 商品分类ID
   */
  @Schema(description = "商品分类ID")
  private String categoryId;

  /**
   * 商品品牌ID
   */
  @Schema(description = "商品品牌ID")
  private String brandId;
}
