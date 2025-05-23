package com.lframework.xingyun.core.dto.stock;

import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class ProductStockChangeDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 仓库ID
   */
  private String scId;

  /**
   * 商品ID
   */
  private String productId;

  /**
   * 变动数量
   */
  private Integer num;

  /**
   * 含税变动金额
   */
  private BigDecimal taxAmount;

  /**
   * 当前含税均价
   */
  private BigDecimal curTaxPrice;

  /**
   * 当前库存数量
   */
  private Integer curStockNum;

  /**
   * 生成时间
   */
  private LocalDateTime createTime;
}
