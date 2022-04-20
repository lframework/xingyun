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
import com.lframework.xingyun.api.bo.retail.returned.GetRetailReturnBo;
import com.lframework.xingyun.api.bo.retail.returned.PrintRetailReturnBo;
import com.lframework.xingyun.api.bo.retail.returned.QueryRetailReturnBo;
import com.lframework.xingyun.api.model.retail.returned.RetailReturnExportModel;
import com.lframework.xingyun.api.print.A4ExcelPortraitPrintBo;
import com.lframework.xingyun.sc.dto.retail.returned.RetailReturnFullDto;
import com.lframework.xingyun.sc.entity.RetailReturn;
import com.lframework.xingyun.sc.service.retail.IRetailReturnService;
import com.lframework.xingyun.sc.vo.retail.returned.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
 * 零售退单管理
 *
 * @author zmj
 */
@Api(tags = "零售退单管理")
@Validated
@RestController
@RequestMapping("/retail/return")
public class RetailReturnController extends DefaultBaseController {

    @Autowired
    private IRetailReturnService retailReturnService;

    /**
     * 打印
     */
    @ApiOperation("打印")
    @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
    @PreAuthorize("@permission.valid('retail:return:query')")
    @GetMapping("/print")
    public InvokeResult<A4ExcelPortraitPrintBo<PrintRetailReturnBo>> print(@NotBlank(message = "退单ID不能为空！") String id) {

        RetailReturnFullDto data = retailReturnService.getDetail(id);
        if (data == null) {
            throw new DefaultClientException("零售退货单不存在！");
        }

        PrintRetailReturnBo result = new PrintRetailReturnBo(data);

        A4ExcelPortraitPrintBo<PrintRetailReturnBo> printResult = new A4ExcelPortraitPrintBo<>(
                "print/retail-return.ftl", result);

        return InvokeResultBuilder.success(printResult);
    }

    /**
     * 退单列表
     */
    @ApiOperation("退单列表")
    @PreAuthorize("@permission.valid('retail:return:query')")
    @GetMapping("/query")
    public InvokeResult<PageResult<QueryRetailReturnBo>> query(@Valid QueryRetailReturnVo vo) {

        PageResult<RetailReturn> pageResult = retailReturnService.query(getPageIndex(vo), getPageSize(vo), vo);

        List<RetailReturn> datas = pageResult.getDatas();
        List<QueryRetailReturnBo> results = null;

        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(QueryRetailReturnBo::new).collect(Collectors.toList());
        }

        return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
    }

    /**
     * 导出
     */
    @ApiOperation("导出")
    @PreAuthorize("@permission.valid('retail:return:export')")
    @PostMapping("/export")
    public void export(@Valid QueryRetailReturnVo vo) {

        ExcelMultipartWriterSheetBuilder builder = ExcelUtil.multipartExportXls("零售退货单信息",
                RetailReturnExportModel.class);

        try {
            int pageIndex = 1;
            while (true) {
                PageResult<RetailReturn> pageResult = retailReturnService.query(pageIndex, getExportSize(), vo);
                List<RetailReturn> datas = pageResult.getDatas();
                List<RetailReturnExportModel> models = datas.stream().map(RetailReturnExportModel::new)
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
    @PreAuthorize("@permission.valid('retail:return:query')")
    @GetMapping
    public InvokeResult<GetRetailReturnBo> findById(@NotBlank(message = "退单ID不能为空！") String id) {

        RetailReturnFullDto data = retailReturnService.getDetail(id);

        GetRetailReturnBo result = new GetRetailReturnBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 创建
     */
    @ApiOperation("创建")
    @PreAuthorize("@permission.valid('retail:return:add')")
    @PostMapping
    public InvokeResult<String> create(@RequestBody @Valid CreateRetailReturnVo vo) {

        vo.validate();

        String id = retailReturnService.create(vo);

        return InvokeResultBuilder.success(id);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PreAuthorize("@permission.valid('retail:return:modify')")
    @PutMapping
    public InvokeResult<Void> update(@RequestBody @Valid UpdateRetailReturnVo vo) {

        vo.validate();

        retailReturnService.update(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 审核通过
     */
    @ApiOperation("审核通过")
    @PreAuthorize("@permission.valid('retail:return:approve')")
    @PatchMapping("/approve/pass")
    public InvokeResult<Void> approvePass(@RequestBody @Valid ApprovePassRetailReturnVo vo) {

        retailReturnService.approvePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量审核通过
     */
    @ApiOperation("批量审核通过")
    @PreAuthorize("@permission.valid('retail:return:approve')")
    @PatchMapping("/approve/pass/batch")
    public InvokeResult<Void> batchApprovePass(@RequestBody @Valid BatchApprovePassRetailReturnVo vo) {

        retailReturnService.batchApprovePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 直接审核通过
     */
    @ApiOperation("直接审核通过")
    @PreAuthorize("@permission.valid('retail:return:approve')")
    @PostMapping("/approve/pass/direct")
    public InvokeResult<Void> directApprovePass(@RequestBody @Valid CreateRetailReturnVo vo) {

        retailReturnService.directApprovePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 审核拒绝
     */
    @ApiOperation("审核拒绝")
    @PreAuthorize("@permission.valid('retail:return:approve')")
    @PatchMapping("/approve/refuse")
    public InvokeResult<Void> approveRefuse(@RequestBody @Valid ApproveRefuseRetailReturnVo vo) {

        retailReturnService.approveRefuse(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量审核拒绝
     */
    @ApiOperation("批量审核拒绝")
    @PreAuthorize("@permission.valid('retail:return:approve')")
    @PatchMapping("/approve/refuse/batch")
    public InvokeResult<Void> batchApproveRefuse(@RequestBody @Valid BatchApproveRefuseRetailReturnVo vo) {

        retailReturnService.batchApproveRefuse(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
    @PreAuthorize("@permission.valid('retail:return:delete')")
    @DeleteMapping
    public InvokeResult<Void> deleteById(@NotBlank(message = "零售退货单ID不能为空！") String id) {

        retailReturnService.deleteById(id);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量删除
     */
    @ApiOperation("批量删除")
    @PreAuthorize("@permission.valid('retail:return:delete')")
    @DeleteMapping("/batch")
    public InvokeResult<Void> deleteByIds(
            @ApiParam(value = "ID", required = true) @RequestBody @NotEmpty(message = "请选择需要删除的零售退货单！") List<String> ids) {

        retailReturnService.deleteByIds(ids);

        return InvokeResultBuilder.success();
    }
}
