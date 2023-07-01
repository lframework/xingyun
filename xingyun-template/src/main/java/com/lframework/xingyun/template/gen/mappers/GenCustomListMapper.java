package com.lframework.xingyun.template.gen.mappers;

import com.lframework.xingyun.template.gen.vo.custom.list.GenCustomListSelectorVo;
import com.lframework.xingyun.template.gen.vo.custom.list.QueryGenCustomListVo;
import com.lframework.xingyun.template.gen.entity.GenCustomList;
import com.lframework.starter.web.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 自定义列表 Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2022-09-17
 */
public interface GenCustomListMapper extends BaseMapper<GenCustomList> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<GenCustomList> query(@Param("vo") QueryGenCustomListVo vo);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<GenCustomList> selector(@Param("vo") GenCustomListSelectorVo vo);

  /**
   * 查询所有关联了数据对象的自定义列表ID
   *
   * @param objId
   * @return
   */
  List<String> getRelaGenDataObjIds(@Param("objId") String objId);

  /**
   * 查询所有关联了数据实体的自定义列表ID
   *
   * @param entityId
   * @return
   */
  List<String> getRelaGenDataEntityIds(@Param("entityId") String entityId);

}
