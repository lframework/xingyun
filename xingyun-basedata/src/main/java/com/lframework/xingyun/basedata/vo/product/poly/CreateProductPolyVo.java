package com.lframework.xingyun.basedata.vo.product.poly;

import com.lframework.starter.common.constants.PatternPool;
import com.lframework.starter.common.exceptions.impl.InputErrorException;
import com.lframework.starter.common.utils.RegUtil;
import com.lframework.starter.common.utils.StringUtil;
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
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateProductPolyVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

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
   * 是否多销售属性
   */
  @Schema(description = "是否多销售属性", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "请选择是否多销售属性！")
  private Boolean multipleSaleProp;

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
   * 分类属性
   */
  @Schema(description = "分类属性", requiredMode = Schema.RequiredMode.REQUIRED)
  @Valid
  private List<PropertyVo> properties;

  /**
   * 商品信息
   */
  @Schema(description = "商品信息", requiredMode = Schema.RequiredMode.REQUIRED)
  @Valid
  @NotEmpty
  private List<ProductVo> products;

  public void validate() {

    int orderNo = 1;
    for (ProductVo product : this.getProducts()) {
      if (this.getMultipleSaleProp()) {
        if (StringUtil.isBlank(product.getSalePropItemId1())) {
          throw new InputErrorException("销售属性1不能为空！");
        }
      }

      if (StringUtil.isBlank(product.getCode())) {
        throw new InputErrorException("第" + (orderNo) + "行商品编号不能为空！");
      }

      if (!RegUtil.isMatch(PatternPool.PATTERN_CODE, product.getCode())) {
        throw new InputErrorException("第" + (orderNo) + "行商品编号必须由字母或数字组成，长度不能超过20位！");
      }

      if (StringUtil.isBlank(product.getName())) {
        throw new InputErrorException("第" + (orderNo) + "行商品名称不能为空！");
      }

      if (product.getPurchasePrice() == null) {
        throw new InputErrorException("第" + (orderNo) + "行商品采购价不能为空！");
      }

      if (product.getSalePrice() == null) {
        throw new InputErrorException("第" + (orderNo) + "行商品销售价不能为空！");
      }

      if (product.getRetailPrice() == null) {
        throw new InputErrorException("第" + (orderNo) + "行商品零售价不能为空！");
      }

      orderNo++;
    }
  }

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

  @Data
  public static class ProductVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 销售属性1ID multipleSaleProp == true时生效
     */
    private String salePropItemId1;

    /**
     * 销售属性2ID multipleSaleProp == true时生效
     */
    private String salePropItemId2;

    /**
     * 商品编号
     */
    private String code;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 规格
     */
    private String spec;

    /**
     * 单位
     */
    private String unit;

    /**
     * 采购价
     */
    private BigDecimal purchasePrice;

    /**
     * 销售价
     */
    private BigDecimal salePrice;

    /**
     * 零售价
     */
    private BigDecimal retailPrice;
  }
}
