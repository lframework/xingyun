package com.lframework.xingyun.sc.bo.sale;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lframework.starter.common.functions.SFunction;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Customer;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.customer.CustomerService;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.starter.web.inner.entity.SysUser;
import com.lframework.xingyun.sc.dto.sale.SaleOrderWithOutDto;
import com.lframework.xingyun.sc.dto.sale.SaleProductDto;
import com.lframework.xingyun.sc.entity.ProductStock;
import com.lframework.xingyun.sc.service.sale.SaleOrderService;
import com.lframework.xingyun.sc.service.stock.ProductStockService;
import com.lframework.starter.web.inner.service.system.SysUserService;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class SaleOrderWithOutBo extends BaseBo<SaleOrderWithOutDto> {

  /**
   * 订单ID
   */
  @Schema(description = "订单ID")
  private String id;

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
   * 订单明细
   */
  @Schema(description = "订单明细")
  private List<DetailBo> details;

  public SaleOrderWithOutBo() {

  }

  public SaleOrderWithOutBo(SaleOrderWithOutDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<SaleOrderWithOutDto> convert(SaleOrderWithOutDto dto) {

    return super.convert(dto, SaleOrderWithOutBo::getDetails);
  }

  @Override
  protected void afterInit(SaleOrderWithOutDto dto) {

    StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
    StoreCenter sc = storeCenterService.findById(dto.getScId());
    this.scName = sc.getName();

    CustomerService customerService = ApplicationUtil.getBean(CustomerService.class);
    Customer customer = customerService.findById(dto.getCustomerId());
    this.customerName = customer.getName();

    if (!StringUtil.isBlank(dto.getSalerId())) {
      SysUserService userService = ApplicationUtil.getBean(SysUserService.class);
      SysUser saler = userService.findById(dto.getSalerId());

      this.salerId = saler.getId();
      this.salerName = saler.getName();
    }

    if (!CollectionUtil.isEmpty(dto.getDetails())) {
      this.details = dto.getDetails().stream().map(t -> new DetailBo(this.getScId(), t))
          .collect(Collectors.toList());
    }
  }

  @Data
  public static class DetailBo extends BaseBo<SaleOrderWithOutDto.DetailDto> {

    /**
     * ID
     */
    @Schema(description = "ID")
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
     * 销售价
     */
    @Schema(description = "销售价")
    private BigDecimal salePrice;

    /**
     * 价格
     */
    @Schema(description = "价格")
    private BigDecimal taxPrice;

    /**
     * 折扣
     */
    @Schema(description = "折扣")
    private BigDecimal discountRate;

    /**
     * 剩余出库数量
     */
    @Schema(description = "剩余出库数量")
    private BigDecimal remainNum;

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
     * 税率（%）
     */
    @Schema(description = "税率（%）")
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

    public DetailBo() {

    }

    public DetailBo(String scId, SaleOrderWithOutDto.DetailDto dto) {

      this.scId = scId;
      this.init(dto);
    }

    @Override
    public BaseBo<SaleOrderWithOutDto.DetailDto> convert(SaleOrderWithOutDto.DetailDto dto) {

      return this;
    }

    @Override
    public <A> BaseBo<SaleOrderWithOutDto.DetailDto> convert(SaleOrderWithOutDto.DetailDto dto,
        SFunction<A, ?>... columns) {

      return this;
    }

    @Override
    protected void afterInit(SaleOrderWithOutDto.DetailDto dto) {

      SaleOrderService saleOrderService = ApplicationUtil.getBean(SaleOrderService.class);
      SaleProductDto product = saleOrderService.getSaleById(dto.getSkuId());

      this.id = dto.getId();
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

      this.orderNum = dto.getOrderNum();
      this.salePrice = dto.getOriPrice();
      this.taxPrice = dto.getTaxPrice();
      this.discountRate = dto.getDiscountRate();
      this.remainNum = NumberUtil.sub(dto.getOrderNum(), dto.getOutNum());
      this.isGift = dto.getIsGift();
      this.taxRate = dto.getTaxRate();
      this.description = dto.getDescription();

      ProductStockService productStockService = ApplicationUtil.getBean(ProductStockService.class);
      ProductStock productStock = productStockService.getBySkuIdAndScId(this.getSkuId(),
          this.getScId());
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
