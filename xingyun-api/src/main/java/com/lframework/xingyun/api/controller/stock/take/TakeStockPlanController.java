package com.lframework.xingyun.api.controller.stock.take;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.components.qrtz.QrtzHandler;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.CronUtil;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.api.bo.stock.take.plan.GetTakeStockPlanBo;
import com.lframework.xingyun.api.bo.stock.take.plan.QueryTakeStockPlanBo;
import com.lframework.xingyun.api.bo.stock.take.plan.QueryTakeStockPlanProductBo;
import com.lframework.xingyun.api.bo.stock.take.plan.TakeStockPlanFullBo;
import com.lframework.xingyun.api.excel.stock.take.plan.TakeStockPlanExportModel;
import com.lframework.xingyun.sc.dto.stock.take.plan.QueryTakeStockPlanProductDto;
import com.lframework.xingyun.sc.dto.stock.take.plan.TakeStockPlanFullDto;
import com.lframework.xingyun.sc.entity.TakeStockConfig;
import com.lframework.xingyun.sc.entity.TakeStockPlan;
import com.lframework.xingyun.sc.impl.stock.take.TakeStockPlanServiceImpl;
import com.lframework.xingyun.sc.service.stock.take.ITakeStockConfigService;
import com.lframework.xingyun.sc.service.stock.take.ITakeStockPlanService;
import com.lframework.xingyun.sc.vo.stock.take.plan.CancelTakeStockPlanVo;
import com.lframework.xingyun.sc.vo.stock.take.plan.CreateTakeStockPlanVo;
import com.lframework.xingyun.sc.vo.stock.take.plan.HandleTakeStockPlanVo;
import com.lframework.xingyun.sc.vo.stock.take.plan.QueryTakeStockPlanVo;
import com.lframework.xingyun.sc.vo.stock.take.plan.UpdateTakeStockPlanVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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
 * ???????????? Controller
 *
 * @author zmj
 */
@Api(tags = "????????????")
@Validated
@RestController
@RequestMapping("/stock/take/plan")
public class TakeStockPlanController extends DefaultBaseController {

    @Autowired
    private ITakeStockPlanService takeStockPlanService;

    @Autowired
    private ITakeStockConfigService takeStockConfigService;

    /**
     * ????????????
     */
    @ApiOperation("????????????")
    @PreAuthorize("@permission.valid('stock:take:plan:query')")
    @GetMapping("/query")
    public InvokeResult<PageResult<QueryTakeStockPlanBo>> query(@Valid QueryTakeStockPlanVo vo) {

        PageResult<TakeStockPlan> pageResult = takeStockPlanService.query(getPageIndex(vo), getPageSize(vo), vo);

        List<TakeStockPlan> datas = pageResult.getDatas();
        List<QueryTakeStockPlanBo> results = null;

        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(QueryTakeStockPlanBo::new).collect(Collectors.toList());
        }

