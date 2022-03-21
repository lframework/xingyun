package com.lframework.xingyun.api.controller.retail;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.api.bo.retail.RetailProductBo;
import com.lframework.xingyun.basedata.dto.product.info.RetailProductDto;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.vo.product.info.QueryRetailProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 零售订单管理
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/retail/order")
public class RetailOrderController extends DefaultBaseController {

    @Autowired
    private IProductService productService;

    /**
     * 根据关键字查询商品
     */
    @PreAuthorize("@permission.valid('retail:out:add', 'retail:out:modify', 'retail:return:add', 'retail:return:modify')")
    @GetMapping("/product/search")
    public InvokeResult searchProducts(@NotBlank(message = "仓库ID不能为空！") String scId, String condition) {

        if (StringUtil.isBlank(condition)) {
            return InvokeResultBuilder.success(Collections.EMPTY_LIST);
        }

        PageResult<RetailProductDto> pageResult = productService
                .queryRetailByCondition(getPageIndex(), getPageSize(), condition);
        List<RetailProductBo> results = Collections.EMPTY_LIST;
        List<RetailProductDto> datas = pageResult.getDatas();
        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(t -> new RetailProductBo(scId, t)).collect(Collectors.toList());
        }

        return InvokeResultBuilder.success(results);
    }

    /**
     * 查询商品列表
     */
    @PreAuthorize("@permission.valid('retail:out:add', 'retail:out:modify', 'retail:return:add', 'retail:return:modify')")
    @GetMapping("/product/list")
    public InvokeResult queryProductList(@Valid QueryRetailProductVo vo) {

        PageResult<RetailProductDto> pageResult = productService.queryRetailList(getPageIndex(), getPageSize(), vo);
        List<RetailProductBo> results = Collections.EMPTY_LIST;
        List<RetailProductDto> datas = pageResult.getDatas();
        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(t -> new RetailProductBo(vo.getScId(), t)).collect(Collectors.toList());

            PageResultUtil.rebuild(pageResult, results);
        }

        return InvokeResultBuilder.success(pageResult);
    }
}
