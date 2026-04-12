package com.lframework.xingyun.basedata.controller;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.ExcelUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.xingyun.basedata.bo.storecenter.GetStoreCenterBo;
import com.lframework.xingyun.basedata.bo.storecenter.QueryStoreCenterBo;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.excel.storecenter.StoreCenterImportListener;
import com.lframework.xingyun.basedata.excel.storecenter.StoreCenterImportModel;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.basedata.vo.storecenter.CreateStoreCenterVo;
import com.lframework.xingyun.basedata.vo.storecenter.QueryStoreCenterVo;
import com.lframework.xingyun.basedata.vo.storecenter.UpdateStoreCenterVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 仓库管理
 *
 * @author zmj
 */
@Tag(name = "仓库管理")
@Validated
@RestController
@RequestMapping("/basedata/storecenter")
public class StoreCenterController extends DefaultBaseController {

  @Autowired
  private StoreCenterService storeCenterService;

  /**
   * 仓库列表
   */
  @Operation(summary = "仓库列表")
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
  @Operation(summary = "查询仓库")
  @Parameter(name = "id", description = "ID", in = ParameterIn.QUERY, required = true)
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
   * 删除仓库
   */
  @Operation(summary = "删除仓库")
  @HasPermission({"base-data:store-center:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(
      @Parameter(description = "ID", required = true) @NotEmpty(message = "仓库ID不能为空！") String id) {

    storeCenterService.deleteById(id);

    storeCenterService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 新增仓库
   */
  @Operation(summary = "新增仓库")
  @HasPermission({"base-data:store-center:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateStoreCenterVo vo) {

    storeCenterService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改仓库
   */
  @Operation(summary = "修改仓库")
  @HasPermission({"base-data:store-center:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateStoreCenterVo vo) {

    storeCenterService.update(vo);

    storeCenterService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  @Operation(summary = "下载导入模板")
  @HasPermission({"base-data:store-center:import"})
  @GetMapping("/import/template")
  public void downloadImportTemplate() {
    ExcelUtil.exportXls("仓库导入模板", StoreCenterImportModel.class);
  }

  @Operation(summary = "导入")
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
