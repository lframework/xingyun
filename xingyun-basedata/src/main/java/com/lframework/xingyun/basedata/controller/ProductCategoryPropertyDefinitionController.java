package com.lframework.xingyun.basedata.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ThreadUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.components.threads.DefaultRunnable;
import com.lframework.starter.web.core.utils.ExcelUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.xingyun.basedata.bo.product.category.RelatedProductCategoryBo;
import com.lframework.xingyun.basedata.bo.product.property.GetProductCategoryPropertyDefinitionBo;
import com.lframework.xingyun.basedata.bo.product.property.ProductCategoryPropertyDefinitionModelorBo;
import com.lframework.xingyun.basedata.bo.product.property.QueryProductCategoryPropertyDefinitionBo;
import com.lframework.xingyun.basedata.dto.product.category.PropertyCategoryCountDto;
import com.lframework.xingyun.basedata.dto.product.property.ProductCategoryPropertyDefinitionModelorDto;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.entity.ProductCategoryPropertyDefinition;
import com.lframework.xingyun.basedata.entity.ProductCategoryPropertyValueRelation;
import com.lframework.xingyun.basedata.excel.product.property.ProductCategoryPropertyImportListener;
import com.lframework.xingyun.basedata.excel.product.property.ProductCategoryPropertyImportModel;
import com.lframework.xingyun.basedata.service.product.ProductCategoryPropertyRelationService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryPropertyValueRelationService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryPropertyDefinitionService;
import com.lframework.xingyun.basedata.vo.product.property.CreateProductCategoryPropertyDefinitionVo;
import com.lframework.xingyun.basedata.vo.product.property.QueryProductCategoryPropertyDefinitionVo;
import com.lframework.xingyun.basedata.vo.product.property.UpdateProductCategoryPropertyDefinitionVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.Map;
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
 * 商品分类属性管理
 *
 * @author zmj
 */
@Tag(name = "商品分类属性管理")
@Validated
@RestController
@RequestMapping("/basedata/product/property")
public class ProductCategoryPropertyDefinitionController extends DefaultBaseController {

  @Autowired
  private ProductCategoryPropertyDefinitionService ProductCategoryPropertyDefinitionService;

  @Autowired
  private ProductCategoryPropertyValueRelationService ProductCategoryPropertyValueRelationService;

  @Autowired
  private ProductCategoryPropertyRelationService ProductCategoryPropertyRelationService;

