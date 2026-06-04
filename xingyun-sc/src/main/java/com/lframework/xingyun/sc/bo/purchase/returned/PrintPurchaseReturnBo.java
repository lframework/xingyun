package com.lframework.xingyun.sc.bo.purchase.returned;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.entity.Supplier;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.basedata.service.supplier.SupplierService;
import com.lframework.xingyun.sc.dto.purchase.returned.PurchaseReturnFullDto;
import com.lframework.xingyun.sc.entity.ReceiveSheet;
import com.lframework.xingyun.sc.enums.PurchaseReturnStatus;
import com.lframework.xingyun.sc.service.purchase.ReceiveSheetService;
import com.lframework.starter.web.inner.service.system.SysUserService;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class PrintPurchaseReturnBo extends BaseBo<PurchaseReturnFullDto> {

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
   * 供应商编号
   */
  @Schema(description = "供应商编号")
  private String supplierCode;

  /**
   * 供应商名称
   */
  @Schema(description = "供应商名称")
  private String supplierName;

  /**
   * 采购员姓名
   */
  @Schema(description = "采购员姓名")
  private String purchaserName;

  /**
   * 付款日期
   */
  @Schema(description = "付款日期")
  private String paymentDate;

  /**
   * 采购收货单号
   */
  @Schema(description = "采购收货单号")
  private String receiveSheetCode;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  /**
   * 创建人
   */
  @Schema(description = "创建人")
  private String createBy;

  /**
   * 创建时间
   */
  @Schema(description = "创建时间")
  private String createTime;

  /**
   * 审核人
   */
  @Schema(description = "审核人")
  private String approveBy;

  /**
   * 审核时间
   */
  @Schema(description = "审核时间")
  private String approveTime;

  /**
   * 订单明细
   */
  @Schema(description = "订单明细")
  private List<ReturnDetailBo> details;

  public PrintPurchaseReturnBo(PurchaseReturnFullDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<PurchaseReturnFullDto> convert(PurchaseReturnFullDto dto) {

    return super.convert(dto, PrintPurchaseReturnBo::getDetails);
  }

  @Override
  protected void afterInit(PurchaseReturnFullDto dto) {

    this.purchaserName = StringPool.EMPTY_STR;
    this.paymentDate = StringPool.EMPTY_STR;
    this.receiveSheetCode = StringPool.EMPTY_STR;
    this.approveBy = StringPool.EMPTY_STR;
    this.approveTime = StringPool.EMPTY_STR;

    StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
    StoreCenter sc = storeCenterService.findById(dto.getScId());
    this.scCode = sc.getCode();
    this.scName = sc.getName();

    SupplierService supplierService = ApplicationUtil.getBean(SupplierService.class);
    Supplier supplier = supplierService.findById(dto.getSupplierId());
    this.supplierCode = supplier.getCode();
    this.supplierName = supplier.getName();

    SysUserService userService = ApplicationUtil.getBean(SysUserService.class);
    if (!StringUtil.isBlank(dto.getPurchaserId())) {
      this.purchaserName = userService.findById(dto.getPurchaserId()).getName();
    }

    ReceiveSheetService receiveSheetService = ApplicationUtil.getBean(ReceiveSheetService.class);
    if (!StringUtil.isBlank(dto.getReceiveSheetId())) {
      ReceiveSheet receiveSheet = receiveSheetService.getById(dto.getReceiveSheetId());
      this.receiveSheetCode = receiveSheet.getCode();
    }

    if (dto.getPaymentDate() != null) {
      this.paymentDate = DateUtil.formatDate(dto.getPaymentDate());
    }

    this.createTime = DateUtil.formatDateTime(dto.getCreateTime());

    if (!StringUtil.isBlank(dto.getApproveBy())
        && dto.getStatus() == PurchaseReturnStatus.APPROVE_PASS) {
      this.approveBy = userService.findById(dto.getApproveBy()).getName();
      this.approveTime = DateUtil.formatDateTime(dto.getApproveTime());
    }

    if (!CollectionUtil.isEmpty(dto.getDetails())) {
      this.details = dto.getDetails().stream().map(ReturnDetailBo::new)
          .collect(Collectors.toList());
    }
  }

  @Data
  public static class ReturnDetailBo extends BaseBo<PurchaseReturnFullDto.ReturnDetailDto> {

    /**
     * 明细ID
     */
    @Schema(description = "明细ID")
    private String id;

    /**
     * 商品ID
     */
    @Schema(description = "商品ID")
    private String productId;

    /**
     * 商品编号
     */
    @Schema(description = "商品编号")
    private String productCode;

    /**
     * SKU编号
     */
    @Schema(description = "SKU编号")
    private String skuCode;

    /**
     * 销售属性
     */
    @Schema(description = "销售属性")
    private String salePropertyText;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String productName;

    /**
     * 退货数量
     */
    @Schema(description = "退货数量")
    private BigDecimal returnNum;

    /**
     * 退货价
     */
    @Schema(description = "退货价")
    private BigDecimal purchasePrice;

    /**
     * 退货金额
     */
    @Schema(description = "退货金额")
    private BigDecimal returnAmount;

    public ReturnDetailBo(PurchaseReturnFullDto.ReturnDetailDto dto) {

      super(dto);
    }

    @Override
    public BaseBo<PurchaseReturnFullDto.ReturnDetailDto> convert(
        PurchaseReturnFullDto.ReturnDetailDto dto) {

      return super.convert(dto);
    }

    @Override
    protected void afterInit(PurchaseReturnFullDto.ReturnDetailDto dto) {

      this.returnNum = dto.getReturnNum();
      this.purchasePrice = dto.getTaxPrice();
      this.returnAmount = dto.getTaxAmount();
    }
  }
}
