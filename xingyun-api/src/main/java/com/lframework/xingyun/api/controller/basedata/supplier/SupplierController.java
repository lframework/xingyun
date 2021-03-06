package com.lframework.xingyun.api.controller.basedata.supplier;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.api.bo.basedata.supplier.GetSupplierBo;
import com.lframework.xingyun.api.bo.basedata.supplier.QuerySupplierBo;
import com.lframework.xingyun.api.excel.basedata.supplier.SupplierImportListener;
import com.lframework.xingyun.api.excel.basedata.supplier.SupplierImportModel;
import com.lframework.xingyun.basedata.entity.Supplier;
import com.lframework.xingyun.basedata.service.supplier.ISupplierService;
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
import org.springframework.security.access.prepost.PreAuthorize;
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
 * ???????????????
 *
 * @author zmj
 */
@Api(tags = "???????????????")
@Validated
@RestController
@RequestMapping("/basedata/supplier")
public class SupplierController extends DefaultBaseController {

  @Autowired
  private ISupplierService supplierService;

  /**
   * ???????????????
   */
  @ApiOperation("???????????????")
  @PreAuthorize("@permission.valid('base-data:supplier:query','base-data:supplier:add','base-data:supplier:modify')")
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
   * ???????????????
   */
  @ApiOperation("???????????????")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @PreAuthorize("@permission.valid('base-data:supplier:query','base-data:supplier:add','base-data:supplier:modify')")
  @GetMapping
  public InvokeResult<GetSupplierBo> get(@NotBlank(message = "ID???????????????") String id) {

    Supplier data = supplierService.findById(id);
    if (data == null) {
      throw new DefaultClientException("?????????????????????");
    }

    GetSupplierBo result = new GetSupplierBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * ?????????????????????
   */
  @ApiOperation("?????????????????????")
  @PreAuthorize("@permission.valid('base-data:supplier:modify')")
  @PatchMapping("/unable/batch")
  public InvokeResult<Void> batchUnable(
      @ApiParam(value = "ID", required = true) @NotEmpty(message = "????????????????????????????????????") @RequestBody List<String> ids) {

    supplierService.batchUnable(ids);

    for (String id : ids) {
      supplierService.cleanCacheByKey(id);
    }

    return InvokeResultBuilder.success();
  }

  /**
   * ?????????????????????
   */
  @ApiOperation("?????????????????????")
  @PreAuthorize("@permission.valid('base-data:supplier:modify')")
  @PatchMapping("/enable/batch")
  public InvokeResult<Void> batchEnable(
      @ApiParam(value = "ID", required = true) @NotEmpty(message = "????????????????????????????????????") @RequestBody List<String> ids) {

    supplierService.batchEnable(ids);

    for (String id : ids) {
      supplierService.cleanCacheByKey(id);
    }

    return InvokeResultBuilder.success();
  }

  /**
   * ???????????????
   */
  @ApiOperation("???????????????")
  @PreAuthorize("@permission.valid('base-data:supplier:add')")
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateSupplierVo vo) {

    supplierService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * ???????????????
   */
  @ApiOperation("???????????????")
  @PreAuthorize("@permission.valid('base-data:supplier:modify')")
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateSupplierVo vo) {

    supplierService.update(vo);

    supplierService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  @ApiOperation("??????????????????")
  @PreAuthorize("@permission.valid('base-data:supplier:import')")
  @GetMapping("/import/template")
  public void downloadImportTemplate() {
    ExcelUtil.exportXls("?????????????????????", SupplierImportModel.class);
  }

  @ApiOperation("??????")
  @PreAuthorize("@permission.valid('base-data:supplier:import')")
  @PostMapping("/import")
  public InvokeResult<Void> importExcel(@NotBlank(message = "ID????????????") String id,
      @NotNull(message = "???????????????") MultipartFile file) {

    SupplierImportListener listener = new SupplierImportListener();
    listener.setTaskId(id);
    ExcelUtil.read(file, SupplierImportModel.class, listener).sheet().doRead();

    return InvokeResultBuilder.success();
  }
}
