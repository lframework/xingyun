package com.lframework.xingyun.template.gen.components;

import com.lframework.xingyun.template.gen.enums.GenQueryType;

public interface QueryParamsColumnConfig {

  /**
   * 查询类型
   *
   * @return
   */
  GenQueryType getQueryType();

  /**
   * 排序
   *
   * @return
   */
  Integer getOrderNo();
}
