package com.lframework.xingyun.sc.controller.purchase;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.annotations.security.HasPermission;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.core.bo.print.A4ExcelPortraitPrintBo;
import com.lframework.xingyun.sc.bo.purchase.GetPurchaseOrderBo;
import com.lframework.xingyun.sc.bo.purchase.PrintPurchaseOrderBo;
import com.lframework.xingyun.sc.bo.purchase.PurchaseOrderWithReceiveBo;
import com.lframework.xingyun.sc.bo.purchase.PurchaseProductBo;
import com.lframework.xingyun.sc.bo.purchase.QueryPurchaseOrderBo;
import com.lframework.xingyun.sc.bo.purchase.QueryPurchaseOrderWithReceiveBo;
import com.lframework.xingyun.sc.dto.purchase.PurchaseOrderFullDto;
import com.lframework.xingyun.sc.dto.purchase.PurchaseOrderWithReceiveDto;
import com.lframework.xingyun.sc.dto.purchase.PurchaseProductDto;
import com.lframework.xingyun.sc.entity.PurchaseOrder;
import com.lframework.xingyun.sc.excel.purchase.PurchaseOrderExportModel;
import com.lframework.xingyun.sc.excel.purchase.PurchaseOrderImportListener;
import com.lframework.xingyun.sc.excel.purchase.PurchaseOrderImportModel;
import com.lframework.xingyun.sc.excel.purchase.PurchaseOrderPayTypeImportListener;
import com.lframework.xingyun.sc.excel.purchase.PurchaseOrderPayTypeImportModel;
import com.lframework.xingyun.sc.service.purchase.PurchaseOrderService;
import com.lframework.xingyun.sc.vo.purchase.ApprovePassPurchaseOrderVo;
import com.lframework.xingyun.sc.vo.purchase.ApproveRefusePurchaseOrderVo;
import com.lframework.xingyun.sc.vo.purchase.CreatePurchaseOrderVo;
import com.lframework.xingyun.sc.vo.purchase.QueryPurchaseOrderVo;
import com.lframework.xingyun.sc.vo.purchase.QueryPurchaseOrderWithReceiveVo;
import com.lframework.xingyun.sc.vo.purchase.QueryPurchaseProductVo;
import com.lframework.xingyun.sc.vo.purchase.UpdatePurchaseOrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
 * 采购订单管理
 *
 * @author zmj
 */
@Api(tags = "采购订单管理")
@Validated
@RestController
@RequestMapping("/purchase/order")
public class PurchaseOrderController extends DefaultBaseController {

  @Autowired
  private PurchaseOrderService purchaseOrderService;

