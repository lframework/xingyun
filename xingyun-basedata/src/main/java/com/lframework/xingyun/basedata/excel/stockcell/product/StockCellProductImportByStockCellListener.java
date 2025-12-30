package com.lframework.xingyun.basedata.excel.stockcell.product;

import com.alibaba.excel.context.AnalysisContext;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.yulichang.query.MPJLambdaQueryWrapper;
import com.github.yulichang.query.MPJQueryWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.lframework.starter.common.exceptions.ClientException;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.components.excel.ExcelImportListener;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.ProductCode;
import com.lframework.xingyun.basedata.entity.StockCell;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.basedata.service.stockcell.StockCellProductService;
import com.lframework.xingyun.basedata.service.stockcell.StockCellService;
import com.lframework.xingyun.basedata.vo.stockcell.product.CreateStockCellProductVo;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StockCellProductImportByStockCellListener extends
    ExcelImportListener<StockCellProductImportByStockCellModel> {

  private final String stockCellId;

  public StockCellProductImportByStockCellListener(String stockCellId) {
    this.stockCellId = stockCellId;
  }

  @Override
  protected void doInvoke(StockCellProductImportByStockCellModel data, AnalysisContext context) {
    if (StringUtil.isBlank(data.getProductCode())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“商品编号”不能为空");
    }

    ProductService productService = ApplicationUtil.getBean(ProductService.class);
    Wrapper<Product> productWrapper = new MPJLambdaWrapper<Product>().selectAll(Product.class)
        .leftJoin(ProductCode.class, ProductCode::getProductId, Product::getId)
        .eq(ProductCode::getCode, data.getProductCode())
        .eq(Product::getAvailable, true);
    Product product = productService.getOne(productWrapper);
    if (product == null) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“商品编号”不存在");
    }
    data.setProductId(product.getId());
  }

  @Override
  protected void afterAllAnalysed(AnalysisContext context) {

    StockCellService stockCellService = ApplicationUtil.getBean(StockCellService.class);
    StockCell stockCell = stockCellService.getById(stockCellId);
    if (stockCell == null || !stockCell.getAvailable()) {
      throw new DefaultClientException("仓位不存在");
    }

    StockCellProductService service = ApplicationUtil.getBean(StockCellProductService.class);
    List<StockCellProductImportByStockCellModel> datas = this.getDatas();
    for (int i = 0; i < datas.size(); i++) {
      StockCellProductImportByStockCellModel data = datas.get(i);

      CreateStockCellProductVo vo = new CreateStockCellProductVo();
      vo.setStockCellId(stockCellId);
      vo.setProductIds(Collections.singletonList(data.getProductId()));

      try {
        service.create(vo);
      } catch (Exception e) {
        if (!(e instanceof ClientException)) {
          log.error(e.getMessage(), e);
        }
        throw new DefaultClientException("第" + (i + 1) + "行数据导入失败，原因：" + e.getMessage());
      }

      this.setSuccessProcessByIndex(i);
    }
  }

  @Override
  protected void doComplete() {
  }
}
