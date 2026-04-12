package com.lframework.xingyun.sc.bo.stock.transfer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.sc.dto.stock.transfer.ScTransferProductDto;
import com.lframework.xingyun.sc.entity.ProductStock;
import com.lframework.xingyun.sc.service.stock.ProductStockService;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class ScTransferProductBo extends BaseBo<ScTransferProductDto> {

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
   * 当前库存数量
   */
  @Schema(description = "当前库存数量")
  private BigDecimal curStockNum;

  /**
   * 仓库ID
   */
  @JsonIgnore
  @Schema(hidden = true)
  private String scId;

  public ScTransferProductBo() {

  }

  public ScTransferProductBo(String scId, ScTransferProductDto dto) {
    this.scId = scId;

    this.init(dto);
  }

  @Override
  protected void afterInit(ScTransferProductDto dto) {

    this.productId = dto.getId();
    this.productCode = dto.getCode();
    this.productName = dto.getName();

    ProductStockService productStockService = ApplicationUtil.getBean(
        ProductStockService.class);
    ProductStock productStock = productStockService.getByProductIdAndScId(dto.getId(),
        this.scId);
    this.curStockNum = productStock == null ? BigDecimal.ZERO : productStock.getStockNum();
  }
}
