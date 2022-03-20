package com.lframework.xingyun.api.controller.purchase;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.api.bo.purchase.receive.*;
import com.lframework.xingyun.api.model.purchase.receive.ReceiveSheetExportModel;
import com.lframework.xingyun.api.print.A4ExcelPortraitPrintBo;
import com.lframework.xingyun.sc.dto.purchase.receive.GetPaymentDateDto;
import com.lframework.xingyun.sc.dto.purchase.receive.ReceiveSheetDto;
import com.lframework.xingyun.sc.dto.purchase.receive.ReceiveSheetFullDto;
import com.lframework.xingyun.sc.dto.purchase.receive.ReceiveSheetWithReturnDto;
import com.lframework.xingyun.sc.service.purchase.IReceiveSheetService;
import com.lframework.xingyun.sc.vo.purchase.receive.*;
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
 * 采购收货单管理
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/purchase/receive/sheet")
public class ReceiveSheetController extends DefaultBaseController {

    @Autowired
    private IReceiveSheetService receiveSheetService;

    /**
     * 打印
     */
    @PreAuthorize("@permission.valid('purchase:receive:query')")
    @GetMapping("/print")
    public InvokeResult print(@NotBlank(message = "订单ID不能为空！") String id) {

        ReceiveSheetFullDto data = receiveSheetService.getDetail(id);

        PrintReceiveSheetBo result = new PrintReceiveSheetBo(data);

        A4ExcelPortraitPrintBo<PrintReceiveSheetBo> printResult = new A4ExcelPortraitPrintBo<PrintReceiveSheetBo>("print/receive-sheet.ftl", result);

        return InvokeResultBuilder.success(printResult);
    }

    /**
     * 订单列表
     */
    @PreAuthorize("@permission.valid('purchase:receive:query')")
    @GetMapping("/query")
    public InvokeResult query(@Valid QueryReceiveSheetVo vo) {

        PageResult<ReceiveSheetDto> pageResult = receiveSheetService.query(getPageIndex(vo), getPageSize(vo), vo);

        List<ReceiveSheetDto> datas = pageResult.getDatas();

        if (!CollectionUtil.isEmpty(datas)) {
            List<QueryReceiveSheetBo> results = datas.stream().map(QueryReceiveSheetBo::new)
                    .collect(Collectors.toList());

            PageResultUtil.rebuild(pageResult, results);
        }

        return InvokeResultBuilder.success(pageResult);
    }

