package com.lframework.xingyun.template.inner.mappers.system;

import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.core.annotations.sort.Sort;
import com.lframework.xingyun.core.annotations.sort.Sorts;
import com.lframework.xingyun.template.inner.entity.SysNotifyGroup;
import com.lframework.xingyun.template.inner.vo.system.notify.QuerySysNotifyGroupVo;
import com.lframework.xingyun.template.inner.vo.system.notify.SysNotifyGroupSelectorVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 消息通知组 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface SysNotifyGroupMapper extends BaseMapper<SysNotifyGroup> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  @Sorts({
      @Sort(value = "name", alias = "tb.name"),
      @Sort(value = "createTime", alias = "tb.create_time"),
  })
  List<SysNotifyGroup> query(@Param("vo") QuerySysNotifyGroupVo vo);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<SysNotifyGroup> selector(@Param("vo") SysNotifyGroupSelectorVo vo);
}
