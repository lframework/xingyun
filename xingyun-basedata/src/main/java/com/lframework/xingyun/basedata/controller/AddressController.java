package com.lframework.xingyun.basedata.controller;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.annotations.security.HasPermission;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.basedata.bo.address.GetAddressBo;
import com.lframework.xingyun.basedata.bo.address.QueryAddressBo;
import com.lframework.xingyun.basedata.entity.Address;
import com.lframework.xingyun.basedata.excel.address.AddressExportTaskWorker;
import com.lframework.xingyun.basedata.excel.address.AddressImportListener;
import com.lframework.xingyun.basedata.excel.address.AddressImportModel;
import com.lframework.xingyun.basedata.service.address.AddressService;
import com.lframework.xingyun.basedata.vo.address.CreateAddressVo;
import com.lframework.xingyun.basedata.vo.address.QueryAddressVo;
import com.lframework.xingyun.basedata.vo.address.UpdateAddressVo;
import com.lframework.xingyun.core.utils.ExportTaskUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 地址库
 *
 * @author zmj
 */
@Api(tags = "地址库")
@Validated
@RestController
@RequestMapping("/basedata/address")
public class AddressController extends DefaultBaseController {

  @Autowired
  private AddressService addressService;

  /**
   * 地址列表
   */
  @ApiOperation("地址列表")
  @HasPermission({"base-data:address:query", "base-data:address:add", "base-data:address:modify"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryAddressBo>> query(@Valid QueryAddressVo vo) {

    PageResult<Address> pageResult = addressService.query(getPageIndex(vo), getPageSize(vo), vo);

    List<Address> datas = pageResult.getDatas();
    List<QueryAddressBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryAddressBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询地址
   */
  @ApiOperation("查询地址")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"base-data:address:query", "base-data:address:add", "base-data:address:modify"})
  @GetMapping
  public InvokeResult<GetAddressBo> get(@NotBlank(message = "ID不能为空！") String id) {

    Address data = addressService.findById(id);
    if (data == null) {
      throw new DefaultClientException("地址不存在！");
    }

    GetAddressBo result = new GetAddressBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增地址
   */
  @ApiOperation("新增地址")
  @HasPermission({"base-data:address:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateAddressVo vo) {

    addressService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改地址
   */
  @ApiOperation("修改地址")
  @HasPermission({"base-data:address:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateAddressVo vo) {

    addressService.update(vo);

    addressService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  /**
   * 导出
   */
  @ApiOperation("导出")
  @HasPermission({"base-data:address:export"})
  @PostMapping("/export")
  public InvokeResult<Void> export(@Valid QueryAddressVo vo) {

    ExportTaskUtil.exportTask("地址信息", AddressExportTaskWorker.class, vo);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("下载导入模板")
  @HasPermission({"base-data:address:import"})
  @GetMapping("/import/template")
  public void downloadImportTemplate() {
    ExcelUtil.exportXls("地址导入模板", AddressImportModel.class);
  }

  @ApiOperation("导入")
  @HasPermission({"base-data:address:import"})
  @PostMapping("/import")
  public InvokeResult<Void> importExcel(@NotBlank(message = "ID不能为空") String id,
      @NotNull(message = "请上传文件") MultipartFile file) {

    AddressImportListener listener = new AddressImportListener();
    listener.setTaskId(id);
    ExcelUtil.read(file, AddressImportModel.class, listener).sheet().doRead();

    return InvokeResultBuilder.success();
  }
}
