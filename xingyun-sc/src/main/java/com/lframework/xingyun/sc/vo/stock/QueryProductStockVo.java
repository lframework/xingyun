package com.lframework.xingyun.sc.vo.stock;

import com.lframework.starter.web.core.vo.SortPageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class QueryProductStockVo extends SortPageVo {

  /**
   * 仓库ID
   */
  @Schema(description = "仓库ID")
  private String scId;

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
