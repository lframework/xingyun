package com.lframework.xingyun.template.inner.mappers.system;

import com.lframework.xingyun.template.inner.entity.SysDept;
import com.lframework.starter.web.mapper.BaseMapper;
import java.util.List;

/**
 * <p>
 * 部门 Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-06-26
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {

  /**
   * 选择器
   *
   * @return
   */
  List<SysDept> selector();

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SysDept findById(String id);
}
