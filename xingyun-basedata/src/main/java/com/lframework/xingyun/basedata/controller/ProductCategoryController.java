package com.lframework.xingyun.basedata.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.utils.ExcelUtil;
import com.lframework.starter.web.inner.service.RecursionMappingService;
import com.lframework.xingyun.basedata.bo.product.category.GetProductCategoryBo;
import com.lframework.xingyun.basedata.bo.product.category.ProductCategoryTreeBo;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.entity.ProductCategoryPropertyRelation;
import com.lframework.xingyun.basedata.entity.ProductCategorySalePropertyRelation;
import com.lframework.xingyun.basedata.excel.product.category.ProductCategoryImportListener;
import com.lframework.xingyun.basedata.excel.product.category.ProductCategoryImportModel;
import com.lframework.xingyun.basedata.service.product.ProductCategoryService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryPropertyRelationService;
import com.lframework.xingyun.basedata.service.product.ProductCategorySalePropertyRelationService;
import com.lframework.xingyun.basedata.vo.product.category.CreateProductCategoryVo;
import com.lframework.xingyun.basedata.vo.product.category.UpdateProductCategoryVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.Set;
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
 * 分类管理
 *
 * @author zmj
 */
@Tag(name = "分类管理")
@Validated
@RestController
@RequestMapping("/basedata/product/category")
public class ProductCategoryController extends DefaultBaseController {

  @Autowired
  private ProductCategoryService productCategoryService;

  @Autowired
  private RecursionMappingService recursionMappingService;

  @Autowired
  private ProductCategoryPropertyRelationService ProductCategoryPropertyRelationService;

  @Autowired
  private ProductCategorySalePropertyRelationService ProductCategorySalePropertyRelationService;

  /**
   * 分类列表
   */
  @Operation(summary = "分类列表")
  @HasPermission({"base-data:product:category:query", "base-data:product:category:add",
      "base-data:product:category:modify"})
  @GetMapping("/query")
  public InvokeResult<List<ProductCategoryTreeBo>> query() {

    List<ProductCategory> datas = productCategoryService.getAllProductCategories();
    if (CollectionUtil.isEmpty(datas)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    Wrapper<ProductCategoryPropertyRelation> propertyWrapper = Wrappers.lambdaQuery(
            ProductCategoryPropertyRelation.class)
        .select(ProductCategoryPropertyRelation::getCategoryId)
        .in(ProductCategoryPropertyRelation::getCategoryId,
            datas.stream().map(ProductCategory::getId).collect(Collectors.toList()))
        .groupBy(ProductCategoryPropertyRelation::getCategoryId);
    Set<String> hasPropertyCategoryIds = ProductCategoryPropertyRelationService.list(propertyWrapper)
        .stream().map(ProductCategoryPropertyRelation::getCategoryId).collect(Collectors.toSet());

    Wrapper<ProductCategorySalePropertyRelation> salePropertyWrapper = Wrappers.lambdaQuery(
            ProductCategorySalePropertyRelation.class)
        .select(ProductCategorySalePropertyRelation::getCategoryId)
        .in(ProductCategorySalePropertyRelation::getCategoryId,
            datas.stream().map(ProductCategory::getId).collect(Collectors.toList()))
        .groupBy(ProductCategorySalePropertyRelation::getCategoryId);
    Set<String> hasSalePropertyCategoryIds = ProductCategorySalePropertyRelationService.list(
            salePropertyWrapper)
        .stream().map(ProductCategorySalePropertyRelation::getCategoryId).collect(Collectors.toSet());

    List<ProductCategoryTreeBo> results = datas.stream().map(ProductCategoryTreeBo::new)
        .peek(t -> t.setHasProperty(hasPropertyCategoryIds.contains(t.getId())))
        .peek(t -> t.setHasSaleProperty(hasSalePropertyCategoryIds.contains(t.getId())))
        .collect(Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  /**
   * 查询分类
   */
  @Operation(summary = "查询分类")
  @Parameter(name = "id", description = "ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"base-data:product:category:query", "base-data:product:category:add",
      "base-data:product:category:modify"})
  @GetMapping
  public InvokeResult<GetProductCategoryBo> get(@NotBlank(message = "ID不能为空！") String id) {

    ProductCategory data = productCategoryService.findById(id);
    if (data == null) {
      throw new DefaultClientException("分类不存在！");
    }

    GetProductCategoryBo result = new GetProductCategoryBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 根据ID删除
   */
  @Operation(summary = "根据ID删除")
  @HasPermission({"base-data:product:category:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(
      @Parameter(description = "ID", required = true) @NotEmpty(message = "分类ID不能为空！") String id) {

    productCategoryService.deleteById(id);
    productCategoryService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 新增分类
   */
  @Operation(summary = "新增分类")
  @HasPermission({"base-data:product:category:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateProductCategoryVo vo) {

    productCategoryService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改分类
   */
  @Operation(summary = "修改分类")
  @HasPermission({"base-data:product:category:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateProductCategoryVo vo) {

    productCategoryService.update(vo);

    productCategoryService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  @Operation(summary = "下载导入模板")
  @HasPermission({"base-data:product:category:import"})
  @GetMapping("/import/template")
  public void downloadImportTemplate() {
    ExcelUtil.exportXlsx("分类导入模板", ProductCategoryImportModel.class);
  }

  @Operation(summary = "导入")
  @HasPermission({"base-data:product:category:import"})
  @PostMapping("/import")
  public InvokeResult<Void> importExcel(@NotBlank(message = "ID不能为空") String id,
      @NotNull(message = "请上传文件") MultipartFile file) {

    ProductCategoryImportListener listener = new ProductCategoryImportListener();
    listener.setTaskId(id);
    ExcelUtil.read(file, ProductCategoryImportModel.class, listener).sheet().doRead();

    return InvokeResultBuilder.success();
  }
}
