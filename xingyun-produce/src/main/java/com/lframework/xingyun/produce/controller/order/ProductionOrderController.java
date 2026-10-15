package com.lframework.xingyun.produce.controller.order;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.xingyun.produce.bo.order.GetProductionOrderBo;
import com.lframework.xingyun.produce.bo.order.QueryProductionOrderBo;
import com.lframework.xingyun.produce.entity.ProductionOrder;
import com.lframework.xingyun.produce.service.order.ProductionOrderService;
import com.lframework.xingyun.produce.vo.order.CompleteProductionOrderVo;
import com.lframework.xingyun.produce.vo.order.CreateProductionOrderVo;
import com.lframework.xingyun.produce.vo.order.QueryProductionOrderVo;
import com.lframework.xingyun.produce.vo.order.UpdateProductionOrderVo;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 生产工单管理
 */
@Tag(name = "生产工单管理")
@Validated
@RestController
@RequestMapping("/produce/order")
public class ProductionOrderController extends DefaultBaseController {

  @Autowired
  private ProductionOrderService productionOrderService;

  @Operation(summary = "工单列表")
  @HasPermission({"produce:order:query"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryProductionOrderBo>> query(@Valid QueryProductionOrderVo vo) {
    PageResult<ProductionOrder> pageResult =
        productionOrderService.query(getPageIndex(vo), getPageSize(vo), vo);
    List<ProductionOrder> datas = pageResult.getDatas();
    List<QueryProductionOrderBo> results = null;
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryProductionOrderBo::new).collect(Collectors.toList());
    }
    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  @Operation(summary = "工单详情")
  @Parameter(name = "id", description = "工单ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"produce:order:query"})
  @GetMapping
  public InvokeResult<GetProductionOrderBo> getById(
      @NotBlank(message = "工单ID不能为空！") String id) {
    ProductionOrder order = productionOrderService.getById(id);
    if (order == null) {
      return InvokeResultBuilder.fail("工单不存在！");
    }
    return InvokeResultBuilder.success(new GetProductionOrderBo(order));
  }

  @Operation(summary = "新增生产工单（自动展开BOM）")
  @HasPermission({"produce:order:add"})
  @PostMapping
  public InvokeResult<String> create(@RequestBody @Valid CreateProductionOrderVo vo) {
    String id = productionOrderService.create(vo);
    return InvokeResultBuilder.success(id);
  }

  @Operation(summary = "修改工单排产信息")
  @HasPermission({"produce:order:modify"})
  @PutMapping
  public InvokeResult<Void> update(@RequestBody @Valid UpdateProductionOrderVo vo) {
    productionOrderService.update(vo);
    return InvokeResultBuilder.success();
  }

  @Operation(summary = "开始生产")
  @Parameter(name = "id", description = "工单ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"produce:order:modify"})
  @PatchMapping("/start")
  public InvokeResult<Void> start(@NotBlank(message = "工单ID不能为空！") String id) {
    productionOrderService.start(id);
    return InvokeResultBuilder.success();
  }

  @Operation(summary = "完工")
  @HasPermission({"produce:order:complete"})
  @PatchMapping("/complete")
  public InvokeResult<Void> complete(@RequestBody @Valid CompleteProductionOrderVo vo) {
    productionOrderService.complete(vo);
    return InvokeResultBuilder.success();
  }

  @Operation(summary = "取消工单")
  @Parameter(name = "id", description = "工单ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"produce:order:modify"})
  @PatchMapping("/cancel")
  public InvokeResult<Void> cancel(@NotBlank(message = "工单ID不能为空！") String id) {
    productionOrderService.cancel(id);
    return InvokeResultBuilder.success();
  }

  @Operation(summary = "删除工单")
  @Parameter(name = "id", description = "工单ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"produce:order:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(@NotBlank(message = "工单ID不能为空！") String id) {
    productionOrderService.deleteById(id);
    return InvokeResultBuilder.success();
  }
}
