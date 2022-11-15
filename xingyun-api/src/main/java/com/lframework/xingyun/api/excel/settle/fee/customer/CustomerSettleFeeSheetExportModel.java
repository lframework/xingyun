package com.lframework.xingyun.api.excel.settle.fee.customer;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.DateUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.components.excel.ExcelModel;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.xingyun.basedata.entity.Customer;
import com.lframework.xingyun.basedata.service.customer.ICustomerService;
import com.lframework.xingyun.sc.enums.SettleStatus;
import com.lframework.xingyun.settle.entity.CustomerSettleFeeSheet;
import com.lframework.xingyun.settle.enums.CustomerSettleFeeSheetStatus;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerSettleFeeSheetExportModel extends BaseBo<CustomerSettleFeeSheet> implements
        ExcelModel {

    /**
     * 业务单据号
     */
    @ExcelProperty("业务单据号")
    private String code;

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
     * 单据总金额
     */
    @ExcelProperty("单据总金额")
    private BigDecimal totalAmount;

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

    public CustomerSettleFeeSheetExportModel() {

    }

    public CustomerSettleFeeSheetExportModel(CustomerSettleFeeSheet dto) {

        super(dto);
    }

    @Override
    public <A> BaseBo<CustomerSettleFeeSheet> convert(CustomerSettleFeeSheet dto) {

        return this;
    }

    @Override
    protected void afterInit(CustomerSettleFeeSheet dto) {

        ICustomerService customerService = ApplicationUtil.getBean(ICustomerService.class);
        Customer customer = customerService.findById(dto.getCustomerId());

        IUserService userService = ApplicationUtil.getBean(IUserService.class);
        UserDto approveBy = null;
        if (!StringUtil.isBlank(dto.getApproveBy())) {
            approveBy = userService.findById(dto.getApproveBy());
        }

        this.setCode(dto.getCode());
        this.setCustomerCode(customer.getCode());
        this.setCustomerName(customer.getName());
        this.setTotalAmount(dto.getTotalAmount());
        this.setCreateTime(DateUtil.toDate(dto.getCreateTime()));
        this.setStatus(EnumUtil.getDesc(CustomerSettleFeeSheetStatus.class, dto.getStatus()));
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
