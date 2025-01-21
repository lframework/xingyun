package com.lframework.xingyun.template.inner.controller.system;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.annotations.security.HasPermission;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.core.service.RecursionMappingService;
import com.lframework.xingyun.template.inner.bo.system.dept.GetSysDeptBo;
import com.lframework.xingyun.template.inner.bo.system.dept.SysDeptTreeBo;
import com.lframework.xingyun.template.inner.entity.SysDept;
import com.lframework.xingyun.template.inner.enums.system.SysDeptNodeType;
import com.lframework.xingyun.template.inner.service.system.SysDeptService;
import com.lframework.xingyun.template.inner.vo.system.dept.CreateSysDeptVo;
import com.lframework.xingyun.template.inner.vo.system.dept.UpdateSysDeptVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
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
 * 部门管理
 *
 * @author zmj
 */
@Api(tags = "部门管理")
@Validated
@RestController
@RequestMapping("/system/dept")
public class SysDeptController extends DefaultBaseController {

  @Autowired
  private SysDeptService sysDeptService;

  @Autowired
  private RecursionMappingService recursionMappingService;

  /**
   * 部门树形菜单数据
   */
  @ApiOperation("部门树形菜单数据")
  @HasPermission({"system:dept:query", "system:dept:add", "system:dept:modify"})
  @GetMapping("/trees")
  public InvokeResult<List<SysDeptTreeBo>> trees() {

    List<SysDept> datas = sysDeptService.selector();
    if (CollectionUtil.isEmpty(datas)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<SysDeptTreeBo> results = datas.stream().map(SysDeptTreeBo::new)
        .collect(Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  /**
   * 查询部门
   */
  @ApiOperation("部门详情")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"system:dept:query", "system:dept:add", "system:dept:modify"})
  @GetMapping
  public InvokeResult<GetSysDeptBo> get(@NotBlank(message = "ID不能为空！") String id) {

    SysDept data = sysDeptService.findById(id);
    if (data == null) {
      throw new DefaultClientException("部门不存在！");
    }

    GetSysDeptBo result = new GetSysDeptBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 停用部门
   */
  @ApiOperation("停用部门")
  @HasPermission({"system:dept:modify"})
  @PatchMapping("/unable")
  public InvokeResult<Void> unable(
      @ApiParam(value = "部门ID", required = true) @NotEmpty(message = "部门ID不能为空！") String id) {

    sysDeptService.unable(id);

    List<String> batchIds = new ArrayList<>();
    batchIds.add(id);

    List<String> tmp = recursionMappingService.getNodeChildIds(id,
        ApplicationUtil.getBean(SysDeptNodeType.class));
    batchIds.addAll(tmp);
    sysDeptService.cleanCacheByKeys(batchIds);

    return InvokeResultBuilder.success();
  }

  /**
   * 启用部门
   */
  @ApiOperation("启用部门")
  @HasPermission({"system:dept:modify"})
  @PatchMapping("/enable")
  public InvokeResult<Void> enable(
      @ApiParam(value = "部门ID", required = true) @NotEmpty(message = "部门ID不能为空！") String id) {

    sysDeptService.enable(id);

    List<String> batchIds = new ArrayList<>();
    batchIds.add(id);

    List<String> tmp = recursionMappingService.getNodeParentIds(id,
        ApplicationUtil.getBean(SysDeptNodeType.class));
    batchIds.addAll(tmp);
    sysDeptService.cleanCacheByKeys(batchIds);

    return InvokeResultBuilder.success();
  }

  /**
   * 新增部门
   */
  @ApiOperation("新增部门")
  @HasPermission({"system:dept:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateSysDeptVo vo) {

    sysDeptService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改部门
   */
  @ApiOperation("修改部门")
  @HasPermission({"system:dept:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateSysDeptVo vo) {

    sysDeptService.update(vo);

    List<String> batchIds = new ArrayList<>();
    batchIds.add(vo.getId());

    if (vo.getAvailable()) {
      List<String> ids = recursionMappingService.getNodeParentIds(vo.getId(),
          ApplicationUtil.getBean(SysDeptNodeType.class));
      batchIds.addAll(ids);
    } else {
      List<String> ids = recursionMappingService.getNodeChildIds(vo.getId(),
          ApplicationUtil.getBean(SysDeptNodeType.class));
      batchIds.addAll(ids);
    }

    sysDeptService.cleanCacheByKeys(batchIds);

    return InvokeResultBuilder.success();
  }
}
