package com.lframework.xingyun.template.gen.service;

import com.lframework.xingyun.template.gen.entity.GenCustomListToolbar;
import com.lframework.starter.web.service.BaseMpService;
import java.util.List;

public interface GenCustomListToolbarService extends BaseMpService<GenCustomListToolbar> {

  /**
   * 根据自定义列表ID查询
   *
   * @param customListId
   * @return
   */
  List<GenCustomListToolbar> getByCustomListId(String customListId);

  /**
   * 根据自定义列表ID删除
   *
   * @param customListId
   */
  void deleteByCustomListId(String customListId);
}
