package com.lframework.xingyun.sc.controller;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.sc.bo.purchase.PurchaseOrderSelectorBo;
import com.lframework.xingyun.sc.bo.purchase.receive.ReceiveSheetSelectorBo;
import com.lframework.xingyun.sc.bo.stock.take.plan.TakeStockPlanSelectorBo;
import com.lframework.xingyun.sc.bo.stock.take.pre.PreTakeStockSheetSelectorBo;
import com.lframework.xingyun.sc.entity.PreTakeStockSheet;
import com.lframework.xingyun.sc.entity.PurchaseOrder;
import com.lframework.xingyun.sc.entity.ReceiveSheet;
import com.lframework.xingyun.sc.entity.TakeStockPlan;
import com.lframework.xingyun.sc.service.purchase.PurchaseOrderService;
import com.lframework.xingyun.sc.service.purchase.ReceiveSheetService;
import com.lframework.xingyun.sc.service.stock.take.PreTakeStockSheetService;
import com.lframework.xingyun.sc.service.stock.take.TakeStockPlanService;
import com.lframework.xingyun.sc.vo.purchase.PurchaseOrderSelectorVo;
import com.lframework.xingyun.sc.vo.purchase.receive.ReceiveSheetSelectorVo;
import com.lframework.xingyun.sc.vo.stock.take.plan.TakeStockPlanSelectorVo;
import com.lframework.xingyun.sc.vo.stock.take.pre.PreTakeStockSheetSelectorVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据选择器
 *
 * @author zmj
 */
@Api(tags = "数据选择器")
@Validated
@RestController
@RequestMapping("/selector")
public class ScSelectorController extends DefaultBaseController {

  @Autowired
  private PurchaseOrderService purchaseOrderService;

  @Autowired
  private ReceiveSheetService receiveSheetService;

  @Autowired
  private TakeStockPlanService takeStockPlanService;

  @Autowired
  private PreTakeStockSheetService preTakeStockSheetService;

  /**
   * 采购订单
   */
  @ApiOperation("采购订单")
  @GetMapping("/purchaseorder")
  public InvokeResult<PageResult<PurchaseOrderSelectorBo>> selector(
      @Valid PurchaseOrderSelectorVo vo) {

    PageResult<PurchaseOrder> pageResult = purchaseOrderService.selector(getPageIndex(vo),
        getPageSize(vo), vo);

    List<PurchaseOrder> datas = pageResult.getDatas();
    List<PurchaseOrderSelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(PurchaseOrderSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 加载采购订单
   */
  @ApiOperation("加载采购订单")
  @PostMapping("/purchaseorder/load")
  public InvokeResult<List<PurchaseOrderSelectorBo>> loadPurchaseOrder(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<PurchaseOrder> datas = purchaseOrderService.listByIds(ids);
    List<PurchaseOrderSelectorBo> results = datas.stream().map(PurchaseOrderSelectorBo::new)
        .collect(
            Collectors.toList());
    return InvokeResultBuilder.success(results);
  }

  /**
   * 采购收货单
   */
  @ApiOperation("采购收货单")
  @GetMapping("/receivesheet")
  public InvokeResult<PageResult<ReceiveSheetSelectorBo>> selector(
      @Valid ReceiveSheetSelectorVo vo) {

    PageResult<ReceiveSheet> pageResult = receiveSheetService.selector(getPageIndex(vo),
        getPageSize(vo), vo);

    List<ReceiveSheet> datas = pageResult.getDatas();
    List<ReceiveSheetSelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(ReceiveSheetSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 加载采购收货单
   */
  @ApiOperation("加载采购收货单")
  @PostMapping("/receivesheet/load")
  public InvokeResult<List<ReceiveSheetSelectorBo>> loadReceiveSheet(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<ReceiveSheet> datas = receiveSheetService.listByIds(ids);
    List<ReceiveSheetSelectorBo> results = datas.stream().map(ReceiveSheetSelectorBo::new).collect(
        Collectors.toList());
    return InvokeResultBuilder.success(results);
  }

  /**
   * 盘点任务
   */
  @ApiOperation("盘点任务")
  @GetMapping("/takestock/plan")
  public InvokeResult<PageResult<TakeStockPlanSelectorBo>> selector(
      @Valid TakeStockPlanSelectorVo vo) {

    PageResult<TakeStockPlan> pageResult = takeStockPlanService.selector(
        getPageIndex(vo), getPageSize(vo), vo);

    List<TakeStockPlan> datas = pageResult.getDatas();
    List<TakeStockPlanSelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(TakeStockPlanSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 加载盘点任务
   */
  @ApiOperation("加载盘点任务")
  @PostMapping("/takestock/plan/load")
  public InvokeResult<List<TakeStockPlanSelectorBo>> loadTakeStockPlan(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<TakeStockPlan> datas = takeStockPlanService.listByIds(ids);
    List<TakeStockPlanSelectorBo> results = datas.stream().map(TakeStockPlanSelectorBo::new)
        .collect(
            Collectors.toList());
    return InvokeResultBuilder.success(results);
  }

  /**
   * 预先盘点单
   */
  @ApiOperation("预先盘点单")
  @GetMapping("/takestock/pre")
  public InvokeResult<PageResult<PreTakeStockSheetSelectorBo>> selector(
      @Valid PreTakeStockSheetSelectorVo vo) {

    PageResult<PreTakeStockSheet> pageResult = preTakeStockSheetService.selector(
        getPageIndex(vo), getPageSize(vo), vo);

    List<PreTakeStockSheet> datas = pageResult.getDatas();
    List<PreTakeStockSheetSelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(PreTakeStockSheetSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 加载预先盘点单
   */
  @ApiOperation("加载预先盘点单")
  @PostMapping("/takestock/pre/load")
  public InvokeResult<List<PreTakeStockSheetSelectorBo>> loadPreTakeStock(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<PreTakeStockSheet> datas = preTakeStockSheetService.listByIds(ids);
    List<PreTakeStockSheetSelectorBo> results = datas.stream().map(PreTakeStockSheetSelectorBo::new)
        .collect(
            Collectors.toList());
    return InvokeResultBuilder.success(results);
  }
}
