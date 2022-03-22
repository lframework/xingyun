package com.lframework.xingyun.sc.vo.purchase;

import com.lframework.starter.web.vo.BaseVo;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class PurchaseProductVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 商品ID
   */
  private String productId;

  /**
   * 采购价
   */
  private BigDecimal purchasePrice;

  /**
   * 采购数量
   */
  private Integer purchaseNum;

  /**
   * 备注
   */
  private String description;
}
