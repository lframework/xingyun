package com.lframework.xingyun.produce.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * BOM明细
 *
 * @author zmj
 */
@Data
@TableName("bom_bom_detail")
public class BomDetail extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * BOM ID
   */
  private String bomId;

  /**
   * 子件商品ID
   */
  private String productId;

  /**
   * 子件SKU ID
   */
  private String skuId;

  /**
   * 单位用量
   */
  private BigDecimal unitNum;

  /**
   * 损耗率%
   */
  private BigDecimal scrapRate;

  /**
   * 是否关键物料
   */
  private Boolean isKeyMaterial;

  /**
   * 排序
   */
  private Integer sortOrder;

  /**
   * 备注
   */
  private String description;

  /**
   * 创建人ID
   */
  @TableField(fill = FieldFill.INSERT)
  private String createById;

  /**
   * 创建人
   */
  @TableField(fill = FieldFill.INSERT)
  private String createBy;

  /**
   * 创建时间
   */
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;

  /**
   * 修改人
   */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private String updateBy;

  /**
   * 修改人ID
   */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private String updateById;

  /**
   * 修改时间
   */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateTime;
}
