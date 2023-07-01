package com.lframework.xingyun.template.inner.controller.system;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.xingyun.template.inner.entity.SysPosition;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.xingyun.template.inner.service.system.SysPositionService;
import com.lframework.xingyun.template.inner.vo.system.position.CreateSysPositionVo;
import com.lframework.xingyun.template.inner.vo.system.position.QuerySysPositionVo;
import com.lframework.xingyun.template.inner.vo.system.position.UpdateSysPositionVo;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.template.inner.bo.system.position.GetSysPositionBo;
import com.lframework.xingyun.template.inner.bo.system.position.QuerySysPositionBo;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 岗位管理
 *
 * @author zmj
 */
@Api(tags = "岗位管理")
@Validated
@RestController
@RequestMapping("/system/position")
public class SysPositionController extends DefaultBaseController {

  @Autowired
  private SysPositionService sysPositionService;

  /**
   * 岗位列表
   */
  @ApiOperation("岗位列表")
  @HasPermission({"system:position:query","system:position:add","system:position:modify"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QuerySysPositionBo>> query(@Valid QuerySysPositionVo vo) {

    PageResult<SysPosition> pageResult = sysPositionService.query(getPageIndex(vo),
        getPageSize(vo), vo);

    List<SysPosition> datas = pageResult.getDatas();
    List<QuerySysPositionBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySysPositionBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询岗位
   */
  @ApiOperation("查询岗位")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"system:position:query","system:position:add","system:position:modify"})
  @GetMapping
  public InvokeResult<GetSysPositionBo> get(@NotBlank(message = "ID不能为空！") String id) {

    SysPosition data = sysPositionService.findById(id);
    if (data == null) {
      throw new DefaultClientException("岗位不存在！");
    }

    GetSysPositionBo result = new GetSysPositionBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 批量停用岗位
   */
  @ApiOperation("批量停用岗位")
  @HasPermission({"system:position:modify"})
  @PatchMapping("/unable/batch")
  public InvokeResult<Void> batchUnable(
      @ApiParam(value = "岗位ID", required = true) @NotEmpty(message = "请选择需要停用的岗位！") @RequestBody List<String> ids) {

    sysPositionService.batchUnable(ids);

    for (String id : ids) {
      sysPositionService.cleanCacheByKey(id);
    }

    return InvokeResultBuilder.success();
  }

  /**
   * 批量启用岗位
   */
  @ApiOperation("批量启用岗位")
  @HasPermission({"system:position:modify"})
  @PatchMapping("/enable/batch")
  public InvokeResult<Void> batchEnable(
      @ApiParam(value = "岗位ID", required = true) @NotEmpty(message = "请选择需要启用的岗位！") @RequestBody List<String> ids) {

    sysPositionService.batchEnable(ids);

    for (String id : ids) {
      sysPositionService.cleanCacheByKey(id);
    }

    return InvokeResultBuilder.success();
  }

  /**
   * 新增岗位
   */
  @ApiOperation("新增岗位")
  @HasPermission({"system:position:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateSysPositionVo vo) {

    sysPositionService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改岗位
   */
  @ApiOperation("修改岗位")
  @HasPermission({"system:position:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateSysPositionVo vo) {

    sysPositionService.update(vo);

    sysPositionService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }
}
