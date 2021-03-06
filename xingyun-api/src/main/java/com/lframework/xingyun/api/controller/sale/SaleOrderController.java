package com.lframework.xingyun.api.controller.sale;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.api.bo.sale.GetSaleOrderBo;
import com.lframework.xingyun.api.bo.sale.PrintSaleOrderBo;
import com.lframework.xingyun.api.bo.sale.QuerySaleOrderBo;
import com.lframework.xingyun.api.bo.sale.QuerySaleOrderWithOutBo;
import com.lframework.xingyun.api.bo.sale.SaleOrderWithOutBo;
import com.lframework.xingyun.api.bo.sale.SaleProductBo;
import com.lframework.xingyun.api.excel.sale.SaleOrderExportModel;
import com.lframework.xingyun.api.print.A4ExcelPortraitPrintBo;
import com.lframework.xingyun.basedata.dto.product.info.SaleProductDto;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.vo.product.info.QuerySaleProductVo;
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
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Collections;
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
 * ??????????????????
 *
 * @author zmj
 */
@Api(tags = "??????????????????")
@Validated
@RestController
@RequestMapping("/sale/order")
public class SaleOrderController extends DefaultBaseController {

    @Autowired
    private ISaleOrderService saleOrderService;

    @Autowired
    private IProductService productService;

    /**
     * ??????
     */
    @ApiOperation("??????")
    @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
    @PreAuthorize("@permission.valid('sale:order:query')")
    @GetMapping("/print")
    public InvokeResult<A4ExcelPortraitPrintBo<PrintSaleOrderBo>> print(@NotBlank(message = "??????ID???????????????") String id) {

        SaleOrderFullDto data = saleOrderService.getDetail(id);
        if (data == null) {
            throw new DefaultClientException("??????????????????");
        }

        PrintSaleOrderBo result = new PrintSaleOrderBo(data);

        A4ExcelPortraitPrintBo<PrintSaleOrderBo> printResult = new A4ExcelPortraitPrintBo<>("print/sale-order.ftl",
                result);

        return InvokeResultBuilder.success(printResult);
    }

    /**
     * ????????????
     */
    @ApiOperation("????????????")
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
     * ??????
     */
    @ApiOperation("??????")
    @PreAuthorize("@permission.valid('sale:order:export')")
    @PostMapping("/export")
    public void export(@Valid QuerySaleOrderVo vo) {

        ExcelMultipartWriterSheetBuilder builder = ExcelUtil.multipartExportXls("???????????????", SaleOrderExportModel.class);

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
     * ???????????????????????????
     */
    @ApiOperation("???????????????????????????")
    @ApiImplicitParams({@ApiImplicitParam(value = "??????ID", name = "scId", paramType = "query", required = true),
            @ApiImplicitParam(value = "?????????", name = "condition", paramType = "query", required = true)})
    @PreAuthorize("@permission.valid('sale:order:add', 'sale:order:modify', 'sale:out:add', 'sale:out:modify', 'sale:return:add', 'sale:return:modify')")
    @GetMapping("/product/search")
    public InvokeResult<List<SaleProductBo>> searchProducts(@NotBlank(message = "??????ID???????????????") String scId,
            String condition) {

        if (StringUtil.isBlank(condition)) {
            return InvokeResultBuilder.success(Collections.EMPTY_LIST);
        }

        PageResult<SaleProductDto> pageResult = productService.querySaleByCondition(getPageIndex(), getPageSize(),
                condition);
        List<SaleProductBo> results = Collections.EMPTY_LIST;
        List<SaleProductDto> datas = pageResult.getDatas();
        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(t -> new SaleProductBo(scId, t)).collect(Collectors.toList());
        }

        return InvokeResultBuilder.success(results);
    }

    /**
     * ??????????????????
     */
    @ApiOperation("??????????????????")
    @PreAuthorize("@permission.valid('sale:order:add', 'sale:order:modify', 'sale:out:add', 'sale:out:modify', 'sale:return:add', 'sale:return:modify')")
    @GetMapping("/product/list")
    public InvokeResult<PageResult<SaleProductBo>> queryProductList(@Valid QuerySaleProductVo vo) {

        PageResult<SaleProductDto> pageResult = productService.querySaleList(getPageIndex(), getPageSize(), vo);
        List<SaleProductBo> results = null;
        List<SaleProductDto> datas = pageResult.getDatas();
        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(t -> new SaleProductBo(vo.getScId(), t)).collect(Collectors.toList());
        }

