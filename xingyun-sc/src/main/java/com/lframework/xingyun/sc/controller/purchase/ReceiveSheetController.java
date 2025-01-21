package com.lframework.xingyun.sc.controller.purchase;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.annotations.security.HasPermission;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.core.bo.print.A4ExcelPortraitPrintBo;
import com.lframework.xingyun.sc.bo.purchase.receive.GetPaymentDateBo;
import com.lframework.xingyun.sc.bo.purchase.receive.GetReceiveSheetBo;
import com.lframework.xingyun.sc.bo.purchase.receive.PrintReceiveSheetBo;
import com.lframework.xingyun.sc.bo.purchase.receive.QueryReceiveSheetBo;
import com.lframework.xingyun.sc.bo.purchase.receive.QueryReceiveSheetWithReturnBo;
import com.lframework.xingyun.sc.bo.purchase.receive.ReceiveSheetWithReturnBo;
import com.lframework.xingyun.sc.dto.purchase.receive.GetPaymentDateDto;
import com.lframework.xingyun.sc.dto.purchase.receive.ReceiveSheetFullDto;
import com.lframework.xingyun.sc.dto.purchase.receive.ReceiveSheetWithReturnDto;
import com.lframework.xingyun.sc.entity.PurchaseConfig;
import com.lframework.xingyun.sc.entity.ReceiveSheet;
import com.lframework.xingyun.sc.excel.purchase.receive.ReceiveSheetExportModel;
import com.lframework.xingyun.sc.excel.purchase.receive.ReceiveSheetImportListener;
import com.lframework.xingyun.sc.excel.purchase.receive.ReceiveSheetImportModel;
import com.lframework.xingyun.sc.excel.purchase.receive.ReceiveSheetPayTypeImportListener;
import com.lframework.xingyun.sc.excel.purchase.receive.ReceiveSheetPayTypeImportModel;
import com.lframework.xingyun.sc.service.purchase.PurchaseConfigService;
import com.lframework.xingyun.sc.service.purchase.ReceiveSheetService;
import com.lframework.xingyun.sc.vo.purchase.receive.ApprovePassReceiveSheetVo;
import com.lframework.xingyun.sc.vo.purchase.receive.ApproveRefuseReceiveSheetVo;
import com.lframework.xingyun.sc.vo.purchase.receive.CreateReceiveSheetVo;
import com.lframework.xingyun.sc.vo.purchase.receive.QueryReceiveSheetVo;
import com.lframework.xingyun.sc.vo.purchase.receive.QueryReceiveSheetWithReturnVo;
import com.lframework.xingyun.sc.vo.purchase.receive.UpdateReceiveSheetVo;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 采购收货单管理
 *
 * @author zmj
 */
@Api(tags = "采购收货单管理")
@Validated
@RestController
@RequestMapping("/purchase/receive/sheet")
public class ReceiveSheetController extends DefaultBaseController {

  @Autowired
  private ReceiveSheetService receiveSheetService;

  @Autowired
  private PurchaseConfigService purchaseConfigService;

