package com.lframework.xingyun.sc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import lombok.Data;

/**
 * <p>
 * 盘点任务详情
 * </p>
 *
 * @author zmj
 */
@Data
@TableName("tbl_take_stock_plan_detail")
public class TakeStockPlanDetail extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 盘点任务ID
   */
  private String planId;

  /**
   * 商品ID
   */
  private String productId;

  /**
   * 初始库存数量
   */
  private Integer stockNum;

  /**
   * 盘点数量
   */
  private Integer oriTakeNum;

  /**
   * 修改后的盘点数量
   */
  private Integer takeNum;

  /**
   * 出项数量
   */
  private Integer totalOutNum;

  /**
   * 入项数量
   */
  private Integer totalInNum;

  /**
   * 备注
   */
  private String description;

  /**
   * 排序
   */
  private Integer orderNo;

}
