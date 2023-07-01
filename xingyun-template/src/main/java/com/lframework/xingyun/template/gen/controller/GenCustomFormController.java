package com.lframework.xingyun.template.gen.controller;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.xingyun.template.gen.entity.GenCustomForm;
import com.lframework.xingyun.template.gen.service.GenCustomFormService;
import com.lframework.xingyun.template.gen.vo.custom.form.CreateGenCustomFormVo;
import com.lframework.xingyun.template.gen.vo.custom.form.QueryGenCustomFormVo;
import com.lframework.xingyun.template.gen.vo.custom.form.UpdateGenCustomFormVo;
import com.lframework.xingyun.template.gen.bo.custom.form.GetGenCustomFormBo;
import com.lframework.xingyun.template.gen.bo.custom.form.QueryGenCustomFormBo;
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

@Api(tags = "自定义表单")
@Slf4j
@Validated
@RestController
@RequestMapping("/gen/custom/form")
public class GenCustomFormController extends DefaultBaseController {

  @Autowired
  private GenCustomFormService genCustomFormService;

  @ApiOperation("查询自定义表单")
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryGenCustomFormBo>> query(
      @Valid QueryGenCustomFormVo vo) {

    PageResult<GenCustomForm> pageResult = genCustomFormService.query(getPageIndex(vo),
        getPageSize(vo),
        vo);
    List<GenCustomForm> datas = pageResult.getDatas();
    List<QueryGenCustomFormBo> results = null;
    if (CollectionUtil.isNotEmpty(datas)) {
      results = datas.stream().map(QueryGenCustomFormBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @GetMapping
  public InvokeResult<GetGenCustomFormBo> get(@NotBlank(message = "ID不能为空！") String id) {

    GenCustomForm data = genCustomFormService.findById(id);

    return InvokeResultBuilder.success(new GetGenCustomFormBo(data));
  }

  @ApiOperation("新增")
  @PostMapping
  public InvokeResult<Void> create(@RequestBody @Valid CreateGenCustomFormVo vo) {

    genCustomFormService.create(vo);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("修改")
  @PutMapping
  public InvokeResult<Void> update(@RequestBody @Valid UpdateGenCustomFormVo vo) {

    genCustomFormService.update(vo);

    genCustomFormService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  @ApiOperation("根据ID删除")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @DeleteMapping
  public InvokeResult<Void> delete(@NotBlank(message = "ID不能为空！") String id) {

    genCustomFormService.delete(id);

    genCustomFormService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("批量删除")
  @DeleteMapping("/batch")
  public InvokeResult<Void> batchDelete(
      @ApiParam(value = "ID", required = true) @RequestBody @NotEmpty(message = "ID不能为空！") List<String> ids) {

    genCustomFormService.batchDelete(ids);

    genCustomFormService.cleanCacheByKeys(ids);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("批量启用")
  @PatchMapping("/enable/batch")
  public InvokeResult<Void> batchEnable(
      @ApiParam(value = "ID", required = true) @RequestBody @NotEmpty(message = "ID不能为空！") List<String> ids) {

    genCustomFormService.batchEnable(ids);

    genCustomFormService.cleanCacheByKeys(ids);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("批量停用")
  @PatchMapping("/unable/batch")
  public InvokeResult<Void> batchUnable(
      @ApiParam(value = "ID", required = true) @RequestBody @NotEmpty(message = "ID不能为空！") List<String> ids) {

    genCustomFormService.batchUnable(ids);

    genCustomFormService.cleanCacheByKeys(ids);

    return InvokeResultBuilder.success();
  }
}