  /**
   * 打印
   */
  @ApiOperation("打印")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"purchase:receive:query"})
  @GetMapping("/print")
  public InvokeResult<A4ExcelPortraitPrintBo<PrintReceiveSheetBo>> print(
      @NotBlank(message = "订单ID不能为空！") String id) {

    ReceiveSheetFullDto data = receiveSheetService.getDetail(id);

    PrintReceiveSheetBo result = new PrintReceiveSheetBo(data);

    A4ExcelPortraitPrintBo<PrintReceiveSheetBo> printResult = new A4ExcelPortraitPrintBo<PrintReceiveSheetBo>(
        "print/receive-sheet.ftl", result);

    return InvokeResultBuilder.success(printResult);
  }

  /**
   * 订单列表
   */
  @ApiOperation("订单列表")
  @HasPermission({"purchase:receive:query"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryReceiveSheetBo>> query(@Valid QueryReceiveSheetVo vo) {

    PageResult<ReceiveSheet> pageResult = receiveSheetService.query(getPageIndex(vo),
        getPageSize(vo), vo);

    List<ReceiveSheet> datas = pageResult.getDatas();
    List<QueryReceiveSheetBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryReceiveSheetBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 导出
   */
  @ApiOperation("导出")
  @HasPermission({"purchase:receive:export"})
  @PostMapping("/export")
  public void export(@Valid QueryReceiveSheetVo vo) {

    ExcelMultipartWriterSheetBuilder builder = ExcelUtil.multipartExportXls("采购收货单信息",
        ReceiveSheetExportModel.class);

    try {
      int pageIndex = 1;
      while (true) {
        PageResult<ReceiveSheet> pageResult = receiveSheetService.query(pageIndex, getExportSize(),
            vo);
        List<ReceiveSheet> datas = pageResult.getDatas();
        List<ReceiveSheetExportModel> models = datas.stream().map(ReceiveSheetExportModel::new)
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
  @HasPermission({"purchase:receive:query"})
  @GetMapping
  public InvokeResult<GetReceiveSheetBo> findById(
      @NotBlank(message = "订单ID不能为空！") String id) {

    ReceiveSheetFullDto data = receiveSheetService.getDetail(id);

    GetReceiveSheetBo result = new GetReceiveSheetBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 根据供应商ID查询默认付款日期
   */
  @ApiOperation("根据供应商ID查询默认付款日期")
  @ApiImplicitParam(value = "供应商ID", name = "supplierId", paramType = "query", required = true)
  @HasPermission({"purchase:receive:add", "purchase:receive:modify"})
  @GetMapping("/paymentdate")
  public InvokeResult<GetPaymentDateBo> getPaymentDate(
      @NotBlank(message = "供应商ID不能为空！") String supplierId) {

    GetPaymentDateDto data = receiveSheetService.getPaymentDate(supplierId);

    GetPaymentDateBo result = new GetPaymentDateBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 根据ID查询（采购退货业务）
   */
  @ApiOperation("根据ID查询（采购退货业务）")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"purchase:return:add", "purchase:return:modify"})
  @GetMapping("/return")
  public InvokeResult<ReceiveSheetWithReturnBo> getWithReturn(
      @NotBlank(message = "收货单ID不能为空！") String id) {

    ReceiveSheetWithReturnDto data = receiveSheetService.getWithReturn(id);
    ReceiveSheetWithReturnBo result = new ReceiveSheetWithReturnBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 查询列表（采购退货业务）
   */
  @ApiOperation("查询列表（采购退货业务）")
  @HasPermission({"purchase:return:add", "purchase:return:modify"})
  @GetMapping("/query/return")
  public InvokeResult<PageResult<QueryReceiveSheetWithReturnBo>> queryWithReturn(
      @Valid QueryReceiveSheetWithReturnVo vo) {

    PageResult<ReceiveSheet> pageResult = receiveSheetService.queryWithReturn(getPageIndex(vo),
        getPageSize(vo), vo);
    List<ReceiveSheet> datas = pageResult.getDatas();

    List<QueryReceiveSheetWithReturnBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryReceiveSheetWithReturnBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 加载列表（采购退货业务）
   */
  @ApiOperation("加载列表（采购退货业务）")
  @HasPermission({"purchase:return:add", "purchase:return:modify"})
  @PostMapping("/query/return/load")
  public InvokeResult<List<QueryReceiveSheetWithReturnBo>> loadWithReturn(
      @RequestBody(required = false) List<String> ids) {

    List<ReceiveSheet> datas = receiveSheetService.listByIds(ids);

    List<QueryReceiveSheetWithReturnBo> results = datas.stream()
        .map(QueryReceiveSheetWithReturnBo::new)
        .collect(Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  /**
   * 创建
   */
  @ApiOperation("创建")
  @HasPermission({"purchase:receive:add"})
  @PostMapping
  public InvokeResult<String> create(@RequestBody @Valid CreateReceiveSheetVo vo) {

    vo.validate();

    String id = receiveSheetService.create(vo);

    return InvokeResultBuilder.success(id);
  }

  /**
   * 修改
   */
  @ApiOperation("修改")
  @HasPermission({"purchase:receive:modify"})
  @PutMapping
  public InvokeResult<Void> update(@RequestBody @Valid UpdateReceiveSheetVo vo) {

    vo.validate();

    receiveSheetService.update(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核通过
   */
  @ApiOperation("审核通过")
  @HasPermission({"purchase:receive:approve"})
  @PatchMapping("/approve/pass")
  public InvokeResult<Void> approvePass(@RequestBody @Valid ApprovePassReceiveSheetVo vo) {

    receiveSheetService.approvePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 直接审核通过
   */
  @ApiOperation("直接审核通过")
  @HasPermission({"purchase:receive:approve"})
  @PostMapping("/approve/pass/direct")
  public InvokeResult<Void> directApprovePass(@RequestBody @Valid CreateReceiveSheetVo vo) {

    receiveSheetService.directApprovePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核拒绝
   */
  @ApiOperation("审核拒绝")
  @HasPermission({"purchase:receive:approve"})
  @PatchMapping("/approve/refuse")
  public InvokeResult<Void> approveRefuse(@RequestBody @Valid ApproveRefuseReceiveSheetVo vo) {

    receiveSheetService.approveRefuse(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 删除
   */
  @ApiOperation("删除")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"purchase:receive:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(@NotBlank(message = "采购收货单ID不能为空！") String id) {

    receiveSheetService.deleteById(id);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("下载导入模板")
  @HasPermission({"purchase:receive:import"})
  @GetMapping("/import/template")
  public void downloadImportTemplate() {
    ExcelUtil.exportXls("采购收货单导入模板", ReceiveSheetImportModel.class);
  }

  @ApiOperation("下载导入支付方式模板")
  @HasPermission({"purchase:receive:import"})
  @GetMapping("/import/template/paytype")
  public void downloadImportPayTypeTemplate() {
    ExcelUtil.exportXls("采购收货单导入支付方式模板", ReceiveSheetPayTypeImportModel.class);
  }

  @ApiOperation("导入")
  @HasPermission({"purchase:receive:import"})
  @PostMapping("/import")
  public InvokeResult<Void> importExcel(@NotBlank(message = "ID不能为空") String id,
      @NotNull(message = "请上传文件") MultipartFile file) {

    PurchaseConfig config = purchaseConfigService.get();
    if (config.getReceiveRequirePurchase()) {
      throw new DefaultClientException("“采购收货单是否关联采购订单”必须设置为“否”才可以导入！");
    }

    ReceiveSheetImportListener listener = new ReceiveSheetImportListener();
    listener.setTaskId(id);
    ExcelUtil.read(file, ReceiveSheetImportModel.class, listener).sheet().doRead();

    return InvokeResultBuilder.success();
  }

  @ApiOperation("导入支付方式")
  @HasPermission({"purchase:receive:import"})
  @PostMapping("/import/paytype")
  public InvokeResult<Void> importPayTypeExcel(@NotBlank(message = "ID不能为空") String id,
      @NotNull(message = "请上传文件") MultipartFile file) {

    ReceiveSheetPayTypeImportListener listener = new ReceiveSheetPayTypeImportListener();
    listener.setTaskId(id);
    ExcelUtil.read(file, ReceiveSheetPayTypeImportModel.class, listener).sheet().doRead();

    return InvokeResultBuilder.success();
  }
}
