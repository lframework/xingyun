package com.lframework.xingyun.sc.facade.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 库存成本调整单明细
 * </p>
 *
 * @author zmj
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tbl_stock_cost_adjust_sheet_detail")
public class StockCostAdjustSheetDetail extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 单据ID
   */
  private String sheetId;

  /**
   * 商品ID
   */
  private String productId;

  /**
   * 库存数量
   */
  private Integer stockNum;

  /**
   * 档案采购价
   */
  private BigDecimal purchasePrice;

  /**
   * 调整前成本价
   */
  private BigDecimal oriPrice;

  /**
   * 调整后成本价
   */
  private BigDecimal price;

  /**
   * 库存调价差额
   */
  private BigDecimal diffAmount;

  /**
   * 备注
   */
  private String description;

  /**
   * 排序
   */
  private Integer orderNo;

}
