package com.lframework.xingyun.api.controller.basedata.product;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.api.bo.basedata.product.category.GetProductCategoryBo;
import com.lframework.xingyun.api.bo.basedata.product.category.ProductCategoryTreeBo;
import com.lframework.xingyun.basedata.dto.product.category.ProductCategoryDto;
import com.lframework.xingyun.basedata.service.product.IProductCategoryService;
import com.lframework.xingyun.basedata.vo.product.category.CreateProductCategoryVo;
import com.lframework.xingyun.basedata.vo.product.category.UpdateProductCategoryVo;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类目管理
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/basedata/product/category")
public class ProductCategoryController extends DefaultBaseController {

  @Autowired
  private IProductCategoryService productCategoryService;

  /**
   * 类目列表
   */
  @PreAuthorize("@permission.valid('base-data:product:category:query','base-data:product:category:add','base-data:product:category:modify')")
  @GetMapping("/query")
  public InvokeResult query() {

    List<ProductCategoryDto> datas = productCategoryService.getAllProductCategories();
    if (CollectionUtil.isEmpty(datas)) {
      return InvokeResultBuilder.success();
    }

    List<ProductCategoryTreeBo> results = datas.stream().map(ProductCategoryTreeBo::new)
        .collect(Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  /**
   * 查询类目
   */
  @PreAuthorize("@permission.valid('base-data:product:category:query','base-data:product:category:add','base-data:product:category:modify')")
  @GetMapping
  public InvokeResult get(@NotBlank(message = "ID不能为空！") String id) {

    ProductCategoryDto data = productCategoryService.getById(id);
    if (data == null) {
      throw new DefaultClientException("类目不存在！");
    }

    GetProductCategoryBo result = new GetProductCategoryBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 批量停用类目
   */
  @PreAuthorize("@permission.valid('base-data:product:category:modify')")
  @PatchMapping("/unable/batch")
  public InvokeResult batchUnable(
      @NotEmpty(message = "请选择需要停用的类目！") @RequestBody List<String> ids) {

    productCategoryService.batchUnable(ids);
    return InvokeResultBuilder.success();
  }

  /**
   * 批量启用类目
   */
  @PreAuthorize("@permission.valid('base-data:product:category:modify')")
  @PatchMapping("/enable/batch")
  public InvokeResult batchEnable(
      @NotEmpty(message = "请选择需要启用的类目！") @RequestBody List<String> ids) {

    productCategoryService.batchEnable(ids);
    return InvokeResultBuilder.success();
  }

  /**
   * 新增类目
   */
  @PreAuthorize("@permission.valid('base-data:product:category:add')")
  @PostMapping
  public InvokeResult create(@Valid CreateProductCategoryVo vo) {

    productCategoryService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改类目
   */
  @PreAuthorize("@permission.valid('base-data:product:category:modify')")
  @PutMapping
  public InvokeResult update(@Valid UpdateProductCategoryVo vo) {

    productCategoryService.update(vo);

    return InvokeResultBuilder.success();
  }
}
