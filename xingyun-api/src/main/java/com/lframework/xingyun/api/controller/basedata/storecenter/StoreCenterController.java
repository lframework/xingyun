package com.lframework.xingyun.api.controller.basedata.storecenter;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.api.bo.basedata.storecenter.GetStoreCenterBo;
import com.lframework.xingyun.api.bo.basedata.storecenter.QueryStoreCenterBo;
import com.lframework.xingyun.api.excel.basedata.storecenter.StoreCenterImportListener;
import com.lframework.xingyun.api.excel.basedata.storecenter.StoreCenterImportModel;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
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
 * ????????????
 *
 * @author zmj
 */
@Api(tags = "????????????")
@Validated
@RestController
@RequestMapping("/basedata/storecenter")
public class StoreCenterController extends DefaultBaseController {

  @Autowired
  private IStoreCenterService storeCenterService;

  /**
   * ????????????
   */
  @ApiOperation("????????????")
  @PreAuthorize("@permission.valid('base-data:store-center:query','base-data:store-center:add','base-data:store-center:modify')")
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
   * ????????????
   */
  @ApiOperation("????????????")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @PreAuthorize("@permission.valid('base-data:store-center:query','base-data:store-center:add','base-data:store-center:modify')")
  @GetMapping
  public InvokeResult<GetStoreCenterBo> get(@NotBlank(message = "ID???????????????") String id) {

    StoreCenter data = storeCenterService.findById(id);
    if (data == null) {
      throw new DefaultClientException("??????????????????");
    }

    GetStoreCenterBo result = new GetStoreCenterBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * ??????????????????
   */
  @ApiOperation("??????????????????")
  @PreAuthorize("@permission.valid('base-data:store-center:modify')")
  @PatchMapping("/unable/batch")
  public InvokeResult<Void> batchUnable(
      @ApiParam(value = "ID", required = true) @NotEmpty(message = "?????????????????????????????????") @RequestBody List<String> ids) {

    storeCenterService.batchUnable(ids);

    for (String id : ids) {
      storeCenterService.cleanCacheByKey(id);
    }

    return InvokeResultBuilder.success();
  }

  /**
   * ??????????????????
   */
  @ApiOperation("??????????????????")
  @PreAuthorize("@permission.valid('base-data:store-center:modify')")
  @PatchMapping("/enable/batch")
  public InvokeResult<Void> batchEnable(
      @ApiParam(value = "ID", required = true) @NotEmpty(message = "?????????????????????????????????") @RequestBody List<String> ids) {

    storeCenterService.batchEnable(ids);

    for (String id : ids) {
      storeCenterService.cleanCacheByKey(id);
    }

    return InvokeResultBuilder.success();
  }

  /**
   * ????????????
   */
  @ApiOperation("????????????")
  @PreAuthorize("@permission.valid('base-data:store-center:add')")
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateStoreCenterVo vo) {

    storeCenterService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * ????????????
   */
  @ApiOperation("????????????")
  @PreAuthorize("@permission.valid('base-data:store-center:modify')")
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateStoreCenterVo vo) {

    storeCenterService.update(vo);

    storeCenterService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  @ApiOperation("??????????????????")
  @PreAuthorize("@permission.valid('base-data:store-center:import')")
  @GetMapping("/import/template")
  public void downloadImportTemplate() {
    ExcelUtil.exportXls("??????????????????", StoreCenterImportModel.class);
  }

  @ApiOperation("??????")
  @PreAuthorize("@permission.valid('base-data:store-center:import')")
  @PostMapping("/import")
  public InvokeResult<Void> importExcel(@NotBlank(message = "ID????????????") String id,
      @NotNull(message = "???????????????") MultipartFile file) {

    StoreCenterImportListener listener = new StoreCenterImportListener();
    listener.setTaskId(id);
    ExcelUtil.read(file, StoreCenterImportModel.class, listener).sheet().doRead();

    return InvokeResultBuilder.success();
  }
}
