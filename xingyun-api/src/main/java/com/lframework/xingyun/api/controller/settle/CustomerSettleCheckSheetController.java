package com.lframework.xingyun.api.controller.settle;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.api.bo.settle.check.customer.CustomerSettleCheckBizItemBo;
import com.lframework.xingyun.api.bo.settle.check.customer.GetCustomerSettleCheckSheetBo;
import com.lframework.xingyun.api.bo.settle.check.customer.QueryCustomerSettleCheckSheetBo;
import com.lframework.xingyun.api.excel.settle.check.customer.CustomerSettleCheckSheetExportModel;
import com.lframework.xingyun.settle.dto.check.customer.CustomerSettleCheckBizItemDto;
import com.lframework.xingyun.settle.dto.check.customer.CustomerSettleCheckSheetFullDto;
import com.lframework.xingyun.settle.entity.CustomerSettleCheckSheet;
import com.lframework.xingyun.settle.service.ICustomerSettleCheckSheetService;
import com.lframework.xingyun.settle.vo.check.customer.ApprovePassCustomerSettleCheckSheetVo;
import com.lframework.xingyun.settle.vo.check.customer.ApproveRefuseCustomerSettleCheckSheetVo;
import com.lframework.xingyun.settle.vo.check.customer.BatchApprovePassCustomerSettleCheckSheetVo;
import com.lframework.xingyun.settle.vo.check.customer.BatchApproveRefuseCustomerSettleCheckSheetVo;
import com.lframework.xingyun.settle.vo.check.customer.CreateCustomerSettleCheckSheetVo;
import com.lframework.xingyun.settle.vo.check.customer.QueryCustomerSettleCheckSheetVo;
import com.lframework.xingyun.settle.vo.check.customer.QueryCustomerUnCheckBizItemVo;
import com.lframework.xingyun.settle.vo.check.customer.UpdateCustomerSettleCheckSheetVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Collections;
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
 * ???????????????
 *
 * @author zmj
 */
@Api(tags = "???????????????")
@Validated
@RestController
@RequestMapping("/customer/settle/checksheet")
public class CustomerSettleCheckSheetController extends DefaultBaseController {

    @Autowired
    private ICustomerSettleCheckSheetService customerSettleCheckSheetService;

    /**
     * ?????????????????????
     */
    @ApiOperation("?????????????????????")
    @PreAuthorize("@permission.valid('customer-settle:check-sheet:query')")
    @GetMapping("/query")
    public InvokeResult<PageResult<QueryCustomerSettleCheckSheetBo>> query(
            @Valid QueryCustomerSettleCheckSheetVo vo) {

        PageResult<CustomerSettleCheckSheet> pageResult = customerSettleCheckSheetService.query(
                getPageIndex(vo), getPageSize(vo), vo);

        List<CustomerSettleCheckSheet> datas = pageResult.getDatas();
        List<QueryCustomerSettleCheckSheetBo> results = null;

        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(QueryCustomerSettleCheckSheetBo::new)
                    .collect(Collectors.toList());
        }

