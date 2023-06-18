package com.lframework.xingyun.settle.bo.sheet;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.mybatis.service.UserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Supplier;
import com.lframework.xingyun.basedata.service.supplier.SupplierService;
import com.lframework.xingyun.settle.entity.SettleSheet;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QuerySettleSheetBo extends BaseBo<SettleSheet> {

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

    public QuerySettleSheetBo() {

    }

    public QuerySettleSheetBo(SettleSheet dto) {

        super(dto);
    }

    @Override
    public BaseBo<SettleSheet> convert(SettleSheet dto) {

        return super.convert(dto, QuerySettleSheetBo::getStatus);
    }

    @Override
    protected void afterInit(SettleSheet dto) {

        SupplierService supplierService = ApplicationUtil.getBean(SupplierService.class);
        Supplier supplier = supplierService.findById(dto.getSupplierId());
        this.supplierCode = supplier.getCode();
        this.supplierName = supplier.getName();

        this.status = dto.getStatus().getCode();

        UserService userService = ApplicationUtil.getBean(UserService.class);

        if (!StringUtil.isBlank(dto.getApproveBy())) {
            this.approveBy = userService.findById(dto.getApproveBy()).getName();
        }

        this.startTime = DateUtil.toLocalDateTime(dto.getStartDate());
        this.endTime = DateUtil.toLocalDateTimeMax(dto.getEndDate());
    }
}
