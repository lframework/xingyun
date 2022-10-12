package com.lframework.xingyun.api.controller.basedata.product;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.api.bo.basedata.product.info.GetProductBo;
import com.lframework.xingyun.api.bo.basedata.product.info.QueryProductBo;
import com.lframework.xingyun.api.bo.basedata.product.info.purchase.PurchaseProductBo;
import com.lframework.xingyun.api.bo.basedata.product.info.retail.RetailProductBo;
import com.lframework.xingyun.api.bo.basedata.product.info.sale.SaleProductBo;
import com.lframework.xingyun.api.bo.basedata.product.info.stock.adjust.StockCostAdjustProductBo;
import com.lframework.xingyun.api.bo.basedata.product.info.stock.take.pre.PreTakeStockProductBo;
import com.lframework.xingyun.api.bo.basedata.product.info.stock.take.sheet.TakeStockSheetProductBo;
import com.lframework.xingyun.api.excel.basedata.product.ProductImportListener;
import com.lframework.xingyun.api.excel.basedata.product.ProductImportModel;
import com.lframework.xingyun.basedata.dto.product.info.GetProductDto;
import com.lframework.xingyun.basedata.dto.product.info.PreTakeStockProductDto;
import com.lframework.xingyun.basedata.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.dto.product.info.PurchaseProductDto;
import com.lframework.xingyun.basedata.dto.product.info.RetailProductDto;
import com.lframework.xingyun.basedata.dto.product.info.SaleProductDto;
import com.lframework.xingyun.basedata.dto.product.info.StockCostAdjustProductDto;
import com.lframework.xingyun.basedata.dto.product.info.TakeStockSheetProductDto;
import com.lframework.xingyun.basedata.service.product.IProductSalePropItemRelationService;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.vo.product.info.QueryPreTakeStockProductVo;
import com.lframework.xingyun.basedata.vo.product.info.QueryProductVo;
import com.lframework.xingyun.basedata.vo.product.info.QueryPurchaseProductVo;
import com.lframework.xingyun.basedata.vo.product.info.QueryRetailProductVo;
import com.lframework.xingyun.basedata.vo.product.info.QuerySaleProductVo;
import com.lframework.xingyun.basedata.vo.product.info.QueryStockCostAdjustProductVo;
import com.lframework.xingyun.basedata.vo.product.info.QueryTakeStockSheetProductVo;
import com.lframework.xingyun.basedata.vo.product.info.UpdateProductVo;
import com.lframework.xingyun.sc.entity.TakeStockPlan;
import com.lframework.xingyun.sc.enums.TakeStockPlanType;
import com.lframework.xingyun.sc.service.stock.take.ITakeStockPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 商品管理
 *
 * @author zmj
 */
@Api(tags = "商品管理")
@Validated
@RestController
@RequestMapping("/basedata/product")
public class ProductController extends DefaultBaseController {

  @Autowired
  private IProductService productService;

  @Autowired
  private IProductSalePropItemRelationService productSalePropItemRelationService;

  @Autowired
  private ITakeStockPlanService takeStockPlanService;

