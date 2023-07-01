package com.lframework.xingyun.template.inner.controller.system;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.xingyun.template.inner.bo.system.dic.category.QuerySysDataDicCategoryBo;
import com.lframework.xingyun.template.inner.entity.SysDataDicCategory;
import com.lframework.xingyun.template.inner.service.system.SysDataDicCategoryService;
import com.lframework.xingyun.template.inner.vo.system.dic.category.CreateSysDataDicCategoryVo;
import com.lframework.xingyun.template.inner.vo.system.dic.category.UpdateSysDataDicCategoryVo;
import com.lframework.xingyun.template.inner.bo.system.dic.category.GetSysDataDicCategoryBo;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import com.lframework.starter.web.annotations.security.HasPermission;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据字典分类
 *
 * @author zmj
 */
@Api(tags = "数据字典分类")
@Validated
@RestController
@RequestMapping("/system/dic/category")
public class SysDataDicCategoryController extends DefaultBaseController {

  @Autowired
  private SysDataDicCategoryService sysDataDicCategoryService;

  /**
   * 查询列表
   */
  @ApiOperation("查询列表")
  @HasPermission({"system:dic-category:*"})
  @GetMapping("/query")
  public InvokeResult<List<QuerySysDataDicCategoryBo>> query() {
    List<SysDataDicCategory> datas = sysDataDicCategoryService.queryList();
    List<QuerySysDataDicCategoryBo> results = CollectionUtil.emptyList();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySysDataDicCategoryBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  /**
   * 根据ID查询
   */
  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"system:dic-category:*"})
  @GetMapping
  public InvokeResult<GetSysDataDicCategoryBo> get(@NotBlank(message = "ID不能为空！") String id) {

    SysDataDicCategory data = sysDataDicCategoryService.findById(id);
    if (data == null) {
      throw new DefaultClientException("数据字典分类不存在！");
    }

    GetSysDataDicCategoryBo result = new GetSysDataDicCategoryBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增数据字典分类
   */
  @ApiOperation("新增数据字典分类")
  @HasPermission({"system:dic-category:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateSysDataDicCategoryVo vo) {

    sysDataDicCategoryService.create(vo);

    sysDataDicCategoryService.cleanCacheByKey("all");

    return InvokeResultBuilder.success();
  }

  /**
   * 修改数据字典分类
   */
  @ApiOperation("修改数据字典分类")
  @HasPermission({"system:dic-category:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateSysDataDicCategoryVo vo) {

    sysDataDicCategoryService.update(vo);

    sysDataDicCategoryService.cleanCacheByKeys(Arrays.asList("all", vo.getId()));

    return InvokeResultBuilder.success();
  }

  @ApiOperation("删除数据字典分类")
  @HasPermission({"system:dic-category:delete"})
  @DeleteMapping
  public InvokeResult<Void> delete(@NotBlank(message = "ID不能为空！") String id) {

    sysDataDicCategoryService.deleteById(id);

    sysDataDicCategoryService.cleanCacheByKeys(Arrays.asList("all", id));

    return InvokeResultBuilder.success();
  }
}
