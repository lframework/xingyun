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
import com.lframework.xingyun.basedata.entity.ProductPropertyItem;
import com.lframework.xingyun.basedata.service.product.IProductPropertyItemService;
import com.lframework.xingyun.basedata.vo.product.property.item.CreateProductPropertyItemVo;
import com.lframework.xingyun.basedata.vo.product.property.item.QueryProductPropertyItemVo;
import com.lframework.xingyun.basedata.vo.product.property.item.UpdateProductPropertyItemVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

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
    private IProductPropertyItemService productPropertyItemService;

    /**
     * 属性值列表
     */
    @ApiOperation("属性值列表")
    @PreAuthorize("@permission.valid('base-data:product:property-item:query','base-data:product:property-item:add','base-data:product:property-item:modify')")
    @GetMapping("/query")
    public InvokeResult<PageResult<QueryProductPropertyItemBo>> query(@Valid QueryProductPropertyItemVo vo) {

        PageResult<ProductPropertyItem> pageResult = productPropertyItemService.query(getPageIndex(vo), getPageSize(vo),
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
    @PreAuthorize("@permission.valid('base-data:product:property-item:query','base-data:product:property-item:add','base-data:product:property-item:modify')")
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
    @PreAuthorize("@permission.valid('base-data:product:property-item:add')")
    @PostMapping
    public InvokeResult<Void> create(@Valid CreateProductPropertyItemVo vo) {

        productPropertyItemService.create(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 修改属性值
     */
    @ApiOperation("修改属性值")
    @PreAuthorize("@permission.valid('base-data:product:property-item:modify')")
    @PutMapping
    public InvokeResult<Void> update(@Valid UpdateProductPropertyItemVo vo) {

        productPropertyItemService.update(vo);

        return InvokeResultBuilder.success();
    }
}
