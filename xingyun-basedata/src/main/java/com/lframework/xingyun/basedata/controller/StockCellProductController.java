package com.lframework.xingyun.basedata.controller;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.utils.ExcelUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.xingyun.basedata.bo.stockcell.product.QueryStockCellProductBo;
import com.lframework.xingyun.basedata.dto.stockcell.product.StockCellProductDto;
import com.lframework.xingyun.basedata.excel.stockcell.product.StockCellProductImportByStockCellListener;
import com.lframework.xingyun.basedata.excel.stockcell.product.StockCellProductImportByStockCellModel;
import com.lframework.xingyun.basedata.excel.stockcell.product.StockCellProductImportListener;
import com.lframework.xingyun.basedata.excel.stockcell.product.StockCellProductImportModel;
import com.lframework.xingyun.basedata.service.stockcell.StockCellProductService;
import com.lframework.xingyun.basedata.vo.stockcell.product.CreateStockCellProductVo;
import com.lframework.xingyun.basedata.vo.stockcell.product.QueryStockCellProductVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 仓位商品管理
 *
 * @author zmj
 */
@Api(tags = "仓位商品管理")
@Validated
@RestController
@RequestMapping("/basedata/stock-cell/product")
public class StockCellProductController extends DefaultBaseController {

  @Autowired
  private StockCellProductService stockCellProductService;

  /**
   * 查询列表
   */
  @ApiOperation("查询列表")
  @HasPermission({"base-data:stock-cell-product:query",
      "base-data:stock-cell-product:add",
      "base-data:stock-cell-product:modify"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryStockCellProductBo>> query(
      @Valid QueryStockCellProductVo vo) {

    PageResult<StockCellProductDto> pageResult = stockCellProductService.query(getPageIndex(vo),
        getPageSize(vo),
        vo);

    List<StockCellProductDto> datas = pageResult.getDatas();
    List<QueryStockCellProductBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryStockCellProductBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 新增
   */
  @ApiOperation("新增")
  @PostMapping
  @HasPermission({"base-data:stock-cell-product:add"})
  public InvokeResult<Void> create(@RequestBody CreateStockCellProductVo vo) {
    stockCellProductService.create(vo);
    return InvokeResultBuilder.success();
  }

  /**
   * 根据ID删除
   */
  @ApiOperation("根据ID删除")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @DeleteMapping
  @HasPermission({"base-data:stock-cell-product:delete"})
  public InvokeResult<Void> deleteById(@NotEmpty(message = "仓库ID不能为空！") String id) {
    stockCellProductService.removeById(id);
    return InvokeResultBuilder.success();
  }

  /**
   * 下载导入模板
   */
  @ApiOperation("下载导入模板")
  @HasPermission({"base-data:stock-cell-product:import"})
  @GetMapping("/import/template")
  public void downloadImportTemplate() {
    ExcelUtil.exportXls("仓位商品导入模板", StockCellProductImportModel.class);
  }

  /**
   * 导入
   */
  @ApiOperation("导入")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true),
      @ApiImplicitParam(value = "文件", name = "file", paramType = "query", required = true)
  })
  @HasPermission({"base-data:stock-cell-product:import"})
  @PostMapping("/import")
  public InvokeResult<Void> importExcel(
      @NotBlank(message = "ID不能为空") String id,
      @NotNull(message = "请上传文件") MultipartFile file) {

    StockCellProductImportListener listener = new StockCellProductImportListener();
    listener.setTaskId(id);
    ExcelUtil.read(file, StockCellProductImportModel.class, listener).sheet().doRead();

    return InvokeResultBuilder.success();
  }

  /**
   * 下载指定仓位导入模板
   */
  @ApiOperation("下载指定仓位导入模板")
  @HasPermission({"base-data:stock-cell-product:import"})
  @GetMapping("/import/by-cell/template")
  public void downloadImportByCellTemplate() {
    ExcelUtil.exportXls("指定仓位导入仓位商品模板", StockCellProductImportByStockCellModel.class);
  }

  /**
   * 指定仓位导入
   */
  @ApiOperation("指定仓位导入")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true),
      @ApiImplicitParam(value = "仓位ID", name = "stockCellId", paramType = "query", required = true),
      @ApiImplicitParam(value = "文件", name = "file", paramType = "query", required = true)
  })
  @HasPermission({"base-data:stock-cell-product:import"})
  @PostMapping("/import/by-cell")
  public InvokeResult<Void> importExcelByCell(
      @NotBlank(message = "ID不能为空") String id,
      @NotBlank(message = "仓位ID不能为空") String stockCellId,
      @NotNull(message = "请上传文件") MultipartFile file) {

    StockCellProductImportByStockCellListener listener = new StockCellProductImportByStockCellListener(
        stockCellId);
    listener.setTaskId(id);
    ExcelUtil.read(file, StockCellProductImportByStockCellModel.class, listener).sheet().doRead();

    return InvokeResultBuilder.success();
  }
}
