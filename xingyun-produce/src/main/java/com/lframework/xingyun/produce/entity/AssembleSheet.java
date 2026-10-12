package com.lframework.xingyun.produce.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import com.lframework.xingyun.produce.enums.AssembleSheetStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 组装单
 *
 * @author zmj
 */
@Data
@TableName("tbl_assemble_sheet")
public class AssembleSheet extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  public static final String CACHE_NAME = "AssembleSheet";

  /**
   * ID
   */
  private String id;

  /**
   * 业务单据号
   */
  private String code;

  /**
   * 仓库ID（子件出库仓、成品入库仓）
   */
  private String scId;

  /**
   * BOM ID
   */
  private String bomId;

  /**
   * 成品商品ID
   */
  private String productId;

  /**
   * 成品SKU ID
   */
  private String skuId;

  /**
   * 组装数量（成品数量）
   */
  private BigDecimal totalNum;

  /**
   * 状态
   */
  private AssembleSheetStatus status;

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
   * 拒绝原因
   */
  private String refuseReason;

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
