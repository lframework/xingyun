package com.lframework.xingyun.api.bo.settle.sheet;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.DateUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Supplier;
import com.lframework.xingyun.basedata.service.supplier.ISupplierService;
import com.lframework.xingyun.settle.dto.sheet.SettleBizItemDto;
import com.lframework.xingyun.settle.dto.sheet.SettleSheetFullDto;
import com.lframework.xingyun.settle.service.ISettleSheetService;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetSettleSheetBo extends BaseBo<SettleSheetFullDto> {

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
     * 供应商ID
     */
    @ApiModelProperty("供应商ID")
    private String supplierId;

    /**
     * 供应商编号
     */
    @ApiModelProperty("供应商编号")
    private String supplierCode;

    /**
     * 供应商名称
     */
    @ApiModelProperty("供应商名称")
    private String supplierName;

    /**
     * 总金额
     */
    @ApiModelProperty("总金额")
    private BigDecimal totalAmount;

    /**
     * 优惠金额
     */
    @ApiModelProperty("优惠金额")
    private BigDecimal totalDiscountAmount;

    /**
     * 起始时间
     */
    @ApiModelProperty("起始时间")
    @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
    private LocalDateTime startTime;

    /**
     * 截止时间
     */
    @ApiModelProperty("截止时间")
    @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
    private LocalDateTime endTime;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String description;

    /**
     * 创建人ID
     */
    @ApiModelProperty("创建人ID")
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

    private List<SheetDetailBo> details;

    public GetSettleSheetBo() {

    }

    public GetSettleSheetBo(SettleSheetFullDto dto) {

        super(dto);
    }

    @Override
    public BaseBo<SettleSheetFullDto> convert(SettleSheetFullDto dto) {

        return super.convert(dto, GetSettleSheetBo::getStatus, GetSettleSheetBo::getDetails);
    }

    @Override
    protected void afterInit(SettleSheetFullDto dto) {

        ISupplierService supplierService = ApplicationUtil.getBean(ISupplierService.class);
        Supplier supplier = supplierService.findById(dto.getSupplierId());
        this.supplierCode = supplier.getCode();
        this.supplierName = supplier.getName();

        this.status = dto.getStatus().getCode();

        IUserService userService = ApplicationUtil.getBean(IUserService.class);
        UserDto createBy = userService.findById(dto.getCreateBy());
        this.createBy = createBy.getName();

        if (!StringUtil.isBlank(dto.getApproveBy())) {
            this.approveBy = userService.findById(dto.getApproveBy()).getName();
        }

        this.startTime = DateUtil.toLocalDateTime(dto.getStartDate());
        this.endTime = DateUtil.toLocalDateTimeMax(dto.getEndDate());

        if (!CollectionUtil.isEmpty(dto.getDetails())) {
            this.details = dto.getDetails().stream().map(SheetDetailBo::new).collect(Collectors.toList());
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class SheetDetailBo extends BaseBo<SettleSheetFullDto.SheetDetailDto> {

        /**
         * 明细ID
         */
        @ApiModelProperty("明细ID")
        private String id;

        /**
         * 单据ID
         */
        @ApiModelProperty("单据ID")
        private String bizId;

        /**
         * 单据号
         */
        @ApiModelProperty("单据号")
        private String bizCode;

        /**
         * 审核时间
         */
        @ApiModelProperty("审核时间")
        @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
        private LocalDateTime approveTime;

        /**
         * 应付金额
         */
        @ApiModelProperty("应付金额")
        private BigDecimal totalPayAmount;

        /**
         * 已付金额
         */
        @ApiModelProperty("已付金额")
        private BigDecimal totalPayedAmount;

        /**
         * 已优惠金额
         */
        @ApiModelProperty("已优惠金额")
        private BigDecimal totalDiscountAmount;

        /**
         * 未付金额
         */
        @ApiModelProperty("未付金额")
        private BigDecimal totalUnPayAmount;

        /**
         * 实付金额
         */
        @ApiModelProperty("实付金额")
        private BigDecimal payAmount;

        /**
         * 优惠金额
         */
        @ApiModelProperty("优惠金额")
        private BigDecimal discountAmount;

        /**
         * 单据备注
         */
        @ApiModelProperty("单据备注")
        private String description;

        public SheetDetailBo() {

        }

        public SheetDetailBo(SettleSheetFullDto.SheetDetailDto dto) {

            super(dto);
        }

        @Override
        public <A> BaseBo<SettleSheetFullDto.SheetDetailDto> convert(SettleSheetFullDto.SheetDetailDto dto) {

            return super.convert(dto);
        }

        @Override
        protected void afterInit(SettleSheetFullDto.SheetDetailDto dto) {

            ISettleSheetService settleSheetService = ApplicationUtil.getBean(ISettleSheetService.class);
            SettleBizItemDto item = settleSheetService.getBizItem(dto.getBizId());
            this.bizCode = item.getCode();
            this.approveTime = item.getApproveTime();
            this.totalPayAmount = item.getTotalPayAmount();
            this.totalPayedAmount = item.getTotalPayedAmount();
            this.totalDiscountAmount = item.getTotalDiscountAmount();
            this.totalUnPayAmount = item.getTotalUnPayAmount();
        }
    }
}
