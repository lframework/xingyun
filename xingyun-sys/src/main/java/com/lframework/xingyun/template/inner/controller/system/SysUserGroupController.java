package com.lframework.xingyun.template.inner.controller.system;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.annotations.security.HasPermission;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.template.inner.bo.system.user.group.GetSysUserGroupBo;
import com.lframework.xingyun.template.inner.bo.system.user.group.QuerySysUserGroupBo;
import com.lframework.xingyun.template.inner.entity.SysUserGroup;
import com.lframework.xingyun.template.inner.service.system.SysUserGroupService;
import com.lframework.xingyun.template.inner.vo.system.user.group.CreateSysUserGroupVo;
import com.lframework.xingyun.template.inner.vo.system.user.group.QuerySysUserGroupVo;
import com.lframework.xingyun.template.inner.vo.system.user.group.UpdateSysUserGroupVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户组 Controller
 *
 * @author zmj
 */
@Api(tags = "用户组")
@Validated
@RestController
@RequestMapping("/sys/user/group")
public class SysUserGroupController extends DefaultBaseController {

  @Autowired
  private SysUserGroupService sysUserGroupService;

  /**
   * 查询列表
   */
  @ApiOperation("查询列表")
  @HasPermission({"system:notify-group:query"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QuerySysUserGroupBo>> query(
      @Valid QuerySysUserGroupVo vo) {

    PageResult<SysUserGroup> pageResult = sysUserGroupService.query(
        getPageIndex(vo),
        getPageSize(vo), vo);

    List<SysUserGroup> datas = pageResult.getDatas();
    List<QuerySysUserGroupBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySysUserGroupBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 根据ID查询
   */
  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"system:notify-group:query"})
  @GetMapping("/detail")
  public InvokeResult<GetSysUserGroupBo> getDetail(
      @NotBlank(message = "id不能为空！") String id) {

    SysUserGroup data = sysUserGroupService.findById(id);
    if (data == null) {
      throw new DefaultClientException("消息通知组不存在！");
    }

    GetSysUserGroupBo result = new GetSysUserGroupBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增
   */
  @ApiOperation("新增")
  @HasPermission({"system:notify-group:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid @RequestBody CreateSysUserGroupVo vo) {

    sysUserGroupService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改
   */
  @ApiOperation("修改")
  @HasPermission({"system:notify-group:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid @RequestBody UpdateSysUserGroupVo vo) {

    sysUserGroupService.update(vo);

    sysUserGroupService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }
}
