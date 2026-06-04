package com.lframework.xingyun.sc.bo.stock.take.sheet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.sc.dto.stock.take.sheet.TakeStockSheetProductDto;
import com.lframework.xingyun.sc.entity.TakeStockConfig;
import com.lframework.xingyun.sc.service.stock.take.TakeStockConfigService;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class TakeStockSheetProductBo extends BaseBo<TakeStockSheetProductDto> {

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
   * 盘点任务ID
   */
  @Schema(description = "盘点任务ID", hidden = true)
  @JsonIgnore
  private String planId;

  public TakeStockSheetProductBo(TakeStockSheetProductDto dto, String planId) {

    this.planId = planId;

    this.init(dto);
  }

  @Override
  protected void afterInit(TakeStockSheetProductDto dto) {

    this.productCode = dto.getCode();
    this.productName = dto.getName();

    TakeStockConfigService takeStockConfigService = ApplicationUtil.getBean(
        TakeStockConfigService.class);
    TakeStockConfig config = takeStockConfigService.get();
    if (config.getShowStock()) {
      this.stockNum = dto.getStockNum() == null ? BigDecimal.ZERO : dto.getStockNum();
    } else {
      this.stockNum = null;
    }
  }
}
