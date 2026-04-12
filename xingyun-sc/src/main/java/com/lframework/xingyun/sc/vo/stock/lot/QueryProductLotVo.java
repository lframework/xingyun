package com.lframework.xingyun.sc.vo.stock.lot;

import com.lframework.starter.web.core.vo.PageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryProductLotVo extends PageVo {

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

  /**
   * 供应商ID
   */
  @Schema(description = "供应商ID")
  private String supplierId;

  /**
   * 生成起始时间
   */
  @Schema(description = "生成起始时间")
  private LocalDateTime createStartTime;

  /**
   * 生成截止时间
   */
  @Schema(description = "生成截止时间")
  private LocalDateTime createEndTime;
}
