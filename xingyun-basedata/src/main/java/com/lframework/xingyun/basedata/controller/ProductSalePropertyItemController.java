package com.lframework.xingyun.basedata.controller;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.xingyun.basedata.bo.product.saleproperty.item.GetProductSalePropertyItemBo;
import com.lframework.xingyun.basedata.bo.product.saleproperty.item.QueryProductSalePropertyItemBo;
import com.lframework.xingyun.basedata.entity.ProductSalePropertyItem;
import com.lframework.xingyun.basedata.service.product.ProductSalePropertyItemService;
import com.lframework.xingyun.basedata.vo.product.saleproperty.item.CreateProductSalePropertyItemVo;
import com.lframework.xingyun.basedata.vo.product.saleproperty.item.QueryProductSalePropertyItemVo;
import com.lframework.xingyun.basedata.vo.product.saleproperty.item.UpdateProductSalePropertyItemVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 销售属性值管理
 *
 * @author zmj
 */
@Tag(name = "销售属性值管理")
@Validated
@RestController
@RequestMapping("/basedata/product/sale/property/item")
public class ProductSalePropertyItemController extends DefaultBaseController {

  @Autowired
  private ProductSalePropertyItemService productSalePropertyItemService;

  /**
   * 销售属性值列表
   */
  @Operation(summary = "销售属性值列表")
  @HasPermission({"base-data:product:sale-property-item:query",
      "base-data:product:sale-property-item:add",
      "base-data:product:sale-property-item:modify"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryProductSalePropertyItemBo>> query(
      @Valid QueryProductSalePropertyItemVo vo) {

    PageResult<ProductSalePropertyItem> pageResult = productSalePropertyItemService.query(
        getPageIndex(vo), getPageSize(vo), vo);

    List<ProductSalePropertyItem> datas = pageResult.getDatas();
    List<QueryProductSalePropertyItemBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryProductSalePropertyItemBo::new)
          .collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询销售属性值
   */
  @Operation(summary = "查询销售属性值")
  @Parameter(name = "id", description = "ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"base-data:product:sale-property-item:query",
      "base-data:product:sale-property-item:add",
      "base-data:product:sale-property-item:modify"})
  @GetMapping
  public InvokeResult<GetProductSalePropertyItemBo> get(
      @NotBlank(message = "ID不能为空！") String id) {

    ProductSalePropertyItem data = productSalePropertyItemService.findById(id);
    if (data == null) {
      throw new DefaultClientException("销售属性值不存在！");
    }

    GetProductSalePropertyItemBo result = new GetProductSalePropertyItemBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增销售属性值
   */
  @Operation(summary = "新增销售属性值")
  @HasPermission({"base-data:product:sale-property-item:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateProductSalePropertyItemVo vo) {

    productSalePropertyItemService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改销售属性值
   */
  @Operation(summary = "修改销售属性值")
  @HasPermission({"base-data:product:sale-property-item:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateProductSalePropertyItemVo vo) {

    productSalePropertyItemService.update(vo);

    productSalePropertyItemService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  /**
   * 删除销售属性值
   */
  @Operation(summary = "删除销售属性值")
  @HasPermission({"base-data:product:sale-property-item:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(
      @Parameter(description = "ID", required = true) @NotEmpty(message = "ID不能为空！") String id) {

    productSalePropertyItemService.deleteById(id);

    productSalePropertyItemService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }
}
