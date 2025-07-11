package com.lframework.xingyun.settle.controller;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.mq.core.utils.ExportTaskUtil;
import com.lframework.xingyun.settle.bo.sheet.customer.CustomerSettleBizItemBo;
import com.lframework.xingyun.settle.bo.sheet.customer.GetCustomerSettleSheetBo;
import com.lframework.xingyun.settle.bo.sheet.customer.QueryCustomerSettleSheetBo;
import com.lframework.xingyun.settle.dto.sheet.customer.CustomerSettleBizItemDto;
import com.lframework.xingyun.settle.dto.sheet.customer.CustomerSettleSheetFullDto;
import com.lframework.xingyun.settle.entity.CustomerSettleSheet;
import com.lframework.xingyun.settle.excel.sheet.customer.CustomerSettleSheetExportTaskWorker;
import com.lframework.xingyun.settle.service.CustomerSettleSheetService;
import com.lframework.xingyun.settle.vo.sheet.customer.ApprovePassCustomerSettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.customer.ApproveRefuseCustomerSettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.customer.CreateCustomerSettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.customer.QueryCustomerSettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.customer.QueryCustomerUnSettleBizItemVo;
import com.lframework.xingyun.settle.vo.sheet.customer.UpdateCustomerSettleSheetVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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

/**
 * 客户结算单
 *
 * @author zmj
 */
@Api(tags = "客户结算单")
@Validated
@RestController
@RequestMapping("/customer/settle/sheet")
public class CustomerSettleSheetController extends DefaultBaseController {

  @Autowired
  private CustomerSettleSheetService customerSettleSheetService;

  /**
   * 客户结算单列表
   */
  @ApiOperation("客户结算单列表")
  @HasPermission({"customer-settle:sheet:query"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryCustomerSettleSheetBo>> query(
      @Valid QueryCustomerSettleSheetVo vo) {

    PageResult<CustomerSettleSheet> pageResult = customerSettleSheetService.query(getPageIndex(vo),
        getPageSize(vo), vo);

    List<CustomerSettleSheet> datas = pageResult.getDatas();
    List<QueryCustomerSettleSheetBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryCustomerSettleSheetBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 导出
   */
  @ApiOperation("导出")
  @HasPermission({"customer-settle:sheet:export"})
  @PostMapping("/export")
  public InvokeResult<Void> export(@Valid QueryCustomerSettleSheetVo vo) {

    ExportTaskUtil.exportTask("客户结算单信息", CustomerSettleSheetExportTaskWorker.class, vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 根据ID查询
   */
  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"customer-settle:sheet:query"})
  @GetMapping
  public InvokeResult<GetCustomerSettleSheetBo> findById(
      @NotBlank(message = "客户结算单ID不能为空！") String id) {

    CustomerSettleSheetFullDto data = customerSettleSheetService.getDetail(id);

    GetCustomerSettleSheetBo result = new GetCustomerSettleSheetBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 创建客户结算单
   */
  @ApiOperation("创建客户结算单")
  @HasPermission({"customer-settle:sheet:add"})
  @PostMapping
  public InvokeResult<String> create(@RequestBody @Valid CreateCustomerSettleSheetVo vo) {

    vo.validate();

    String id = customerSettleSheetService.create(vo);

    return InvokeResultBuilder.success(id);
  }

  /**
   * 修改客户结算单
   */
  @ApiOperation("修改客户结算单")
  @HasPermission({"customer-settle:sheet:modify"})
  @PutMapping
  public InvokeResult<Void> update(@RequestBody @Valid UpdateCustomerSettleSheetVo vo) {

    vo.validate();

    customerSettleSheetService.update(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核通过客户结算单
   */
  @ApiOperation("审核通过客户结算单")
  @HasPermission({"customer-settle:sheet:approve"})
  @PatchMapping("/approve/pass")
  public InvokeResult<Void> approvePass(@RequestBody @Valid ApprovePassCustomerSettleSheetVo vo) {

    customerSettleSheetService.approvePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 直接审核通过客户结算单
   */
  @ApiOperation("直接审核通过客户结算单")
  @HasPermission({"customer-settle:sheet:approve"})
  @PostMapping("/approve/pass/direct")
  public InvokeResult<Void> directApprovePass(@RequestBody @Valid CreateCustomerSettleSheetVo vo) {

    customerSettleSheetService.directApprovePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核拒绝客户结算单
   */
  @ApiOperation("审核拒绝客户结算单")
  @HasPermission({"customer-settle:sheet:approve"})
  @PatchMapping("/approve/refuse")
  public InvokeResult<Void> approveRefuse(
      @RequestBody @Valid ApproveRefuseCustomerSettleSheetVo vo) {

    customerSettleSheetService.approveRefuse(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 删除客户结算单
   */
  @ApiOperation("删除客户结算单")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"customer-settle:sheet:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(@NotBlank(message = "客户结算单ID不能为空！") String id) {

    customerSettleSheetService.deleteById(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 查询未结算的业务单据
   */
  @ApiOperation("查询未结算的业务单据")
  @HasPermission({"customer-settle:sheet:add", "customer-settle:sheet:modify"})
  @GetMapping("/unsettle-items")
  public InvokeResult<List<CustomerSettleBizItemBo>> getUnSettleItems(
      @Valid QueryCustomerUnSettleBizItemVo vo) {

    List<CustomerSettleBizItemDto> results = customerSettleSheetService.getUnSettleBizItems(vo);
    List<CustomerSettleBizItemBo> datas = CollectionUtil.emptyList();
    if (!CollectionUtil.isEmpty(results)) {
      datas = results.stream().map(CustomerSettleBizItemBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(datas);
  }
}
