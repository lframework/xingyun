package com.lframework.xingyun.chart.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.chart.enums.OrderChartBizType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-11-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tbl_order_chart")
public class OrderChart extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 单据总金额
   */
  private BigDecimal totalAmount;

  /**
   * 创建时间 新增时赋值
   */
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;

  /**
   * 创建日期
   */
  private String createDate;

  /**
   * 创建时间（小时）
   */
  private String createHour;

  /**
   * 业务类型
   */
  private OrderChartBizType bizType;
}
