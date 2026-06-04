package com.lframework.xingyun.sc.bo.stock.adjust.stock;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.sc.dto.stock.adjust.stock.StockAdjustProductDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class StockAdjustProductBo extends BaseBo<StockAdjustProductDto> {

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
   * 当前库存数量
   */
  @Schema(description = "当前库存数量")
  private BigDecimal curStockNum;

  public StockAdjustProductBo() {

  }

  public StockAdjustProductBo(StockAdjustProductDto dto) {
    super(dto);
  }

  @Override
  protected void afterInit(StockAdjustProductDto dto) {

    this.productCode = dto.getCode();
    this.productName = dto.getName();

    this.curStockNum = dto.getCurStockNum() == null ? BigDecimal.ZERO : dto.getCurStockNum();
  }
}
