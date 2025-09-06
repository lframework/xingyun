package com.lframework.xingyun.sc.vo.purchase;

import com.lframework.starter.common.exceptions.impl.InputErrorException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.xingyun.sc.vo.paytype.OrderPayTypeVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePurchaseOrderVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 仓库ID
   */
  @ApiModelProperty(value = "仓库ID", required = true)
  @NotBlank(message = "仓库ID不能为空！")
  private String scId;

  /**
   * 供应商ID
   */
  @ApiModelProperty(value = "供应商ID", required = true)
  @NotBlank(message = "供应商ID不能为空！")
  private String supplierId;

  /**
   * 采购员ID
   */
  @ApiModelProperty("采购员ID")
  private String purchaserId;

  /**
   * 预计到货日期
   */
  @ApiModelProperty(value = "预计到货日期", required = true)
  @NotNull(message = "预计到货日期不能为空！")
  private LocalDate expectArriveDate;

  /**
   * 商品信息
   */
  @ApiModelProperty(value = "商品信息", required = true)
  @Valid
  @NotEmpty(message = "商品不能为空！")
  private List<PurchaseProductVo> products;

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
    for (PurchaseProductVo product : this.products) {

      if (StringUtil.isBlank(product.getProductId())) {
        throw new InputErrorException("第" + orderNo + "行商品不能为空！");
      }

      if (product.getPurchaseNum() == null) {
        throw new InputErrorException("第" + orderNo + "行商品采购数量不能为空！");
      }

      if (NumberUtil.le(product.getPurchaseNum(), BigDecimal.ZERO)) {
        throw new InputErrorException("第" + orderNo + "行商品采购数量必须大于0！");
      }

      if (!NumberUtil.isNumberPrecision(product.getPurchaseNum(), 8)) {
        throw new InputErrorException("第" + orderNo + "行商品采购数量最多允许8位小数！");
      }

      if (product.getPurchasePrice() == null) {
        throw new InputErrorException("第" + orderNo + "行商品采购价不能为空！");
      }

      if (NumberUtil.lt(product.getPurchasePrice(), BigDecimal.ZERO)) {
        throw new InputErrorException("第" + orderNo + "行商品采购价不允许小于0！");
      }

      if (!NumberUtil.isNumberPrecision(product.getPurchasePrice(), 6)) {
        throw new InputErrorException("第" + orderNo + "行商品采购价最多允许6位小数！");
      }

      orderNo++;
    }

    BigDecimal totalAmount = this.products.stream()
        .map(t -> NumberUtil.getNumber(NumberUtil.mul(t.getPurchaseNum(), t.getPurchasePrice()), 2))
        .reduce(NumberUtil::add)
        .orElse(BigDecimal.ZERO);
    BigDecimal payTypeAmount = CollectionUtil.isEmpty(this.payTypes) ? BigDecimal.ZERO
        : this.payTypes.stream().map(OrderPayTypeVo::getPayAmount).reduce(NumberUtil::add)
            .orElse(BigDecimal.ZERO);
    if (!NumberUtil.equal(totalAmount, payTypeAmount)) {
      throw new InputErrorException("所有支付方式的支付金额不等于含税总金额，请检查！");
    }
  }
}
