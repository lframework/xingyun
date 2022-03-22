package com.lframework.xingyun.api.controller.basedata.product;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.api.bo.basedata.product.property.item.GetProductPropertyItemBo;
import com.lframework.xingyun.api.bo.basedata.product.property.item.QueryProductPropertyItemBo;
import com.lframework.xingyun.basedata.dto.product.property.item.ProductPropertyItemDto;
import com.lframework.xingyun.basedata.service.product.IProductPropertyItemService;
import com.lframework.xingyun.basedata.vo.product.property.item.CreateProductPropertyItemVo;
import com.lframework.xingyun.basedata.vo.product.property.item.QueryProductPropertyItemVo;
import com.lframework.xingyun.basedata.vo.product.property.item.UpdateProductPropertyItemVo;
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
 * 属性值管理
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/basedata/product/property/item")
public class ProductPropertyItemController extends DefaultBaseController {

  @Autowired
  private IProductPropertyItemService productPropertyItemService;

  /**
   * 属性值列表
   */
  @PreAuthorize("@permission.valid('base-data:product:property-item:query','base-data:product:property-item:add','base-data:product:property-item:modify')")
  @GetMapping("/query")
  public InvokeResult query(@Valid QueryProductPropertyItemVo vo) {

    PageResult<ProductPropertyItemDto> pageResult = productPropertyItemService
        .query(getPageIndex(vo), getPageSize(vo), vo);

    List<ProductPropertyItemDto> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      List<QueryProductPropertyItemBo> results = datas.stream().map(QueryProductPropertyItemBo::new)
          .collect(Collectors.toList());

      PageResultUtil.rebuild(pageResult, results);
    }

    return InvokeResultBuilder.success(pageResult);
  }

  /**
   * 查询属性值
   */
  @PreAuthorize("@permission.valid('base-data:product:property-item:query','base-data:product:property-item:add','base-data:product:property-item:modify')")
  @GetMapping
  public InvokeResult get(@NotBlank(message = "ID不能为空！") String id) {

    ProductPropertyItemDto data = productPropertyItemService.getById(id);
    if (data == null) {
      throw new DefaultClientException("属性值不存在！");
    }

    GetProductPropertyItemBo result = new GetProductPropertyItemBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增属性值
   */
  @PreAuthorize("@permission.valid('base-data:product:property-item:add')")
  @PostMapping
  public InvokeResult create(@Valid CreateProductPropertyItemVo vo) {

    productPropertyItemService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改属性值
   */
  @PreAuthorize("@permission.valid('base-data:product:property-item:modify')")
  @PutMapping
  public InvokeResult update(@Valid UpdateProductPropertyItemVo vo) {

    productPropertyItemService.update(vo);

    return InvokeResultBuilder.success();
  }
}
