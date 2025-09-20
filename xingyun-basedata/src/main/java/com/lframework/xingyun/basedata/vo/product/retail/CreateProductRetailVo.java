package com.lframework.xingyun.basedata.vo.product.retail;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateProductRetailVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 商品ID
   */
  @ApiModelProperty("商品ID")
  private String id;

  /**
   * 零售价
   */
  @ApiModelProperty(value = "零售价", required = true)
  @NotNull(message = "请输入零售价！")
  @Positive(message = "零售价必须大于0！")
  @Digits(integer = 20, fraction = 6, message = "零售价最多允许6位小数！")
  private BigDecimal price;
}
