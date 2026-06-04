package com.lframework.xingyun.sc.bo.stock.take.sheet;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.ProductBrand;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.product.ProductBrandService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.sc.dto.stock.take.sheet.TakeStockSheetFullDto;
import com.lframework.xingyun.sc.entity.PreTakeStockSheet;
import com.lframework.xingyun.sc.entity.TakeStockConfig;
import com.lframework.xingyun.sc.entity.TakeStockPlan;
import com.lframework.xingyun.sc.enums.TakeStockPlanType;
import com.lframework.xingyun.sc.service.stock.take.PreTakeStockSheetService;
import com.lframework.xingyun.sc.service.stock.take.TakeStockConfigService;
import com.lframework.xingyun.sc.service.stock.take.TakeStockPlanService;
import com.lframework.starter.web.inner.service.system.SysUserService;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

/**
 * <p>
 * 盘点单详情 Bo
 * </p>
 *
 * @author zmj
 */
@Data
public class TakeStockSheetFullBo extends BaseBo<TakeStockSheetFullDto> {

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
   * 盘点任务ID
   */
  @Schema(description = "盘点任务ID")
  private String planId;

  /**
   * 盘点任务号
   */
  @Schema(description = "盘点任务号")
  private String planCode;

  /**
   * 预先盘点单ID
   */
  @Schema(description = "预先盘点单ID")
  private String preSheetId;

  /**
   * 预先盘点单号
   */
  @Schema(description = "预先盘点单号")
  private String preSheetCode;

  /**
   * 仓库名称
   */
  @Schema(description = "仓库名称")
  private String scName;

  /**
   * 盘点任务-盘点类别
   */
  @Schema(description = "盘点任务-盘点类别")
  private Integer takeType;

  /**
   * 业务名称
   */
  @Schema(description = "业务名称")
  private String bizName;

  /**
   * 盘点任务-盘点状态
   */
  @Schema(description = "盘点任务-盘点状态")
  private Integer takeStatus;

  /**
   * 状态
   */
  @Schema(description = "状态")
  private Integer status;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  /**
   * 拒绝理由
   */
  @Schema(description = "拒绝理由")
  private String refuseReason;

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
   * 明细
   */
  @Schema(description = "明细")
  private List<SheetDetailBo> details;

  public TakeStockSheetFullBo(TakeStockSheetFullDto dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<TakeStockSheetFullDto> convert(TakeStockSheetFullDto dto) {

    return super.convert(dto, TakeStockSheetFullBo::getTakeStatus, TakeStockSheetFullBo::getStatus);
  }

  @Override
  protected void afterInit(TakeStockSheetFullDto dto) {

    this.status = dto.getStatus().getCode();

    TakeStockPlanService takeStockPlanService = ApplicationUtil.getBean(TakeStockPlanService.class);
    TakeStockPlan plan = takeStockPlanService.getById(dto.getPlanId());
    this.planCode = plan.getCode();
    this.takeType = plan.getTakeType().getCode();
    this.takeStatus = plan.getTakeStatus().getCode();

    String bizId = plan.getBizId();
    if (plan.getTakeType() == TakeStockPlanType.CATEGORY) {
      ProductCategoryService productCategoryService = ApplicationUtil.getBean(
          ProductCategoryService.class);
      String[] categoryIds = bizId.split(",");
      StringBuilder builder = new StringBuilder();
      for (String categoryId : categoryIds) {
        ProductCategory productCategory = productCategoryService.findById(categoryId);
        builder.append(productCategory.getName()).append(StringPool.STR_SPLIT_CN);
      }

      if (builder.length() > 0) {
        builder.setLength(builder.length() - 1);
      }

      this.bizName = builder.toString();
    } else if (plan.getTakeType() == TakeStockPlanType.BRAND) {
      ProductBrandService productBrandService = ApplicationUtil.getBean(ProductBrandService.class);
      String[] brandIds = bizId.split(",");
      StringBuilder builder = new StringBuilder();
      for (String brandId : brandIds) {
        ProductBrand productBrand = productBrandService.findById(brandId);
        builder.append(productBrand.getName()).append(StringPool.STR_SPLIT_CN);
      }

      if (builder.length() > 0) {
        builder.setLength(builder.length() - 1);
      }

      this.bizName = builder.toString();
    }

    if (!StringUtil.isBlank(dto.getPreSheetId())) {
      PreTakeStockSheetService preTakeStockSheetService = ApplicationUtil.getBean(
          PreTakeStockSheetService.class);
      PreTakeStockSheet preSheet = preTakeStockSheetService.getById(dto.getPreSheetId());
      this.preSheetCode = preSheet.getCode();
    }

    StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
    StoreCenter sc = storeCenterService.findById(dto.getScId());
    this.scName = sc.getName();

    SysUserService userService = ApplicationUtil.getBean(SysUserService.class);
    if (!StringUtil.isBlank(this.approveBy)) {
      this.approveBy = userService.findById(this.approveBy).getName();
    }

    TakeStockConfigService takeStockConfigService = ApplicationUtil.getBean(
        TakeStockConfigService.class);
    TakeStockConfig config = takeStockConfigService.get();
    this.details = dto.getDetails().stream().map(t -> new SheetDetailBo(t, config.getShowStock()))
        .collect(Collectors.toList());
  }

  @Data
  public static class SheetDetailBo extends BaseBo<TakeStockSheetFullDto.SheetDetailDto> {

    /**
     * ID
     */
    @Schema(description = "ID")
    private String productId;

    /**
     * SKU ID
     */
    @Schema(description = "SKU ID")
    private String skuId;

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
     * 编号
     */
    @Schema(description = "编号")
    private String productCode;

    /**
     * 名称
     */
    @Schema(description = "名称")
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
     * 盘点数量
     */
    @Schema(description = "盘点数量")
    private BigDecimal takeNum;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String description;

    /**
     * 是否显示库存
     */
    @JsonIgnore
    @Schema(hidden = true)
    private Boolean showStock;

    public SheetDetailBo(TakeStockSheetFullDto.SheetDetailDto dto, Boolean showStock) {

      this.showStock = showStock;

      this.init(dto);
    }

    @Override
    protected void afterInit(TakeStockSheetFullDto.SheetDetailDto dto) {

      if (this.showStock) {
        this.stockNum = dto.getStockNum();
      } else {
        this.stockNum = null;
      }
    }
  }
}
