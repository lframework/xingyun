package com.lframework.xingyun.settle.bo.pre;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.mybatis.service.UserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Supplier;
import com.lframework.xingyun.basedata.service.supplier.SupplierService;
import com.lframework.xingyun.settle.entity.SettlePreSheet;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QuerySettlePreSheetBo extends BaseBo<SettlePreSheet> {

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
     * 结算状态
     */
    @ApiModelProperty("结算状态")
    private Integer settleStatus;

    public QuerySettlePreSheetBo() {

    }

    public QuerySettlePreSheetBo(SettlePreSheet dto) {

        super(dto);
    }

    @Override
    public BaseBo<SettlePreSheet> convert(SettlePreSheet dto) {

        return super.convert(dto, QuerySettlePreSheetBo::getStatus, QuerySettlePreSheetBo::getSettleStatus);
    }

    @Override
    protected void afterInit(SettlePreSheet dto) {

        SupplierService supplierService = ApplicationUtil.getBean(SupplierService.class);
        Supplier supplier = supplierService.findById(dto.getSupplierId());
        this.supplierCode = supplier.getCode();
        this.supplierName = supplier.getName();

        this.status = dto.getStatus().getCode();
        this.settleStatus = dto.getSettleStatus().getCode();

        UserService userService = ApplicationUtil.getBean(UserService.class);

        if (!StringUtil.isBlank(dto.getApproveBy())) {
            this.approveBy = userService.findById(dto.getApproveBy()).getName();
        }
    }
}
