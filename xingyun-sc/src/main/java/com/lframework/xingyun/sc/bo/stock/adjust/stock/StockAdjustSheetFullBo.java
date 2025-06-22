package com.lframework.xingyun.sc.bo.stock.adjust.stock;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.annotations.convert.EnumConvert;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.utils.EnumUtil;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.ProductBrand;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.product.ProductBrandService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryService;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.sc.dto.stock.adjust.stock.StockAdjustSheetFullDto;
import com.lframework.xingyun.sc.entity.ProductStock;
import com.lframework.xingyun.sc.entity.StockAdjustReason;
import com.lframework.xingyun.sc.enums.StockAdjustSheetStatus;
import com.lframework.xingyun.sc.service.stock.ProductStockService;
import com.lframework.xingyun.sc.service.stock.adjust.StockAdjustReasonService;
import com.lframework.starter.web.inner.service.system.SysUserService;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

/**
 * <p>
 * 库存调整单 GetBo
 * </p>
 *
 * @author zmj
 */
@Data
public class StockAdjustSheetFullBo extends BaseBo<StockAdjustSheetFullDto> {

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
   * 业务类型
   */
  @ApiModelProperty("业务类型")
  @EnumConvert
  private Integer bizType;

  /**
   * 调整原因ID
   */
  @ApiModelProperty("调整原因ID")
  private String reasonId;

  /**
   * 调整原因
   */
  @ApiModelProperty("调整原因")
  private String reasonName;

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

  public StockAdjustSheetFullBo(StockAdjustSheetFullDto dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<StockAdjustSheetFullDto> convert(StockAdjustSheetFullDto dto) {

    return super.convert(dto, StockAdjustSheetFullBo::getStatus);
  }

  @Override
  protected void afterInit(StockAdjustSheetFullDto dto) {

    this.status = dto.getStatus().getCode();

    StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
    StoreCenter sc = storeCenterService.findById(dto.getScId());
    this.scName = sc.getName();

    SysUserService userService = ApplicationUtil.getBean(SysUserService.class);
    if (!StringUtil.isBlank(dto.getApproveBy())) {
      this.approveBy = userService.findById(dto.getApproveBy()).getName();
    }

    StockAdjustReasonService stockAdjustReasonService = ApplicationUtil.getBean(StockAdjustReasonService.class);
    StockAdjustReason reason = stockAdjustReasonService.findById(dto.getReasonId());
    this.reasonName = reason.getName();

    this.details = dto.getDetails().stream().map(t -> new DetailBo(t, this.scId, this.status))
        .collect(Collectors.toList());
  }

  @Data
  public static class DetailBo extends BaseBo<StockAdjustSheetFullDto.DetailDto> {

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
     * SKU
     */
    @ApiModelProperty("SKU")
    private String skuCode;

    /**
     * 简码
     */
    @ApiModelProperty("简码")
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
     * 调整库存数量
     */
    @ApiModelProperty("调整库存数量")
    private Integer stockNum;

    /**
     * 当前库存数量
     */
    @ApiModelProperty("当前库存数量")
    private Integer curStockNum;

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

    public DetailBo(StockAdjustSheetFullDto.DetailDto dto, String scId, Integer status) {

      this.scId = scId;
      this.status = status;

      this.init(dto);
    }

    @Override
    public <A> BaseBo<StockAdjustSheetFullDto.DetailDto> convert(
        StockAdjustSheetFullDto.DetailDto dto) {

      return super.convert(dto);
    }

    @Override
    protected void afterInit(StockAdjustSheetFullDto.DetailDto dto) {

      ProductService productService = ApplicationUtil.getBean(ProductService.class);

      Product product = productService.findById(dto.getProductId());

      ProductCategoryService productCategoryService = ApplicationUtil.getBean(
          ProductCategoryService.class);
      ProductCategory productCategory = productCategoryService.findById(product.getCategoryId());

      ProductBrandService productBrandService = ApplicationUtil.getBean(ProductBrandService.class);
      ProductBrand productBrand = productBrandService.findById(product.getBrandId());

      this.productCode = product.getCode();
      this.productName = product.getName();
      this.brandName = productBrand.getName();
      this.categoryName = productCategory.getName();
      this.skuCode = product.getSkuCode();
      this.externalCode = product.getExternalCode();
      this.spec = product.getSpec();
      this.unit = product.getUnit();

      if (EnumUtil.getByCode(StockAdjustSheetStatus.class, this.status)
          != StockAdjustSheetStatus.APPROVE_PASS) {
        ProductStockService productStockService = ApplicationUtil.getBean(
            ProductStockService.class);
        ProductStock productStock = productStockService.getByProductIdAndScId(dto.getProductId(),
            this.scId);
        this.curStockNum = productStock == null ? 0 : productStock.getStockNum();
      }
    }
  }
}
