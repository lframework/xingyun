package com.lframework.xingyun.api.excel.settle.sheet.customer;

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
import com.lframework.xingyun.settle.entity.CustomerSettleSheet;
import com.lframework.xingyun.settle.enums.CustomerSettleSheetStatus;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerSettleSheetExportModel extends BaseBo<CustomerSettleSheet> implements
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
     * 实付总金额
     */
    @ExcelProperty("实付总金额")
    private BigDecimal totalAmount;

    /**
     * 优惠总金额
     */
    @ExcelProperty("优惠总金额")
    private BigDecimal totalDiscountAmount;

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
     * 备注
     */
    @ExcelProperty("备注")
    private String description;

    public CustomerSettleSheetExportModel() {

    }

    public CustomerSettleSheetExportModel(CustomerSettleSheet dto) {

        super(dto);
    }

    @Override
    public <A> BaseBo<CustomerSettleSheet> convert(CustomerSettleSheet dto) {

        return this;
    }

    @Override
    protected void afterInit(CustomerSettleSheet dto) {

        ICustomerService customerService = ApplicationUtil.getBean(ICustomerService.class);
        Customer customer = customerService.findById(dto.getCustomerId());

        IUserService userService = ApplicationUtil.getBean(IUserService.class);
        UserDto createBy = userService.findById(dto.getCreateBy());
        UserDto approveBy = null;
        if (!StringUtil.isBlank(dto.getApproveBy())) {
            approveBy = userService.findById(dto.getApproveBy());
        }

        this.setCode(dto.getCode());
        this.setCustomerCode(customer.getCode());
        this.setCustomerName(customer.getName());
        this.setTotalAmount(dto.getTotalAmount());
        this.setTotalDiscountAmount(dto.getTotalDiscountAmount());
        this.setCreateTime(DateUtil.toDate(dto.getCreateTime()));
        this.setCreateBy(createBy.getName());
        this.setStatus(EnumUtil.getDesc(CustomerSettleSheetStatus.class, dto.getStatus()));
        if (approveBy != null) {
            this.setApproveBy(approveBy.getName());
        }

        if (dto.getApproveTime() != null) {
            this.setApproveTime(DateUtil.toDate(dto.getApproveTime()));
        }
        this.setDescription(dto.getDescription());
    }
}
