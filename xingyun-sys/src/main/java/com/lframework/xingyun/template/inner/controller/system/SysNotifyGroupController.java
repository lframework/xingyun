package com.lframework.xingyun.template.inner.controller.system;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.annotations.security.HasPermission;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.template.inner.bo.system.notify.GetSysNotifyGroupBo;
import com.lframework.xingyun.template.inner.bo.system.notify.QuerySysNotifyGroupBo;
import com.lframework.xingyun.template.inner.entity.SysNotifyGroup;
import com.lframework.xingyun.template.inner.service.system.SysNotifyGroupService;
import com.lframework.xingyun.template.inner.vo.system.notify.CreateSysNotifyGroupVo;
import com.lframework.xingyun.template.inner.vo.system.notify.QuerySysNotifyGroupVo;
import com.lframework.xingyun.template.inner.vo.system.notify.UpdateSysNotifyGroupVo;
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
 * 消息通知组 Controller
 *
 * @author zmj
 */
@Api(tags = "消息通知组")
@Validated
@RestController
@RequestMapping("/sys/notify/group")
public class SysNotifyGroupController extends DefaultBaseController {

  @Autowired
  private SysNotifyGroupService sysNotifyGroupService;

  /**
   * 查询列表
   */
  @ApiOperation("查询列表")
  @HasPermission({"system:notify-group:query"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QuerySysNotifyGroupBo>> query(
      @Valid QuerySysNotifyGroupVo vo) {

    PageResult<SysNotifyGroup> pageResult = sysNotifyGroupService.query(
        getPageIndex(vo),
        getPageSize(vo), vo);

    List<SysNotifyGroup> datas = pageResult.getDatas();
    List<QuerySysNotifyGroupBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySysNotifyGroupBo::new).collect(Collectors.toList());
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
  public InvokeResult<GetSysNotifyGroupBo> getDetail(
      @NotBlank(message = "id不能为空！") String id) {

    SysNotifyGroup data = sysNotifyGroupService.findById(id);
    if (data == null) {
      throw new DefaultClientException("消息通知组不存在！");
    }

    GetSysNotifyGroupBo result = new GetSysNotifyGroupBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增
   */
  @ApiOperation("新增")
  @HasPermission({"system:notify-group:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid @RequestBody CreateSysNotifyGroupVo vo) {

    sysNotifyGroupService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改
   */
  @ApiOperation("修改")
  @HasPermission({"system:notify-group:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid @RequestBody UpdateSysNotifyGroupVo vo) {

    sysNotifyGroupService.update(vo);

    sysNotifyGroupService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }
}
