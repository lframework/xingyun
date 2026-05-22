package com.lframework.xingyun.basedata.dto.product.category.saleproperty;

import com.lframework.starter.web.core.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;

@Data
public class ProductCategorySalePropertyDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 分类销售属性关系ID
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

  /**
   * 编号
   */
  private String code;

  /**
   * 名称
   */
  private String name;

  /**
   * 备注
   */
  private String description;
}
