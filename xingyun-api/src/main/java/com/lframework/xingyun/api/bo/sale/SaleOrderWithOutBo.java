package com.lframework.xingyun.api.bo.sale;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lframework.common.functions.SFunction;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.NumberUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.customer.CustomerDto;
import com.lframework.xingyun.basedata.dto.product.info.SaleProductDto;
import com.lframework.xingyun.basedata.dto.storecenter.StoreCenterDto;
import com.lframework.xingyun.basedata.service.customer.ICustomerService;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.sc.dto.sale.SaleOrderWithOutDto;
import com.lframework.xingyun.sc.dto.stock.ProductStockDto;
import com.lframework.xingyun.sc.service.stock.IProductStockService;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SaleOrderWithOutBo extends BaseBo<SaleOrderWithOutDto> {

  /**
   * 订单ID
   */
  @ApiModelProperty("订单ID")
  private String id;

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
   * 订单明细
   */
  @ApiModelProperty("订单明细")
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

    IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
    StoreCenterDto sc = storeCenterService.getById(dto.getScId());
    this.scName = sc.getName();

    ICustomerService customerService = ApplicationUtil.getBean(ICustomerService.class);
    CustomerDto customer = customerService.getById(dto.getCustomerId());
    this.customerName = customer.getName();

    if (!StringUtil.isBlank(dto.getSalerId())) {
      IUserService userService = ApplicationUtil.getBean(IUserService.class);
      UserDto saler = userService.getById(dto.getSalerId());

      this.salerId = saler.getId();
      this.salerName = saler.getName();
    }

    if (!CollectionUtil.isEmpty(dto.getDetails())) {
      this.details = dto.getDetails().stream().map(t -> new DetailBo(this.getScId(), t))
          .collect(Collectors.toList());
    }
  }

  @Data
  @EqualsAndHashCode(callSuper = true)
  public static class DetailBo extends BaseBo<SaleOrderWithOutDto.DetailDto> {

    /**
     * ID
     */
    @ApiModelProperty("ID")
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
     * 销售价
     */
    @ApiModelProperty("销售价")
    private BigDecimal salePrice;

    /**
     * 价格
     */
    @ApiModelProperty("价格")
    private BigDecimal taxPrice;

    /**
     * 折扣
     */
    @ApiModelProperty("折扣")
    private BigDecimal discountRate;

    /**
     * 剩余出库数量
     */
    @ApiModelProperty("剩余出库数量")
    private Integer remainNum;

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
     * 税率（%）
     */
    @ApiModelProperty("税率（%）")
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

    public DetailBo() {

    }

    public DetailBo(String scId, SaleOrderWithOutDto.DetailDto dto) {

      this.scId = scId;
      if (dto != null) {
        this.convert(dto);

        this.afterInit(dto);
      }
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

      IProductService productService = ApplicationUtil.getBean(IProductService.class);
      SaleProductDto product = productService.getSaleById(dto.getProductId());

      this.id = dto.getId();
      this.productId = product.getId();
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

      this.orderNum = dto.getOrderNum();
      this.salePrice = dto.getOriPrice();
      this.taxPrice = dto.getTaxPrice();
      this.discountRate = dto.getDiscountRate();
      this.remainNum = NumberUtil.sub(dto.getOrderNum(), dto.getOutNum()).intValue();
      this.isGift = dto.getIsGift();
      this.taxRate = dto.getTaxRate();
      this.description = dto.getDescription();

      IProductStockService productStockService = ApplicationUtil
          .getBean(IProductStockService.class);
      ProductStockDto productStock = productStockService
          .getByProductIdAndScId(this.getProductId(), this.getScId());
      this.stockNum = productStock == null ? 0 : productStock.getStockNum();
    }
  }
}
