package com.lframework.xingyun.basedata.entity;

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
 * @since 2021-08-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("base_data_product_saleprop_item_relation")
public class ProductSalePropItemRelation extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 商品ID
   */
  private String productId;

  /**
   * 销售属性值ID
   */
  private String salePropItemId;

  /**
   * 排序
   */
  private Integer orderNo;
}
