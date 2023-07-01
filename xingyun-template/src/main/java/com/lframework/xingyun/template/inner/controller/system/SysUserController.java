package com.lframework.xingyun.template.inner.controller.system;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.starter.web.annotations.security.HasPermission;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.template.inner.bo.system.user.GetSysUserBo;
import com.lframework.xingyun.template.inner.bo.system.user.QuerySysUserBo;
import com.lframework.xingyun.template.core.entity.SysUser;
import com.lframework.xingyun.template.inner.service.system.SysUserService;
import com.lframework.xingyun.template.inner.vo.system.user.CreateSysUserVo;
import com.lframework.xingyun.template.inner.vo.system.user.QuerySysUserVo;
import com.lframework.xingyun.template.inner.vo.system.user.UpdateSysUserVo;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理
 *
 * @author zmj
 */
@Api(tags = "用户管理")
@Validated
@RestController
@RequestMapping("/system/user")
public class SysUserController extends DefaultBaseController {

  @Autowired
  private SysUserService sysUserService;

  /**
   * 用户列表
   */
  @ApiOperation("用户列表")
  @HasPermission({"system:user:query", "system:user:add", "system:user:modify"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QuerySysUserBo>> query(@Valid QuerySysUserVo vo) {

    PageResult<SysUser> pageResult = sysUserService.query(getPageIndex(vo),
        getPageSize(vo), vo);

    List<SysUser> datas = pageResult.getDatas();
    List<QuerySysUserBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySysUserBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询用户
   */
  @ApiOperation("查询用户")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"system:user:query", "system:user:add", "system:user:modify"})
  @GetMapping
  public InvokeResult<GetSysUserBo> get(@NotBlank(message = "ID不能为空！") String id) {

    SysUser data = sysUserService.findById(id);
    if (data == null) {
      throw new DefaultClientException("用户不存在！");
    }

    GetSysUserBo result = new GetSysUserBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 批量停用用户
   */
  @ApiOperation("批量停用用户")
  @HasPermission({"system:user:modify"})
  @PatchMapping("/unable/batch")
  public InvokeResult<Void> batchUnable(
      @ApiParam(value = "用户ID", required = true) @NotEmpty(message = "请选择需要停用的用户！") @RequestBody List<String> ids) {

    sysUserService.batchUnable(ids);

    for (String id : ids) {
      sysUserService.cleanCacheByKey(id);
    }

    return InvokeResultBuilder.success();
  }

  /**
   * 批量启用用户
   */
  @ApiOperation("批量启用用户")
  @HasPermission({"system:user:modify"})
  @PatchMapping("/enable/batch")
  public InvokeResult<Void> batchEnable(
      @ApiParam(value = "用户ID", required = true) @NotEmpty(message = "请选择需要启用的用户！") @RequestBody List<String> ids) {

    sysUserService.batchEnable(ids);

    for (String id : ids) {
      sysUserService.cleanCacheByKey(id);
    }

    return InvokeResultBuilder.success();
  }

  /**
   * 新增用户
   */
  @ApiOperation("新增用户")
  @HasPermission({"system:user:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid @RequestBody CreateSysUserVo vo) {

    sysUserService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改用户
   */
  @ApiOperation("修改用户")
  @HasPermission({"system:user:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid @RequestBody UpdateSysUserVo vo) {

    sysUserService.update(vo);

    sysUserService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  /**
   * 解锁用户
   */
  @ApiOperation("解锁用户")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"system:user:modify"})
  @PatchMapping("unlock")
  public InvokeResult<Void> unlock(@NotBlank(message = "ID不能为空！") String id) {

    sysUserService.unlockById(id);

    sysUserService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }
}
