package com.lframework.xingyun.api.controller.stock.take;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.api.bo.stock.take.sheet.QueryTakeStockSheetBo;
import com.lframework.xingyun.api.bo.stock.take.sheet.TakeStockSheetFullBo;
import com.lframework.xingyun.api.bo.stock.take.sheet.TakeStockSheetProductBo;
import com.lframework.xingyun.api.excel.stock.take.sheet.TakeStockSheetExportModel;
import com.lframework.xingyun.basedata.dto.product.info.TakeStockSheetProductDto;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.vo.product.info.QueryTakeStockSheetProductVo;
import com.lframework.xingyun.sc.dto.stock.take.sheet.TakeStockSheetFullDto;
import com.lframework.xingyun.sc.entity.TakeStockPlan;
import com.lframework.xingyun.sc.entity.TakeStockSheet;
import com.lframework.xingyun.sc.enums.TakeStockPlanType;
import com.lframework.xingyun.sc.service.stock.take.ITakeStockPlanService;
import com.lframework.xingyun.sc.service.stock.take.ITakeStockSheetService;
import com.lframework.xingyun.sc.vo.stock.take.sheet.ApprovePassTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.sheet.ApproveRefuseTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.sheet.BatchApprovePassTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.sheet.BatchApproveRefuseTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.sheet.CreateTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.sheet.QueryTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.sheet.UpdateTakeStockSheetVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
 * ????????? Controller
 *
 * @author zmj
 */
@Api(tags = "?????????")
@Validated
@RestController
@RequestMapping("/stock/take/sheet")
public class TakeStockSheetController extends DefaultBaseController {

    @Autowired
    private ITakeStockSheetService takeStockSheetService;

    @Autowired
    private IProductService productService;

    @Autowired
    private ITakeStockPlanService takeStockPlanService;

    /**
     * ????????????
     */
    @ApiOperation("????????????")
    @PreAuthorize("@permission.valid('stock:take:sheet:query')")
    @GetMapping("/query")
    public InvokeResult<PageResult<QueryTakeStockSheetBo>> query(@Valid QueryTakeStockSheetVo vo) {

        PageResult<TakeStockSheet> pageResult = takeStockSheetService.query(getPageIndex(vo), getPageSize(vo), vo);

        List<TakeStockSheet> datas = pageResult.getDatas();
        List<QueryTakeStockSheetBo> results = null;

        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(QueryTakeStockSheetBo::new).collect(Collectors.toList());
        }

