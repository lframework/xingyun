package com.lframework.xingyun.api.controller.basedata.product;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.api.bo.basedata.product.property.GetProductPropertyBo;
import com.lframework.xingyun.api.bo.basedata.product.property.ProductPropertyModelorBo;
import com.lframework.xingyun.api.bo.basedata.product.property.QueryProductPropertyBo;
import com.lframework.xingyun.basedata.dto.product.property.ProductPropertyDto;
import com.lframework.xingyun.basedata.dto.product.property.ProductPropertyModelorDto;
import com.lframework.xingyun.basedata.service.product.IProductPropertyService;
import com.lframework.xingyun.basedata.vo.product.property.CreateProductPropertyVo;
import com.lframework.xingyun.basedata.vo.product.property.QueryProductPropertyVo;
import com.lframework.xingyun.basedata.vo.product.property.UpdateProductPropertyVo;
import java.util.Collections;
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
 * 属性管理
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/basedata/product/property")
public class ProductPropertyController extends DefaultBaseController {

  @Autowired
  private IProductPropertyService productPropertyService;

  /**
   * 属性列表
   */
  @PreAuthorize("@permission.valid('base-data:product:property:query','base-data:product:property:add','base-data:product:property:modify')")
  @GetMapping("/query")
  public InvokeResult query(@Valid QueryProductPropertyVo vo) {

    PageResult<ProductPropertyDto> pageResult = productPropertyService
        .query(getPageIndex(vo), getPageSize(vo), vo);

    List<ProductPropertyDto> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      List<QueryProductPropertyBo> results = datas.stream().map(QueryProductPropertyBo::new)
          .collect(Collectors.toList());

      PageResultUtil.rebuild(pageResult, results);
    }

    return InvokeResultBuilder.success(pageResult);
  }

  /**
   * 查询属性
   */
  @PreAuthorize("@permission.valid('base-data:product:property:query','base-data:product:property:add','base-data:product:property:modify')")
  @GetMapping
  public InvokeResult get(@NotBlank(message = "ID不能为空！") String id) {

    ProductPropertyDto data = productPropertyService.getById(id);
    if (data == null) {
      throw new DefaultClientException("属性不存在！");
    }

    GetProductPropertyBo result = new GetProductPropertyBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 批量停用属性
   */
  @PreAuthorize("@permission.valid('base-data:product:property:modify')")
  @PatchMapping("/unable/batch")
  public InvokeResult batchUnable(
      @NotEmpty(message = "请选择需要停用的属性！") @RequestBody List<String> ids) {

    productPropertyService.batchUnable(ids);
    return InvokeResultBuilder.success();
  }

  /**
   * 批量启用属性
   */
  @PreAuthorize("@permission.valid('base-data:product:property:modify')")
  @PatchMapping("/enable/batch")
  public InvokeResult batchEnable(
      @NotEmpty(message = "请选择需要启用的属性！") @RequestBody List<String> ids) {

    productPropertyService.batchEnable(ids);
    return InvokeResultBuilder.success();
  }

  /**
   * 新增属性
   */
  @PreAuthorize("@permission.valid('base-data:product:property:add')")
  @PostMapping
  public InvokeResult create(@Valid CreateProductPropertyVo vo) {

    productPropertyService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改属性
   */
  @PreAuthorize("@permission.valid('base-data:product:property:modify')")
  @PutMapping
  public InvokeResult update(@Valid UpdateProductPropertyVo vo) {

    productPropertyService.update(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 属性模型
   */
  @GetMapping("/modelor/category")
  public InvokeResult getModelorByCategory(@NotBlank(message = "类目ID不能为空！") String categoryId) {

    List<ProductPropertyModelorDto> datas = productPropertyService
        .getModelorByCategoryId(categoryId);

    List<ProductPropertyModelorBo> results = Collections.EMPTY_LIST;
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(ProductPropertyModelorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }
}
