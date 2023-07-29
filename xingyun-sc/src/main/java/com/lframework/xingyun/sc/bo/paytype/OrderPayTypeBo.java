package com.lframework.xingyun.sc.bo.paytype;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.PayType;
import com.lframework.xingyun.basedata.service.paytype.PayTypeService;
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

  @ApiModelProperty("是否记录内容")
  private Boolean recText;

  public OrderPayTypeBo() {
  }

  public OrderPayTypeBo(OrderPayType dto) {
    super(dto);
  }

  @Override
  protected void afterInit(OrderPayType dto) {
    PayTypeService payTypeService = ApplicationUtil.getBean(PayTypeService.class);
    PayType payType = payTypeService.findById(dto.getPayTypeId());
    this.recText = payType.getRecText();
  }
}
