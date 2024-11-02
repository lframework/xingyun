package com.lframework.xingyun.sc.bo.purchase;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.basedata.service.supplier.SupplierService;
import com.lframework.xingyun.sc.bo.paytype.OrderPayTypeBo;
import com.lframework.xingyun.sc.dto.purchase.PurchaseOrderFullDto;
import com.lframework.xingyun.sc.dto.purchase.PurchaseProductDto;
import com.lframework.xingyun.sc.entity.OrderPayType;
import com.lframework.xingyun.sc.entity.ProductStock;
import com.lframework.xingyun.sc.service.paytype.OrderPayTypeService;
import com.lframework.xingyun.sc.service.purchase.PurchaseOrderService;
import com.lframework.xingyun.sc.service.stock.ProductStockService;
import com.lframework.xingyun.template.core.service.UserService;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class GetPurchaseOrderBo extends BaseBo<PurchaseOrderFullDto> {

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
   * 仓库ID
   */
  @ApiModelProperty("仓库ID")
  private String scId;

  /**
   * 仓库名称
   */
  @ApiModelProperty("仓库名称")
  private String scName;

  /**
   * 供应商ID
   */
  @ApiModelProperty("供应商ID")
  private String supplierId;

  /**
   * 供应商名称
   */
  @ApiModelProperty("供应商名称")
  private String supplierName;

  /**
   * 采购员ID
   */
  @ApiModelProperty("采购员ID")
  private String purchaserId;

  /**
   * 采购员姓名
   */
  @ApiModelProperty("采购员姓名")
  private String purchaserName;

  /**
   * 预计到货日期
   */
  @ApiModelProperty("预计到货日期")
  @JsonFormat(pattern = StringPool.DATE_PATTERN)
  private LocalDate expectArriveDate;

  /**
   * 采购数量
   */
  @ApiModelProperty("采购数量")
  private Integer totalNum;

  /**
   * 赠品数量
   */
  @ApiModelProperty("赠品数量")
  private Integer giftNum;

  /**
   * 采购金额
   */
  @ApiModelProperty("采购金额")
  private BigDecimal totalAmount;

  /**
   * 支付方式
   */
  @ApiModelProperty("支付方式")
  private List<OrderPayTypeBo> payTypes;

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
   * 订单明细
   */
  @ApiModelProperty("订单明细")
  private List<OrderDetailBo> details;

  public GetPurchaseOrderBo() {

  }

  public GetPurchaseOrderBo(PurchaseOrderFullDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<PurchaseOrderFullDto> convert(PurchaseOrderFullDto dto) {

    return super.convert(dto, GetPurchaseOrderBo::getStatus, GetPurchaseOrderBo::getDetails);
  }

  @Override
  protected void afterInit(PurchaseOrderFullDto dto) {

    StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
    this.scName = storeCenterService.findById(dto.getScId()).getName();

    SupplierService supplierService = ApplicationUtil.getBean(SupplierService.class);
    this.supplierName = supplierService.findById(dto.getSupplierId()).getName();

    UserService userService = ApplicationUtil.getBean(UserService.class);
    if (!StringUtil.isBlank(dto.getPurchaserId())) {
      this.purchaserName = userService.findById(dto.getPurchaserId()).getName();
    }

    if (!StringUtil.isBlank(dto.getApproveBy())) {
      this.approveBy = userService.findById(dto.getApproveBy()).getName();
    }

    this.status = dto.getStatus().getCode();

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
  public static class OrderDetailBo extends BaseBo<PurchaseOrderFullDto.OrderDetailDto> {

    /**
     * 明细ID
     */
    @ApiModelProperty("明细ID")
    private String id;

    /**
     * 商品ID
     */
    @ApiModelProperty("商品ID")
    private String productId;

    /**
     * 商品编号
     */
    @ApiModelProperty("商品编号")
    private String productCode;

    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
    private String productName;

    /**
     * SKU编号
     */
    @ApiModelProperty("SKU编号")
    private String skuCode;

    /**
     * 简码
     */
    @ApiModelProperty("简码")
    private String externalCode;

    /**
     * 单位
     */
    @ApiModelProperty("单位")
    private String unit;

    /**
     * 规格
     */
    @ApiModelProperty("规格")
    private String spec;

    /**
     * 分类名称
     */
    @ApiModelProperty("分类名称")
    private String categoryName;

    /**
     * 品牌名称
     */
    @ApiModelProperty("品牌名称")
    private String brandName;

    /**
     * 采购数量
     */
    @ApiModelProperty("采购数量")
    private Integer purchaseNum;

    /**
     * 采购价
     */
    @ApiModelProperty("采购价")
    private BigDecimal purchasePrice;

    /**
     * 含税成本价
     */
    @ApiModelProperty("含税成本价")
    private BigDecimal taxCostPrice;

    /**
     * 库存数量
     */
    @ApiModelProperty("库存数量")
    private Integer stockNum;

    /**
     * 是否赠品
     */
    @ApiModelProperty("是否赠品")
    private Boolean isGift;

    /**
     * 税率
     */
    @ApiModelProperty("税率")
    private BigDecimal taxRate;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String description;

    /**
     * 仓库ID
     */
    @ApiModelProperty(value = "仓库ID", hidden = true)
    @JsonIgnore
    private String scId;

    public OrderDetailBo(String scId, PurchaseOrderFullDto.OrderDetailDto dto) {

      this.scId = scId;
      this.init(dto);
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

      PurchaseOrderService purchaseOrderService = ApplicationUtil.getBean(
          PurchaseOrderService.class);
      PurchaseProductDto product = purchaseOrderService.getPurchaseById(dto.getProductId());

      this.productCode = product.getCode();
      this.productName = product.getName();
      this.skuCode = product.getSkuCode();
      this.externalCode = product.getExternalCode();
      this.unit = product.getUnit();
      this.spec = product.getSpec();
      this.categoryName = product.getCategoryName();
      this.brandName = product.getBrandName();

      ProductStockService productStockService = ApplicationUtil.getBean(
          ProductStockService.class);
      ProductStock productStock = productStockService.getByProductIdAndScId(
          this.getProductId(), this.getScId());
      this.taxCostPrice =
          productStock == null ? BigDecimal.ZERO
              : NumberUtil.getNumber(productStock.getTaxPrice(), 2);
      this.stockNum = productStock == null ? 0 : productStock.getStockNum();
    }
  }
}
