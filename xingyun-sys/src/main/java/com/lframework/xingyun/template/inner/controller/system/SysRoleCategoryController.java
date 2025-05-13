package com.lframework.xingyun.template.inner.controller.system;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.template.inner.bo.system.role.category.GetSysRoleCategoryBo;
import com.lframework.xingyun.template.inner.bo.system.role.category.QuerySysRoleCategoryBo;
import com.lframework.xingyun.template.inner.entity.SysRoleCategory;
import com.lframework.xingyun.template.inner.service.system.SysRoleCategoryService;
import com.lframework.xingyun.template.inner.vo.system.role.category.CreateSysRoleCategoryVo;
import com.lframework.xingyun.template.inner.vo.system.role.category.UpdateSysRoleCategoryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色分类
 *
 * @author zmj
 */
@Api(tags = "角色分类")
@Validated
@RestController
@RequestMapping("/sys/role/category")
public class SysRoleCategoryController extends DefaultBaseController {

  @Autowired
  private SysRoleCategoryService sysRoleCategoryService;

  /**
   * 查询列表
   */
  @ApiOperation("查询列表")
  @GetMapping("/query")
  public InvokeResult<List<QuerySysRoleCategoryBo>> query() {
    List<SysRoleCategory> datas = sysRoleCategoryService.queryList();
    List<QuerySysRoleCategoryBo> results = CollectionUtil.emptyList();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySysRoleCategoryBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  /**
   * 根据ID查询
   */
  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @GetMapping
  public InvokeResult<GetSysRoleCategoryBo> get(@NotBlank(message = "ID不能为空！") String id) {

    SysRoleCategory data = sysRoleCategoryService.findById(id);
    if (data == null) {
      throw new DefaultClientException("角色分类不存在！");
    }

    GetSysRoleCategoryBo result = new GetSysRoleCategoryBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增角色分类
   */
  @ApiOperation("新增角色分类")
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateSysRoleCategoryVo vo) {

    sysRoleCategoryService.create(vo);

    sysRoleCategoryService.cleanCacheByKey("all");

    return InvokeResultBuilder.success();
  }

  /**
   * 修改角色分类
   */
  @ApiOperation("修改角色分类")
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateSysRoleCategoryVo vo) {

    sysRoleCategoryService.update(vo);

    sysRoleCategoryService.cleanCacheByKeys(Arrays.asList("all", vo.getId()));

    return InvokeResultBuilder.success();
  }

  @ApiOperation("删除角色分类")
  @DeleteMapping
  public InvokeResult<Void> delete(@NotBlank(message = "ID不能为空！") String id) {

    sysRoleCategoryService.deleteById(id);

    sysRoleCategoryService.cleanCacheByKeys(Arrays.asList("all", id));

    return InvokeResultBuilder.success();
  }
}
