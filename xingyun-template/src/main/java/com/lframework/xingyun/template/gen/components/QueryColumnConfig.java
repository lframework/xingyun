package com.lframework.xingyun.template.gen.components;

import com.lframework.xingyun.template.gen.enums.GenQueryWidthType;

public interface QueryColumnConfig {

  /**
   * 宽度类型
   *
   * @return
   */
  GenQueryWidthType getWidthType();

  /**
   * 宽度
   *
   * @return
   */
  Integer getWidth();

  /**
   * 页面是否排序
   *
   * @return
   */
  Boolean getSortable();

  /**
   * 排序
   *
   * @return
   */
  Integer getOrderNo();
}
