package com.lframework.xingyun.template.gen.components;

import com.lframework.xingyun.template.gen.enums.GenConvertType;

public interface Table {

  /**
   * 获取数据库名
   *
   * @return
   */
  String getSchema();

  /**
   * 获取数据表名
   *
   * @return
   */
  String getTableName();

  /**
   * 获取数据表备注
   *
   * @return
   */
  String getComment();

  /**
   * 获取转换方式
   *
   * @return
   */
  GenConvertType getConvertType();
}
