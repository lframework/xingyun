package com.lframework.xingyun.core.dto.stock;

import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductLotChangeDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 批次ID
   */
  private String lotId;

  /**
   * 仓库ID
   */
  private String scId;

  /**
   * 商品ID
   */
  private String productId;

  /**
   * 供应商ID
   */
  private String supplierId;

  /**
   * 含税金额
   */
  private BigDecimal taxAmount;

  /**
   * 无税金额
   */
  private BigDecimal unTaxAmount;

  /**
   * 变动数量
   */
  private Integer num;
}
