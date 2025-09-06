package com.lframework.xingyun.sc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import java.math.BigDecimal;
import lombok.Data;

/**
 * <p>
 * 预先盘点单详情
 * </p>
 *
 * @author zmj
 */
@Data
@TableName("tbl_pre_take_stock_sheet_detail")
public class PreTakeStockSheetDetail extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 预先盘点单ID
   */
  private String sheetId;

  /**
   * 商品ID
   */
  private String productId;

  /**
   * 初盘数量
   */
  private BigDecimal firstNum;

  /**
   * 复盘数量
   */
  private BigDecimal secondNum;

  /**
   * 抽盘数量
   */
  private BigDecimal randNum;

  /**
   * 排序
   */
  private Integer orderNo;

}
