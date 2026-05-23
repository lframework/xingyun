package com.lframework.xingyun.basedata.controller;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.utils.ExcelUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.xingyun.basedata.bo.product.info.GetProductBo;
import com.lframework.xingyun.basedata.bo.product.info.QueryProductBo;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.ProductSku;
import com.lframework.xingyun.basedata.excel.product.ProductImportListener;
import com.lframework.xingyun.basedata.excel.product.ProductImportModel;
import com.lframework.xingyun.basedata.service.product.ProductBundleService;
import com.lframework.xingyun.basedata.service.product.ProductCodeService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryPropertyValueRelationService;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.basedata.service.product.ProductSkuService;
import com.lframework.xingyun.basedata.vo.product.info.CreateProductVo;
import com.lframework.xingyun.basedata.vo.product.info.QueryProductVo;
import com.lframework.xingyun.basedata.vo.product.info.UpdateProductVo;
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
 * 商品管理
 *
 * @author zmj
 */
@Tag(name = "商品管理")
@Validated
@RestController
@RequestMapping("/basedata/product")
public class ProductController extends DefaultBaseController {

  @Autowired
  private ProductService productService;

  @Autowired
  private ProductBundleService productBundleService;

  @Autowired
  private ProductCategoryPropertyValueRelationService ProductCategoryPropertyValueRelationService;

  @Autowired
  private ProductCodeService productCodeService;

  @Autowired
  private ProductSkuService productSkuService;

  /**
   * 商品列表
   */
  @Operation(summary = "商品列表")
  @HasPermission({"base-data:product:info:query", "base-data:product:info:add",
      "base-data:product:info:modify"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryProductBo>> query(@Valid QueryProductVo vo) {

    PageResult<Product> pageResult = productService.query(getPageIndex(vo), getPageSize(vo), vo);

    List<Product> datas = pageResult.getDatas();
    List<QueryProductBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {

      results = datas.stream().map(QueryProductBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 根据商品编号查询商品ID
   */
  @Operation(summary = "根据商品编号查询商品ID", description = "返回SKU ID，如果SKU不存在则返回null")
  @Parameter(name = "code", description = "商品编号", in = ParameterIn.QUERY, required = true)
  @HasPermission({"base-data:product:info:query"})
  @GetMapping("/id/code")
  public InvokeResult<String> getIdByCode(
      @NotBlank(message = "商品编号不能为空！") String code) {

    ProductSku sku = productSkuService.findAvailableByCode(code);

    return InvokeResultBuilder.success(sku == null ? null : sku.getId());
  }

  /**
   * 商品详情
   */
  @Operation(summary = "商品详情")
  @Parameter(name = "id", description = "ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"base-data:product:info:query", "base-data:product:info:add",
      "base-data:product:info:modify"})
  @GetMapping
  public InvokeResult<GetProductBo> get(@NotBlank(message = "ID不能为空！") String id) {

    Product data = productService.findById(id);

    GetProductBo result = new GetProductBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增商品
   */
  @Operation(summary = "新增商品")
  @HasPermission({"base-data:product:info:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid @RequestBody CreateProductVo vo) {

    productService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改商品
   */
  @Operation(summary = "修改商品")
  @HasPermission({"base-data:product:info:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid @RequestBody UpdateProductVo vo) {

    productService.update(vo);

    productService.cleanCacheByKey(vo.getId());

    ProductCategoryPropertyValueRelationService.cleanCacheByKey(vo.getId());

    productBundleService.cleanCacheByKey(vo.getId());

    productCodeService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  /**
   * 根据ID删除
   */
  @Operation(summary = "根据ID删除")
  @HasPermission({"base-data:product:info:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(
      @Parameter(description = "ID", required = true) @NotEmpty(message = "ID不能为空！") String id) {

    productService.deleteById(id);

    productService.cleanCacheByKey(id);

    ProductCategoryPropertyValueRelationService.cleanCacheByKey(id);

    productBundleService.cleanCacheByKey(id);

    productCodeService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  @Operation(summary = "下载导入模板")
  @HasPermission({"base-data:product:info:import"})
  @GetMapping("/import/template")
  public void downloadImportTemplate() {
    ExcelUtil.exportXls("商品导入模板", ProductImportModel.class);
  }

  @Operation(summary = "导入")
  @HasPermission({"base-data:product:info:import"})
  @PostMapping("/import")
  public InvokeResult<Void> importExcel(@NotBlank(message = "ID不能为空") String id,
      @NotNull(message = "请上传文件") MultipartFile file) {

    ProductImportListener listener = new ProductImportListener();
    listener.setTaskId(id);
    ExcelUtil.read(file, ProductImportModel.class, listener).sheet().doRead();

    return InvokeResultBuilder.success();
  }
}
