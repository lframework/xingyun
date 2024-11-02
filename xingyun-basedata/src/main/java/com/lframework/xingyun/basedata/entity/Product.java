package com.lframework.xingyun.basedata.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.basedata.enums.ProductType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 商品
 * </p>
 *
 * @author zmj
 * @since 2021-07-11
 */
@Data
@TableName("base_data_product")
public class Product extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  public static final String CACHE_NAME = "Product";

  /**
   * ID
   */
  private String id;

  /**
   * 编号
   */
  private String code;

  /**
   * 名称
   */
  private String name;

  /**
   * 简称
   */
  private String shortName;

  /**
   * SKU
   */
  private String skuCode;

  /**
   * 简码
   */
  private String externalCode;

  /**
   * 分类ID
   */
  private String categoryId;

  /**
   * 品牌ID
   */
  private String brandId;

  /**
   * 商品类型
   */
  private ProductType productType;

  /**
   * 进项税率（%）
   */
  private BigDecimal taxRate;

  /**
   * 销项税率（%）
   */
  private BigDecimal saleTaxRate;

  /**
   * 规格
   */
  private String spec;

  /**
   * 单位
   */
  private String unit;

  /**
   * 重量（kg）
   */
  private BigDecimal weight;

  /**
   * 体积（cm3）
   */
  private BigDecimal volume;

  /**
   * 状态
   */
  private Boolean available;

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
