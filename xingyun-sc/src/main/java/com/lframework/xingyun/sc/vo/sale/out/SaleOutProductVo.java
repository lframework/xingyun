package com.lframework.xingyun.sc.vo.sale.out;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class SaleOutProductVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 商品ID
   */
  @ApiModelProperty("商品ID")
  private String productId;

  /**
   * 原价
   */
  @ApiModelProperty("原价")
  private BigDecimal oriPrice;

  /**
   * 现价
   */
  @ApiModelProperty("现价")
  private BigDecimal taxPrice;

  /**
   * 折扣（%）
   */
  @ApiModelProperty("折扣（%）")
  private BigDecimal discountRate;

  /**
   * 出库数量
   */
  @ApiModelProperty("出库数量")
  private Integer orderNum;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 销售订单明细ID
   */
  @ApiModelProperty("销售订单明细ID")
  private String saleOrderDetailId;
}
