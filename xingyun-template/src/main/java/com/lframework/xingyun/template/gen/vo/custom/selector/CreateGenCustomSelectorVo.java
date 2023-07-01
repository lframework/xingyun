package com.lframework.xingyun.template.gen.vo.custom.selector;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateGenCustomSelectorVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 名称
   */
  @ApiModelProperty(value = "名称", required = true)
  @NotBlank(message = "名称不能为空！")
  private String name;

  /**
   * 分类ID
   */
  @ApiModelProperty("分类ID")
  private String categoryId;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 自定义列表ID
   */
  @ApiModelProperty(value = "自定义列表ID", required = true)
  @NotBlank(message = "自定义列表ID不能为空！")
  private String customListId;

  /**
   * 对话框标题
   */
  @ApiModelProperty(value = "对话框标题")
  private String dialogTittle;

  /**
   * 对话框宽度
   */
  @ApiModelProperty(value = "对话框宽度", required = true)
  @NotBlank(message = "对话框宽度不能为空！")
  private String dialogWidth;

  /**
   * 占位符
   */
  @ApiModelProperty("占位符")
  private String placeholder;

  /**
   * ID字段
   */
  @ApiModelProperty(value = "ID字段", required = true)
  @NotBlank(message = "ID字段不能为空！")
  private String idColumn;

  /**
   * ID字段关联ID
   */
  @ApiModelProperty(value = "ID字段关联ID", required = true)
  @NotBlank(message = "ID字段不能为空！")
  private String idColumnRelaId;

  /**
   * 名称字段
   */
  @ApiModelProperty(value = "名称字段", required = true)
  @NotBlank(message = "名称字段不能为空！")
  private String nameColumn;

  /**
   * 名称字段关联ID
   */
  @ApiModelProperty(value = "名称字段关联ID", required = true)
  @NotBlank(message = "名称字段不能为空！")
  private String nameColumnRelaId;
}
