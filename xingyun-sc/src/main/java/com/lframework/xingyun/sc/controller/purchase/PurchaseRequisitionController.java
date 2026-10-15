package com.lframework.xingyun.sc.controller.purchase;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.xingyun.sc.bo.purchase.requisition.GetPurchaseRequisitionBo;
import com.lframework.xingyun.sc.bo.purchase.requisition.QueryPurchaseRequisitionBo;
import com.lframework.xingyun.sc.entity.PurchaseRequisition;
import com.lframework.xingyun.sc.service.purchase.PurchaseRequisitionService;
import com.lframework.xingyun.sc.vo.purchase.requisition.AdjustRequisitionDetailVo;
import com.lframework.xingyun.sc.vo.purchase.requisition.QueryPurchaseRequisitionVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 采购建议单管理
 */
@Tag(name = "采购建议单管理")
@Validated
@RestController
@RequestMapping("/sc/purchase/requisition")
public class PurchaseRequisitionController extends DefaultBaseController {

  @Autowired
  private PurchaseRequisitionService purchaseRequisitionService;

  @Operation(summary = "采购建议单列表")
  @HasPermission({"purchase:requisition:query"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryPurchaseRequisitionBo>> query(
      @Valid QueryPurchaseRequisitionVo vo) {
    PageResult<PurchaseRequisition> pageResult =
        purchaseRequisitionService.query(getPageIndex(vo), getPageSize(vo), vo);
    List<PurchaseRequisition> datas = pageResult.getDatas();
    List<QueryPurchaseRequisitionBo> results = null;
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryPurchaseRequisitionBo::new).collect(Collectors.toList());
    }
    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  @Operation(summary = "采购建议单详情")
  @Parameter(name = "id", description = "建议单ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"purchase:requisition:query"})
  @GetMapping
  public InvokeResult<GetPurchaseRequisitionBo> getById(
      @NotBlank(message = "建议单ID不能为空！") String id) {
    PurchaseRequisition req = purchaseRequisitionService.getById(id);
    if (req == null) {
      return InvokeResultBuilder.fail("采购建议单不存在！");
    }
    return InvokeResultBuilder.success(new GetPurchaseRequisitionBo(req));
  }

  @Operation(summary = "从生产工单生成采购建议单")
  @Parameter(name = "productionOrderId", description = "生产工单ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"purchase:requisition:add"})
  @PostMapping("/generate")
  public InvokeResult<String> generate(
      @NotBlank(message = "生产工单ID不能为空！") String productionOrderId) {
    String id = purchaseRequisitionService.generateFromProductionOrder(productionOrderId);
    return InvokeResultBuilder.success(id);
  }

  @Operation(summary = "确认采购建议单")
  @Parameter(name = "id", description = "建议单ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"purchase:requisition:modify"})
  @PatchMapping("/confirm")
  public InvokeResult<Void> confirm(@NotBlank(message = "建议单ID不能为空！") String id) {
    purchaseRequisitionService.confirm(id);
    return InvokeResultBuilder.success();
  }

  @Operation(summary = "调整建议单明细采购数量")
  @HasPermission({"purchase:requisition:modify"})
  @PatchMapping("/detail/adjust")
  public InvokeResult<Void> adjustDetail(@RequestBody @Valid AdjustRequisitionDetailVo vo) {
    purchaseRequisitionService.adjustDetail(vo);
    return InvokeResultBuilder.success();
  }

  @Operation(summary = "转采购订单（标记已转，返回建议单ID供前端预填）")
  @Parameter(name = "id", description = "建议单ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"purchase:requisition:convert"})
  @PatchMapping("/convert")
  public InvokeResult<String> convert(@NotBlank(message = "建议单ID不能为空！") String id) {
    String requisitionId = purchaseRequisitionService.convertToPurchaseOrder(id);
    return InvokeResultBuilder.success(requisitionId);
  }

  @Operation(summary = "取消采购建议单")
  @Parameter(name = "id", description = "建议单ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"purchase:requisition:modify"})
  @PatchMapping("/cancel")
  public InvokeResult<Void> cancel(@NotBlank(message = "建议单ID不能为空！") String id) {
    purchaseRequisitionService.cancel(id);
    return InvokeResultBuilder.success();
  }
}
