package com.lframework.xingyun.template.inner.service.system;

import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.template.inner.entity.SysDept;
import com.lframework.xingyun.template.inner.vo.system.dept.CreateSysDeptVo;
import com.lframework.xingyun.template.inner.vo.system.dept.UpdateSysDeptVo;
import java.util.Collection;
import java.util.List;

public interface SysDeptService extends BaseMpService<SysDept> {

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

  /**
   * 根据编号查询
   *
   * @param code
   * @return
   */
  SysDept findByCode(String code);

  /**
   * 根据ID停用
   *
   * @param ids
   */
  void batchUnable(Collection<String> ids);

  /**
   * 根据ID启用
   *
   * @param ids
   */
  void batchEnable(Collection<String> ids);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateSysDeptVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateSysDeptVo vo);
}
