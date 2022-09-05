package com.lframework.xingyun.api.controller.sale;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.api.bo.sale.GetSaleOrderBo;
import com.lframework.xingyun.api.bo.sale.PrintSaleOrderBo;
import com.lframework.xingyun.api.bo.sale.QuerySaleOrderBo;
import com.lframework.xingyun.api.bo.sale.QuerySaleOrderWithOutBo;
import com.lframework.xingyun.api.bo.sale.SaleOrderWithOutBo;
import com.lframework.xingyun.api.excel.sale.SaleOrderExportModel;
import com.lframework.xingyun.api.print.A4ExcelPortraitPrintBo;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.sc.dto.sale.SaleOrderFullDto;
import com.lframework.xingyun.sc.dto.sale.SaleOrderWithOutDto;
import com.lframework.xingyun.sc.entity.SaleOrder;
import com.lframework.xingyun.sc.service.sale.ISaleOrderService;
import com.lframework.xingyun.sc.vo.sale.ApprovePassSaleOrderVo;
import com.lframework.xingyun.sc.vo.sale.ApproveRefuseSaleOrderVo;
import com.lframework.xingyun.sc.vo.sale.BatchApprovePassSaleOrderVo;
import com.lframework.xingyun.sc.vo.sale.BatchApproveRefuseSaleOrderVo;
import com.lframework.xingyun.sc.vo.sale.CreateSaleOrderVo;
import com.lframework.xingyun.sc.vo.sale.QuerySaleOrderVo;
import com.lframework.xingyun.sc.vo.sale.QuerySaleOrderWithOutVo;
import com.lframework.xingyun.sc.vo.sale.UpdateSaleOrderVo;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 销售订单管理
 *
 * @author zmj
 */
@Api(tags = "销售订单管理")
@Validated
@RestController
@RequestMapping("/sale/order")
public class SaleOrderController extends DefaultBaseController {

    @Autowired
    private ISaleOrderService saleOrderService;

    @Autowired
    private IProductService productService;

    /**
     * 打印
     */
    @ApiOperation("打印")
    @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
    @PreAuthorize("@permission.valid('sale:order:query')")
    @GetMapping("/print")
    public InvokeResult<A4ExcelPortraitPrintBo<PrintSaleOrderBo>> print(@NotBlank(message = "订单ID不能为空！") String id) {

        SaleOrderFullDto data = saleOrderService.getDetail(id);
        if (data == null) {
            throw new DefaultClientException("订单不存在！");
        }

        PrintSaleOrderBo result = new PrintSaleOrderBo(data);

        A4ExcelPortraitPrintBo<PrintSaleOrderBo> printResult = new A4ExcelPortraitPrintBo<>("print/sale-order.ftl",
                result);

        return InvokeResultBuilder.success(printResult);
    }

    /**
     * 订单列表
     */
    @ApiOperation("订单列表")
    @PreAuthorize("@permission.valid('sale:order:query')")
    @GetMapping("/query")
    public InvokeResult<PageResult<QuerySaleOrderBo>> query(@Valid QuerySaleOrderVo vo) {

        PageResult<SaleOrder> pageResult = saleOrderService.query(getPageIndex(vo), getPageSize(vo), vo);

        List<SaleOrder> datas = pageResult.getDatas();
        List<QuerySaleOrderBo> results = null;

        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(QuerySaleOrderBo::new).collect(Collectors.toList());
        }

