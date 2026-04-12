package com.lframework.xingyun.sc.vo.purchase.returned;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.exceptions.impl.InputErrorException;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.xingyun.sc.dto.purchase.receive.GetPaymentDateDto;
import com.lframework.xingyun.sc.entity.PurchaseConfig;
import com.lframework.xingyun.sc.entity.ReceiveSheetDetail;
import com.lframework.xingyun.sc.service.purchase.PurchaseConfigService;
import com.lframework.xingyun.sc.service.purchase.ReceiveSheetDetailService;
import com.lframework.xingyun.sc.service.purchase.ReceiveSheetService;
import com.lframework.xingyun.sc.vo.paytype.OrderPayTypeVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreatePurchaseReturnVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 仓库ID
   */
  @Schema(description = "仓库ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "仓库ID不能为空！")
  private String scId;

  /**
   * 供应商ID
   */
  @Schema(description = "供应商ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "供应商ID不能为空！")
  private String supplierId;

  /**
   * 采购员ID
   */
  @Schema(description = "采购员ID")
  private String purchaserId;

  /**
   * 付款日期
   */
  @Schema(description = "付款日期")
  private LocalDate paymentDate;

  /**
   * 收货单ID
   */
  @Schema(description = "收货单ID")
  private String receiveSheetId;

  /**
   * 商品信息
   */
  @Schema(description = "商品信息", requiredMode = Schema.RequiredMode.REQUIRED)
  @Valid
  @NotEmpty(message = "商品不能为空！")
  private List<ReturnProductVo> products;

  /**
   * 支付方式
   */
  @Schema(description = "支付方式")
  @Valid
  private List<OrderPayTypeVo> payTypes;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  /**
   * 是否关联采购收货单
   */
  @Schema(description = "是否关联采购收货单")
  private Boolean required;

  public void validate() {

    PurchaseConfigService purchaseConfigService = ApplicationUtil.getBean(
        PurchaseConfigService.class);
    PurchaseConfig purchaseConfig = purchaseConfigService.get();

    if (!purchaseConfig.getPurchaseReturnRequireReceive().equals(this.required)) {
      throw new DefaultClientException("系统参数发生改变，请刷新页面后重试！");
    }

    this.validate(purchaseConfig.getPurchaseReturnRequireReceive());
  }

  protected void validate(boolean requireReceive) {

    ReceiveSheetService receiveSheetService = ApplicationUtil.getBean(ReceiveSheetService.class);
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

    ReceiveSheetDetailService receiveSheetDetailService = ApplicationUtil.getBean(
        ReceiveSheetDetailService.class);

    int orderNo = 1;
    for (ReturnProductVo product : this.products) {

      if (StringUtil.isBlank(product.getProductId())) {
        throw new InputErrorException("第" + orderNo + "行商品不能为空！");
      }

      if (product.getReturnNum() == null) {
        throw new InputErrorException("第" + orderNo + "行商品退货数量不能为空！");
      }

      if (NumberUtil.le(product.getReturnNum(), 0)) {
        throw new InputErrorException("第" + orderNo + "行商品退货数量必须大于0！");
      }
      
      if (!NumberUtil.isNumberPrecision(product.getReturnNum(), 8)) {
        throw new InputErrorException("第" + orderNo + "行商品退货数量最多允许8位小数！");
      }

      if (!requireReceive) {
        if (product.getPurchasePrice() == null) {
          throw new InputErrorException("第" + orderNo + "行商品退货价不能为空！");
        }

        if (NumberUtil.lt(product.getPurchasePrice(), BigDecimal.ZERO)) {
          throw new InputErrorException("第" + orderNo + "行商品退货价不允许小于0！");
        }

        if (!NumberUtil.isNumberPrecision(product.getPurchasePrice(), 6)) {
          throw new InputErrorException("第" + orderNo + "行商品退货价最多允许6位小数！");
        }
      } else {
        if (StringUtil.isNotBlank(product.getReceiveSheetDetailId())) {
          ReceiveSheetDetail detail = receiveSheetDetailService.getById(
              product.getReceiveSheetDetailId());
          product.setPurchasePrice(detail.getTaxPrice());
        } else {
          product.setPurchasePrice(BigDecimal.ZERO);
        }
      }

      orderNo++;
    }
  }
}
