package com.lframework.xingyun.template.gen.controller;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.xingyun.template.gen.service.GenCustomSelectorService;
import com.lframework.xingyun.template.gen.vo.custom.selector.CreateGenCustomSelectorVo;
import com.lframework.xingyun.template.gen.vo.custom.selector.QueryGenCustomSelectorVo;
import com.lframework.xingyun.template.gen.vo.custom.selector.UpdateGenCustomSelectorVo;
import com.lframework.xingyun.template.gen.bo.custom.selector.GetGenCustomSelectorBo;
import com.lframework.xingyun.template.gen.bo.custom.selector.QueryGenCustomSelectorBo;
import com.lframework.xingyun.template.gen.entity.GenCustomSelector;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageResultUtil;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "自定义选择器")
@Slf4j
@Validated
@RestController
@RequestMapping("/gen/custom/selector")
public class GenCustomSelectorController extends DefaultBaseController {

  @Autowired
  private GenCustomSelectorService genCustomSelectorService;

  @ApiOperation("查询自定义选择器")
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryGenCustomSelectorBo>> query(
      @Valid QueryGenCustomSelectorVo vo) {

    PageResult<GenCustomSelector> pageResult = genCustomSelectorService.query(getPageIndex(vo),
        getPageSize(vo),
        vo);
    List<GenCustomSelector> datas = pageResult.getDatas();
    List<QueryGenCustomSelectorBo> results = null;
    if (CollectionUtil.isNotEmpty(datas)) {
      results = datas.stream().map(QueryGenCustomSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @GetMapping
  public InvokeResult<GetGenCustomSelectorBo> get(@NotBlank(message = "ID不能为空！") String id) {

    GenCustomSelector data = genCustomSelectorService.findById(id);

    return InvokeResultBuilder.success(new GetGenCustomSelectorBo(data));
  }

  @ApiOperation("新增")
  @PostMapping
  public InvokeResult<Void> create(@RequestBody @Valid CreateGenCustomSelectorVo vo) {

    genCustomSelectorService.create(vo);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("修改")
  @PutMapping
  public InvokeResult<Void> update(@RequestBody @Valid UpdateGenCustomSelectorVo vo) {

    genCustomSelectorService.update(vo);

    genCustomSelectorService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  @ApiOperation("根据ID删除")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @DeleteMapping
  public InvokeResult<Void> delete(@NotBlank(message = "ID不能为空！") String id) {

    genCustomSelectorService.delete(id);

    genCustomSelectorService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("批量删除")
  @DeleteMapping("/batch")
  public InvokeResult<Void> batchDelete(
      @ApiParam(value = "ID", required = true) @RequestBody @NotEmpty(message = "ID不能为空！") List<String> ids) {

    genCustomSelectorService.batchDelete(ids);

    genCustomSelectorService.cleanCacheByKeys(ids);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("批量启用")
  @PatchMapping("/enable/batch")
  public InvokeResult<Void> batchEnable(
      @ApiParam(value = "ID", required = true) @RequestBody @NotEmpty(message = "ID不能为空！") List<String> ids) {

    genCustomSelectorService.batchEnable(ids);

    genCustomSelectorService.cleanCacheByKeys(ids);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("批量停用")
  @PatchMapping("/unable/batch")
  public InvokeResult<Void> batchUnable(
      @ApiParam(value = "ID", required = true) @RequestBody @NotEmpty(message = "ID不能为空！") List<String> ids) {

    genCustomSelectorService.batchUnable(ids);

    genCustomSelectorService.cleanCacheByKeys(ids);

    return InvokeResultBuilder.success();
  }
}
