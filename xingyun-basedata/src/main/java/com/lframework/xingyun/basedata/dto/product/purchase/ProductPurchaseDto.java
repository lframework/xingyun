package com.lframework.xingyun.basedata.dto.product.purchase;

import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductPurchaseDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 采购价
   */
  private BigDecimal price;
}
