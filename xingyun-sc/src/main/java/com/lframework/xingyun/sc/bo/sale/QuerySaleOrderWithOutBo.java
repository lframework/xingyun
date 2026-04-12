package com.lframework.xingyun.sc.bo.sale;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Customer;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.customer.CustomerService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.sc.entity.SaleOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QuerySaleOrderWithOutBo extends BaseBo<SaleOrder> {

    /**
     * ID
     */
    @Schema(description = "ID")
    private String id;

    /**
     * 单号
     */
    @Schema(description = "单号")
    private String code;

    /**
     * 仓库编号
     */
    @Schema(description = "仓库编号")
    private String scCode;

    /**
     * 仓库名称
     */
    @Schema(description = "仓库名称")
    private String scName;

    /**
     * 客户编号
     */
    @Schema(description = "客户编号")
    private String customerCode;

    /**
     * 客户名称
     */
    @Schema(description = "客户名称")
    private String customerName;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

    public QuerySaleOrderWithOutBo(SaleOrder dto) {

        super(dto);
    }

    @Override
    protected void afterInit(SaleOrder dto) {

        StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
        StoreCenter sc = storeCenterService.findById(dto.getScId());
        this.scCode = sc.getCode();
        this.scName = sc.getName();

        CustomerService customerService = ApplicationUtil.getBean(CustomerService.class);
        Customer customer = customerService.findById(dto.getCustomerId());
        this.customerCode = customer.getCode();
        this.customerName = customer.getName();
    }
}
