package com.lframework.xingyun.sc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
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
@TableName("tbl_sale_config")
public class SaleConfig extends BaseEntity implements BaseDto {

  public static final String CACHE_NAME = "SaleConfig";
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

  /**
   * 销售出库单是否需要物流单
   */
  private Boolean outStockRequireLogistics;
}
