package com.lframework.xingyun.basedata.controller;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.xingyun.basedata.bo.supplier.product.GetSupplierProductBo;
import com.lframework.xingyun.basedata.bo.supplier.product.QuerySupplierProductBo;
import com.lframework.xingyun.basedata.entity.SupplierProduct;
import com.lframework.xingyun.basedata.service.supplier.SupplierProductService;
import com.lframework.xingyun.basedata.vo.supplier.product.CreateSupplierProductVo;
import com.lframework.xingyun.basedata.vo.supplier.product.QuerySupplierProductVo;
import com.lframework.xingyun.basedata.vo.supplier.product.UpdateSupplierProductVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
 * 供应商商品关联管理
 */
@Tag(name = "供应商商品关联管理")
@Validated
@RestController
@RequestMapping("/basedata/supplier/product")
public class SupplierProductController extends DefaultBaseController {

  @Autowired
  private SupplierProductService supplierProductService;

  @Operation(summary = "供应商商品关联列表")
  @HasPermission({"base-data:supplier:product:query", "base-data:supplier:product:add", "base-data:supplier:product:modify"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QuerySupplierProductBo>> query(@Valid QuerySupplierProductVo vo) {

    PageResult<SupplierProduct> pageResult = supplierProductService.query(getPageIndex(vo),
        getPageSize(vo), vo);

    List<SupplierProduct> datas = pageResult.getDatas();
    List<QuerySupplierProductBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySupplierProductBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  @Operation(summary = "根据ID查询")
  @GetMapping
  public InvokeResult<GetSupplierProductBo> get(@NotBlank(message = "ID不能为空！") String id) {

    SupplierProduct data = supplierProductService.findById(id);
    if (data == null) {
      throw new DefaultClientException("供应商商品关联不存在！");
    }

    return InvokeResultBuilder.success(new GetSupplierProductBo(data));
  }

  @Operation(summary = "根据供应商ID查询关联商品")
  @GetMapping("/by-supplier")
  public InvokeResult<List<QuerySupplierProductBo>> listBySupplier(
      @NotBlank(message = "供应商ID不能为空！") String supplierId) {

    List<SupplierProduct> datas = supplierProductService.listBySupplierId(supplierId);
    List<QuerySupplierProductBo> results = null;
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySupplierProductBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  @Operation(summary = "根据商品ID查询关联供应商")
  @GetMapping("/by-product")
  public InvokeResult<List<QuerySupplierProductBo>> listByProduct(
      @NotBlank(message = "商品ID不能为空！") String productId) {

    List<SupplierProduct> datas = supplierProductService.listByProductId(productId);
    List<QuerySupplierProductBo> results = null;
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySupplierProductBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  @Operation(summary = "新增供应商商品关联")
  @HasPermission({"base-data:supplier:product:add"})
  @PostMapping
  public InvokeResult<String> create(@Valid CreateSupplierProductVo vo) {

    String id = supplierProductService.create(vo);
    return InvokeResultBuilder.success(id);
  }

  @Operation(summary = "修改供应商商品关联")
  @HasPermission({"base-data:supplier:product:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateSupplierProductVo vo) {

    supplierProductService.update(vo);
    return InvokeResultBuilder.success();
  }

  @Operation(summary = "删除供应商商品关联")
  @HasPermission({"base-data:supplier:product:delete"})
  @DeleteMapping
  public InvokeResult<Void> delete(@NotBlank(message = "ID不能为空！") String id) {

    supplierProductService.deleteById(id);
    return InvokeResultBuilder.success();
  }
}