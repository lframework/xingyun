package com.lframework.xingyun.sc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import java.math.BigDecimal;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-09-12
 */
@Data
@TableName("tbl_product_stock")
public class ProductStock extends BaseEntity implements BaseDto {

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
  private BigDecimal stockNum;

  /**
   * 含税价格
   */
  private BigDecimal taxPrice;

  /**
   * 含税金额
   */
  private BigDecimal taxAmount;
}
