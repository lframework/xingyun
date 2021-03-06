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
import com.lframework.xingyun.api.excel.settle.fee.SettleFeeSheetExportModel;
import com.lframework.xingyun.settle.dto.fee.SettleFeeSheetFullDto;
import com.lframework.xingyun.settle.entity.SettleFeeSheet;
import com.lframework.xingyun.settle.service.ISettleFeeSheetService;
import com.lframework.xingyun.settle.vo.fee.ApprovePassSettleFeeSheetVo;
import com.lframework.xingyun.settle.vo.fee.ApproveRefuseSettleFeeSheetVo;
import com.lframework.xingyun.settle.vo.fee.BatchApprovePassSettleFeeSheetVo;
import com.lframework.xingyun.settle.vo.fee.BatchApproveRefuseSettleFeeSheetVo;
import com.lframework.xingyun.settle.vo.fee.CreateSettleFeeSheetVo;
import com.lframework.xingyun.settle.vo.fee.QuerySettleFeeSheetVo;
import com.lframework.xingyun.settle.vo.fee.UpdateSettleFeeSheetVo;
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
 * ??????????????????
 *
 * @author zmj
 */
@Api(tags = "??????????????????")
@Validated
@RestController
@RequestMapping("/settle/feesheet")
public class SettleFeeSheetController extends DefaultBaseController {

    @Autowired
    private ISettleFeeSheetService settleFeeSheetService;

    /**
     * ????????????????????????
     */
    @ApiOperation("????????????????????????")
    @PreAuthorize("@permission.valid('settle:fee-sheet:query')")
    @GetMapping("/query")
    public InvokeResult<PageResult<QuerySettleFeeSheetBo>> query(@Valid QuerySettleFeeSheetVo vo) {

        PageResult<SettleFeeSheet> pageResult = settleFeeSheetService.query(getPageIndex(vo), getPageSize(vo), vo);

        List<SettleFeeSheet> datas = pageResult.getDatas();
        List<QuerySettleFeeSheetBo> results = null;

        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(QuerySettleFeeSheetBo::new).collect(Collectors.toList());
        }

        return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
    }

    /**
     * ??????
     */
    @ApiOperation("??????")
    @PreAuthorize("@permission.valid('settle:fee-sheet:export')")
    @PostMapping("/export")
    public void export(@Valid QuerySettleFeeSheetVo vo) {

        ExcelMultipartWriterSheetBuilder builder = ExcelUtil.multipartExportXls("????????????????????????",
                SettleFeeSheetExportModel.class);

        try {
            int pageIndex = 1;
            while (true) {
                PageResult<SettleFeeSheet> pageResult = settleFeeSheetService.query(pageIndex, getExportSize(), vo);
                List<SettleFeeSheet> datas = pageResult.getDatas();
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
     * ??????ID??????
     */
    @ApiOperation("??????ID??????")
    @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
    @PreAuthorize("@permission.valid('settle:fee-sheet:query')")
    @GetMapping
    public InvokeResult<GetSettleFeeSheetBo> findById(@NotBlank(message = "??????????????????ID???????????????") String id) {

        SettleFeeSheetFullDto data = settleFeeSheetService.getDetail(id);

        GetSettleFeeSheetBo result = new GetSettleFeeSheetBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * ????????????????????????
     */
    @ApiOperation("????????????????????????")
    @PreAuthorize("@permission.valid('settle:fee-sheet:add')")
    @PostMapping
    public InvokeResult<String> create(@RequestBody @Valid CreateSettleFeeSheetVo vo) {

        vo.validate();

        String id = settleFeeSheetService.create(vo);

        return InvokeResultBuilder.success(id);
    }

    /**
     * ????????????????????????
     */
    @ApiOperation("????????????????????????")
    @PreAuthorize("@permission.valid('settle:fee-sheet:modify')")
    @PutMapping
    public InvokeResult<Void> update(@RequestBody @Valid UpdateSettleFeeSheetVo vo) {

        vo.validate();

        settleFeeSheetService.update(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * ??????????????????????????????
     */
    @ApiOperation("??????????????????????????????")
    @PreAuthorize("@permission.valid('settle:fee-sheet:approve')")
    @PatchMapping("/approve/pass")
    public InvokeResult<Void> approvePass(@RequestBody @Valid ApprovePassSettleFeeSheetVo vo) {

        settleFeeSheetService.approvePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * ????????????????????????????????????
     */
    @ApiOperation("????????????????????????????????????")
    @PreAuthorize("@permission.valid('settle:fee-sheet:approve')")
    @PatchMapping("/approve/pass/batch")
    public InvokeResult<Void> batchApprovePass(@RequestBody @Valid BatchApprovePassSettleFeeSheetVo vo) {

        settleFeeSheetService.batchApprovePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * ????????????????????????????????????
     */
    @ApiOperation("????????????????????????????????????")
    @PreAuthorize("@permission.valid('settle:fee-sheet:approve')")
    @PostMapping("/approve/pass/direct")
    public InvokeResult<Void> directApprovePass(@RequestBody @Valid CreateSettleFeeSheetVo vo) {

        settleFeeSheetService.directApprovePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * ??????????????????????????????
     */
    @ApiOperation("??????????????????????????????")
    @PreAuthorize("@permission.valid('settle:fee-sheet:approve')")
    @PatchMapping("/approve/refuse")
    public InvokeResult<Void> approveRefuse(@RequestBody @Valid ApproveRefuseSettleFeeSheetVo vo) {

        settleFeeSheetService.approveRefuse(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * ????????????????????????????????????
     */
    @ApiOperation("????????????????????????????????????")
    @PreAuthorize("@permission.valid('settle:fee-sheet:approve')")
    @PatchMapping("/approve/refuse/batch")
    public InvokeResult<Void> batchApproveRefuse(@RequestBody @Valid BatchApproveRefuseSettleFeeSheetVo vo) {

        settleFeeSheetService.batchApproveRefuse(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * ????????????????????????
     */
    @ApiOperation("????????????????????????")
    @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
    @PreAuthorize("@permission.valid('settle:fee-sheet:delete')")
    @DeleteMapping
    public InvokeResult<Void> deleteById(@NotBlank(message = "??????????????????ID???????????????") String id) {

        settleFeeSheetService.deleteById(id);

        return InvokeResultBuilder.success();
    }

    /**
     * ??????????????????????????????
     */
    @ApiOperation("??????????????????????????????")
    @PreAuthorize("@permission.valid('settle:fee-sheet:delete')")
    @DeleteMapping("/batch")
    public InvokeResult<Void> deleteByIds(
            @ApiParam(value = "ID", required = true) @RequestBody @NotEmpty(message = "?????????????????????????????????????????????") List<String> ids) {

        settleFeeSheetService.deleteByIds(ids);

        return InvokeResultBuilder.success();
    }
}
