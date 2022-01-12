package com.lframework.xingyun.api.controller.basedata.product;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.api.bo.basedata.product.brand.GetProductBrandBo;
import com.lframework.xingyun.api.bo.basedata.product.brand.QueryProductBrandBo;
import com.lframework.xingyun.basedata.dto.product.brand.ProductBrandDto;
import com.lframework.xingyun.basedata.service.product.IProductBrandService;
import com.lframework.xingyun.basedata.vo.product.brand.CreateProductBrandVo;
import com.lframework.xingyun.basedata.vo.product.brand.QueryProductBrandVo;
import com.lframework.xingyun.basedata.vo.product.brand.UpdateProductBrandVo;
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
 * 品牌管理
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/basedata/product/brand")
public class ProductBrandController extends DefaultBaseController {

    @Autowired
    private IProductBrandService productBrandService;

    /**
     * 品牌列表
     */
    @PreAuthorize("@permission.valid('base-data:product:brand:query','base-data:product:brand:add','base-data:product:brand:modify')")
    @GetMapping("/query")
    public InvokeResult query(@Valid QueryProductBrandVo vo) {

        PageResult<ProductBrandDto> pageResult = productBrandService.query(getPageIndex(vo), getPageSize(vo), vo);

        List<ProductBrandDto> datas = pageResult.getDatas();

        if (!CollectionUtil.isEmpty(datas)) {
            List<QueryProductBrandBo> results = datas.stream().map(QueryProductBrandBo::new)
                    .collect(Collectors.toList());

            PageResultUtil.rebuild(pageResult, results);
        }

        return InvokeResultBuilder.success(pageResult);
    }


    /**
     * 查询品牌
     */
    @PreAuthorize("@permission.valid('base-data:product:brand:query','base-data:product:brand:add','base-data:product:brand:modify')")
    @GetMapping
    public InvokeResult get(@NotBlank(message = "ID不能为空！") String id) {

        ProductBrandDto data = productBrandService.getById(id);
        if (data == null) {
            throw new DefaultClientException("品牌不存在！");
        }

        GetProductBrandBo result = new GetProductBrandBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 批量停用品牌
     */
    @PreAuthorize("@permission.valid('base-data:product:brand:modify')")
    @PatchMapping("/unable/batch")
    public InvokeResult batchUnable(@NotEmpty(message = "请选择需要停用的品牌！") @RequestBody List<String> ids) {

        productBrandService.batchUnable(ids);
        return InvokeResultBuilder.success();
    }

    /**
     * 批量启用品牌
     */
    @PreAuthorize("@permission.valid('base-data:product:brand:modify')")
    @PatchMapping("/enable/batch")
    public InvokeResult batchEnable(@NotEmpty(message = "请选择需要启用的品牌！") @RequestBody List<String> ids) {

        productBrandService.batchEnable(ids);
        return InvokeResultBuilder.success();
    }

    /**
     * 新增品牌
     */
    @PreAuthorize("@permission.valid('base-data:product:brand:add')")
    @PostMapping
    public InvokeResult create(@Valid CreateProductBrandVo vo) {

        productBrandService.create(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 修改品牌
     */
    @PreAuthorize("@permission.valid('base-data:product:brand:modify')")
    @PutMapping
    public InvokeResult update(@Valid UpdateProductBrandVo vo) {

        productBrandService.update(vo);

        return InvokeResultBuilder.success();
    }
}
