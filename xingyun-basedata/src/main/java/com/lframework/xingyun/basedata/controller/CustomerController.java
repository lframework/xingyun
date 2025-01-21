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
import com.lframework.xingyun.basedata.bo.customer.GetCustomerBo;
import com.lframework.xingyun.basedata.bo.customer.QueryCustomerBo;
import com.lframework.xingyun.basedata.entity.Customer;
import com.lframework.xingyun.basedata.excel.customer.CustomerImportListener;
import com.lframework.xingyun.basedata.excel.customer.CustomerImportModel;
import com.lframework.xingyun.basedata.service.customer.CustomerService;
import com.lframework.xingyun.basedata.vo.customer.CreateCustomerVo;
import com.lframework.xingyun.basedata.vo.customer.QueryCustomerVo;
import com.lframework.xingyun.basedata.vo.customer.UpdateCustomerVo;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 客户管理
 *
 * @author zmj
 */
@Api(tags = "客户管理")
@Validated
@RestController
@RequestMapping("/basedata/customer")
public class CustomerController extends DefaultBaseController {

  @Autowired
  private CustomerService customerService;

  /**
   * 客户列表
   */
  @ApiOperation("客户列表")
  @HasPermission({"base-data:customer:query", "base-data:customer:add",
      "base-data:customer:modify"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryCustomerBo>> query(@Valid QueryCustomerVo vo) {

    PageResult<Customer> pageResult = customerService.query(getPageIndex(vo), getPageSize(vo), vo);

    List<Customer> datas = pageResult.getDatas();
    List<QueryCustomerBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryCustomerBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询客户
   */
  @ApiOperation("查询客户")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"base-data:customer:query", "base-data:customer:add",
      "base-data:customer:modify"})
  @GetMapping
  public InvokeResult<GetCustomerBo> get(@NotBlank(message = "ID不能为空！") String id) {

    Customer data = customerService.findById(id);
    if (data == null) {
      throw new DefaultClientException("客户不存在！");
    }

    GetCustomerBo result = new GetCustomerBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 停用客户
   */
  @ApiOperation("停用客户")
  @HasPermission({"base-data:customer:modify"})
  @PatchMapping("/unable")
  public InvokeResult<Void> unable(
      @ApiParam(value = "ID", required = true) @NotEmpty(message = "客户ID不能为空！") String id) {

    customerService.unable(id);

    customerService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 启用客户
   */
  @ApiOperation("启用客户")
  @HasPermission({"base-data:customer:modify"})
  @PatchMapping("/enable")
  public InvokeResult<Void> enable(
      @ApiParam(value = "ID", required = true) @NotEmpty(message = "客户ID不能为空！") String id) {

    customerService.enable(id);

    customerService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 新增客户
   */
  @ApiOperation("新增客户")
  @HasPermission({"base-data:customer:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateCustomerVo vo) {

    customerService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改客户
   */
  @ApiOperation("修改客户")
  @HasPermission({"base-data:customer:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateCustomerVo vo) {

    customerService.update(vo);

    customerService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  @ApiOperation("下载导入模板")
  @HasPermission({"base-data:customer:import"})
  @GetMapping("/import/template")
  public void downloadImportTemplate() {
    ExcelUtil.exportXls("客户导入模板", CustomerImportModel.class);
  }

  @ApiOperation("导入")
  @HasPermission({"base-data:customer:import"})
  @PostMapping("/import")
  public InvokeResult<Void> importExcel(@NotBlank(message = "ID不能为空") String id,
      @NotNull(message = "请上传文件") MultipartFile file) {

    CustomerImportListener listener = new CustomerImportListener();
    listener.setTaskId(id);
    ExcelUtil.read(file, CustomerImportModel.class, listener).sheet().doRead();

    return InvokeResultBuilder.success();
  }
}
