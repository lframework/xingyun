package com.lframework.xingyun.api.controller.basedata.product;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.api.bo.basedata.product.saleprop.item.GetEnableSalePropItemBo;
import com.lframework.xingyun.api.bo.basedata.product.saleprop.item.GetProductSalePropItemBo;
import com.lframework.xingyun.api.bo.basedata.product.saleprop.item.QueryProductSalePropItemBo;
import com.lframework.xingyun.basedata.dto.product.saleprop.item.ProductSalePropItemDto;
import com.lframework.xingyun.basedata.service.product.IProductSalePropItemService;
import com.lframework.xingyun.basedata.vo.product.saleprop.item.CreateProductSalePropItemVo;
import com.lframework.xingyun.basedata.vo.product.saleprop.item.QueryProductSalePropItemVo;
import com.lframework.xingyun.basedata.vo.product.saleprop.item.UpdateProductSalePropItemVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 销售属性管理
 *
 * @author zmj
 */
@Api(tags = "销售属性管理")
@Validated
@RestController
@RequestMapping("/basedata/product/saleprop/item")
public class ProductSalePropItemController extends DefaultBaseController {

  @Autowired
  private IProductSalePropItemService productSalePropItemService;

  /**
   * 销售属性列表
   */
  @ApiOperation("销售属性列表")
  @PreAuthorize("@permission.valid('base-data:product:saleprop-item:query','base-data:product:saleprop-item:add','base-data:product:saleprop-item:modify')")
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryProductSalePropItemBo>> query(
      @Valid QueryProductSalePropItemVo vo) {

    PageResult<ProductSalePropItemDto> pageResult = productSalePropItemService.query(
        getPageIndex(vo), getPageSize(vo), vo);

    List<ProductSalePropItemDto> datas = pageResult.getDatas();
    List<QueryProductSalePropItemBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {

      results = datas.stream().map(QueryProductSalePropItemBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询销售属性
   */
  @ApiOperation("查询销售属性")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @PreAuthorize("@permission.valid('base-data:product:saleprop-item:query','base-data:product:saleprop-item:add','base-data:product:saleprop-item:modify')")
  @GetMapping
  public InvokeResult<GetProductSalePropItemBo> get(@NotBlank(message = "ID不能为空！") String id) {

    ProductSalePropItemDto data = productSalePropItemService.getById(id);
    if (data == null) {
      throw new DefaultClientException("销售属性不存在！");
    }

    GetProductSalePropItemBo result = new GetProductSalePropItemBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增销售属性
   */
  @ApiOperation("新增销售属性")
  @PreAuthorize("@permission.valid('base-data:product:saleprop-item:add')")
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateProductSalePropItemVo vo) {

    productSalePropItemService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改销售属性
   */
  @ApiOperation("修改销售属性")
  @PreAuthorize("@permission.valid('base-data:product:saleprop-item:modify')")
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateProductSalePropItemVo vo) {

    productSalePropItemService.update(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 根据销售属性组ID查询启用的销售属性
   */
  @ApiOperation("根据销售属性组ID查询启用的销售属性")
  @ApiImplicitParam(value = "销售属性组ID", name = "groupId", paramType = "query", required = true)
  @GetMapping("/enable")
  public InvokeResult<List<GetEnableSalePropItemBo>> queryEnableList(
      @NotBlank(message = "销售属性组ID不能为空！") String groupId) {

    List<ProductSalePropItemDto> datas = productSalePropItemService.getEnablesByGroupId(groupId);
    List<GetEnableSalePropItemBo> results = Collections.EMPTY_LIST;
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(GetEnableSalePropItemBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }
}
