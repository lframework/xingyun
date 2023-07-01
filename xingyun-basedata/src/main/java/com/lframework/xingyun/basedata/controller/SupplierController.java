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
import com.lframework.xingyun.basedata.bo.supplier.GetSupplierBo;
import com.lframework.xingyun.basedata.bo.supplier.QuerySupplierBo;
import com.lframework.xingyun.basedata.entity.Supplier;
import com.lframework.xingyun.basedata.excel.supplier.SupplierImportListener;
import com.lframework.xingyun.basedata.excel.supplier.SupplierImportModel;
import com.lframework.xingyun.basedata.service.supplier.SupplierService;
import com.lframework.xingyun.basedata.vo.supplier.CreateSupplierVo;
import com.lframework.xingyun.basedata.vo.supplier.QuerySupplierVo;
import com.lframework.xingyun.basedata.vo.supplier.UpdateSupplierVo;
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
 * 供应商管理
 *
 * @author zmj
 */
@Api(tags = "供应商管理")
@Validated
@RestController
@RequestMapping("/basedata/supplier")
public class SupplierController extends DefaultBaseController {

  @Autowired
  private SupplierService supplierService;

  /**
   * 供应商列表
   */
  @ApiOperation("供应商列表")
  @HasPermission({"base-data:supplier:query", "base-data:supplier:add",
      "base-data:supplier:modify"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QuerySupplierBo>> query(@Valid QuerySupplierVo vo) {

    PageResult<Supplier> pageResult = supplierService.query(getPageIndex(vo), getPageSize(vo), vo);

    List<Supplier> datas = pageResult.getDatas();
    List<QuerySupplierBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {

      results = datas.stream().map(QuerySupplierBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询供应商
   */
  @ApiOperation("查询供应商")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"base-data:supplier:query", "base-data:supplier:add",
      "base-data:supplier:modify"})
  @GetMapping
  public InvokeResult<GetSupplierBo> get(@NotBlank(message = "ID不能为空！") String id) {

    Supplier data = supplierService.findById(id);
    if (data == null) {
      throw new DefaultClientException("供应商不存在！");
    }

    GetSupplierBo result = new GetSupplierBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 批量停用供应商
   */
  @ApiOperation("批量停用供应商")
  @HasPermission({"base-data:supplier:modify"})
  @PatchMapping("/unable/batch")
  public InvokeResult<Void> batchUnable(
      @ApiParam(value = "ID", required = true) @NotEmpty(message = "请选择需要停用的供应商！") @RequestBody List<String> ids) {

    supplierService.batchUnable(ids);

    for (String id : ids) {
      supplierService.cleanCacheByKey(id);
    }

    return InvokeResultBuilder.success();
  }

  /**
   * 批量启用供应商
   */
  @ApiOperation("批量启用供应商")
  @HasPermission({"base-data:supplier:modify"})
  @PatchMapping("/enable/batch")
  public InvokeResult<Void> batchEnable(
      @ApiParam(value = "ID", required = true) @NotEmpty(message = "请选择需要启用的供应商！") @RequestBody List<String> ids) {

    supplierService.batchEnable(ids);

    for (String id : ids) {
      supplierService.cleanCacheByKey(id);
    }

    return InvokeResultBuilder.success();
  }

  /**
   * 新增供应商
   */
  @ApiOperation("新增供应商")
  @HasPermission({"base-data:supplier:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateSupplierVo vo) {

    supplierService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改供应商
   */
  @ApiOperation("修改供应商")
  @HasPermission({"base-data:supplier:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateSupplierVo vo) {

    supplierService.update(vo);

    supplierService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  @ApiOperation("下载导入模板")
  @HasPermission({"base-data:supplier:import"})
  @GetMapping("/import/template")
  public void downloadImportTemplate() {
    ExcelUtil.exportXls("供应商导入模板", SupplierImportModel.class);
  }

  @ApiOperation("导入")
  @HasPermission({"base-data:supplier:import"})
  @PostMapping("/import")
  public InvokeResult<Void> importExcel(@NotBlank(message = "ID不能为空") String id,
      @NotNull(message = "请上传文件") MultipartFile file) {

    SupplierImportListener listener = new SupplierImportListener();
    listener.setTaskId(id);
    ExcelUtil.read(file, SupplierImportModel.class, listener).sheet().doRead();

    return InvokeResultBuilder.success();
  }
}
