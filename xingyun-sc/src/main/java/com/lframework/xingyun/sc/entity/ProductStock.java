package com.lframework.xingyun.sc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.entity.BaseEntity;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-09-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tbl_product_stock")
public class ProductStock extends BaseEntity {

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