  /**
   * 商品分类属性列表
   */
  @Operation(summary = "商品分类属性列表")
  @HasPermission({"base-data:product:property:query", "base-data:product:property:add",
      "base-data:product:property:modify"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryProductCategoryPropertyDefinitionBo>> query(@Valid QueryProductCategoryPropertyDefinitionVo vo) {

    PageResult<ProductCategoryPropertyDefinition> pageResult = ProductCategoryPropertyDefinitionService.query(getPageIndex(vo),
        getPageSize(vo), vo);

    List<ProductCategoryPropertyDefinition> datas = pageResult.getDatas();
    List<QueryProductCategoryPropertyDefinitionBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {

      results = datas.stream().map(QueryProductCategoryPropertyDefinitionBo::new).collect(Collectors.toList());
      List<String> propertyIds = results.stream().map(QueryProductCategoryPropertyDefinitionBo::getId).collect(
          Collectors.toList());
      Map<String, Integer> categoryCountMap = ProductCategoryPropertyRelationService.countCategoriesByPropertyIds(
              propertyIds).stream()
          .collect(Collectors.toMap(PropertyCategoryCountDto::getPropertyId,
              PropertyCategoryCountDto::getCategoryCount));
      results.forEach(t -> t.setCategoryCount(categoryCountMap.getOrDefault(t.getId(), 0)));
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询商品分类属性
   */
  @Operation(summary = "查询商品分类属性")
  @Parameter(name = "id", description = "ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"base-data:product:property:query", "base-data:product:property:add",
      "base-data:product:property:modify"})
  @GetMapping
  public InvokeResult<GetProductCategoryPropertyDefinitionBo> get(@NotBlank(message = "ID不能为空！") String id) {

    ProductCategoryPropertyDefinition data = ProductCategoryPropertyDefinitionService.findById(id);
    if (data == null) {
      throw new DefaultClientException("商品分类属性不存在！");
    }

    GetProductCategoryPropertyDefinitionBo result = new GetProductCategoryPropertyDefinitionBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 根据ID删除
   */
  @Operation(summary = "删除商品分类属性")
  @HasPermission({"base-data:product:property:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(
      @Parameter(description = "ID", required = true) @NotEmpty(message = "商品分类属性ID不能为空！") String id) {

    ProductCategoryPropertyDefinitionService.deleteById(id);

    ProductCategoryPropertyDefinitionService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 新增商品分类属性
   */
  @Operation(summary = "新增商品分类属性")
  @HasPermission({"base-data:product:property:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid @RequestBody CreateProductCategoryPropertyDefinitionVo vo) {

    ProductCategoryPropertyDefinitionService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改商品分类属性
   */
  @Operation(summary = "修改商品分类属性")
  @HasPermission({"base-data:product:property:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid @RequestBody UpdateProductCategoryPropertyDefinitionVo vo) {

    ProductCategoryPropertyDefinitionService.update(vo);

    ProductCategoryPropertyDefinitionService.cleanCacheByKey(vo.getId());

    ThreadUtil.execAsync(new DefaultRunnable((() -> {
      Wrapper<ProductCategoryPropertyValueRelation> relationWrapper = Wrappers.lambdaQuery(
              ProductCategoryPropertyValueRelation.class).select(ProductCategoryPropertyValueRelation::getProductId)
          .eq(ProductCategoryPropertyValueRelation::getPropertyId, vo.getId())
          .groupBy(ProductCategoryPropertyValueRelation::getProductId);
      List<ProductCategoryPropertyValueRelation> relations = ProductCategoryPropertyValueRelationService.list(
          relationWrapper);

      ProductCategoryPropertyValueRelationService.cleanCacheByKeys(
          relations.stream().map(ProductCategoryPropertyValueRelation::getProductId).collect(
              Collectors.toList()));
    })));

    return InvokeResultBuilder.success();
  }

  /**
   * 查询已关联商品分类
   */
  @Operation(summary = "查询已关联商品分类")
  @Parameter(name = "id", description = "分类属性ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"base-data:product:category:query"})
  @GetMapping("/categories")
  public InvokeResult<List<RelatedProductCategoryBo>> getCategories(
      @NotBlank(message = "分类属性ID不能为空！") String id) {

    List<ProductCategory> datas = ProductCategoryPropertyRelationService.getCategoriesByPropertyId(id);
    List<RelatedProductCategoryBo> results = CollectionUtil.emptyList();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(RelatedProductCategoryBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  @Operation(summary = "下载导入模板")
  @HasPermission({"base-data:product:property:import"})
  @GetMapping("/import/template")
  public void downloadImportTemplate() {
    ExcelUtil.exportXls("商品分类属性导入模板", ProductCategoryPropertyImportModel.class);
  }

  @Operation(summary = "导入")
  @HasPermission({"base-data:product:property:import"})
  @PostMapping("/import")
  public InvokeResult<Void> importExcel(@NotBlank(message = "ID不能为空") String id,
      @NotNull(message = "请上传文件") MultipartFile file) {

    ProductCategoryPropertyImportListener listener = new ProductCategoryPropertyImportListener();
    listener.setTaskId(id);
    ExcelUtil.read(file, ProductCategoryPropertyImportModel.class, listener).sheet().doRead();

    return InvokeResultBuilder.success();
  }

  /**
   * 分类属性模型
   */
  @Operation(summary = "分类属性模型")
  @Parameter(name = "categoryId", description = "分类ID", in = ParameterIn.QUERY, required = true)
  @GetMapping("/modelor/category")
  public InvokeResult<List<ProductCategoryPropertyDefinitionModelorBo>> getModelorByCategory(
      @NotBlank(message = "分类ID不能为空！") String categoryId) {

    List<ProductCategoryPropertyDefinitionModelorDto> datas = ProductCategoryPropertyDefinitionService.getModelorByCategoryId(
        categoryId);

    List<ProductCategoryPropertyDefinitionModelorBo> results = CollectionUtil.emptyList();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(ProductCategoryPropertyDefinitionModelorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }
}
