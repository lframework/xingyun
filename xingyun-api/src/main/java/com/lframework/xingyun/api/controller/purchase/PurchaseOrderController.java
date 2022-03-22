package com.lframework.xingyun.api.controller.purchase;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.api.bo.purchase.GetPurchaseOrderBo;
import com.lframework.xingyun.api.bo.purchase.PrintPurchaseOrderBo;
import com.lframework.xingyun.api.bo.purchase.PurchaseOrderWithReceiveBo;
import com.lframework.xingyun.api.bo.purchase.PurchaseProductBo;
import com.lframework.xingyun.api.bo.purchase.QueryPurchaseOrderBo;
import com.lframework.xingyun.api.bo.purchase.QueryPurchaseOrderWithReceiveBo;
import com.lframework.xingyun.api.model.purchase.PurchaseOrderExportModel;
import com.lframework.xingyun.api.print.A4ExcelPortraitPrintBo;
import com.lframework.xingyun.basedata.dto.product.info.PurchaseProductDto;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.vo.product.info.QueryPurchaseProductVo;
import com.lframework.xingyun.sc.dto.purchase.PurchaseOrderDto;
import com.lframework.xingyun.sc.dto.purchase.PurchaseOrderFullDto;
import com.lframework.xingyun.sc.dto.purchase.PurchaseOrderWithReceiveDto;
import com.lframework.xingyun.sc.service.purchase.IPurchaseOrderService;
import com.lframework.xingyun.sc.vo.purchase.ApprovePassPurchaseOrderVo;
import com.lframework.xingyun.sc.vo.purchase.ApproveRefusePurchaseOrderVo;
import com.lframework.xingyun.sc.vo.purchase.BatchApprovePassPurchaseOrderVo;
import com.lframework.xingyun.sc.vo.purchase.BatchApproveRefusePurchaseOrderVo;
import com.lframework.xingyun.sc.vo.purchase.CreatePurchaseOrderVo;
import com.lframework.xingyun.sc.vo.purchase.QueryPurchaseOrderVo;
import com.lframework.xingyun.sc.vo.purchase.QueryPurchaseOrderWithRecevieVo;
import com.lframework.xingyun.sc.vo.purchase.UpdatePurchaseOrderVo;
import java.util.Collections;
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
 * 采购订单管理
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/purchase/order")
public class PurchaseOrderController extends DefaultBaseController {

  @Autowired
  private IPurchaseOrderService purchaseOrderService;

  @Autowired
  private IProductService productService;

  /**
   * 打印
   *
   * @param id
   * @return
   */
  @PreAuthorize("@permission.valid('purchase:order:query')")
  @GetMapping("/print")
  public InvokeResult print(@NotBlank(message = "订单ID不能为空！") String id) {
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
  @PreAuthorize("@permission.valid('purchase:order:query')")
  @GetMapping("/query")
  public InvokeResult query(@Valid QueryPurchaseOrderVo vo) {

    PageResult<PurchaseOrderDto> pageResult = purchaseOrderService
        .query(getPageIndex(vo), getPageSize(vo), vo);

    List<PurchaseOrderDto> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      List<QueryPurchaseOrderBo> results = datas.stream().map(QueryPurchaseOrderBo::new)
          .collect(Collectors.toList());

      PageResultUtil.rebuild(pageResult, results);
    }

    return InvokeResultBuilder.success(pageResult);
  }

