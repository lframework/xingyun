package com.lframework.xingyun.basedata.excel.stockcell.product;

import com.alibaba.excel.context.AnalysisContext;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.lframework.starter.common.exceptions.ClientException;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.components.excel.ExcelImportListener;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.ProductCode;
import com.lframework.xingyun.basedata.entity.StockCell;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.basedata.service.stockcell.StockCellProductService;
import com.lframework.xingyun.basedata.service.stockcell.StockCellService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.basedata.vo.stockcell.product.CreateStockCellProductVo;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StockCellProductImportListener extends
    ExcelImportListener<StockCellProductImportModel> {

  @Override
  protected void doInvoke(StockCellProductImportModel data, AnalysisContext context) {
    if (StringUtil.isBlank(data.getScCode())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“仓库编号”不能为空");
    }

    StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
    Wrapper<StoreCenter> scWrapper = Wrappers.lambdaQuery(StoreCenter.class)
        .eq(StoreCenter::getCode, data.getScCode()).eq(StoreCenter::getAvailable, true);
    StoreCenter sc = storeCenterService.getOne(scWrapper);
    if (sc == null) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“仓库编号”不存在");
    }
    data.setScId(sc.getId());

    if (StringUtil.isBlank(data.getStockCellCode())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“仓位编号”不能为空");
    }

    StockCellService stockCellService = ApplicationUtil.getBean(StockCellService.class);
    Wrapper<StockCell> cellWrapper = Wrappers.lambdaQuery(StockCell.class)
        .eq(StockCell::getCode, data.getStockCellCode())
        .eq(StockCell::getScId, data.getScId())
        .eq(StockCell::getAvailable, true);
    StockCell stockCell = stockCellService.getOne(cellWrapper);
    if (stockCell == null) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“仓位编号”不存在");
    }
    data.setStockCellId(stockCell.getId());
    data.setCellType(stockCell.getCellType().getCode());

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

    StockCellProductService service = ApplicationUtil.getBean(StockCellProductService.class);
    List<StockCellProductImportModel> datas = this.getDatas();

    for (int i = 0; i < datas.size(); i++) {
      StockCellProductImportModel data = datas.get(i);

      CreateStockCellProductVo vo = new CreateStockCellProductVo();
      vo.setStockCellId(data.getStockCellId());
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
