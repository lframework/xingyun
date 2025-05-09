package com.lframework.xingyun.sc.controller.sale;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.annotations.security.HasPermission;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.core.utils.ExportTaskUtil;
import com.lframework.xingyun.sc.bo.sale.GetSaleOrderBo;
import com.lframework.xingyun.sc.bo.sale.PrintSaleOrderBo;
import com.lframework.xingyun.sc.bo.sale.QuerySaleOrderBo;
import com.lframework.xingyun.sc.bo.sale.QuerySaleOrderWithOutBo;
import com.lframework.xingyun.sc.bo.sale.SaleOrderWithOutBo;
import com.lframework.xingyun.sc.bo.sale.SaleProductBo;
import com.lframework.xingyun.sc.dto.sale.SaleOrderFullDto;
import com.lframework.xingyun.sc.dto.sale.SaleOrderWithOutDto;
import com.lframework.xingyun.sc.dto.sale.SaleProductDto;
import com.lframework.xingyun.sc.entity.SaleOrder;
import com.lframework.xingyun.sc.excel.sale.SaleOrderExportTaskWorker;
import com.lframework.xingyun.sc.service.sale.SaleOrderService;
import com.lframework.xingyun.sc.vo.sale.ApprovePassSaleOrderVo;
import com.lframework.xingyun.sc.vo.sale.ApproveRefuseSaleOrderVo;
import com.lframework.xingyun.sc.vo.sale.CreateSaleOrderVo;
import com.lframework.xingyun.sc.vo.sale.QuerySaleOrderVo;
import com.lframework.xingyun.sc.vo.sale.QuerySaleOrderWithOutVo;
import com.lframework.xingyun.sc.vo.sale.QuerySaleProductVo;
import com.lframework.xingyun.sc.vo.sale.UpdateSaleOrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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
 * 销售订单管理
 *
 * @author zmj
 */
@Api(tags = "销售订单管理")
@Validated
@RestController
@RequestMapping("/sale/order")
public class SaleOrderController extends DefaultBaseController {

  @Autowired
  private SaleOrderService saleOrderService;

