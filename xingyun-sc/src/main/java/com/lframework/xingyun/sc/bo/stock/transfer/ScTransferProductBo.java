package com.lframework.xingyun.sc.bo.stock.transfer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.xingyun.sc.dto.stock.transfer.ScTransferProductDto;
import com.lframework.xingyun.sc.entity.ProductStock;
import com.lframework.xingyun.sc.service.stock.ProductStockService;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ScTransferProductBo extends BaseBo<ScTransferProductDto> {

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
   * 分类名称
   */
  @ApiModelProperty("分类名称")
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
   * 当前库存数量
   */
  @ApiModelProperty("当前库存数量")
  private Integer curStockNum;

  /**
   * 仓库ID
   */
  @JsonIgnore
  @ApiModelProperty(hidden = true)
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
    this.curStockNum = productStock == null ? 0 : productStock.getStockNum();
  }
}