  @PreAuthorize("@permission.valid('purchase:order:export')")
  @PostMapping("/export")
  public void export(@Valid QueryPurchaseOrderVo vo) {

    ExcelMultipartWriterSheetBuilder builder = ExcelUtil
        .multipartExportXls("采购单信息", PurchaseOrderExportModel.class);

    try {
      int pageIndex = 1;
      while (true) {
        PageResult<PurchaseOrderDto> pageResult = purchaseOrderService
            .query(pageIndex, getExportSize(), vo);
        List<PurchaseOrderDto> datas = pageResult.getDatas();
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
   * 根据关键字查询商品
   */
  @PreAuthorize("@permission.valid('purchase:order:add', 'purchase:order:modify', 'purchase:receive:add', 'purchase:receive:modify', 'purchase:return:add', 'purchase:return:modify')")
  @GetMapping("/product/search")
  public InvokeResult searchProducts(@NotBlank(message = "仓库ID不能为空！") String scId,
      String condition) {

    if (StringUtil.isBlank(condition)) {
      return InvokeResultBuilder.success(Collections.EMPTY_LIST);
    }

    PageResult<PurchaseProductDto> pageResult = productService
        .queryPurchaseByCondition(getPageIndex(), getPageSize(), condition);
    List<PurchaseProductBo> results = Collections.EMPTY_LIST;
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
  @PreAuthorize("@permission.valid('purchase:order:add', 'purchase:order:modify', 'purchase:receive:add', 'purchase:receive:modify', 'purchase:return:add', 'purchase:return:modify')")
  @GetMapping("/product/list")
  public InvokeResult queryProductList(@Valid QueryPurchaseProductVo vo) {

    PageResult<PurchaseProductDto> pageResult = productService
        .queryPurchaseList(getPageIndex(), getPageSize(), vo);
    List<PurchaseProductBo> results = Collections.EMPTY_LIST;
    List<PurchaseProductDto> datas = pageResult.getDatas();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(t -> new PurchaseProductBo(vo.getScId(), t))
          .collect(Collectors.toList());

      PageResultUtil.rebuild(pageResult, results);
    }

    return InvokeResultBuilder.success(pageResult);
  }

  /**
   * 根据ID查询
   */
  @PreAuthorize("@permission.valid('purchase:order:query')")
  @GetMapping
  public InvokeResult getById(@NotBlank(message = "订单ID不能为空！") String id) {

    PurchaseOrderFullDto data = purchaseOrderService.getDetail(id);

    GetPurchaseOrderBo result = new GetPurchaseOrderBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 根据ID查询（收货业务）
   */
  @PreAuthorize("@permission.valid('purchase:receive:add', 'purchase:receive:modify')")
  @GetMapping("/receive")
  public InvokeResult getWithReceive(@NotBlank(message = "订单ID不能为空！") String id) {

    PurchaseOrderWithReceiveDto data = purchaseOrderService.getWithReceive(id);
    PurchaseOrderWithReceiveBo result = new PurchaseOrderWithReceiveBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 查询列表（收货业务）
   */
  @PreAuthorize("@permission.valid('purchase:receive:add', 'purchase:receive:modify')")
  @GetMapping("/query/receive")
  public InvokeResult getWithReceive(@Valid QueryPurchaseOrderWithRecevieVo vo) {

    PageResult<PurchaseOrderDto> pageResult = purchaseOrderService
        .queryWithReceive(getPageIndex(vo), getPageSize(vo), vo);
    List<PurchaseOrderDto> datas = pageResult.getDatas();

    List<QueryPurchaseOrderWithReceiveBo> results = Collections.EMPTY_LIST;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryPurchaseOrderWithReceiveBo::new)
          .collect(Collectors.toList());
      PageResultUtil.rebuild(pageResult, results);
    }

    return InvokeResultBuilder.success(pageResult);
  }

  /**
   * 创建订单
   */
  @PreAuthorize("@permission.valid('purchase:order:add')")
  @PostMapping
  public InvokeResult create(@RequestBody @Valid CreatePurchaseOrderVo vo) {

    vo.validate();

    String id = purchaseOrderService.create(vo);

    return InvokeResultBuilder.success(id);
  }

  /**
   * 修改订单
   */
  @PreAuthorize("@permission.valid('purchase:order:modify')")
  @PutMapping
  public InvokeResult update(@RequestBody @Valid UpdatePurchaseOrderVo vo) {

    vo.validate();

    purchaseOrderService.update(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核通过订单
   */
  @PreAuthorize("@permission.valid('purchase:order:approve')")
  @PatchMapping("/approve/pass")
  public InvokeResult approvePass(@RequestBody @Valid ApprovePassPurchaseOrderVo vo) {

    purchaseOrderService.approvePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 批量审核通过订单
   */
  @PreAuthorize("@permission.valid('purchase:order:approve')")
  @PatchMapping("/approve/pass/batch")
  public InvokeResult batchApprovePass(@RequestBody @Valid BatchApprovePassPurchaseOrderVo vo) {

    purchaseOrderService.batchApprovePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 直接审核通过订单
   */
  @PreAuthorize("@permission.valid('purchase:order:approve')")
  @PostMapping("/approve/pass/direct")
  public InvokeResult directApprovePass(@RequestBody @Valid CreatePurchaseOrderVo vo) {

    purchaseOrderService.directApprovePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核拒绝订单
   */
  @PreAuthorize("@permission.valid('purchase:order:approve')")
  @PatchMapping("/approve/refuse")
  public InvokeResult approveRefuse(@RequestBody @Valid ApproveRefusePurchaseOrderVo vo) {

    purchaseOrderService.approveRefuse(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 批量审核拒绝订单
   */
  @PreAuthorize("@permission.valid('purchase:order:approve')")
  @PatchMapping("/approve/refuse/batch")
  public InvokeResult batchApproveRefuse(@RequestBody @Valid BatchApproveRefusePurchaseOrderVo vo) {

    purchaseOrderService.batchApproveRefuse(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 删除订单
   */
  @PreAuthorize("@permission.valid('purchase:order:delete')")
  @DeleteMapping
  public InvokeResult deleteById(@NotBlank(message = "订单ID不能为空！") String id) {

    purchaseOrderService.deleteById(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 批量删除订单
   */
  @PreAuthorize("@permission.valid('purchase:order:delete')")
  @DeleteMapping("/batch")
  public InvokeResult deleteByIds(
      @RequestBody @NotEmpty(message = "请选择需要删除的订单！") List<String> ids) {

    purchaseOrderService.deleteByIds(ids);

    return InvokeResultBuilder.success();
  }

  /**
   * 取消审核订单
   */
  @PreAuthorize("@permission.valid('purchase:order:approve')")
  @PatchMapping("/approve/cancel")
  public InvokeResult cancelApprovePass(@NotBlank(message = "订单ID不能为空！") String id) {

    purchaseOrderService.cancelApprovePass(id);

    return InvokeResultBuilder.success();
  }
}
