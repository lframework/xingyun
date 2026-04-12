package com.lframework.xingyun.sc.vo.stock.take.pre;

import com.lframework.starter.web.core.vo.PageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class QueryPreTakeStockProductVo extends PageVo {

  private static final long serialVersionUID = 1L;

  /**
   * 检索关键字
   */
  @Schema(description = "检索关键字")
  private String condition;

  /**
   * 分类ID
   */
  @Schema(description = "分类ID")
  private String categoryId;

  /**
   * 品牌ID
   */
  @Schema(description = "品牌ID")
  private String brandId;
}
