package com.lframework.xingyun.sc.bo.paytype;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.PayType;
import com.lframework.xingyun.basedata.service.paytype.PayTypeService;
import com.lframework.xingyun.sc.entity.OrderPayType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class OrderPayTypeBo extends BaseBo<OrderPayType> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 订单ID
   */
  @Schema(description = "订单ID")
  private String orderId;

  /**
   * 支付方式ID
   */
  @Schema(description = "支付方式ID")
  private String payTypeId;

  /**
   * 支付金额
   */
  @Schema(description = "支付金额")
  private BigDecimal payAmount;

  /**
   * 支付内容
   */
  @Schema(description = "支付内容")
  private String text;

  @Schema(description = "是否记录内容")
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
