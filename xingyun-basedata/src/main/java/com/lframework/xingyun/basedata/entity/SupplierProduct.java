package com.lframework.xingyun.basedata.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 供应商商品关联
 */
@Data
@TableName("base_data_supplier_product")
public class SupplierProduct extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  public static final String CACHE_NAME = "SupplierProduct";

  /**
   * ID
   */
  private String id;

  /**
   * 供应商ID
   */
  private String supplierId;

  /**
   * 商品ID
   */
  private String productId;

  /**
   * SKU ID（null 表示商品下所有SKU）
   */
  private String skuId;

  /**
   * 供应商商品编号
   */
  private String supplierProductCode;

  /**
   * 供应商商品名称
   */
  private String supplierProductName;

  /**
   * 默认采购价
   */
  private BigDecimal defaultPurchasePrice;

  /**
   * 最小起订量
   */
  private BigDecimal minOrderQty;

  /**
   * 供货周期（天）
   */
  private Integer leadTimeDays;

  /**
   * 是否默认供应商
   */
  private Boolean isDefault;

  /**
   * 状态
   */
  private Boolean available;

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