  /**
   * 商品列表
   */
  @ApiOperation("商品列表")
  @PreAuthorize("@permission.valid('base-data:product:info:query','base-data:product:info:add','base-data:product:info:modify')")
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryProductBo>> query(@Valid QueryProductVo vo) {

    PageResult<ProductDto> pageResult = productService.query(getPageIndex(vo), getPageSize(vo), vo);

    List<ProductDto> datas = pageResult.getDatas();
    List<QueryProductBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {

      results = datas.stream().map(QueryProductBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 商品详情
   */
  @ApiOperation("商品详情")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @PreAuthorize("@permission.valid('base-data:product:info:query','base-data:product:info:add','base-data:product:info:modify')")
  @GetMapping
  public InvokeResult<GetProductBo> get(@NotBlank(message = "ID不能为空！") String id) {

    GetProductDto data = productService.getDetailById(id);

    GetProductBo result = new GetProductBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 修改商品
   */
  @ApiOperation("修改商品")
  @PreAuthorize("@permission.valid('base-data:product:info:modify')")
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateProductVo vo) {

    productService.update(vo);

    productService.cleanCacheByKey(vo.getId());

    productSalePropItemRelationService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  @ApiOperation("下载导入模板")
  @PreAuthorize("@permission.valid('base-data:product:info:import')")
  @GetMapping("/import/template")
  public void downloadImportTemplate() {
    ExcelUtil.exportXls("商品导入模板", ProductImportModel.class);
  }

  @ApiOperation("导入")
  @PreAuthorize("@permission.valid('base-data:product:info:import')")
  @PostMapping("/import")
  public InvokeResult<Void> importExcel(@NotBlank(message = "ID不能为空") String id,
      @NotNull(message = "请上传文件") MultipartFile file) {

    ProductImportListener listener = new ProductImportListener();
    listener.setTaskId(id);
    ExcelUtil.read(file, ProductImportModel.class, listener).sheet().doRead();

    return InvokeResultBuilder.success();
  }

  /**
   * 根据关键字查询商品
   */
  @ApiOperation("根据关键字查询可采购商品")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "仓库ID", name = "scId", paramType = "query", required = true),
      @ApiImplicitParam(value = "关键字", name = "condition", paramType = "query", required = true)})
  @PreAuthorize("@permission.valid('purchase:order:add', 'purchase:order:modify', 'purchase:receive:add', 'purchase:receive:modify', 'purchase:return:add', 'purchase:return:modify')")
  @GetMapping("/purchase/product/search")
  public InvokeResult<List<PurchaseProductBo>> searchPurchaseProducts(
      @NotBlank(message = "仓库ID不能为空！") String scId, String condition) {

    if (StringUtil.isBlank(condition)) {
      return InvokeResultBuilder.success(Collections.EMPTY_LIST);
    }

    PageResult<PurchaseProductDto> pageResult = productService.queryPurchaseByCondition(
        getPageIndex(), getPageSize(), condition);
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
  @ApiOperation("查询可采购商品列表")
  @PreAuthorize("@permission.valid('purchase:order:add', 'purchase:order:modify', 'purchase:receive:add', 'purchase:receive:modify', 'purchase:return:add', 'purchase:return:modify')")
  @GetMapping("/purchase/product/list")
  public InvokeResult<PageResult<PurchaseProductBo>> queryPurchaseProductList(
      @Valid QueryPurchaseProductVo vo) {

    PageResult<PurchaseProductDto> pageResult = productService.queryPurchaseList(getPageIndex(),
        getPageSize(), vo);
    List<PurchaseProductBo> results = null;
    List<PurchaseProductDto> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(t -> new PurchaseProductBo(vo.getScId(), t))
          .collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 根据关键字查询商品
   */
  @ApiOperation("根据关键字查询可零售商品")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "仓库ID", name = "scId", paramType = "query", required = true),
      @ApiImplicitParam(value = "关键字", name = "condition", paramType = "query", required = true)})
  @PreAuthorize("@permission.valid('retail:out:add', 'retail:out:modify', 'retail:return:add', 'retail:return:modify')")
  @GetMapping("/retail/product/search")
  public InvokeResult<List<RetailProductBo>> searchRetailProducts(
      @NotBlank(message = "仓库ID不能为空！") String scId, String condition) {

    if (StringUtil.isBlank(condition)) {
      return InvokeResultBuilder.success(Collections.EMPTY_LIST);
    }

    PageResult<RetailProductDto> pageResult = productService.queryRetailByCondition(getPageIndex(),
        getPageSize(), condition);
    List<RetailProductBo> results = Collections.EMPTY_LIST;
    List<RetailProductDto> datas = pageResult.getDatas();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(t -> new RetailProductBo(scId, t)).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  /**
   * 查询商品列表
   */
  @ApiOperation("查询可零售商品列表")
  @PreAuthorize("@permission.valid('retail:out:add', 'retail:out:modify', 'retail:return:add', 'retail:return:modify')")
  @GetMapping("/retail/product/list")
  public InvokeResult<PageResult<RetailProductBo>> queryRetailProductList(
      @Valid QueryRetailProductVo vo) {

    PageResult<RetailProductDto> pageResult = productService.queryRetailList(getPageIndex(),
        getPageSize(), vo);
    List<RetailProductBo> results = null;
    List<RetailProductDto> datas = pageResult.getDatas();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(t -> new RetailProductBo(vo.getScId(), t))
          .collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 根据关键字查询商品
   */
  @ApiOperation("根据关键字查询可销售商品")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "仓库ID", name = "scId", paramType = "query", required = true),
      @ApiImplicitParam(value = "关键字", name = "condition", paramType = "query", required = true)})
  @PreAuthorize("@permission.valid('sale:order:add', 'sale:order:modify', 'sale:out:add', 'sale:out:modify', 'sale:return:add', 'sale:return:modify')")
  @GetMapping("/sale/product/search")
  public InvokeResult<List<SaleProductBo>> searchSaleProducts(
      @NotBlank(message = "仓库ID不能为空！") String scId, String condition) {

    if (StringUtil.isBlank(condition)) {
      return InvokeResultBuilder.success(Collections.EMPTY_LIST);
    }

    PageResult<SaleProductDto> pageResult = productService.querySaleByCondition(getPageIndex(),
        getPageSize(), condition);
    List<SaleProductBo> results = Collections.EMPTY_LIST;
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
  @PreAuthorize("@permission.valid('sale:order:add', 'sale:order:modify', 'sale:out:add', 'sale:out:modify', 'sale:return:add', 'sale:return:modify')")
  @GetMapping("/sale/product/list")
  public InvokeResult<PageResult<SaleProductBo>> querySaleProductList(
      @Valid QuerySaleProductVo vo) {

    PageResult<SaleProductDto> pageResult = productService.querySaleList(getPageIndex(),
        getPageSize(), vo);
    List<SaleProductBo> results = null;
    List<SaleProductDto> datas = pageResult.getDatas();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(t -> new SaleProductBo(vo.getScId(), t))
          .collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 根据关键字查询商品列表
   */
  @ApiOperation("根据关键字查询可库存成本调整商品列表")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "仓库ID", name = "scId", paramType = "query", required = true),
      @ApiImplicitParam(value = "关键字", name = "condition", paramType = "query", required = true)})
  @PreAuthorize("@permission.valid('stock:adjust:cost:add', 'stock:adjust:cost:modify')")
  @GetMapping("/stock/adjust/cost/product/search")
  public InvokeResult<List<StockCostAdjustProductBo>> searchStockAdjustCostProducts(
      @NotBlank(message = "仓库ID不能为空！") String scId, String condition) {

    if (StringUtil.isBlank(condition)) {
      return InvokeResultBuilder.success(Collections.EMPTY_LIST);
    }
    PageResult<StockCostAdjustProductDto> pageResult = productService.queryStockCostAdjustByCondition(
        getPageIndex(), getPageSize(), scId, condition);
    List<StockCostAdjustProductBo> results = Collections.EMPTY_LIST;
    List<StockCostAdjustProductDto> datas = pageResult.getDatas();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(StockCostAdjustProductBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  /**
   * 查询商品列表
   */
  @ApiOperation("查询可库存成本调整商品列表")
  @PreAuthorize("@permission.valid('stock:adjust:cost:add', 'stock:adjust:cost:modify')")
  @GetMapping("/stock/adjust/cost/product/list")
  public InvokeResult<PageResult<StockCostAdjustProductBo>> queryStockAdjustCostProductList(
      @Valid QueryStockCostAdjustProductVo vo) {

    PageResult<StockCostAdjustProductDto> pageResult = productService.queryStockCostAdjustList(
        getPageIndex(), getPageSize(), vo);
    List<StockCostAdjustProductBo> results = null;
    List<StockCostAdjustProductDto> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(StockCostAdjustProductBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 根据关键字查询商品列表
   */
  @ApiOperation("根据关键字查询预先盘点单商品列表")
  @ApiImplicitParam(value = "关键字", name = "condition", paramType = "query", required = true)
  @PreAuthorize("@permission.valid('stock:take:pre:add', 'stock:take:pre:modify')")
  @GetMapping("/stock/take/pre/product/search")
  public InvokeResult<List<PreTakeStockProductBo>> searchProducts(String condition) {

    if (StringUtil.isBlank(condition)) {
      return InvokeResultBuilder.success(Collections.EMPTY_LIST);
    }
    PageResult<PreTakeStockProductDto> pageResult = productService.queryPreTakeStockByCondition(
        getPageIndex(), getPageSize(), condition);
    List<PreTakeStockProductBo> results = Collections.EMPTY_LIST;
    List<PreTakeStockProductDto> datas = pageResult.getDatas();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(PreTakeStockProductBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  /**
   * 查询商品列表
   */
  @ApiOperation("查询预先盘点单商品列表")
  @PreAuthorize("@permission.valid('stock:take:pre:add', 'stock:take:pre:modify')")
  @GetMapping("/stock/take/pre/product/list")
  public InvokeResult<PageResult<PreTakeStockProductBo>> queryProductList(
      @Valid QueryPreTakeStockProductVo vo) {

    PageResult<PreTakeStockProductDto> pageResult = productService.queryPreTakeStockList(
        getPageIndex(), getPageSize(), vo);
    List<PreTakeStockProductBo> results = null;
    List<PreTakeStockProductDto> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(PreTakeStockProductBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 根据关键字查询商品列表
   */
  @ApiOperation("根据关键字查询盘点单商品列表")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "盘点任务ID", name = "planId", paramType = "query", required = true),
      @ApiImplicitParam(value = "关键字", name = "condition", paramType = "query", required = true)})
  @PreAuthorize("@permission.valid('stock:take:sheet:add', 'stock:take:sheet:modify')")
  @GetMapping("/stock/take/sheet/product/search")
  public InvokeResult<List<TakeStockSheetProductBo>> searchProducts(
      @NotBlank(message = "盘点任务ID不能为空！") String planId, String condition) {

    if (StringUtil.isBlank(condition)) {
      return InvokeResultBuilder.success(Collections.EMPTY_LIST);
    }

    TakeStockPlan takeStockPlan = takeStockPlanService.getById(planId);
    if (takeStockPlan.getTakeType() == TakeStockPlanType.SIMPLE) {
      planId = null;
    }
    PageResult<TakeStockSheetProductDto> pageResult = productService.queryTakeStockByCondition(
        getPageIndex(), getPageSize(), planId, condition);
    List<TakeStockSheetProductBo> results = Collections.EMPTY_LIST;
    List<TakeStockSheetProductDto> datas = pageResult.getDatas();
    if (!CollectionUtil.isEmpty(datas)) {
      String finalPlanId = planId;
      results = datas.stream()
          .map(t -> new TakeStockSheetProductBo(t, finalPlanId, takeStockPlan.getScId()))
          .collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  /**
   * 查询商品列表
   */
  @ApiOperation("查询盘点单商品列表")
  @PreAuthorize("@permission.valid('stock:take:sheet:add', 'stock:take:sheet:modify')")
  @GetMapping("/stock/take/sheet/product/list")
  public InvokeResult<PageResult<TakeStockSheetProductBo>> queryProductList(
      @Valid QueryTakeStockSheetProductVo vo) {

    TakeStockPlan takeStockPlan = takeStockPlanService.getById(vo.getPlanId());
    if (takeStockPlan.getTakeType() == TakeStockPlanType.SIMPLE) {
      vo.setPlanId(null);
    }

    PageResult<TakeStockSheetProductDto> pageResult = productService.queryTakeStockList(
        getPageIndex(), getPageSize(), vo);
    List<TakeStockSheetProductBo> results = null;
    List<TakeStockSheetProductDto> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream()
          .map(t -> new TakeStockSheetProductBo(t, vo.getPlanId(), takeStockPlan.getScId()))
          .collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }
}
