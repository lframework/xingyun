package com.lframework.xingyun.sc.api.controller.retail;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.core.print.A4ExcelPortraitPrintBo;
import com.lframework.xingyun.sc.api.bo.purchase.receive.GetPaymentDateBo;
import com.lframework.xingyun.sc.api.bo.retail.out.GetRetailOutSheetBo;
import com.lframework.xingyun.sc.api.bo.retail.out.PrintRetailOutSheetBo;
import com.lframework.xingyun.sc.api.bo.retail.out.QueryRetailOutSheetBo;
import com.lframework.xingyun.sc.api.bo.retail.out.QueryRetailOutSheetWithReturnBo;
import com.lframework.xingyun.sc.api.bo.retail.out.RetailOutSheetWithReturnBo;
import com.lframework.xingyun.sc.api.excel.retail.out.RetailOutSheetExportModel;
import com.lframework.xingyun.sc.biz.service.retail.IRetailOutSheetService;
import com.lframework.xingyun.sc.facade.dto.purchase.receive.GetPaymentDateDto;
import com.lframework.xingyun.sc.facade.dto.retail.out.RetailOutSheetFullDto;
import com.lframework.xingyun.sc.facade.dto.retail.out.RetailOutSheetWithReturnDto;
import com.lframework.xingyun.sc.facade.entity.RetailOutSheet;
import com.lframework.xingyun.sc.facade.vo.retail.out.ApprovePassRetailOutSheetVo;
import com.lframework.xingyun.sc.facade.vo.retail.out.ApproveRefuseRetailOutSheetVo;
import com.lframework.xingyun.sc.facade.vo.retail.out.BatchApprovePassRetailOutSheetVo;
import com.lframework.xingyun.sc.facade.vo.retail.out.BatchApproveRefuseRetailOutSheetVo;
import com.lframework.xingyun.sc.facade.vo.retail.out.CreateRetailOutSheetVo;
import com.lframework.xingyun.sc.facade.vo.retail.out.QueryRetailOutSheetVo;
import com.lframework.xingyun.sc.facade.vo.retail.out.QueryRetailOutSheetWithReturnVo;
import com.lframework.xingyun.sc.facade.vo.retail.out.UpdateRetailOutSheetVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
 * 零售出库单管理
 *
 * @author zmj
 */
@Api(tags = "零售出库单管理")
@Validated
@RestController
@RequestMapping("/retail/out/sheet")
public class RetailOutSheetController extends DefaultBaseController {

  @Autowired
  private IRetailOutSheetService retailOutSheetService;

  /**
   * 打印
   */
  @ApiOperation("打印")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @PreAuthorize("@permission.valid('retail:out:query')")
  @GetMapping("/print")
  public InvokeResult<A4ExcelPortraitPrintBo<PrintRetailOutSheetBo>> print(
      @NotBlank(message = "订单ID不能为空！") String id) {

    RetailOutSheetFullDto data = retailOutSheetService.getDetail(id);
    if (data == null) {
      throw new DefaultClientException("零售出库单不存在！");
    }

    PrintRetailOutSheetBo result = new PrintRetailOutSheetBo(data);
    A4ExcelPortraitPrintBo<PrintRetailOutSheetBo> printResult = new A4ExcelPortraitPrintBo<>(
        "print/retail-out-sheet.ftl", result);

    return InvokeResultBuilder.success(printResult);
  }

