package com.lframework.xingyun.basedata.bo.product.property;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.ProductCategoryProperty;
import com.lframework.xingyun.basedata.entity.ProductProperty;
import com.lframework.xingyun.basedata.enums.PropertyType;
import com.lframework.xingyun.basedata.service.product.ProductCategoryPropertyService;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class GetProductPropertyBo extends BaseBo<ProductProperty> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 编号
   */
  @ApiModelProperty("编号")
  private String code;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 是否必填
   */
  @ApiModelProperty("是否必填")
  private Boolean isRequired;

  /**
   * 录入类型
   */
  @ApiModelProperty("录入类型")
  private Integer columnType;

  /**
   * 数据类型
   */
  @ApiModelProperty("数据类型")
  private Integer columnDataType;

  /**
   * 属性类别
   */
  @ApiModelProperty("属性类别")
  private Integer propertyType;

  /**
   * 分类
   */
  @ApiModelProperty("分类")
  private List<String> categories;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  public GetProductPropertyBo() {

  }

  public GetProductPropertyBo(ProductProperty dto) {

    super(dto);
  }

  @Override
  protected void afterInit(ProductProperty dto) {

    this.columnType = dto.getColumnType().getCode();
    this.propertyType = dto.getPropertyType().getCode();
    if (dto.getColumnDataType() != null) {
      this.columnDataType = dto.getColumnDataType().getCode();
    }

    if (dto.getPropertyType() == PropertyType.APPOINT) {
      ProductCategoryPropertyService productCategoryPropertyService = ApplicationUtil.getBean(
          ProductCategoryPropertyService.class);
      List<ProductCategoryProperty> categoryPropertyList = productCategoryPropertyService.getByPropertyId(
          dto.getId());

      this.categories = categoryPropertyList.stream().map(ProductCategoryProperty::getCategoryId)
          .collect(Collectors.toList());
    }
  }
}
