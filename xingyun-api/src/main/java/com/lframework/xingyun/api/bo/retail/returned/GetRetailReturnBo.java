package com.lframework.xingyun.api.bo.retail.returned;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.NumberUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.product.info.RetailProductDto;
import com.lframework.xingyun.basedata.service.member.IMemberService;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.basedata.service.supplier.ISupplierService;
import com.lframework.xingyun.sc.dto.retail.out.RetailOutSheetDetailLotDto;
import com.lframework.xingyun.sc.dto.retail.returned.RetailReturnFullDto;
import com.lframework.xingyun.sc.entity.RetailOutSheet;
import com.lframework.xingyun.sc.service.retail.IRetailOutSheetDetailLotService;
import com.lframework.xingyun.sc.service.retail.IRetailOutSheetService;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetRetailReturnBo extends BaseBo<RetailReturnFullDto> {

    /**
     * ID
     */
    @ApiModelProperty("ID")
    private String id;

    /**
     * 单号
     */
    @ApiModelProperty("单号")
    private String code;

    /**
     * 仓库ID
     */
    @ApiModelProperty("仓库ID")
    private String scId;

    /**
     * 仓库名称
     */
    @ApiModelProperty("仓库名称")
    private String scName;

    /**
     * 客户ID
     */
    @ApiModelProperty("客户ID")
    private String memberId;

    /**
     * 客户名称
     */
    @ApiModelProperty("客户名称")
    private String memberName;

    /**
     * 销售员ID
     */
    @ApiModelProperty("销售员ID")
    private String salerId;

    /**
     * 销售员姓名
     */
    @ApiModelProperty("销售员姓名")
    private String salerName;

    /**
     * 付款日期
     */
    @ApiModelProperty("付款日期")
    @JsonFormat(pattern = StringPool.DATE_PATTERN)
    private LocalDate paymentDate;

    /**
     * 销售出库单ID
     */
    @ApiModelProperty("销售出库单ID")
    private String outSheetId;

    /**
     * 销售出库单号
     */
    @ApiModelProperty("销售出库单号")
    private String outSheetCode;

    /**
     * 退货数量
     */
    @ApiModelProperty("退货数量")
    private Integer totalNum;

    /**
     * 赠品数量
     */
    @ApiModelProperty("赠品数量")
    private Integer giftNum;

    /**
     * 退货金额
     */
    @ApiModelProperty("退货金额")
    private BigDecimal totalAmount;

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
    @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

    /**
     * 审核人
     */
    @ApiModelProperty("审核人")
    private String approveBy;

    /**
     * 审核时间
     */
    @ApiModelProperty("审核时间")
    @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
    private LocalDateTime approveTime;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private Integer status;

    /**
     * 拒绝原因
     */
    @ApiModelProperty("拒绝原因")
    private String refuseReason;

    /**
     * 结算状态
     */
    @ApiModelProperty("结算状态")
    private Integer settleStatus;

    /**
     * 订单明细
     */
    @ApiModelProperty("订单明细")
    private List<ReturnDetailBo> details;

    public GetRetailReturnBo(RetailReturnFullDto dto) {

        super(dto);
    }

    @Override
    public BaseBo<RetailReturnFullDto> convert(RetailReturnFullDto dto) {

        return super.convert(dto, GetRetailReturnBo::getStatus, GetRetailReturnBo::getSettleStatus,
                GetRetailReturnBo::getDetails);
    }

    @Override
    protected void afterInit(RetailReturnFullDto dto) {

        IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
        this.scName = storeCenterService.findById(dto.getScId()).getName();

        if (!StringUtil.isBlank(dto.getMemberId())) {
            IMemberService memberService = ApplicationUtil.getBean(IMemberService.class);
            this.memberName = memberService.findById(dto.getMemberId()).getName();
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

        if (!StringUtil.isBlank(dto.getApproveBy())) {
            this.approveBy = userService.findById(dto.getApproveBy()).getName();
        }

        this.status = dto.getStatus().getCode();
        this.settleStatus = dto.getSettleStatus().getCode();

        this.totalNum = dto.getTotalNum();
        this.giftNum = dto.getTotalGiftNum();
        this.totalAmount = dto.getTotalAmount();

        if (!CollectionUtil.isEmpty(dto.getDetails())) {
            this.details = dto.getDetails().stream().map(t -> new ReturnDetailBo(this.getScId(), t))
                    .collect(Collectors.toList());
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class ReturnDetailBo extends BaseBo<RetailReturnFullDto.ReturnDetailDto> {

        /**
         * 明细ID
         */
        @ApiModelProperty("明细ID")
        private String id;

        /**
         * 商品ID
         */
        @ApiModelProperty("商品ID")
        private String productId;

        /**
         * 供应商ID
         */
        @ApiModelProperty("供应商ID")
        private String supplierId;

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
         * 单位
         */
        @ApiModelProperty("单位")
        private String unit;

        /**
         * 规格
         */
        @ApiModelProperty("规格")
        private String spec;

        /**
         * 类目名称
         */
        @ApiModelProperty("类目名称")
        private String categoryName;

        /**
         * 品牌名称
         */
        @ApiModelProperty("品牌名称")
        private String brandName;

        /**
         * 销售属性1
         */
        @ApiModelProperty("销售属性1")
        private String salePropItemName1;

        /**
         * 销售属性2
         */
        @ApiModelProperty("销售属性2")
        private String salePropItemName2;

        /**
         * 出库数量
         */
        @ApiModelProperty("出库数量")
        private Integer outNum;

        /**
         * 剩余退货数量
         */
        @ApiModelProperty("剩余退货数量")
        private Integer remainNum;

        /**
         * 退货数量
         */
        @ApiModelProperty("退货数量")
        private Integer returnNum;

        /**
         * 原价
         */
        @ApiModelProperty("原价")
        private BigDecimal retailPrice;

        /**
         * 价格
         */
        @ApiModelProperty("价格")
        private BigDecimal taxPrice;

        /**
         * 折扣
         */
        @ApiModelProperty("折扣")
        private BigDecimal discountRate;

        /**
         * 是否赠品
         */
        @ApiModelProperty("是否赠品")
        private Boolean isGift;

        /**
         * 税率
         */
        @ApiModelProperty("税率")
        private BigDecimal taxRate;

        /**
         * 备注
         */
        @ApiModelProperty("备注")
        private String description;

        /**
         * 销售出库单明细ID
         */
        @ApiModelProperty("销售出库单明细ID")
        private String outSheetDetailId;

        /**
         * 仓库ID
         */
        @ApiModelProperty(value = "仓库ID", hidden = true)
        @JsonIgnore
        private String scId;

        public ReturnDetailBo(String scId, RetailReturnFullDto.ReturnDetailDto dto) {

            this.scId = scId;
            if (dto != null) {
                this.convert(dto);

                this.afterInit(dto);
            }
        }

        @Override
        public BaseBo<RetailReturnFullDto.ReturnDetailDto> convert(RetailReturnFullDto.ReturnDetailDto dto) {

            return super.convert(dto);
        }

        @Override
        protected void afterInit(RetailReturnFullDto.ReturnDetailDto dto) {

            this.returnNum = dto.getReturnNum();
            this.retailPrice = dto.getTaxPrice();
            this.taxPrice = dto.getTaxPrice();
            this.discountRate = dto.getDiscountRate();

            this.supplierId = dto.getSupplierId();
            ISupplierService supplierService = ApplicationUtil.getBean(ISupplierService.class);
            this.supplierName = supplierService.findById(dto.getSupplierId()).getName();

            IProductService productService = ApplicationUtil.getBean(IProductService.class);
            RetailProductDto product = productService.getRetailById(dto.getProductId());

            this.productCode = product.getCode();
            this.productName = product.getName();
            this.skuCode = product.getSkuCode();
            this.externalCode = product.getExternalCode();
            this.unit = product.getUnit();
            this.spec = product.getSpec();
            this.categoryName = product.getCategoryName();
            this.brandName = product.getBrandName();
            if (product.getSaleProps() != null) {
                this.salePropItemName1 = product.getSaleProps().getItemName1();
                this.salePropItemName2 = product.getSaleProps().getItemName2();
            }

            if (!StringUtil.isBlank(dto.getOutSheetDetailId())) {
                IRetailOutSheetDetailLotService receiveSheetDetailService = ApplicationUtil.getBean(
                    IRetailOutSheetDetailLotService.class);
                RetailOutSheetDetailLotDto outSheetDetail = receiveSheetDetailService.findById(
                    dto.getOutSheetDetailId());
                this.outNum = outSheetDetail.getOrderNum();
                this.remainNum = NumberUtil.sub(outSheetDetail.getOrderNum(),
                    outSheetDetail.getReturnNum()).intValue();
            }
        }
    }
}
