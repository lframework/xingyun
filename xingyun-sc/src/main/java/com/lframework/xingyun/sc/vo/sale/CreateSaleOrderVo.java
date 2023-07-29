package com.lframework.xingyun.sc.vo.sale;

import com.lframework.starter.common.exceptions.impl.InputErrorException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.vo.BaseVo;
import com.lframework.xingyun.sc.vo.paytype.OrderPayTypeVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateSaleOrderVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 仓库ID
   */
  @ApiModelProperty(value = "仓库ID", required = true)
  @NotBlank(message = "仓库ID不能为空！")
  private String scId;

  /**
   * 客户ID
   */
  @ApiModelProperty(value = "客户ID", required = true)
  @NotBlank(message = "客户ID不能为空！")
  private String customerId;

  /**
   * 销售员ID
   */
  @ApiModelProperty("销售员ID")
  private String salerId;

  /**
   * 商品信息
   */
  @ApiModelProperty(value = "商品信息", required = true)
  @Valid
  @NotEmpty(message = "商品不能为空！")
  private List<SaleProductVo> products;

  /**
   * 支付方式
   */
  @ApiModelProperty("约定支付")
  @Valid
  private List<OrderPayTypeVo> payTypes;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  public void validate() {

    int orderNo = 1;
    for (SaleProductVo product : this.products) {

      if (StringUtil.isBlank(product.getProductId())) {
        throw new InputErrorException("第" + orderNo + "行商品不能为空！");
      }

      if (product.getOrderNum() == null) {
        throw new InputErrorException("第" + orderNo + "行商品销售数量不能为空！");
      }

      if (product.getOrderNum() <= 0) {
        throw new InputErrorException("第" + orderNo + "行商品销售数量必须大于0！");
      }

      if (product.getOriPrice() == null) {
        throw new InputErrorException("第" + orderNo + "行商品参考销售价不能为空！");
      }

      if (product.getTaxPrice() == null) {
        throw new InputErrorException("第" + orderNo + "行商品价格不能为空！");
      }

      if (product.getTaxPrice().doubleValue() < 0D) {
        throw new InputErrorException("第" + orderNo + "行商品价格不允许小于0！");
      }

      if (!NumberUtil.equal(product.getOriPrice(), 0D)) {
        // 由 根据原价和折扣率校验现价 更改为 根据原价、现价计算折扣率，即：不以传入的折扣率为准
        BigDecimal discountRate = NumberUtil.getNumber(
            NumberUtil.mul(NumberUtil.div(product.getTaxPrice(), product.getOriPrice()), 100), 2);
        product.setDiscountRate(discountRate);
      } else {
        //如果原价为0，折扣率固定为100
        product.setDiscountRate(BigDecimal.valueOf(100));
      }

      orderNo++;
    }

    BigDecimal totalAmount = this.products.stream()
        .map(t -> NumberUtil.mul(t.getOrderNum(), t.getTaxPrice())).reduce(NumberUtil::add)
        .orElse(BigDecimal.ZERO);
    BigDecimal payTypeAmount = CollectionUtil.isEmpty(this.payTypes) ? BigDecimal.ZERO
        : this.payTypes.stream().map(OrderPayTypeVo::getPayAmount).reduce(NumberUtil::add)
            .orElse(BigDecimal.ZERO);
    if (!NumberUtil.equal(totalAmount, payTypeAmount)) {
      throw new InputErrorException("所有支付方式的支付金额不等于含税总金额，请检查！");
    }
  }
}
