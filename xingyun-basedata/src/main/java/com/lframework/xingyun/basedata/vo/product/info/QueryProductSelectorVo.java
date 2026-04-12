package com.lframework.xingyun.basedata.vo.product.info;

import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.starter.web.core.vo.PageVo;
import com.lframework.xingyun.basedata.enums.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryProductSelectorVo extends PageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  @Schema(description = "编号")
  private String code;

  /**
   * 名称
   */
  @Schema(description = "名称")
  private String name;

  /**
   * 简称
   */
  @Schema(description = "简称")
  private String shortName;

  /**
   * 品牌ID
   */
  @Schema(description = "品牌ID")
  private String brandId;

  /**
   * 分类ID
   */
  @Schema(description = "分类ID")
  private String categoryId;

  /**
   * 创建起始时间
   */
  @Schema(description = "创建起始时间")
  private LocalDateTime startTime;

  /**
   * 创建截止时间
   */
  @Schema(description = "创建截止时间")
  private LocalDateTime endTime;

  /**
   * 商品类型
   */
  @Schema(description = "商品类型")
  @IsEnum(message = "商品类型格式错误！", enumClass = ProductType.class)
  private Integer productType;
}
