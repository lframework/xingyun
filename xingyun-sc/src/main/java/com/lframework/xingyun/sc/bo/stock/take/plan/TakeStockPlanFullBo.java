package com.lframework.xingyun.sc.bo.stock.take.plan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.ProductBrand;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.product.ProductBrandService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryService;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.sc.dto.stock.take.plan.TakeStockPlanFullDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class TakeStockPlanFullBo extends BaseBo<TakeStockPlanFullDto> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 业务单据号
   */
  @Schema(description = "业务单据号")
  private String code;

  /**
   * 仓库名称
   */
  @Schema(description = "仓库名称")
  private String scName;

  /**
   * 盘点类别
   */
  @Schema(description = "盘点类别")
  private Integer takeType;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  /**
   * 盘点状态
   */
  @Schema(description = "盘点状态")
  private Integer takeStatus;

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
   * 修改人
   */
  @Schema(description = "修改人")
  private String updateBy;

  /**
   * 修改时间
   */
  @Schema(description = "修改时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime updateTime;

  /**
   * 明细
   */
  @Schema(description = "明细")
  private List<DetailBo> details;

  public TakeStockPlanFullBo(TakeStockPlanFullDto dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<TakeStockPlanFullDto> convert(TakeStockPlanFullDto dto) {

    return super.convert(dto, TakeStockPlanFullBo::getTakeType, TakeStockPlanFullBo::getTakeStatus,
        TakeStockPlanFullBo::getDetails);
  }

  @Override
  protected void afterInit(TakeStockPlanFullDto dto) {

    this.takeType = dto.getTakeType().getCode();
    this.takeStatus = dto.getTakeStatus().getCode();

    StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
    StoreCenter sc = storeCenterService.findById(dto.getScId());
    this.scName = sc.getName();

    this.details = CollectionUtil.isEmpty(dto.getDetails()) ?
        CollectionUtil.emptyList() :
        dto.getDetails().stream().map(t -> new DetailBo(t, this.id)).collect(Collectors.toList());
  }

  @Data
  public static class DetailBo extends BaseBo<TakeStockPlanFullDto.DetailDto> {

    /**
     * 明细ID
     */
    @Schema(description = "明细ID")
    private String id;

    /**
     * 商品ID
     */
    @Schema(description = "商品ID")
    private String productId;

    /**
     * 商品编号
     */
    @Schema(description = "商品编号")
    private String productCode;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String productName;

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
     * 规格
     */
    @Schema(description = "规格")
    private String spec;

    /**
     * 单位
     */
    @Schema(description = "单位")
    private String unit;

    /**
     * 库存数量
     */
    @Schema(description = "库存数量")
    private BigDecimal stockNum;

    /**
     * 原盘点数量（通过盘点单统计）
     */
    @Schema(description = "原盘点数量（通过盘点单统计）")
    private BigDecimal oriTakeNum;

    /**
     * 修改后的盘点数量
     */
    @Schema(description = "修改后的盘点数量")
    private BigDecimal takeNum;

    /**
     * 出项数量
     */
    @Schema(description = "出项数量")
    private BigDecimal totalOutNum;

    /**
     * 进项数量
     */
    @Schema(description = "进项数量")
    private BigDecimal totalInNum;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String description;

    /**
     * 差异数量
     */
    @Schema(description = "差异数量")
    private BigDecimal diffNum;

    /**
     * 盘点任务ID
     */
    @Schema(description = "盘点任务ID", hidden = true)
    @JsonIgnore
    private String planId;

    public DetailBo(TakeStockPlanFullDto.DetailDto dto, String planId) {

      this.planId = planId;

      this.init(dto);
    }

    @Override
    public <A> BaseBo<TakeStockPlanFullDto.DetailDto> convert(TakeStockPlanFullDto.DetailDto dto) {

      return super.convert(dto);
    }

    @Override
    protected void afterInit(TakeStockPlanFullDto.DetailDto dto) {

      ProductService productService = ApplicationUtil.getBean(ProductService.class);
      Product product = productService.findById(dto.getProductId());

      ProductCategoryService productCategoryService = ApplicationUtil.getBean(
          ProductCategoryService.class);
      ProductCategory productCategory = productCategoryService.findById(product.getCategoryId());

      if(StringUtil.isNotBlank(product.getBrandId())) {
        ProductBrandService productBrandService = ApplicationUtil.getBean(ProductBrandService.class);
        ProductBrand productBrand = productBrandService.findById(product.getBrandId());
        this.brandName = productBrand.getName();
      }

      this.productCode = product.getCode();
      this.productName = product.getName();
      this.categoryName = productCategory.getName();
      this.spec = product.getSpec();
      this.unit = product.getUnit();

      if (this.oriTakeNum != null || this.takeNum != null) {

        if (this.takeNum != null) {
          this.diffNum = NumberUtil.sub(this.takeNum, this.stockNum);
        } else {
          this.diffNum = NumberUtil.sub(this.oriTakeNum, this.stockNum);
        }
      }
    }
  }
}
