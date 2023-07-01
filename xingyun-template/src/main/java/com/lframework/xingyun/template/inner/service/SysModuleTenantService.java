package com.lframework.xingyun.template.inner.service;

import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.template.inner.entity.SysModuleTenant;
import com.lframework.xingyun.template.inner.vo.system.module.SysModuleTenantVo;
import java.util.List;

public interface SysModuleTenantService extends BaseMpService<SysModuleTenant> {

  /**
   * 根据租户ID查询可用模块ID
   *
   * @param tenantId
   * @return
   */
  List<Integer> getAvailableModuleIdsByTenantId(Integer tenantId);

  /**
   * 根据租户ID查询
   * @param tenantId
   * @return
   */
  List<SysModuleTenant> getByTenantId(Integer tenantId);

  /**
   * 设置模块
   * @param vo
   */
  void setting(SysModuleTenantVo vo);
}
