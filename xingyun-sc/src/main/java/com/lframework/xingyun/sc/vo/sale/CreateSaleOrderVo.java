package com.lframework.xingyun.sc.vo.sale;

import com.lframework.common.exceptions.impl.InputErrorException;
import com.lframework.common.utils.NumberUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.vo.BaseVo;
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
  @NotBlank(message = "仓库ID不能为空！")
  private String scId;

  /**
   * 客户ID
   */
  @NotBlank(message = "客户ID不能为空！")
  private String customerId;

  /**
   * 销售员ID
   */
  private String salerId;

  /**
   * 商品信息
   */
  @Valid
  @NotEmpty(message = "商品不能为空！")
  private List<SaleProductVo> products;

  /**
   * 备注
   */
  private String description;

  @Override
  public void validate() {

    int orderNo = 1;
    for (SaleProductVo product : this.products) {

      if (StringUtil.isBlank(product.getProductId())) {
        throw new InputErrorException("第" + orderNo + "行商品不能为空！");
      }

      if (product.getDiscountRate() == null) {
        throw new InputErrorException("第" + orderNo + "行商品折扣不能为空！");
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
        BigDecimal diffPrice = NumberUtil.sub(NumberUtil.getNumber(
                NumberUtil.mul(product.getOriPrice(), NumberUtil.div(product.getDiscountRate(), 100D)),
                2),
            product.getTaxPrice());
        if (!NumberUtil.le(diffPrice.abs(), 0.01D)) {
          throw new InputErrorException("第" + orderNo + "行商品折扣率不正确！");
        }
      } else {
        //如果原价为0，折扣率固定为100
        product.setDiscountRate(BigDecimal.valueOf(100));
      }

      orderNo++;
    }
  }
}
