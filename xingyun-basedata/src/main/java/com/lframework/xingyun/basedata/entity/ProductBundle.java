package com.lframework.xingyun.basedata.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 组合商品
 * </p>
 *
 * @author zmj
 * @since 2023-05-25
 */
@Data
@TableName("base_data_product_bundle")
public class ProductBundle extends BaseEntity implements BaseDto {

  public static final String CACHE_NAME = "ProductBundle";

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 主商品ID
   */
  private String mainProductId;

  /**
   * 单品ID
   */
  private String productId;

  /**
   * 包含数量
   */
  private Integer bundleNum;

  /**
   * 销售价
   */
  private BigDecimal salePrice;

  /**
   * 零售价
   */
  private BigDecimal retailPrice;



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
   * 修改时间 新增和修改时赋值
   */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateTime;
}
