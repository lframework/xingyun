package com.lframework.xingyun.basedata.vo.product.property;

import com.lframework.starter.web.core.components.validation.IsCode;
import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.xingyun.basedata.enums.ColumnDataType;
import com.lframework.xingyun.basedata.enums.ColumnType;
import com.lframework.xingyun.basedata.enums.PropertyType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateProductCategoryPropertyDefinitionVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED)
  @IsCode
  @NotBlank(message = "请输入编号！")
  private String code;

  /**
   * 名称
   */
  @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "请输入名称！")
  private String name;

  /**
   * 是否必填
   */
  @Schema(description = "是否必填", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "请选择是否必填！")
  private Boolean isRequired;

  /**
   * 录入类型
   */
  @Schema(description = "录入类型", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "请选择录入类型！")
  @IsEnum(enumClass = ColumnType.class, message = "请选择录入类型！")
  private Integer columnType;

  /**
   * 数据类型
   */
  @Schema(description = "数据类型")
  @IsEnum(enumClass = ColumnDataType.class, message = "请选择数据类型！")
  private Integer columnDataType;

  /**
   * 属性类别
   */
  @Schema(description = "属性类别")
  @IsEnum(enumClass = PropertyType.class, message = "请选择属性类别！")
  private Integer propertyType;

  /**
   * 分类ID
   */
  @Schema(description = "分类ID")
  private List<String> categoryIds;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;
}
