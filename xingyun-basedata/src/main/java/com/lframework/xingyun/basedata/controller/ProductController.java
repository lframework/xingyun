package com.lframework.xingyun.basedata.controller;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.starter.web.annotations.security.HasPermission;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.basedata.bo.product.info.GetProductBo;
import com.lframework.xingyun.basedata.bo.product.info.QueryProductBo;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.excel.product.ProductImportListener;
import com.lframework.xingyun.basedata.excel.product.ProductImportModel;
import com.lframework.xingyun.basedata.service.product.ProductBundleService;
import com.lframework.xingyun.basedata.service.product.ProductPropertyRelationService;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.basedata.vo.product.info.CreateProductVo;
import com.lframework.xingyun.basedata.vo.product.info.QueryProductVo;
import com.lframework.xingyun.basedata.vo.product.info.UpdateProductVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
@Api(tags = "商品管理")
@Validated
@RestController
@RequestMapping("/basedata/product")
public class ProductController extends DefaultBaseController {

  @Autowired
  private ProductService productService;

  @Autowired
  private ProductBundleService productBundleService;

  @Autowired
  private ProductPropertyRelationService productPropertyRelationService;

  /**
   * 商品列表
   */
  @ApiOperation("商品列表")
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
   * 商品详情
   */
  @ApiOperation("商品详情")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
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
  @ApiOperation("新增商品")
  @HasPermission({"base-data:product:info:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid @RequestBody CreateProductVo vo) {

    productService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改商品
   */
  @ApiOperation("修改商品")
  @HasPermission({"base-data:product:info:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid @RequestBody UpdateProductVo vo) {

    productService.update(vo);

    productService.cleanCacheByKey(vo.getId());

    productPropertyRelationService.cleanCacheByKey(vo.getId());

    productBundleService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  @ApiOperation("下载导入模板")
  @HasPermission({"base-data:product:info:import"})
  @GetMapping("/import/template")
  public void downloadImportTemplate() {
    ExcelUtil.exportXls("商品导入模板", ProductImportModel.class);
  }

  @ApiOperation("导入")
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
