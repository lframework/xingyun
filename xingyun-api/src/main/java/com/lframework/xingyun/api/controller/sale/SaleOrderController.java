package com.lframework.xingyun.api.controller.sale;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.api.bo.sale.*;
import com.lframework.xingyun.api.model.sale.SaleOrderExportModel;
import com.lframework.xingyun.basedata.dto.product.info.SaleProductDto;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.vo.product.info.QuerySaleProductVo;
import com.lframework.xingyun.sc.dto.sale.SaleOrderDto;
import com.lframework.xingyun.sc.dto.sale.SaleOrderFullDto;
import com.lframework.xingyun.sc.dto.sale.SaleOrderWithOutDto;
import com.lframework.xingyun.sc.service.sale.ISaleOrderService;
import com.lframework.xingyun.sc.vo.sale.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 销售订单管理
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/sale/order")
public class SaleOrderController extends DefaultBaseController {

    @Autowired
    private ISaleOrderService saleOrderService;

    @Autowired
    private IProductService productService;

    /**
     * 订单列表
     */
    @PreAuthorize("@permission.valid('sale:order:query')")
    @GetMapping("/query")
    public InvokeResult query(@Valid QuerySaleOrderVo vo) {

        PageResult<SaleOrderDto> pageResult = saleOrderService.query(getPageIndex(vo), getPageSize(vo), vo);

        List<SaleOrderDto> datas = pageResult.getDatas();

        if (!CollectionUtil.isEmpty(datas)) {
            List<QuerySaleOrderBo> results = datas.stream().map(QuerySaleOrderBo::new).collect(Collectors.toList());

            PageResultUtil.rebuild(pageResult, results);
        }

        return InvokeResultBuilder.success(pageResult);
    }

    @PreAuthorize("@permission.valid('sale:order:export')")
    @PostMapping("/export")
    public void export(@Valid QuerySaleOrderVo vo) {

        ExcelMultipartWriterSheetBuilder builder = ExcelUtil.multipartExportXls("销售单信息", SaleOrderExportModel.class);

        try {
            int pageIndex = 1;
            while (true) {
                PageResult<SaleOrderDto> pageResult = saleOrderService.query(pageIndex, getExportSize(), vo);
                List<SaleOrderDto> datas = pageResult.getDatas();
                List<SaleOrderExportModel> models = datas.stream().map(SaleOrderExportModel::new)
                        .collect(Collectors.toList());
                builder.doWrite(models);

                if (!pageResult.isHasNext()) {
                    break;
                }
                pageIndex++;
            }
        } finally {
            builder.finish();
        }
    }

    /**
     * 根据关键字查询商品
     */
    @PreAuthorize("@permission.valid('sale:order:add', 'sale:order:modify', 'sale:out:add', 'sale:out:modify', 'sale:return:add', 'sale:return:modify')")
    @GetMapping("/product/search")
    public InvokeResult searchProducts(@NotBlank(message = "仓库ID不能为空！") String scId, String condition) {

        if (StringUtil.isBlank(condition)) {
            return InvokeResultBuilder.success(Collections.EMPTY_LIST);
        }

        PageResult<SaleProductDto> pageResult = productService
                .querySaleByCondition(getPageIndex(), getPageSize(), condition);
        List<SaleProductBo> results = Collections.EMPTY_LIST;
        List<SaleProductDto> datas = pageResult.getDatas();
        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(t -> new SaleProductBo(scId, t)).collect(Collectors.toList());
        }

