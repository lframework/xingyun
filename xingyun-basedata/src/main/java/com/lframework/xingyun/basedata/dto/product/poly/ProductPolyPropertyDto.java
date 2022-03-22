package com.lframework.xingyun.basedata.dto.product.poly;

import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;

@Data
public class ProductPolyPropertyDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  public static final String CACHE_NAME = "ProductPolyPropertyDto";

  /**
   * ID
   */
  private String id;

  /**
   * 聚合ID
   */
  private String polyId;

  /**
   * 属性ID
   */
  private String propertyId;

  /**
   * 属性值ID
   */
  private String propertyItemId;

  /**
   * 属性名称
   */
  private String propertyName;
}
