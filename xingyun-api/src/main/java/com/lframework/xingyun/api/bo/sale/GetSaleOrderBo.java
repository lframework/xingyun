package com.lframework.xingyun.api.bo.sale;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.service.IUserService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.product.info.SaleProductDto;
import com.lframework.xingyun.basedata.service.customer.ICustomerService;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.sc.dto.sale.SaleOrderFullDto;
import com.lframework.xingyun.sc.dto.stock.ProductStockDto;
import com.lframework.xingyun.sc.service.stock.IProductStockService;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetSaleOrderBo extends BaseBo<SaleOrderFullDto> {

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
   * 客户ID
   */
  @ApiModelProperty("客户ID")
  private String customerId;

  /**
   * 客户名称
   */
  @ApiModelProperty("客户名称")
  private String customerName;

  /**
   * 销售员ID
   */
  @ApiModelProperty("销售员ID")
  private String salerId;

  /**
   * 销售员姓名
   */
  @ApiModelProperty("销售员姓名")
  private String salerName;

  /**
   * 销售数量
   */
  @ApiModelProperty("销售数量")
  private Integer totalNum;

  /**
   * 赠品数量
   */
  @ApiModelProperty("赠品数量")
  private Integer giftNum;

  /**
   * 销售金额
   */
  @ApiModelProperty("销售金额")
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
   * 订单明细
   */
  @ApiModelProperty("订单明细")
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

    IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
    this.scName = storeCenterService.getById(dto.getScId()).getName();

    ICustomerService customerService = ApplicationUtil.getBean(ICustomerService.class);
    this.customerName = customerService.getById(dto.getCustomerId()).getName();

    IUserService userService = ApplicationUtil.getBean(IUserService.class);
    if (!StringUtil.isBlank(dto.getSalerId())) {
      this.salerName = userService.getById(dto.getSalerId()).getName();
    }

    this.createBy = userService.getById(dto.getCreateBy()).getName();

    if (!StringUtil.isBlank(dto.getApproveBy())) {
      this.approveBy = userService.getById(dto.getApproveBy()).getName();
    }

    this.status = dto.getStatus().getCode();

    this.totalNum = dto.getTotalNum();
    this.giftNum = dto.getTotalGiftNum();
    this.totalAmount = dto.getTotalAmount();

    if (!CollectionUtil.isEmpty(dto.getDetails())) {
      this.details = dto.getDetails().stream().map(t -> new OrderDetailBo(this.getScId(), t))
          .collect(Collectors.toList());
    }
  }

  @Data
  @EqualsAndHashCode(callSuper = true)
  public static class OrderDetailBo extends BaseBo<SaleOrderFullDto.OrderDetailDto> {

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
     * 外部编号
     */
    @ApiModelProperty("外部编号")
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
     * 类目名称
     */
    @ApiModelProperty("类目名称")
    private String categoryName;

    /**
     * 品牌名称
     */
    @ApiModelProperty("品牌名称")
    private String brandName;

    /**
     * 销售属性1
     */
    @ApiModelProperty("销售属性1")
    private String salePropItemName1;

    /**
     * 销售属性2
     */
    @ApiModelProperty("销售属性2")
    private String salePropItemName2;

    /**
     * 销售数量
     */
    @ApiModelProperty("销售数量")
    private Integer orderNum;

    /**
     * 原价
     */
    @ApiModelProperty("原价")
    private BigDecimal oriPrice;

    /**
     * 现价
     */
    @ApiModelProperty("现价")
    private BigDecimal taxPrice;

    /**
     * 折扣（%）
     */
    @ApiModelProperty("折扣（%）")
    private BigDecimal discountRate;

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

    /**
     * 库存数量
     */
    @ApiModelProperty("库存数量")
    private Integer stockNum;

    public OrderDetailBo(String scId, SaleOrderFullDto.OrderDetailDto dto) {

      this.scId = scId;
      if (dto != null) {
        this.convert(dto);

        this.afterInit(dto);
      }
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

      IProductService productService = ApplicationUtil.getBean(IProductService.class);
      SaleProductDto product = productService.getSaleById(dto.getProductId());

      this.productCode = product.getCode();
      this.productName = product.getName();
      this.skuCode = product.getSkuCode();
      this.externalCode = product.getExternalCode();
      this.unit = product.getUnit();
      this.spec = product.getSpec();
      this.categoryName = product.getCategoryName();
      this.brandName = product.getBrandName();
      if (!CollectionUtil.isEmpty(product.getSaleProps())) {
        if (product.getSaleProps().size() > 0) {
          this.salePropItemName1 = product.getSaleProps().get(0).getName();
        }

        if (product.getSaleProps().size() > 1) {
          this.salePropItemName2 = product.getSaleProps().get(1).getName();
        }
      }

      IProductStockService productStockService = ApplicationUtil
          .getBean(IProductStockService.class);
      ProductStockDto productStock = productStockService
          .getByProductIdAndScId(this.getProductId(), this.getScId());
      this.stockNum = productStock == null ? 0 : productStock.getStockNum();
    }
  }
}
