package com.lframework.xingyun.sc.vo.sale.out;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.exceptions.impl.InputErrorException;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.xingyun.sc.dto.purchase.receive.GetPaymentDateDto;
import com.lframework.xingyun.sc.entity.SaleConfig;
import com.lframework.xingyun.sc.entity.SaleOrderDetail;
import com.lframework.xingyun.sc.service.sale.SaleConfigService;
import com.lframework.xingyun.sc.service.sale.SaleOrderDetailService;
import com.lframework.xingyun.sc.service.sale.SaleOutSheetService;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateSaleOutSheetVo implements BaseVo, Serializable {

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
   * 付款日期
   */
  @ApiModelProperty("付款日期")
  private LocalDate paymentDate;

  /**
   * 是否允许修改付款日期
   */
  @ApiModelProperty("是否允许修改付款日期")
  private Boolean allowModifyPaymentDate = Boolean.FALSE;

  /**
   * 商品信息
   */
  @ApiModelProperty(value = "商品信息", required = true)
  @Valid
  @NotEmpty(message = "商品不能为空！")
  private List<SaleOutProductVo> products;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 销售订单ID
   */
  @ApiModelProperty("销售订单ID")
  private String saleOrderId;

  /**
   * 是否关联销售订单
   */
  @ApiModelProperty("是否关联销售订单")
  private Boolean required;

  public void validate() {

    SaleConfigService saleConfigService = ApplicationUtil.getBean(SaleConfigService.class);
    SaleConfig saleConfig = saleConfigService.get();

    if (!saleConfig.getOutStockRequireSale().equals(this.required)) {
      throw new DefaultClientException("系统参数发生改变，请刷新页面后重试！");
    }

    this.validate(saleConfig.getOutStockRequireSale());
  }

  protected void validate(boolean requireSale) {

    SaleOutSheetService saleOutSheetService = ApplicationUtil.getBean(SaleOutSheetService.class);
    GetPaymentDateDto paymentDate = saleOutSheetService.getPaymentDate(this.getCustomerId());
    if (paymentDate.getAllowModify()) {
      if (this.getPaymentDate() == null) {
        throw new InputErrorException("付款日期不能为空！");
      }
    }

    if (requireSale) {
      if (StringUtil.isBlank(this.getSaleOrderId())) {
        throw new InputErrorException("销售订单不能为空！");
      }
    }

    SaleOrderDetailService saleOrderDetailService = ApplicationUtil.getBean(
        SaleOrderDetailService.class);

    int orderNo = 1;
    for (SaleOutProductVo product : this.products) {

      if (StringUtil.isBlank(product.getProductId())) {
        throw new InputErrorException("第" + orderNo + "行商品不能为空！");
      }

      if (product.getOrderNum() == null) {
        throw new InputErrorException("第" + orderNo + "行商品销售数量不能为空！");
      }

      if (product.getOrderNum() <= 0) {
        throw new InputErrorException("第" + orderNo + "行商品销售数量必须大于0！");
      }

      if (!requireSale) {

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
      } else {
        if (StringUtil.isNotBlank(product.getSaleOrderDetailId())) {
          SaleOrderDetail orderDetail = saleOrderDetailService.getById(
              product.getSaleOrderDetailId());
          product.setTaxPrice(orderDetail.getTaxPrice());
        } else {
          product.setTaxPrice(BigDecimal.ZERO);
        }
      }

      orderNo++;
    }
  }
}
