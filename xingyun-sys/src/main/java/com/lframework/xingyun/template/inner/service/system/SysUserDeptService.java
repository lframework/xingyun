package com.lframework.xingyun.template.inner.service.system;

import com.lframework.xingyun.template.inner.entity.SysUserDept;
import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.template.inner.vo.system.dept.SysUserDeptSettingVo;
import java.util.List;

public interface SysUserDeptService extends BaseMpService<SysUserDept> {

  /**
   * 设置部门
   *
   * @param vo
   */
  void setting(SysUserDeptSettingVo vo);

  /**
   * 根据用户ID查询
   *
   * @param userId
   * @return
   */
  List<SysUserDept> getByUserId(String userId);

  /**
   * 根据部门ID查询是否存在
   *
   * @param deptId
   * @return
   */
  Boolean hasByDeptId(String deptId);
}