        return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
    }

    /**
     * ??????
     */
    @ApiOperation("??????")
    @PreAuthorize("@permission.valid('customer-settle:check-sheet:export')")
    @PostMapping("/export")
    public void export(@Valid QueryCustomerSettleCheckSheetVo vo) {

        ExcelMultipartWriterSheetBuilder builder = ExcelUtil.multipartExportXls("?????????????????????",
                CustomerSettleCheckSheetExportModel.class);

        try {
            int pageIndex = 1;
            while (true) {
                PageResult<CustomerSettleCheckSheet> pageResult = customerSettleCheckSheetService.query(
                        pageIndex, getExportSize(), vo);
                List<CustomerSettleCheckSheet> datas = pageResult.getDatas();
                List<CustomerSettleCheckSheetExportModel> models = datas.stream()
                        .map(CustomerSettleCheckSheetExportModel::new)
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
    @PreAuthorize("@permission.valid('customer-settle:check-sheet:query')")
    @GetMapping
    public InvokeResult<GetCustomerSettleCheckSheetBo> findById(
            @NotBlank(message = "???????????????ID???????????????") String id) {

        CustomerSettleCheckSheetFullDto data = customerSettleCheckSheetService.getDetail(id);

        GetCustomerSettleCheckSheetBo result = new GetCustomerSettleCheckSheetBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * ?????????????????????
     */
    @ApiOperation("?????????????????????")
    @PreAuthorize("@permission.valid('customer-settle:check-sheet:add')")
    @PostMapping
    public InvokeResult<String> create(@RequestBody @Valid CreateCustomerSettleCheckSheetVo vo) {

        vo.validate();

        String id = customerSettleCheckSheetService.create(vo);

        return InvokeResultBuilder.success(id);
    }

    /**
     * ?????????????????????
     */
    @ApiOperation("?????????????????????")
    @PreAuthorize("@permission.valid('customer-settle:check-sheet:modify')")
    @PutMapping
    public InvokeResult<Void> update(@RequestBody @Valid UpdateCustomerSettleCheckSheetVo vo) {

        vo.validate();

        customerSettleCheckSheetService.update(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * ???????????????????????????
     */
    @ApiOperation("???????????????????????????")
    @PreAuthorize("@permission.valid('customer-settle:check-sheet:approve')")
    @PatchMapping("/approve/pass")
    public InvokeResult<Void> approvePass(
            @RequestBody @Valid ApprovePassCustomerSettleCheckSheetVo vo) {

        customerSettleCheckSheetService.approvePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * ?????????????????????????????????
     */
    @ApiOperation("?????????????????????????????????")
    @PreAuthorize("@permission.valid('customer-settle:check-sheet:approve')")
    @PatchMapping("/approve/pass/batch")
    public InvokeResult<Void> batchApprovePass(
            @RequestBody @Valid BatchApprovePassCustomerSettleCheckSheetVo vo) {

        customerSettleCheckSheetService.batchApprovePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * ?????????????????????????????????
     */
    @ApiOperation("?????????????????????????????????")
    @PreAuthorize("@permission.valid('customer-settle:check-sheet:approve')")
    @PostMapping("/approve/pass/direct")
    public InvokeResult<Void> directApprovePass(
            @RequestBody @Valid CreateCustomerSettleCheckSheetVo vo) {

        customerSettleCheckSheetService.directApprovePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * ???????????????????????????
     */
    @ApiOperation("???????????????????????????")
    @PreAuthorize("@permission.valid('customer-settle:check-sheet:approve')")
    @PatchMapping("/approve/refuse")
    public InvokeResult<Void> approveRefuse(
            @RequestBody @Valid ApproveRefuseCustomerSettleCheckSheetVo vo) {

        customerSettleCheckSheetService.approveRefuse(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * ?????????????????????????????????
     */
    @ApiOperation("?????????????????????????????????")
    @PreAuthorize("@permission.valid('customer-settle:check-sheet:approve')")
    @PatchMapping("/approve/refuse/batch")
    public InvokeResult<Void> batchApproveRefuse(
            @RequestBody @Valid BatchApproveRefuseCustomerSettleCheckSheetVo vo) {

        customerSettleCheckSheetService.batchApproveRefuse(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * ?????????????????????
     */
    @ApiOperation("?????????????????????")
    @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
    @PreAuthorize("@permission.valid('customer-settle:check-sheet:delete')")
    @DeleteMapping
    public InvokeResult<Void> deleteById(@NotBlank(message = "???????????????ID???????????????") String id) {

        customerSettleCheckSheetService.deleteById(id);

        return InvokeResultBuilder.success();
    }

    /**
     * ???????????????????????????
     */
    @ApiOperation("???????????????????????????")
    @PreAuthorize("@permission.valid('customer-settle:check-sheet:delete')")
    @DeleteMapping("/batch")
    public InvokeResult<Void> deleteByIds(
            @ApiParam(value = "ID", required = true) @RequestBody @NotEmpty(message = "??????????????????????????????????????????") List<String> ids) {

        customerSettleCheckSheetService.deleteByIds(ids);

        return InvokeResultBuilder.success();
    }

    /**
     * ??????????????????????????????
     */
    @ApiOperation("??????????????????????????????")
    @PreAuthorize("@permission.valid('customer-settle:check-sheet:add', 'customer-settle:check-sheet:modify')")
    @GetMapping("/uncheck-items")
    public InvokeResult<List<CustomerSettleCheckBizItemBo>> getUnCheckItems(
            @Valid QueryCustomerUnCheckBizItemVo vo) {

        List<CustomerSettleCheckBizItemDto> results = customerSettleCheckSheetService.getUnCheckBizItems(
                vo);
        List<CustomerSettleCheckBizItemBo> datas = Collections.EMPTY_LIST;
        if (!CollectionUtil.isEmpty(results)) {
            datas = results.stream().map(CustomerSettleCheckBizItemBo::new)
                    .collect(Collectors.toList());
        }

        return InvokeResultBuilder.success(datas);
    }
}
