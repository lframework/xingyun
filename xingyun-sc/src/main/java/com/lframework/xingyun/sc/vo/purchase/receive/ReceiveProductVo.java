package com.lframework.xingyun.sc.vo.purchase.receive;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class ReceiveProductVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 商品ID
   */
  @ApiModelProperty("商品ID")
  private String productId;

  /**
   * 采购价
   */
  @ApiModelProperty("采购价")
  private BigDecimal purchasePrice;

  /**
   * 收货数量
   */
  @ApiModelProperty("收货数量")
  private Integer receiveNum;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 采购订单明细ID
   */
  @ApiModelProperty("采购订单明细ID")
  private String purchaseOrderDetailId;
}
