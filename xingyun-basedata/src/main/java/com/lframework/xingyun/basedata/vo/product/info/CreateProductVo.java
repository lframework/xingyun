package com.lframework.xingyun.basedata.vo.product.info;

import com.lframework.starter.web.core.components.validation.IsCode;
import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.xingyun.basedata.enums.ProductType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

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
  @ApiModelProperty(value = "商品SKU编号")
  private String skuCode;

  /**
   * 简码
   */
  @ApiModelProperty("简码")
  private String externalCode;

  /**
   * 分类ID
   */
  @ApiModelProperty("分类ID")
  @NotBlank(message = "分类ID不能为空！")
  private String categoryId;

  /**
   * 品牌ID
   */
  @ApiModelProperty("品牌ID")
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
  @ApiModelProperty(value = "进项税率（%）")
  @Min(value = 0, message = "进项税率（%）不允许小于0！")
  private BigDecimal taxRate;

  /**
   * 销项税率（%）
   */
  @ApiModelProperty(value = "销项税率（%）")
  @Min(value = 0, message = "销项税率（%）不允许小于0！")
  private BigDecimal saleTaxRate;

  /**
   * 商品类型
   */
  @ApiModelProperty(value = "商品类型", required = true)
  @NotNull(message = "商品类型不能为空！")
  @IsEnum(message = "商品类型格式错误！", enumClass = ProductType.class)
  private Integer productType;

  /**
   * 重量（kg）
   */
  @ApiModelProperty(value = "重量（kg）")
  @Digits(integer = 10, fraction = 2, message = "重量最多允许2位小数！")
  private BigDecimal weight;

  /**
   * 体积（cm3）
   */
  @ApiModelProperty(value = "体积（cm3）")
  @Digits(integer = 10, fraction = 2, message = "体积最多允许2位小数！")
  private BigDecimal volume;

  /**
   * 单品
   */
  @ApiModelProperty(value = "单品")
  @Valid
  private List<ProductBundleVo> productBundles;

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
