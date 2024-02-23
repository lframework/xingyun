package com.lframework.xingyun.sc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.dto.BaseDto;
import com.lframework.starter.web.entity.BaseEntity;
import lombok.Data;

/**
 * <p>
 * 库存调整单明细
 * </p>
 *
 * @author zmj
 * @since 2023-04-18
 */
@Data
@TableName("tbl_stock_adjust_sheet_detail")
public class StockAdjustSheetDetail extends BaseEntity implements BaseDto {

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
   * 调整库存数量
   */
  private Integer stockNum;

  /**
   * 备注
   */
  private String description;

  /**
   * 排序
   */
  private Integer orderNo;

}
