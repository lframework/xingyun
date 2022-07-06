package com.lframework.xingyun.api.bo.retail.returned;

import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.DateUtil;
import com.lframework.common.utils.NumberUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.bo.BasePrintDataBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.product.info.RetailProductDto;
import com.lframework.xingyun.basedata.entity.Member;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.member.IMemberService;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.basedata.service.supplier.ISupplierService;
import com.lframework.xingyun.sc.dto.retail.returned.RetailReturnFullDto;
import com.lframework.xingyun.sc.entity.RetailOutSheet;
import com.lframework.xingyun.sc.enums.RetailReturnStatus;
import com.lframework.xingyun.sc.service.retail.IRetailOutSheetService;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PrintRetailReturnBo extends BasePrintDataBo<RetailReturnFullDto> {

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
    private String memberCode;

    /**
     * 客户名称
     */
    @ApiModelProperty("客户名称")
    private String memberName;

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

    public PrintRetailReturnBo(RetailReturnFullDto dto) {

        super(dto);
    }

    @Override
    public BaseBo<RetailReturnFullDto> convert(RetailReturnFullDto dto) {

        return super.convert(dto, PrintRetailReturnBo::getDetails);
    }

    @Override
    protected void afterInit(RetailReturnFullDto dto) {

        this.salerName = StringPool.EMPTY_STR;
        this.paymentDate = StringPool.EMPTY_STR;
        this.outSheetCode = StringPool.EMPTY_STR;
        this.description = StringPool.EMPTY_STR;
        this.approveBy = StringPool.EMPTY_STR;
        this.approveTime = StringPool.EMPTY_STR;

        IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
        StoreCenter sc = storeCenterService.findById(dto.getScId());
        this.scCode = sc.getCode();
        this.scName = sc.getName();

        if (!StringUtil.isBlank(dto.getMemberId())) {
            IMemberService memberService = ApplicationUtil.getBean(IMemberService.class);
            Member member = memberService.findById(dto.getMemberId());
            this.memberCode = member.getCode();
            this.memberName = member.getName();
        }

        IUserService userService = ApplicationUtil.getBean(IUserService.class);
        if (!StringUtil.isBlank(dto.getSalerId())) {
            this.salerName = userService.findById(dto.getSalerId()).getName();
        }

        IRetailOutSheetService retailOutSheetService = ApplicationUtil.getBean(
            IRetailOutSheetService.class);
        if (!StringUtil.isBlank(dto.getOutSheetId())) {
            RetailOutSheet outSheet = retailOutSheetService.getById(dto.getOutSheetId());
            this.outSheetCode = outSheet.getCode();
        }

        if (dto.getPaymentDate() != null) {
            this.paymentDate = DateUtil.formatDate(dto.getPaymentDate());
        }

        this.createBy = userService.findById(dto.getCreateBy()).getName();
        this.createTime = DateUtil.formatDateTime(dto.getCreateTime());

        if (!StringUtil.isBlank(dto.getApproveBy()) && dto.getStatus() == RetailReturnStatus.APPROVE_PASS) {
            this.approveBy = userService.findById(dto.getApproveBy()).getName();
            this.approveTime = DateUtil.formatDateTime(dto.getApproveTime());
        }

        if (!CollectionUtil.isEmpty(dto.getDetails())) {
            this.details = dto.getDetails().stream().map(ReturnDetailBo::new).collect(Collectors.toList());
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class ReturnDetailBo extends BaseBo<RetailReturnFullDto.ReturnDetailDto> {

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

        public ReturnDetailBo(RetailReturnFullDto.ReturnDetailDto dto) {

            super(dto);
        }

        @Override
        public BaseBo<RetailReturnFullDto.ReturnDetailDto> convert(RetailReturnFullDto.ReturnDetailDto dto) {

            return super.convert(dto);
        }

        @Override
        protected void afterInit(RetailReturnFullDto.ReturnDetailDto dto) {

            this.returnNum = dto.getReturnNum();
            this.taxPrice = dto.getTaxPrice();
            this.returnAmount = NumberUtil.mul(dto.getTaxPrice(), dto.getReturnNum());

            ISupplierService supplierService = ApplicationUtil.getBean(ISupplierService.class);
            this.supplierName = supplierService.findById(dto.getSupplierId()).getName();

            IProductService productService = ApplicationUtil.getBean(IProductService.class);
            RetailProductDto product = productService.getRetailById(dto.getProductId());

            this.productCode = product.getCode();
            this.productName = product.getName();
            this.skuCode = product.getSkuCode();
            this.externalCode = product.getExternalCode();
        }
    }
}
