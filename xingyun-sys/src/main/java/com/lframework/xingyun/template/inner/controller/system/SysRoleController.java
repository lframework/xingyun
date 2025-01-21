package com.lframework.xingyun.template.inner.controller.system;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.xingyun.template.inner.bo.system.role.GetSysRoleBo;
import com.lframework.xingyun.template.inner.bo.system.role.QuerySysRoleBo;
import com.lframework.xingyun.template.inner.entity.SysRole;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.xingyun.template.inner.service.system.SysRoleService;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.template.inner.vo.system.role.CreateSysRoleVo;
import com.lframework.xingyun.template.inner.vo.system.role.QuerySysRoleVo;
import com.lframework.xingyun.template.inner.vo.system.role.UpdateSysRoleVo;
import com.lframework.starter.web.annotations.security.HasPermission;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色管理
 *
 * @author zmj
 */
@Api(tags = "角色管理")
@Validated
@RestController
@RequestMapping("/system/role")
public class SysRoleController extends DefaultBaseController {

  @Autowired
  private SysRoleService sysRoleService;

  /**
   * 角色列表
   */
  @ApiOperation("角色列表")
  @HasPermission({"system:role:query","system:role:add","system:role:modify"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QuerySysRoleBo>> query(@Valid QuerySysRoleVo vo) {

    PageResult<SysRole> pageResult = sysRoleService.query(getPageIndex(vo),
        getPageSize(vo), vo);

    List<SysRole> datas = pageResult.getDatas();
    List<QuerySysRoleBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySysRoleBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询角色
   */
  @ApiOperation("查询角色")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"system:role:query","system:role:add","system:role:modify"})
  @GetMapping
  public InvokeResult<GetSysRoleBo> get(@NotBlank(message = "ID不能为空！") String id) {

    SysRole data = sysRoleService.findById(id);
    if (data == null) {
      throw new DefaultClientException("角色不存在！");
    }

    GetSysRoleBo result = new GetSysRoleBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 停用角色
   */
  @ApiOperation("停用角色")
  @HasPermission({"system:role:modify"})
  @PatchMapping("/unable")
  public InvokeResult<Void> unable(
      @ApiParam(value = "角色ID", required = true) @NotEmpty(message = "角色ID不能为空！") String id) {

    sysRoleService.unable(id);

    sysRoleService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 启用角色
   */
  @ApiOperation("启用角色")
  @HasPermission({"system:role:modify"})
  @PatchMapping("/enable")
  public InvokeResult<Void> enable(
      @ApiParam(value = "角色ID", required = true) @NotEmpty(message = "角色ID不能为空！") String id) {

    sysRoleService.enable(id);

    sysRoleService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 新增角色
   */
  @ApiOperation("新增角色")
  @HasPermission({"system:role:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateSysRoleVo vo) {

    sysRoleService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改角色
   */
  @ApiOperation("修改角色")
  @HasPermission({"system:role:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateSysRoleVo vo) {

    sysRoleService.update(vo);

    sysRoleService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }
}
