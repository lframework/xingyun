package com.lframework.xingyun.template.gen.controller;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.xingyun.template.gen.entity.GenDataEntityCategory;
import com.lframework.xingyun.template.gen.service.GenDataEntityCategoryService;
import com.lframework.xingyun.template.gen.vo.data.entity.category.CreateGenDataEntityCategoryVo;
import com.lframework.xingyun.template.gen.vo.data.entity.category.UpdateGenDataEntityCategoryVo;
import com.lframework.xingyun.template.gen.bo.data.entity.category.GetGenDataEntityCategoryBo;
import com.lframework.xingyun.template.gen.bo.data.entity.category.QueryGenDataEntityCategoryBo;
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
 * 数据实体分类
 *
 * @author zmj
 */
@Api(tags = "数据实体分类")
@Validated
@RestController
@RequestMapping("/gen/data/entity/category")
public class GenDataEntityCategoryController extends DefaultBaseController {

  @Autowired
  private GenDataEntityCategoryService genDataEntityCategoryService;

  /**
   * 查询列表
   */
  @ApiOperation("查询列表")
  @GetMapping("/query")
  public InvokeResult<List<QueryGenDataEntityCategoryBo>> query() {
    List<GenDataEntityCategory> datas = genDataEntityCategoryService.queryList();
    List<QueryGenDataEntityCategoryBo> results = CollectionUtil.emptyList();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryGenDataEntityCategoryBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  /**
   * 根据ID查询
   */
  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @GetMapping
  public InvokeResult<GetGenDataEntityCategoryBo> get(@NotBlank(message = "ID不能为空！") String id) {

    GenDataEntityCategory data = genDataEntityCategoryService.findById(id);
    if (data == null) {
      throw new DefaultClientException("数据实体分类不存在！");
    }

    GetGenDataEntityCategoryBo result = new GetGenDataEntityCategoryBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增数据实体分类
   */
  @ApiOperation("新增数据实体分类")
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateGenDataEntityCategoryVo vo) {

    genDataEntityCategoryService.create(vo);

    genDataEntityCategoryService.cleanCacheByKey("all");

    return InvokeResultBuilder.success();
  }

  /**
   * 修改数据实体分类
   */
  @ApiOperation("修改数据实体分类")
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateGenDataEntityCategoryVo vo) {

    genDataEntityCategoryService.update(vo);

    genDataEntityCategoryService.cleanCacheByKeys(Arrays.asList("all", vo.getId()));

    return InvokeResultBuilder.success();
  }

  @ApiOperation("删除数据实体分类")
  @DeleteMapping
  public InvokeResult<Void> delete(@NotBlank(message = "ID不能为空！") String id) {

    genDataEntityCategoryService.deleteById(id);

    genDataEntityCategoryService.cleanCacheByKeys(Arrays.asList("all", id));

    return InvokeResultBuilder.success();
  }
}
