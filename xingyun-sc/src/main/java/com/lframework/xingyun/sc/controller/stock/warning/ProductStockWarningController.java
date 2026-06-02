package com.lframework.xingyun.sc.controller.stock.warning;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.utils.ExcelUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.xingyun.sc.bo.stock.warning.GetProductStockWarningBo;
import com.lframework.xingyun.sc.bo.stock.warning.GetProductStockWarningNotifyBo;
import com.lframework.xingyun.sc.bo.stock.warning.QueryProductStockWarningBo;
import com.lframework.xingyun.sc.entity.ProductStockWarning;
import com.lframework.xingyun.sc.entity.ProductStockWarningNotify;
import com.lframework.xingyun.sc.excel.stock.warning.StockWarningImportListener;
import com.lframework.xingyun.sc.excel.stock.warning.StockWarningImportModel;
import com.lframework.xingyun.sc.service.stock.warning.ProductStockWarningNotifyService;
import com.lframework.xingyun.sc.service.stock.warning.ProductStockWarningService;
import com.lframework.xingyun.sc.vo.stock.warning.CreateProductStockWarningVo;
import com.lframework.xingyun.sc.vo.stock.warning.QueryProductStockWarningVo;
import com.lframework.xingyun.sc.vo.stock.warning.UpdateProductStockWarningVo;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 库存预警 Controller
 *
 * @author zmj
 */
@Tag(name = "库存预警")
@Validated
@RestController
@RequestMapping("/stock/warning")
public class ProductStockWarningController extends DefaultBaseController {

  @Autowired
  private ProductStockWarningService productStockWarningService;

  @Autowired
  private ProductStockWarningNotifyService productStockWarningNotifyService;

  /**
   * 查询列表
   */
  @Operation(summary = "查询列表")
  @HasPermission({"stock:warning:query"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryProductStockWarningBo>> query(
      @Valid QueryProductStockWarningVo vo) {

    PageResult<ProductStockWarning> pageResult = productStockWarningService.query(
        getPageIndex(vo),
        getPageSize(vo), vo);

    List<ProductStockWarning> datas = pageResult.getDatas();
    List<QueryProductStockWarningBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryProductStockWarningBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 根据ID查询
   */
  @Operation(summary = "根据ID查询")
  @Parameter(name = "id", description = "ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"stock:warning:query"})
  @GetMapping("/detail")
  public InvokeResult<GetProductStockWarningBo> getDetail(
      @NotBlank(message = "id不能为空！") String id) {

    ProductStockWarning data = productStockWarningService.findById(id);
    if (data == null) {
      throw new DefaultClientException("库存预警不存在！");
    }

    GetProductStockWarningBo result = new GetProductStockWarningBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增
   */
  @Operation(summary = "新增")
  @HasPermission({"stock:warning:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateProductStockWarningVo vo) {

    productStockWarningService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改
   */
  @Operation(summary = "修改")
  @HasPermission({"stock:warning:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateProductStockWarningVo vo) {

    productStockWarningService.update(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 根据ID删除
   */
  @Operation(summary = "根据ID删除")
  @Parameter(name = "id", description = "ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"stock:warning:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(@NotBlank(message = "id不能为空！") String id) {

    productStockWarningService.deleteById(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 批量删除
   */
  @Operation(summary = "批量删除")
  @HasPermission({"stock:warning:delete"})
  @DeleteMapping("/batch")
  public InvokeResult<Void> deleteByIds(
      @Parameter(description = "ID", required = true) @RequestBody @NotEmpty(message = "请选择需要删除的库存预警！") List<String> ids) {

    productStockWarningService.deleteByIds(ids);

    return InvokeResultBuilder.success();
  }

  /**
   * 查询设置的消息通知组
   */
  @Operation(summary = "查询设置的消息通知组")
  @HasPermission({"stock:warning:notify"})
  @GetMapping("/setting")
  public InvokeResult<List<GetProductStockWarningNotifyBo>> getSetting() {
    List<ProductStockWarningNotify> datas = productStockWarningNotifyService.list(
        Wrappers.lambdaQuery(
            ProductStockWarningNotify.class).orderByDesc(ProductStockWarningNotify::getId));

    return InvokeResultBuilder.success(
        datas.stream().map(GetProductStockWarningNotifyBo::new).collect(
            Collectors.toList()));
  }

  /**
   * 新增设置的消息通知组
   */
  @Operation(summary = "保存设置的消息通知组")
  @HasPermission({"stock:warning:notify"})
  @PostMapping("/setting")
  public InvokeResult<Void> createSetting(
      @Parameter(description = "消息通知组ID", required = true) @NotBlank(message = "消息通知组ID不能为空！") String id) {

    productStockWarningNotifyService.createSetting(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 删除设置的消息通知组
   */
  @Operation(summary = "删除设置的消息通知组")
  @HasPermission({"stock:warning:notify"})
  @DeleteMapping("/setting")
  public InvokeResult<Void> deleteSetting(
      @Parameter(description = "消息通知组ID", required = true) @NotBlank(message = "消息通知组ID不能为空！") String id) {

    productStockWarningNotifyService.deleteSetting(id);

    return InvokeResultBuilder.success();
  }

  @Operation(summary = "下载导入模板")
  @HasPermission({"stock:warning:import"})
  @GetMapping("/import/template")
  public void downloadImportTemplate() {
    ExcelUtil.exportXlsx("库存预警导入模板", StockWarningImportModel.class);
  }

  @Operation(summary = "导入")
  @HasPermission({"stock:warning:import"})
  @PostMapping("/import")
  public InvokeResult<Void> importExcel(@NotBlank(message = "ID不能为空") String id,
      @NotNull(message = "请上传文件") MultipartFile file) {

    StockWarningImportListener listener = new StockWarningImportListener();
    listener.setTaskId(id);
    ExcelUtil.read(file, StockWarningImportModel.class, listener).sheet().doRead();

    return InvokeResultBuilder.success();
  }
}
