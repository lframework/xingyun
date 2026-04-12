package com.lframework.xingyun.basedata.vo.product.poly;

import com.lframework.starter.web.core.components.validation.IsCode;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateProductPolyVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "id不能为空！")
  private String id;

  /**
   * 商品货号
   */
  @Schema(description = "商品货号", requiredMode = Schema.RequiredMode.REQUIRED)
  @IsCode
  @NotBlank(message = "商品货号不能为空！")
  private String code;

  /**
   * 商品名称
   */
  @Schema(description = "商品名称", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "商品名称不能为空！")
  private String name;

  /**
   * 商品简称
   */
  @Schema(description = "商品简称")
  private String shortName;

  /**
   * 分类ID
   */
  @Schema(description = "分类ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "请选择商品分类！")
  private String categoryId;

  /**
   * 品牌ID
   */
  @Schema(description = "品牌ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "请选择商品品牌！")
  private String brandId;

  /**
   * 进项税率（%）
   */
  @Schema(description = "进项税率（%）", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "进项税率（%）不能为空！")
  @Min(value = 0, message = "进项税率（%）不允许小于0！")
  @Digits(integer = 10, fraction = 0, message = "进项税率（%）必须为整数！")
  private BigDecimal taxRate;

  /**
   * 销项税率（%）
   */
  @Schema(description = "销项税率（%）", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "销项税率（%）不能为空！")
  @Min(value = 0, message = "销项税率（%）不允许小于0！")
  @Digits(integer = 10, fraction = 0, message = "销项税率（%）必须为整数！")
  private BigDecimal saleTaxRate;

  /**
   * 商品属性
   */
  @Schema(description = "商品属性", requiredMode = Schema.RequiredMode.REQUIRED)
  @Valid
  private List<PropertyVo> properties;

  @Data
  public static class PropertyVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 属性ID
     */
    @NotBlank(message = "属性值ID不能为空！")
    private String id;

    /**
     * 属性值内容
     */
    private String text;
  }
}
