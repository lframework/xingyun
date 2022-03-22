package com.lframework.xingyun.sc.dto.stock;

import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.sc.enums.ProductStockBizType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ProductLotDto implements BaseDto, Serializable {

  public static final String CACHE_NAME = "ProductLotDto";

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 批次号
   */
  private String lotCode;

  /**
   * 商品ID
   */
  private String productId;

  /**
   * 供应商ID
   */
  private String supplierId;

  /**
   * 税率（%）
   */
  private BigDecimal taxRate;

  /**
   * 创建时间
   */
  private LocalDateTime createTime;

  /**
   * 业务单据ID
   */
  private String bizId;

  /**
   * 业务单据明细ID
   */
  private String bizDetailId;

  /**
   * 业务单据号
   */
  private String bizCode;

  /**
   * 业务类型
   */
  private ProductStockBizType bizType;
}
