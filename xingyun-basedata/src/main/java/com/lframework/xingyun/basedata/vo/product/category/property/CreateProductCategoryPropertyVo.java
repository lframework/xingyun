package com.lframework.xingyun.basedata.vo.product.category.property;

import com.lframework.starter.web.core.components.validation.IsCode;
import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.xingyun.basedata.enums.ColumnType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.Data;

@Data
public class CreateProductCategoryPropertyVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 分类ID
   */
  @Schema(description = "分类ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "商品分类不能为空！")
  private String categoryId;

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
   * 备注
   */
  @Schema(description = "备注")
  private String description;
}
