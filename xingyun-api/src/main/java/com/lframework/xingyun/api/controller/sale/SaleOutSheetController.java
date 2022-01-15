package com.lframework.xingyun.api.controller.sale;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.api.bo.purchase.receive.GetPaymentDateBo;
import com.lframework.xingyun.api.bo.sale.out.GetSaleOutSheetBo;
import com.lframework.xingyun.api.bo.sale.out.QuerySaleOutSheetBo;
import com.lframework.xingyun.api.bo.sale.out.QuerySaleOutSheetWithReturnBo;
import com.lframework.xingyun.api.bo.sale.out.SaleOutSheetWithReturnBo;
import com.lframework.xingyun.api.model.sale.out.SaleOutSheetExportModel;
import com.lframework.xingyun.sc.dto.purchase.receive.GetPaymentDateDto;
import com.lframework.xingyun.sc.dto.sale.out.SaleOutSheetDto;
import com.lframework.xingyun.sc.dto.sale.out.SaleOutSheetFullDto;
import com.lframework.xingyun.sc.dto.sale.out.SaleOutSheetWithReturnDto;
import com.lframework.xingyun.sc.service.sale.ISaleOutSheetService;
import com.lframework.xingyun.sc.vo.sale.out.*;
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
 * 销售出库单管理
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/sale/out/sheet")
public class SaleOutSheetController extends DefaultBaseController {

    @Autowired
    private ISaleOutSheetService saleOutSheetService;

    /**
     * 订单列表
     */
    @PreAuthorize("@permission.valid('sale:out:query')")
    @GetMapping("/query")
    public InvokeResult query(@Valid QuerySaleOutSheetVo vo) {

        PageResult<SaleOutSheetDto> pageResult = saleOutSheetService.query(getPageIndex(vo), getPageSize(vo), vo);

        List<SaleOutSheetDto> datas = pageResult.getDatas();

        if (!CollectionUtil.isEmpty(datas)) {
            List<QuerySaleOutSheetBo> results = datas.stream().map(QuerySaleOutSheetBo::new)
                    .collect(Collectors.toList());

            PageResultUtil.rebuild(pageResult, results);
        }

        return InvokeResultBuilder.success(pageResult);
    }

    @PreAuthorize("@permission.valid('sale:out:export')")
    @PostMapping("/export")
    public void export(@Valid QuerySaleOutSheetVo vo) {

        ExcelMultipartWriterSheetBuilder builder = ExcelUtil
                .multipartExportXls("销售出库单信息", SaleOutSheetExportModel.class);

        try {
            int pageIndex = 1;
            while (true) {
                PageResult<SaleOutSheetDto> pageResult = saleOutSheetService.query(pageIndex, getExportSize(), vo);
                List<SaleOutSheetDto> datas = pageResult.getDatas();
                List<SaleOutSheetExportModel> models = datas.stream().map(SaleOutSheetExportModel::new)
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
    @PreAuthorize("@permission.valid('sale:out:query')")
    @GetMapping
    public InvokeResult getById(@NotBlank(message = "订单ID不能为空！") String id) {

        SaleOutSheetFullDto data = saleOutSheetService.getDetail(id);

        GetSaleOutSheetBo result = new GetSaleOutSheetBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 根据客户ID查询默认付款日期
     */
    @PreAuthorize("@permission.valid('sale:out:add', 'sale:out:modify')")
    @GetMapping("/paymentdate")
    public InvokeResult getPaymentDate(@NotBlank(message = "客户ID不能为空！") String customerId) {

        GetPaymentDateDto data = saleOutSheetService.getPaymentDate(customerId);

        GetPaymentDateBo result = new GetPaymentDateBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 根据ID查询（销售退货业务）
     */
    @PreAuthorize("@permission.valid('sale:return:add', 'sale:return:modify')")
    @GetMapping("/return")
    public InvokeResult getWithReturn(@NotBlank(message = "出库单ID不能为空！") String id) {

        SaleOutSheetWithReturnDto data = saleOutSheetService.getWithReturn(id);
        SaleOutSheetWithReturnBo result = new SaleOutSheetWithReturnBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 查询列表（销售退货业务）
     */
    @PreAuthorize("@permission.valid('sale:return:add', 'sale:return:modify')")
    @GetMapping("/query/return")
    public InvokeResult queryWithReturn(@Valid QuerySaleOutSheetWithReturnVo vo) {

        PageResult<SaleOutSheetDto> pageResult = saleOutSheetService
                .queryWithReturn(getPageIndex(vo), getPageSize(vo), vo);
        List<SaleOutSheetDto> datas = pageResult.getDatas();

        List<QuerySaleOutSheetWithReturnBo> results = Collections.EMPTY_LIST;

        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(QuerySaleOutSheetWithReturnBo::new).collect(Collectors.toList());
            PageResultUtil.rebuild(pageResult, results);
        }

        return InvokeResultBuilder.success(pageResult);
    }

    /**
     * 创建
     */
    @PreAuthorize("@permission.valid('sale:out:add')")
    @PostMapping
    public InvokeResult create(@RequestBody @Valid CreateSaleOutSheetVo vo) {

        vo.validate();

        String id = saleOutSheetService.create(vo);

        return InvokeResultBuilder.success(id);
    }

    /**
     * 修改
     */
    @PreAuthorize("@permission.valid('sale:out:modify')")
    @PutMapping
    public InvokeResult update(@RequestBody @Valid UpdateSaleOutSheetVo vo) {

        vo.validate();

        saleOutSheetService.update(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 审核通过
     */
    @PreAuthorize("@permission.valid('sale:out:approve')")
    @PatchMapping("/approve/pass")
    public InvokeResult approvePass(@RequestBody @Valid ApprovePassSaleOutSheetVo vo) {

        saleOutSheetService.approvePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量审核通过
     */
    @PreAuthorize("@permission.valid('sale:out:approve')")
    @PatchMapping("/approve/pass/batch")
    public InvokeResult batchApprovePass(@RequestBody @Valid BatchApprovePassSaleOutSheetVo vo) {

        saleOutSheetService.batchApprovePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 直接审核通过
     */
    @PreAuthorize("@permission.valid('sale:out:approve')")
    @PostMapping("/approve/pass/redirect")
    public InvokeResult redirectApprovePass(@RequestBody @Valid CreateSaleOutSheetVo vo) {

        saleOutSheetService.redirectApprovePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 审核拒绝
     */
    @PreAuthorize("@permission.valid('sale:out:approve')")
    @PatchMapping("/approve/refuse")
    public InvokeResult approveRefuse(@RequestBody @Valid ApproveRefuseSaleOutSheetVo vo) {

        saleOutSheetService.approveRefuse(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量审核拒绝
     */
    @PreAuthorize("@permission.valid('sale:out:approve')")
    @PatchMapping("/approve/refuse/batch")
    public InvokeResult batchApproveRefuse(@RequestBody @Valid BatchApproveRefuseSaleOutSheetVo vo) {

        saleOutSheetService.batchApproveRefuse(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 删除
     */
    @PreAuthorize("@permission.valid('sale:out:delete')")
    @DeleteMapping
    public InvokeResult deleteById(@NotBlank(message = "销售出库单ID不能为空！") String id) {

        saleOutSheetService.deleteById(id);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量删除
     */
    @PreAuthorize("@permission.valid('sale:out:delete')")
    @DeleteMapping("/batch")
    public InvokeResult deleteByIds(@RequestBody @NotEmpty(message = "请选择需要删除的销售出库单！") List<String> ids) {

        saleOutSheetService.deleteByIds(ids);

        return InvokeResultBuilder.success();
    }
}
