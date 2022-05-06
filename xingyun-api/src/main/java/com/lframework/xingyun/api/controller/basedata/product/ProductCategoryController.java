package com.lframework.xingyun.api.controller.basedata.product;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.service.system.IRecursionMappingService;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.api.bo.basedata.product.category.GetProductCategoryBo;
import com.lframework.xingyun.api.bo.basedata.product.category.ProductCategoryTreeBo;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.enums.ProductCategoryNodeType;
import com.lframework.xingyun.basedata.service.product.IProductCategoryService;
import com.lframework.xingyun.basedata.vo.product.category.CreateProductCategoryVo;
import com.lframework.xingyun.basedata.vo.product.category.UpdateProductCategoryVo;
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
 * 类目管理
 *
 * @author zmj
 */
@Api(tags = "类目管理")
@Validated
@RestController
@RequestMapping("/basedata/product/category")
public class ProductCategoryController extends DefaultBaseController {

    @Autowired
    private IProductCategoryService productCategoryService;

    @Autowired
    private IRecursionMappingService recursionMappingService;

    /**
     * 类目列表
     */
    @ApiOperation("类目列表")
    @PreAuthorize("@permission.valid('base-data:product:category:query','base-data:product:category:add','base-data:product:category:modify')")
    @GetMapping("/query")
    public InvokeResult<List<ProductCategoryTreeBo>> query() {

        List<ProductCategory> datas = productCategoryService.getAllProductCategories();
        if (CollectionUtil.isEmpty(datas)) {
            return InvokeResultBuilder.success(Collections.EMPTY_LIST);
        }

        List<ProductCategoryTreeBo> results = datas.stream().map(ProductCategoryTreeBo::new)
                .collect(Collectors.toList());

        return InvokeResultBuilder.success(results);
    }

    /**
     * 查询类目
     */
    @ApiOperation("查询类目")
    @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
    @PreAuthorize("@permission.valid('base-data:product:category:query','base-data:product:category:add','base-data:product:category:modify')")
    @GetMapping
    public InvokeResult<GetProductCategoryBo> get(@NotBlank(message = "ID不能为空！") String id) {

        ProductCategory data = productCategoryService.findById(id);
        if (data == null) {
            throw new DefaultClientException("类目不存在！");
        }

        GetProductCategoryBo result = new GetProductCategoryBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 批量停用类目
     */
    @ApiOperation("批量停用类目")
    @PreAuthorize("@permission.valid('base-data:product:category:modify')")
    @PatchMapping("/unable/batch")
    public InvokeResult<Void> batchUnable(
            @ApiParam(value = "ID", required = true) @NotEmpty(message = "请选择需要停用的类目！") @RequestBody List<String> ids) {

        productCategoryService.batchUnable(ids);
        return InvokeResultBuilder.success();
    }

    /**
     * 批量启用类目
     */
    @ApiOperation("批量启用类目")
    @PreAuthorize("@permission.valid('base-data:product:category:modify')")
    @PatchMapping("/enable/batch")
    public InvokeResult<Void> batchEnable(
            @ApiParam(value = "ID", required = true) @NotEmpty(message = "请选择需要启用的类目！") @RequestBody List<String> ids) {

        productCategoryService.batchEnable(ids);

        for (String id : ids) {
            productCategoryService.cleanCacheByKey(id);
        }

        return InvokeResultBuilder.success();
    }

    /**
     * 新增类目
     */
    @ApiOperation("新增类目")
    @PreAuthorize("@permission.valid('base-data:product:category:add')")
    @PostMapping
    public InvokeResult<Void> create(@Valid CreateProductCategoryVo vo) {

        productCategoryService.create(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 修改类目
     */
    @ApiOperation("修改类目")
    @PreAuthorize("@permission.valid('base-data:product:category:modify')")
    @PutMapping
    public InvokeResult<Void> update(@Valid UpdateProductCategoryVo vo) {

        productCategoryService.update(vo);

        productCategoryService.cleanCacheByKey(vo.getId());

        ProductCategory data = productCategoryService.findById(vo.getId());
        if (!vo.getAvailable()) {
            if (data.getAvailable()) {
                //如果是停用 子节点全部停用
                List<String> childrenIds = recursionMappingService.getNodeChildIds(data.getId(),
                    ApplicationUtil.getBean(ProductCategoryNodeType.class));
                if (!CollectionUtil.isEmpty(childrenIds)) {
                    for (String childrenId : childrenIds) {
                        productCategoryService.cleanCacheByKey(childrenId);
                    }
                }
            }
        } else {
            if (!data.getAvailable()) {
                //如果是启用 父节点全部启用
                List<String> parentIds = recursionMappingService.getNodeParentIds(data.getId(),
                    ApplicationUtil.getBean(ProductCategoryNodeType.class));
                if (!CollectionUtil.isEmpty(parentIds)) {
                    for (String parentId : parentIds) {
                        productCategoryService.cleanCacheByKey(parentId);
                    }
                }
            }
        }

        return InvokeResultBuilder.success();
    }
}