        return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
    }

    /**
     * ????????????
     */
    @ApiOperation("????????????")
    @PreAuthorize("@permission.valid('stock:take:sheet:export')")
    @PostMapping("/export")
    public void export(@Valid QueryTakeStockSheetVo vo) {

        ExcelMultipartWriterSheetBuilder builder = ExcelUtil.multipartExportXls("?????????????????????",
                TakeStockSheetExportModel.class);

        try {
            int pageIndex = 1;
            while (true) {
                PageResult<TakeStockSheet> pageResult = takeStockSheetService.query(pageIndex, getExportSize(), vo);
                List<TakeStockSheet> datas = pageResult.getDatas();
                List<TakeStockSheetExportModel> models = datas.stream().map(TakeStockSheetExportModel::new)
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
    @PreAuthorize("@permission.valid('stock:take:sheet:query')")
    @GetMapping("/detail")
    public InvokeResult<TakeStockSheetFullBo> getDetail(@NotBlank(message = "id???????????????") String id) {

        TakeStockSheetFullDto data = takeStockSheetService.getDetail(id);
        if (data == null) {
            throw new DefaultClientException("?????????????????????");
        }

        TakeStockSheetFullBo result = new TakeStockSheetFullBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * ?????????????????????????????????
     */
    @ApiOperation("?????????????????????????????????")
    @ApiImplicitParams({@ApiImplicitParam(value = "????????????ID", name = "planId", paramType = "query", required = true),
            @ApiImplicitParam(value = "?????????", name = "condition", paramType = "query", required = true)})
    @PreAuthorize("@permission.valid('stock:take:sheet:add', 'stock:take:sheet:modify')")
    @GetMapping("/product/search")
    public InvokeResult<List<TakeStockSheetProductBo>> searchProducts(@NotBlank(message = "????????????ID???????????????") String planId,
            String condition) {

        if (StringUtil.isBlank(condition)) {
            return InvokeResultBuilder.success(Collections.EMPTY_LIST);
        }

        TakeStockPlan takeStockPlan = takeStockPlanService.getById(planId);
        if (takeStockPlan.getTakeType() == TakeStockPlanType.SIMPLE) {
            planId = null;
        }
        PageResult<TakeStockSheetProductDto> pageResult = productService.queryTakeStockByCondition(getPageIndex(),
                getPageSize(), planId, condition);
        List<TakeStockSheetProductBo> results = Collections.EMPTY_LIST;
        List<TakeStockSheetProductDto> datas = pageResult.getDatas();
        if (!CollectionUtil.isEmpty(datas)) {
            String finalPlanId = planId;
            results = datas.stream().map(t -> new TakeStockSheetProductBo(t, finalPlanId, takeStockPlan.getScId()))
                    .collect(Collectors.toList());
        }

        return InvokeResultBuilder.success(results);
    }

    /**
     * ??????????????????
     */
    @ApiOperation("??????????????????")
    @PreAuthorize("@permission.valid('stock:take:sheet:add', 'stock:take:sheet:modify')")
    @GetMapping("/product/list")
    public InvokeResult<PageResult<TakeStockSheetProductBo>> queryProductList(@Valid QueryTakeStockSheetProductVo vo) {

        TakeStockPlan takeStockPlan = takeStockPlanService.getById(vo.getPlanId());
        if (takeStockPlan.getTakeType() == TakeStockPlanType.SIMPLE) {
            vo.setPlanId(null);
        }

        PageResult<TakeStockSheetProductDto> pageResult = productService.queryTakeStockList(getPageIndex(),
                getPageSize(), vo);
        List<TakeStockSheetProductBo> results = null;
        List<TakeStockSheetProductDto> datas = pageResult.getDatas();

        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(t -> new TakeStockSheetProductBo(t, vo.getPlanId(), takeStockPlan.getScId()))
                    .collect(Collectors.toList());
        }

        return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
    }

    /**
     * ??????
     */
    @ApiOperation("??????")
    @PreAuthorize("@permission.valid('stock:take:sheet:add')")
    @PostMapping
    public InvokeResult<Void> create(@Valid @RequestBody CreateTakeStockSheetVo vo) {

        takeStockSheetService.create(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * ??????
     */
    @ApiOperation("??????")
    @PreAuthorize("@permission.valid('stock:take:sheet:modify')")
    @PutMapping
    public InvokeResult<Void> update(@Valid @RequestBody UpdateTakeStockSheetVo vo) {

        takeStockSheetService.update(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * ??????????????????
     */
    @ApiOperation("??????????????????")
    @PreAuthorize("@permission.valid('stock:take:sheet:approve')")
    @PostMapping("/approve/direct")
    public InvokeResult<Void> directApprovePass(@Valid @RequestBody CreateTakeStockSheetVo vo) {

        takeStockSheetService.directApprovePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * ????????????
     */
    @ApiOperation("????????????")
    @PreAuthorize("@permission.valid('stock:take:sheet:approve')")
    @PatchMapping("/approve/pass")
    public InvokeResult<Void> approvePass(@Valid ApprovePassTakeStockSheetVo vo) {

        takeStockSheetService.approvePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * ??????????????????
     */
    @ApiOperation("??????????????????")
    @PreAuthorize("@permission.valid('stock:take:sheet:approve')")
    @PatchMapping("/approve/pass/batch")
    public InvokeResult<Void> batchApprovePass(@Valid @RequestBody BatchApprovePassTakeStockSheetVo vo) {

        takeStockSheetService.batchApprovePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * ????????????
     */
    @ApiOperation("????????????")
    @PreAuthorize("@permission.valid('stock:take:sheet:approve')")
    @PatchMapping("/approve/refuse")
    public InvokeResult<Void> approveRefuse(@Valid ApproveRefuseTakeStockSheetVo vo) {

        takeStockSheetService.approveRefuse(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * ??????????????????
     */
    @ApiOperation("??????????????????")
    @PreAuthorize("@permission.valid('stock:take:sheet:approve')")
    @PatchMapping("/approve/refuse/batch")
    public InvokeResult<Void> batchApprovePass(@Valid @RequestBody BatchApproveRefuseTakeStockSheetVo vo) {

        takeStockSheetService.batchApproveRefuse(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * ????????????
     */
    @ApiOperation("????????????")
    @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
    @PreAuthorize("@permission.valid('stock:take:sheet:cancel-approve')")
    @PatchMapping("/approve/cancel")
    public InvokeResult<Void> cancelApprovePass(@NotBlank(message = "ID???????????????") String id) {

        takeStockSheetService.cancelApprovePass(id);

        return InvokeResultBuilder.success();
    }

    /**
     * ??????
     */
    @ApiOperation("??????")
    @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
    @PreAuthorize("@permission.valid('stock:take:sheet:delete')")
    @DeleteMapping
    public InvokeResult<Void> deleteById(@NotBlank(message = "ID???????????????") String id) {

        takeStockSheetService.deleteById(id);

        return InvokeResultBuilder.success();
    }

    /**
     * ??????
     */
    @ApiOperation("??????")
    @PreAuthorize("@permission.valid('stock:take:sheet:delete')")
    @DeleteMapping("/batch")
    public InvokeResult<Void> batchDelete(
            @ApiParam(value = "ID", required = true) @NotEmpty(message = "?????????????????????????????????????????????") @RequestBody List<String> ids) {

        takeStockSheetService.batchDelete(ids);

        return InvokeResultBuilder.success();
    }

}
