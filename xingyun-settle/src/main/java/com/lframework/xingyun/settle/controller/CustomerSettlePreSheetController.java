package com.lframework.xingyun.settle.controller;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.settle.bo.pre.customer.GetCustomerSettlePreSheetBo;
import com.lframework.xingyun.settle.bo.pre.customer.QueryCustomerSettlePreSheetBo;
import com.lframework.xingyun.settle.excel.pre.customer.CustomerSettlePreSheetExportModel;
import com.lframework.xingyun.settle.dto.pre.customer.CustomerSettlePreSheetFullDto;
import com.lframework.xingyun.settle.entity.CustomerSettlePreSheet;
import com.lframework.xingyun.settle.service.CustomerSettlePreSheetService;
import com.lframework.xingyun.settle.vo.pre.customer.ApprovePassCustomerSettlePreSheetVo;
import com.lframework.xingyun.settle.vo.pre.customer.ApproveRefuseCustomerSettlePreSheetVo;
import com.lframework.xingyun.settle.vo.pre.customer.CreateCustomerSettlePreSheetVo;
import com.lframework.xingyun.settle.vo.pre.customer.QueryCustomerSettlePreSheetVo;
import com.lframework.xingyun.settle.vo.pre.customer.UpdateCustomerSettlePreSheetVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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
 * 客户预付款单
 *
 * @author zmj
 */
@Api(tags = "客户预付款单")
@Validated
@RestController
@RequestMapping("/customer/settle/presheet")
public class CustomerSettlePreSheetController extends DefaultBaseController {

    @Autowired
    private CustomerSettlePreSheetService settlePreSheetService;

    /**
     * 客户预付款单列表
     */
    @ApiOperation("客户预付款单列表")
    @HasPermission({"customer-settle:pre-sheet:query"})
    @GetMapping("/query")
    public InvokeResult<PageResult<QueryCustomerSettlePreSheetBo>> query(
            @Valid QueryCustomerSettlePreSheetVo vo) {

        PageResult<CustomerSettlePreSheet> pageResult = settlePreSheetService.query(
                getPageIndex(vo), getPageSize(vo), vo);

        List<CustomerSettlePreSheet> datas = pageResult.getDatas();
        List<QueryCustomerSettlePreSheetBo> results = null;

        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(QueryCustomerSettlePreSheetBo::new)
                    .collect(Collectors.toList());
        }

        return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
    }

    /**
     * 导出
     */
    @ApiOperation("导出")
    @HasPermission({"customer-settle:pre-sheet:export"})
    @PostMapping("/export")
    public void export(@Valid QueryCustomerSettlePreSheetVo vo) {

        ExcelMultipartWriterSheetBuilder builder = ExcelUtil.multipartExportXls("客户预付款单信息",
                CustomerSettlePreSheetExportModel.class);

        try {
            int pageIndex = 1;
            while (true) {
                PageResult<CustomerSettlePreSheet> pageResult = settlePreSheetService.query(
                        pageIndex, getExportSize(), vo);
                List<CustomerSettlePreSheet> datas = pageResult.getDatas();
                List<CustomerSettlePreSheetExportModel> models = datas.stream()
                        .map(CustomerSettlePreSheetExportModel::new)
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
    @HasPermission({"customer-settle:pre-sheet:query"})
    @GetMapping
    public InvokeResult<GetCustomerSettlePreSheetBo> findById(
            @NotBlank(message = "客户预付款单ID不能为空！") String id) {

        CustomerSettlePreSheetFullDto data = settlePreSheetService.getDetail(id);

        GetCustomerSettlePreSheetBo result = new GetCustomerSettlePreSheetBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 创建客户预付款单
     */
    @ApiOperation("创建客户预付款单")
    @HasPermission({"customer-settle:pre-sheet:add"})
    @PostMapping
    public InvokeResult<String> create(@RequestBody @Valid CreateCustomerSettlePreSheetVo vo) {

        vo.validate();

        String id = settlePreSheetService.create(vo);

        return InvokeResultBuilder.success(id);
    }

    /**
     * 修改客户预付款单
     */
    @ApiOperation("修改客户预付款单")
    @HasPermission({"customer-settle:pre-sheet:modify"})
    @PutMapping
    public InvokeResult<Void> update(@RequestBody @Valid UpdateCustomerSettlePreSheetVo vo) {

        vo.validate();

        settlePreSheetService.update(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 审核通过客户预付款单
     */
    @ApiOperation("审核通过客户预付款单")
    @HasPermission({"customer-settle:pre-sheet:approve"})
    @PatchMapping("/approve/pass")
    public InvokeResult<Void> approvePass(
            @RequestBody @Valid ApprovePassCustomerSettlePreSheetVo vo) {

        settlePreSheetService.approvePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 直接审核通过客户预付款单
     */
    @ApiOperation("直接审核通过客户预付款单")
    @HasPermission({"customer-settle:pre-sheet:approve"})
    @PostMapping("/approve/pass/direct")
    public InvokeResult<Void> directApprovePass(
            @RequestBody @Valid CreateCustomerSettlePreSheetVo vo) {

        settlePreSheetService.directApprovePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 审核拒绝客户预付款单
     */
    @ApiOperation("审核拒绝客户预付款单")
    @HasPermission({"customer-settle:pre-sheet:approve"})
    @PatchMapping("/approve/refuse")
    public InvokeResult<Void> approveRefuse(
            @RequestBody @Valid ApproveRefuseCustomerSettlePreSheetVo vo) {

        settlePreSheetService.approveRefuse(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 删除客户预付款单
     */
    @ApiOperation("删除客户预付款单")
    @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
    @HasPermission({"customer-settle:pre-sheet:delete"})
    @DeleteMapping
    public InvokeResult<Void> deleteById(@NotBlank(message = "客户预付款单ID不能为空！") String id) {

        settlePreSheetService.deleteById(id);

        return InvokeResultBuilder.success();
    }
}
