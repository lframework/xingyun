package com.lframework.xingyun.basedata.bo.product.property;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.ProductCategoryPropertyRelation;
import com.lframework.xingyun.basedata.entity.ProductCategoryPropertyDefinition;
import com.lframework.xingyun.basedata.enums.PropertyType;
import com.lframework.xingyun.basedata.service.product.ProductCategoryPropertyRelationService;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class GetProductCategoryPropertyDefinitionBo extends BaseBo<ProductCategoryPropertyDefinition> {

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
   * 分类
   */
  @Schema(description = "分类")
  private List<String> categories;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  public GetProductCategoryPropertyDefinitionBo() {

  }

  public GetProductCategoryPropertyDefinitionBo(ProductCategoryPropertyDefinition dto) {

    super(dto);
  }

  @Override
  protected void afterInit(ProductCategoryPropertyDefinition dto) {

    this.columnType = dto.getColumnType().getCode();
    this.propertyType = dto.getPropertyType().getCode();
    if (dto.getColumnDataType() != null) {
      this.columnDataType = dto.getColumnDataType().getCode();
    }

    if (dto.getPropertyType() == PropertyType.APPOINT) {
      ProductCategoryPropertyRelationService ProductCategoryPropertyRelationService = ApplicationUtil.getBean(
          ProductCategoryPropertyRelationService.class);
      List<ProductCategoryPropertyRelation> categoryPropertyList = ProductCategoryPropertyRelationService.getByPropertyId(
          dto.getId());

      this.categories = categoryPropertyList.stream().map(ProductCategoryPropertyRelation::getCategoryId)
          .collect(Collectors.toList());
    }
  }
}
