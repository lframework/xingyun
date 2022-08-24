package com.lframework.xingyun.sc.api.bo.retail.out;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lframework.common.functions.SFunction;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.NumberUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.facade.MemberFeignClient;
import com.lframework.xingyun.basedata.facade.ProductFeignClient;
import com.lframework.xingyun.basedata.facade.StoreCenterFeignClient;
import com.lframework.xingyun.basedata.facade.SupplierFeignClient;
import com.lframework.xingyun.basedata.facade.dto.product.info.RetailProductDto;
import com.lframework.xingyun.basedata.facade.entity.Member;
import com.lframework.xingyun.basedata.facade.entity.StoreCenter;
import com.lframework.xingyun.sc.biz.service.stock.IProductLotService;
import com.lframework.xingyun.sc.biz.service.stock.IProductStockService;
import com.lframework.xingyun.sc.facade.dto.retail.out.RetailOutSheetWithReturnDto;
import com.lframework.xingyun.sc.facade.entity.ProductLot;
import com.lframework.xingyun.sc.facade.entity.ProductStock;
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

    StoreCenterFeignClient storeCenterFeignClient = ApplicationUtil.getBean(
        StoreCenterFeignClient.class);
    StoreCenter sc = storeCenterFeignClient.findById(dto.getScId()).getData();
    this.scName = sc.getName();

    if (!StringUtil.isBlank(dto.getMemberId())) {
      MemberFeignClient memberFeignClient = ApplicationUtil.getBean(MemberFeignClient.class);
      Member member = memberFeignClient.findById(dto.getMemberId()).getData();
      this.memberName = member.getName();
    }

    if (!StringUtil.isBlank(dto.getSalerId())) {
      IUserService userService = ApplicationUtil.getBean(IUserService.class);
      UserDto saler = userService.findById(dto.getSalerId());

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

      ProductFeignClient productFeignClient = ApplicationUtil.getBean(ProductFeignClient.class);
      RetailProductDto product = productFeignClient.getRetailById(dto.getProductId()).getData();

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
      if (product.getSaleProps() != null) {
        this.salePropItemName1 = product.getSaleProps().getItemName1();
        this.salePropItemName2 = product.getSaleProps().getItemName2();
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
      ProductLot lot = productLotService.findById(dto.getLotId());

      SupplierFeignClient supplierFeignClient = ApplicationUtil.getBean(SupplierFeignClient.class);
      this.supplierId = lot.getSupplierId();
      this.supplierName = supplierFeignClient.findById(lot.getSupplierId()).getData().getName();

      IProductStockService productStockService = ApplicationUtil.getBean(
          IProductStockService.class);
      ProductStock productStock = productStockService.getByProductIdAndScId(this.getProductId(),
          this.getScId());
      this.stockNum = productStock == null ? 0 : productStock.getStockNum();
    }
  }
}
