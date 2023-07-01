package com.lframework.xingyun.template.inner.service.system;

import com.lframework.xingyun.template.inner.entity.SysUserPosition;
import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.template.inner.vo.system.position.SysUserPositionSettingVo;
import java.util.List;

public interface SysUserPositionService extends BaseMpService<SysUserPosition> {

  /**
   * 设置岗位
   *
   * @param vo
   */
  void setting(SysUserPositionSettingVo vo);

  /**
   * 根据用户ID查询
   *
   * @param userId
   * @return
   */
  List<SysUserPosition> getByUserId(String userId);
}
