package com.lframework.xingyun.api.controller.purchase;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.api.bo.purchase.receive.GetPaymentDateBo;
import com.lframework.xingyun.api.bo.purchase.receive.GetReceiveSheetBo;
import com.lframework.xingyun.api.bo.purchase.receive.PrintReceiveSheetBo;
import com.lframework.xingyun.api.bo.purchase.receive.QueryReceiveSheetBo;
import com.lframework.xingyun.api.bo.purchase.receive.QueryReceiveSheetWithReturnBo;
import com.lframework.xingyun.api.bo.purchase.receive.ReceiveSheetWithReturnBo;
import com.lframework.xingyun.api.excel.purchase.receive.ReceiveSheetExportModel;
import com.lframework.xingyun.api.excel.purchase.receive.ReceiveSheetImportListener;
import com.lframework.xingyun.api.excel.purchase.receive.ReceiveSheetImportModel;
import com.lframework.xingyun.api.print.A4ExcelPortraitPrintBo;
import com.lframework.xingyun.sc.dto.purchase.receive.GetPaymentDateDto;
import com.lframework.xingyun.sc.dto.purchase.receive.ReceiveSheetFullDto;
import com.lframework.xingyun.sc.dto.purchase.receive.ReceiveSheetWithReturnDto;
import com.lframework.xingyun.sc.entity.PurchaseConfig;
import com.lframework.xingyun.sc.entity.ReceiveSheet;
import com.lframework.xingyun.sc.service.purchase.IPurchaseConfigService;
import com.lframework.xingyun.sc.service.purchase.IReceiveSheetService;
import com.lframework.xingyun.sc.vo.purchase.receive.ApprovePassReceiveSheetVo;
import com.lframework.xingyun.sc.vo.purchase.receive.ApproveRefuseReceiveSheetVo;
import com.lframework.xingyun.sc.vo.purchase.receive.BatchApprovePassReceiveSheetVo;
import com.lframework.xingyun.sc.vo.purchase.receive.BatchApproveRefuseReceiveSheetVo;
import com.lframework.xingyun.sc.vo.purchase.receive.CreateReceiveSheetVo;
import com.lframework.xingyun.sc.vo.purchase.receive.QueryReceiveSheetVo;
import com.lframework.xingyun.sc.vo.purchase.receive.QueryReceiveSheetWithReturnVo;
import com.lframework.xingyun.sc.vo.purchase.receive.UpdateReceiveSheetVo;
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
 * ?????????????????????
 *
 * @author zmj
 */
@Api(tags = "?????????????????????")
@Validated
@RestController
@RequestMapping("/purchase/receive/sheet")
public class ReceiveSheetController extends DefaultBaseController {

  @Autowired
  private IReceiveSheetService receiveSheetService;

  @Autowired
  private IPurchaseConfigService purchaseConfigService;

  /**
   * ??????
   */
  @ApiOperation("??????")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @PreAuthorize("@permission.valid('purchase:receive:query')")
  @GetMapping("/print")
  public InvokeResult<A4ExcelPortraitPrintBo<PrintReceiveSheetBo>> print(
      @NotBlank(message = "??????ID???????????????") String id) {

    ReceiveSheetFullDto data = receiveSheetService.getDetail(id);

    PrintReceiveSheetBo result = new PrintReceiveSheetBo(data);

    A4ExcelPortraitPrintBo<PrintReceiveSheetBo> printResult = new A4ExcelPortraitPrintBo<PrintReceiveSheetBo>(
        "print/receive-sheet.ftl", result);

    return InvokeResultBuilder.success(printResult);
  }

