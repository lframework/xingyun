package com.lframework.xingyun.settle.controller;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.settle.bo.check.GetSettleCheckSheetBo;
import com.lframework.xingyun.settle.bo.check.QuerySettleCheckSheetBo;
import com.lframework.xingyun.settle.bo.check.SettleCheckBizItemBo;
import com.lframework.xingyun.settle.excel.check.SettleCheckSheetExportModel;
import com.lframework.xingyun.settle.dto.check.SettleCheckBizItemDto;
import com.lframework.xingyun.settle.dto.check.SettleCheckSheetFullDto;
import com.lframework.xingyun.settle.entity.SettleCheckSheet;
import com.lframework.xingyun.settle.service.SettleCheckSheetService;
import com.lframework.xingyun.settle.vo.check.ApprovePassSettleCheckSheetVo;
import com.lframework.xingyun.settle.vo.check.ApproveRefuseSettleCheckSheetVo;
import com.lframework.xingyun.settle.vo.check.BatchApprovePassSettleCheckSheetVo;
import com.lframework.xingyun.settle.vo.check.BatchApproveRefuseSettleCheckSheetVo;
import com.lframework.xingyun.settle.vo.check.CreateSettleCheckSheetVo;
import com.lframework.xingyun.settle.vo.check.QuerySettleCheckSheetVo;
import com.lframework.xingyun.settle.vo.check.QueryUnCheckBizItemVo;
import com.lframework.xingyun.settle.vo.check.UpdateSettleCheckSheetVo;
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
import com.lframework.starter.web.annotations.security.HasPermission;
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
 * 供应商对账单
 *
 * @author zmj
 */
@Api(tags = "供应商对账单")
@Validated
@RestController
@RequestMapping("/settle/checksheet")
public class SettleCheckSheetController extends DefaultBaseController {

    @Autowired
    private SettleCheckSheetService settleCheckSheetService;

    /**
     * 供应商对账单列表
     */
    @ApiOperation("供应商对账单列表")
    @HasPermission({"settle:check-sheet:query"})
    @GetMapping("/query")
    public InvokeResult<PageResult<QuerySettleCheckSheetBo>> query(@Valid QuerySettleCheckSheetVo vo) {

        PageResult<SettleCheckSheet> pageResult = settleCheckSheetService.query(getPageIndex(vo), getPageSize(vo), vo);

        List<SettleCheckSheet> datas = pageResult.getDatas();
        List<QuerySettleCheckSheetBo> results = null;

        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(QuerySettleCheckSheetBo::new).collect(Collectors.toList());
        }

