package com.lframework.xingyun.settle.bo.pre.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.xingyun.template.core.service.UserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Customer;
import com.lframework.xingyun.basedata.service.customer.CustomerService;
import com.lframework.xingyun.settle.dto.pre.customer.CustomerSettlePreSheetFullDto;
import com.lframework.xingyun.settle.entity.SettleInItem;
import com.lframework.xingyun.settle.service.SettleInItemService;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class GetCustomerSettlePreSheetBo extends BaseBo<CustomerSettlePreSheetFullDto> {

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

    public GetCustomerSettlePreSheetBo() {

    }

    public GetCustomerSettlePreSheetBo(CustomerSettlePreSheetFullDto dto) {

        super(dto);
    }

    @Override
    public BaseBo<CustomerSettlePreSheetFullDto> convert(CustomerSettlePreSheetFullDto dto) {

        return super.convert(dto, GetCustomerSettlePreSheetBo::getStatus,
            GetCustomerSettlePreSheetBo::getDetails);
    }

    @Override
    protected void afterInit(CustomerSettlePreSheetFullDto dto) {

        CustomerService customerService = ApplicationUtil.getBean(CustomerService.class);
        Customer customer = customerService.findById(dto.getCustomerId());
        this.customerCode = customer.getCode();
        this.customerName = customer.getName();

        this.status = dto.getStatus().getCode();

        UserService userService = ApplicationUtil.getBean(UserService.class);

        if (!StringUtil.isBlank(dto.getApproveBy())) {
            this.approveBy = userService.findById(dto.getApproveBy()).getName();
        }

        if (!CollectionUtil.isEmpty(dto.getDetails())) {
            this.details = dto.getDetails().stream().map(SheetDetailBo::new)
                .collect(Collectors.toList());
        }
    }

    @Data
    public static class SheetDetailBo extends BaseBo<CustomerSettlePreSheetFullDto.SheetDetailDto> {

        /**
         * 明细ID
         */
        @ApiModelProperty("明细ID")
        private String id;

        /**
         * 项目ID
         */
        @ApiModelProperty("项目ID")
        private String itemId;

        /**
         * 项目名称
         */
        @ApiModelProperty("项目名称")
        private String itemName;

        /**
         * 金额
         */
        @ApiModelProperty("金额")
        private BigDecimal amount;

        public SheetDetailBo() {

        }

        public SheetDetailBo(CustomerSettlePreSheetFullDto.SheetDetailDto dto) {

            super(dto);
        }

        @Override
        protected void afterInit(CustomerSettlePreSheetFullDto.SheetDetailDto dto) {

            SettleInItemService settleInItemService = ApplicationUtil.getBean(
                SettleInItemService.class);
            SettleInItem item = settleInItemService.findById(dto.getItemId());
            this.itemName = item.getName();
        }
    }
}
