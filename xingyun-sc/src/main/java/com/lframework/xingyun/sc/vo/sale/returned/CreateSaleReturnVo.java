package com.lframework.xingyun.sc.vo.sale.returned;

import com.lframework.common.exceptions.impl.InputErrorException;
import com.lframework.common.utils.NumberUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.vo.BaseVo;
import com.lframework.xingyun.sc.dto.purchase.receive.GetPaymentDateDto;
import com.lframework.xingyun.sc.dto.sale.config.SaleConfigDto;
import com.lframework.xingyun.sc.service.sale.ISaleConfigService;
import com.lframework.xingyun.sc.service.sale.ISaleOutSheetService;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class CreateSaleReturnVo implements BaseVo, Serializable {

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
     * 采购员ID
     */
    private String salerId;

    /**
     * 付款日期
     */
    private LocalDate paymentDate;

    /**
     * 收货单ID
     */
    private String outSheetId;

    /**
     * 商品信息
     */
    @Valid
    @NotEmpty(message = "商品不能为空！")
    private List<SaleReturnProductVo> products;

    /**
     * 备注
     */
    private String description;

    @Override
    public void validate() {

        ISaleConfigService saleConfigService = ApplicationUtil.getBean(ISaleConfigService.class);
        SaleConfigDto saleConfig = saleConfigService.get();

        this.validate(saleConfig.getSaleReturnRequireOutStock());
    }

    protected void validate(boolean requireOut) {

        ISaleOutSheetService saleOutSheetService = ApplicationUtil.getBean(ISaleOutSheetService.class);
        GetPaymentDateDto paymentDate = saleOutSheetService.getPaymentDate(this.getCustomerId());
        if (paymentDate.getAllowModify()) {
            if (this.getPaymentDate() == null) {
                throw new InputErrorException("付款日期不能为空！");
            }
        }

        if (requireOut) {
            if (StringUtil.isBlank(this.getOutSheetId())) {
                throw new InputErrorException("销售出库单不能为空！");
            }
        }

        int orderNo = 1;
        for (SaleReturnProductVo product : this.products) {

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
                            NumberUtil.mul(product.getOriPrice(), NumberUtil.div(product.getDiscountRate(), 100D)), 2),
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
                throw new InputErrorException("销售出库单中的商品必须全部或部分退货！");
            }
        }
    }
}
