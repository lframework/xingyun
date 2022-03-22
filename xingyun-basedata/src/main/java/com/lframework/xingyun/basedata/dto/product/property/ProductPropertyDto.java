package com.lframework.xingyun.basedata.dto.product.property;

import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.basedata.enums.ColumnDataType;
import com.lframework.xingyun.basedata.enums.ColumnType;
import com.lframework.xingyun.basedata.enums.PropertyType;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ProductPropertyDto implements BaseDto, Serializable {

  public static final String CACHE_NAME = "ProductPropertyDto";

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
   * 状态
   */
  private Boolean available;

  /**
   * 备注
   */
  private String description;

  /**
   * 创建人ID
   */
  private String createBy;

  /**
   * 创建时间
   */
  private LocalDateTime createTime;

  /**
   * 修改人ID
   */
  private String updateBy;

  /**
   * 修改时间
   */
  private LocalDateTime updateTime;
}
