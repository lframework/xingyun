package com.lframework.xingyun.sc.bo.stock.adjust.stock;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.annotations.convert.EnumConvert;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.sc.dto.stock.adjust.stock.StockAdjustSheetFullDto;
import com.lframework.xingyun.sc.entity.StockAdjustReason;
import com.lframework.xingyun.sc.enums.StockAdjustSheetStatus;
import com.lframework.xingyun.sc.service.stock.adjust.StockAdjustReasonService;
import com.lframework.starter.web.inner.service.system.SysUserService;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
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
  @Schema(description = "ID")
  private String id;

  /**
   * 业务单据号
   */
  @Schema(description = "业务单据号")
  private String code;

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
   * 业务类型
   */
  @Schema(description = "业务类型")
  @EnumConvert
  private Integer bizType;

  /**
   * 调整原因ID
   */
  @Schema(description = "调整原因ID")
  private String reasonId;

  /**
   * 调整原因
   */
  @Schema(description = "调整原因")
  private String reasonName;

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
   * 拒绝原因
   */
  @Schema(description = "拒绝原因")
  private String refuseReason;

  /**
   * 明细
   */
  @Schema(description = "明细")
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

    this.details = dto.getDetails().stream().map(t -> new DetailBo(t, this.status))
        .collect(Collectors.toList());
  }

  @Data
  public static class DetailBo extends BaseBo<StockAdjustSheetFullDto.DetailDto> {

    /**
     * ID
     */
    @Schema(description = "ID")
    private String id;

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
     * 调整库存数量
     */
    @Schema(description = "调整库存数量")
    private BigDecimal stockNum;

    /**
     * 当前库存数量
     */
    @Schema(description = "当前库存数量")
    private BigDecimal curStockNum;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String description;

    /**
     * 状态
     */
    @JsonIgnore
    @Schema(hidden = true)
    private Integer status;

    public DetailBo(StockAdjustSheetFullDto.DetailDto dto, Integer status) {

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
      if (!StockAdjustSheetStatus.APPROVE_PASS.getCode().equals(this.status)) {
        this.curStockNum = dto.getCurStockNum() == null ? BigDecimal.ZERO : dto.getCurStockNum();
      } else {
        this.curStockNum = null;
      }
    }
  }
}
