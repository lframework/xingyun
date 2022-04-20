package com.lframework.xingyun.api.controller.basedata.product;

import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.basedata.service.product.IProductPolyService;
import com.lframework.xingyun.basedata.vo.product.poly.CreateProductPolyVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品聚合管理
 *
 * @author zmj
 */
@Api(tags = "商品聚合管理")
@Validated
@RestController
@RequestMapping("/basedata/product/poly")
public class ProductPolyController extends DefaultBaseController {

    @Autowired
    private IProductPolyService productPolyService;

    /**
     * 新增商品聚合
     */
    @ApiOperation("新增商品聚合")
    @PostMapping
    public InvokeResult<Void> create(@RequestBody CreateProductPolyVo vo) {

        vo.validate();

        productPolyService.create(vo);

        return InvokeResultBuilder.success();
    }
}
