package com.lframework.xingyun.sc.dto.stock;

import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.sc.enums.ProductStockBizType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ProductStockLogDto implements BaseDto, Serializable {

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
   * 批次ID
   */
  private String lotId;

  /**
   * 原库存数量
   */
  private Integer oriStockNum;

  /**
   * 现库存数量
   */
  private Integer curStockNum;

  /**
   * 原含税成本价
   */
  private BigDecimal oriTaxPrice;

  /**
   * 现含税成本价
   */
  private BigDecimal curTaxPrice;

  /**
   * 原无税成本价
   */
  private BigDecimal oriUnTaxPrice;

  /**
   * 现无税成本价
   */
  private BigDecimal curUnTaxPrice;

  /**
   * 库存数量
   */
  private Integer stockNum;

  /**
   * 含税金额
   */
  private BigDecimal taxAmount;

  /**
   * 无税金额
   */
  private BigDecimal unTaxAmount;

  /**
   * 创建人
   */
  private String createBy;

  /**
   * 创建时间
   */
  private LocalDateTime createTime;

  /**
   * 业务单据ID
   */
  private String bizId;

  /**
   * 业务单据号
   */
  private String bizCode;

  /**
   * 业务单据明细ID
   */
  private String bizDetailId;

  /**
   * 业务类型
   */
  private ProductStockBizType bizType;
}
