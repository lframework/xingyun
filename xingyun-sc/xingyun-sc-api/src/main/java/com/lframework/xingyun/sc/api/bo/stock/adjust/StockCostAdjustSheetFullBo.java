package com.lframework.xingyun.sc.api.bo.stock.adjust;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.NumberUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.xingyun.basedata.facade.ProductFeignClient;
import com.lframework.xingyun.basedata.facade.StoreCenterFeignClient;
import com.lframework.xingyun.basedata.facade.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.facade.entity.StoreCenter;
import com.lframework.xingyun.sc.biz.service.stock.IProductStockService;
import com.lframework.xingyun.sc.facade.dto.stock.adjust.StockCostAdjustSheetFullDto;
import com.lframework.xingyun.sc.facade.entity.ProductStock;
import com.lframework.xingyun.sc.facade.enums.StockCostAdjustSheetStatus;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 库存成本调整单 GetBo
 * </p>
 *
 * @author zmj
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StockCostAdjustSheetFullBo extends BaseBo<StockCostAdjustSheetFullDto> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 业务单据号
   */
  @ApiModelProperty("业务单据号")
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
   * 调价品种数
   */
  @ApiModelProperty("调价品种数")
  private Integer productNum;

  /**
   * 库存调价差额
   */
  @ApiModelProperty("库存调价差额")
  private BigDecimal diffAmount;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Integer status;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 修改人
   */
  @ApiModelProperty("修改人")
  private String updateBy;

  /**
   * 修改时间
   */
  @ApiModelProperty("修改时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime updateTime;

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
   * 拒绝原因
   */
  @ApiModelProperty("拒绝原因")
  private String refuseReason;

  /**
   * 明细
   */
  @ApiModelProperty("明细")
  private List<DetailBo> details;

  public StockCostAdjustSheetFullBo(StockCostAdjustSheetFullDto dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<StockCostAdjustSheetFullDto> convert(StockCostAdjustSheetFullDto dto) {

    return super.convert(dto, StockCostAdjustSheetFullBo::getStatus);
  }

  @Override
  protected void afterInit(StockCostAdjustSheetFullDto dto) {

    this.status = dto.getStatus().getCode();

    StoreCenterFeignClient storeCenterFeignClient = ApplicationUtil.getBean(
        StoreCenterFeignClient.class);
    StoreCenter sc = storeCenterFeignClient.findById(dto.getScId()).getData();
    this.scName = sc.getName();

    IUserService userService = ApplicationUtil.getBean(IUserService.class);
    this.updateBy = userService.findById(dto.getUpdateBy()).getName();
    if (!StringUtil.isBlank(dto.getApproveBy())) {
      this.approveBy = userService.findById(dto.getApproveBy()).getName();
    }

    this.details = dto.getDetails().stream().map(t -> new DetailBo(t, this.scId, this.status))
        .collect(Collectors.toList());
  }

  @Data
  @EqualsAndHashCode(callSuper = true)
  public static class DetailBo extends BaseBo<StockCostAdjustSheetFullDto.DetailDto> {

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
     * 编号
     */
    @ApiModelProperty("编号")
    private String productCode;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String productName;

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
     * SKU
     */
    @ApiModelProperty("SKU")
    private String skuCode;

    /**
     * 外部编号
     */
    @ApiModelProperty("外部编号")
    private String externalCode;

    /**
     * 规格
     */
    @ApiModelProperty("规格")
    private String spec;

    /**
     * 单位
     */
    @ApiModelProperty("单位")
    private String unit;

    /**
     * 档案采购价
     */
    @ApiModelProperty("档案采购价")
    private BigDecimal purchasePrice;

    /**
     * 库存数量
     */
    @ApiModelProperty("库存数量")
    private Integer stockNum;

    /**
     * 调整前成本价
     */
    @ApiModelProperty("调整前成本价")
    private BigDecimal oriPrice;

    /**
     * 调整后成本价
     */
    @ApiModelProperty("调整后成本价")
    private BigDecimal price;

    /**
     * 库存调价差额
     */
    @ApiModelProperty("库存调价差额")
    private BigDecimal diffAmount;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String description;

    /**
     * 仓库ID
     */
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private String scId;

    /**
     * 状态
     */
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private Integer status;

    public DetailBo(StockCostAdjustSheetFullDto.DetailDto dto, String scId, Integer status) {

      this.scId = scId;
      this.status = status;

      if (dto != null) {
        this.convert(dto);
        this.afterInit(dto);
      }
    }

    @Override
    public <A> BaseBo<StockCostAdjustSheetFullDto.DetailDto> convert(
        StockCostAdjustSheetFullDto.DetailDto dto) {

      return super.convert(dto);
    }

    @Override
    protected void afterInit(StockCostAdjustSheetFullDto.DetailDto dto) {

      ProductFeignClient productFeignClient = ApplicationUtil.getBean(ProductFeignClient.class);

      ProductDto product = productFeignClient.findById(dto.getProductId()).getData();

      this.productCode = product.getCode();
      this.productName = product.getName();
      this.brandName = product.getPoly().getBrandName();
      this.categoryName = product.getPoly().getCategoryName();
      this.skuCode = product.getSkuCode();
      this.externalCode = product.getExternalCode();
      this.spec = product.getSpec();
      this.unit = product.getUnit();

      if (EnumUtil.getByCode(StockCostAdjustSheetStatus.class, this.status)
          != StockCostAdjustSheetStatus.APPROVE_PASS) {
        IProductStockService productStockService = ApplicationUtil.getBean(
            IProductStockService.class);
        ProductStock productStock = productStockService.getByProductIdAndScId(dto.getProductId(),
            this.scId);
        this.stockNum = productStock == null ? 0 : productStock.getStockNum();
        this.oriPrice = productStock == null ? BigDecimal.ZERO
            : NumberUtil.getNumber(productStock.getTaxPrice(), 2);
        this.diffAmount = NumberUtil.getNumber(
            NumberUtil.mul(NumberUtil.sub(this.price, this.oriPrice), this.stockNum), 2);
      }
    }
  }
}
