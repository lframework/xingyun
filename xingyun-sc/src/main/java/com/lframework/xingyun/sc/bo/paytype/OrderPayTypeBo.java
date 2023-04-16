package com.lframework.xingyun.sc.bo.paytype;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.sc.entity.OrderPayType;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class OrderPayTypeBo extends BaseBo<OrderPayType> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 订单ID
   */
  @ApiModelProperty("订单ID")
  private String orderId;

  /**
   * 支付方式ID
   */
  @ApiModelProperty("支付方式ID")
  private String payTypeId;

  /**
   * 支付金额
   */
  @ApiModelProperty("支付金额")
  private BigDecimal payAmount;

  /**
   * 支付内容
   */
  @ApiModelProperty("支付内容")
  private String text;

  public OrderPayTypeBo() {
  }

  public OrderPayTypeBo(OrderPayType dto) {
    super(dto);
  }
}
