package com.lframework.xingyun.template.gen.mappers;

import com.lframework.xingyun.template.gen.vo.custom.selector.GenCustomSelectorSelectorVo;
import com.lframework.xingyun.template.gen.vo.custom.selector.QueryGenCustomSelectorVo;
import com.lframework.xingyun.template.gen.entity.GenCustomSelector;
import com.lframework.starter.web.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 自定义选择器 Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2022-09-17
 */
public interface GenCustomSelectorMapper extends BaseMapper<GenCustomSelector> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<GenCustomSelector> query(@Param("vo") QueryGenCustomSelectorVo vo);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<GenCustomSelector> selector(@Param("vo") GenCustomSelectorSelectorVo vo);

  /**
   * 查询所有关联了自定义列表的自定义选择器ID
   *
   * @return
   */
  List<String> getRelaGenCustomListIds(@Param("customListId") String customListId);

}