  /**
   * 订单列表
   */
  @ApiOperation("订单列表")
  @PreAuthorize("@permission.valid('retail:out:query')")
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryRetailOutSheetBo>> query(@Valid QueryRetailOutSheetVo vo) {

    PageResult<RetailOutSheet> pageResult = retailOutSheetService.query(getPageIndex(vo),
        getPageSize(vo), vo);

    List<RetailOutSheet> datas = pageResult.getDatas();
    List<QueryRetailOutSheetBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {

      results = datas.stream().map(QueryRetailOutSheetBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 导出
   */
  @ApiOperation("导出")
  @PreAuthorize("@permission.valid('retail:out:export')")
  @PostMapping("/export")
  public void export(@Valid QueryRetailOutSheetVo vo) {

    ExcelMultipartWriterSheetBuilder builder = ExcelUtil.multipartExportXls("零售出库单信息",
        RetailOutSheetExportModel.class);

    try {
      int pageIndex = 1;
      while (true) {
        PageResult<RetailOutSheet> pageResult = retailOutSheetService.query(pageIndex,
            getExportSize(), vo);
        List<RetailOutSheet> datas = pageResult.getDatas();
        List<RetailOutSheetExportModel> models = datas.stream().map(RetailOutSheetExportModel::new)
            .collect(Collectors.toList());
        builder.doWrite(models);

        if (!pageResult.isHasNext()) {
          break;
        }
        pageIndex++;
      }
    } finally {
      builder.finish();
    }
  }

  /**
   * 根据ID查询
   */
  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @PreAuthorize("@permission.valid('retail:out:query')")
  @GetMapping
  public InvokeResult<GetRetailOutSheetBo> findById(@NotBlank(message = "订单ID不能为空！") String id) {

    RetailOutSheetFullDto data = retailOutSheetService.getDetail(id);

    GetRetailOutSheetBo result = new GetRetailOutSheetBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 根据会员ID查询默认付款日期
   */
  @ApiOperation("根据会员ID查询默认付款日期")
  @ApiImplicitParam(value = "会员ID", name = "memberId", paramType = "query")
  @PreAuthorize("@permission.valid('retail:out:add', 'retail:out:modify')")
  @GetMapping("/paymentdate")
  public InvokeResult<GetPaymentDateBo> getPaymentDate(String memberId) {

    GetPaymentDateDto data = retailOutSheetService.getPaymentDate(memberId);

    GetPaymentDateBo result = new GetPaymentDateBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 根据ID查询（零售退货业务）
   */
  @ApiOperation("根据ID查询（零售退货业务）")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @PreAuthorize("@permission.valid('retail:return:add', 'retail:return:modify')")
  @GetMapping("/return")
  public InvokeResult<RetailOutSheetWithReturnBo> getWithReturn(
      @NotBlank(message = "出库单ID不能为空！") String id) {

    RetailOutSheetWithReturnDto data = retailOutSheetService.getWithReturn(id);
    RetailOutSheetWithReturnBo result = new RetailOutSheetWithReturnBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 查询列表（零售退货业务）
   */
  @ApiOperation("查询列表（零售退货业务）")
  @PreAuthorize("@permission.valid('retail:return:add', 'retail:return:modify')")
  @GetMapping("/query/return")
  public InvokeResult<PageResult<QueryRetailOutSheetWithReturnBo>> queryWithReturn(
      @Valid QueryRetailOutSheetWithReturnVo vo) {

    PageResult<RetailOutSheet> pageResult = retailOutSheetService.queryWithReturn(getPageIndex(vo),
        getPageSize(vo),
        vo);
    List<RetailOutSheet> datas = pageResult.getDatas();

    List<QueryRetailOutSheetWithReturnBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryRetailOutSheetWithReturnBo::new)
          .collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 创建
   */
  @ApiOperation("创建")
  @PreAuthorize("@permission.valid('retail:out:add')")
  @PostMapping
  public InvokeResult<String> create(@RequestBody @Valid CreateRetailOutSheetVo vo) {

    vo.validate();

    String id = retailOutSheetService.create(vo);

    return InvokeResultBuilder.success(id);
  }

  /**
   * 修改
   */
  @ApiOperation("修改")
  @PreAuthorize("@permission.valid('retail:out:modify')")
  @PutMapping
  public InvokeResult<Void> update(@RequestBody @Valid UpdateRetailOutSheetVo vo) {

    vo.validate();

    retailOutSheetService.update(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核通过
   */
  @ApiOperation("审核通过")
  @PreAuthorize("@permission.valid('retail:out:approve')")
  @PatchMapping("/approve/pass")
  public InvokeResult<Void> approvePass(@RequestBody @Valid ApprovePassRetailOutSheetVo vo) {

    retailOutSheetService.approvePass(vo);

    RetailOutSheet outSheet = retailOutSheetService.getById(vo.getId());

    return InvokeResultBuilder.success();
  }

  /**
   * 批量审核通过
   */
  @ApiOperation("批量审核通过")
  @PreAuthorize("@permission.valid('retail:out:approve')")
  @PatchMapping("/approve/pass/batch")
  public InvokeResult<Void> batchApprovePass(
      @RequestBody @Valid BatchApprovePassRetailOutSheetVo vo) {

    retailOutSheetService.batchApprovePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 直接审核通过
   */
  @ApiOperation("直接审核通过")
  @PreAuthorize("@permission.valid('retail:out:approve')")
  @PostMapping("/approve/pass/direct")
  public InvokeResult<Void> directApprovePass(@RequestBody @Valid CreateRetailOutSheetVo vo) {

    retailOutSheetService.directApprovePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核拒绝
   */
  @ApiOperation("审核拒绝")
  @PreAuthorize("@permission.valid('retail:out:approve')")
  @PatchMapping("/approve/refuse")
  public InvokeResult<Void> approveRefuse(@RequestBody @Valid ApproveRefuseRetailOutSheetVo vo) {

    retailOutSheetService.approveRefuse(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 批量审核拒绝
   */
  @ApiOperation("批量审核拒绝")
  @PreAuthorize("@permission.valid('retail:out:approve')")
  @PatchMapping("/approve/refuse/batch")
  public InvokeResult<Void> batchApproveRefuse(
      @RequestBody @Valid BatchApproveRefuseRetailOutSheetVo vo) {

    retailOutSheetService.batchApproveRefuse(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 删除
   */
  @ApiOperation("删除")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @PreAuthorize("@permission.valid('retail:out:delete')")
  @DeleteMapping
  public InvokeResult<Void> deleteById(@NotBlank(message = "零售出库单ID不能为空！") String id) {

    retailOutSheetService.deleteById(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 批量删除
   */
  @ApiOperation("批量删除")
  @PreAuthorize("@permission.valid('retail:out:delete')")
  @DeleteMapping("/batch")
  public InvokeResult<Void> deleteByIds(
      @ApiParam(value = "ID", required = true) @RequestBody @NotEmpty(message = "请选择需要删除的零售出库单！") List<String> ids) {

    retailOutSheetService.deleteByIds(ids);

    return InvokeResultBuilder.success();
  }
}
