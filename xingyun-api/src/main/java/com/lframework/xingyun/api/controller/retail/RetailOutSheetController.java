package com.lframework.xingyun.api.controller.retail;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.api.bo.purchase.receive.GetPaymentDateBo;
import com.lframework.xingyun.api.bo.retail.out.*;
import com.lframework.xingyun.api.model.retail.out.RetailOutSheetExportModel;
import com.lframework.xingyun.api.print.A4ExcelPortraitPrintBo;
import com.lframework.xingyun.sc.dto.purchase.receive.GetPaymentDateDto;
import com.lframework.xingyun.sc.dto.retail.out.RetailOutSheetDto;
import com.lframework.xingyun.sc.dto.retail.out.RetailOutSheetFullDto;
import com.lframework.xingyun.sc.dto.retail.out.RetailOutSheetWithReturnDto;
import com.lframework.xingyun.sc.service.retail.IRetailOutSheetService;
import com.lframework.xingyun.sc.vo.retail.out.*;
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
 * 零售出库单管理
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/retail/out/sheet")
public class RetailOutSheetController extends DefaultBaseController {

    @Autowired
    private IRetailOutSheetService retailOutSheetService;

    /**
     * 打印
     */
    @PreAuthorize("@permission.valid('retail:out:query')")
    @GetMapping("/print")
    public InvokeResult print(@NotBlank(message = "订单ID不能为空！") String id) {

        RetailOutSheetFullDto data = retailOutSheetService.getDetail(id);
        if (data == null) {
            throw new DefaultClientException("零售出库单不存在！");
        }

        PrintRetailOutSheetBo result = new PrintRetailOutSheetBo(data);
        A4ExcelPortraitPrintBo<PrintRetailOutSheetBo> printResult = new A4ExcelPortraitPrintBo<>("print/retail-out-sheet.ftl", result);

        return InvokeResultBuilder.success(printResult);
    }

    /**
     * 订单列表
     */
    @PreAuthorize("@permission.valid('retail:out:query')")
    @GetMapping("/query")
    public InvokeResult query(@Valid QueryRetailOutSheetVo vo) {

        PageResult<RetailOutSheetDto> pageResult = retailOutSheetService.query(getPageIndex(vo), getPageSize(vo), vo);

        List<RetailOutSheetDto> datas = pageResult.getDatas();

        if (!CollectionUtil.isEmpty(datas)) {
            List<QueryRetailOutSheetBo> results = datas.stream().map(QueryRetailOutSheetBo::new)
                    .collect(Collectors.toList());

            PageResultUtil.rebuild(pageResult, results);
        }

        return InvokeResultBuilder.success(pageResult);
    }

