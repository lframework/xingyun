package com.lframework.xingyun.basedata.controller;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.starter.web.annotations.security.HasPermission;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.basedata.bo.product.property.item.GetProductPropertyItemBo;
import com.lframework.xingyun.basedata.bo.product.property.item.QueryProductPropertyItemBo;
import com.lframework.xingyun.basedata.entity.ProductPropertyItem;
import com.lframework.xingyun.basedata.service.product.ProductPropertyItemService;
import com.lframework.xingyun.basedata.vo.product.property.item.CreateProductPropertyItemVo;
import com.lframework.xingyun.basedata.vo.product.property.item.QueryProductPropertyItemVo;
import com.lframework.xingyun.basedata.vo.product.property.item.UpdateProductPropertyItemVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 属性值管理
 *
 * @author zmj
 */
@Api(tags = "属性值管理")
@Validated
@RestController
@RequestMapping("/basedata/product/property/item")
public class ProductPropertyItemController extends DefaultBaseController {

  @Autowired
  private ProductPropertyItemService productPropertyItemService;

  /**
   * 属性值列表
   */
  @ApiOperation("属性值列表")
  @HasPermission({"base-data:product:property-item:query", "base-data:product:property-item:add",
      "base-data:product:property-item:modify"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryProductPropertyItemBo>> query(
      @Valid QueryProductPropertyItemVo vo) {

    PageResult<ProductPropertyItem> pageResult = productPropertyItemService.query(getPageIndex(vo),
        getPageSize(vo),
        vo);

    List<ProductPropertyItem> datas = pageResult.getDatas();
    List<QueryProductPropertyItemBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryProductPropertyItemBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询属性值
   */
  @ApiOperation("查询属性值")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"base-data:product:property-item:query", "base-data:product:property-item:add",
      "base-data:product:property-item:modify"})
  @GetMapping
  public InvokeResult<GetProductPropertyItemBo> get(@NotBlank(message = "ID不能为空！") String id) {

    ProductPropertyItem data = productPropertyItemService.findById(id);
    if (data == null) {
      throw new DefaultClientException("属性值不存在！");
    }

    GetProductPropertyItemBo result = new GetProductPropertyItemBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增属性值
   */
  @ApiOperation("新增属性值")
  @HasPermission({"base-data:product:property-item:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateProductPropertyItemVo vo) {

    productPropertyItemService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改属性值
   */
  @ApiOperation("修改属性值")
  @HasPermission({"base-data:product:property-item:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateProductPropertyItemVo vo) {

    productPropertyItemService.update(vo);

    productPropertyItemService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }
}
