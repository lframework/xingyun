package com.lframework.xingyun.sc.api.controller;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.sc.api.bo.purchase.PurchaseOrderSelectorBo;
import com.lframework.xingyun.sc.api.bo.purchase.receive.ReceiveSheetSelectorBo;
import com.lframework.xingyun.sc.api.bo.stock.take.plan.TakeStockPlanSelectorBo;
import com.lframework.xingyun.sc.api.bo.stock.take.pre.PreTakeStockSheetSelectorBo;
import com.lframework.xingyun.sc.biz.service.purchase.IPurchaseOrderService;
import com.lframework.xingyun.sc.biz.service.purchase.IReceiveSheetService;
import com.lframework.xingyun.sc.biz.service.stock.take.IPreTakeStockSheetService;
import com.lframework.xingyun.sc.biz.service.stock.take.ITakeStockPlanService;
import com.lframework.xingyun.sc.facade.dto.stock.take.plan.TakeStockPlanSelectorDto;
import com.lframework.xingyun.sc.facade.dto.stock.take.pre.PreTakeStockSheetSelectorDto;
import com.lframework.xingyun.sc.facade.entity.PurchaseOrder;
import com.lframework.xingyun.sc.facade.entity.ReceiveSheet;
import com.lframework.xingyun.sc.facade.vo.purchase.PurchaseOrderSelectorVo;
import com.lframework.xingyun.sc.facade.vo.purchase.receive.ReceiveSheetSelectorVo;
import com.lframework.xingyun.sc.facade.vo.stock.take.plan.TakeStockPlanSelectorVo;
import com.lframework.xingyun.sc.facade.vo.stock.take.pre.PreTakeStockSheetSelectorVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 仓库数据选择器
 *
 * @author zmj
 */
@Api(tags = "仓库数据选择器")
@Validated
@RestController
@RequestMapping("/selector")
public class ScSelectorController extends DefaultBaseController {

  @Autowired
  private IPurchaseOrderService purchaseOrderService;

  @Autowired
  private IReceiveSheetService receiveSheetService;

  @Autowired
  private ITakeStockPlanService takeStockPlanService;

  @Autowired
  private IPreTakeStockSheetService preTakeStockSheetService;

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
   * 盘点任务
   */
  @ApiOperation("盘点任务")
  @GetMapping("/takestock/plan")
  public InvokeResult<PageResult<TakeStockPlanSelectorBo>> selector(
      @Valid TakeStockPlanSelectorVo vo) {

    PageResult<TakeStockPlanSelectorDto> pageResult = takeStockPlanService.selector(
        getPageIndex(vo), getPageSize(vo), vo);

    List<TakeStockPlanSelectorDto> datas = pageResult.getDatas();
    List<TakeStockPlanSelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(TakeStockPlanSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 预先盘点单
   */
  @ApiOperation("预先盘点单")
  @GetMapping("/takestock/pre")
  public InvokeResult<PageResult<PreTakeStockSheetSelectorBo>> selector(
      @Valid PreTakeStockSheetSelectorVo vo) {

    PageResult<PreTakeStockSheetSelectorDto> pageResult = preTakeStockSheetService.selector(
        getPageIndex(vo), getPageSize(vo), vo);

    List<PreTakeStockSheetSelectorDto> datas = pageResult.getDatas();
    List<PreTakeStockSheetSelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(PreTakeStockSheetSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }
}
