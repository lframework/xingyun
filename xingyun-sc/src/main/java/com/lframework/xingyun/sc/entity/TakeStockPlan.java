package com.lframework.xingyun.sc.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import com.lframework.xingyun.sc.enums.TakeStockPlanStatus;
import com.lframework.xingyun.sc.enums.TakeStockPlanType;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 盘点任务
 * </p>
 *
 * @author zmj
 */
@Data
@TableName("tbl_take_stock_plan")
public class TakeStockPlan extends BaseEntity implements BaseDto {

  public static final String CACHE_NAME = "TakeStockPlan";
  private static final long serialVersionUID = 1L;
  /**
   * ID
   */
  private String id;

  /**
   * 业务单据号
   */
  private String code;

  /**
   * 仓库ID
   */
  private String scId;

  /**
   * 盘点类别
   */
  private TakeStockPlanType takeType;

  /**
   * 业务ID
   */
  private String bizId;

  /**
   * 盘点状态
   */
  private TakeStockPlanStatus takeStatus;

  /**
   * 备注
   */
  private String description;

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
   * 修改人 新增和修改时赋值
   */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private String updateBy;

  /**
   * 修改人ID 新增和修改时赋值
   */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private String updateById;

  /**
   * 修改时间
   */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateTime;

}
