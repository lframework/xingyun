package com.lframework.xingyun.sc.vo.stock.take.sheet;

import com.lframework.starter.web.core.vo.PageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QueryTakeStockSheetProductVo extends PageVo {

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

  /**
   * 盘点任务ID
   */
  @Schema(description = "盘点任务ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "盘点任务ID不能为空！")
  private String planId;
}
