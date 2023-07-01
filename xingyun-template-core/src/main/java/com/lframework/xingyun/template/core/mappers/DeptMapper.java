package com.lframework.xingyun.template.core.mappers;

import com.lframework.xingyun.template.core.dto.DeptDto;

/**
 * @author zmj
 */
public interface DeptMapper {

  /**
   * 根据ID查询 主要用于各个业务关联查询信息
   *
   * @param id
   * @return
   */
  DeptDto findById(String id);

  /**
   * 根据编号查询
   *
   * @param code
   * @return
   */
  DeptDto findByCode(String code);
}