        return InvokeResultBuilder.success(results);
    }

    /**
     * 查询商品列表
     */
    @PreAuthorize("@permission.valid('sale:order:add', 'sale:order:modify', 'sale:out:add', 'sale:out:modify', 'sale:return:add', 'sale:return:modify')")
    @GetMapping("/product/list")
    public InvokeResult queryProductList(@Valid QuerySaleProductVo vo) {

        PageResult<SaleProductDto> pageResult = productService.querySaleList(getPageIndex(), getPageSize(), vo);
        List<SaleProductBo> results = Collections.EMPTY_LIST;
        List<SaleProductDto> datas = pageResult.getDatas();
        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(t -> new SaleProductBo(vo.getScId(), t)).collect(Collectors.toList());

            PageResultUtil.rebuild(pageResult, results);
        }

        return InvokeResultBuilder.success(pageResult);
    }

    /**
     * 根据ID查询
     */
    @PreAuthorize("@permission.valid('sale:order:query')")
    @GetMapping
    public InvokeResult getById(@NotBlank(message = "订单ID不能为空！") String id) {

        SaleOrderFullDto data = saleOrderService.getDetail(id);

        GetSaleOrderBo result = new GetSaleOrderBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 根据ID查询（出库业务）
     */
    @PreAuthorize("@permission.valid('sale:out:add', 'sale:out:modify')")
    @GetMapping("/out")
    public InvokeResult getWithOut(@NotBlank(message = "订单ID不能为空！") String id) {

        SaleOrderWithOutDto data = saleOrderService.getWithOut(id);
        SaleOrderWithOutBo result = new SaleOrderWithOutBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 查询列表（出库业务）
     */
    @PreAuthorize("@permission.valid('sale:out:add', 'sale:out:modify')")
    @GetMapping("/query/out")
    public InvokeResult getWithOut(@Valid QuerySaleOrderWithOutVo vo) {

        PageResult<SaleOrderDto> pageResult = saleOrderService.queryWithOut(getPageIndex(vo), getPageSize(vo), vo);
        List<SaleOrderDto> datas = pageResult.getDatas();

        List<QuerySaleOrderWithOutBo> results = Collections.EMPTY_LIST;

        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(QuerySaleOrderWithOutBo::new).collect(Collectors.toList());
            PageResultUtil.rebuild(pageResult, results);
        }

        return InvokeResultBuilder.success(pageResult);
    }

    /**
     * 创建订单
     */
    @PreAuthorize("@permission.valid('sale:order:add')")
    @PostMapping
    public InvokeResult create(@RequestBody @Valid CreateSaleOrderVo vo) {

        vo.validate();

        String id = saleOrderService.create(vo);

        return InvokeResultBuilder.success(id);
    }

    /**
     * 修改订单
     */
    @PreAuthorize("@permission.valid('sale:order:modify')")
    @PutMapping
    public InvokeResult update(@RequestBody @Valid UpdateSaleOrderVo vo) {

        vo.validate();

        saleOrderService.update(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 审核通过订单
     */
    @PreAuthorize("@permission.valid('sale:order:approve')")
    @PatchMapping("/approve/pass")
    public InvokeResult approvePass(@RequestBody @Valid ApprovePassSaleOrderVo vo) {

        saleOrderService.approvePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量审核通过订单
     */
    @PreAuthorize("@permission.valid('sale:order:approve')")
    @PatchMapping("/approve/pass/batch")
    public InvokeResult batchApprovePass(@RequestBody @Valid BatchApprovePassSaleOrderVo vo) {

        saleOrderService.batchApprovePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 直接审核通过订单
     */
    @PreAuthorize("@permission.valid('sale:order:approve')")
    @PostMapping("/approve/pass/direct")
    public InvokeResult directApprovePass(@RequestBody @Valid CreateSaleOrderVo vo) {

        saleOrderService.directApprovePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 审核拒绝订单
     */
    @PreAuthorize("@permission.valid('sale:order:approve')")
    @PatchMapping("/approve/refuse")
    public InvokeResult approveRefuse(@RequestBody @Valid ApproveRefuseSaleOrderVo vo) {

        saleOrderService.approveRefuse(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量审核拒绝订单
     */
    @PreAuthorize("@permission.valid('sale:order:approve')")
    @PatchMapping("/approve/refuse/batch")
    public InvokeResult batchApproveRefuse(@RequestBody @Valid BatchApproveRefuseSaleOrderVo vo) {

        saleOrderService.batchApproveRefuse(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 删除订单
     */
    @PreAuthorize("@permission.valid('sale:order:delete')")
    @DeleteMapping
    public InvokeResult deleteById(@NotBlank(message = "订单ID不能为空！") String id) {

        saleOrderService.deleteById(id);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量删除订单
     */
    @PreAuthorize("@permission.valid('sale:order:delete')")
    @DeleteMapping("/batch")
    public InvokeResult deleteByIds(@RequestBody @NotEmpty(message = "请选择需要删除的订单！") List<String> ids) {

        saleOrderService.deleteByIds(ids);

        return InvokeResultBuilder.success();
    }
}
