package com.lframework.xingyun.sc.bo.purchase;

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
import com.lframework.xingyun.sc.dto.purchase.PurchaseOrderFullDto;
import com.lframework.xingyun.sc.dto.purchase.PurchaseProductDto;
import com.lframework.xingyun.sc.enums.PurchaseOrderStatus;
import com.lframework.xingyun.sc.service.purchase.PurchaseOrderService;
import com.lframework.starter.web.inner.service.system.SysUserService;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class PrintPurchaseOrderBo extends BaseBo<PurchaseOrderFullDto> {

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
   * 预计到货日期
   */
  @Schema(description = "预计到货日期")
  private String expectArriveDate;

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
  private List<OrderDetailBo> details;

  public PrintPurchaseOrderBo() {

  }

  public PrintPurchaseOrderBo(PurchaseOrderFullDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<PurchaseOrderFullDto> convert(PurchaseOrderFullDto dto) {

    return super.convert(dto, PrintPurchaseOrderBo::getDetails);
  }

  @Override
  protected void afterInit(PurchaseOrderFullDto dto) {

    this.purchaserName = StringPool.EMPTY_STR;
    this.expectArriveDate = StringPool.EMPTY_STR;
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

    if (dto.getExpectArriveDate() != null) {
      this.expectArriveDate = DateUtil.formatDate(dto.getExpectArriveDate());
    }

    this.createTime = DateUtil.formatDateTime(dto.getCreateTime());

    if (!StringUtil.isBlank(dto.getApproveBy())
        && dto.getStatus() == PurchaseOrderStatus.APPROVE_PASS) {
      this.approveBy = userService.findById(dto.getApproveBy()).getName();
      this.approveTime = DateUtil.formatDateTime(dto.getApproveTime());
    }

    if (!CollectionUtil.isEmpty(dto.getDetails())) {
      this.details = dto.getDetails().stream().map(OrderDetailBo::new).collect(Collectors.toList());
    }
  }

  @Data
  public static class OrderDetailBo extends BaseBo<PurchaseOrderFullDto.OrderDetailDto> {

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
     * 采购数量
     */
    @Schema(description = "采购数量")
    private BigDecimal purchaseNum;

    /**
     * 采购价
     */
    @Schema(description = "采购价")
    private BigDecimal purchasePrice;

    /**
     * 采购金额
     */
    @Schema(description = "采购金额")
    private BigDecimal purchaseAmount;

    public OrderDetailBo(PurchaseOrderFullDto.OrderDetailDto dto) {

      super(dto);
    }

    @Override
    public BaseBo<PurchaseOrderFullDto.OrderDetailDto> convert(
        PurchaseOrderFullDto.OrderDetailDto dto) {

      return super.convert(dto);
    }

    @Override
    protected void afterInit(PurchaseOrderFullDto.OrderDetailDto dto) {

      this.purchaseNum = dto.getOrderNum();
      this.purchasePrice = dto.getTaxPrice();
      this.purchaseAmount = dto.getTaxAmount();

      PurchaseOrderService purchaseOrderService = ApplicationUtil.getBean(
          PurchaseOrderService.class);
      PurchaseProductDto product = purchaseOrderService.getPurchaseById(dto.getSkuId());

      this.productCode = product.getProductCode();
      this.skuCode = product.getSkuCode();
      this.salePropertyText = product.getSalePropertyText();
      this.productName = product.getName();
    }
  }
}
