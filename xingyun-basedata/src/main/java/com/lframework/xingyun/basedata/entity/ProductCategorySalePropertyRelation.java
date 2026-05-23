package com.lframework.xingyun.basedata.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("base_data_product_category_sale_property_relation")
public class ProductCategorySalePropertyRelation extends BaseEntity implements BaseDto {

  public static final String CACHE_NAME = "ProductCategorySalePropertyRelation";

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 销售属性ID
   */
  private String propertyId;

  /**
   * 商品分类ID
   */
  private String categoryId;
}
