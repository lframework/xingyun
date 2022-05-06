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
import com.lframework.xingyun.basedata.dto.product.property.ProductPropertyModelorDto;
import com.lframework.xingyun.basedata.entity.ProductProperty;
import com.lframework.xingyun.basedata.service.product.IProductPropertyService;
import com.lframework.xingyun.basedata.vo.product.property.CreateProductPropertyVo;
import com.lframework.xingyun.basedata.vo.product.property.QueryProductPropertyVo;
import com.lframework.xingyun.basedata.vo.product.property.UpdateProductPropertyVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = "属性管理")
@Validated
@RestController
@RequestMapping("/basedata/product/property")
public class ProductPropertyController extends DefaultBaseController {

    @Autowired
    private IProductPropertyService productPropertyService;

    /**
     * 属性列表
     */
    @ApiOperation("属性列表")
    @PreAuthorize("@permission.valid('base-data:product:property:query','base-data:product:property:add','base-data:product:property:modify')")
    @GetMapping("/query")
    public InvokeResult<PageResult<QueryProductPropertyBo>> query(@Valid QueryProductPropertyVo vo) {

        PageResult<ProductProperty> pageResult = productPropertyService.query(getPageIndex(vo), getPageSize(vo), vo);

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
    @PreAuthorize("@permission.valid('base-data:product:property:query','base-data:product:property:add','base-data:product:property:modify')")
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
    @PreAuthorize("@permission.valid('base-data:product:property:modify')")
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
    @PreAuthorize("@permission.valid('base-data:product:property:modify')")
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
    @PreAuthorize("@permission.valid('base-data:product:property:add')")
    @PostMapping
    public InvokeResult<Void> create(@Valid CreateProductPropertyVo vo) {

        productPropertyService.create(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 修改属性
     */
    @ApiOperation("修改属性")
    @PreAuthorize("@permission.valid('base-data:product:property:modify')")
    @PutMapping
    public InvokeResult<Void> update(@Valid UpdateProductPropertyVo vo) {

        productPropertyService.update(vo);

        productPropertyService.cleanCacheByKey(vo.getId());

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

        List<ProductPropertyModelorDto> datas = productPropertyService.getModelorByCategoryId(categoryId);

        List<ProductPropertyModelorBo> results = Collections.EMPTY_LIST;
        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(ProductPropertyModelorBo::new).collect(Collectors.toList());
        }

        return InvokeResultBuilder.success(results);
    }
}
