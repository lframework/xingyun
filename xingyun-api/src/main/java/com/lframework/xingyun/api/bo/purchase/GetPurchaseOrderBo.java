package com.lframework.xingyun.api.bo.purchase;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.NumberUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.service.IUserService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.product.info.PurchaseProductDto;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.basedata.service.supplier.ISupplierService;
import com.lframework.xingyun.sc.dto.purchase.PurchaseOrderFullDto;
import com.lframework.xingyun.sc.dto.stock.ProductStockDto;
import com.lframework.xingyun.sc.service.stock.IProductStockService;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
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

    IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
    this.scName = storeCenterService.getById(dto.getScId()).getName();

    ISupplierService supplierService = ApplicationUtil.getBean(ISupplierService.class);
    this.supplierName = supplierService.getById(dto.getSupplierId()).getName();

    IUserService userService = ApplicationUtil.getBean(IUserService.class);
    if (!StringUtil.isBlank(dto.getPurchaserId())) {
      this.purchaserName = userService.getById(dto.getPurchaserId()).getName();
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
      if (dto != null) {
        this.convert(dto);

        this.afterInit(dto);
      }
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

      IProductService productService = ApplicationUtil.getBean(IProductService.class);
      PurchaseProductDto product = productService.getPurchaseById(dto.getProductId());

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

      IProductStockService productStockService = ApplicationUtil.getBean(
          IProductStockService.class);
      ProductStockDto productStock = productStockService.getByProductIdAndScId(this.getProductId(),
          this.getScId());
      this.taxCostPrice = productStock == null ? BigDecimal.ZERO
          : NumberUtil.getNumber(productStock.getTaxPrice(), 2);
      this.stockNum = productStock == null ? 0 : productStock.getStockNum();
    }
  }
}
