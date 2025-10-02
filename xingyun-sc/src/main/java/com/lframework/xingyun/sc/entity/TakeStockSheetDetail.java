package com.lframework.xingyun.sc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import java.math.BigDecimal;
import lombok.Data;

/**
 * <p>
 * 盘点单详情
 * </p>
 *
 * @author zmj
 */
@Data
@TableName("tbl_take_stock_sheet_detail")
public class TakeStockSheetDetail extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 盘点单ID
   */
  private String sheetId;

  /**
   * 商品ID
   */
  private String productId;

  /**
   * 盘点数量
   */
  private BigDecimal takeNum;

  /**
   * 备注
   */
  private String description;

  /**
   * 排序
   */
  private Integer orderNo;

}
