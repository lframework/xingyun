package com.lframework.xingyun.api.controller.purchase;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.api.bo.purchase.returned.GetPurchaseReturnBo;
import com.lframework.xingyun.api.bo.purchase.returned.PrintPurchaseReturnBo;
import com.lframework.xingyun.api.bo.purchase.returned.QueryPurchaseReturnBo;
import com.lframework.xingyun.api.model.purchase.returned.PurchaseReturnExportModel;
import com.lframework.xingyun.api.print.A4ExcelPortraitPrintBo;
import com.lframework.xingyun.sc.dto.purchase.returned.PurchaseReturnDto;
import com.lframework.xingyun.sc.dto.purchase.returned.PurchaseReturnFullDto;
import com.lframework.xingyun.sc.service.purchase.IPurchaseReturnService;
import com.lframework.xingyun.sc.vo.purchase.returned.ApprovePassPurchaseReturnVo;
import com.lframework.xingyun.sc.vo.purchase.returned.ApproveRefusePurchaseReturnVo;
import com.lframework.xingyun.sc.vo.purchase.returned.BatchApprovePassPurchaseReturnVo;
import com.lframework.xingyun.sc.vo.purchase.returned.BatchApproveRefusePurchaseReturnVo;
import com.lframework.xingyun.sc.vo.purchase.returned.CreatePurchaseReturnVo;
import com.lframework.xingyun.sc.vo.purchase.returned.QueryPurchaseReturnVo;
import com.lframework.xingyun.sc.vo.purchase.returned.UpdatePurchaseReturnVo;
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
 * 采购退单管理
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/purchase/return")
public class PurchaseReturnController extends DefaultBaseController {

  @Autowired
  private IPurchaseReturnService purchaseReturnService;

  /**
   * 打印
   */
  @PreAuthorize("@permission.valid('purchase:return:query')")
  @GetMapping("/print")
  public InvokeResult print(@NotBlank(message = "退单ID不能为空！") String id) {

    PurchaseReturnFullDto data = purchaseReturnService.getDetail(id);

    PrintPurchaseReturnBo result = new PrintPurchaseReturnBo(data);
    A4ExcelPortraitPrintBo<PrintPurchaseReturnBo> printResult = new A4ExcelPortraitPrintBo<>(
        "print/purchase-return.ftl", result);

    return InvokeResultBuilder.success(printResult);
  }

  /**
   * 退单列表
   */
  @PreAuthorize("@permission.valid('purchase:return:query')")
  @GetMapping("/query")
  public InvokeResult query(@Valid QueryPurchaseReturnVo vo) {

    PageResult<PurchaseReturnDto> pageResult = purchaseReturnService
        .query(getPageIndex(vo), getPageSize(vo), vo);

    List<PurchaseReturnDto> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      List<QueryPurchaseReturnBo> results = datas.stream().map(QueryPurchaseReturnBo::new)
          .collect(Collectors.toList());

      PageResultUtil.rebuild(pageResult, results);
    }

    return InvokeResultBuilder.success(pageResult);
  }

  @PreAuthorize("@permission.valid('purchase:return:export')")
  @PostMapping("/export")
  public void export(@Valid QueryPurchaseReturnVo vo) {

    ExcelMultipartWriterSheetBuilder builder = ExcelUtil
        .multipartExportXls("采购退货单信息", PurchaseReturnExportModel.class);

    try {
      int pageIndex = 1;
      while (true) {
        PageResult<PurchaseReturnDto> pageResult = purchaseReturnService
            .query(pageIndex, getExportSize(), vo);
        List<PurchaseReturnDto> datas = pageResult.getDatas();
        List<PurchaseReturnExportModel> models = datas.stream().map(PurchaseReturnExportModel::new)
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
  @PreAuthorize("@permission.valid('purchase:return:query')")
  @GetMapping
  public InvokeResult getById(@NotBlank(message = "退单ID不能为空！") String id) {

    PurchaseReturnFullDto data = purchaseReturnService.getDetail(id);

    GetPurchaseReturnBo result = new GetPurchaseReturnBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 创建
   */
  @PreAuthorize("@permission.valid('purchase:return:add')")
  @PostMapping
  public InvokeResult create(@RequestBody @Valid CreatePurchaseReturnVo vo) {

    vo.validate();

    String id = purchaseReturnService.create(vo);

    return InvokeResultBuilder.success(id);
  }

  /**
   * 修改
   */
  @PreAuthorize("@permission.valid('purchase:return:modify')")
  @PutMapping
  public InvokeResult update(@RequestBody @Valid UpdatePurchaseReturnVo vo) {

    vo.validate();

    purchaseReturnService.update(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核通过
   */
  @PreAuthorize("@permission.valid('purchase:return:approve')")
  @PatchMapping("/approve/pass")
  public InvokeResult approvePass(@RequestBody @Valid ApprovePassPurchaseReturnVo vo) {

    purchaseReturnService.approvePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 批量审核通过
   */
  @PreAuthorize("@permission.valid('purchase:return:approve')")
  @PatchMapping("/approve/pass/batch")
  public InvokeResult batchApprovePass(@RequestBody @Valid BatchApprovePassPurchaseReturnVo vo) {

    purchaseReturnService.batchApprovePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 直接审核通过
   */
  @PreAuthorize("@permission.valid('purchase:return:approve')")
  @PostMapping("/approve/pass/direct")
  public InvokeResult directApprovePass(@RequestBody @Valid CreatePurchaseReturnVo vo) {

    purchaseReturnService.directApprovePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核拒绝
   */
  @PreAuthorize("@permission.valid('purchase:return:approve')")
  @PatchMapping("/approve/refuse")
  public InvokeResult approveRefuse(@RequestBody @Valid ApproveRefusePurchaseReturnVo vo) {

    purchaseReturnService.approveRefuse(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 批量审核拒绝
   */
  @PreAuthorize("@permission.valid('purchase:return:approve')")
  @PatchMapping("/approve/refuse/batch")
  public InvokeResult batchApproveRefuse(
      @RequestBody @Valid BatchApproveRefusePurchaseReturnVo vo) {

    purchaseReturnService.batchApproveRefuse(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 删除
   */
  @PreAuthorize("@permission.valid('purchase:return:delete')")
  @DeleteMapping
  public InvokeResult deleteById(@NotBlank(message = "采购退货单ID不能为空！") String id) {

    purchaseReturnService.deleteById(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 批量删除
   */
  @PreAuthorize("@permission.valid('purchase:return:delete')")
  @DeleteMapping("/batch")
  public InvokeResult deleteByIds(
      @RequestBody @NotEmpty(message = "请选择需要删除的采购退货单！") List<String> ids) {

    purchaseReturnService.deleteByIds(ids);

    return InvokeResultBuilder.success();
  }
}
