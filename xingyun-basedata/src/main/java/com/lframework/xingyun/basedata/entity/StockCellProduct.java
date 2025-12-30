package com.lframework.xingyun.basedata.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import com.lframework.xingyun.basedata.enums.StockCellType;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 仓位商品
 * </p>
 *
 * @author lframework
 * @since 2026-01-02
 */
@Data
@TableName("tbl_stock_cell_product")
public class StockCellProduct extends BaseEntity implements BaseDto {

  public static final String CACHE_NAME = "StockCellProduct";
  private static final long serialVersionUID = 1L;
  /**
   * ID
   */
  private String id;

   /**
   * 仓库ID
   */
  private String scId;

  /**
   * 仓位ID
   */
  private String stockCellId;

  /**
   * 商品ID
   */
  private String productId;

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
   * 创建时间 新增时赋值
   */
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;
}