        return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
    }

    /**
     * ????????????
     */
    @ApiOperation("????????????")
    @PreAuthorize("@permission.valid('stock:take:plan:export')")
    @PostMapping("/export")
    public void export(@Valid QueryTakeStockPlanVo vo) {

        ExcelMultipartWriterSheetBuilder builder = ExcelUtil.multipartExportXls("??????????????????",
                TakeStockPlanExportModel.class);

        try {
            int pageIndex = 1;
            while (true) {
                PageResult<TakeStockPlan> pageResult = takeStockPlanService.query(pageIndex, getExportSize(), vo);
                List<TakeStockPlan> datas = pageResult.getDatas();
                List<TakeStockPlanExportModel> models = datas.stream().map(TakeStockPlanExportModel::new)
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
    @PreAuthorize("@permission.valid('stock:take:plan:query', 'stock:take:sheet:add', 'stock:take:sheet:modify')")
    @GetMapping
    public InvokeResult<GetTakeStockPlanBo> get(@NotBlank(message = "id???????????????") String id) {

        TakeStockPlan data = takeStockPlanService.getById(id);
        if (data == null) {
            throw new DefaultClientException("????????????????????????");
        }

        GetTakeStockPlanBo result = new GetTakeStockPlanBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * ??????ID??????
     */
    @ApiOperation("??????ID????????????")
    @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
    @PreAuthorize("@permission.valid('stock:take:plan:query', 'stock:take:sheet:add', 'stock:take:sheet:modify')")
    @GetMapping("/detail")
    public InvokeResult<TakeStockPlanFullBo> getDetail(@NotBlank(message = "id???????????????") String id) {

        TakeStockPlanFullDto data = takeStockPlanService.getDetail(id);
        if (data == null) {
            throw new DefaultClientException("????????????????????????");
        }

        TakeStockPlanFullBo result = new TakeStockPlanFullBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * ??????????????????ID??????????????????
     */
    @ApiOperation("??????????????????ID??????????????????")
    @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
    @PreAuthorize("@permission.valid('stock:take:sheet:add', 'stock:take:sheet:modify')")
    @GetMapping("/products")
    public InvokeResult<List<QueryTakeStockPlanProductBo>> getProducts(@NotBlank(message = "id???????????????") String id) {

        TakeStockConfig config = takeStockConfigService.get();
        if (!config.getShowProduct()) {
            // ???????????????????????????????????????emptyList
            return InvokeResultBuilder.success(Collections.EMPTY_LIST);
        }

        List<QueryTakeStockPlanProductDto> datas = takeStockPlanService.getProducts(id);
        if (CollectionUtil.isEmpty(datas)) {
            return InvokeResultBuilder.success(Collections.EMPTY_LIST);
        }

        List<QueryTakeStockPlanProductBo> results = datas.stream().map(QueryTakeStockPlanProductBo::new)
                .collect(Collectors.toList());

        return InvokeResultBuilder.success(results);
    }

    /**
     * ??????
     */
    @ApiOperation("??????")
    @PreAuthorize("@permission.valid('stock:take:plan:add')")
    @PostMapping
    public InvokeResult<Void> create(@Valid @RequestBody CreateTakeStockPlanVo vo) {

        vo.validate();

        String id = takeStockPlanService.create(vo);

        TakeStockConfig config = takeStockConfigService.get();

        // ????????????
        QrtzHandler.addJob(TakeStockPlanServiceImpl.AutoCancelJob.class,
                CronUtil.getDateTimeCron(LocalDateTime.now().plusHours(config.getCancelHours())),
                Collections.singletonMap("id", id));

        return InvokeResultBuilder.success();
    }

    /**
     * ??????
     */
    @ApiOperation("??????")
    @PreAuthorize("@permission.valid('stock:take:plan:modify')")
    @PutMapping
    public InvokeResult<Void> update(@Valid UpdateTakeStockPlanVo vo) {

        takeStockPlanService.update(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * ????????????
     */
    @ApiOperation("????????????")
    @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
    @PreAuthorize("@permission.valid('stock:take:plan:create-diff')")
    @PatchMapping("/diff")
    public InvokeResult<Void> createDiff(@NotBlank(message = "ID???????????????") String id) {

        takeStockPlanService.createDiff(id);

        return InvokeResultBuilder.success();
    }

    /**
     * ????????????
     */
    @ApiOperation("????????????")
    @PreAuthorize("@permission.valid('stock:take:plan:handle-diff')")
    @PatchMapping("/handle")
    public InvokeResult<Void> handleDiff(@Valid @RequestBody HandleTakeStockPlanVo vo) {

        takeStockPlanService.handleDiff(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * ??????
     */
    @ApiOperation("??????")
    @PreAuthorize("@permission.valid('stock:take:plan:cancel')")
    @PatchMapping("/cancel")
    public InvokeResult<Void> cancel(@Valid CancelTakeStockPlanVo vo) {

        takeStockPlanService.cancel(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * ??????
     */
    @ApiOperation("??????")
    @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
    @PreAuthorize("@permission.valid('stock:take:plan:delete')")
    @DeleteMapping
    public InvokeResult<Void> deleteById(@NotBlank(message = "ID???????????????") String id) {

        takeStockPlanService.deleteById(id);

        return InvokeResultBuilder.success();
    }
}