  /**
   * 打印
   */
  @ApiOperation("打印")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"purchase:order:query"})
  @GetMapping("/print")
  public InvokeResult<A4ExcelPortraitPrintBo<PrintPurchaseOrderBo>> print(
      @NotBlank(message = "订单ID不能为空！") String id) {

    PurchaseOrderFullDto data = purchaseOrderService.getDetail(id);
    if (data == null) {
      throw new DefaultClientException("订单不存在！");
    }

    PrintPurchaseOrderBo result = new PrintPurchaseOrderBo(data);

    A4ExcelPortraitPrintBo<PrintPurchaseOrderBo> printResult = new A4ExcelPortraitPrintBo<>(
        "print/purchase-order.ftl", result);

    return InvokeResultBuilder.success(printResult);
  }

  /**
   * 订单列表
   */
  @ApiOperation("订单列表")
  @HasPermission({"purchase:order:query"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryPurchaseOrderBo>> query(@Valid QueryPurchaseOrderVo vo) {

    PageResult<PurchaseOrder> pageResult = purchaseOrderService.query(getPageIndex(vo),
        getPageSize(vo), vo);

    List<PurchaseOrder> datas = pageResult.getDatas();
    List<QueryPurchaseOrderBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {

      results = datas.stream().map(QueryPurchaseOrderBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 导出
   */
  @ApiOperation("导出")
  @HasPermission({"purchase:order:export"})
  @PostMapping("/export")
  public void export(@Valid QueryPurchaseOrderVo vo) {

    ExcelMultipartWriterSheetBuilder builder = ExcelUtil.multipartExportXls("采购单信息",
        PurchaseOrderExportModel.class);

    try {
      int pageIndex = 1;
      while (true) {
        PageResult<PurchaseOrder> pageResult = purchaseOrderService.query(pageIndex,
            getExportSize(), vo);
        List<PurchaseOrder> datas = pageResult.getDatas();
        List<PurchaseOrderExportModel> models = datas.stream().map(PurchaseOrderExportModel::new)
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
  @HasPermission({"purchase:order:query"})
  @GetMapping
  public InvokeResult<GetPurchaseOrderBo> findById(
      @NotBlank(message = "订单ID不能为空！") String id) {

    PurchaseOrderFullDto data = purchaseOrderService.getDetail(id);

    GetPurchaseOrderBo result = new GetPurchaseOrderBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 根据ID查询（收货业务）
   */
  @ApiOperation("根据ID查询（收货业务）")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"purchase:receive:add", "purchase:receive:modify"})
  @GetMapping("/receive")
  public InvokeResult<PurchaseOrderWithReceiveBo> getWithReceive(
      @NotBlank(message = "订单ID不能为空！") String id) {

    PurchaseOrderWithReceiveDto data = purchaseOrderService.getWithReceive(id);
    PurchaseOrderWithReceiveBo result = new PurchaseOrderWithReceiveBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 查询列表（收货业务）
   */
  @ApiOperation("查询列表（收货业务）")
  @HasPermission({"purchase:receive:add", "purchase:receive:modify"})
  @GetMapping("/query/receive")
  public InvokeResult<PageResult<QueryPurchaseOrderWithReceiveBo>> queryWithReceive(
      @Valid QueryPurchaseOrderWithReceiveVo vo) {

    PageResult<PurchaseOrder> pageResult = purchaseOrderService.queryWithReceive(getPageIndex(vo),
        getPageSize(vo),
        vo);
    List<PurchaseOrder> datas = pageResult.getDatas();

    List<QueryPurchaseOrderWithReceiveBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryPurchaseOrderWithReceiveBo::new)
          .collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 加载列表（收货业务）
   */
  @ApiOperation("加载列表（收货业务）")
  @HasPermission({"purchase:receive:add", "purchase:receive:modify"})
  @PostMapping("/query/receive/load")
  public InvokeResult<List<QueryPurchaseOrderWithReceiveBo>> loadWithReceive(
      @RequestBody(required = false) List<String> ids) {

    List<PurchaseOrder> datas = purchaseOrderService.listByIds(ids);

    List<QueryPurchaseOrderWithReceiveBo> results = datas.stream()
        .map(QueryPurchaseOrderWithReceiveBo::new)
        .collect(Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  /**
   * 创建订单
   */
  @ApiOperation("创建订单")
  @HasPermission({"purchase:order:add"})
  @PostMapping
  public InvokeResult<String> create(@RequestBody @Valid CreatePurchaseOrderVo vo) {

    vo.validate();

    String id = purchaseOrderService.create(vo);

    return InvokeResultBuilder.success(id);
  }

  /**
   * 修改订单
   */
  @ApiOperation("修改订单")
  @HasPermission({"purchase:order:modify"})
  @PutMapping
  public InvokeResult<Void> update(@RequestBody @Valid UpdatePurchaseOrderVo vo) {

    vo.validate();

    purchaseOrderService.update(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核通过订单
   */
  @ApiOperation("审核通过订单")
  @HasPermission({"purchase:order:approve"})
  @PatchMapping("/approve/pass")
  public InvokeResult<Void> approvePass(@RequestBody @Valid ApprovePassPurchaseOrderVo vo) {

    purchaseOrderService.approvePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 直接审核通过订单
   */
  @ApiOperation("直接审核通过订单")
  @HasPermission({"purchase:order:approve"})
  @PostMapping("/approve/pass/direct")
  public InvokeResult<Void> directApprovePass(@RequestBody @Valid CreatePurchaseOrderVo vo) {

    purchaseOrderService.directApprovePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核拒绝订单
   */
  @ApiOperation("审核拒绝订单")
  @HasPermission({"purchase:order:approve"})
  @PatchMapping("/approve/refuse")
  public InvokeResult<Void> approveRefuse(@RequestBody @Valid ApproveRefusePurchaseOrderVo vo) {

    purchaseOrderService.approveRefuse(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 删除订单
   */
  @ApiOperation("删除订单")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"purchase:order:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(@NotBlank(message = "订单ID不能为空！") String id) {

    purchaseOrderService.deleteById(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 取消审核订单
   */
  @ApiOperation("取消审核订单")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"purchase:order:approve"})
  @PatchMapping("/approve/cancel")
  public InvokeResult<Void> cancelApprovePass(@NotBlank(message = "订单ID不能为空！") String id) {

    purchaseOrderService.cancelApprovePass(id);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("下载导入模板")
  @HasPermission({"purchase:order:import"})
  @GetMapping("/import/template")
  public void downloadImportTemplate() {
    ExcelUtil.exportXls("采购订单导入模板", PurchaseOrderImportModel.class);
  }

  @ApiOperation("下载导入约定支付模板")
  @HasPermission({"purchase:order:import"})
  @GetMapping("/import/template/paytype")
  public void downloadImportPayTypeTemplate() {
    ExcelUtil.exportXls("采购订单导入约定支付模板", PurchaseOrderPayTypeImportModel.class);
  }

  @ApiOperation("导入")
  @HasPermission({"purchase:order:import"})
  @PostMapping("/import")
  public InvokeResult<Void> importExcel(@NotBlank(message = "ID不能为空") String id,
      @NotNull(message = "请上传文件") MultipartFile file) {

    PurchaseOrderImportListener listener = new PurchaseOrderImportListener();
    listener.setTaskId(id);
    ExcelUtil.read(file, PurchaseOrderImportModel.class, listener).sheet().doRead();

    return InvokeResultBuilder.success();
  }

  @ApiOperation("导入约定支付")
  @HasPermission({"purchase:order:import"})
  @PostMapping("/import/paytype")
  public InvokeResult<Void> importPayTypeExcel(@NotBlank(message = "ID不能为空") String id,
      @NotNull(message = "请上传文件") MultipartFile file) {

    PurchaseOrderPayTypeImportListener listener = new PurchaseOrderPayTypeImportListener();
    listener.setTaskId(id);
    ExcelUtil.read(file, PurchaseOrderPayTypeImportModel.class, listener).sheet().doRead();

    return InvokeResultBuilder.success();
  }

  /**
   * 根据关键字查询商品
   */
  @ApiOperation("根据关键字查询可采购商品")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "仓库ID", name = "scId", paramType = "query", required = true),
      @ApiImplicitParam(value = "关键字", name = "condition", paramType = "query", required = true)})
  @HasPermission({"purchase:order:add", "purchase:order:modify", "purchase:receive:add",
      "purchase:receive:modify", "purchase:return:add", "purchase:return:modify"})
  @GetMapping("/product/search")
  public InvokeResult<List<PurchaseProductBo>> searchPurchaseProducts(
      @NotBlank(message = "仓库ID不能为空！") String scId, String condition) {

    if (StringUtil.isBlank(condition)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    PageResult<PurchaseProductDto> pageResult = purchaseOrderService.queryPurchaseByCondition(
        getPageIndex(), getPageSize(), condition);
    List<PurchaseProductBo> results = CollectionUtil.emptyList();
    List<PurchaseProductDto> datas = pageResult.getDatas();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(t -> new PurchaseProductBo(scId, t))
          .collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  /**
   * 查询商品列表
   */
  @ApiOperation("查询可采购商品列表")
  @HasPermission({"purchase:order:add", "purchase:order:modify", "purchase:receive:add",
      "purchase:receive:modify", "purchase:return:add", "purchase:return:modify"})
  @GetMapping("/product/list")
  public InvokeResult<PageResult<PurchaseProductBo>> queryPurchaseProductList(
      @Valid QueryPurchaseProductVo vo) {

    PageResult<PurchaseProductDto> pageResult = purchaseOrderService.queryPurchaseList(
        getPageIndex(vo),
        getPageSize(vo), vo);
    List<PurchaseProductBo> results = null;
    List<PurchaseProductDto> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(t -> new PurchaseProductBo(vo.getScId(), t))
          .collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }
}
