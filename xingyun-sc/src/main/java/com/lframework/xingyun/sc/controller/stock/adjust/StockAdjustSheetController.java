package com.lframework.xingyun.sc.controller.stock.adjust;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.ExcelUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.mq.core.utils.ExportTaskUtil;
import com.lframework.xingyun.sc.bo.stock.adjust.stock.QueryStockAdjustSheetBo;
import com.lframework.xingyun.sc.bo.stock.adjust.stock.StockAdjustProductBo;
import com.lframework.xingyun.sc.bo.stock.adjust.stock.StockAdjustSheetFullBo;
import com.lframework.xingyun.sc.dto.stock.adjust.stock.StockAdjustProductDto;
import com.lframework.xingyun.sc.dto.stock.adjust.stock.StockAdjustSheetFullDto;
import com.lframework.xingyun.sc.entity.StockAdjustSheet;
import com.lframework.xingyun.sc.excel.stock.adjust.StockAdjustSheetExportTaskWorker;
import com.lframework.xingyun.sc.excel.stock.adjust.StockAdjustSheetImportListener;
import com.lframework.xingyun.sc.excel.stock.adjust.StockAdjustSheetImportModel;
import com.lframework.xingyun.sc.service.stock.adjust.StockAdjustSheetService;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.ApprovePassStockAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.ApproveRefuseStockAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.CreateStockAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.QueryStockAdjustProductVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.QueryStockAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.UpdateStockAdjustSheetVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
import org.springframework.web.multipart.MultipartFile;

/**
 * 库存调整单 Controller
 *
 * @author zmj
 */
@Tag(name = "库存调整单")
@Validated
@RestController
@RequestMapping("/stock/adjust")
public class StockAdjustSheetController extends DefaultBaseController {

  @Autowired
  private StockAdjustSheetService stockAdjustSheetService;

  /**
   * 查询列表
   */
  @Operation(summary = "查询列表")
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
  @Operation(summary = "根据ID查询")
  @Parameter(name = "id", description = "ID", in = ParameterIn.QUERY, required = true)
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
  @Operation(summary = "导出")
  @HasPermission({"stock:adjust:export"})
  @PostMapping("/export")
  public InvokeResult<Void> export(@Valid QueryStockAdjustSheetVo vo) {

    ExportTaskUtil.exportTask("库存调整单信息", StockAdjustSheetExportTaskWorker.class, vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 下载导入模板
   */
  @Operation(summary = "下载导入模板")
  @HasPermission({"stock:adjust:import"})
  @GetMapping("/import/template")
  public void downloadImportTemplate() {

    ExcelUtil.exportXlsx("库存调整单导入模板", StockAdjustSheetImportModel.class);
  }

  /**
   * 导入
   */
  @Operation(summary = "导入")
  @HasPermission({"stock:adjust:import"})
  @PostMapping("/import")
  public InvokeResult<Void> importExcel(@NotBlank(message = "ID不能为空") String id,
      @NotNull(message = "请上传文件") MultipartFile file) {

    StockAdjustSheetImportListener listener = new StockAdjustSheetImportListener();
    listener.setTaskId(id);
    ExcelUtil.read(file, StockAdjustSheetImportModel.class, listener).sheet().doRead();

    return InvokeResultBuilder.success();
  }

  /**
   * 根据关键字查询商品列表
   */
  @Operation(summary = "根据关键字查询商品列表")
  @Parameters({
      @Parameter(name = "scId", description = "仓库ID", in = ParameterIn.QUERY, required = true),
      @Parameter(name = "condition", description = "关键字", in = ParameterIn.QUERY, required = true)})
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
  @Operation(summary = "查询商品列表")
  @HasPermission({"stock:adjust:add", "stock:adjust:modify"})
  @GetMapping("/product/list")
  public InvokeResult<PageResult<StockAdjustProductBo>> queryProductList(
      @Valid QueryStockAdjustProductVo vo) {

    PageResult<StockAdjustProductDto> pageResult = stockAdjustSheetService.queryStockAdjustList(
        getPageIndex(vo),
        getPageSize(vo), vo);
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
  @Operation(summary = "新增")
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
  @Operation(summary = "修改")
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
  @Operation(summary = "根据ID删除")
  @Parameter(name = "id", description = "ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"stock:adjust:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(@NotBlank(message = "id不能为空！") String id) {

    stockAdjustSheetService.deleteById(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核通过
   */
  @Operation(summary = "审核通过")
  @HasPermission({"stock:adjust:approve"})
  @PatchMapping("/approve/pass")
  public InvokeResult<Void> approvePass(@RequestBody @Valid ApprovePassStockAdjustSheetVo vo) {

    stockAdjustSheetService.approvePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 直接审核通过
   */
  @Operation(summary = "直接审核通过")
  @HasPermission({"stock:adjust:approve"})
  @PostMapping("/approve/pass/direct")
  public InvokeResult<Void> directApprovePass(@RequestBody @Valid CreateStockAdjustSheetVo vo) {

    vo.validate();

    stockAdjustSheetService.directApprovePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核拒绝
   */
  @Operation(summary = "审核拒绝")
  @HasPermission({"stock:adjust:approve"})
  @PatchMapping("/approve/refuse")
  public InvokeResult<Void> approveRefuse(
      @RequestBody @Valid ApproveRefuseStockAdjustSheetVo vo) {

    stockAdjustSheetService.approveRefuse(vo);

    return InvokeResultBuilder.success();
  }
}
