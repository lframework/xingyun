package com.lframework.xingyun.api.bo.retail.out;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lframework.common.functions.SFunction;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.NumberUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.service.IUserService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.member.MemberDto;
import com.lframework.xingyun.basedata.dto.product.info.RetailProductDto;
import com.lframework.xingyun.basedata.dto.storecenter.StoreCenterDto;
import com.lframework.xingyun.basedata.service.member.IMemberService;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.basedata.service.supplier.ISupplierService;
import com.lframework.xingyun.sc.dto.retail.out.RetailOutSheetWithReturnDto;
import com.lframework.xingyun.sc.dto.stock.ProductLotDto;
import com.lframework.xingyun.sc.dto.stock.ProductStockDto;
import com.lframework.xingyun.sc.service.stock.IProductLotService;
import com.lframework.xingyun.sc.service.stock.IProductStockService;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RetailOutSheetWithReturnBo extends BaseBo<RetailOutSheetWithReturnDto> {

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
   * 会员ID
   */
  @ApiModelProperty("会员ID")
  private String memberId;

  /**
   * 会员名称
   */
  @ApiModelProperty("会员名称")
  private String memberName;

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

  public RetailOutSheetWithReturnBo() {

  }

  public RetailOutSheetWithReturnBo(RetailOutSheetWithReturnDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<RetailOutSheetWithReturnDto> convert(RetailOutSheetWithReturnDto dto) {

    return super.convert(dto, RetailOutSheetWithReturnBo::getSalerId,
        RetailOutSheetWithReturnBo::getDetails);
  }

  @Override
  protected void afterInit(RetailOutSheetWithReturnDto dto) {

    IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
    StoreCenterDto sc = storeCenterService.getById(dto.getScId());
    this.scName = sc.getName();

    IMemberService memberService = ApplicationUtil.getBean(IMemberService.class);
    MemberDto member = memberService.getById(dto.getMemberId());
    this.memberName = member.getName();

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
  public static class DetailBo extends BaseBo<RetailOutSheetWithReturnDto.SheetDetailDto> {

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
     * 出库数量
     */
    @ApiModelProperty("出库数量")
    private Integer outNum;

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
     * 原价
     */
    @ApiModelProperty("原价")
    private BigDecimal retailPrice;

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
     * 库存数量
     */
    @ApiModelProperty("库存数量")
    private Integer stockNum;

    /**
     * 剩余退货数量
     */
    @ApiModelProperty("剩余退货数量")
    private Integer remainNum;

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

    public DetailBo(String scId, RetailOutSheetWithReturnDto.SheetDetailDto dto) {

      this.scId = scId;

      if (dto != null) {
        this.convert(dto);

        this.afterInit(dto);
      }
    }

    @Override
    public BaseBo<RetailOutSheetWithReturnDto.SheetDetailDto> convert(
        RetailOutSheetWithReturnDto.SheetDetailDto dto) {

      return this;
    }

    @Override
    public <A> BaseBo<RetailOutSheetWithReturnDto.SheetDetailDto> convert(
        RetailOutSheetWithReturnDto.SheetDetailDto dto, SFunction<A, ?>... columns) {

      return this;
    }

    @Override
    protected void afterInit(RetailOutSheetWithReturnDto.SheetDetailDto dto) {

      IProductService productService = ApplicationUtil.getBean(IProductService.class);
      RetailProductDto product = productService.getRetailById(dto.getProductId());

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

      this.outNum = dto.getOrderNum();
      this.retailPrice = dto.getOriPrice();
      this.taxPrice = dto.getTaxPrice();
      this.discountRate = dto.getDiscountRate();
      this.remainNum = NumberUtil.sub(dto.getOrderNum(), dto.getReturnNum()).intValue();
      this.isGift = dto.getIsGift();
      this.taxRate = dto.getTaxRate();
      this.description = dto.getDescription();

      IProductLotService productLotService = ApplicationUtil.getBean(IProductLotService.class);
      ProductLotDto lot = productLotService.getById(dto.getLotId());

      ISupplierService supplierService = ApplicationUtil.getBean(ISupplierService.class);
      this.supplierId = lot.getSupplierId();
      this.supplierName = supplierService.getById(lot.getSupplierId()).getName();

      IProductStockService productStockService = ApplicationUtil
          .getBean(IProductStockService.class);
      ProductStockDto productStock = productStockService
          .getByProductIdAndScId(this.getProductId(), this.getScId());
      this.stockNum = productStock == null ? 0 : productStock.getStockNum();
    }
  }
}
