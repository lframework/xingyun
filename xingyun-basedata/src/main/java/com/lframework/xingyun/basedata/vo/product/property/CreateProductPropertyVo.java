package com.lframework.xingyun.basedata.vo.product.property;

import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import com.lframework.xingyun.basedata.enums.ColumnDataType;
import com.lframework.xingyun.basedata.enums.ColumnType;
import com.lframework.xingyun.basedata.enums.PropertyType;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateProductPropertyVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  @ApiModelProperty(value = "编号", required = true)
  @NotBlank(message = "请输入编号！")
  private String code;

  /**
   * 名称
   */
  @ApiModelProperty(value = "名称", required = true)
  @NotBlank(message = "请输入名称！")
  private String name;

  /**
   * 是否必填
   */
  @ApiModelProperty(value = "是否必填", required = true)
  @NotNull(message = "请选择是否必填！")
  private Boolean isRequired;

  /**
   * 录入类型
   */
  @ApiModelProperty(value = "录入类型", required = true)
  @NotNull(message = "请选择录入类型！")
  @IsEnum(enumClass = ColumnType.class, message = "请选择录入类型！")
  private Integer columnType;

  /**
   * 数据类型
   */
  @ApiModelProperty("数据类型")
  @IsEnum(enumClass = ColumnDataType.class, message = "请选择数据类型！")
  private Integer columnDataType;

  /**
   * 属性类别
   */
  @ApiModelProperty(value = "属性类别", required = true)
  @NotNull(message = "请选择属性类别！")
  @IsEnum(enumClass = PropertyType.class, message = "请选择属性类别！")
  private Integer propertyType;

  /**
   * 类目ID
   */
  @ApiModelProperty("类目ID")
  private List<String> categoryIds;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;
}
