package com.lframework.xingyun.api.controller.stock;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.api.bo.stock.product.QueryProductStockBo;
import com.lframework.xingyun.api.model.stock.ProductStockExportModel;
import com.lframework.xingyun.sc.dto.stock.ProductStockDto;
import com.lframework.xingyun.sc.service.stock.IProductStockService;
import com.lframework.xingyun.sc.vo.stock.QueryProductStockVo;
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
 * 商品库存
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/stock/product")
public class ProductStockController extends DefaultBaseController {

    @Autowired
    private IProductStockService productStockService;

    /**
     * 查询商品库存
     */
    @PreAuthorize("@permission.valid('stock:product:query')")
    @GetMapping("/query")
    public InvokeResult query(@Valid QueryProductStockVo vo) {

        PageResult<ProductStockDto> pageResult = productStockService.query(getPageIndex(vo), getPageSize(vo), vo);
        List<QueryProductStockBo> results = Collections.EMPTY_LIST;
        List<ProductStockDto> datas = pageResult.getDatas();
        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(QueryProductStockBo::new).collect(Collectors.toList());

            PageResultUtil.rebuild(pageResult, results);
        }

        return InvokeResultBuilder.success(pageResult);
    }

    /**
     * 导出商品库存
     */
    @PreAuthorize("@permission.valid('stock:product:export')")
    @GetMapping("/export")
    public void export(@Valid QueryProductStockVo vo) {

        ExcelMultipartWriterSheetBuilder builder = ExcelUtil
                .multipartExportXls("商品库存信息", ProductStockExportModel.class);

        try {
            int pageIndex = 1;
            while (true) {
                PageResult<ProductStockDto> pageResult = productStockService.query(pageIndex, getExportSize(), vo);
                List<ProductStockDto> datas = pageResult.getDatas();
                List<ProductStockExportModel> models = datas.stream().map(ProductStockExportModel::new)
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
