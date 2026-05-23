package com.lframework.xingyun.sc.bo.sale;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.service.customer.CustomerService;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.sc.bo.paytype.OrderPayTypeBo;
import com.lframework.xingyun.sc.dto.sale.SaleOrderFullDto;
import com.lframework.xingyun.sc.dto.sale.SaleProductDto;
import com.lframework.xingyun.sc.entity.OrderPayType;
import com.lframework.xingyun.sc.entity.ProductStock;
import com.lframework.xingyun.sc.service.paytype.OrderPayTypeService;
import com.lframework.xingyun.sc.service.sale.SaleOrderService;
import com.lframework.xingyun.sc.service.stock.ProductStockService;
import com.lframework.starter.web.inner.service.system.SysUserService;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class GetSaleOrderBo extends BaseBo<SaleOrderFullDto> {

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
   * 客户ID
   */
  @Schema(description = "客户ID")
  private String customerId;

  /**
   * 客户名称
   */
  @Schema(description = "客户名称")
  private String customerName;

  /**
   * 销售员ID
   */
  @Schema(description = "销售员ID")
  private String salerId;

  /**
   * 销售员姓名
   */
  @Schema(description = "销售员姓名")
  private String salerName;

  /**
   * 销售数量
   */
  @Schema(description = "销售数量")
  private BigDecimal totalNum;

  /**
   * 赠品数量
   */
  @Schema(description = "赠品数量")
  private BigDecimal giftNum;

  /**
   * 销售金额
   */
  @Schema(description = "销售金额")
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
   * 订单明细
   */
  @Schema(description = "订单明细")
  private List<OrderDetailBo> details;

  public GetSaleOrderBo() {

  }

  public GetSaleOrderBo(SaleOrderFullDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<SaleOrderFullDto> convert(SaleOrderFullDto dto) {

    return super.convert(dto, GetSaleOrderBo::getStatus, GetSaleOrderBo::getDetails);
  }

  @Override
  protected void afterInit(SaleOrderFullDto dto) {

    StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
    this.scName = storeCenterService.findById(dto.getScId()).getName();

    CustomerService customerService = ApplicationUtil.getBean(CustomerService.class);
    this.customerName = customerService.findById(dto.getCustomerId()).getName();

    SysUserService userService = ApplicationUtil.getBean(SysUserService.class);
    if (!StringUtil.isBlank(dto.getSalerId())) {
      this.salerName = userService.findById(dto.getSalerId()).getName();
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
  public static class OrderDetailBo extends BaseBo<SaleOrderFullDto.OrderDetailDto> {

    /**
     * 明细ID
     */
    @Schema(description = "明细ID")
    private String id;

    /**
     * 组合商品ID
     */
    @Schema(description = "组合商品ID")
    private String mainProductId;

    /**
     * 组合商品名称
     */
    @Schema(description = "组合商品名称")
    private String mainProductName;

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
     * 销售数量
     */
    @Schema(description = "销售数量")
    private BigDecimal orderNum;

    /**
     * 原价
     */
    @Schema(description = "原价")
    private BigDecimal oriPrice;

    /**
     * 现价
     */
    @Schema(description = "现价")
    private BigDecimal taxPrice;

    /**
     * 折扣（%）
     */
    @Schema(description = "折扣（%）")
    private BigDecimal discountRate;

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
     * 仓库ID
     */
    @Schema(description = "仓库ID", hidden = true)
    @JsonIgnore
    private String scId;

    /**
     * 库存数量
     */
    @Schema(description = "库存数量")
    private BigDecimal stockNum;

    public OrderDetailBo(String scId, SaleOrderFullDto.OrderDetailDto dto) {

      this.scId = scId;
      this.init(dto);
    }

    @Override
    public BaseBo<SaleOrderFullDto.OrderDetailDto> convert(SaleOrderFullDto.OrderDetailDto dto) {

      return super.convert(dto);
    }

    @Override
    protected void afterInit(SaleOrderFullDto.OrderDetailDto dto) {

      this.orderNum = dto.getOrderNum();
      this.oriPrice = dto.getOriPrice();
      this.taxPrice = dto.getTaxPrice();
      this.discountRate = dto.getDiscountRate();

      SaleOrderService saleOrderService = ApplicationUtil.getBean(SaleOrderService.class);
      SaleProductDto product = saleOrderService.getSaleById(dto.getSkuId());

      this.productId = product.getProductId();
      this.skuId = product.getSkuId();
      this.productCode = product.getCode();
      this.skuCode = product.getSkuCode();
      this.salePropertyText = product.getSalePropertyText();
      this.productName = product.getName();
      this.unit = product.getUnit();
      this.spec = product.getSpec();
      this.categoryName = product.getCategoryName();
      this.brandName = product.getBrandName();

      ProductStockService productStockService = ApplicationUtil.getBean(
          ProductStockService.class);
      ProductStock productStock = productStockService.getBySkuIdAndScId(
          this.getSkuId(), this.getScId());
      this.stockNum = productStock == null ? BigDecimal.ZERO : productStock.getStockNum();

      if (StringUtil.isNotBlank(dto.getMainProductId())) {
        ProductService productService = ApplicationUtil.getBean(ProductService.class);
        Product mainProduct = productService.findById(dto.getMainProductId());
        this.mainProductId = dto.getMainProductId();
        this.mainProductName = mainProduct.getName();
      }
    }
  }
}
