package com.lframework.xingyun.api.controller.stock;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.api.bo.stock.product.log.QueryProductStockLogBo;
import com.lframework.xingyun.api.excel.stock.ProductStockLogExportModel;
import com.lframework.xingyun.sc.entity.ProductStockLog;
import com.lframework.xingyun.sc.service.stock.IProductStockLogService;
import com.lframework.xingyun.sc.vo.stock.log.QueryProductStockLogVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品库存变动记录
 *
 * @author zmj
 */
@Api(tags = "商品库存变动记录")
@Validated
@RestController
@RequestMapping("/stock/product/log")
public class ProductStockLogController extends DefaultBaseController {

    @Autowired
    private IProductStockLogService productStockLogService;

    /**
     * 查询商品库存变动记录
     */
    @ApiOperation("查询商品库存变动记录")
    @PreAuthorize("@permission.valid('stock:product-log:query')")
    @GetMapping("/query")
    public InvokeResult<PageResult<QueryProductStockLogBo>> query(@Valid QueryProductStockLogVo vo) {

        PageResult<ProductStockLog> pageResult = productStockLogService.query(getPageIndex(vo), getPageSize(vo), vo);
        List<QueryProductStockLogBo> results = null;
        List<ProductStockLog> datas = pageResult.getDatas();

        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(QueryProductStockLogBo::new).collect(Collectors.toList());
        }

        return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
    }

    /**
     * 导出商品库存变动记录
     */
    @ApiOperation("导出商品库存变动记录")
    @PreAuthorize("@permission.valid('stock:product-log:export')")
    @GetMapping("/export")
    public void export(@Valid QueryProductStockLogVo vo) {

        ExcelMultipartWriterSheetBuilder builder = ExcelUtil.multipartExportXls("商品库存变动记录信息",
                ProductStockLogExportModel.class);

        try {
            int pageIndex = 1;
            while (true) {
                PageResult<ProductStockLog> pageResult = productStockLogService.query(pageIndex, getExportSize(), vo);
                List<ProductStockLog> datas = pageResult.getDatas();
                List<ProductStockLogExportModel> models = datas.stream().map(ProductStockLogExportModel::new)
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
}
