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
 * @since 2023-05-26
 */
@Data
@TableName("tbl_retail_out_sheet_detail_bundle")
public class RetailOutSheetDetailBundle extends BaseEntity implements BaseDto {

  public static final String CACHE_NAME = "RetailOutSheetDetailBundle";
  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 出库单ID
   */
  private String sheetId;

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
  private BigDecimal orderNum;

  /**
   * 单品ID
   */
  private String productId;

  /**
   * 单品数量
   */
  private BigDecimal productOrderNum;

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

  /**
   * 总金额
   */
  private BigDecimal productTaxAmount;
}
