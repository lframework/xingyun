package com.lframework.xingyun.api.excel.sale.returned;

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
import com.lframework.xingyun.basedata.entity.Customer;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.customer.ICustomerService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.sc.entity.SaleOutSheet;
import com.lframework.xingyun.sc.entity.SaleReturn;
import com.lframework.xingyun.sc.service.sale.ISaleOutSheetService;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SaleReturnExportModel extends BaseBo<SaleReturn> implements ExcelModel {

    /**
     * 单号
     */
    @ExcelProperty("业务单据号")
    private String code;

    /**
     * 仓库编号
     */
    @ExcelProperty("仓库编号")
    private String scCode;

    /**
     * 仓库名称
     */
    @ExcelProperty("仓库名称")
    private String scName;

    /**
     * 客户编号
     */
    @ExcelProperty("客户编号")
    private String customerCode;

    /**
     * 客户名称
     */
    @ExcelProperty("客户名称")
    private String customerName;

    /**
     * 销售员姓名
     */
    @ExcelProperty("销售员")
    private String salerName;

    /**
     * 单据总金额
     */
    @ExcelProperty("单据总金额")
    private BigDecimal totalAmount;

    /**
     * 商品数量
     */
    @ExcelProperty("商品数量")
    private Integer totalNum;

    /**
     * 赠品数量
     */
    @ExcelProperty("赠品数量")
    private Integer giftNum;

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

    /**
     * 销售出库单号
     */
    @ExcelProperty("销售出库单号")
    private String outSheetCode;

    public SaleReturnExportModel() {

    }

    public SaleReturnExportModel(SaleReturn dto) {

        super(dto);
    }

    @Override
    public <A> BaseBo<SaleReturn> convert(SaleReturn dto) {

        return this;
    }

    @Override
    protected void afterInit(SaleReturn dto) {

        IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
        StoreCenter sc = storeCenterService.findById(dto.getScId());

        ICustomerService customerService = ApplicationUtil.getBean(ICustomerService.class);
        Customer customer = customerService.findById(dto.getCustomerId());

        IUserService userService = ApplicationUtil.getBean(IUserService.class);
        UserDto saler = null;
        if (!StringUtil.isBlank(dto.getSalerId())) {
            saler = userService.findById(dto.getSalerId());
        }
        UserDto approveBy = null;
        if (!StringUtil.isBlank(dto.getApproveBy())) {
            approveBy = userService.findById(dto.getApproveBy());
        }

        this.setCode(dto.getCode());
        this.setScCode(sc.getCode());
        this.setScName(sc.getName());
        this.setCustomerCode(customer.getCode());
        this.setCustomerName(customer.getName());
        this.setSalerName(saler == null ? null : saler.getName());
        this.setTotalAmount(dto.getTotalAmount());
        this.setTotalNum(dto.getTotalNum());
        this.setGiftNum(dto.getTotalGiftNum());
        this.setCreateTime(DateUtil.toDate(dto.getCreateTime()));
        this.setStatus(dto.getStatus().getDesc());
        if (dto.getApproveTime() != null) {
            this.setApproveTime(DateUtil.toDate(dto.getApproveTime()));
        }
        if (approveBy != null) {
            this.setApproveBy(approveBy.getName());
        }
        this.setSettleStatus(dto.getSettleStatus().getDesc());
        this.setDescription(dto.getDescription());
        if (!StringUtil.isBlank(dto.getOutSheetId())) {
            ISaleOutSheetService saleOutSheetService = ApplicationUtil.getBean(ISaleOutSheetService.class);
            SaleOutSheet saleOutSheet = saleOutSheetService.getById(dto.getOutSheetId());
            this.setOutSheetCode(saleOutSheet.getCode());
        }
    }
}
