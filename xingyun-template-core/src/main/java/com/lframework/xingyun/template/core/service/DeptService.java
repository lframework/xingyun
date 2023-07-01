package com.lframework.xingyun.template.core.service;

import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.template.core.dto.DeptDto;

/**
 * 部门Service
 *
 * @author zmj
 */
public interface DeptService extends BaseService {

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
