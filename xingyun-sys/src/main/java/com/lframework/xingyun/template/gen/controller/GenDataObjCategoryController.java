package com.lframework.xingyun.template.gen.controller;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.xingyun.template.gen.bo.data.obj.category.GetGenDataObjCategoryBo;
import com.lframework.xingyun.template.gen.bo.data.obj.category.QueryGenDataObjCategoryBo;
import com.lframework.xingyun.template.gen.entity.GenDataObjCategory;
import com.lframework.xingyun.template.gen.service.GenDataObjCategoryService;
import com.lframework.xingyun.template.gen.vo.data.obj.category.CreateGenDataObjCategoryVo;
import com.lframework.xingyun.template.gen.vo.data.obj.category.UpdateGenDataObjCategoryVo;
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
 * 数据对象分类
 *
 * @author zmj
 */
@Api(tags = "数据对象分类")
@Validated
@RestController
@RequestMapping("/gen/data/obj/category")
public class GenDataObjCategoryController extends DefaultBaseController {

  @Autowired
  private GenDataObjCategoryService genDataObjCategoryService;

  /**
   * 查询列表
   */
  @ApiOperation("查询列表")
  @GetMapping("/query")
  public InvokeResult<List<QueryGenDataObjCategoryBo>> query() {
    List<GenDataObjCategory> datas = genDataObjCategoryService.queryList();
    List<QueryGenDataObjCategoryBo> results = CollectionUtil.emptyList();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryGenDataObjCategoryBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  /**
   * 根据ID查询
   */
  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @GetMapping
  public InvokeResult<GetGenDataObjCategoryBo> get(@NotBlank(message = "ID不能为空！") String id) {

    GenDataObjCategory data = genDataObjCategoryService.findById(id);
    if (data == null) {
      throw new DefaultClientException("数据对象分类不存在！");
    }

    GetGenDataObjCategoryBo result = new GetGenDataObjCategoryBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增数据对象分类
   */
  @ApiOperation("新增数据对象分类")
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateGenDataObjCategoryVo vo) {

    genDataObjCategoryService.create(vo);

    genDataObjCategoryService.cleanCacheByKey("all");

    return InvokeResultBuilder.success();
  }

  /**
   * 修改数据对象分类
   */
  @ApiOperation("修改数据对象分类")
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateGenDataObjCategoryVo vo) {

    genDataObjCategoryService.update(vo);

    genDataObjCategoryService.cleanCacheByKeys(Arrays.asList("all", vo.getId()));

    return InvokeResultBuilder.success();
  }

  @ApiOperation("删除数据对象分类")
  @DeleteMapping
  public InvokeResult<Void> delete(@NotBlank(message = "ID不能为空！") String id) {

    genDataObjCategoryService.deleteById(id);

    genDataObjCategoryService.cleanCacheByKeys(Arrays.asList("all", id));

    return InvokeResultBuilder.success();
  }
}
