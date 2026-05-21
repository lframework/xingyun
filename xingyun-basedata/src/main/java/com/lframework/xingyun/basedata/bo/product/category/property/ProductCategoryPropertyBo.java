package com.lframework.xingyun.basedata.bo.product.category.property;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.basedata.dto.product.category.property.ProductCategoryPropertyDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProductCategoryPropertyBo extends BaseBo<ProductCategoryPropertyDto> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 编号
   */
  @Schema(description = "编号")
  private String code;

  /**
   * 名称
   */
  @Schema(description = "名称")
  private String name;

  /**
   * 是否必填
   */
  @Schema(description = "是否必填")
  private Boolean isRequired;

  /**
   * 录入类型
   */
  @Schema(description = "录入类型")
  private Integer columnType;

  /**
   * 数据类型
   */
  @Schema(description = "数据类型")
  private Integer columnDataType;

  /**
   * 属性类别
   */
  @Schema(description = "属性类别")
  private Integer propertyType;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  public ProductCategoryPropertyBo() {

  }

  public ProductCategoryPropertyBo(ProductCategoryPropertyDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<ProductCategoryPropertyDto> convert(ProductCategoryPropertyDto dto) {

    return super.convert(dto, ProductCategoryPropertyBo::getColumnType,
        ProductCategoryPropertyBo::getColumnDataType, ProductCategoryPropertyBo::getPropertyType);
  }

  @Override
  protected void afterInit(ProductCategoryPropertyDto dto) {

    this.id = dto.getPropertyId();
    this.columnType = dto.getColumnType().getCode();
    if (dto.getColumnDataType() != null) {
      this.columnDataType = dto.getColumnDataType().getCode();
    }
    this.propertyType = dto.getPropertyType().getCode();
  }
}
