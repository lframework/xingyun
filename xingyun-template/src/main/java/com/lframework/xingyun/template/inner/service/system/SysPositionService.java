package com.lframework.xingyun.template.inner.service.system;

import com.lframework.xingyun.template.inner.entity.SysPosition;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.template.inner.vo.system.position.CreateSysPositionVo;
import com.lframework.xingyun.template.inner.vo.system.position.QuerySysPositionVo;
import com.lframework.xingyun.template.inner.vo.system.position.SysPositionSelectorVo;
import com.lframework.xingyun.template.inner.vo.system.position.UpdateSysPositionVo;
import java.util.Collection;

public interface SysPositionService extends BaseMpService<SysPosition> {

  /**
   * 查询全部岗位信息
   *
   * @return
   */
  PageResult<SysPosition> query(Integer pageIndex, Integer pageSize,
      QuerySysPositionVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SysPosition findById(String id);

  /**
   * 选择器
   *
   * @return
   */
  PageResult<SysPosition> selector(Integer pageIndex, Integer pageSize,
      SysPositionSelectorVo vo);

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
  String create(CreateSysPositionVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateSysPositionVo vo);
}
