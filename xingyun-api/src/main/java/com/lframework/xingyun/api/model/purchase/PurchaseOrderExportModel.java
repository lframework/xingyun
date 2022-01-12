package com.lframework.xingyun.api.model.purchase;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.DateUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.components.excel.ExcelModel;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.service.IUserService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.storecenter.StoreCenterDto;
import com.lframework.xingyun.basedata.dto.supplier.SupplierDto;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.basedata.service.supplier.ISupplierService;
import com.lframework.xingyun.sc.dto.purchase.PurchaseOrderDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class PurchaseOrderExportModel extends BaseBo<PurchaseOrderDto> implements ExcelModel {

    /**
     * 单号
     */
    @ExcelProperty("采购订单号")
    private String code;

    /**
     * 仓库名称
     */
    @ExcelProperty("仓库名称")
    private String scName;

    /**
     * 供应商名称
     */
    @ExcelProperty("供应商名称")
    private String supplierName;

    /**
     * 采购员姓名
     */
    @ExcelProperty("采购员")
    private String purchaserName;

    /**
     * 预计到货日期
     */
    @ExcelProperty("预计到货日期")
    @DateTimeFormat(StringPool.DATE_PATTERN)
    private Date expectArriveDate;

    /**
     * 采购数量
     */
    @ExcelProperty("采购数量")
    private Integer purchaseNum;

    /**
     * 赠品数量
     */
    @ExcelProperty("赠品数量")
    private Integer giftNum;

    /**
     * 采购金额
     */
    @ExcelProperty("采购金额")
    private BigDecimal purchaseAmount;

    /**
     * 备注
     */
    @ExcelProperty("订单备注")
    private String description;

    /**
     * 创建人
     */
    @ExcelProperty("创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @ExcelProperty("创建时间")
    @DateTimeFormat(StringPool.DATE_TIME_PATTERN)
    private Date createTime;

    /**
     * 审核人
     */
    @ExcelProperty("审核人")
    private String approveBy;

    /**
     * 审核时间
     */
    @ExcelProperty("审核时间")
    @DateTimeFormat(StringPool.DATE_TIME_PATTERN)
    private Date approveTime;

    /**
     * 状态
     */
    @ExcelProperty("状态")
    private String status;

    /**
     * 拒绝原因
     */
    @ExcelProperty("拒绝原因")
    private String refuseReason;

    public PurchaseOrderExportModel() {

    }

    public PurchaseOrderExportModel(PurchaseOrderDto dto) {

        super(dto);
    }

    @Override
    public <A> BaseBo<PurchaseOrderDto> convert(PurchaseOrderDto dto) {

        return this;
    }

    @Override
    protected void afterInit(PurchaseOrderDto dto) {

        IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
        StoreCenterDto sc = storeCenterService.getById(dto.getScId());

        ISupplierService supplierService = ApplicationUtil.getBean(ISupplierService.class);
        SupplierDto supplier = supplierService.getById(dto.getSupplierId());

        IUserService userService = ApplicationUtil.getBean(IUserService.class);
        UserDto createBy = userService.getById(dto.getCreateBy());

        UserDto approveBy = null;
        if (!StringUtil.isBlank(dto.getApproveBy())) {
            approveBy = userService.getById(dto.getApproveBy());
        }

        this.setCode(dto.getCode());
        this.setScName(sc.getName());
        this.setSupplierName(supplier.getName());
        if (!StringUtil.isBlank(dto.getPurchaserId())) {
            UserDto purchaser = userService.getById(dto.getPurchaserId());
            this.setPurchaserName(purchaser.getName());
        }
        this.setExpectArriveDate(DateUtil.toDate(dto.getExpectArriveDate()));
        this.setPurchaseNum(dto.getTotalNum());
        this.setGiftNum(dto.getTotalGiftNum());
        this.setPurchaseAmount(dto.getTotalAmount());
        this.setDescription(dto.getDescription());
        this.setCreateBy(createBy.getName());
        this.setCreateTime(DateUtil.toDate(dto.getCreateTime()));
        if (approveBy != null) {
            this.setApproveBy(approveBy.getName());
        }

        if (dto.getApproveTime() != null) {
            this.setApproveTime(DateUtil.toDate(dto.getApproveTime()));
        }

        this.setStatus(dto.getStatus().getDesc());
        this.setRefuseReason(dto.getRefuseReason());
    }
}
