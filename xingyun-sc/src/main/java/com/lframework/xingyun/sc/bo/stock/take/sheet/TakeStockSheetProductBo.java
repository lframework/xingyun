package com.lframework.xingyun.sc.bo.stock.take.sheet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.xingyun.sc.dto.stock.take.plan.GetTakeStockPlanDetailProductDto;
import com.lframework.xingyun.sc.dto.stock.take.sheet.TakeStockSheetProductDto;
import com.lframework.xingyun.sc.entity.ProductStock;
import com.lframework.xingyun.sc.entity.TakeStockConfig;
import com.lframework.xingyun.sc.service.stock.ProductStockService;
import com.lframework.xingyun.sc.service.stock.take.TakeStockConfigService;
import com.lframework.xingyun.sc.service.stock.take.TakeStockPlanDetailService;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TakeStockSheetProductBo extends BaseBo<TakeStockSheetProductDto> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
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
   * 类目名称
   */
  @ApiModelProperty("类目名称")
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
   * 外部编号
   */
  @ApiModelProperty("外部编号")
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
   * 库存数量
   */
  @ApiModelProperty("库存数量")
  private Integer stockNum;

  /**
   * 盘点任务ID
   */
  @ApiModelProperty(value = "盘点任务ID", hidden = true)
  @JsonIgnore
  private String planId;

  /**
   * 仓库ID
   */
  @ApiModelProperty(value = "仓库ID", hidden = true)
  @JsonIgnore
  private String scId;

  public TakeStockSheetProductBo(TakeStockSheetProductDto dto, String planId, String scId) {

    this.planId = planId;
    this.scId = scId;

    this.init(dto);
  }

  @Override
  protected void afterInit(TakeStockSheetProductDto dto) {

    this.productId = dto.getId();
    this.productCode = dto.getCode();
    this.productName = dto.getName();

    TakeStockConfigService takeStockConfigService = ApplicationUtil.getBean(
        TakeStockConfigService.class);
    TakeStockConfig config = takeStockConfigService.get();
    if (config.getShowStock()) {
      if (!StringUtil.isBlank(this.planId)) {
        TakeStockPlanDetailService takeStockPlanDetailService = ApplicationUtil.getBean(
            TakeStockPlanDetailService.class);
        GetTakeStockPlanDetailProductDto product = takeStockPlanDetailService.getByPlanIdAndProductId(
            this.planId, this.productId);
        this.stockNum = product.getStockNum();
      } else {
        ProductStockService productStockService = ApplicationUtil.getBean(
            ProductStockService.class);
        ProductStock productStock = productStockService.getByProductIdAndScId(this.productId,
            this.scId);
        this.stockNum = productStock == null ? 0 : productStock.getStockNum();
      }
    }
  }
}
