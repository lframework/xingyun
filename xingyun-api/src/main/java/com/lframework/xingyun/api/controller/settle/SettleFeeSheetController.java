package com.lframework.xingyun.api.controller.settle;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.api.bo.settle.fee.GetSettleFeeSheetBo;
import com.lframework.xingyun.api.bo.settle.fee.QuerySettleFeeSheetBo;
import com.lframework.xingyun.api.model.settle.fee.SettleFeeSheetExportModel;
import com.lframework.xingyun.settle.dto.fee.SettleFeeSheetDto;
import com.lframework.xingyun.settle.dto.fee.SettleFeeSheetFullDto;
import com.lframework.xingyun.settle.service.ISettleFeeSheetService;
import com.lframework.xingyun.settle.vo.fee.*;
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
 * 供应商费用单
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/settle/feesheet")
public class SettleFeeSheetController extends DefaultBaseController {

    @Autowired
    private ISettleFeeSheetService settleFeeSheetService;

    /**
     * 供应商费用单列表
     */
    @PreAuthorize("@permission.valid('settle:fee-sheet:query')")
    @GetMapping("/query")
    public InvokeResult query(@Valid QuerySettleFeeSheetVo vo) {

        PageResult<SettleFeeSheetDto> pageResult = settleFeeSheetService.query(getPageIndex(vo), getPageSize(vo), vo);

        List<SettleFeeSheetDto> datas = pageResult.getDatas();

        if (!CollectionUtil.isEmpty(datas)) {
            List<QuerySettleFeeSheetBo> results = datas.stream().map(QuerySettleFeeSheetBo::new)
                    .collect(Collectors.toList());

            PageResultUtil.rebuild(pageResult, results);
        }

        return InvokeResultBuilder.success(pageResult);
    }

    @PreAuthorize("@permission.valid('settle:fee-sheet:export')")
    @PostMapping("/export")
    public void export(@Valid QuerySettleFeeSheetVo vo) {

        ExcelMultipartWriterSheetBuilder builder = ExcelUtil
                .multipartExportXls("供应商费用单信息", SettleFeeSheetExportModel.class);

        try {
            int pageIndex = 1;
            while (true) {
                PageResult<SettleFeeSheetDto> pageResult = settleFeeSheetService.query(pageIndex, getExportSize(), vo);
                List<SettleFeeSheetDto> datas = pageResult.getDatas();
                List<SettleFeeSheetExportModel> models = datas.stream().map(SettleFeeSheetExportModel::new)
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
    @PreAuthorize("@permission.valid('settle:fee-sheet:query')")
    @GetMapping
    public InvokeResult getById(@NotBlank(message = "供应商费用单ID不能为空！") String id) {

        SettleFeeSheetFullDto data = settleFeeSheetService.getDetail(id);

        GetSettleFeeSheetBo result = new GetSettleFeeSheetBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 创建供应商费用单
     */
    @PreAuthorize("@permission.valid('settle:fee-sheet:add')")
    @PostMapping
    public InvokeResult create(@RequestBody @Valid CreateSettleFeeSheetVo vo) {

        vo.validate();

        String id = settleFeeSheetService.create(vo);

        return InvokeResultBuilder.success(id);
    }

    /**
     * 修改供应商费用单
     */
    @PreAuthorize("@permission.valid('settle:fee-sheet:modify')")
    @PutMapping
    public InvokeResult update(@RequestBody @Valid UpdateSettleFeeSheetVo vo) {

        vo.validate();

        settleFeeSheetService.update(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 审核通过供应商费用单
     */
    @PreAuthorize("@permission.valid('settle:fee-sheet:approve')")
    @PatchMapping("/approve/pass")
    public InvokeResult approvePass(@RequestBody @Valid ApprovePassSettleFeeSheetVo vo) {

        settleFeeSheetService.approvePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量审核通过供应商费用单
     */
    @PreAuthorize("@permission.valid('settle:fee-sheet:approve')")
    @PatchMapping("/approve/pass/batch")
    public InvokeResult batchApprovePass(@RequestBody @Valid BatchApprovePassSettleFeeSheetVo vo) {

        settleFeeSheetService.batchApprovePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 直接审核通过供应商费用单
     */
    @PreAuthorize("@permission.valid('settle:fee-sheet:approve')")
    @PostMapping("/approve/pass/redirect")
    public InvokeResult redirectApprovePass(@RequestBody @Valid CreateSettleFeeSheetVo vo) {

        settleFeeSheetService.redirectApprovePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 审核拒绝供应商费用单
     */
    @PreAuthorize("@permission.valid('settle:fee-sheet:approve')")
    @PatchMapping("/approve/refuse")
    public InvokeResult approveRefuse(@RequestBody @Valid ApproveRefuseSettleFeeSheetVo vo) {

        settleFeeSheetService.approveRefuse(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量审核拒绝供应商费用单
     */
    @PreAuthorize("@permission.valid('settle:fee-sheet:approve')")
    @PatchMapping("/approve/refuse/batch")
    public InvokeResult batchApproveRefuse(@RequestBody @Valid BatchApproveRefuseSettleFeeSheetVo vo) {

        settleFeeSheetService.batchApproveRefuse(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 删除供应商费用单
     */
    @PreAuthorize("@permission.valid('settle:fee-sheet:delete')")
    @DeleteMapping
    public InvokeResult deleteById(@NotBlank(message = "供应商费用单ID不能为空！") String id) {

        settleFeeSheetService.deleteById(id);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量删除供应商费用单
     */
    @PreAuthorize("@permission.valid('settle:fee-sheet:delete')")
    @DeleteMapping("/batch")
    public InvokeResult deleteByIds(@RequestBody @NotEmpty(message = "请选择需要删除的供应商费用单！") List<String> ids) {

        settleFeeSheetService.deleteByIds(ids);

        return InvokeResultBuilder.success();
    }
}
