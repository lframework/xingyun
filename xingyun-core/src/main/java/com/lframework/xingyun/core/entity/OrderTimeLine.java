package com.lframework.xingyun.core.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.core.enums.OrderTimeLineBizType;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-07-07
 */
@Data
@TableName("tbl_order_time_line")
public class OrderTimeLine extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  public static final String CACHE_NAME = "OrderTimeLine";

  /**
   * ID
   */
  private String id;

  /**
   * 订单ID
   */
  private String orderId;

  /**
   * 描述内容
   */
  private String content;

  /**
   * 创建人ID 新增时赋值
   */
  @TableField(fill = FieldFill.INSERT)
  private String createById;

  /**
   * 创建人 新增时赋值
   */
  @TableField(fill = FieldFill.INSERT)
  private String createBy;

  /**
   * 创建时间
   */
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;

  /**
   * 业务类型
   */
  private OrderTimeLineBizType bizType;
}
