package com.lframework.xingyun.sc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 预先盘点单详情
 * </p>
 *
 * @author zmj
 */
@Data
@EqualsAndHashCode(callSuper = true)
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
  private Integer firstNum;

  /**
   * 复盘数量
   */
  private Integer secondNum;

  /**
   * 抽盘数量
   */
  private Integer randNum;

  /**
   * 排序
   */
  private Integer orderNo;

}
