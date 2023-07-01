package com.lframework.xingyun.sc.controller.stock.adjust;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.starter.web.annotations.security.HasPermission;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.sc.bo.stock.adjust.stock.QueryStockAdjustSheetBo;
import com.lframework.xingyun.sc.bo.stock.adjust.stock.StockAdjustProductBo;
import com.lframework.xingyun.sc.bo.stock.adjust.stock.StockAdjustSheetFullBo;
import com.lframework.xingyun.sc.dto.stock.adjust.stock.StockAdjustProductDto;
import com.lframework.xingyun.sc.dto.stock.adjust.stock.StockAdjustSheetFullDto;
import com.lframework.xingyun.sc.entity.StockAdjustSheet;
import com.lframework.xingyun.sc.excel.stock.adjust.StockAdjustSheetExportModel;
import com.lframework.xingyun.sc.service.stock.adjust.StockAdjustSheetService;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.ApprovePassStockAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.ApproveRefuseStockAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.BatchApprovePassStockAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.BatchApproveRefuseStockAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.CreateStockAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.QueryStockAdjustProductVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.QueryStockAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.UpdateStockAdjustSheetVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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
 * 库存调整单 Controller
 *
 * @author zmj
 */
@Api(tags = "库存调整单")
@Validated
@RestController
@RequestMapping("/stock/adjust")
public class StockAdjustSheetController extends DefaultBaseController {

  @Autowired
  private StockAdjustSheetService stockAdjustSheetService;

  /**
   * 查询列表
   */
  @ApiOperation("查询列表")
  @HasPermission({"stock:adjust:query"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryStockAdjustSheetBo>> query(
      @Valid QueryStockAdjustSheetVo vo) {

    PageResult<StockAdjustSheet> pageResult = stockAdjustSheetService.query(
        getPageIndex(vo),
        getPageSize(vo), vo);

    List<StockAdjustSheet> datas = pageResult.getDatas();
    List<QueryStockAdjustSheetBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryStockAdjustSheetBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 根据ID查询
   */
  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"stock:adjust:query"})
  @GetMapping("/detail")
  public InvokeResult<StockAdjustSheetFullBo> getDetail(
      @NotBlank(message = "id不能为空！") String id) {

    StockAdjustSheetFullDto data = stockAdjustSheetService.getDetail(id);
    if (data == null) {
      throw new DefaultClientException("库存调整单不存在！");
    }

    StockAdjustSheetFullBo result = new StockAdjustSheetFullBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 导出
   */
  @ApiOperation("导出")
  @HasPermission({"stock:adjust:export"})
  @PostMapping("/export")
  public void export(@Valid QueryStockAdjustSheetVo vo) {

    ExcelMultipartWriterSheetBuilder builder = ExcelUtil.multipartExportXls("库存调整单信息",
        StockAdjustSheetExportModel.class);

    try {
      int pageIndex = 1;
      while (true) {
        PageResult<StockAdjustSheet> pageResult = stockAdjustSheetService.query(pageIndex,
            getExportSize(), vo);
        List<StockAdjustSheet> datas = pageResult.getDatas();
        List<StockAdjustSheetExportModel> models = datas.stream()
            .map(StockAdjustSheetExportModel::new)
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
   * 根据关键字查询商品列表
   */
  @ApiOperation("根据关键字查询商品列表")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "仓库ID", name = "scId", paramType = "query", required = true),
      @ApiImplicitParam(value = "关键字", name = "condition", paramType = "query", required = true)})
  @HasPermission({"stock:adjust:add", "stock:adjust:modify"})
  @GetMapping("/product/search")
  public InvokeResult<List<StockAdjustProductBo>> searchProducts(
      @NotBlank(message = "仓库ID不能为空！") String scId,
      String condition) {

    if (StringUtil.isBlank(condition)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }
    PageResult<StockAdjustProductDto> pageResult = stockAdjustSheetService.queryStockAdjustByCondition(
        getPageIndex(), getPageSize(), scId, condition);
    List<StockAdjustProductBo> results = CollectionUtil.emptyList();
    List<StockAdjustProductDto> datas = pageResult.getDatas();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(t -> new StockAdjustProductBo(scId, t))
          .collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  /**
   * 查询商品列表
   */
  @ApiOperation("查询商品列表")
  @HasPermission({"stock:adjust:add", "stock:adjust:modify"})
  @GetMapping("/product/list")
  public InvokeResult<PageResult<StockAdjustProductBo>> queryProductList(
      @Valid QueryStockAdjustProductVo vo) {

    PageResult<StockAdjustProductDto> pageResult = stockAdjustSheetService.queryStockAdjustList(
        getPageIndex(),
        getPageSize(), vo);
    List<StockAdjustProductBo> results = null;
    List<StockAdjustProductDto> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(t -> new StockAdjustProductBo(vo.getScId(), t))
          .collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 新增
   */
  @ApiOperation("新增")
  @HasPermission({"stock:adjust:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid @RequestBody CreateStockAdjustSheetVo vo) {

    vo.validate();

    stockAdjustSheetService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改
   */
  @ApiOperation("修改")
  @HasPermission({"stock:adjust:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid @RequestBody UpdateStockAdjustSheetVo vo) {

    vo.validate();

    stockAdjustSheetService.update(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 根据ID删除
   */
  @ApiOperation("根据ID删除")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"stock:adjust:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(@NotBlank(message = "id不能为空！") String id) {

    stockAdjustSheetService.deleteById(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 批量删除
   */
  @ApiOperation("批量删除")
  @HasPermission({"stock:adjust:delete"})
  @DeleteMapping("/batch")
  public InvokeResult<Void> deleteByIds(
      @ApiParam(value = "ID", required = true) @RequestBody @NotEmpty(message = "请选择需要删除的库存调整单！") List<String> ids) {

    stockAdjustSheetService.deleteByIds(ids);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核通过
   */
  @ApiOperation("审核通过")
  @HasPermission({"stock:adjust:approve"})
  @PatchMapping("/approve/pass")
  public InvokeResult<Void> approvePass(@RequestBody @Valid ApprovePassStockAdjustSheetVo vo) {

    stockAdjustSheetService.approvePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 批量审核通过
   */
  @ApiOperation("批量审核通过")
  @HasPermission({"stock:adjust:approve"})
  @PatchMapping("/approve/pass/batch")
  public InvokeResult<Void> batchApprovePass(
      @RequestBody @Valid BatchApprovePassStockAdjustSheetVo vo) {

    stockAdjustSheetService.batchApprovePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 直接审核通过
   */
  @ApiOperation("直接审核通过")
  @HasPermission({"stock:adjust:approve"})
  @PostMapping("/approve/pass/direct")
  public InvokeResult<Void> directApprovePass(@RequestBody @Valid CreateStockAdjustSheetVo vo) {

    stockAdjustSheetService.directApprovePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核拒绝
   */
  @ApiOperation("审核拒绝")
  @HasPermission({"stock:adjust:approve"})
  @PatchMapping("/approve/refuse")
  public InvokeResult<Void> approveRefuse(
      @RequestBody @Valid ApproveRefuseStockAdjustSheetVo vo) {

    stockAdjustSheetService.approveRefuse(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 批量审核拒绝
   */
  @ApiOperation("批量审核拒绝")
  @HasPermission({"stock:adjust:approve"})
  @PatchMapping("/approve/refuse/batch")
  public InvokeResult<Void> batchApproveRefuse(
      @RequestBody @Valid BatchApproveRefuseStockAdjustSheetVo vo) {

    stockAdjustSheetService.batchApproveRefuse(vo);

    return InvokeResultBuilder.success();
  }
}
