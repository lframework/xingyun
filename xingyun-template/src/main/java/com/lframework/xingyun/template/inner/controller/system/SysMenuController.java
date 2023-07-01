package com.lframework.xingyun.template.inner.controller.system;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.exceptions.impl.InputErrorException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.xingyun.template.inner.entity.SysMenu;
import com.lframework.xingyun.template.inner.enums.system.SysMenuDisplay;
import com.lframework.xingyun.template.inner.bo.system.menu.GetSysMenuBo;
import com.lframework.xingyun.template.inner.bo.system.menu.QuerySysMenuBo;
import com.lframework.xingyun.template.inner.service.SysModuleTenantService;
import com.lframework.xingyun.template.inner.service.system.SysMenuService;
import com.lframework.xingyun.template.inner.vo.system.menu.CreateSysMenuVo;
import com.lframework.xingyun.template.inner.vo.system.menu.UpdateSysMenuVo;
import com.lframework.starter.web.common.tenant.TenantContextHolder;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.common.security.SecurityUtil;
import com.lframework.starter.web.utils.TenantUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import com.lframework.starter.web.annotations.security.HasPermission;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统菜单管理
 *
 * @author zmj
 */
@Api(tags = "系统菜单管理")
@Validated
@RestController
@RequestMapping("/system/menu")
public class SysMenuController extends DefaultBaseController {

  @Autowired
  private SysMenuService sysMenuService;

  @Autowired
  private SysModuleTenantService sysModuleTenantService;

  /**
   * 系统菜单列表
   */
  @ApiOperation("系统菜单列表")
  @HasPermission({"system:menu:query", "system:menu:add"})
  @GetMapping("/query")
  public InvokeResult<List<QuerySysMenuBo>> query() {

    // 先查询当前租户使用的module
    List<Integer> moduleIds = null;
    if (TenantUtil.enableTenant()) {
      moduleIds = sysModuleTenantService.getAvailableModuleIdsByTenantId(TenantContextHolder.getTenantId());
    }

    List<QuerySysMenuBo> results = CollectionUtil.emptyList();
    List<SysMenu> datas = sysMenuService.queryList(moduleIds);
    if (CollectionUtil.isNotEmpty(datas)) {
      results = datas.stream().map(QuerySysMenuBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  /**
   * 新增系统菜单
   */
  @ApiOperation("新增系统菜单")
  @HasPermission({"system:menu:add"})
  @PostMapping
  public InvokeResult<Void> add(@Valid CreateSysMenuVo vo) {

    this.validVo(vo);

    sysMenuService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 查看系统菜单
   */
  @ApiOperation("查看系统菜单")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"system:menu:query", "system:menu:add", "system:menu:modify"})
  @GetMapping
  public InvokeResult<GetSysMenuBo> get(@NotBlank(message = "ID不能为空！") String id) {

    SysMenu data = sysMenuService.findById(id);
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("菜单不存在！");
    }

    return InvokeResultBuilder.success(new GetSysMenuBo(data));
  }

  /**
   * 修改系统菜单
   */
  @ApiOperation("修改系统菜单")
  @HasPermission({"system:menu:modify"})
  @PutMapping
  public InvokeResult<Void> modify(@Valid UpdateSysMenuVo vo) {

    this.validVo(vo);

    sysMenuService.update(vo);

    sysMenuService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  /**
   * 根据ID删除
   */
  @ApiOperation("根据ID删除")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"system:menu:delete"})
  @DeleteMapping
  public InvokeResult<Void> delete(@NotBlank(message = "ID不能为空！") String id) {

    sysMenuService.deleteById(id);

    sysMenuService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 批量启用
   */
  @ApiOperation("批量启用")
  @HasPermission({"system:menu:modify"})
  @PatchMapping("/enable/batch")
  public InvokeResult<Void> batchEnable(
      @ApiParam(value = "菜单ID", required = true) @NotEmpty(message = "请选择需要启用的菜单！") @RequestBody List<String> ids) {

    sysMenuService.batchEnable(ids, SecurityUtil.getCurrentUser().getId());

    for (String id : ids) {
      sysMenuService.cleanCacheByKey(id);
    }

    return InvokeResultBuilder.success();
  }

  /**
   * 批量停用
   */
  @ApiOperation("批量停用")
  @HasPermission({"system:menu:modify"})
  @PatchMapping("/unable/batch")
  public InvokeResult<Void> batchUnable(
      @ApiParam(value = "菜单ID", required = true) @NotEmpty(message = "请选择需要停用的菜单！") @RequestBody List<String> ids) {

    sysMenuService.batchUnable(ids, SecurityUtil.getCurrentUser().getId());

    for (String id : ids) {
      sysMenuService.cleanCacheByKey(id);
    }

    return InvokeResultBuilder.success();
  }

  private void validVo(CreateSysMenuVo vo) {

    SysMenuDisplay sysMenuDisplay = EnumUtil.getByCode(SysMenuDisplay.class, vo.getDisplay());

    if (sysMenuDisplay == SysMenuDisplay.CATALOG || sysMenuDisplay == SysMenuDisplay.FUNCTION) {
      if (StringUtil.isBlank(vo.getName())) {
        throw new InputErrorException("请输入路由名称！");
      }

      if (StringUtil.isBlank(vo.getPath())) {
        throw new InputErrorException("请输入路由路径！");
      }

      if (ObjectUtil.isNull(vo.getHidden())) {
        throw new InputErrorException("请选择是否隐藏！");
      }

      if (sysMenuDisplay == SysMenuDisplay.FUNCTION) {
        if (vo.getComponentType() == null) {
          throw new InputErrorException("请选择组件类型！");
        }
        if (StringUtil.isBlank(vo.getComponent())) {
          throw new InputErrorException("请输入组件！");
        }
        if (ObjectUtil.isNull(vo.getNoCache())) {
          throw new InputErrorException("请选择是否不缓存！");
        }

        if (!StringUtil.isBlank(vo.getParentId())) {
          SysMenu parentMenu = sysMenuService.findById(vo.getParentId());

          if (parentMenu.getDisplay() != SysMenuDisplay.CATALOG) {
            throw new InputErrorException(
                "当菜单类型是“" + SysMenuDisplay.FUNCTION.getDesc() + "”时，父级菜单类型必须是“"
                    + SysMenuDisplay.CATALOG.getDesc() + "”！");
          }
        }
      }
    } else if (sysMenuDisplay == SysMenuDisplay.PERMISSION) {
      if (StringUtil.isBlank(vo.getParentId())) {
        throw new InputErrorException(
            "当菜单类型是“" + SysMenuDisplay.PERMISSION.getDesc() + "”时，父级菜单不能为空！");
      }

      SysMenu parentMenu = sysMenuService.findById(vo.getParentId());
      if (ObjectUtil.isNull(parentMenu)) {
        throw new InputErrorException(
            "当菜单类型是“" + SysMenuDisplay.PERMISSION.getDesc() + "”时，父级菜单不能为空！");
      }

      if (parentMenu.getDisplay() != SysMenuDisplay.FUNCTION) {
        throw new InputErrorException(
            "当菜单类型是“" + SysMenuDisplay.PERMISSION.getDesc() + "”时，父级菜单类型必须是“"
                + SysMenuDisplay.FUNCTION.getDesc() + "”！");
      }
      if (StringUtil.isBlank(vo.getPermission())) {
        throw new InputErrorException("请输入权限！");
      }
    }
  }
}
