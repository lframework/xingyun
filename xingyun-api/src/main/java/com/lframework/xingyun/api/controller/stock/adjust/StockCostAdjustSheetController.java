package com.lframework.xingyun.api.controller.stock.adjust;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.api.bo.basedata.product.info.stock.adjust.StockCostAdjustProductBo;
import com.lframework.xingyun.api.bo.stock.adjust.QueryStockCostAdjustSheetBo;
import com.lframework.xingyun.api.bo.stock.adjust.StockCostAdjustSheetFullBo;
import com.lframework.xingyun.api.excel.stock.adjust.StockCostAdjustSheetExportModel;
import com.lframework.xingyun.basedata.dto.product.info.StockCostAdjustProductDto;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.vo.product.info.QueryStockCostAdjustProductVo;
import com.lframework.xingyun.sc.dto.stock.adjust.StockCostAdjustSheetFullDto;
import com.lframework.xingyun.sc.entity.StockCostAdjustSheet;
import com.lframework.xingyun.sc.service.stock.adjust.IStockCostAdjustSheetService;
import com.lframework.xingyun.sc.vo.stock.adjust.ApprovePassStockCostAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.ApproveRefuseStockCostAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.BatchApprovePassStockCostAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.BatchApproveRefuseStockCostAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.CreateStockCostAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.QueryStockCostAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.UpdateStockCostAdjustSheetVo;
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
 * 库存成本调整单 Controller
 *
 * @author zmj
 */
@Api(tags = "库存成本调整单")
@Validated
@RestController
@RequestMapping("/stock/adjust/cost")
public class StockCostAdjustSheetController extends DefaultBaseController {

    @Autowired
    private IStockCostAdjustSheetService stockCostAdjustSheetService;

    @Autowired
    private IProductService productService;

    /**
     * 查询列表
     */
    @ApiOperation("查询列表")
    @PreAuthorize("@permission.valid('stock:adjust:cost:query')")
    @GetMapping("/query")
    public InvokeResult<PageResult<QueryStockCostAdjustSheetBo>> query(@Valid QueryStockCostAdjustSheetVo vo) {

        PageResult<StockCostAdjustSheet> pageResult = stockCostAdjustSheetService.query(getPageIndex(vo),
                getPageSize(vo), vo);

        List<StockCostAdjustSheet> datas = pageResult.getDatas();
        List<QueryStockCostAdjustSheetBo> results = null;

        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(QueryStockCostAdjustSheetBo::new).collect(Collectors.toList());
        }

