package com.lframework.xingyun.api.controller.settle;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.api.bo.settle.pre.GetSettlePreSheetBo;
import com.lframework.xingyun.api.bo.settle.pre.QuerySettlePreSheetBo;
import com.lframework.xingyun.api.model.settle.pre.SettlePreSheetExportModel;
import com.lframework.xingyun.settle.dto.pre.SettlePreSheetDto;
import com.lframework.xingyun.settle.dto.pre.SettlePreSheetFullDto;
import com.lframework.xingyun.settle.service.ISettlePreSheetService;
import com.lframework.xingyun.settle.vo.pre.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 供应商预付款单
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/settle/presheet")
public class SettlePreSheetController extends DefaultBaseController {

    @Autowired
    private ISettlePreSheetService settlePreSheetService;

    /**
     * 供应商预付款单列表
     */
    @PreAuthorize("@permission.valid('settle:pre-sheet:query')")
    @GetMapping("/query")
    public InvokeResult query(@Valid QuerySettlePreSheetVo vo) {

        PageResult<SettlePreSheetDto> pageResult = settlePreSheetService.query(getPageIndex(vo), getPageSize(vo), vo);

        List<SettlePreSheetDto> datas = pageResult.getDatas();

        if (!CollectionUtil.isEmpty(datas)) {
            List<QuerySettlePreSheetBo> results = datas.stream().map(QuerySettlePreSheetBo::new)
                    .collect(Collectors.toList());

            PageResultUtil.rebuild(pageResult, results);
        }

        return InvokeResultBuilder.success(pageResult);
    }

    @PreAuthorize("@permission.valid('settle:pre-sheet:export')")
    @PostMapping("/export")
    public void export(@Valid QuerySettlePreSheetVo vo) {

        ExcelMultipartWriterSheetBuilder builder = ExcelUtil
                .multipartExportXls("供应商预付款单信息", SettlePreSheetExportModel.class);

        try {
            int pageIndex = 1;
            while (true) {
                PageResult<SettlePreSheetDto> pageResult = settlePreSheetService.query(pageIndex, getExportSize(), vo);
                if (!pageResult.isHasNext()) {
                    break;
                }
                pageIndex++;
                List<SettlePreSheetDto> datas = pageResult.getDatas();
                List<SettlePreSheetExportModel> models = datas.stream().map(SettlePreSheetExportModel::new)
                        .collect(Collectors.toList());
                builder.doWrite(models);
            }
        } finally {
            builder.finish();
        }
    }

    /**
     * 根据ID查询
     */
    @PreAuthorize("@permission.valid('settle:pre-sheet:query')")
    @GetMapping
    public InvokeResult getById(@NotBlank(message = "供应商预付款单ID不能为空！") String id) {

        SettlePreSheetFullDto data = settlePreSheetService.getDetail(id);

        GetSettlePreSheetBo result = new GetSettlePreSheetBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 创建供应商预付款单
     */
    @PreAuthorize("@permission.valid('settle:pre-sheet:add')")
    @PostMapping
    public InvokeResult create(@RequestBody @Valid CreateSettlePreSheetVo vo) {

        vo.validate();

        String id = settlePreSheetService.create(vo);

        return InvokeResultBuilder.success(id);
    }

    /**
     * 修改供应商预付款单
     */
    @PreAuthorize("@permission.valid('settle:pre-sheet:modify')")
    @PutMapping
    public InvokeResult update(@RequestBody @Valid UpdateSettlePreSheetVo vo) {

        vo.validate();

        settlePreSheetService.update(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 审核通过供应商预付款单
     */
    @PreAuthorize("@permission.valid('settle:pre-sheet:approve')")
    @PatchMapping("/approve/pass")
    public InvokeResult approvePass(@RequestBody @Valid ApprovePassSettlePreSheetVo vo) {

        settlePreSheetService.approvePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量审核通过供应商预付款单
     */
    @PreAuthorize("@permission.valid('settle:pre-sheet:approve')")
    @PatchMapping("/approve/pass/batch")
    public InvokeResult batchApprovePass(@RequestBody @Valid BatchApprovePassSettlePreSheetVo vo) {

        settlePreSheetService.batchApprovePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 直接审核通过供应商预付款单
     */
    @PreAuthorize("@permission.valid('settle:pre-sheet:approve')")
    @PostMapping("/approve/pass/redirect")
    public InvokeResult redirectApprovePass(@RequestBody @Valid CreateSettlePreSheetVo vo) {

        settlePreSheetService.redirectApprovePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 审核拒绝供应商预付款单
     */
    @PreAuthorize("@permission.valid('settle:pre-sheet:approve')")
    @PatchMapping("/approve/refuse")
    public InvokeResult approveRefuse(@RequestBody @Valid ApproveRefuseSettlePreSheetVo vo) {

        settlePreSheetService.approveRefuse(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量审核拒绝供应商预付款单
     */
    @PreAuthorize("@permission.valid('settle:pre-sheet:approve')")
    @PatchMapping("/approve/refuse/batch")
    public InvokeResult batchApproveRefuse(@RequestBody @Valid BatchApproveRefuseSettlePreSheetVo vo) {

        settlePreSheetService.batchApproveRefuse(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 删除供应商预付款单
     */
    @PreAuthorize("@permission.valid('settle:pre-sheet:delete')")
    @DeleteMapping
    public InvokeResult deleteById(@NotBlank(message = "供应商预付款单ID不能为空！") String id) {

        settlePreSheetService.deleteById(id);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量删除供应商预付款单
     */
    @PreAuthorize("@permission.valid('settle:pre-sheet:delete')")
    @DeleteMapping("/batch")
    public InvokeResult deleteByIds(@RequestBody @NotEmpty(message = "请选择需要删除的供应商预付款单！") List<String> ids) {

        settlePreSheetService.deleteByIds(ids);

        return InvokeResultBuilder.success();
    }
}
