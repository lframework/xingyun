package com.lframework.xingyun.sc.dto.stock;

import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductStockDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 仓库ID
   */
  private String scId;

  /**
   * 商品ID
   */
  private String productId;

  /**
   * 库存数量
   */
  private Integer stockNum;

  /**
   * 含税价格
   */
  private BigDecimal taxPrice;

  /**
   * 含税金额
   */
  private BigDecimal taxAmount;

  /**
   * 无税价格
   */
  private BigDecimal unTaxPrice;

  /**
   * 无税金额
   */
  private BigDecimal unTaxAmount;
}
