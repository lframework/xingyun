package com.lframework.xingyun.api.controller.basedata.product;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.api.bo.basedata.product.poly.GetProductPolyBo;
import com.lframework.xingyun.api.bo.basedata.product.poly.QueryProductPolyBo;
import com.lframework.xingyun.basedata.dto.product.poly.ProductPolyDto;
import com.lframework.xingyun.basedata.entity.ProductPoly;
import com.lframework.xingyun.basedata.service.product.IProductPolyPropertyService;
import com.lframework.xingyun.basedata.service.product.IProductPolyService;
import com.lframework.xingyun.basedata.vo.product.poly.CreateProductPolyVo;
import com.lframework.xingyun.basedata.vo.product.poly.QueryProductPolyVo;
import com.lframework.xingyun.basedata.vo.product.poly.UpdateProductPolyVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品SPU管理
 *
 * @author zmj
 */
@Api(tags = "商品SPU管理")
@Validated
@RestController
@RequestMapping("/basedata/product/poly")
public class ProductPolyController extends DefaultBaseController {

    @Autowired
    private IProductPolyService productPolyService;

    private IProductPolyPropertyService productPolyPropertyService;

    /**
     * 查询列表
     */
    @ApiOperation("查询列表")
    @PreAuthorize("@permission.valid('base-data:product:poly:query')")
    @GetMapping("/query")
    public InvokeResult<PageResult<QueryProductPolyBo>> query(@Valid QueryProductPolyVo vo) {

        PageResult<ProductPoly> pageResult = productPolyService.query(getPageIndex(vo), getPageSize(vo), vo);

        List<ProductPoly> datas = pageResult.getDatas();
        List<QueryProductPolyBo> results = null;

        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(QueryProductPolyBo::new).collect(Collectors.toList());
        }

        return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
    }

    /**
     * 新增商品聚合
     */
    @ApiOperation("新增")
    @PreAuthorize("@permission.valid('base-data:product:info:modify', 'base-data:product:poly:modify')")
    @PostMapping
    public InvokeResult<Void> create(@RequestBody CreateProductPolyVo vo) {

        vo.validate();

        productPolyService.create(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 根据ID查询
     */
    @ApiOperation("根据ID查询")
    @ApiImplicitParam(value = "id", name = "id", paramType = "query", required = true)
    @PreAuthorize("@permission.valid('base-data:product:poly:query')")
    @GetMapping
    public InvokeResult<GetProductPolyBo> get(@NotBlank(message = "id不能为空！") String id) {

        ProductPolyDto data = productPolyService.findById(id);
        if (data == null) {
            throw new DefaultClientException("商品SPU不存在！");
        }

        GetProductPolyBo result = new GetProductPolyBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PreAuthorize("@permission.valid('base-data:product:poly:modify')")
    @PutMapping
    public InvokeResult<Void> update(@Valid @RequestBody UpdateProductPolyVo vo) {

        productPolyService.update(vo);

        productPolyService.cleanCacheByKey(vo.getId());

        productPolyPropertyService.cleanCacheByKey(vo.getId());

        return InvokeResultBuilder.success();
    }
}
