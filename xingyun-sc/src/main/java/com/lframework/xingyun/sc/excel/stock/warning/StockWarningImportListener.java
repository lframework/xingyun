package com.lframework.xingyun.sc.excel.stock.warning;

import com.alibaba.excel.context.AnalysisContext;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.web.core.components.excel.ExcelImportListener;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.sc.entity.ProductStockWarning;
import com.lframework.xingyun.sc.service.stock.warning.ProductStockWarningService;
import com.lframework.xingyun.sc.vo.stock.warning.CreateProductStockWarningVo;
import com.lframework.xingyun.sc.vo.stock.warning.UpdateProductStockWarningVo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StockWarningImportListener extends ExcelImportListener<StockWarningImportModel> {

  @Override
  protected void doInvoke(StockWarningImportModel data, AnalysisContext context) {
    StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
    Wrapper<StoreCenter> queryScWrapper = Wrappers.lambdaQuery(StoreCenter.class)
        .eq(StoreCenter::getCode, data.getScCode());
    StoreCenter sc = storeCenterService.getOne(queryScWrapper);
    if (sc == null) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“仓库编号”不存在");
    }

    data.setScId(sc.getId());
    ProductService productService = ApplicationUtil.getBean(ProductService.class);
    Wrapper<Product> queryProductWrapper = Wrappers.lambdaQuery(Product.class)
        .eq(Product::getCode, data.getProductCode());
    Product product = productService.getOne(queryProductWrapper);
    if (product == null) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“商品编号”不存在");
    }
    data.setProductId(product.getId());

    if (data.getMinLimit() <= 0) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“预警下限”不能小于0");
    }

    if (data.getMaxLimit() <= 0) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“预警上限”不能小于0");
    }

    if (data.getMaxLimit() < data.getMinLimit()) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“预警上限”不能小于“预警下限”");
    }
  }

  @Override
  protected void afterAllAnalysed(AnalysisContext context) {

    ProductStockWarningService productStockWarningService = ApplicationUtil.getBean(
        ProductStockWarningService.class);

    int index = 0;
    for (StockWarningImportModel data : this.getDatas()) {
      Wrapper<ProductStockWarning> checkWrapper = Wrappers.lambdaQuery(ProductStockWarning.class)
          .eq(ProductStockWarning::getScId, data.getScId())
          .eq(ProductStockWarning::getProductId, data.getProductId());
      ProductStockWarning record = productStockWarningService.getOne(checkWrapper);
      if (record == null) {
        CreateProductStockWarningVo vo = new CreateProductStockWarningVo();
        vo.setScId(data.getScId());
        vo.setProductId(data.getProductId());
        vo.setMinLimit(data.getMinLimit());
        vo.setMaxLimit(data.getMaxLimit());

        productStockWarningService.create(vo);
      } else {
        UpdateProductStockWarningVo vo = new UpdateProductStockWarningVo();
        vo.setId(record.getId());
        vo.setAvailable(record.getAvailable());
        vo.setScId(data.getScId());
        vo.setProductId(data.getProductId());
        vo.setMinLimit(data.getMinLimit());
        vo.setMaxLimit(data.getMaxLimit());

        productStockWarningService.update(vo);
      }

      index++;
      this.setSuccessProcessByIndex(index);
    }
  }

  @Override
  protected void doComplete() {

  }
}
