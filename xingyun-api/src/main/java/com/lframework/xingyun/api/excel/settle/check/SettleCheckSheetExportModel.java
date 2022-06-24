package com.lframework.xingyun.api.excel.settle.check;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.DateUtil;
import com.lframework.common.utils.NumberUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.components.excel.ExcelModel;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.xingyun.basedata.entity.Supplier;
import com.lframework.xingyun.basedata.service.supplier.ISupplierService;
import com.lframework.xingyun.sc.enums.SettleStatus;
import com.lframework.xingyun.settle.entity.SettleCheckSheet;
import com.lframework.xingyun.settle.enums.SettleCheckSheetStatus;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SettleCheckSheetExportModel extends BaseBo<SettleCheckSheet> implements ExcelModel {

    /**
     * 业务单据号
     */
    @ExcelProperty("业务单据号")
    private String code;

    /**
     * 供应商编号
     */
    @ExcelProperty("供应商编号")
    private String supplierCode;

    /**
     * 供应商名称
     */
    @ExcelProperty("供应商名称")
    private String supplierName;

    /**
     * 单据金额
     */
    @ExcelProperty("单据金额")
    private BigDecimal totalAmount;

    /**
     * 应付总金额
     */
    @ExcelProperty("应付总金额")
    private BigDecimal totalPayAmount;

    /**
     * 已付款金额
     */
    @ExcelProperty("已付款金额")
    private BigDecimal totalPayedAmount;

    /**
     * 已优惠金额
     */
    @ExcelProperty("已优惠金额")
    private BigDecimal totalDiscountAmount;

    /**
     * 未付款金额
     */
    @ExcelProperty("未付款金额")
    private BigDecimal totalUnPayAmount;

    /**
     * 操作时间
     */
    @ExcelProperty("操作时间")
    @DateTimeFormat(StringPool.DATE_TIME_PATTERN)
    private Date createTime;

    /**
     * 操作人
     */
    @ExcelProperty("操作人")
    private String createBy;

    /**
     * 审核状态
     */
    @ExcelProperty("审核状态")
    private String status;

    /**
     * 审核时间
     */
    @ExcelProperty("审核时间")
    @DateTimeFormat(StringPool.DATE_TIME_PATTERN)
    private Date approveTime;

    /**
     * 审核人
     */
    @ExcelProperty("审核人")
    private String approveBy;

    /**
     * 结算状态
     */
    @ExcelProperty("结算状态")
    private String settleStatus;

    /**
     * 备注
     */
    @ExcelProperty("备注")
    private String description;

    public SettleCheckSheetExportModel() {

    }

    public SettleCheckSheetExportModel(SettleCheckSheet dto) {

        super(dto);
    }

    @Override
    public <A> BaseBo<SettleCheckSheet> convert(SettleCheckSheet dto) {

        return this;
    }

    @Override
    protected void afterInit(SettleCheckSheet dto) {

        ISupplierService supplierService = ApplicationUtil.getBean(ISupplierService.class);
        Supplier supplier = supplierService.findById(dto.getSupplierId());

        IUserService userService = ApplicationUtil.getBean(IUserService.class);
        UserDto createBy = userService.findById(dto.getCreateBy());
        UserDto approveBy = null;
        if (!StringUtil.isBlank(dto.getApproveBy())) {
            approveBy = userService.findById(dto.getApproveBy());
        }

        this.setCode(dto.getCode());
        this.setSupplierCode(supplier.getCode());
        this.setSupplierName(supplier.getName());
        this.setTotalAmount(dto.getTotalAmount());
        this.setTotalPayAmount(dto.getTotalPayAmount());
        this.setTotalPayedAmount(dto.getTotalPayedAmount());
        this.setTotalDiscountAmount(dto.getTotalDiscountAmount());
        this.setTotalUnPayAmount(
                NumberUtil.sub(dto.getTotalPayAmount(), dto.getTotalPayedAmount(), dto.getTotalDiscountAmount()));
        this.setCreateTime(DateUtil.toDate(dto.getCreateTime()));
        this.setCreateBy(createBy.getName());
        this.setStatus(EnumUtil.getDesc(SettleCheckSheetStatus.class, dto.getStatus()));
        if (approveBy != null) {
            this.setApproveBy(approveBy.getName());
        }

        if (dto.getApproveTime() != null) {
            this.setApproveTime(DateUtil.toDate(dto.getApproveTime()));
        }
        this.setSettleStatus(EnumUtil.getDesc(SettleStatus.class, dto.getSettleStatus()));
        this.setDescription(dto.getDescription());
    }
}
