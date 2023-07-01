package com.lframework.xingyun.template.gen.vo.custom.form;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateGenCustomFormVo implements BaseVo, Serializable {

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
   * 是否对话框表单
   */
  @ApiModelProperty(value = "是否对话框表单", required = true)
  @NotNull(message = "是否对话框表单不能为空！")
  private Boolean isDialog;

  /**
   * 对话框标题
   */
  @ApiModelProperty(value = "对话框标题")
  private String dialogTittle;

  /**
   * 对话框宽度
   */
  @ApiModelProperty(value = "对话框宽度")
  private String dialogWidth;

  /**
   * 前置提交脚本
   */
  @ApiModelProperty(value = "前置提交脚本")
  private String prefixSubmit;

  /**
   * 后置提交脚本
   */
  @ApiModelProperty(value = "后置提交脚本")
  private String suffixSubmit;

  /**
   * 是否需要查询
   */
  @ApiModelProperty(value = "是否需要查询", required = true)
  @NotNull(message = "是否需要查询不能为空！")
  private Boolean requireQuery;

  /**
   * 查询数据Bean名称
   */
  @ApiModelProperty(value = "查询数据Bean名称")
  private String queryBean;

  /**
   * 操作数据Bean名称
   */
  @ApiModelProperty(value = "操作数据Bean名称")
  private String handleBean;

  /**
   * 表单配置
   */
  @ApiModelProperty(value = "表单配置", required = true)
  @NotBlank(message = "表单配置不能为空！")
  private String formConfig;
}
