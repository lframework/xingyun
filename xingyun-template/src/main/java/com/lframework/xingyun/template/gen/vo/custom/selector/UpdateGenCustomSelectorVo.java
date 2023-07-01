package com.lframework.xingyun.template.gen.vo.custom.selector;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateGenCustomSelectorVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotBlank(message = "ID不能为空！")
  private String id;

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
  @ApiModelProperty("ID字段")
  private String idColumn;

  /**
   * ID字段关联ID
   */
  @ApiModelProperty("ID字段关联ID")
  private String idColumnRelaId;

  /**
   * 名称字段
   */
  @ApiModelProperty("名称字段")
  private String nameColumn;

  /**
   * 名称字段关联ID
   */
  @ApiModelProperty("名称字段关联ID")
  private String nameColumnRelaId;

  /**
   * 状态
   */
  @ApiModelProperty(value = "状态", required = true)
  @NotNull(message = "状态不能为空！")
  private Boolean available;
}
