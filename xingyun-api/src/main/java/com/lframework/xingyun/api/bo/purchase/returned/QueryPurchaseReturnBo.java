package com.lframework.xingyun.api.bo.purchase.returned;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.service.IUserService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.storecenter.StoreCenterDto;
import com.lframework.xingyun.basedata.dto.supplier.SupplierDto;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.basedata.service.supplier.ISupplierService;
import com.lframework.xingyun.sc.dto.purchase.receive.ReceiveSheetDto;
import com.lframework.xingyun.sc.dto.purchase.returned.PurchaseReturnDto;
import com.lframework.xingyun.sc.service.purchase.IReceiveSheetService;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryPurchaseReturnBo extends BaseBo<PurchaseReturnDto> {

    /**
     * ID
     */
    private String id;

    /**
     * 单号
     */
    private String code;

    /**
     * 仓库编号
     */
    private String scCode;

    /**
     * 仓库名称
     */
    private String scName;

    /**
     * 供应商编号
     */
    private String supplierCode;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 采购员姓名
     */
    private String purchaserName;

    /**
     * 采购收货单ID
     */
    private String receiveSheetId;

    /**
     * 采购收货单号
     */
    private String receiveSheetCode;

    /**
     * 采购数量
     */
    private Integer totalNum;

    /**
     * 赠品数量
     */
    private Integer totalGiftNum;

    /**
     * 采购金额
     */
    private BigDecimal totalAmount;

    /**
     * 备注
     */
    private String description;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

    /**
     * 审核人
     */
    private String approveBy;

    /**
     * 审核时间
     */
    @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
    private LocalDateTime approveTime;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 拒绝原因
     */
    private String refuseReason;

    /**
     * 结算状态
     */
    private Integer settleStatus;

    public QueryPurchaseReturnBo(PurchaseReturnDto dto) {

        super(dto);
    }

    @Override
    public BaseBo<PurchaseReturnDto> convert(PurchaseReturnDto dto) {

        return super.convert(dto, QueryPurchaseReturnBo::getStatus, QueryPurchaseReturnBo::getSettleStatus);
    }

    @Override
    protected void afterInit(PurchaseReturnDto dto) {

        IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
        StoreCenterDto sc = storeCenterService.getById(dto.getScId());
        this.scCode = sc.getCode();
        this.scName = sc.getName();

        ISupplierService supplierService = ApplicationUtil.getBean(ISupplierService.class);
        SupplierDto supplier = supplierService.getById(dto.getSupplierId());
        this.supplierCode = supplier.getCode();
        this.supplierName = supplier.getName();

        IUserService userService = ApplicationUtil.getBean(IUserService.class);
        if (!StringUtil.isBlank(dto.getPurchaserId())) {
            this.purchaserName = userService.getById(dto.getPurchaserId()).getName();
        }

        this.createBy = userService.getById(dto.getCreateBy()).getName();

        if (!StringUtil.isBlank(dto.getApproveBy())) {
            this.approveBy = userService.getById(dto.getApproveBy()).getName();
        }

        this.status = dto.getStatus().getCode();
        this.settleStatus = dto.getSettleStatus().getCode();

        if (!StringUtil.isBlank(dto.getReceiveSheetId())) {
            IReceiveSheetService receiveSheetService = ApplicationUtil.getBean(IReceiveSheetService.class);
            ReceiveSheetDto receiveSheet = receiveSheetService.getById(dto.getReceiveSheetId());
            this.receiveSheetCode = receiveSheet.getCode();
        }
    }
}
