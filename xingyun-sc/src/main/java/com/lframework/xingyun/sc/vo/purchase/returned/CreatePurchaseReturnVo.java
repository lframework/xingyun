package com.lframework.xingyun.sc.vo.purchase.returned;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.exceptions.impl.InputErrorException;
import com.lframework.common.utils.NumberUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.vo.BaseVo;
import com.lframework.xingyun.sc.dto.purchase.config.PurchaseConfigDto;
import com.lframework.xingyun.sc.dto.purchase.receive.GetPaymentDateDto;
import com.lframework.xingyun.sc.service.purchase.IPurchaseConfigService;
import com.lframework.xingyun.sc.service.purchase.IReceiveSheetService;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreatePurchaseReturnVo implements BaseVo, Serializable {

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
   * 付款日期
   */
  @ApiModelProperty("付款日期")
  private LocalDate paymentDate;

  /**
   * 收货单ID
   */
  @ApiModelProperty("收货单ID")
  private String receiveSheetId;

  /**
   * 商品信息
   */
  @ApiModelProperty(value = "商品信息", required = true)
  @Valid
  @NotEmpty(message = "商品不能为空！")
  private List<ReturnProductVo> products;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 是否关联采购收货单
   */
  @ApiModelProperty("是否关联采购收货单")
  private Boolean required;

  @Override
  public void validate() {

    IPurchaseConfigService purchaseConfigService = ApplicationUtil
        .getBean(IPurchaseConfigService.class);
    PurchaseConfigDto purchaseConfig = purchaseConfigService.get();

    if (!purchaseConfig.getPurchaseReturnRequireReceive().equals(this.required)) {
      throw new DefaultClientException("系统参数发生改变，请刷新页面后重试！");
    }

    this.validate(purchaseConfig.getPurchaseReturnRequireReceive());
  }

  protected void validate(boolean requireReceive) {

    IReceiveSheetService receiveSheetService = ApplicationUtil.getBean(IReceiveSheetService.class);
    GetPaymentDateDto paymentDate = receiveSheetService.getPaymentDate(this.supplierId);
    if (paymentDate.getAllowModify()) {
      if (this.getPaymentDate() == null) {
        throw new InputErrorException("付款日期不能为空！");
      }
    }

    if (requireReceive) {
      if (StringUtil.isBlank(this.getReceiveSheetId())) {
        throw new InputErrorException("采购收货单不能为空！");
      }
    }

    int orderNo = 1;
    for (ReturnProductVo product : this.products) {

      if (StringUtil.isBlank(product.getProductId())) {
        throw new InputErrorException("第" + orderNo + "行商品不能为空！");
      }

      if (product.getReturnNum() == null) {
        throw new InputErrorException("第" + orderNo + "行商品退货数量不能为空！");
      }

      if (product.getReturnNum() <= 0) {
        throw new InputErrorException("第" + orderNo + "行商品退货数量必须大于0！");
      }

      if (!requireReceive) {
        if (product.getPurchasePrice() == null) {
          throw new InputErrorException("第" + orderNo + "行商品采购价不能为空！");
        }

        if (product.getPurchasePrice().doubleValue() < 0D) {
          throw new InputErrorException("第" + orderNo + "行商品采购价不允许小于0！");
        }

        if (!NumberUtil.isNumberPrecision(product.getPurchasePrice(), 2)) {
          throw new InputErrorException("第" + orderNo + "行商品采购价最多允许2位小数！");
        }
      }

      orderNo++;
    }

    if (requireReceive) {
      if (this.products.stream().allMatch(t -> StringUtil.isBlank(t.getReceiveSheetDetailId()))) {
        throw new InputErrorException("采购收货单中的商品必须全部或部分退货！");
      }
    }
  }
}
