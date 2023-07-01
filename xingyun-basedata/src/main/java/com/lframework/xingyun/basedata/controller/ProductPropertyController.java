package com.lframework.xingyun.basedata.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ThreadUtil;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.starter.web.annotations.security.HasPermission;
import com.lframework.starter.web.common.threads.DefaultRunnable;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.basedata.bo.product.property.GetProductPropertyBo;
import com.lframework.xingyun.basedata.bo.product.property.ProductPropertyModelorBo;
import com.lframework.xingyun.basedata.bo.product.property.QueryProductPropertyBo;
import com.lframework.xingyun.basedata.dto.product.property.ProductPropertyModelorDto;
import com.lframework.xingyun.basedata.entity.ProductProperty;
import com.lframework.xingyun.basedata.entity.ProductPropertyRelation;
import com.lframework.xingyun.basedata.service.product.ProductPropertyRelationService;
import com.lframework.xingyun.basedata.service.product.ProductPropertyService;
import com.lframework.xingyun.basedata.vo.product.property.CreateProductPropertyVo;
import com.lframework.xingyun.basedata.vo.product.property.QueryProductPropertyVo;
import com.lframework.xingyun.basedata.vo.product.property.UpdateProductPropertyVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 属性管理
 *
 * @author zmj
 */
@Api(tags = "属性管理")
@Validated
@RestController
@RequestMapping("/basedata/product/property")
public class ProductPropertyController extends DefaultBaseController {

  @Autowired
  private ProductPropertyService productPropertyService;

  @Autowired
  private ProductPropertyRelationService productPropertyRelationService;

  /**
   * 属性列表
   */
  @ApiOperation("属性列表")
  @HasPermission({"base-data:product:property:query", "base-data:product:property:add",
      "base-data:product:property:modify"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryProductPropertyBo>> query(@Valid QueryProductPropertyVo vo) {

    PageResult<ProductProperty> pageResult = productPropertyService.query(getPageIndex(vo),
        getPageSize(vo), vo);

    List<ProductProperty> datas = pageResult.getDatas();
    List<QueryProductPropertyBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {

      results = datas.stream().map(QueryProductPropertyBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询属性
   */
  @ApiOperation("查询属性")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"base-data:product:property:query", "base-data:product:property:add",
      "base-data:product:property:modify"})
  @GetMapping
  public InvokeResult<GetProductPropertyBo> get(@NotBlank(message = "ID不能为空！") String id) {

    ProductProperty data = productPropertyService.findById(id);
    if (data == null) {
      throw new DefaultClientException("属性不存在！");
    }

    GetProductPropertyBo result = new GetProductPropertyBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 批量停用属性
   */
  @ApiOperation("批量停用属性")
  @HasPermission({"base-data:product:property:modify"})
  @PatchMapping("/unable/batch")
  public InvokeResult<Void> batchUnable(
      @ApiParam(value = "ID", required = true) @NotEmpty(message = "请选择需要停用的属性！") @RequestBody List<String> ids) {

    productPropertyService.batchUnable(ids);

    for (String id : ids) {
      productPropertyService.cleanCacheByKey(id);
    }

    return InvokeResultBuilder.success();
  }

  /**
   * 批量启用属性
   */
  @ApiOperation("批量启用属性")
  @HasPermission({"base-data:product:property:modify"})
  @PatchMapping("/enable/batch")
  public InvokeResult<Void> batchEnable(
      @ApiParam(value = "ID", required = true) @NotEmpty(message = "请选择需要启用的属性！") @RequestBody List<String> ids) {

    productPropertyService.batchEnable(ids);

    for (String id : ids) {
      productPropertyService.cleanCacheByKey(id);
    }

    return InvokeResultBuilder.success();
  }

  /**
   * 新增属性
   */
  @ApiOperation("新增属性")
  @HasPermission({"base-data:product:property:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateProductPropertyVo vo) {

    productPropertyService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改属性
   */
  @ApiOperation("修改属性")
  @HasPermission({"base-data:product:property:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateProductPropertyVo vo) {

    productPropertyService.update(vo);

    productPropertyService.cleanCacheByKey(vo.getId());

    ThreadUtil.execAsync(new DefaultRunnable((() -> {
      Wrapper<ProductPropertyRelation> relationWrapper = Wrappers.lambdaQuery(
              ProductPropertyRelation.class).select(ProductPropertyRelation::getProductId)
          .eq(ProductPropertyRelation::getPropertyId, vo.getId())
          .groupBy(ProductPropertyRelation::getProductId);
      List<ProductPropertyRelation> relations = productPropertyRelationService.list(
          relationWrapper);

      productPropertyRelationService.cleanCacheByKeys(
          relations.stream().map(ProductPropertyRelation::getProductId).collect(
              Collectors.toList()));
    })));

    return InvokeResultBuilder.success();
  }

  /**
   * 属性模型
   */
  @ApiOperation("属性模型")
  @ApiImplicitParam(value = "类目ID", name = "categoryId", paramType = "query", required = true)
  @GetMapping("/modelor/category")
  public InvokeResult<List<ProductPropertyModelorBo>> getModelorByCategory(
      @NotBlank(message = "类目ID不能为空！") String categoryId) {

    List<ProductPropertyModelorDto> datas = productPropertyService.getModelorByCategoryId(
        categoryId);

    List<ProductPropertyModelorBo> results = CollectionUtil.emptyList();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(ProductPropertyModelorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }
}
