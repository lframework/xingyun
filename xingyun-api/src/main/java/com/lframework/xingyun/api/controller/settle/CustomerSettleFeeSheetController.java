package com.lframework.xingyun.api.controller.settle;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.api.bo.settle.fee.customer.GetCustomerSettleFeeSheetBo;
import com.lframework.xingyun.api.bo.settle.fee.customer.QueryCustomerSettleFeeSheetBo;
import com.lframework.xingyun.api.excel.settle.fee.customer.CustomerSettleFeeSheetExportModel;
import com.lframework.xingyun.settle.dto.fee.customer.CustomerSettleFeeSheetFullDto;
import com.lframework.xingyun.settle.entity.CustomerSettleFeeSheet;
import com.lframework.xingyun.settle.service.ICustomerSettleFeeSheetService;
import com.lframework.xingyun.settle.vo.fee.customer.ApprovePassCustomerSettleFeeSheetVo;
import com.lframework.xingyun.settle.vo.fee.customer.ApproveRefuseCustomerSettleFeeSheetVo;
import com.lframework.xingyun.settle.vo.fee.customer.BatchApprovePassCustomerSettleFeeSheetVo;
import com.lframework.xingyun.settle.vo.fee.customer.BatchApproveRefuseCustomerSettleFeeSheetVo;
import com.lframework.xingyun.settle.vo.fee.customer.CreateCustomerSettleFeeSheetVo;
import com.lframework.xingyun.settle.vo.fee.customer.QueryCustomerSettleFeeSheetVo;
import com.lframework.xingyun.settle.vo.fee.customer.UpdateCustomerSettleFeeSheetVo;
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
 * 客户费用单
 *
 * @author zmj
 */
@Api(tags = "客户费用单")
@Validated
@RestController
@RequestMapping("/customer/settle/feesheet")
public class CustomerSettleFeeSheetController extends DefaultBaseController {

    @Autowired
    private ICustomerSettleFeeSheetService customerSettleFeeSheetService;

    /**
     * 客户费用单列表
     */
    @ApiOperation("客户费用单列表")
    @PreAuthorize("@permission.valid('customer-settle:fee-sheet:query')")
    @GetMapping("/query")
    public InvokeResult<PageResult<QueryCustomerSettleFeeSheetBo>> query(
            @Valid QueryCustomerSettleFeeSheetVo vo) {

        PageResult<CustomerSettleFeeSheet> pageResult = customerSettleFeeSheetService.query(
                getPageIndex(vo), getPageSize(vo), vo);

        List<CustomerSettleFeeSheet> datas = pageResult.getDatas();
        List<QueryCustomerSettleFeeSheetBo> results = null;

        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(QueryCustomerSettleFeeSheetBo::new)
                    .collect(Collectors.toList());
        }

