package com.lframework.xingyun.basedata.vo.product.info;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.*;

import lombok.Data;

@Data
public class ProductBundleVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 商品ID
   */
  @ApiModelProperty(value = "商品ID", required = true)
  @NotBlank(message = "商品ID不能为空！")
  private String productId;

  /**
   * 包含数量
   */
  @ApiModelProperty(value = "包含数量", required = true)
  @NotNull(message = "包含数量不能为空！")
  @Min(value = 1, message = "包含数量必须大于0！")
  private Integer bundleNum;

  /**
   * 采购价
   */
  @ApiModelProperty(value = "采购价", required = true)
  @NotNull(message = "采购价不能为空！")
  @Digits(integer = 10, fraction = 6, message = "采购价最多允许6位小数！")
  @Positive(message = "采购价必须大于0！")
  private BigDecimal purchasePrice;

  /**
   * 销售价
   */
  @ApiModelProperty(value = "销售价", required = true)
  @NotNull(message = "销售价不能为空！")
  @Digits(integer = 10, fraction = 6, message = "销售价最多允许6位小数！")
  @Positive(message = "销售价必须大于0！")
  private BigDecimal salePrice;

  /**
   * 零售价
   */
  @ApiModelProperty(value = "零售价", required = true)
  @NotNull(message = "零售价不能为空！")
  @Digits(integer = 10, fraction = 6, message = "零售价最多允许6位小数！")
  @Positive(message = "零售价必须大于0！")
  private BigDecimal retailPrice;
}
