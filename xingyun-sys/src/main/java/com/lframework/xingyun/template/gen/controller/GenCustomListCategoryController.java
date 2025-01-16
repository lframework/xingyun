package com.lframework.xingyun.template.gen.controller;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.xingyun.template.gen.entity.GenCustomListCategory;
import com.lframework.xingyun.template.gen.service.GenCustomListCategoryService;
import com.lframework.xingyun.template.gen.vo.custom.list.category.CreateGenCustomListCategoryVo;
import com.lframework.xingyun.template.gen.vo.custom.list.category.UpdateGenCustomListCategoryVo;
import com.lframework.xingyun.template.gen.bo.custom.list.category.GetGenCustomListCategoryBo;
import com.lframework.xingyun.template.gen.bo.custom.list.category.QueryGenCustomListCategoryBo;
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
 * 自定义列表分类
 *
 * @author zmj
 */
@Api(tags = "自定义列表分类")
@Validated
@RestController
@RequestMapping("/gen/custom/list/category")
public class GenCustomListCategoryController extends DefaultBaseController {

  @Autowired
  private GenCustomListCategoryService genCustomListCategoryService;

  /**
   * 查询列表
   */
  @ApiOperation("查询列表")
  @GetMapping("/query")
  public InvokeResult<List<QueryGenCustomListCategoryBo>> query() {
    List<GenCustomListCategory> datas = genCustomListCategoryService.queryList();
    List<QueryGenCustomListCategoryBo> results = CollectionUtil.emptyList();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryGenCustomListCategoryBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  /**
   * 根据ID查询
   */
  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @GetMapping
  public InvokeResult<GetGenCustomListCategoryBo> get(@NotBlank(message = "ID不能为空！") String id) {

    GenCustomListCategory data = genCustomListCategoryService.findById(id);
    if (data == null) {
      throw new DefaultClientException("自定义列表分类不存在！");
    }

    GetGenCustomListCategoryBo result = new GetGenCustomListCategoryBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增自定义列表分类
   */
  @ApiOperation("新增自定义列表分类")
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateGenCustomListCategoryVo vo) {

    genCustomListCategoryService.create(vo);

    genCustomListCategoryService.cleanCacheByKey("all");

    return InvokeResultBuilder.success();
  }

  /**
   * 修改自定义列表分类
   */
  @ApiOperation("修改自定义列表分类")
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateGenCustomListCategoryVo vo) {

    genCustomListCategoryService.update(vo);

    genCustomListCategoryService.cleanCacheByKeys(Arrays.asList("all", vo.getId()));

    return InvokeResultBuilder.success();
  }

  @ApiOperation("删除自定义列表分类")
  @DeleteMapping
  public InvokeResult<Void> delete(@NotBlank(message = "ID不能为空！") String id) {

    genCustomListCategoryService.deleteById(id);

    genCustomListCategoryService.cleanCacheByKeys(Arrays.asList("all", id));

    return InvokeResultBuilder.success();
  }
}
