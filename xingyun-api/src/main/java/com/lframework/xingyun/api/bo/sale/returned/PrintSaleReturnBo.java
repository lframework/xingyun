package com.lframework.xingyun.api.bo.sale.returned;

import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.DateUtil;
import com.lframework.common.utils.NumberUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.bo.BasePrintDataBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.product.info.SaleProductDto;
import com.lframework.xingyun.basedata.entity.Customer;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.customer.ICustomerService;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.basedata.service.supplier.ISupplierService;
import com.lframework.xingyun.sc.dto.sale.returned.SaleReturnFullDto;
import com.lframework.xingyun.sc.entity.SaleOutSheet;
import com.lframework.xingyun.sc.enums.SaleReturnStatus;
import com.lframework.xingyun.sc.service.sale.ISaleOutSheetService;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PrintSaleReturnBo extends BasePrintDataBo<SaleReturnFullDto> {

    /**
     * 单号
     */
    @ApiModelProperty("单号")
    private String code;

    /**
     * 仓库编号
     */
    @ApiModelProperty("仓库编号")
    private String scCode;

    /**
     * 仓库名称
     */
    @ApiModelProperty("仓库名称")
    private String scName;

    /**
     * 客户编号
     */
    @ApiModelProperty("客户编号")
    private String customerCode;

    /**
     * 客户名称
     */
    @ApiModelProperty("客户名称")
    private String customerName;

    /**
     * 销售员姓名
     */
    @ApiModelProperty("销售员姓名")
    private String salerName;

    /**
     * 付款日期
     */
    @ApiModelProperty("付款日期")
    private String paymentDate;

    /**
     * 销售出库单号
     */
    @ApiModelProperty("销售出库单号")
    private String outSheetCode;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String description;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private String createTime;

    /**
     * 审核人
     */
    @ApiModelProperty("审核人")
    private String approveBy;

    /**
     * 审核时间
     */
    @ApiModelProperty("审核时间")
    private String approveTime;

    /**
     * 订单明细
     */
    @ApiModelProperty("订单明细")
    private List<ReturnDetailBo> details;

    public PrintSaleReturnBo(SaleReturnFullDto dto) {

        super(dto);
    }

    @Override
    public BaseBo<SaleReturnFullDto> convert(SaleReturnFullDto dto) {

        return super.convert(dto, PrintSaleReturnBo::getDetails);
    }

    @Override
    protected void afterInit(SaleReturnFullDto dto) {

        this.salerName = StringPool.EMPTY_STR;
        this.paymentDate = StringPool.EMPTY_STR;
        this.outSheetCode = StringPool.EMPTY_STR;
        this.approveBy = StringPool.EMPTY_STR;
        this.approveTime = StringPool.EMPTY_STR;

        IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
        StoreCenter sc = storeCenterService.findById(dto.getScId());
        this.scCode = sc.getCode();
        this.scName = sc.getName();

        ICustomerService customerService = ApplicationUtil.getBean(ICustomerService.class);
        Customer customer = customerService.findById(dto.getCustomerId());
        this.customerCode = customer.getCode();
        this.customerName = customer.getName();

        IUserService userService = ApplicationUtil.getBean(IUserService.class);
        if (!StringUtil.isBlank(dto.getSalerId())) {
            this.salerName = userService.findById(dto.getSalerId()).getName();
        }

        ISaleOutSheetService saleOutSheetService = ApplicationUtil.getBean(ISaleOutSheetService.class);
        if (!StringUtil.isBlank(dto.getOutSheetId())) {
            SaleOutSheet outSheet = saleOutSheetService.getById(dto.getOutSheetId());
            this.outSheetCode = outSheet.getCode();
        }

        if (dto.getPaymentDate() != null) {
            this.paymentDate = DateUtil.formatDate(dto.getPaymentDate());
        }

        this.createBy = userService.findById(dto.getCreateBy()).getName();
        this.createTime = DateUtil.formatDateTime(dto.getCreateTime());

        if (!StringUtil.isBlank(dto.getApproveBy()) && dto.getStatus() == SaleReturnStatus.APPROVE_PASS) {
            this.approveBy = userService.findById(dto.getApproveBy()).getName();
            this.approveTime = DateUtil.formatDateTime(dto.getApproveTime());
        }

        if (!CollectionUtil.isEmpty(dto.getDetails())) {
            this.details = dto.getDetails().stream().map(ReturnDetailBo::new).collect(Collectors.toList());
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class ReturnDetailBo extends BaseBo<SaleReturnFullDto.ReturnDetailDto> {

        /**
         * 供应商名称
         */
        @ApiModelProperty("供应商名称")
        private String supplierName;

        /**
         * 商品编号
         */
        @ApiModelProperty("商品编号")
        private String productCode;

        /**
         * 商品名称
         */
        @ApiModelProperty("商品名称")
        private String productName;

        /**
         * SKU编号
         */
        @ApiModelProperty("SKU编号")
        private String skuCode;

        /**
         * 外部编号
         */
        @ApiModelProperty("外部编号")
        private String externalCode;

        /**
         * 退货数量
         */
        @ApiModelProperty("退货数量")
        private Integer returnNum;

        /**
         * 价格
         */
        @ApiModelProperty("价格")
        private BigDecimal taxPrice;

        /**
         * 退货金额
         */
        @ApiModelProperty("退货金额")
        private BigDecimal returnAmount;

        public ReturnDetailBo(SaleReturnFullDto.ReturnDetailDto dto) {

            super(dto);
        }

        @Override
        public BaseBo<SaleReturnFullDto.ReturnDetailDto> convert(SaleReturnFullDto.ReturnDetailDto dto) {

            return super.convert(dto);
        }

        @Override
        protected void afterInit(SaleReturnFullDto.ReturnDetailDto dto) {

            this.returnNum = dto.getReturnNum();
            this.taxPrice = dto.getTaxPrice();
            this.returnAmount = NumberUtil.mul(dto.getTaxPrice(), dto.getReturnNum());

            ISupplierService supplierService = ApplicationUtil.getBean(ISupplierService.class);
            this.supplierName = supplierService.findById(dto.getSupplierId()).getName();

            IProductService productService = ApplicationUtil.getBean(IProductService.class);
            SaleProductDto product = productService.getSaleById(dto.getProductId());

            this.productCode = product.getCode();
            this.productName = product.getName();
            this.skuCode = product.getSkuCode();
            this.externalCode = product.getExternalCode();
        }
    }
}
