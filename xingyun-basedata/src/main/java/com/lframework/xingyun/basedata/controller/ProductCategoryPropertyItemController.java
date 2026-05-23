package com.lframework.xingyun.basedata.controller;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.xingyun.basedata.bo.product.property.item.GetProductCategoryPropertyItemBo;
import com.lframework.xingyun.basedata.bo.product.property.item.QueryProductCategoryPropertyItemBo;
import com.lframework.xingyun.basedata.entity.ProductCategoryPropertyItem;
import com.lframework.xingyun.basedata.service.product.ProductCategoryPropertyItemService;
import com.lframework.xingyun.basedata.vo.product.property.item.CreateProductCategoryPropertyItemVo;
import com.lframework.xingyun.basedata.vo.product.property.item.QueryProductCategoryPropertyItemVo;
import com.lframework.xingyun.basedata.vo.product.property.item.UpdateProductCategoryPropertyItemVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 分类属性值管理
 *
 * @author zmj
 */
@Tag(name = "分类属性值管理")
@Validated
@RestController
@RequestMapping("/basedata/product/property/item")
public class ProductCategoryPropertyItemController extends DefaultBaseController {

  @Autowired
  private ProductCategoryPropertyItemService ProductCategoryPropertyItemService;

  /**
   * 分类属性值列表
   */
  @Operation(summary = "分类属性值列表")
  @HasPermission({"base-data:product:property-item:query", "base-data:product:property-item:add",
      "base-data:product:property-item:modify"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryProductCategoryPropertyItemBo>> query(
      @Valid QueryProductCategoryPropertyItemVo vo) {

    PageResult<ProductCategoryPropertyItem> pageResult = ProductCategoryPropertyItemService.query(getPageIndex(vo),
        getPageSize(vo),
        vo);

    List<ProductCategoryPropertyItem> datas = pageResult.getDatas();
    List<QueryProductCategoryPropertyItemBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryProductCategoryPropertyItemBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询分类属性值
   */
  @Operation(summary = "查询分类属性值")
  @Parameter(name = "id", description = "ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"base-data:product:property-item:query", "base-data:product:property-item:add",
      "base-data:product:property-item:modify"})
  @GetMapping
  public InvokeResult<GetProductCategoryPropertyItemBo> get(@NotBlank(message = "ID不能为空！") String id) {

    ProductCategoryPropertyItem data = ProductCategoryPropertyItemService.findById(id);
    if (data == null) {
      throw new DefaultClientException("分类属性值不存在！");
    }

    GetProductCategoryPropertyItemBo result = new GetProductCategoryPropertyItemBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增分类属性值
   */
  @Operation(summary = "新增分类属性值")
  @HasPermission({"base-data:product:property-item:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateProductCategoryPropertyItemVo vo) {

    ProductCategoryPropertyItemService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改分类属性值
   */
  @Operation(summary = "修改分类属性值")
  @HasPermission({"base-data:product:property-item:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateProductCategoryPropertyItemVo vo) {

    ProductCategoryPropertyItemService.update(vo);

    ProductCategoryPropertyItemService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  /**
   * 根据ID删除
   */
  @Operation(summary = "删除分类属性值")
  @HasPermission({"base-data:product:property-item:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(
      @Parameter(description = "ID", required = true) @NotEmpty(message = "ID不能为空！") String id) {

    ProductCategoryPropertyItemService.deleteById(id);

    ProductCategoryPropertyItemService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }
}
