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
import com.lframework.xingyun.basedata.dto.product.saleprop.ProductSalePropGroupDto;
import com.lframework.xingyun.basedata.service.product.IProductSalePropGroupService;
import com.lframework.xingyun.basedata.vo.product.saleprop.CreateProductSalePropGroupVo;
import com.lframework.xingyun.basedata.vo.product.saleprop.QueryProductSalePropGroupVo;
import com.lframework.xingyun.basedata.vo.product.saleprop.UpdateProductSalePropGroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 销售属性组管理
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/basedata/product/saleprop/group")
public class ProductSalePropGroupController extends DefaultBaseController {

    @Autowired
    private IProductSalePropGroupService productSalePropGroupService;

    /**
     * 销售属性组列表
     */
    @PreAuthorize("@permission.valid('base-data:product:saleprop-group:query','base-data:product:saleprop-group:add','base-data:product:saleprop-group:modify')")
    @GetMapping("/query")
    public InvokeResult query(@Valid QueryProductSalePropGroupVo vo) {

        PageResult<ProductSalePropGroupDto> pageResult = productSalePropGroupService
                .query(getPageIndex(vo), getPageSize(vo), vo);

        List<ProductSalePropGroupDto> datas = pageResult.getDatas();

        if (!CollectionUtil.isEmpty(datas)) {
            List<QueryProductSalePropGroupBo> results = datas.stream().map(QueryProductSalePropGroupBo::new)
                    .collect(Collectors.toList());

            PageResultUtil.rebuild(pageResult, results);
        }

        return InvokeResultBuilder.success(pageResult);
    }

    /**
     * 查询销售属性组
     */
    @PreAuthorize("@permission.valid('base-data:product:saleprop-group:query','base-data:product:saleprop-group:add','base-data:product:saleprop-group:modify')")
    @GetMapping
    public InvokeResult get(@NotBlank(message = "ID不能为空！") String id) {

        ProductSalePropGroupDto data = productSalePropGroupService.getById(id);
        if (data == null) {
            throw new DefaultClientException("销售属性组不存在！");
        }

        GetProductSalePropGroupBo result = new GetProductSalePropGroupBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 批量停用销售属性组
     */
    @PreAuthorize("@permission.valid('base-data:product:saleprop-group:modify')")
    @PatchMapping("/unable/batch")
    public InvokeResult batchUnable(@NotEmpty(message = "请选择需要停用的销售属性组！") @RequestBody List<String> ids) {

        productSalePropGroupService.batchUnable(ids);
        return InvokeResultBuilder.success();
    }

    /**
     * 批量启用销售属性组
     */
    @PreAuthorize("@permission.valid('base-data:product:saleprop-group:modify')")
    @PatchMapping("/enable/batch")
    public InvokeResult batchEnable(@NotEmpty(message = "请选择需要启用的销售属性组！") @RequestBody List<String> ids) {

        productSalePropGroupService.batchEnable(ids);
        return InvokeResultBuilder.success();
    }

    /**
     * 新增销售属性组
     */
    @PreAuthorize("@permission.valid('base-data:product:saleprop-group:add')")
    @PostMapping
    public InvokeResult create(@Valid CreateProductSalePropGroupVo vo) {

        productSalePropGroupService.create(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 修改销售属性组
     */
    @PreAuthorize("@permission.valid('base-data:product:saleprop-group:modify')")
    @PutMapping
    public InvokeResult update(@Valid UpdateProductSalePropGroupVo vo) {

        productSalePropGroupService.update(vo);

        return InvokeResultBuilder.success();
    }
}
