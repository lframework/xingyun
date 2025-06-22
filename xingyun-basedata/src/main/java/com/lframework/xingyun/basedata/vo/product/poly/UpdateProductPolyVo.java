package com.lframework.xingyun.basedata.vo.product.poly;

import com.lframework.starter.web.core.components.validation.IsCode;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateProductPolyVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotBlank(message = "id不能为空！")
  private String id;

  /**
   * 商品货号
   */
  @ApiModelProperty(value = "商品货号", required = true)
  @IsCode
  @NotBlank(message = "商品货号不能为空！")
  private String code;

  /**
   * 商品名称
   */
  @ApiModelProperty(value = "商品名称", required = true)
  @NotBlank(message = "商品名称不能为空！")
  private String name;

  /**
   * 商品简称
   */
  @ApiModelProperty("商品简称")
  private String shortName;

  /**
   * 分类ID
   */
  @ApiModelProperty(value = "分类ID", required = true)
  @NotBlank(message = "请选择商品分类！")
  private String categoryId;

  /**
   * 品牌ID
   */
  @ApiModelProperty(value = "品牌ID", required = true)
  @NotBlank(message = "请选择商品品牌！")
  private String brandId;

  /**
   * 进项税率（%）
   */
  @ApiModelProperty(value = "进项税率（%）", required = true)
  @NotNull(message = "进项税率（%）不能为空！")
  @Min(value = 0, message = "进项税率（%）不允许小于0！")
  @Digits(integer = 10, fraction = 0, message = "进项税率（%）必须为整数！")
  private BigDecimal taxRate;

  /**
   * 销项税率（%）
   */
  @ApiModelProperty(value = "销项税率（%）", required = true)
  @NotNull(message = "销项税率（%）不能为空！")
  @Min(value = 0, message = "销项税率（%）不允许小于0！")
  @Digits(integer = 10, fraction = 0, message = "销项税率（%）必须为整数！")
  private BigDecimal saleTaxRate;

  /**
   * 商品属性
   */
  @ApiModelProperty(value = "商品属性", required = true)
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