    @PreAuthorize("@permission.valid('purchase:receive:export')")
    @PostMapping("/export")
    public void export(@Valid QueryReceiveSheetVo vo) {

        ExcelMultipartWriterSheetBuilder builder = ExcelUtil
                .multipartExportXls("采购收货单信息", ReceiveSheetExportModel.class);

        try {
            int pageIndex = 1;
            while (true) {
                PageResult<ReceiveSheetDto> pageResult = receiveSheetService.query(pageIndex, getExportSize(), vo);
                List<ReceiveSheetDto> datas = pageResult.getDatas();
                List<ReceiveSheetExportModel> models = datas.stream().map(ReceiveSheetExportModel::new)
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
    @PreAuthorize("@permission.valid('purchase:receive:query')")
    @GetMapping
    public InvokeResult getById(@NotBlank(message = "订单ID不能为空！") String id) {

        ReceiveSheetFullDto data = receiveSheetService.getDetail(id);

        GetReceiveSheetBo result = new GetReceiveSheetBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 根据供应商ID查询默认付款日期
     */
    @PreAuthorize("@permission.valid('purchase:receive:add', 'purchase:receive:modify')")
    @GetMapping("/paymentdate")
    public InvokeResult getPaymentDate(@NotBlank(message = "供应商ID不能为空！") String supplierId) {

        GetPaymentDateDto data = receiveSheetService.getPaymentDate(supplierId);

        GetPaymentDateBo result = new GetPaymentDateBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 根据ID查询（采购退货业务）
     */
    @PreAuthorize("@permission.valid('purchase:return:add', 'purchase:return:modify')")
    @GetMapping("/return")
    public InvokeResult getWithReturn(@NotBlank(message = "收货单ID不能为空！") String id) {

        ReceiveSheetWithReturnDto data = receiveSheetService.getWithReturn(id);
        ReceiveSheetWithReturnBo result = new ReceiveSheetWithReturnBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 查询列表（采购退货业务）
     */
    @PreAuthorize("@permission.valid('purchase:return:add', 'purchase:return:modify')")
    @GetMapping("/query/return")
    public InvokeResult queryWithReturn(@Valid QueryReceiveSheetWithReturnVo vo) {

        PageResult<ReceiveSheetDto> pageResult = receiveSheetService
                .queryWithReturn(getPageIndex(vo), getPageSize(vo), vo);
        List<ReceiveSheetDto> datas = pageResult.getDatas();

        List<QueryReceiveSheetWithReturnBo> results = Collections.EMPTY_LIST;

        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(QueryReceiveSheetWithReturnBo::new).collect(Collectors.toList());
            PageResultUtil.rebuild(pageResult, results);
        }

        return InvokeResultBuilder.success(pageResult);
    }

    /**
     * 创建
     */
    @PreAuthorize("@permission.valid('purchase:receive:add')")
    @PostMapping
    public InvokeResult create(@RequestBody @Valid CreateReceiveSheetVo vo) {

        vo.validate();

        String id = receiveSheetService.create(vo);

        return InvokeResultBuilder.success(id);
    }

    /**
     * 修改
     */
    @PreAuthorize("@permission.valid('purchase:receive:modify')")
    @PutMapping
    public InvokeResult update(@RequestBody @Valid UpdateReceiveSheetVo vo) {

        vo.validate();

        receiveSheetService.update(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 审核通过
     */
    @PreAuthorize("@permission.valid('purchase:receive:approve')")
    @PatchMapping("/approve/pass")
    public InvokeResult approvePass(@RequestBody @Valid ApprovePassReceiveSheetVo vo) {

        receiveSheetService.approvePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量审核通过
     */
    @PreAuthorize("@permission.valid('purchase:receive:approve')")
    @PatchMapping("/approve/pass/batch")
    public InvokeResult batchApprovePass(@RequestBody @Valid BatchApprovePassReceiveSheetVo vo) {

        receiveSheetService.batchApprovePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 直接审核通过
     */
    @PreAuthorize("@permission.valid('purchase:receive:approve')")
    @PostMapping("/approve/pass/direct")
    public InvokeResult directApprovePass(@RequestBody @Valid CreateReceiveSheetVo vo) {

        receiveSheetService.directApprovePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 审核拒绝
     */
    @PreAuthorize("@permission.valid('purchase:receive:approve')")
    @PatchMapping("/approve/refuse")
    public InvokeResult approveRefuse(@RequestBody @Valid ApproveRefuseReceiveSheetVo vo) {

        receiveSheetService.approveRefuse(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量审核拒绝
     */
    @PreAuthorize("@permission.valid('purchase:receive:approve')")
    @PatchMapping("/approve/refuse/batch")
    public InvokeResult batchApproveRefuse(@RequestBody @Valid BatchApproveRefuseReceiveSheetVo vo) {

        receiveSheetService.batchApproveRefuse(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 删除
     */
    @PreAuthorize("@permission.valid('purchase:receive:delete')")
    @DeleteMapping
    public InvokeResult deleteById(@NotBlank(message = "采购收货单ID不能为空！") String id) {

        receiveSheetService.deleteById(id);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量删除
     */
    @PreAuthorize("@permission.valid('purchase:receive:delete')")
    @DeleteMapping("/batch")
    public InvokeResult deleteByIds(@RequestBody @NotEmpty(message = "请选择需要删除的采购收货单！") List<String> ids) {

        receiveSheetService.deleteByIds(ids);

        return InvokeResultBuilder.success();
    }
}
