package com.lframework.xingyun.basedata.controller;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.utils.ExcelUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.xingyun.basedata.bo.stockcell.GetStockCellBo;
import com.lframework.xingyun.basedata.bo.stockcell.QueryStockCellBo;
import com.lframework.xingyun.basedata.dto.stockcell.StockCellDto;
import com.lframework.xingyun.basedata.excel.stockcell.StockCellImportListener;
import com.lframework.xingyun.basedata.excel.stockcell.StockCellImportModel;
import com.lframework.xingyun.basedata.service.stockcell.StockCellService;
import com.lframework.xingyun.basedata.vo.stockcell.CreateStockCellVo;
import com.lframework.xingyun.basedata.vo.stockcell.QueryStockCellVo;
import com.lframework.xingyun.basedata.vo.stockcell.UpdateStockCellVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 仓位管理
 *
 * @author zmj
 */
@Tag(name = "仓位管理")
@Validated
@RestController
@RequestMapping("/basedata/stock-cell")
public class StockCellController extends DefaultBaseController {

  @Autowired
  private StockCellService stockCellService;

  /**
   * 仓位列表
   */
  @Operation(summary = "仓位列表")
  @HasPermission({"base-data:stock-cell:query", "base-data:stock-cell:add",
      "base-data:stock-cell:modify", "base-data:stock-cell-product:query",
      "base-data:stock-cell-product:add",
      "base-data:stock-cell-product:modify"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryStockCellBo>> query(@Valid QueryStockCellVo vo) {

    PageResult<StockCellDto> pageResult = stockCellService.query(getPageIndex(vo), getPageSize(vo),
        vo);

    List<StockCellDto> datas = pageResult.getDatas();
    List<QueryStockCellBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryStockCellBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询仓位
   */
  @Operation(summary = "查询仓位")
  @Parameter(name = "id", description = "ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"base-data:stock-cell:query", "base-data:stock-cell:add",
      "base-data:stock-cell:modify"})
  @GetMapping
  public InvokeResult<GetStockCellBo> get(@NotBlank(message = "ID不能为空！") String id) {

    StockCellDto data = stockCellService.findById(id);
    if (data == null) {
      throw new DefaultClientException("仓位不存在！");
    }

    GetStockCellBo result = new GetStockCellBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 删除仓位
   */
  @Operation(summary = "删除仓位")
  @HasPermission({"base-data:stock-cell:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(
      @Parameter(description = "ID", required = true) @NotEmpty(message = "仓位ID不能为空！") String id) {

    stockCellService.deleteById(id);

    stockCellService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 新增仓位
   */
  @Operation(summary = "新增仓位")
  @HasPermission({"base-data:stock-cell:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateStockCellVo vo) {

    stockCellService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改仓位
   */
  @Operation(summary = "修改仓位")
  @HasPermission({"base-data:stock-cell:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateStockCellVo vo) {

    stockCellService.update(vo);

    stockCellService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  @Operation(summary = "下载导入模板")
  @HasPermission({"base-data:stock-cell:import"})
  @GetMapping("/import/template")
  public void downloadImportTemplate() {
    ExcelUtil.exportXls("仓位导入模板", StockCellImportModel.class);
  }

  @Operation(summary = "导入")
  @HasPermission({"base-data:stock-cell:import"})
  @PostMapping("/import")
  public InvokeResult<Void> importExcel(@NotBlank(message = "ID不能为空") String id,
      @NotNull(message = "请上传文件") MultipartFile file) {

    StockCellImportListener listener = new StockCellImportListener();
    listener.setTaskId(id);
    ExcelUtil.read(file, StockCellImportModel.class, listener).sheet().doRead();

    return InvokeResultBuilder.success();
  }
}
