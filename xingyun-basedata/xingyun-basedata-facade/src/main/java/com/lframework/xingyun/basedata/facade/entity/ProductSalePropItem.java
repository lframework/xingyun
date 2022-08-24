package com.lframework.xingyun.basedata.facade.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-07-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("base_data_product_saleprop_item")
public class ProductSalePropItem extends BaseEntity implements BaseDto {

  public static final String CACHE_NAME = "ProductSalePropItem";
  private static final long serialVersionUID = 1L;
  /**
   * ID
   */
  private String id;

  /**
   * 销售属性组ID
   */
  private String groupId;

  /**
   * 编号
   */
  private String code;

  /**
   * 名称
   */
  private String name;

  /**
   * 状态
   */
  private Boolean available;

  /**
   * 备注
   */
  private String description;
}
