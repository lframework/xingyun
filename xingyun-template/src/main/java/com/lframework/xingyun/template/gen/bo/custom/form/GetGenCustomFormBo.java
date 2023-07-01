package com.lframework.xingyun.template.gen.bo.custom.form;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.xingyun.template.gen.entity.GenCustomForm;
import com.lframework.xingyun.template.gen.entity.GenCustomFormCategory;
import com.lframework.xingyun.template.gen.service.GenCustomFormCategoryService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetGenCustomFormBo extends BaseBo<GenCustomForm> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

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
   * 分类名称
   */
  @ApiModelProperty("分类名称")
  private String categoryName;

  /**
   * 是否对话框表单
   */
  @ApiModelProperty("是否对话框表单")
  private Boolean isDialog;

  /**
   * 对话框标题
   */
  @ApiModelProperty("对话框标题")
  private String dialogTittle;

  /**
   * 对话框宽度
   */
  @ApiModelProperty("对话框宽度")
  private String dialogWidth;

  /**
   * 表单配置
   */
  @ApiModelProperty("表单配置")
  private String formConfig;

  /**
   * 前置提交脚本
   */
  @ApiModelProperty("前置提交脚本")
  private String prefixSubmit;

  /**
   * 后置提交脚本
   */
  @ApiModelProperty("后置提交脚本")
  private String suffixSubmit;

  /**
   * 是否需要查询
   */
  @ApiModelProperty("是否需要查询")
  private Boolean requireQuery;

  /**
   * 查询数据Bean名称
   */
  @ApiModelProperty("查询数据Bean名称")
  private String queryBean;

  /**
   * 操作数据Bean名称
   */
  @ApiModelProperty("操作数据Bean名称")
  private String handleBean;

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

  public GetGenCustomFormBo() {
  }

  public GetGenCustomFormBo(GenCustomForm dto) {
    super(dto);
  }

  @Override
  protected void afterInit(GenCustomForm dto) {
    if (!StringUtil.isBlank(dto.getCategoryId())) {
      GenCustomFormCategoryService genCustomFormCategoryService = ApplicationUtil.getBean(
          GenCustomFormCategoryService.class);
      GenCustomFormCategory category = genCustomFormCategoryService
          .findById(dto.getCategoryId());
      this.categoryName = category.getName();
    }
  }
}