        return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
    }

    /**
     * 导出
     */
    @ApiOperation("导出")
    @PreAuthorize("@permission.valid('sale:order:export')")
    @PostMapping("/export")
    public void export(@Valid QuerySaleOrderVo vo) {

        ExcelMultipartWriterSheetBuilder builder = ExcelUtil.multipartExportXls("销售单信息", SaleOrderExportModel.class);

        try {
            int pageIndex = 1;
            while (true) {
                PageResult<SaleOrder> pageResult = saleOrderService.query(pageIndex, getExportSize(), vo);
                List<SaleOrder> datas = pageResult.getDatas();
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
     * 根据ID查询
     */
    @ApiOperation("根据ID查询")
    @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
    @PreAuthorize("@permission.valid('sale:order:query')")
    @GetMapping
    public InvokeResult<GetSaleOrderBo> findById(@NotBlank(message = "订单ID不能为空！") String id) {

        SaleOrderFullDto data = saleOrderService.getDetail(id);

        GetSaleOrderBo result = new GetSaleOrderBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 根据ID查询（出库业务）
     */
    @ApiOperation("根据ID查询（出库业务）")
    @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
    @PreAuthorize("@permission.valid('sale:out:add', 'sale:out:modify')")
    @GetMapping("/out")
    public InvokeResult<SaleOrderWithOutBo> getWithOut(@NotBlank(message = "订单ID不能为空！") String id) {

        SaleOrderWithOutDto data = saleOrderService.getWithOut(id);
        SaleOrderWithOutBo result = new SaleOrderWithOutBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 查询列表（出库业务）
     */
    @ApiOperation("查询列表（出库业务）")
    @PreAuthorize("@permission.valid('sale:out:add', 'sale:out:modify')")
    @GetMapping("/query/out")
    public InvokeResult<PageResult<QuerySaleOrderWithOutBo>> getWithOut(@Valid QuerySaleOrderWithOutVo vo) {

        PageResult<SaleOrder> pageResult = saleOrderService.queryWithOut(getPageIndex(vo), getPageSize(vo), vo);
        List<SaleOrder> datas = pageResult.getDatas();

        List<QuerySaleOrderWithOutBo> results = null;

        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(QuerySaleOrderWithOutBo::new).collect(Collectors.toList());
        }

        return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
    }

    /**
     * 创建订单
     */
    @ApiOperation("创建订单")
    @PreAuthorize("@permission.valid('sale:order:add')")
    @PostMapping
    public InvokeResult<String> create(@RequestBody @Valid CreateSaleOrderVo vo) {

        vo.validate();

        String id = saleOrderService.create(vo);

        return InvokeResultBuilder.success(id);
    }

    /**
     * 修改订单
     */
    @ApiOperation("修改订单")
    @PreAuthorize("@permission.valid('sale:order:modify')")
    @PutMapping
    public InvokeResult<Void> update(@RequestBody @Valid UpdateSaleOrderVo vo) {

        vo.validate();

        saleOrderService.update(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 审核通过订单
     */
    @ApiOperation("审核通过订单")
    @PreAuthorize("@permission.valid('sale:order:approve')")
    @PatchMapping("/approve/pass")
    public InvokeResult<Void> approvePass(@RequestBody @Valid ApprovePassSaleOrderVo vo) {

        saleOrderService.approvePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量审核通过订单
     */
    @ApiOperation("批量审核通过订单")
    @PreAuthorize("@permission.valid('sale:order:approve')")
    @PatchMapping("/approve/pass/batch")
    public InvokeResult<Void> batchApprovePass(@RequestBody @Valid BatchApprovePassSaleOrderVo vo) {

        saleOrderService.batchApprovePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 直接审核通过订单
     */
    @ApiOperation("直接审核通过订单")
    @PreAuthorize("@permission.valid('sale:order:approve')")
    @PostMapping("/approve/pass/direct")
    public InvokeResult<Void> directApprovePass(@RequestBody @Valid CreateSaleOrderVo vo) {

        saleOrderService.directApprovePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 审核拒绝订单
     */
    @ApiOperation("审核拒绝订单")
    @PreAuthorize("@permission.valid('sale:order:approve')")
    @PatchMapping("/approve/refuse")
    public InvokeResult<Void> approveRefuse(@RequestBody @Valid ApproveRefuseSaleOrderVo vo) {

        saleOrderService.approveRefuse(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量审核拒绝订单
     */
    @ApiOperation("批量审核拒绝订单")
    @PreAuthorize("@permission.valid('sale:order:approve')")
    @PatchMapping("/approve/refuse/batch")
    public InvokeResult<Void> batchApproveRefuse(@RequestBody @Valid BatchApproveRefuseSaleOrderVo vo) {

        saleOrderService.batchApproveRefuse(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 删除订单
     */
    @ApiOperation("删除订单")
    @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
    @PreAuthorize("@permission.valid('sale:order:delete')")
    @DeleteMapping
    public InvokeResult<Void> deleteById(@NotBlank(message = "订单ID不能为空！") String id) {

        saleOrderService.deleteById(id);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量删除订单
     */
    @ApiOperation("批量删除订单")
    @PreAuthorize("@permission.valid('sale:order:delete')")
    @DeleteMapping("/batch")
    public InvokeResult<Void> deleteByIds(
            @ApiParam(value = "ID", required = true) @RequestBody @NotEmpty(message = "请选择需要删除的订单！") List<String> ids) {

        saleOrderService.deleteByIds(ids);

        return InvokeResultBuilder.success();
    }
}
