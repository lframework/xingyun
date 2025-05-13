package com.lframework.xingyun.template.inner.mappers.system;

import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.template.inner.entity.SysRoleCategory;
import com.lframework.xingyun.template.inner.vo.system.role.category.SysRoleCategorySelectorVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 角色分类 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface SysRoleCategoryMapper extends BaseMapper<SysRoleCategory> {

  /**
   * 查询列表
   *
   * @return
   */
  List<SysRoleCategory> query();

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<SysRoleCategory> selector(@Param("vo") SysRoleCategorySelectorVo vo);
}
