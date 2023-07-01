package com.lframework.xingyun.template.gen.components.custom.selector;

import java.io.Serializable;
import lombok.Data;

@Data
public class CustomSelectorConfig implements Serializable {

  public static final String CACHE_NAME = "CustomSelectorConfig";

  private static final long serialVersionUID = 1L;

  /**
   * 自定义列表ID
   */
  private String customListId;

  /**
   * ID字段
   */
  private String idColumn;

  /**
   * 名称字段
   */
  private String nameColumn;

  /**
   * 占位符
   */
  private String placeholder;

  /**
   * 对话框标题
   */
  private String dialogTittle;

  /**
   * 对话框宽度
   */
  private String dialogWidth;
}
