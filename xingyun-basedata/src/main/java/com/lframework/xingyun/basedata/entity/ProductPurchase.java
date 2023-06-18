package com.lframework.xingyun.basedata.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import java.math.BigDecimal;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-07-11
 */
@Data
@TableName("base_data_product_purchase")
public class ProductPurchase extends BaseEntity implements BaseDto {

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
