package com.lframework.xingyun.sc.vo.purchase.returned;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class ReturnProductVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 商品ID
   */
  @ApiModelProperty("商品ID")
  private String productId;

  /**
   * 退货价
   */
  @ApiModelProperty("退货价")
  private BigDecimal purchasePrice;

  /**
   * 退货数量
   */
  @ApiModelProperty("退货数量")
  private Integer returnNum;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 收货单明细ID
   */
  @ApiModelProperty("收货单明细ID")
  private String receiveSheetDetailId;
}
