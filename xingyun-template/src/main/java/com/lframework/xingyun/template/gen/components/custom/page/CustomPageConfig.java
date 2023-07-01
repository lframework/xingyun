package com.lframework.xingyun.template.gen.components.custom.page;

import java.io.Serializable;
import lombok.Data;

@Data
public class CustomPageConfig implements Serializable {

  public static final String CACHE_NAME = "CustomPageConfig";

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private Integer id;

  /**
   * 组件配置
   */
  private String componentConfig;
}
