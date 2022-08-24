package com.lframework.xingyun.basedata.api.bo.product.info.retail;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.facade.dto.product.info.RetailProductDto;
import com.lframework.xingyun.sc.facade.ProductStockFeignClient;
import com.lframework.xingyun.sc.facade.entity.ProductStock;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RetailProductBo extends BaseBo<RetailProductDto> {

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
   * 是否多销售属性
   */
  @ApiModelProperty("是否多销售属性")
  private Boolean multiSaleProp;

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
   * 零售价
   */
  @ApiModelProperty("零售价")
  private BigDecimal retailPrice;

  /**
   * 库存数量
   */
  @ApiModelProperty("库存数量")
  private Integer stockNum;

  /**
   * 税率（%）
   */
  @ApiModelProperty("税率（%）")
  private BigDecimal taxRate;

  /**
   * 销售属性1
   */
  @ApiModelProperty("销售属性1")
  private String salePropItemName1;

  /**
   * 销售属性2
   */
  @ApiModelProperty("销售属性2")
  private String salePropItemName2;

  /**
   * 仓库ID
   */
  @ApiModelProperty(value = "仓库ID", hidden = true)
  @JsonIgnore
  private String scId;

  public RetailProductBo(String scId, RetailProductDto dto) {

    this.scId = scId;

    if (dto != null) {
      this.convert(dto);

      this.afterInit(dto);
    }
  }

  @Override
  protected void afterInit(RetailProductDto dto) {

    this.productId = dto.getId();
    this.productCode = dto.getCode();
    this.productName = dto.getName();

    if (dto.getSaleProps() != null) {
      this.salePropItemName1 = dto.getSaleProps().getItemName1();
      this.salePropItemName2 = dto.getSaleProps().getItemName2();
    }

    ProductStockFeignClient productStockFeignClient = ApplicationUtil.getBean(
        ProductStockFeignClient.class);
    ProductStock productStock = productStockFeignClient.getByProductIdAndScId(this.getProductId(),
        this.getScId()).getData();
    this.stockNum = productStock == null ? 0 : productStock.getStockNum();
  }
}
