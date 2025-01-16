package com.lframework.xingyun.template.gen.controller;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.xingyun.template.gen.bo.custom.page.category.GetGenCustomPageCategoryBo;
import com.lframework.xingyun.template.gen.bo.custom.page.category.QueryGenCustomPageCategoryBo;
import com.lframework.xingyun.template.gen.service.GenCustomPageCategoryService;
import com.lframework.xingyun.template.gen.vo.custom.page.category.CreateGenCustomPageCategoryVo;
import com.lframework.xingyun.template.gen.vo.custom.page.category.UpdateGenCustomPageCategoryVo;
import com.lframework.xingyun.template.gen.entity.GenCustomPageCategory;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自定义页面分类
 *
 * @author zmj
 */
@Api(tags = "自定义页面分类")
@Validated
@RestController
@RequestMapping("/gen/custom/page/category")
public class GenCustomPageCategoryController extends DefaultBaseController {

  @Autowired
  private GenCustomPageCategoryService genCustomPageCategoryService;

  /**
   * 查询列表
   */
  @ApiOperation("查询列表")
  @GetMapping("/query")
  public InvokeResult<List<QueryGenCustomPageCategoryBo>> query() {
    List<GenCustomPageCategory> datas = genCustomPageCategoryService.queryList();
    List<QueryGenCustomPageCategoryBo> results = CollectionUtil.emptyList();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryGenCustomPageCategoryBo::new)
          .collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  /**
   * 根据ID查询
   */
  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @GetMapping
  public InvokeResult<GetGenCustomPageCategoryBo> get(
      @NotBlank(message = "ID不能为空！") String id) {

    GenCustomPageCategory data = genCustomPageCategoryService.findById(id);
    if (data == null) {
      throw new DefaultClientException("自定义页面分类不存在！");
    }

    GetGenCustomPageCategoryBo result = new GetGenCustomPageCategoryBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增自定义页面分类
   */
  @ApiOperation("新增自定义页面分类")
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateGenCustomPageCategoryVo vo) {

    genCustomPageCategoryService.create(vo);

    genCustomPageCategoryService.cleanCacheByKey("all");

    return InvokeResultBuilder.success();
  }

  /**
   * 修改自定义页面分类
   */
  @ApiOperation("修改自定义页面分类")
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateGenCustomPageCategoryVo vo) {

    genCustomPageCategoryService.update(vo);

    genCustomPageCategoryService.cleanCacheByKeys(Arrays.asList("all", vo.getId()));

    return InvokeResultBuilder.success();
  }

  @ApiOperation("删除自定义页面分类")
  @DeleteMapping
  public InvokeResult<Void> delete(@NotBlank(message = "ID不能为空！") String id) {

    genCustomPageCategoryService.deleteById(id);

    genCustomPageCategoryService.cleanCacheByKeys(Arrays.asList("all", id));

    return InvokeResultBuilder.success();
  }
}
