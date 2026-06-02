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
import com.lframework.xingyun.basedata.bo.product.category.RelatedProductCategoryBo;
import com.lframework.xingyun.basedata.bo.product.saleproperty.GetProductSalePropertyDefinitionBo;
import com.lframework.xingyun.basedata.bo.product.saleproperty.ProductSalePropertyDefinitionModelorBo;
import com.lframework.xingyun.basedata.bo.product.saleproperty.QueryProductSalePropertyDefinitionBo;
import com.lframework.xingyun.basedata.dto.product.category.PropertyCategoryCountDto;
import com.lframework.xingyun.basedata.dto.product.saleproperty.ProductSalePropertyDefinitionModelorDto;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.entity.ProductSalePropertyDefinition;
import com.lframework.xingyun.basedata.excel.product.saleproperty.ProductSalePropertyImportListener;
import com.lframework.xingyun.basedata.excel.product.saleproperty.ProductSalePropertyImportModel;
import com.lframework.xingyun.basedata.service.product.ProductCategorySalePropertyRelationService;
import com.lframework.xingyun.basedata.service.product.ProductSalePropertyDefinitionService;
import com.lframework.xingyun.basedata.vo.product.saleproperty.CreateProductSalePropertyDefinitionVo;
import com.lframework.xingyun.basedata.vo.product.saleproperty.QueryProductSalePropertyDefinitionVo;
import com.lframework.xingyun.basedata.vo.product.saleproperty.UpdateProductSalePropertyDefinitionVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
 * 商品销售属性管理
 *
 * @author zmj
 */
@Tag(name = "商品销售属性管理")
@Validated
@RestController
@RequestMapping("/basedata/product/sale/property")
public class ProductSalePropertyDefinitionController extends DefaultBaseController {

  @Autowired
  private ProductSalePropertyDefinitionService ProductSalePropertyDefinitionService;

  @Autowired
  private ProductCategorySalePropertyRelationService ProductCategorySalePropertyRelationService;

  /**
   * 商品销售属性列表
   */
  @Operation(summary = "商品销售属性列表")
  @HasPermission({"base-data:product:sale-property:query",
      "base-data:product:sale-property:add", "base-data:product:sale-property:modify"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryProductSalePropertyDefinitionBo>> query(
      @Valid QueryProductSalePropertyDefinitionVo vo) {

    PageResult<ProductSalePropertyDefinition> pageResult = ProductSalePropertyDefinitionService.query(
        getPageIndex(vo), getPageSize(vo), vo);

    List<ProductSalePropertyDefinition> datas = pageResult.getDatas();
    List<QueryProductSalePropertyDefinitionBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryProductSalePropertyDefinitionBo::new).collect(Collectors.toList());
      List<String> propertyIds = results.stream().map(QueryProductSalePropertyDefinitionBo::getId).collect(
          Collectors.toList());
      Map<String, Integer> categoryCountMap = ProductCategorySalePropertyRelationService.countCategoriesByPropertyIds(
              propertyIds).stream()
          .collect(Collectors.toMap(PropertyCategoryCountDto::getPropertyId,
              PropertyCategoryCountDto::getCategoryCount));
      results.forEach(t -> t.setCategoryCount(categoryCountMap.getOrDefault(t.getId(), 0)));
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询商品销售属性
   */
  @Operation(summary = "查询商品销售属性")
  @Parameter(name = "id", description = "ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"base-data:product:sale-property:query",
      "base-data:product:sale-property:add", "base-data:product:sale-property:modify"})
  @GetMapping
  public InvokeResult<GetProductSalePropertyDefinitionBo> get(
      @NotBlank(message = "ID不能为空！") String id) {

    ProductSalePropertyDefinition data = ProductSalePropertyDefinitionService.findById(id);
    if (data == null) {
      throw new DefaultClientException("商品销售属性不存在！");
    }

    GetProductSalePropertyDefinitionBo result = new GetProductSalePropertyDefinitionBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 删除商品销售属性
   */
  @Operation(summary = "删除商品销售属性")
  @HasPermission({"base-data:product:sale-property:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(
      @Parameter(description = "ID", required = true) @NotEmpty(message = "商品销售属性ID不能为空！") String id) {

    ProductSalePropertyDefinitionService.deleteById(id);

    ProductSalePropertyDefinitionService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 新增商品销售属性
   */
  @Operation(summary = "新增商品销售属性")
  @HasPermission({"base-data:product:sale-property:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid @RequestBody CreateProductSalePropertyDefinitionVo vo) {

    ProductSalePropertyDefinitionService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改商品销售属性
   */
  @Operation(summary = "修改商品销售属性")
  @HasPermission({"base-data:product:sale-property:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid @RequestBody UpdateProductSalePropertyDefinitionVo vo) {

    ProductSalePropertyDefinitionService.update(vo);

    ProductSalePropertyDefinitionService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  /**
   * 查询已关联商品分类
   */
  @Operation(summary = "查询已关联商品分类")
  @Parameter(name = "id", description = "销售属性ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"base-data:product:category:query"})
  @GetMapping("/categories")
  public InvokeResult<List<RelatedProductCategoryBo>> getCategories(
      @NotBlank(message = "销售属性ID不能为空！") String id) {

    List<ProductCategory> datas = ProductCategorySalePropertyRelationService.getCategoriesByPropertyId(id);
    List<RelatedProductCategoryBo> results = CollectionUtil.emptyList();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(RelatedProductCategoryBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  @Operation(summary = "下载导入模板")
  @HasPermission({"base-data:product:sale-property:import"})
  @GetMapping("/import/template")
  public void downloadImportTemplate() {
    ExcelUtil.exportXlsx("商品销售属性导入模板", ProductSalePropertyImportModel.class);
  }

  @Operation(summary = "导入")
  @HasPermission({"base-data:product:sale-property:import"})
  @PostMapping("/import")
  public InvokeResult<Void> importExcel(@NotBlank(message = "ID不能为空") String id,
      @NotNull(message = "请上传文件") MultipartFile file) {

    ProductSalePropertyImportListener listener = new ProductSalePropertyImportListener();
    listener.setTaskId(id);
    ExcelUtil.read(file, ProductSalePropertyImportModel.class, listener).sheet().doRead();

    return InvokeResultBuilder.success();
  }

  /**
   * 商品销售属性模型
   */
  @Operation(summary = "商品销售属性模型")
  @GetMapping("/modelor")
  public InvokeResult<List<ProductSalePropertyDefinitionModelorBo>> getModelor() {

    List<ProductSalePropertyDefinitionModelorDto> datas = ProductSalePropertyDefinitionService.getModelor();
    List<ProductSalePropertyDefinitionModelorBo> results = CollectionUtil.emptyList();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(ProductSalePropertyDefinitionModelorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }
}