    @PreAuthorize("@permission.valid('retail:out:export')")
    @PostMapping("/export")
    public void export(@Valid QueryRetailOutSheetVo vo) {

        ExcelMultipartWriterSheetBuilder builder = ExcelUtil
                .multipartExportXls("零售出库单信息", RetailOutSheetExportModel.class);

        try {
            int pageIndex = 1;
            while (true) {
                PageResult<RetailOutSheetDto> pageResult = retailOutSheetService.query(pageIndex, getExportSize(), vo);
                List<RetailOutSheetDto> datas = pageResult.getDatas();
                List<RetailOutSheetExportModel> models = datas.stream().map(RetailOutSheetExportModel::new)
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
    @PreAuthorize("@permission.valid('retail:out:query')")
    @GetMapping
    public InvokeResult getById(@NotBlank(message = "订单ID不能为空！") String id) {

        RetailOutSheetFullDto data = retailOutSheetService.getDetail(id);

        GetRetailOutSheetBo result = new GetRetailOutSheetBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 根据会员ID查询默认付款日期
     */
    @PreAuthorize("@permission.valid('retail:out:add', 'retail:out:modify')")
    @GetMapping("/paymentdate")
    public InvokeResult getPaymentDate(@NotBlank(message = "会员ID不能为空！") String memberId) {

        GetPaymentDateDto data = retailOutSheetService.getPaymentDate(memberId);

        GetPaymentDateBo result = new GetPaymentDateBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 根据ID查询（零售退货业务）
     */
    @PreAuthorize("@permission.valid('retail:return:add', 'retail:return:modify')")
    @GetMapping("/return")
    public InvokeResult getWithReturn(@NotBlank(message = "出库单ID不能为空！") String id) {

        RetailOutSheetWithReturnDto data = retailOutSheetService.getWithReturn(id);
        RetailOutSheetWithReturnBo result = new RetailOutSheetWithReturnBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 查询列表（零售退货业务）
     */
    @PreAuthorize("@permission.valid('retail:return:add', 'retail:return:modify')")
    @GetMapping("/query/return")
    public InvokeResult queryWithReturn(@Valid QueryRetailOutSheetWithReturnVo vo) {

        PageResult<RetailOutSheetDto> pageResult = retailOutSheetService
                .queryWithReturn(getPageIndex(vo), getPageSize(vo), vo);
        List<RetailOutSheetDto> datas = pageResult.getDatas();

        List<QueryRetailOutSheetWithReturnBo> results = Collections.EMPTY_LIST;

        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(QueryRetailOutSheetWithReturnBo::new).collect(Collectors.toList());
            PageResultUtil.rebuild(pageResult, results);
        }

        return InvokeResultBuilder.success(pageResult);
    }

    /**
     * 创建
     */
    @PreAuthorize("@permission.valid('retail:out:add')")
    @PostMapping
    public InvokeResult create(@RequestBody @Valid CreateRetailOutSheetVo vo) {

        vo.validate();

        String id = retailOutSheetService.create(vo);

        return InvokeResultBuilder.success(id);
    }

    /**
     * 修改
     */
    @PreAuthorize("@permission.valid('retail:out:modify')")
    @PutMapping
    public InvokeResult update(@RequestBody @Valid UpdateRetailOutSheetVo vo) {

        vo.validate();

        retailOutSheetService.update(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 审核通过
     */
    @PreAuthorize("@permission.valid('retail:out:approve')")
    @PatchMapping("/approve/pass")
    public InvokeResult approvePass(@RequestBody @Valid ApprovePassRetailOutSheetVo vo) {

        retailOutSheetService.approvePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量审核通过
     */
    @PreAuthorize("@permission.valid('retail:out:approve')")
    @PatchMapping("/approve/pass/batch")
    public InvokeResult batchApprovePass(@RequestBody @Valid BatchApprovePassRetailOutSheetVo vo) {

        retailOutSheetService.batchApprovePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 直接审核通过
     */
    @PreAuthorize("@permission.valid('retail:out:approve')")
    @PostMapping("/approve/pass/direct")
    public InvokeResult directApprovePass(@RequestBody @Valid CreateRetailOutSheetVo vo) {

        retailOutSheetService.directApprovePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 审核拒绝
     */
    @PreAuthorize("@permission.valid('retail:out:approve')")
    @PatchMapping("/approve/refuse")
    public InvokeResult approveRefuse(@RequestBody @Valid ApproveRefuseRetailOutSheetVo vo) {

        retailOutSheetService.approveRefuse(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量审核拒绝
     */
    @PreAuthorize("@permission.valid('retail:out:approve')")
    @PatchMapping("/approve/refuse/batch")
    public InvokeResult batchApproveRefuse(@RequestBody @Valid BatchApproveRefuseRetailOutSheetVo vo) {

        retailOutSheetService.batchApproveRefuse(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 删除
     */
    @PreAuthorize("@permission.valid('retail:out:delete')")
    @DeleteMapping
    public InvokeResult deleteById(@NotBlank(message = "零售出库单ID不能为空！") String id) {

        retailOutSheetService.deleteById(id);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量删除
     */
    @PreAuthorize("@permission.valid('retail:out:delete')")
    @DeleteMapping("/batch")
    public InvokeResult deleteByIds(@RequestBody @NotEmpty(message = "请选择需要删除的零售出库单！") List<String> ids) {

        retailOutSheetService.deleteByIds(ids);

        return InvokeResultBuilder.success();
    }
}
