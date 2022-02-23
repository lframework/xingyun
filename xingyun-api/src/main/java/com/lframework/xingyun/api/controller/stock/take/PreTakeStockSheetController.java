package com.lframework.xingyun.api.controller.stock.take;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.api.bo.stock.take.pre.GetPreTakeStockSheetBo;
import com.lframework.xingyun.api.bo.stock.take.pre.PreTakeStockProductBo;
import com.lframework.xingyun.api.bo.stock.take.pre.QueryPreTakeStockSheetBo;
import com.lframework.xingyun.basedata.dto.product.info.PreTakeStockProductDto;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.vo.product.info.QueryPreTakeStockProductVo;
import com.lframework.xingyun.sc.dto.stock.take.pre.PreTakeStockSheetDto;
import com.lframework.xingyun.sc.dto.stock.take.pre.PreTakeStockSheetFullDto;
import com.lframework.xingyun.sc.service.stock.take.IPreTakeStockSheetService;
import com.lframework.xingyun.sc.vo.stock.take.pre.CreatePreTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.pre.QueryPreTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.pre.UpdatePreTakeStockSheetVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 预先盘点单 Controller
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/stock/take/pre")
public class PreTakeStockSheetController extends DefaultBaseController {

    @Autowired
    private IPreTakeStockSheetService preTakeStockSheetService;

    @Autowired
    private IProductService productService;

    /**
     * 查询列表
     */
    @PreAuthorize("@permission.valid('stock:take:pre:query','stock:take:pre:add','stock:take:pre:modify')")
    @GetMapping("/query")
    public InvokeResult query(@Valid QueryPreTakeStockSheetVo vo) {

        PageResult<PreTakeStockSheetDto> pageResult = preTakeStockSheetService.query(getPageIndex(vo), getPageSize(vo), vo);

        List<PreTakeStockSheetDto> datas = pageResult.getDatas();

        if (!CollectionUtil.isEmpty(datas)) {
            List<QueryPreTakeStockSheetBo> results = datas.stream().map(QueryPreTakeStockSheetBo::new).collect(Collectors.toList());

            PageResultUtil.rebuild(pageResult, results);
        }

        return InvokeResultBuilder.success(pageResult);
    }

    /**
     * 根据关键字查询商品列表
     */
    @PreAuthorize("@permission.valid('stock:take:pre:add', 'stock:take:pre:modify')")
    @GetMapping("/product/search")
    public InvokeResult searchProducts(String condition) {
        if (StringUtil.isBlank(condition)) {
            return InvokeResultBuilder.success(Collections.EMPTY_LIST);
        }
        PageResult<PreTakeStockProductDto> pageResult = productService.queryPreTakeStockByCondition(getPageIndex(), getPageSize(), condition);
        List<PreTakeStockProductBo> results = Collections.EMPTY_LIST;
        List<PreTakeStockProductDto> datas = pageResult.getDatas();
        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(PreTakeStockProductBo::new).collect(Collectors.toList());
        }

        return InvokeResultBuilder.success(results);
    }

    /**
     * 查询商品列表
     */
    @PreAuthorize("@permission.valid('stock:take:pre:add', 'stock:take:pre:modify')")
    @GetMapping("/product/list")
    public InvokeResult queryProductList(@Valid QueryPreTakeStockProductVo vo) {

        PageResult<PreTakeStockProductDto> pageResult = productService.queryPreTakeStockList(getPageIndex(), getPageSize(), vo);
        List<PreTakeStockProductBo> results = Collections.EMPTY_LIST;
        List<PreTakeStockProductDto> datas = pageResult.getDatas();
        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(PreTakeStockProductBo::new).collect(Collectors.toList());

            PageResultUtil.rebuild(pageResult, results);
        }

        return InvokeResultBuilder.success(pageResult);
    }

    /**
     * 根据ID查询
     */
    @PreAuthorize("@permission.valid('stock:take:pre:query','stock:take:pre:add','stock:take:pre:modify')")
    @GetMapping
    public InvokeResult getDetail(@NotBlank(message = "id不能为空！") String id) {

        PreTakeStockSheetFullDto data = preTakeStockSheetService.getDetail(id);
        if (data == null) {
            throw new DefaultClientException("预先盘点单不存在！");
        }

        GetPreTakeStockSheetBo result = new GetPreTakeStockSheetBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 新增
     */
    @PreAuthorize("@permission.valid('stock:take:pre:add')")
    @PostMapping
    public InvokeResult create(@Valid @RequestBody CreatePreTakeStockSheetVo vo) {

        vo.validate();

        preTakeStockSheetService.create(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 修改
     */
    @PreAuthorize("@permission.valid('stock:take:pre:modify')")
    @PutMapping
    public InvokeResult update(@Valid @RequestBody UpdatePreTakeStockSheetVo vo) {

        vo.validate();

        preTakeStockSheetService.update(vo);

        return InvokeResultBuilder.success();
    }
}
