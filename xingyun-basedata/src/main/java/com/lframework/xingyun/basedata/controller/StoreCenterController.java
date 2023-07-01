package com.lframework.xingyun.basedata.controller;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.starter.web.annotations.security.HasPermission;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.basedata.bo.storecenter.GetStoreCenterBo;
import com.lframework.xingyun.basedata.bo.storecenter.QueryStoreCenterBo;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.excel.storecenter.StoreCenterImportListener;
import com.lframework.xingyun.basedata.excel.storecenter.StoreCenterImportModel;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.basedata.vo.storecenter.CreateStoreCenterVo;
import com.lframework.xingyun.basedata.vo.storecenter.QueryStoreCenterVo;
import com.lframework.xingyun.basedata.vo.storecenter.UpdateStoreCenterVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 仓库管理
 *
 * @author zmj
 */
@Api(tags = "仓库管理")
@Validated
@RestController
@RequestMapping("/basedata/storecenter")
public class StoreCenterController extends DefaultBaseController {

  @Autowired
  private StoreCenterService storeCenterService;

  /**
   * 仓库列表
   */
  @ApiOperation("仓库列表")
  @HasPermission({"base-data:store-center:query", "base-data:store-center:add",
      "base-data:store-center:modify"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryStoreCenterBo>> query(@Valid QueryStoreCenterVo vo) {

    PageResult<StoreCenter> pageResult = storeCenterService.query(getPageIndex(vo), getPageSize(vo),
        vo);

    List<StoreCenter> datas = pageResult.getDatas();
    List<QueryStoreCenterBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryStoreCenterBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询仓库
   */
  @ApiOperation("查询仓库")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"base-data:store-center:query", "base-data:store-center:add",
      "base-data:store-center:modify"})
  @GetMapping
  public InvokeResult<GetStoreCenterBo> get(@NotBlank(message = "ID不能为空！") String id) {

    StoreCenter data = storeCenterService.findById(id);
    if (data == null) {
      throw new DefaultClientException("仓库不存在！");
    }

    GetStoreCenterBo result = new GetStoreCenterBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 批量停用仓库
   */
  @ApiOperation("批量停用仓库")
  @HasPermission({"base-data:store-center:modify"})
  @PatchMapping("/unable/batch")
  public InvokeResult<Void> batchUnable(
      @ApiParam(value = "ID", required = true) @NotEmpty(message = "请选择需要停用的仓库！") @RequestBody List<String> ids) {

    storeCenterService.batchUnable(ids);

    for (String id : ids) {
      storeCenterService.cleanCacheByKey(id);
    }

    return InvokeResultBuilder.success();
  }

  /**
   * 批量启用仓库
   */
  @ApiOperation("批量启用仓库")
  @HasPermission({"base-data:store-center:modify"})
  @PatchMapping("/enable/batch")
  public InvokeResult<Void> batchEnable(
      @ApiParam(value = "ID", required = true) @NotEmpty(message = "请选择需要启用的仓库！") @RequestBody List<String> ids) {

    storeCenterService.batchEnable(ids);

    for (String id : ids) {
      storeCenterService.cleanCacheByKey(id);
    }

    return InvokeResultBuilder.success();
  }

  /**
   * 新增仓库
   */
  @ApiOperation("新增仓库")
  @HasPermission({"base-data:store-center:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateStoreCenterVo vo) {

    storeCenterService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改仓库
   */
  @ApiOperation("修改仓库")
  @HasPermission({"base-data:store-center:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateStoreCenterVo vo) {

    storeCenterService.update(vo);

    storeCenterService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  @ApiOperation("下载导入模板")
  @HasPermission({"base-data:store-center:import"})
  @GetMapping("/import/template")
  public void downloadImportTemplate() {
    ExcelUtil.exportXls("仓库导入模板", StoreCenterImportModel.class);
  }

  @ApiOperation("导入")
  @HasPermission({"base-data:store-center:import"})
  @PostMapping("/import")
  public InvokeResult<Void> importExcel(@NotBlank(message = "ID不能为空") String id,
      @NotNull(message = "请上传文件") MultipartFile file) {

    StoreCenterImportListener listener = new StoreCenterImportListener();
    listener.setTaskId(id);
    ExcelUtil.read(file, StoreCenterImportModel.class, listener).sheet().doRead();

    return InvokeResultBuilder.success();
  }
}
