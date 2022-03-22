package com.lframework.xingyun.basedata.vo.product.sale;

import com.lframework.starter.web.vo.BaseVo;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateProductSaleVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 商品ID
   */
  @NotBlank(message = "商品ID不能为空！")
  private String id;

  /**
   * 销售价
   */
  @NotNull(message = "请输入销售价！")
  @Positive(message = "销售价必须大于0！")
  @Digits(integer = 20, fraction = 2, message = "销售价最多允许2位小数！")
  private BigDecimal price;
}