  /**
   * ????????????
   */
  @ApiOperation("????????????")
  @PreAuthorize("@permission.valid('purchase:receive:query')")
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
   * ??????
   */
  @ApiOperation("??????")
  @PreAuthorize("@permission.valid('purchase:receive:export')")
  @PostMapping("/export")
  public void export(@Valid QueryReceiveSheetVo vo) {

    ExcelMultipartWriterSheetBuilder builder = ExcelUtil.multipartExportXls("?????????????????????",
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
   * ??????ID??????
   */
  @ApiOperation("??????ID??????")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @PreAuthorize("@permission.valid('purchase:receive:query')")
  @GetMapping
  public InvokeResult<GetReceiveSheetBo> findById(@NotBlank(message = "??????ID???????????????") String id) {

    ReceiveSheetFullDto data = receiveSheetService.getDetail(id);

    GetReceiveSheetBo result = new GetReceiveSheetBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * ???????????????ID????????????????????????
   */
  @ApiOperation("???????????????ID????????????????????????")
  @ApiImplicitParam(value = "?????????ID", name = "supplierId", paramType = "query", required = true)
  @PreAuthorize("@permission.valid('purchase:receive:add', 'purchase:receive:modify')")
  @GetMapping("/paymentdate")
  public InvokeResult<GetPaymentDateBo> getPaymentDate(
      @NotBlank(message = "?????????ID???????????????") String supplierId) {

    GetPaymentDateDto data = receiveSheetService.getPaymentDate(supplierId);

    GetPaymentDateBo result = new GetPaymentDateBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * ??????ID??????????????????????????????
   */
  @ApiOperation("??????ID??????????????????????????????")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @PreAuthorize("@permission.valid('purchase:return:add', 'purchase:return:modify')")
  @GetMapping("/return")
  public InvokeResult<ReceiveSheetWithReturnBo> getWithReturn(
      @NotBlank(message = "?????????ID???????????????") String id) {

    ReceiveSheetWithReturnDto data = receiveSheetService.getWithReturn(id);
    ReceiveSheetWithReturnBo result = new ReceiveSheetWithReturnBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * ????????????????????????????????????
   */
  @ApiOperation("????????????????????????????????????")
  @PreAuthorize("@permission.valid('purchase:return:add', 'purchase:return:modify')")
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
   * ??????
   */
  @ApiOperation("??????")
  @PreAuthorize("@permission.valid('purchase:receive:add')")
  @PostMapping
  public InvokeResult<String> create(@RequestBody @Valid CreateReceiveSheetVo vo) {

    vo.validate();

    String id = receiveSheetService.create(vo);

    return InvokeResultBuilder.success(id);
  }

  /**
   * ??????
   */
  @ApiOperation("??????")
  @PreAuthorize("@permission.valid('purchase:receive:modify')")
  @PutMapping
  public InvokeResult<Void> update(@RequestBody @Valid UpdateReceiveSheetVo vo) {

    vo.validate();

    receiveSheetService.update(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * ????????????
   */
  @ApiOperation("????????????")
  @PreAuthorize("@permission.valid('purchase:receive:approve')")
  @PatchMapping("/approve/pass")
  public InvokeResult<Void> approvePass(@RequestBody @Valid ApprovePassReceiveSheetVo vo) {

    receiveSheetService.approvePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * ??????????????????
   */
  @ApiOperation("??????????????????")
  @PreAuthorize("@permission.valid('purchase:receive:approve')")
  @PatchMapping("/approve/pass/batch")
  public InvokeResult<Void> batchApprovePass(
      @RequestBody @Valid BatchApprovePassReceiveSheetVo vo) {

    receiveSheetService.batchApprovePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * ??????????????????
   */
  @ApiOperation("??????????????????")
  @PreAuthorize("@permission.valid('purchase:receive:approve')")
  @PostMapping("/approve/pass/direct")
  public InvokeResult<Void> directApprovePass(@RequestBody @Valid CreateReceiveSheetVo vo) {

    receiveSheetService.directApprovePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * ????????????
   */
  @ApiOperation("????????????")
  @PreAuthorize("@permission.valid('purchase:receive:approve')")
  @PatchMapping("/approve/refuse")
  public InvokeResult<Void> approveRefuse(@RequestBody @Valid ApproveRefuseReceiveSheetVo vo) {

    receiveSheetService.approveRefuse(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * ??????????????????
   */
  @ApiOperation("??????????????????")
  @PreAuthorize("@permission.valid('purchase:receive:approve')")
  @PatchMapping("/approve/refuse/batch")
  public InvokeResult<Void> batchApproveRefuse(
      @RequestBody @Valid BatchApproveRefuseReceiveSheetVo vo) {

    receiveSheetService.batchApproveRefuse(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * ??????
   */
  @ApiOperation("??????")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @PreAuthorize("@permission.valid('purchase:receive:delete')")
  @DeleteMapping
  public InvokeResult<Void> deleteById(@NotBlank(message = "???????????????ID???????????????") String id) {

    receiveSheetService.deleteById(id);

    return InvokeResultBuilder.success();
  }

  /**
   * ????????????
   */
  @ApiOperation("????????????")
  @PreAuthorize("@permission.valid('purchase:receive:delete')")
  @DeleteMapping("/batch")
  public InvokeResult<Void> deleteByIds(
      @ApiParam(value = "ID", required = true) @RequestBody @NotEmpty(message = "??????????????????????????????????????????") List<String> ids) {

    receiveSheetService.deleteByIds(ids);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("??????????????????")
  @PreAuthorize("@permission.valid('purchase:receive:import')")
  @GetMapping("/import/template")
  public void downloadImportTemplate() {
    ExcelUtil.exportXls("???????????????????????????", ReceiveSheetImportModel.class);
  }

  @ApiOperation("??????")
  @PreAuthorize("@permission.valid('purchase:receive:import')")
  @PostMapping("/import")
  public InvokeResult<Void> importExcel(@NotBlank(message = "ID????????????") String id,
      @NotNull(message = "???????????????") MultipartFile file) {

    PurchaseConfig config = purchaseConfigService.get();
    if (config.getReceiveRequirePurchase()) {
      throw new DefaultClientException("???????????????????????????????????????????????????????????????????????????????????????");
    }

    ReceiveSheetImportListener listener = new ReceiveSheetImportListener();
    listener.setTaskId(id);
    ExcelUtil.read(file, ReceiveSheetImportModel.class, listener).sheet().doRead();

    return InvokeResultBuilder.success();
  }
}
