package com.lframework.xingyun.api.controller.basedata.product;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.api.bo.basedata.product.saleprop.GetProductSalePropGroupBo;
import com.lframework.xingyun.api.bo.basedata.product.saleprop.QueryProductSalePropGroupBo;
import com.lframework.xingyun.basedata.entity.ProductSalePropGroup;
import com.lframework.xingyun.basedata.service.product.IProductSalePropGroupService;
import com.lframework.xingyun.basedata.vo.product.saleprop.CreateProductSalePropGroupVo;
import com.lframework.xingyun.basedata.vo.product.saleprop.QueryProductSalePropGroupVo;
import com.lframework.xingyun.basedata.vo.product.saleprop.UpdateProductSalePropGroupVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
 * 销售属性组管理
 *
 * @author zmj
 */
@Api(tags = "销售属性组管理")
@Validated
@RestController
@RequestMapping("/basedata/product/saleprop/group")
public class ProductSalePropGroupController extends DefaultBaseController {

    @Autowired
    private IProductSalePropGroupService productSalePropGroupService;

    /**
     * 销售属性组列表
     */
    @ApiOperation("销售属性组列表")
    @PreAuthorize("@permission.valid('base-data:product:saleprop-group:query','base-data:product:saleprop-group:add','base-data:product:saleprop-group:modify')")
    @GetMapping("/query")
    public InvokeResult<PageResult<QueryProductSalePropGroupBo>> query(@Valid QueryProductSalePropGroupVo vo) {

        PageResult<ProductSalePropGroup> pageResult = productSalePropGroupService.query(getPageIndex(vo),
                getPageSize(vo), vo);

        List<ProductSalePropGroup> datas = pageResult.getDatas();
        List<QueryProductSalePropGroupBo> results = null;

        if (!CollectionUtil.isEmpty(datas)) {

            results = datas.stream().map(QueryProductSalePropGroupBo::new).collect(Collectors.toList());
        }

        return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
    }

    /**
     * 查询销售属性组
     */
    @ApiOperation("查询销售属性组")
    @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
    @PreAuthorize("@permission.valid('base-data:product:saleprop-group:query','base-data:product:saleprop-group:add','base-data:product:saleprop-group:modify')")
    @GetMapping
    public InvokeResult<GetProductSalePropGroupBo> get(@NotBlank(message = "ID不能为空！") String id) {

        ProductSalePropGroup data = productSalePropGroupService.findById(id);
        if (data == null) {
            throw new DefaultClientException("销售属性组不存在！");
        }

        GetProductSalePropGroupBo result = new GetProductSalePropGroupBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 批量停用销售属性组
     */
    @ApiOperation("批量停用销售属性组")
    @PreAuthorize("@permission.valid('base-data:product:saleprop-group:modify')")
    @PatchMapping("/unable/batch")
    public InvokeResult<Void> batchUnable(
            @ApiParam(value = "ID", required = true) @NotEmpty(message = "请选择需要停用的销售属性组！") @RequestBody List<String> ids) {

        productSalePropGroupService.batchUnable(ids);

        for (String id : ids) {
            productSalePropGroupService.cleanCacheByKey(id);
        }

        return InvokeResultBuilder.success();
    }

    /**
     * 批量启用销售属性组
     */
    @ApiOperation("批量启用销售属性组")
    @PreAuthorize("@permission.valid('base-data:product:saleprop-group:modify')")
    @PatchMapping("/enable/batch")
    public InvokeResult<Void> batchEnable(
            @ApiParam(value = "ID", required = true) @NotEmpty(message = "请选择需要启用的销售属性组！") @RequestBody List<String> ids) {

        productSalePropGroupService.batchEnable(ids);

        for (String id : ids) {
            productSalePropGroupService.cleanCacheByKey(id);
        }

        return InvokeResultBuilder.success();
    }

    /**
     * 新增销售属性组
     */
    @ApiOperation("新增销售属性组")
    @PreAuthorize("@permission.valid('base-data:product:saleprop-group:add')")
    @PostMapping
    public InvokeResult<Void> create(@Valid CreateProductSalePropGroupVo vo) {

        productSalePropGroupService.create(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 修改销售属性组
     */
    @ApiOperation("修改销售属性组")
    @PreAuthorize("@permission.valid('base-data:product:saleprop-group:modify')")
    @PutMapping
    public InvokeResult<Void> update(@Valid UpdateProductSalePropGroupVo vo) {

        productSalePropGroupService.update(vo);

        productSalePropGroupService.cleanCacheByKey(vo.getId());

        return InvokeResultBuilder.success();
    }
}
