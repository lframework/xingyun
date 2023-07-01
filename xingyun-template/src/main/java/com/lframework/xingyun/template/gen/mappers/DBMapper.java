package com.lframework.xingyun.template.gen.mappers;

import com.lframework.starter.web.mapper.BaseMapper;

public interface DBMapper extends BaseMapper<Void> {

  /**
   * 查询当前数据库名称
   *
   * @return
   */
  String getCurrentDBName();
}