        return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
    }

    /**
     * ??????ID??????
     */
    @ApiOperation("??????ID??????")
    @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
    @PreAuthorize("@permission.valid('sale:order:query')")
    @GetMapping
    public InvokeResult<GetSaleOrderBo> findById(@NotBlank(message = "??????ID???????????????") String id) {

        SaleOrderFullDto data = saleOrderService.getDetail(id);

        GetSaleOrderBo result = new GetSaleOrderBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * ??????ID????????????????????????
     */
    @ApiOperation("??????ID????????????????????????")
    @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
    @PreAuthorize("@permission.valid('sale:out:add', 'sale:out:modify')")
    @GetMapping("/out")
    public InvokeResult<SaleOrderWithOutBo> getWithOut(@NotBlank(message = "??????ID???????????????") String id) {

        SaleOrderWithOutDto data = saleOrderService.getWithOut(id);
        SaleOrderWithOutBo result = new SaleOrderWithOutBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * ??????????????????????????????
     */
    @ApiOperation("??????????????????????????????")
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
     * ????????????
     */
    @ApiOperation("????????????")
    @PreAuthorize("@permission.valid('sale:order:add')")
    @PostMapping
    public InvokeResult<String> create(@RequestBody @Valid CreateSaleOrderVo vo) {

        vo.validate();

        String id = saleOrderService.create(vo);

        return InvokeResultBuilder.success(id);
    }

    /**
     * ????????????
     */
    @ApiOperation("????????????")
    @PreAuthorize("@permission.valid('sale:order:modify')")
    @PutMapping
    public InvokeResult<Void> update(@RequestBody @Valid UpdateSaleOrderVo vo) {

        vo.validate();

        saleOrderService.update(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * ??????????????????
     */
    @ApiOperation("??????????????????")
    @PreAuthorize("@permission.valid('sale:order:approve')")
    @PatchMapping("/approve/pass")
    public InvokeResult<Void> approvePass(@RequestBody @Valid ApprovePassSaleOrderVo vo) {

        saleOrderService.approvePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * ????????????????????????
     */
    @ApiOperation("????????????????????????")
    @PreAuthorize("@permission.valid('sale:order:approve')")
    @PatchMapping("/approve/pass/batch")
    public InvokeResult<Void> batchApprovePass(@RequestBody @Valid BatchApprovePassSaleOrderVo vo) {

        saleOrderService.batchApprovePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * ????????????????????????
     */
    @ApiOperation("????????????????????????")
    @PreAuthorize("@permission.valid('sale:order:approve')")
    @PostMapping("/approve/pass/direct")
    public InvokeResult<Void> directApprovePass(@RequestBody @Valid CreateSaleOrderVo vo) {

        saleOrderService.directApprovePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * ??????????????????
     */
    @ApiOperation("??????????????????")
    @PreAuthorize("@permission.valid('sale:order:approve')")
    @PatchMapping("/approve/refuse")
    public InvokeResult<Void> approveRefuse(@RequestBody @Valid ApproveRefuseSaleOrderVo vo) {

        saleOrderService.approveRefuse(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * ????????????????????????
     */
    @ApiOperation("????????????????????????")
    @PreAuthorize("@permission.valid('sale:order:approve')")
    @PatchMapping("/approve/refuse/batch")
    public InvokeResult<Void> batchApproveRefuse(@RequestBody @Valid BatchApproveRefuseSaleOrderVo vo) {

        saleOrderService.batchApproveRefuse(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * ????????????
     */
    @ApiOperation("????????????")
    @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
    @PreAuthorize("@permission.valid('sale:order:delete')")
    @DeleteMapping
    public InvokeResult<Void> deleteById(@NotBlank(message = "??????ID???????????????") String id) {

        saleOrderService.deleteById(id);

        return InvokeResultBuilder.success();
    }

    /**
     * ??????????????????
     */
    @ApiOperation("??????????????????")
    @PreAuthorize("@permission.valid('sale:order:delete')")
    @DeleteMapping("/batch")
    public InvokeResult<Void> deleteByIds(
            @ApiParam(value = "ID", required = true) @RequestBody @NotEmpty(message = "?????????????????????????????????") List<String> ids) {

        saleOrderService.deleteByIds(ids);

        return InvokeResultBuilder.success();
    }
}
