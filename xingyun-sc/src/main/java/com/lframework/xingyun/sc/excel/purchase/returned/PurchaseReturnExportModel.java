package com.lframework.xingyun.sc.excel.purchase.returned;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.xingyun.template.core.service.UserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.starter.web.components.excel.ExcelModel;
import com.lframework.xingyun.template.core.dto.UserDto;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.entity.Supplier;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.basedata.service.supplier.SupplierService;
import com.lframework.xingyun.sc.entity.PurchaseReturn;
import com.lframework.xingyun.sc.entity.ReceiveSheet;
import com.lframework.xingyun.sc.service.purchase.ReceiveSheetService;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class PurchaseReturnExportModel extends BaseBo<PurchaseReturn> implements ExcelModel {

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
     * 采购员姓名
     */
    @ExcelProperty("采购员")
    private String purchaserName;

    /**
     * 单据总金额
     */
    @ExcelProperty("单据总金额")
    private BigDecimal totalAmount;

    /**
     * 商品数量
     */
    @ExcelProperty("商品数量")
    private Integer receiveNum;

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
     * 采购收货单号
     */
    @ExcelProperty("采购收货单号")
    private String receiveSheetCode;

    public PurchaseReturnExportModel() {

    }

    public PurchaseReturnExportModel(PurchaseReturn dto) {

        super(dto);
    }

    @Override
    public <A> BaseBo<PurchaseReturn> convert(PurchaseReturn dto) {

        return this;
    }

    @Override
    protected void afterInit(PurchaseReturn dto) {

        StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
        StoreCenter sc = storeCenterService.findById(dto.getScId());

        SupplierService supplierService = ApplicationUtil.getBean(SupplierService.class);
        Supplier supplier = supplierService.findById(dto.getSupplierId());

        UserService userService = ApplicationUtil.getBean(UserService.class);
        UserDto purchaser = null;
        if (!StringUtil.isBlank(dto.getPurchaserId())) {
            purchaser = userService.findById(dto.getPurchaserId());
        }
        UserDto approveBy = null;
        if (!StringUtil.isBlank(dto.getApproveBy())) {
            approveBy = userService.findById(dto.getApproveBy());
        }

        this.setCode(dto.getCode());
        this.setScCode(sc.getCode());
        this.setScName(sc.getName());
        this.setSupplierCode(supplier.getCode());
        this.setSupplierName(supplier.getName());
        this.setPurchaserName(purchaser == null ? null : purchaser.getName());
        this.setTotalAmount(dto.getTotalAmount());
        this.setReceiveNum(dto.getTotalNum());
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
        if (!StringUtil.isBlank(dto.getReceiveSheetId())) {
            ReceiveSheetService receiveSheetService = ApplicationUtil.getBean(ReceiveSheetService.class);
            ReceiveSheet receiveSheet = receiveSheetService.getById(dto.getReceiveSheetId());
            this.setReceiveSheetCode(receiveSheet.getCode());
        }
    }
}
