package com.lframework.xingyun.basedata.vo.product.info;

import com.lframework.starter.web.core.components.validation.IsCode;
import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.xingyun.basedata.enums.ProductType;
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
public class CreateProductVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED)
  @IsCode
  @NotBlank(message = "请输入编号！")
  private String code;

  /**
   * 扩展编号
   */
  @Schema(description = "扩展编号")
  private List<String> multiCodes;

  /**
   * 名称
   */
  @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "请输入名称！")
  private String name;

  /**
   * 简称
   */
  @Schema(description = "简称")
  private String shortName;

  /**
   * 分类ID
   */
  @Schema(description = "分类ID")
  @NotBlank(message = "分类ID不能为空！")
  private String categoryId;

  /**
   * 品牌ID
   */
  @Schema(description = "品牌ID")
  private String brandId;

  /**
   * 规格
   */
  @Schema(description = "规格")
  private String spec;

  /**
   * 单位
   */
  @Schema(description = "单位")
  private String unit;

  /**
   * 进项税率（%）
   */
  @Schema(description = "进项税率（%）")
  @Min(value = 0, message = "进项税率（%）不允许小于0！")
  private BigDecimal taxRate;

  /**
   * 销项税率（%）
   */
  @Schema(description = "销项税率（%）")
  @Min(value = 0, message = "销项税率（%）不允许小于0！")
  private BigDecimal saleTaxRate;

  /**
   * 商品类型
   */
  @Schema(description = "商品类型", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "商品类型不能为空！")
  @IsEnum(message = "商品类型格式错误！", enumClass = ProductType.class)
  private Integer productType;

  /**
   * 重量（kg）
   */
  @Schema(description = "重量（kg）")
  @Digits(integer = 10, fraction = 2, message = "重量最多允许2位小数！")
  private BigDecimal weight;

  /**
   * 体积（cm3）
   */
  @Schema(description = "体积（cm3）")
  @Digits(integer = 10, fraction = 2, message = "体积最多允许2位小数！")
  private BigDecimal volume;

  /**
   * 单品
   */
  @Schema(description = "单品")
  @Valid
  private List<ProductBundleVo> productBundles;

  /**
   * 分类属性
   */
  @Schema(description = "分类属性")
  @Valid
  private List<ProductPropertyRelationVo> properties;

  /**
   * 采购价
   */
  @Schema(description = "采购价")
  private BigDecimal purchasePrice;

  /**
   * 销售价
   */
  @Schema(description = "销售价")
  private BigDecimal salePrice;

  /**
   * 零售价
   */
  @Schema(description = "零售价")
  private BigDecimal retailPrice;


}