  /**
   * 打印
   */
  @ApiOperation("打印")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"sale:order:query"})
  @GetMapping("/print")
  public InvokeResult<PrintSaleOrderBo> print(
      @NotBlank(message = "订单ID不能为空！") String id) {

    SaleOrderFullDto data = saleOrderService.getDetail(id);
    if (data == null) {
      throw new DefaultClientException("订单不存在！");
    }

    PrintSaleOrderBo result = new PrintSaleOrderBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 订单列表
   */
  @ApiOperation("订单列表")
  @HasPermission({"sale:order:query"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QuerySaleOrderBo>> query(@Valid QuerySaleOrderVo vo) {

    PageResult<SaleOrder> pageResult = saleOrderService.query(getPageIndex(vo), getPageSize(vo),
        vo);

    List<SaleOrder> datas = pageResult.getDatas();
    List<QuerySaleOrderBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySaleOrderBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 导出
   */
  @ApiOperation("导出")
  @HasPermission({"sale:order:export"})
  @PostMapping("/export")
  public InvokeResult<Void> export(@Valid QuerySaleOrderVo vo) {

    ExportTaskUtil.exportTask("销售单信息", SaleOrderExportTaskWorker.class, vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 根据ID查询
   */
  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"sale:order:query"})
  @GetMapping
  public InvokeResult<GetSaleOrderBo> findById(@NotBlank(message = "订单ID不能为空！") String id) {

    SaleOrderFullDto data = saleOrderService.getDetail(id);

    GetSaleOrderBo result = new GetSaleOrderBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 根据ID查询（出库业务）
   */
  @ApiOperation("根据ID查询（出库业务）")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"sale:out:add", "sale:out:modify"})
  @GetMapping("/out")
  public InvokeResult<SaleOrderWithOutBo> getWithOut(
      @NotBlank(message = "订单ID不能为空！") String id) {

    SaleOrderWithOutDto data = saleOrderService.getWithOut(id);
    SaleOrderWithOutBo result = new SaleOrderWithOutBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 查询列表（出库业务）
   */
  @ApiOperation("查询列表（出库业务）")
  @HasPermission({"sale:out:add", "sale:out:modify"})
  @GetMapping("/query/out")
  public InvokeResult<PageResult<QuerySaleOrderWithOutBo>> queryWithOut(
      @Valid QuerySaleOrderWithOutVo vo) {

    PageResult<SaleOrder> pageResult = saleOrderService.queryWithOut(getPageIndex(vo),
        getPageSize(vo), vo);
    List<SaleOrder> datas = pageResult.getDatas();

    List<QuerySaleOrderWithOutBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySaleOrderWithOutBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 加载列表（出库业务）
   */
  @ApiOperation("加载列表（出库业务）")
  @HasPermission({"sale:out:add", "sale:out:modify"})
  @PostMapping("/query/out/load")
  public InvokeResult<List<QuerySaleOrderWithOutBo>> getWithOut(
      @RequestBody(required = false) List<String> ids) {

    List<SaleOrder> datas = saleOrderService.listByIds(ids);

    List<QuerySaleOrderWithOutBo> results = datas.stream()
        .map(QuerySaleOrderWithOutBo::new)
        .collect(Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  /**
   * 创建订单
   */
  @ApiOperation("创建订单")
  @HasPermission({"sale:order:add"})
  @PostMapping
  public InvokeResult<String> create(@RequestBody @Valid CreateSaleOrderVo vo) {

    vo.validate();

    String id = saleOrderService.create(vo);

    return InvokeResultBuilder.success(id);
  }

  /**
   * 修改订单
   */
  @ApiOperation("修改订单")
  @HasPermission({"sale:order:modify"})
  @PutMapping
  public InvokeResult<Void> update(@RequestBody @Valid UpdateSaleOrderVo vo) {

    vo.validate();

    saleOrderService.update(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核通过订单
   */
  @ApiOperation("审核通过订单")
  @HasPermission({"sale:order:approve"})
  @PatchMapping("/approve/pass")
  public InvokeResult<Void> approvePass(@RequestBody @Valid ApprovePassSaleOrderVo vo) {

    saleOrderService.approvePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 直接审核通过订单
   */
  @ApiOperation("直接审核通过订单")
  @HasPermission({"sale:order:approve"})
  @PostMapping("/approve/pass/direct")
  public InvokeResult<Void> directApprovePass(@RequestBody @Valid CreateSaleOrderVo vo) {

    saleOrderService.directApprovePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核拒绝订单
   */
  @ApiOperation("审核拒绝订单")
  @HasPermission({"sale:order:approve"})
  @PatchMapping("/approve/refuse")
  public InvokeResult<Void> approveRefuse(@RequestBody @Valid ApproveRefuseSaleOrderVo vo) {

    saleOrderService.approveRefuse(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 删除订单
   */
  @ApiOperation("删除订单")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"sale:order:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(@NotBlank(message = "订单ID不能为空！") String id) {

    saleOrderService.deleteById(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 根据关键字查询商品
   */
  @ApiOperation("根据关键字查询可销售商品")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "仓库ID", name = "scId", paramType = "query", required = true),
      @ApiImplicitParam(value = "关键字", name = "condition", paramType = "query", required = true)})
  @HasPermission({"sale:order:add", "sale:order:modify", "sale:out:add", "sale:out:modify",
      "sale:return:add", "sale:return:modify"})
  @GetMapping("/product/search")
  public InvokeResult<List<SaleProductBo>> searchSaleProducts(
      @NotBlank(message = "仓库ID不能为空！") String scId, String condition, Boolean isReturn) {

    if (isReturn == null) {
      isReturn = false;
    }
    if (StringUtil.isBlank(condition)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    PageResult<SaleProductDto> pageResult = saleOrderService.querySaleByCondition(getPageIndex(),
        getPageSize(), condition, isReturn);
    List<SaleProductBo> results = CollectionUtil.emptyList();
    List<SaleProductDto> datas = pageResult.getDatas();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(t -> new SaleProductBo(scId, t)).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  /**
   * 查询商品列表
   */
  @ApiOperation("查询可销售商品列表")
  @HasPermission({"sale:order:add", "sale:order:modify", "sale:out:add", "sale:out:modify",
      "sale:return:add", "sale:return:modify"})
  @GetMapping("/product/list")
  public InvokeResult<PageResult<SaleProductBo>> querySaleProductList(
      @Valid QuerySaleProductVo vo) {

    PageResult<SaleProductDto> pageResult = saleOrderService.querySaleList(getPageIndex(vo),
        getPageSize(vo), vo);
    List<SaleProductBo> results = null;
    List<SaleProductDto> datas = pageResult.getDatas();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(t -> new SaleProductBo(vo.getScId(), t))
          .collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }
}
