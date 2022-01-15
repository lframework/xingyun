package com.lframework.xingyun.api.controller.stock;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.api.bo.stock.lot.QueryProductLotBo;
import com.lframework.xingyun.api.model.stock.ProductLotExportModel;
import com.lframework.xingyun.sc.dto.stock.ProductLotWithStockDto;
import com.lframework.xingyun.sc.service.stock.IProductLotService;
import com.lframework.xingyun.sc.vo.stock.lot.QueryProductLotVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品批次
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/stock/lot")
public class ProductLotController extends DefaultBaseController {

    @Autowired
    private IProductLotService productLotService;

    /**
     * 查询商品库存
     */
    @PreAuthorize("@permission.valid('stock:lot:query')")
    @GetMapping("/query")
    public InvokeResult query(@Valid QueryProductLotVo vo) {

        PageResult<ProductLotWithStockDto> pageResult = productLotService.query(getPageIndex(vo), getPageSize(vo), vo);
        List<QueryProductLotBo> results = Collections.EMPTY_LIST;
        List<ProductLotWithStockDto> datas = pageResult.getDatas();
        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(QueryProductLotBo::new).collect(Collectors.toList());

            PageResultUtil.rebuild(pageResult, results);
        }

        return InvokeResultBuilder.success(pageResult);
    }

    /**
     * 导出商品库存
     */
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
