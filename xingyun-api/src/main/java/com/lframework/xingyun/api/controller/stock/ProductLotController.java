package com.lframework.xingyun.api.controller.stock;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.api.bo.stock.lot.QueryProductLotBo;
import com.lframework.xingyun.api.excel.stock.ProductLotExportModel;
import com.lframework.xingyun.sc.dto.stock.ProductLotWithStockDto;
import com.lframework.xingyun.sc.service.stock.IProductLotService;
import com.lframework.xingyun.sc.vo.stock.lot.QueryProductLotVo;
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
 * 商品批次
 *
 * @author zmj
 */
@Api(tags = "商品批次")
@Validated
@RestController
@RequestMapping("/stock/lot")
public class ProductLotController extends DefaultBaseController {

    @Autowired
    private IProductLotService productLotService;

    /**
     * 查询商品库存
     */
    @ApiOperation("查询商品库存")
    @PreAuthorize("@permission.valid('stock:lot:query')")
    @GetMapping("/query")
    public InvokeResult<PageResult<QueryProductLotBo>> query(@Valid QueryProductLotVo vo) {

        PageResult<ProductLotWithStockDto> pageResult = productLotService.query(getPageIndex(vo), getPageSize(vo), vo);
        List<QueryProductLotBo> results = null;
        List<ProductLotWithStockDto> datas = pageResult.getDatas();
        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(QueryProductLotBo::new).collect(Collectors.toList());
        }

        return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
    }

    /**
     * 导出商品库存
     */
    @ApiOperation("导出商品库存")
    @PreAuthorize("@permission.valid('stock:lot:export')")
    @GetMapping("/export")
    public void export(@Valid QueryProductLotVo vo) {

        ExcelMultipartWriterSheetBuilder builder = ExcelUtil.multipartExportXls("商品批次信息", ProductLotExportModel.class);

        try {
            int pageIndex = 1;
            while (true) {
                PageResult<ProductLotWithStockDto> pageResult = productLotService.query(pageIndex, getExportSize(), vo);
                List<ProductLotWithStockDto> datas = pageResult.getDatas();
                List<ProductLotExportModel> models = datas.stream().map(ProductLotExportModel::new)
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
