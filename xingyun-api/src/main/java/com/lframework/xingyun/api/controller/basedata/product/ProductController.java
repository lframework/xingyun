package com.lframework.xingyun.api.controller.basedata.product;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.api.bo.basedata.product.info.GetProductBo;
import com.lframework.xingyun.api.bo.basedata.product.info.QueryProductBo;
import com.lframework.xingyun.basedata.dto.product.info.GetProductDto;
import com.lframework.xingyun.basedata.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.vo.product.info.QueryProductVo;
import com.lframework.xingyun.basedata.vo.product.info.UpdateProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品管理
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/basedata/product")
public class ProductController extends DefaultBaseController {

    @Autowired
    private IProductService productService;

    /**
     * 商品列表
     */
    @PreAuthorize("@permission.valid('base-data:product:info:query','base-data:product:info:add','base-data:product:info:modify')")
    @GetMapping("/query")
    public InvokeResult query(@Valid QueryProductVo vo) {

        PageResult<ProductDto> pageResult = productService.query(getPageIndex(vo), getPageSize(vo), vo);

        List<ProductDto> datas = pageResult.getDatas();

        if (!CollectionUtil.isEmpty(datas)) {
            List<QueryProductBo> results = datas.stream().map(QueryProductBo::new).collect(Collectors.toList());

            PageResultUtil.rebuild(pageResult, results);
        }

        return InvokeResultBuilder.success(pageResult);
    }

    /**
     * 商品详情
     */
    @PreAuthorize("@permission.valid('base-data:product:info:query','base-data:product:info:add','base-data:product:info:modify')")
    @GetMapping
    public InvokeResult get(@NotBlank(message = "ID不能为空！") String id) {

        GetProductDto data = productService.getDetailById(id);

        GetProductBo result = new GetProductBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 修改商品
     */
    @PreAuthorize("@permission.valid('base-data:product:info:modify')")
    @PutMapping
    public InvokeResult update(@Valid UpdateProductVo vo) {

        productService.update(vo);

        return InvokeResultBuilder.success();
    }
}
