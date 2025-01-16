package com.lframework.xingyun.template.inner.controller.system;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.xingyun.template.inner.bo.system.module.QuerySysModuleBo;
import com.lframework.xingyun.template.inner.entity.SysModule;
import com.lframework.xingyun.template.inner.entity.SysModuleTenant;
import com.lframework.xingyun.template.inner.service.SysModuleService;
import com.lframework.xingyun.template.inner.service.SysModuleTenantService;
import com.lframework.xingyun.template.inner.vo.system.module.SysModuleTenantVo;
import com.lframework.starter.web.annotations.security.HasPermission;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.TenantUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统模块管理
 *
 * @author zmj
 */
@Api(tags = "系统模块管理")
@Validated
@RestController
@RequestMapping("/system/module")
public class SysModuleController extends DefaultBaseController {

  @Autowired
  private SysModuleService sysModuleService;

  @Autowired
  private SysModuleTenantService sysModuleTenantService;

  /**
   * 查询列表
   */
  @ApiOperation("查询列表")
  @HasPermission(value = {"system:tenant:module"})
  @GetMapping("/query")
  public InvokeResult<List<QuerySysModuleBo>> query(@NotNull(message = "租户ID不能为空！") Integer tenantId) {

    if (!TenantUtil.enableTenant()) {
      return InvokeResultBuilder.success(Collections.emptyList());
    }

    // 先查询所有模块
    List<SysModule> sysModules = sysModuleService.list();
    List<QuerySysModuleBo> results = sysModules.stream().map(QuerySysModuleBo::new)
        .collect(Collectors.toList());
    if (CollectionUtil.isEmpty(results)) {
      return InvokeResultBuilder.success(results);
    }

    List<SysModuleTenant> sysModuleTenants = sysModuleTenantService.getByTenantId(tenantId);

    for (QuerySysModuleBo result : results) {
      SysModuleTenant sysModuleTenant = sysModuleTenants.stream()
          .filter(t -> ObjectUtil.equal(t.getModuleId(), result.getId())).findFirst().orElse(null);
      result.setEnabled(sysModuleTenant != null);
      if (sysModuleTenant == null) {
        continue;
      }
      result.setExpireTime(sysModuleTenant.getExpireTime());
    }

    return InvokeResultBuilder.success(results);
  }

  /**
   * 模块授权
   */
  @ApiOperation("模块授权")
  @HasPermission(value = {"system:tenant:module"})
  @PostMapping("/setting")
  public InvokeResult<Void> setting(
      @RequestBody(required = false) @Valid SysModuleTenantVo vo) {

    if (!TenantUtil.enableTenant()) {
      return InvokeResultBuilder.success();
    }
    sysModuleTenantService.setting(vo);

    sysModuleTenantService.cleanCacheByKey(vo.getTenantId());

    return InvokeResultBuilder.success();
  }
}