        return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
    }

    /**
     * 根据ID查询
     */
    @ApiOperation("根据ID查询")
    @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
    @PreAuthorize("@permission.valid('stock:adjust:cost:query')")
    @GetMapping("/detail")
    public InvokeResult<StockCostAdjustSheetFullBo> getDetail(@NotBlank(message = "id不能为空！") String id) {

        StockCostAdjustSheetFullDto data = stockCostAdjustSheetService.getDetail(id);
        if (data == null) {
            throw new DefaultClientException("库存成本调整单不存在！");
        }

        StockCostAdjustSheetFullBo result = new StockCostAdjustSheetFullBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 导出
     */
    @ApiOperation("导出")
    @PreAuthorize("@permission.valid('stock:adjust:cost:export')")
    @PostMapping("/export")
    public void export(@Valid QueryStockCostAdjustSheetVo vo) {

        ExcelMultipartWriterSheetBuilder builder = ExcelUtil.multipartExportXls("库存成本调整单信息",
                StockCostAdjustSheetExportModel.class);

        try {
            int pageIndex = 1;
            while (true) {
                PageResult<StockCostAdjustSheet> pageResult = stockCostAdjustSheetService.query(pageIndex,
                        getExportSize(), vo);
                List<StockCostAdjustSheet> datas = pageResult.getDatas();
                List<StockCostAdjustSheetExportModel> models = datas.stream().map(StockCostAdjustSheetExportModel::new)
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
     * 根据关键字查询商品列表
     */
    @ApiOperation("根据关键字查询商品列表")
    @ApiImplicitParams({@ApiImplicitParam(value = "仓库ID", name = "scId", paramType = "query", required = true),
            @ApiImplicitParam(value = "关键字", name = "condition", paramType = "query", required = true)})
    @PreAuthorize("@permission.valid('stock:adjust:cost:add', 'stock:adjust:cost:modify')")
    @GetMapping("/product/search")
    public InvokeResult<List<StockCostAdjustProductBo>> searchProducts(@NotBlank(message = "仓库ID不能为空！") String scId,
            String condition) {

        if (StringUtil.isBlank(condition)) {
            return InvokeResultBuilder.success(Collections.EMPTY_LIST);
        }
        PageResult<StockCostAdjustProductDto> pageResult = productService.queryStockCostAdjustByCondition(
                getPageIndex(), getPageSize(), scId, condition);
        List<StockCostAdjustProductBo> results = Collections.EMPTY_LIST;
        List<StockCostAdjustProductDto> datas = pageResult.getDatas();
        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(StockCostAdjustProductBo::new).collect(Collectors.toList());
        }

        return InvokeResultBuilder.success(results);
    }

    /**
     * 查询商品列表
     */
    @ApiOperation("查询商品列表")
    @PreAuthorize("@permission.valid('stock:adjust:cost:add', 'stock:adjust:cost:modify')")
    @GetMapping("/product/list")
    public InvokeResult<PageResult<StockCostAdjustProductBo>> queryProductList(
            @Valid QueryStockCostAdjustProductVo vo) {

        PageResult<StockCostAdjustProductDto> pageResult = productService.queryStockCostAdjustList(getPageIndex(),
                getPageSize(), vo);
        List<StockCostAdjustProductBo> results = null;
        List<StockCostAdjustProductDto> datas = pageResult.getDatas();

        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(StockCostAdjustProductBo::new).collect(Collectors.toList());
        }

        return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
    }

    /**
     * 新增
     */
    @ApiOperation("新增")
    @PreAuthorize("@permission.valid('stock:adjust:cost:add')")
    @PostMapping
    public InvokeResult<Void> create(@Valid @RequestBody CreateStockCostAdjustSheetVo vo) {

        vo.validate();

        stockCostAdjustSheetService.create(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PreAuthorize("@permission.valid('stock:adjust:cost:modify')")
    @PutMapping
    public InvokeResult<Void> update(@Valid @RequestBody UpdateStockCostAdjustSheetVo vo) {

        vo.validate();

        stockCostAdjustSheetService.update(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 根据ID删除
     */
    @ApiOperation("根据ID删除")
    @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
    @PreAuthorize("@permission.valid('stock:adjust:cost:delete')")
    @DeleteMapping
    public InvokeResult<Void> deleteById(@NotBlank(message = "id不能为空！") String id) {

        stockCostAdjustSheetService.deleteById(id);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量删除
     */
    @ApiOperation("批量删除")
    @PreAuthorize("@permission.valid('stock:adjust:cost:delete')")
    @DeleteMapping("/batch")
    public InvokeResult<Void> deleteByIds(
            @ApiParam(value = "ID", required = true) @RequestBody @NotEmpty(message = "请选择需要删除的库存成本调整单！") List<String> ids) {

        stockCostAdjustSheetService.deleteByIds(ids);

        return InvokeResultBuilder.success();
    }

    /**
     * 审核通过
     */
    @ApiOperation("审核通过")
    @PreAuthorize("@permission.valid('stock:adjust:cost:approve')")
    @PatchMapping("/approve/pass")
    public InvokeResult<Void> approvePass(@RequestBody @Valid ApprovePassStockCostAdjustSheetVo vo) {

        stockCostAdjustSheetService.approvePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量审核通过
     */
    @ApiOperation("批量审核通过")
    @PreAuthorize("@permission.valid('stock:adjust:cost:approve')")
    @PatchMapping("/approve/pass/batch")
    public InvokeResult<Void> batchApprovePass(@RequestBody @Valid BatchApprovePassStockCostAdjustSheetVo vo) {

        stockCostAdjustSheetService.batchApprovePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 直接审核通过
     */
    @ApiOperation("直接审核通过")
    @PreAuthorize("@permission.valid('stock:adjust:cost:approve')")
    @PostMapping("/approve/pass/direct")
    public InvokeResult<Void> directApprovePass(@RequestBody @Valid CreateStockCostAdjustSheetVo vo) {

        stockCostAdjustSheetService.directApprovePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 审核拒绝
     */
    @ApiOperation("审核拒绝")
    @PreAuthorize("@permission.valid('stock:adjust:cost:approve')")
    @PatchMapping("/approve/refuse")
    public InvokeResult<Void> approveRefuse(@RequestBody @Valid ApproveRefuseStockCostAdjustSheetVo vo) {

        stockCostAdjustSheetService.approveRefuse(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量审核拒绝
     */
    @ApiOperation("批量审核拒绝")
    @PreAuthorize("@permission.valid('stock:adjust:cost:approve')")
    @PatchMapping("/approve/refuse/batch")
    public InvokeResult<Void> batchApproveRefuse(@RequestBody @Valid BatchApproveRefuseStockCostAdjustSheetVo vo) {

        stockCostAdjustSheetService.batchApproveRefuse(vo);

        return InvokeResultBuilder.success();
    }
}
