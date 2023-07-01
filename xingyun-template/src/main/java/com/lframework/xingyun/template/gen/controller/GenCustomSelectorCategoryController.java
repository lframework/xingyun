package com.lframework.xingyun.template.gen.controller;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.xingyun.template.gen.service.GenCustomSelectorCategoryService;
import com.lframework.xingyun.template.gen.vo.custom.selector.category.CreateGenCustomSelectorCategoryVo;
import com.lframework.xingyun.template.gen.vo.custom.selector.category.UpdateGenCustomSelectorCategoryVo;
import com.lframework.xingyun.template.gen.bo.custom.selector.category.GetGenCustomSelectorCategoryBo;
import com.lframework.xingyun.template.gen.bo.custom.selector.category.QueryGenCustomSelectorCategoryBo;
import com.lframework.xingyun.template.gen.entity.GenCustomSelectorCategory;
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
 * 自定义选择器分类
 *
 * @author zmj
 */
@Api(tags = "自定义选择器分类")
@Validated
@RestController
@RequestMapping("/gen/custom/selector/category")
public class GenCustomSelectorCategoryController extends DefaultBaseController {

  @Autowired
  private GenCustomSelectorCategoryService genCustomSelectorCategoryService;

  /**
   * 查询列表
   */
  @ApiOperation("查询列表")
  @GetMapping("/query")
  public InvokeResult<List<QueryGenCustomSelectorCategoryBo>> query() {
    List<GenCustomSelectorCategory> datas = genCustomSelectorCategoryService.queryList();
    List<QueryGenCustomSelectorCategoryBo> results = CollectionUtil.emptyList();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryGenCustomSelectorCategoryBo::new)
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
  public InvokeResult<GetGenCustomSelectorCategoryBo> get(
      @NotBlank(message = "ID不能为空！") String id) {

    GenCustomSelectorCategory data = genCustomSelectorCategoryService.findById(id);
    if (data == null) {
      throw new DefaultClientException("自定义选择器分类不存在！");
    }

    GetGenCustomSelectorCategoryBo result = new GetGenCustomSelectorCategoryBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增自定义选择器分类
   */
  @ApiOperation("新增自定义选择器分类")
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateGenCustomSelectorCategoryVo vo) {

    genCustomSelectorCategoryService.create(vo);

    genCustomSelectorCategoryService.cleanCacheByKey("all");

    return InvokeResultBuilder.success();
  }

  /**
   * 修改自定义选择器分类
   */
  @ApiOperation("修改自定义选择器分类")
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateGenCustomSelectorCategoryVo vo) {

    genCustomSelectorCategoryService.update(vo);

    genCustomSelectorCategoryService.cleanCacheByKeys(Arrays.asList("all", vo.getId()));

    return InvokeResultBuilder.success();
  }

  @ApiOperation("删除自定义选择器分类")
  @DeleteMapping
  public InvokeResult<Void> delete(@NotBlank(message = "ID不能为空！") String id) {

    genCustomSelectorCategoryService.deleteById(id);

    genCustomSelectorCategoryService.cleanCacheByKeys(Arrays.asList("all", id));

    return InvokeResultBuilder.success();
  }
}
