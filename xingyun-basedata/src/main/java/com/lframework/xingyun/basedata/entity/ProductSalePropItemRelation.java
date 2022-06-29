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
   * 聚合ID
   */
  private String polyId;

  /**
   * 商品ID
   */
  private String productId;

  /**
   * 销售属性组ID1
   */
  private String salePropGroupId1;

  /**
   * 销售属性值ID1
   */
  private String salePropItemId1;

  /**
   * 销售属性组ID2
   */
  private String salePropGroupId2;

  /**
   * 销售属性值ID2
   */
  private String salePropItemId2;
}
