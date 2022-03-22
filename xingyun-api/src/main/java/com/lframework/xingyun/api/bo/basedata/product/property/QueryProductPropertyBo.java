package com.lframework.xingyun.api.bo.basedata.product.property;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.basedata.dto.product.property.ProductPropertyDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryProductPropertyBo extends BaseBo<ProductPropertyDto> {

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
  private Integer columnType;

  /**
   * 属性类别
   */
  private Integer propertyType;

  /**
   * 状态
   */
  private Boolean available;

  /**
   * 备注
   */
  private String description;

  public QueryProductPropertyBo() {

  }

  public QueryProductPropertyBo(ProductPropertyDto dto) {

    super(dto);
  }

  @Override
  protected void afterInit(ProductPropertyDto dto) {

    this.columnType = dto.getColumnType().getCode();
    this.propertyType = dto.getPropertyType().getCode();
  }
}
