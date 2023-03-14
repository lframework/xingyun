package com.lframework.xingyun.settle.bo.fee.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.mybatis.service.UserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Customer;
import com.lframework.xingyun.basedata.service.customer.CustomerService;
import com.lframework.xingyun.settle.entity.CustomerSettleFeeSheet;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryCustomerSettleFeeSheetBo extends BaseBo<CustomerSettleFeeSheet> {

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
     * 客户ID
     */
    @ApiModelProperty("客户ID")
    private String customerId;

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
     * 单据类型
     */
    @ApiModelProperty("单据类型")
    private Integer sheetType;

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

    public QueryCustomerSettleFeeSheetBo() {

    }

    public QueryCustomerSettleFeeSheetBo(CustomerSettleFeeSheet dto) {

        super(dto);
    }

    @Override
    public BaseBo<CustomerSettleFeeSheet> convert(CustomerSettleFeeSheet dto) {

        return super.convert(dto, QueryCustomerSettleFeeSheetBo::getSheetType,
            QueryCustomerSettleFeeSheetBo::getStatus,
            QueryCustomerSettleFeeSheetBo::getSettleStatus);
    }

    @Override
    protected void afterInit(CustomerSettleFeeSheet dto) {

        CustomerService customerService = ApplicationUtil.getBean(CustomerService.class);
        Customer customer = customerService.findById(dto.getCustomerId());
        this.customerCode = customer.getCode();
        this.customerName = customer.getName();

        this.sheetType = dto.getSheetType().getCode();
        this.status = dto.getStatus().getCode();
        this.settleStatus = dto.getSettleStatus().getCode();

        UserService userService = ApplicationUtil.getBean(UserService.class);

        if (!StringUtil.isBlank(dto.getApproveBy())) {
            this.approveBy = userService.findById(dto.getApproveBy()).getName();
        }
    }
}
