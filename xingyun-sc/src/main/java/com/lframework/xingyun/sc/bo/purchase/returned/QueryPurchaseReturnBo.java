package com.lframework.xingyun.sc.bo.purchase.returned;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.mybatis.service.UserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.entity.Supplier;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.basedata.service.supplier.SupplierService;
import com.lframework.xingyun.sc.entity.PurchaseReturn;
import com.lframework.xingyun.sc.entity.ReceiveSheet;
import com.lframework.xingyun.sc.service.purchase.ReceiveSheetService;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryPurchaseReturnBo extends BaseBo<PurchaseReturn> {

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
     * 供应商编号
     */
    @ApiModelProperty("供应商编号")
    private String supplierCode;

    /**
     * 供应商名称
     */
    @ApiModelProperty("供应商名称")
    private String supplierName;

    /**
     * 采购员姓名
     */
    @ApiModelProperty("采购员姓名")
    private String purchaserName;

    /**
     * 采购收货单ID
     */
    @ApiModelProperty("采购收货单ID")
    private String receiveSheetId;

    /**
     * 采购收货单号
     */
    @ApiModelProperty("采购收货单号")
    private String receiveSheetCode;

    /**
     * 采购数量
     */
    @ApiModelProperty("采购数量")
    private Integer totalNum;

    /**
     * 赠品数量
     */
    @ApiModelProperty("赠品数量")
    private Integer totalGiftNum;

    /**
     * 采购金额
     */
    @ApiModelProperty("采购金额")
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

    public QueryPurchaseReturnBo(PurchaseReturn dto) {

        super(dto);
    }

    @Override
    public BaseBo<PurchaseReturn> convert(PurchaseReturn dto) {

        return super.convert(dto, QueryPurchaseReturnBo::getStatus, QueryPurchaseReturnBo::getSettleStatus);
    }

    @Override
    protected void afterInit(PurchaseReturn dto) {

        StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
        StoreCenter sc = storeCenterService.findById(dto.getScId());
        this.scCode = sc.getCode();
        this.scName = sc.getName();

        SupplierService supplierService = ApplicationUtil.getBean(SupplierService.class);
        Supplier supplier = supplierService.findById(dto.getSupplierId());
        this.supplierCode = supplier.getCode();
        this.supplierName = supplier.getName();

        UserService userService = ApplicationUtil.getBean(UserService.class);
        if (!StringUtil.isBlank(dto.getPurchaserId())) {
            this.purchaserName = userService.findById(dto.getPurchaserId()).getName();
        }

        if (!StringUtil.isBlank(dto.getApproveBy())) {
            this.approveBy = userService.findById(dto.getApproveBy()).getName();
        }

        this.status = dto.getStatus().getCode();
        this.settleStatus = dto.getSettleStatus().getCode();

        if (!StringUtil.isBlank(dto.getReceiveSheetId())) {
            ReceiveSheetService receiveSheetService = ApplicationUtil.getBean(ReceiveSheetService.class);
            ReceiveSheet receiveSheet = receiveSheetService.getById(dto.getReceiveSheetId());
            this.receiveSheetCode = receiveSheet.getCode();
        }
    }
}
