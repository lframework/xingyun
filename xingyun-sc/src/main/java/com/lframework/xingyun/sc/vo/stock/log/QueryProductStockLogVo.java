package com.lframework.xingyun.sc.vo.stock.log;

import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.SortPageVo;
import com.lframework.xingyun.sc.enums.ProductStockBizType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryProductStockLogVo extends SortPageVo {

  private static final long serialVersionUID = 1L;

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
   * 创建起始时间
   */
  @Schema(description = "创建起始时间")
  private LocalDateTime createStartTime;

  /**
   * 创建截止时间
   */
  @Schema(description = "创建截止时间")
  private LocalDateTime createEndTime;

  /**
   * 业务类型
   */
  @Schema(description = "业务类型")
  @IsEnum(message = "业务类型不存在！", enumClass = ProductStockBizType.class)
  private Integer bizType;
}
