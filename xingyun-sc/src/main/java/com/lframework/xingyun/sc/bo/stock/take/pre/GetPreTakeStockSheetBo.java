package com.lframework.xingyun.sc.bo.stock.take.pre;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.sc.dto.stock.take.pre.PreTakeStockSheetFullDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

/**
 * <p>
 * 预先盘点单 GetBo
 * </p>
 *
 * @author zmj
 */
@Data
public class GetPreTakeStockSheetBo extends BaseBo<PreTakeStockSheetFullDto> {

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
   * 盘点状态
   */
  @Schema(description = "盘点状态")
  private Integer takeStatus;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  /**
   * 操作人
   */
  @Schema(description = "操作人")
  private String updateBy;

  /**
   * 操作时间
   */
  @Schema(description = "操作时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime updateTime;

  /**
   * 明细
   */
  @Schema(description = "明细")
  private List<SheetDetailBo> details;

  @Data
  public static class SheetDetailBo extends BaseBo<PreTakeStockSheetFullDto.SheetDetailDto> {

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
     * 编号
     */
    @Schema(description = "编号")
    private String productCode;

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
     * 初盘数量
     */
    @Schema(description = "初盘数量")
    private BigDecimal firstNum;

    /**
     * 复盘数量
     */
    @Schema(description = "复盘数量")
    private BigDecimal secondNum;

    /**
     * 抽盘数量
     */
    @Schema(description = "抽盘数量")
    private BigDecimal randNum;

    public SheetDetailBo(PreTakeStockSheetFullDto.SheetDetailDto dto) {

      super(dto);
    }

    @Override
    protected void afterInit(PreTakeStockSheetFullDto.SheetDetailDto dto) {

      this.productId = dto.getProductId();
      this.skuId = dto.getSkuId();
      this.productCode = dto.getProductCode();
      this.skuCode = dto.getSkuCode();
      this.productName = dto.getProductName();
      this.salePropertyText = dto.getSalePropertyText();
      this.categoryName = dto.getCategoryName();
      this.brandName = dto.getBrandName();
      this.spec = dto.getSpec();
      this.unit = dto.getUnit();
    }
  }

  public GetPreTakeStockSheetBo() {

  }

  public GetPreTakeStockSheetBo(PreTakeStockSheetFullDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<PreTakeStockSheetFullDto> convert(PreTakeStockSheetFullDto dto) {

    return super.convert(dto, GetPreTakeStockSheetBo::getTakeStatus);
  }

  @Override
  protected void afterInit(PreTakeStockSheetFullDto dto) {

    StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
    StoreCenter sc = storeCenterService.findById(dto.getScId());

    this.scId = sc.getId();
    this.scName = sc.getName();

    this.takeStatus = dto.getTakeStatus().getCode();

    this.details = dto.getDetails().stream().map(SheetDetailBo::new).collect(Collectors.toList());
  }
}
