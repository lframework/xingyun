package com.lframework.xingyun.template.gen.service;

import com.lframework.xingyun.template.gen.entity.GenDataObjQueryDetail;
import com.lframework.starter.web.service.BaseMpService;
import java.util.List;

public interface GenDataObjQueryDetailService extends BaseMpService<GenDataObjQueryDetail> {

  /**
   * 根据数据对象ID查询
   *
   * @param objId
   * @return
   */
  List<GenDataObjQueryDetail> getByObjId(String objId);

  /**
   * 根据数据对象ID删除
   *
   * @param objId
   */
  void deleteByObjId(String objId);
}
