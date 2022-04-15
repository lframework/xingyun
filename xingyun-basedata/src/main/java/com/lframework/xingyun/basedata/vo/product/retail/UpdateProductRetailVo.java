package com.lframework.xingyun.basedata.vo.product.retail;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateProductRetailVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 商品ID
   */
  @ApiModelProperty(value = "商品ID", required = true)
  @NotBlank(message = "商品ID不能为空！")
  private String id;

  /**
   * 零售价
   */
  @ApiModelProperty(value = "零售价", required = true)
  @NotNull(message = "请输入零售价！")
  @Positive(message = "零售价必须大于0！")
  @Digits(integer = 20, fraction = 2, message = "零售价最多允许2位小数！")
  private BigDecimal price;
}
