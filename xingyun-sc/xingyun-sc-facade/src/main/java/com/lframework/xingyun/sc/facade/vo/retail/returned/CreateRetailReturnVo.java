package com.lframework.xingyun.sc.facade.vo.retail.returned;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.exceptions.impl.InputErrorException;
import com.lframework.common.utils.NumberUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.vo.BaseVo;
import com.lframework.xingyun.sc.facade.RetailConfigFeignClient;
import com.lframework.xingyun.sc.facade.RetailOutSheetFeignClient;
import com.lframework.xingyun.sc.facade.dto.purchase.receive.GetPaymentDateDto;
import com.lframework.xingyun.sc.facade.entity.RetailConfig;
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
public class CreateRetailReturnVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 仓库ID
   */
  @ApiModelProperty(value = "仓库ID", required = true)
  @NotBlank(message = "仓库ID不能为空！")
  private String scId;

  /**
   * 会员ID
   */
  @ApiModelProperty("会员ID")
  private String memberId;

  /**
   * 采购员ID
   */
  @ApiModelProperty("采购员ID")
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
   * 收货单ID
   */
  @ApiModelProperty("收货单ID")
  private String outSheetId;

  /**
   * 商品信息
   */
  @ApiModelProperty(value = "商品信息", required = true)
  @Valid
  @NotEmpty(message = "商品不能为空！")
  private List<RetailReturnProductVo> products;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 是否关联零售出库单
   */
  @ApiModelProperty("是否关联零售出库单")
  private Boolean required;

  @Override
  public void validate() {

    RetailConfigFeignClient retailConfigFeignClient = ApplicationUtil.getBean(
        RetailConfigFeignClient.class);
    RetailConfig retailConfig = retailConfigFeignClient.get().getData();

    if (retailConfig.getRetailReturnRequireMember()) {
      if (StringUtil.isBlank(this.memberId)) {
        throw new DefaultClientException("请选择会员！");
      }
    }

    if (!retailConfig.getRetailReturnRequireOutStock().equals(this.required)) {
      throw new DefaultClientException("系统参数发生改变，请刷新页面后重试！");
    }

    this.validate(retailConfig.getRetailReturnRequireOutStock());
  }

  protected void validate(boolean requireOut) {

    RetailOutSheetFeignClient retailOutSheetFeignClient = ApplicationUtil.getBean(
        RetailOutSheetFeignClient.class);
    GetPaymentDateDto paymentDate = retailOutSheetFeignClient.getPaymentDate(this.getMemberId())
        .getData();
    if (paymentDate.getAllowModify()) {
      if (this.getPaymentDate() == null) {
        throw new InputErrorException("付款日期不能为空！");
      }
    }

    if (requireOut) {
      if (StringUtil.isBlank(this.getOutSheetId())) {
        throw new InputErrorException("零售出库单不能为空！");
      }
    }

    int orderNo = 1;
    for (RetailReturnProductVo product : this.products) {

      if (StringUtil.isBlank(product.getProductId())) {
        throw new InputErrorException("第" + orderNo + "行商品不能为空！");
      }

      if (product.getReturnNum() == null) {
        throw new InputErrorException("第" + orderNo + "行商品退货数量不能为空！");
      }

      if (product.getReturnNum() <= 0) {
        throw new InputErrorException("第" + orderNo + "行商品退货数量必须大于0！");
      }

      if (!requireOut) {
        if (StringUtil.isBlank(product.getSupplierId())) {
          throw new InputErrorException("第" + orderNo + "行商品供应商不能为空！");
        }

        if (product.getDiscountRate() == null) {
          throw new InputErrorException("第" + orderNo + "行商品折扣不能为空！");
        }

        if (product.getOriPrice() == null) {
          throw new InputErrorException("第" + orderNo + "行商品参考零售价不能为空！");
        }

        if (product.getTaxPrice() == null) {
          throw new InputErrorException("第" + orderNo + "行商品价格不能为空！");
        }

        if (product.getTaxPrice().doubleValue() < 0D) {
          throw new InputErrorException("第" + orderNo + "行商品价格不允许小于0！");
        }

        if (!NumberUtil.equal(product.getOriPrice(), 0D)) {
          BigDecimal diffPrice = NumberUtil.sub(NumberUtil.getNumber(
                  NumberUtil.mul(product.getOriPrice(),
                      NumberUtil.div(product.getDiscountRate(), 100D)), 2),
              product.getTaxPrice());
          if (!NumberUtil.le(diffPrice.abs(), 0.01D)) {
            throw new InputErrorException("第" + orderNo + "行商品折扣率不正确！");
          }
        } else {
          //如果原价为0，折扣率固定为100
          product.setDiscountRate(BigDecimal.valueOf(100));
        }
      }

      orderNo++;
    }

    if (requireOut) {
      if (this.products.stream().allMatch(t -> StringUtil.isBlank(t.getOutSheetDetailId()))) {
        throw new InputErrorException("零售出库单中的商品必须全部或部分退货！");
      }
    }
  }
}
