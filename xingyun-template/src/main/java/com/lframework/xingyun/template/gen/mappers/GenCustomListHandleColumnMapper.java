package com.lframework.xingyun.template.gen.mappers;

import com.lframework.xingyun.template.gen.entity.GenCustomListHandleColumn;
import com.lframework.starter.web.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GenCustomListHandleColumnMapper extends BaseMapper<GenCustomListHandleColumn> {

  /**
   * 根据自定义列表ID查询
   *
   * @param customListId
   * @return
   */
  List<GenCustomListHandleColumn> getByCustomListId(@Param("customListId") String customListId);
}
