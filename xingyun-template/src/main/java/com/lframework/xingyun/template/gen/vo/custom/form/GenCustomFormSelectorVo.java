package com.lframework.xingyun.template.gen.vo.custom.form;

import com.lframework.starter.web.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GenCustomFormSelectorVo extends PageVo {

  private static final long serialVersionUID = 1L;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 分类ID
   */
  @ApiModelProperty("分类ID")
  private String categoryId;

  /**
   * 是否对话框表单
   */
  @ApiModelProperty("是否对话框表单")
  private Boolean isDialog;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;
}
