package com.lframework.xingyun.template.gen.components.custom.form;

import java.io.Serializable;
import lombok.Data;

@Data
public class CustomFormConfig implements Serializable {

  public static final String CACHE_NAME = "CustomFormConfig";

  private static final long serialVersionUID = 1L;

  /**
   * 是否对话框表单
   */
  private Boolean isDialog;

  /**
   * 对话框宽度
   */
  private String dialogWidth;

  /**
   * 对话框标题
   */
  private String dialogTittle;

  /**
   * 表单配置
   */
  private String formConfig;

  /**
   * 提交前置脚本
   */
  private String prefixSubmit;

  /**
   * 提交后置脚本
   */
  private String suffixSubmit;

  /**
   * 是否需要查询
   */
  private Boolean requireQuery;

  /**
   * 操作数据Bean
   */
  private String handleBean;
}
