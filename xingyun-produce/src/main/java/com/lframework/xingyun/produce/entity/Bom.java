package com.lframework.xingyun.produce.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import com.lframework.xingyun.produce.enums.BomStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * BOM清单
 *
 * @author zmj
 */
@Data
@TableName("bom_bom")
public class Bom extends BaseEntity implements BaseDto {

  public static final String CACHE_NAME = "Bom";
  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * BOM编号
   */
  private String code;

  /**
   * BOM名称
   */
  private String name;

  /**
   * 成品/半成品商品ID
   */
  private String mainProductId;

  /**
   * 成品SKU ID
   */
  private String mainSkuId;

  /**
   * 产出数量
   */
  private BigDecimal outputNum;

  /**
   * 废品率%
   */
  private BigDecimal scrapRate;

  /**
   * 状态
   */
  private BomStatus status;

  /**
   * 是否默认版本
   */
  private Boolean isDefault;

  /**
   * 版本
   */
  private String version;

  /**
   * 备注
   */
  private String description;

  /**
   * 审核人
   */
  private String approveBy;

  /**
   * 审核时间
   */
  private LocalDateTime approveTime;

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
