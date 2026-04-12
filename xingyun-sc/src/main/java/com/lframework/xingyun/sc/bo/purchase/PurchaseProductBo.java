package com.lframework.xingyun.sc.bo.purchase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.sc.dto.purchase.PurchaseProductDto;
import com.lframework.xingyun.sc.entity.ProductStock;
import com.lframework.xingyun.sc.service.stock.ProductStockService;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class PurchaseProductBo extends BaseBo<PurchaseProductDto> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String productId;

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
   * 采购价
   */
  @Schema(description = "采购价")
  private BigDecimal purchasePrice;

  /**
   * 含税成本价
   */
  @Schema(description = "含税成本价")
  private BigDecimal taxCostPrice;

  /**
   * 库存数量
   */
  @Schema(description = "库存数量")
  private BigDecimal stockNum;

  /**
   * 税率（%）
   */
  @Schema(description = "税率（%）")
  private BigDecimal taxRate;

  /**
   * 仓库ID
   */
  @Schema(description = "仓库ID", hidden = true)
  @JsonIgnore
  private String scId;

  public PurchaseProductBo(String scId, PurchaseProductDto dto) {

    this.scId = scId;

    this.init(dto);
  }

  @Override
  protected void afterInit(PurchaseProductDto dto) {

    this.productId = dto.getId();
    this.productCode = dto.getCode();
    this.productName = dto.getName();

    ProductStockService productStockService = ApplicationUtil.getBean(
        ProductStockService.class);
    ProductStock productStock = productStockService.getByProductIdAndScId(this.getProductId(),
        this.getScId());
    this.taxCostPrice =
        productStock == null ? BigDecimal.ZERO
            : NumberUtil.getNumber(productStock.getTaxPrice(), 6);
    this.stockNum = productStock == null ? BigDecimal.ZERO : productStock.getStockNum();
  }
}
