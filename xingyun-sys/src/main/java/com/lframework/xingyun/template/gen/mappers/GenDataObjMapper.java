package com.lframework.xingyun.template.gen.mappers;

import com.lframework.xingyun.template.gen.entity.GenDataObj;
import com.lframework.xingyun.template.gen.vo.data.obj.GenDataObjSelectorVo;
import com.lframework.xingyun.template.gen.vo.data.obj.QueryGenDataObjVo;
import com.lframework.starter.web.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 数据对象 Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2022-09-17
 */
public interface GenDataObjMapper extends BaseMapper<GenDataObj> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<GenDataObj> query(@Param("vo") QueryGenDataObjVo vo);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<GenDataObj> selector(@Param("vo") GenDataObjSelectorVo vo);

  /**
   * 查询所有关联了数据实体的数据对象ID
   * @param entityId
   * @return
   */
  List<String> getRelaGenDataEntityIds(@Param("entityId") String entityId);

}
