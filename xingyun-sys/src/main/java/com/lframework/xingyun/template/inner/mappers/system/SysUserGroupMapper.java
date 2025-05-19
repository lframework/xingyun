package com.lframework.xingyun.template.inner.mappers.system;

import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.core.annotations.sort.Sort;
import com.lframework.xingyun.core.annotations.sort.Sorts;
import com.lframework.xingyun.template.inner.entity.SysUserGroup;
import com.lframework.xingyun.template.inner.vo.system.user.group.QuerySysUserGroupVo;
import com.lframework.xingyun.template.inner.vo.system.user.group.SysUserGroupSelectorVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户组 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface SysUserGroupMapper extends BaseMapper<SysUserGroup> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  @Sorts({
      @Sort(value = "code", alias = "tb.code"),
      @Sort(value = "name", alias = "tb.name"),
      @Sort(value = "createTime", alias = "tb.create_time"),
  })
  List<SysUserGroup> query(@Param("vo") QuerySysUserGroupVo vo);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<SysUserGroup> selector(@Param("vo") SysUserGroupSelectorVo vo);
}
