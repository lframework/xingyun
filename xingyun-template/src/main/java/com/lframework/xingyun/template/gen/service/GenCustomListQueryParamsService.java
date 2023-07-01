package com.lframework.xingyun.template.gen.service;

import com.lframework.xingyun.template.gen.entity.GenCustomListQueryParams;
import com.lframework.starter.web.service.BaseMpService;
import java.util.List;

public interface GenCustomListQueryParamsService extends BaseMpService<GenCustomListQueryParams> {

  /**
   * 根据自定义列表ID查询
   * @param customListId
   * @return
   */
  List<GenCustomListQueryParams> getByCustomListId(String customListId);

  /**
   * 根据自定义列表ID删除
   * @param customListId
   */
  void deleteByCustomListId(String customListId);

}
