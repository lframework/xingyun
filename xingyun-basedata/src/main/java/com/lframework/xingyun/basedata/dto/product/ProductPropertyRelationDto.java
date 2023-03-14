package com.lframework.xingyun.basedata.dto.product;

import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.basedata.enums.ColumnType;
import java.io.Serializable;
import lombok.Data;

@Data
public class ProductPropertyRelationDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  public static final String CACHE_NAME = "ProductPropertyRelationDto";

  /**
   * ID
   */
  private String id;

  /**
   * 商品ID
   */
  private String productId;

  /**
   * 属性ID
   */
  private String propertyId;

  /**
   * 属性值ID
   */
  private String propertyItemId;

  /**
   * 字段类型
   */
  private ColumnType propertyColumnType;

  /**
   * 属性名称
   */
  private String propertyName;

  /**
   * 属性值
   */
  private String propertyText;
}
