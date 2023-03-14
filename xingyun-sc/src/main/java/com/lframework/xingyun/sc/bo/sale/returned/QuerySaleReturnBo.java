package com.lframework.xingyun.sc.bo.sale.returned;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.mybatis.service.UserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Customer;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.customer.CustomerService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.sc.entity.SaleOutSheet;
import com.lframework.xingyun.sc.entity.SaleReturn;
import com.lframework.xingyun.sc.service.sale.SaleOutSheetService;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QuerySaleReturnBo extends BaseBo<SaleReturn> {

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
    private String customerCode;

    /**
     * 客户名称
     */
    @ApiModelProperty("客户名称")
    private String customerName;

    /**
     * 销售员姓名
     */
    @ApiModelProperty("销售员姓名")
    private String salerName;

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
    private Integer totalGiftNum;

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

    public QuerySaleReturnBo(SaleReturn dto) {

        super(dto);
    }

    @Override
    public BaseBo<SaleReturn> convert(SaleReturn dto) {

        return super.convert(dto, QuerySaleReturnBo::getStatus, QuerySaleReturnBo::getSettleStatus);
    }

    @Override
    protected void afterInit(SaleReturn dto) {

        StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
        StoreCenter sc = storeCenterService.findById(dto.getScId());
        this.scCode = sc.getCode();
        this.scName = sc.getName();

        CustomerService customerService = ApplicationUtil.getBean(CustomerService.class);
        Customer customer = customerService.findById(dto.getCustomerId());
        this.customerCode = customer.getCode();
        this.customerName = customer.getName();

        UserService userService = ApplicationUtil.getBean(UserService.class);
        if (!StringUtil.isBlank(dto.getSalerId())) {
            this.salerName = userService.findById(dto.getSalerId()).getName();
        }

        if (!StringUtil.isBlank(dto.getApproveBy())) {
            this.approveBy = userService.findById(dto.getApproveBy()).getName();
        }

        this.status = dto.getStatus().getCode();
        this.settleStatus = dto.getSettleStatus().getCode();

        if (!StringUtil.isBlank(dto.getOutSheetId())) {
            SaleOutSheetService saleOutSheetService = ApplicationUtil.getBean(SaleOutSheetService.class);
            SaleOutSheet outSheet = saleOutSheetService.getById(dto.getOutSheetId());
            this.outSheetCode = outSheet.getCode();
        }
    }
}
