package com.lframework.xingyun.sc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.entity.BaseEntity;
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
@TableName("tbl_sale_config")
public class SaleConfig extends BaseEntity {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 销售出库单是否关联销售订单
   */
  private Boolean outStockRequireSale;

  /**
   * 销售出库单是否多次关联销售订单
   */
  private Boolean outStockMultipleRelateSale;

  /**
   * 销售退货单是否关联销售出库单
   */
  private Boolean saleReturnRequireOutStock;

  /**
   * 销售退货单是否多次关联销售出库单
   */
  private Boolean saleReturnMultipleRelateOutStock;
}
