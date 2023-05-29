package com.lframework.xingyun.sc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2023-05-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tbl_sale_order_detail_bundle")
public class SaleOrderDetailBundle extends BaseEntity implements BaseDto {

  public static final String CACHE_NAME = "SaleOrderDetailBundle";
  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 销售单ID
   */
  private String orderId;

  /**
   * 明细ID
   */
  private String detailId;

  /**
   * 组合商品ID
   */
  private String mainProductId;

  /**
   * 组合商品数量
   */
  private Integer orderNum;

  /**
   * 单品ID
   */
  private String productId;

  /**
   * 单品数量
   */
  private Integer productOrderNum;

  /**
   * 单品原价
   */
  private BigDecimal productOriPrice;

  /**
   * 单品含税价格
   */
  private BigDecimal productTaxPrice;

  /**
   * 单品税率
   */
  private BigDecimal productTaxRate;

  /**
   * 单品明细ID
   */
  private String productDetailId;
}