        return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
    }

    /**
     * 导出
     */
    @ApiOperation("导出")
    @HasPermission({"settle:check-sheet:export"})
    @PostMapping("/export")
    public void export(@Valid QuerySettleCheckSheetVo vo) {

        ExcelMultipartWriterSheetBuilder builder = ExcelUtil.multipartExportXls("供应商对账单信息",
                SettleCheckSheetExportModel.class);

        try {
            int pageIndex = 1;
            while (true) {
                PageResult<SettleCheckSheet> pageResult = settleCheckSheetService.query(pageIndex, getExportSize(), vo);
                List<SettleCheckSheet> datas = pageResult.getDatas();
                List<SettleCheckSheetExportModel> models = datas.stream().map(SettleCheckSheetExportModel::new)
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
    @HasPermission({"settle:check-sheet:query"})
    @GetMapping
    public InvokeResult<GetSettleCheckSheetBo> findById(@NotBlank(message = "供应商对账单ID不能为空！") String id) {

        SettleCheckSheetFullDto data = settleCheckSheetService.getDetail(id);

        GetSettleCheckSheetBo result = new GetSettleCheckSheetBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 创建供应商对账单
     */
    @ApiOperation("创建供应商对账单")
    @HasPermission({"settle:check-sheet:add"})
    @PostMapping
    public InvokeResult<String> create(@RequestBody @Valid CreateSettleCheckSheetVo vo) {

        vo.validate();

        String id = settleCheckSheetService.create(vo);

        return InvokeResultBuilder.success(id);
    }

    /**
     * 修改供应商对账单
     */
    @ApiOperation("修改供应商对账单")
    @HasPermission({"settle:check-sheet:modify"})
    @PutMapping
    public InvokeResult<Void> update(@RequestBody @Valid UpdateSettleCheckSheetVo vo) {

        vo.validate();

        settleCheckSheetService.update(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 审核通过供应商对账单
     */
    @ApiOperation("审核通过供应商对账单")
    @HasPermission({"settle:check-sheet:approve"})
    @PatchMapping("/approve/pass")
    public InvokeResult<Void> approvePass(@RequestBody @Valid ApprovePassSettleCheckSheetVo vo) {

        settleCheckSheetService.approvePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量审核通过供应商对账单
     */
    @ApiOperation("批量审核通过供应商对账单")
    @HasPermission({"settle:check-sheet:approve"})
    @PatchMapping("/approve/pass/batch")
    public InvokeResult<Void> batchApprovePass(@RequestBody @Valid BatchApprovePassSettleCheckSheetVo vo) {

        settleCheckSheetService.batchApprovePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 直接审核通过供应商对账单
     */
    @ApiOperation("直接审核通过供应商对账单")
    @HasPermission({"settle:check-sheet:approve"})
    @PostMapping("/approve/pass/direct")
    public InvokeResult<Void> directApprovePass(@RequestBody @Valid CreateSettleCheckSheetVo vo) {

        settleCheckSheetService.directApprovePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 审核拒绝供应商对账单
     */
    @ApiOperation("审核拒绝供应商对账单")
    @HasPermission({"settle:check-sheet:approve"})
    @PatchMapping("/approve/refuse")
    public InvokeResult<Void> approveRefuse(@RequestBody @Valid ApproveRefuseSettleCheckSheetVo vo) {

        settleCheckSheetService.approveRefuse(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量审核拒绝供应商对账单
     */
    @ApiOperation("批量审核拒绝供应商对账单")
    @HasPermission({"settle:check-sheet:approve"})
    @PatchMapping("/approve/refuse/batch")
    public InvokeResult<Void> batchApproveRefuse(@RequestBody @Valid BatchApproveRefuseSettleCheckSheetVo vo) {

        settleCheckSheetService.batchApproveRefuse(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 删除供应商对账单
     */
    @ApiOperation("删除供应商对账单")
    @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
    @HasPermission({"settle:check-sheet:delete"})
    @DeleteMapping
    public InvokeResult<Void> deleteById(@NotBlank(message = "供应商对账单ID不能为空！") String id) {

        settleCheckSheetService.deleteById(id);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量删除供应商对账单
     */
    @ApiOperation("批量删除供应商对账单")
    @HasPermission({"settle:check-sheet:delete"})
    @DeleteMapping("/batch")
    public InvokeResult<Void> deleteByIds(
            @ApiParam(value = "ID", required = true) @RequestBody @NotEmpty(message = "请选择需要删除的供应商对账单！") List<String> ids) {

        settleCheckSheetService.deleteByIds(ids);

        return InvokeResultBuilder.success();
    }

    /**
     * 查询未对账的业务单据
     */
    @ApiOperation("查询未对账的业务单据")
    @HasPermission({"settle:check-sheet:add", "settle:check-sheet:modify"})
    @GetMapping("/uncheck-items")
    public InvokeResult<List<SettleCheckBizItemBo>> getUnCheckItems(@Valid QueryUnCheckBizItemVo vo) {

        List<SettleCheckBizItemDto> results = settleCheckSheetService.getUnCheckBizItems(vo);
        List<SettleCheckBizItemBo> datas = CollectionUtil.emptyList();
        if (!CollectionUtil.isEmpty(results)) {
            datas = results.stream().map(SettleCheckBizItemBo::new).collect(Collectors.toList());
        }

        return InvokeResultBuilder.success(datas);
    }
}
