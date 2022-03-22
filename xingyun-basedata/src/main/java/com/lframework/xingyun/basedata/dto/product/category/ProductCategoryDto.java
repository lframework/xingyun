package com.lframework.xingyun.basedata.dto.product.category;

import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;

@Data
public class ProductCategoryDto implements BaseDto, Serializable {

  public static final String CACHE_NAME = "ProductCategoryDto";

  private static final long serialVersionUID = 1L;

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
   * 父级ID
   */
  private String parentId;

  /**
   * 状态
   */
  private Boolean available;

  /**
   * 备注
   */
  private String description;
}
