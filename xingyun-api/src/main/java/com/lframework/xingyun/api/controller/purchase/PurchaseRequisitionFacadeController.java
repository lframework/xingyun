package com.lframework.xingyun.api.controller.purchase;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.xingyun.produce.entity.ProductionOrder;
import com.lframework.xingyun.produce.entity.ProductionOrderDetail;
import com.lframework.xingyun.produce.service.order.ProductionOrderDetailService;
import com.lframework.xingyun.produce.service.order.ProductionOrderService;
import com.lframework.xingyun.sc.service.purchase.PurchaseRequisitionService;
import com.lframework.xingyun.sc.vo.purchase.requisition.GenerateRequisitionVo;
import com.lframework.xingyun.sc.vo.purchase.requisition.RequisitionDetailItemVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 采购建议单 Facade —— 读取生产工单明细（已含 BOM 展开的 requireNum）后调用 sc 层生成建议单。
 * 放在 xingyun-api 层以避免 xingyun-sc 和 xingyun-produce 之间的循环依赖。
 */
@Tag(name = "采购建议单（Facade）")
@Validated
@RestController
@RequestMapping("/sc/purchase/requisition")
public class PurchaseRequisitionFacadeController extends DefaultBaseController {

  @Autowired
  private ProductionOrderService productionOrderService;

  @Autowired
  private ProductionOrderDetailService productionOrderDetailService;

  @Autowired
  private PurchaseRequisitionService purchaseRequisitionService;

  @Operation(summary = "从生产工单生成采购建议单")
  @Parameter(name = "productionOrderId", description = "生产工单ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"purchase:requisition:add"})
  @PostMapping("/generate/from-order")
  public InvokeResult<String> generateFromOrder(
      @NotBlank(message = "生产工单ID不能为空！") String productionOrderId) {

    ProductionOrder order = productionOrderService.getById(productionOrderId);
    if (order == null) {
      throw new DefaultClientException("生产工单不存在！");
    }

    List<ProductionOrderDetail> details =
        productionOrderDetailService.listByOrderId(productionOrderId);
    if (details == null || details.isEmpty()) {
      throw new DefaultClientException("生产工单无明细，无法生成采购建议单！");
    }

    List<RequisitionDetailItemVo> items = details.stream().map(d -> {
      RequisitionDetailItemVo item = new RequisitionDetailItemVo();
      item.setProductId(d.getProductId());
      item.setSkuId(d.getSkuId());
      item.setRequireNum(d.getRequireNum());
      return item;
    }).collect(Collectors.toList());

    GenerateRequisitionVo vo = new GenerateRequisitionVo();
    vo.setProductionOrderId(productionOrderId);
    vo.setScId(order.getScId());
    vo.setItems(items);

    String id = purchaseRequisitionService.generate(vo);
    return InvokeResultBuilder.success(id);
  }
}