        return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
    }

    /**
     * 导出
     */
    @ApiOperation("导出")
    @PreAuthorize("@permission.valid('customer-settle:fee-sheet:export')")
    @PostMapping("/export")
    public void export(@Valid QueryCustomerSettleFeeSheetVo vo) {

        ExcelMultipartWriterSheetBuilder builder = ExcelUtil.multipartExportXls("客户费用单信息",
                CustomerSettleFeeSheetExportModel.class);

        try {
            int pageIndex = 1;
            while (true) {
                PageResult<CustomerSettleFeeSheet> pageResult = customerSettleFeeSheetService.query(
                        pageIndex, getExportSize(), vo);
                List<CustomerSettleFeeSheet> datas = pageResult.getDatas();
                List<CustomerSettleFeeSheetExportModel> models = datas.stream()
                        .map(CustomerSettleFeeSheetExportModel::new)
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
    @PreAuthorize("@permission.valid('customer-settle:fee-sheet:query')")
    @GetMapping
    public InvokeResult<GetCustomerSettleFeeSheetBo> findById(
            @NotBlank(message = "客户费用单ID不能为空！") String id) {

        CustomerSettleFeeSheetFullDto data = customerSettleFeeSheetService.getDetail(id);

        GetCustomerSettleFeeSheetBo result = new GetCustomerSettleFeeSheetBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 创建客户费用单
     */
    @ApiOperation("创建客户费用单")
    @PreAuthorize("@permission.valid('customer-settle:fee-sheet:add')")
    @PostMapping
    public InvokeResult<String> create(@RequestBody @Valid CreateCustomerSettleFeeSheetVo vo) {

        vo.validate();

        String id = customerSettleFeeSheetService.create(vo);

        return InvokeResultBuilder.success(id);
    }

    /**
     * 修改客户费用单
     */
    @ApiOperation("修改客户费用单")
    @PreAuthorize("@permission.valid('customer-settle:fee-sheet:modify')")
    @PutMapping
    public InvokeResult<Void> update(@RequestBody @Valid UpdateCustomerSettleFeeSheetVo vo) {

        vo.validate();

        customerSettleFeeSheetService.update(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 审核通过客户费用单
     */
    @ApiOperation("审核通过客户费用单")
    @PreAuthorize("@permission.valid('customer-settle:fee-sheet:approve')")
    @PatchMapping("/approve/pass")
    public InvokeResult<Void> approvePass(
            @RequestBody @Valid ApprovePassCustomerSettleFeeSheetVo vo) {

        customerSettleFeeSheetService.approvePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量审核通过客户费用单
     */
    @ApiOperation("批量审核通过客户费用单")
    @PreAuthorize("@permission.valid('customer-settle:fee-sheet:approve')")
    @PatchMapping("/approve/pass/batch")
    public InvokeResult<Void> batchApprovePass(
            @RequestBody @Valid BatchApprovePassCustomerSettleFeeSheetVo vo) {

        customerSettleFeeSheetService.batchApprovePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 直接审核通过客户费用单
     */
    @ApiOperation("直接审核通过客户费用单")
    @PreAuthorize("@permission.valid('customer-settle:fee-sheet:approve')")
    @PostMapping("/approve/pass/direct")
    public InvokeResult<Void> directApprovePass(
            @RequestBody @Valid CreateCustomerSettleFeeSheetVo vo) {

        customerSettleFeeSheetService.directApprovePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 审核拒绝客户费用单
     */
    @ApiOperation("审核拒绝客户费用单")
    @PreAuthorize("@permission.valid('customer-settle:fee-sheet:approve')")
    @PatchMapping("/approve/refuse")
    public InvokeResult<Void> approveRefuse(
            @RequestBody @Valid ApproveRefuseCustomerSettleFeeSheetVo vo) {

        customerSettleFeeSheetService.approveRefuse(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量审核拒绝客户费用单
     */
    @ApiOperation("批量审核拒绝客户费用单")
    @PreAuthorize("@permission.valid('customer-settle:fee-sheet:approve')")
    @PatchMapping("/approve/refuse/batch")
    public InvokeResult<Void> batchApproveRefuse(
            @RequestBody @Valid BatchApproveRefuseCustomerSettleFeeSheetVo vo) {

        customerSettleFeeSheetService.batchApproveRefuse(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 删除客户费用单
     */
    @ApiOperation("删除客户费用单")
    @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
    @PreAuthorize("@permission.valid('customer-settle:fee-sheet:delete')")
    @DeleteMapping
    public InvokeResult<Void> deleteById(@NotBlank(message = "客户费用单ID不能为空！") String id) {

        customerSettleFeeSheetService.deleteById(id);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量删除客户费用单
     */
    @ApiOperation("批量删除客户费用单")
    @PreAuthorize("@permission.valid('customer-settle:fee-sheet:delete')")
    @DeleteMapping("/batch")
    public InvokeResult<Void> deleteByIds(
            @ApiParam(value = "ID", required = true) @RequestBody @NotEmpty(message = "请选择需要删除的客户费用单！") List<String> ids) {

        customerSettleFeeSheetService.deleteByIds(ids);

        return InvokeResultBuilder.success();
    }
}
