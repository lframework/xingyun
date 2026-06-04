package com.lframework.xingyun.sc.controller.purchase;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.ExcelUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.mq.core.utils.ExportTaskUtil;
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
import com.lframework.xingyun.sc.excel.purchase.PurchaseOrderExportTaskWorker;
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
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Tag(name = "采购订单管理")
@Validated
@RestController
@RequestMapping("/purchase/order")
public class PurchaseOrderController extends DefaultBaseController {

  @Autowired
  private PurchaseOrderService purchaseOrderService;

  /**
   * 打印
   */
  @Operation(summary = "打印")
  @Parameter(name = "id", description = "ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"purchase:order:query"})
  @GetMapping("/print")
  public InvokeResult<PrintPurchaseOrderBo> print(
      @NotBlank(message = "订单ID不能为空！") String id) {

    PurchaseOrderFullDto data = purchaseOrderService.getDetail(id, false);
    if (data == null) {
      throw new DefaultClientException("订单不存在！");
    }

    PrintPurchaseOrderBo result = new PrintPurchaseOrderBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 订单列表
   */
  @Operation(summary = "订单列表")
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
  @Operation(summary = "导出")
  @HasPermission({"purchase:order:export"})
  @PostMapping("/export")
  public InvokeResult<Void> export(@Valid QueryPurchaseOrderVo vo) {

    ExportTaskUtil.exportTask("采购单信息", PurchaseOrderExportTaskWorker.class, vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 根据ID查询
   */
  @Operation(summary = "根据ID查询")
  @Parameters({
      @Parameter(name = "id", description = "ID", in = ParameterIn.QUERY, required = true),
      @Parameter(name = "isForm", description = "是否为表单数据", in = ParameterIn.QUERY, schema = @Schema(defaultValue = "false"))
  })
  @HasPermission({"purchase:order:query"})
  @GetMapping
  public InvokeResult<GetPurchaseOrderBo> findById(
      @NotBlank(message = "订单ID不能为空！") String id, Boolean isForm) {

    PurchaseOrderFullDto data = purchaseOrderService.getDetail(id, isForm);

    GetPurchaseOrderBo result = new GetPurchaseOrderBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 根据ID查询（收货业务）
   */
  @Operation(summary = "根据ID查询（收货业务）")
  @Parameter(name = "id", description = "ID", in = ParameterIn.QUERY, required = true)
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
  @Operation(summary = "查询列表（收货业务）")
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
  @Operation(summary = "加载列表（收货业务）")
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
  @Operation(summary = "创建订单")
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
  @Operation(summary = "修改订单")
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
  @Operation(summary = "审核通过订单")
  @HasPermission({"purchase:order:approve"})
  @PatchMapping("/approve/pass")
  public InvokeResult<Void> approvePass(@RequestBody @Valid ApprovePassPurchaseOrderVo vo) {

    purchaseOrderService.approvePass(vo, getCurrentUser().getId());

    return InvokeResultBuilder.success();
  }

  /**
   * 直接审核通过订单
   */
  @Operation(summary = "直接审核通过订单")
  @HasPermission({"purchase:order:approve"})
  @PostMapping("/approve/pass/direct")
  public InvokeResult<Void> directApprovePass(@RequestBody @Valid CreatePurchaseOrderVo vo) {

    vo.validate();

    purchaseOrderService.directApprovePass(vo, getCurrentUser().getId());

    return InvokeResultBuilder.success();
  }

  /**
   * 审核拒绝订单
   */
  @Operation(summary = "审核拒绝订单")
  @HasPermission({"purchase:order:approve"})
  @PatchMapping("/approve/refuse")
  public InvokeResult<Void> approveRefuse(@RequestBody @Valid ApproveRefusePurchaseOrderVo vo) {

    purchaseOrderService.approveRefuse(vo, getCurrentUser().getId());

    return InvokeResultBuilder.success();
  }

  /**
   * 删除订单
   */
  @Operation(summary = "删除订单")
  @Parameter(name = "id", description = "ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"purchase:order:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(@NotBlank(message = "订单ID不能为空！") String id) {

    purchaseOrderService.deleteById(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 取消审核订单
   */
  @Operation(summary = "取消审核订单")
  @Parameter(name = "id", description = "ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"purchase:order:approve"})
  @PatchMapping("/approve/cancel")
  public InvokeResult<Void> cancelApprovePass(@NotBlank(message = "订单ID不能为空！") String id) {

    purchaseOrderService.cancelApprovePass(id);

    return InvokeResultBuilder.success();
  }

  @Operation(summary = "下载导入模板")
  @HasPermission({"purchase:order:import"})
  @GetMapping("/import/template")
  public void downloadImportTemplate() {
    ExcelUtil.exportXlsx("采购订单导入模板", PurchaseOrderImportModel.class);
  }

  @Operation(summary = "下载导入约定支付模板")
  @HasPermission({"purchase:order:import"})
  @GetMapping("/import/template/paytype")
  public void downloadImportPayTypeTemplate() {
    ExcelUtil.exportXlsx("采购订单导入约定支付模板", PurchaseOrderPayTypeImportModel.class);
  }

  @Operation(summary = "导入")
  @HasPermission({"purchase:order:import"})
  @PostMapping("/import")
  public InvokeResult<Void> importExcel(@NotBlank(message = "ID不能为空") String id,
      @NotNull(message = "请上传文件") MultipartFile file) {

    PurchaseOrderImportListener listener = new PurchaseOrderImportListener();
    listener.setTaskId(id);
    ExcelUtil.read(file, PurchaseOrderImportModel.class, listener).sheet().doRead();

    return InvokeResultBuilder.success();
  }

  @Operation(summary = "导入约定支付")
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
  @Operation(summary = "根据关键字查询可采购商品")
  @Parameters({
      @Parameter(name = "scId", description = "仓库ID", in = ParameterIn.QUERY, required = true),
      @Parameter(name = "condition", description = "关键字", in = ParameterIn.QUERY, required = true)})
  @HasPermission({"purchase:order:add", "purchase:order:modify", "purchase:receive:add",
      "purchase:receive:modify", "purchase:return:add", "purchase:return:modify"})
  @GetMapping("/product/search")
  public InvokeResult<List<PurchaseProductBo>> searchPurchaseProducts(
      @NotBlank(message = "仓库ID不能为空！") String scId, String condition, Boolean isReturn) {

    if (isReturn == null) {
      isReturn = false;
    }

    if (StringUtil.isBlank(condition)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    PageResult<PurchaseProductDto> pageResult = purchaseOrderService.queryPurchaseByCondition(
        getPageIndex(), getPageSize(), scId, condition, isReturn);
    List<PurchaseProductBo> results = CollectionUtil.emptyList();
    List<PurchaseProductDto> datas = pageResult.getDatas();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(PurchaseProductBo::new)
          .collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  /**
   * 查询商品列表
   */
  @Operation(summary = "查询可采购商品列表")
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
      results = datas.stream().map(PurchaseProductBo::new)
          .collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }
}
