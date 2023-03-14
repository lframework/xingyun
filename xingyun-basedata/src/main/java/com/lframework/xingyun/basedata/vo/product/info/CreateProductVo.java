package com.lframework.xingyun.basedata.vo.product.info;

import com.lframework.starter.web.components.validation.IsCode;
import com.lframework.starter.web.vo.BaseVo;
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
public class CreateProductVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  @ApiModelProperty(value = "编号", required = true)
  @IsCode
  @NotBlank(message = "请输入编号！")
  private String code;

  /**
   * 名称
   */
  @ApiModelProperty(value = "名称", required = true)
  @NotBlank(message = "请输入名称！")
  private String name;

  /**
   * 简称
   */
  @ApiModelProperty(value = "简称")
  private String shortName;

  /**
   * 商品SKU编号
   */
  @ApiModelProperty(value = "商品SKU编号", required = true)
  @NotBlank(message = "商品SKU编号不能为空！")
  private String skuCode;

  /**
   * 外部编号
   */
  @ApiModelProperty("外部编号")
  private String externalCode;

  /**
   * 类目ID
   */
  @ApiModelProperty("类目ID")
  @NotBlank(message = "类目ID不能为空！")
  private String categoryId;

  /**
   * 品牌ID
   */
  @ApiModelProperty("品牌ID")
  @NotBlank(message = "品牌ID不能为空！")
  private String brandId;

  /**
   * 规格
   */
  @ApiModelProperty("规格")
  private String spec;

  /**
   * 单位
   */
  @ApiModelProperty("单位")
  private String unit;

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
  @ApiModelProperty(value = "商品属性")
  @Valid
  private List<ProductPropertyRelationVo> properties;

  /**
   * 采购价
   */
  @ApiModelProperty("采购价")
  private BigDecimal purchasePrice;

  /**
   * 销售价
   */
  @ApiModelProperty("销售价")
  private BigDecimal salePrice;

  /**
   * 零售价
   */
  @ApiModelProperty("零售价")
  private BigDecimal retailPrice;


}
