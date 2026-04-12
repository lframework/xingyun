package com.lframework.xingyun.sc.bo.retail;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.sc.dto.retail.RetailProductDto;
import com.lframework.xingyun.sc.entity.ProductStock;
import com.lframework.xingyun.sc.service.stock.ProductStockService;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class RetailProductBo extends BaseBo<RetailProductDto> {

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
   * 是否多销售属性
   */
  @Schema(description = "是否多销售属性")
  private Boolean multiSaleProp;

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
   * 零售价
   */
  @Schema(description = "零售价")
  private BigDecimal retailPrice;

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

  public RetailProductBo(String scId, RetailProductDto dto) {

    this.scId = scId;

    this.init(dto);
  }

  @Override
  protected void afterInit(RetailProductDto dto) {

    this.productId = dto.getId();
    this.productCode = dto.getCode();
    this.productName = dto.getName();

    ProductStockService productStockService = ApplicationUtil.getBean(ProductStockService.class);
    ProductStock productStock = productStockService.getByProductIdAndScId(this.getProductId(),
        this.getScId());
    this.stockNum = productStock == null ? BigDecimal.ZERO : productStock.getStockNum();
  }
}
