package com.lframework.xingyun.sc.bo.purchase.receive;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.basedata.service.supplier.SupplierService;
import com.lframework.xingyun.sc.bo.paytype.OrderPayTypeBo;
import com.lframework.xingyun.sc.dto.purchase.PurchaseProductDto;
import com.lframework.xingyun.sc.dto.purchase.receive.ReceiveSheetFullDto;
import com.lframework.xingyun.sc.entity.OrderPayType;
import com.lframework.xingyun.sc.entity.ProductStock;
import com.lframework.xingyun.sc.entity.PurchaseOrder;
import com.lframework.xingyun.sc.entity.PurchaseOrderDetail;
import com.lframework.xingyun.sc.service.paytype.OrderPayTypeService;
import com.lframework.xingyun.sc.service.purchase.PurchaseOrderDetailService;
import com.lframework.xingyun.sc.service.purchase.PurchaseOrderService;
import com.lframework.xingyun.sc.service.stock.ProductStockService;
import com.lframework.starter.web.inner.service.system.SysUserService;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class GetReceiveSheetBo extends BaseBo<ReceiveSheetFullDto> {

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
   * 仓库ID
   */
  @Schema(description = "仓库ID")
  private String scId;

  /**
   * 仓库名称
   */
  @Schema(description = "仓库名称")
  private String scName;

  /**
   * 供应商ID
   */
  @Schema(description = "供应商ID")
  private String supplierId;

  /**
   * 供应商名称
   */
  @Schema(description = "供应商名称")
  private String supplierName;

  /**
   * 采购员ID
   */
  @Schema(description = "采购员ID")
  private String purchaserId;

  /**
   * 采购员姓名
   */
  @Schema(description = "采购员姓名")
  private String purchaserName;

  /**
   * 付款日期
   */
  @Schema(description = "付款日期")
  @JsonFormat(pattern = StringPool.DATE_PATTERN)
  private LocalDate paymentDate;

  /**
   * 到货日期
   */
  @Schema(description = "到货日期")
  @JsonFormat(pattern = StringPool.DATE_PATTERN)
  private LocalDate receiveDate;

  /**
   * 采购订单ID
   */
  @Schema(description = "采购订单ID")
  private String purchaseOrderId;

  /**
   * 采购订单号
   */
  @Schema(description = "采购订单号")
  private String purchaseOrderCode;

  /**
   * 采购数量
   */
  @Schema(description = "采购数量")
  private BigDecimal totalNum;

  /**
   * 赠品数量
   */
  @Schema(description = "赠品数量")
  private BigDecimal giftNum;

  /**
   * 采购金额
   */
  @Schema(description = "采购金额")
  private BigDecimal totalAmount;

  /**
   * 支付方式
   */
  @Schema(description = "支付方式")
  private List<OrderPayTypeBo> payTypes;

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
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  /**
   * 审核人
   */
  @Schema(description = "审核人")
  private String approveBy;

  /**
   * 审核时间
   */
  @Schema(description = "审核时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime approveTime;

  /**
   * 状态
   */
  @Schema(description = "状态")
  private Integer status;

  /**
   * 拒绝原因
   */
  @Schema(description = "拒绝原因")
  private String refuseReason;

  /**
   * 结算状态
   */
  @Schema(description = "结算状态")
  private Integer settleStatus;

  /**
   * 订单明细
   */
  @Schema(description = "订单明细")
  private List<OrderDetailBo> details;

  public GetReceiveSheetBo() {

  }

  public GetReceiveSheetBo(ReceiveSheetFullDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<ReceiveSheetFullDto> convert(ReceiveSheetFullDto dto) {

    return super.convert(dto, GetReceiveSheetBo::getStatus, GetReceiveSheetBo::getSettleStatus,
        GetReceiveSheetBo::getDetails);
  }

  @Override
  protected void afterInit(ReceiveSheetFullDto dto) {

    StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
    this.scName = storeCenterService.findById(dto.getScId()).getName();

    SupplierService supplierService = ApplicationUtil.getBean(SupplierService.class);
    this.supplierName = supplierService.findById(dto.getSupplierId()).getName();

    SysUserService userService = ApplicationUtil.getBean(SysUserService.class);
    if (!StringUtil.isBlank(dto.getPurchaserId())) {
      this.purchaserName = userService.findById(dto.getPurchaserId()).getName();
    }

    PurchaseOrderService purchaseOrderService = ApplicationUtil.getBean(PurchaseOrderService.class);
    if (!StringUtil.isBlank(dto.getPurchaseOrderId())) {
      PurchaseOrder purchaseOrder = purchaseOrderService.getById(dto.getPurchaseOrderId());
      this.purchaseOrderCode = purchaseOrder.getCode();
    }

    if (!StringUtil.isBlank(dto.getApproveBy())) {
      this.approveBy = userService.findById(dto.getApproveBy()).getName();
    }

    this.status = dto.getStatus().getCode();
    this.settleStatus = dto.getSettleStatus().getCode();

    this.totalNum = dto.getTotalNum();
    this.giftNum = dto.getTotalGiftNum();
    this.totalAmount = dto.getTotalAmount();

    if (!CollectionUtil.isEmpty(dto.getDetails())) {
      this.details = dto.getDetails().stream().map(t -> new OrderDetailBo(this.getScId(), t))
          .collect(Collectors.toList());
    }

    OrderPayTypeService orderPayTypeService = ApplicationUtil.getBean(OrderPayTypeService.class);
    List<OrderPayType> orderPayTypes = orderPayTypeService.findByOrderId(dto.getId());
    this.payTypes = orderPayTypes.stream().map(OrderPayTypeBo::new).collect(Collectors.toList());
  }

  @Data
  public static class OrderDetailBo extends BaseBo<ReceiveSheetFullDto.OrderDetailDto> {

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
     * SKU ID
     */
    @Schema(description = "SKU ID")
    private String skuId;

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
     * 单位
     */
    @Schema(description = "单位")
    private String unit;

    /**
     * 规格
     */
    @Schema(description = "规格")
    private String spec;

    /**
     * 分类名称
     */
    @Schema(description = "分类名称")
    private String categoryName;

    /**
     * 品牌名称
     */
    @Schema(description = "品牌名称")
    private String brandName;

    /**
     * 采购数量
     */
    @Schema(description = "采购数量")
    private BigDecimal orderNum;

    /**
     * 剩余收货数量
     */
    @Schema(description = "剩余收货数量")
    private BigDecimal remainNum;

    /**
     * 收货数量
     */
    @Schema(description = "收货数量")
    private BigDecimal receiveNum;

    /**
     * 采购价
     */
    @Schema(description = "采购价")
    private BigDecimal purchasePrice;

    /**
     * 采购总金额
     */
    @Schema(description = "采购总金额")
    private BigDecimal taxAmount;

    /**
     * 含税成本价
     */
    @Schema(description = "含税成本价")
    private BigDecimal taxCostPrice;

    /**
     * 库存数量
     */
    @Schema(description = "库存数量")
    private BigDecimal stockNum;

    /**
     * 是否赠品
     */
    @Schema(description = "是否赠品")
    private Boolean isGift;

    /**
     * 税率
     */
    @Schema(description = "税率")
    private BigDecimal taxRate;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String description;

    /**
     * 采购订单明细ID
     */
    @Schema(description = "采购订单明细ID")
    private String purchaseOrderDetailId;

    /**
     * 仓库ID
     */
    @Schema(description = "仓库ID", hidden = true)
    @JsonIgnore
    private String scId;

    public OrderDetailBo(String scId, ReceiveSheetFullDto.OrderDetailDto dto) {

      this.scId = scId;
      this.init(dto);
    }

    @Override
    public BaseBo<ReceiveSheetFullDto.OrderDetailDto> convert(
        ReceiveSheetFullDto.OrderDetailDto dto) {

      return super.convert(dto);
    }

    @Override
    protected void afterInit(ReceiveSheetFullDto.OrderDetailDto dto) {

      this.receiveNum = dto.getOrderNum();
      this.purchasePrice = dto.getTaxPrice();

      PurchaseOrderService purchaseOrderService = ApplicationUtil.getBean(
          PurchaseOrderService.class);
      PurchaseProductDto product = purchaseOrderService.getPurchaseById(dto.getSkuId());

      this.productId = product.getId();
      this.skuId = product.getSkuId();
      this.productCode = product.getProductCode();
      this.skuCode = product.getSkuCode();
      this.salePropertyText = product.getSalePropertyText();
      this.productName = product.getName();
      this.unit = product.getUnit();
      this.spec = product.getSpec();
      this.categoryName = product.getCategoryName();
      this.brandName = product.getBrandName();

      if (!StringUtil.isBlank(dto.getPurchaseOrderDetailId())) {
        PurchaseOrderDetailService purchaseOrderDetailService = ApplicationUtil.getBean(
            PurchaseOrderDetailService.class);
        PurchaseOrderDetail purchaseOrderDetail = purchaseOrderDetailService.getById(
            dto.getPurchaseOrderDetailId());
        this.orderNum = purchaseOrderDetail.getOrderNum();
        this.remainNum = NumberUtil.sub(purchaseOrderDetail.getOrderNum(),
                purchaseOrderDetail.getReceiveNum());
      }

      ProductStockService productStockService = ApplicationUtil.getBean(ProductStockService.class);
      ProductStock productStock = productStockService.getBySkuIdAndScId(this.getSkuId(),
          this.getScId());
      this.taxCostPrice =
          productStock == null ? BigDecimal.ZERO
              : NumberUtil.getNumber(productStock.getTaxPrice(), 6);
      this.stockNum = productStock == null ? BigDecimal.ZERO : productStock.getStockNum();
    }
  }
}
