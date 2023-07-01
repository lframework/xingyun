package com.lframework.xingyun.template.gen.controller;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.xingyun.template.gen.entity.GenCustomFormCategory;
import com.lframework.xingyun.template.gen.service.GenCustomFormCategoryService;
import com.lframework.xingyun.template.gen.vo.custom.form.category.CreateGenCustomFormCategoryVo;
import com.lframework.xingyun.template.gen.vo.custom.form.category.UpdateGenCustomFormCategoryVo;
import com.lframework.xingyun.template.gen.bo.custom.form.category.GetGenCustomFormCategoryBo;
import com.lframework.xingyun.template.gen.bo.custom.form.category.QueryGenCustomFormCategoryBo;
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
 * 自定义表单分类
 *
 * @author zmj
 */
@Api(tags = "自定义表单分类")
@Validated
@RestController
@RequestMapping("/gen/custom/form/category")
public class GenCustomFormCategoryController extends DefaultBaseController {

  @Autowired
  private GenCustomFormCategoryService genCustomFormCategoryService;

  /**
   * 查询列表
   */
  @ApiOperation("查询列表")
  @GetMapping("/query")
  public InvokeResult<List<QueryGenCustomFormCategoryBo>> query() {
    List<GenCustomFormCategory> datas = genCustomFormCategoryService.queryList();
    List<QueryGenCustomFormCategoryBo> results = CollectionUtil.emptyList();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryGenCustomFormCategoryBo::new)
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
  public InvokeResult<GetGenCustomFormCategoryBo> get(
      @NotBlank(message = "ID不能为空！") String id) {

    GenCustomFormCategory data = genCustomFormCategoryService.findById(id);
    if (data == null) {
      throw new DefaultClientException("自定义表单分类不存在！");
    }

    GetGenCustomFormCategoryBo result = new GetGenCustomFormCategoryBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增自定义表单分类
   */
  @ApiOperation("新增自定义表单分类")
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateGenCustomFormCategoryVo vo) {

    genCustomFormCategoryService.create(vo);

    genCustomFormCategoryService.cleanCacheByKey("all");

    return InvokeResultBuilder.success();
  }

  /**
   * 修改自定义表单分类
   */
  @ApiOperation("修改自定义表单分类")
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateGenCustomFormCategoryVo vo) {

    genCustomFormCategoryService.update(vo);

    genCustomFormCategoryService.cleanCacheByKeys(Arrays.asList("all", vo.getId()));

    return InvokeResultBuilder.success();
  }

  @ApiOperation("删除自定义表单分类")
  @DeleteMapping
  public InvokeResult<Void> delete(@NotBlank(message = "ID不能为空！") String id) {

    genCustomFormCategoryService.deleteById(id);

    genCustomFormCategoryService.cleanCacheByKeys(Arrays.asList("all", id));

    return InvokeResultBuilder.success();
  }
}
