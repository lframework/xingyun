package com.lframework.xingyun.template.inner.mappers.system;

import com.lframework.xingyun.core.annotations.sort.Sort;
import com.lframework.xingyun.core.annotations.sort.Sorts;
import com.lframework.xingyun.template.inner.entity.SysDataDicItem;
import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.template.inner.vo.system.dic.item.QuerySysDataDicItemVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 数据字典值 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface SysDataDicItemMapper extends BaseMapper<SysDataDicItem> {

  /**
   * 查询列表
   *
   * @return
   */
  @Sorts({
      @Sort(value = "code", alias = "tb", autoParse = true),
      @Sort(value = "name", alias = "tb", autoParse = true),
  })
  List<SysDataDicItem> query(@Param("vo") QuerySysDataDicItemVo vo);
}
