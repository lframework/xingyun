package com.lframework.xingyun.basedata.dto.product.category.property;

import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.xingyun.basedata.enums.ColumnDataType;
import com.lframework.xingyun.basedata.enums.ColumnType;
import com.lframework.xingyun.basedata.enums.PropertyType;
import java.io.Serializable;
import lombok.Data;

@Data
public class ProductCategoryPropertyRelationDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 分类属性关系ID
   */
  private String id;

  /**
   * 分类属性ID
   */
  private String propertyId;

  /**
   * 商品分类ID
   */
  private String categoryId;

  /**
   * 编号
   */
  private String code;

  /**
   * 名称
   */
  private String name;

  /**
   * 是否必填
   */
  private Boolean isRequired;

  /**
   * 录入类型
   */
  private ColumnType columnType;

  /**
   * 数据类型
   */
  private ColumnDataType columnDataType;

  /**
   * 属性类别
   */
  private PropertyType propertyType;

  /**
   * 备注
   */
  private String description;
}
