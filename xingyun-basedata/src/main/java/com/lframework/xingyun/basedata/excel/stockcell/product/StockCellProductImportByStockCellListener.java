package com.lframework.xingyun.basedata.excel.stockcell.product;

import com.alibaba.excel.context.AnalysisContext;
import com.lframework.starter.common.exceptions.ClientException;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.components.excel.ExcelImportListener;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.ProductSku;
import com.lframework.xingyun.basedata.entity.StockCell;
import com.lframework.xingyun.basedata.service.product.ProductSkuService;
import com.lframework.xingyun.basedata.service.stockcell.StockCellProductService;
import com.lframework.xingyun.basedata.service.stockcell.StockCellService;
import com.lframework.xingyun.basedata.vo.stockcell.product.CreateStockCellProductVo;
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
    if (StringUtil.isBlank(data.getSkuCode())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“SKU编号”不能为空");
    }

    ProductSkuService productSkuService = ApplicationUtil.getBean(ProductSkuService.class);
    ProductSku sku = productSkuService.findAvailableByCode(data.getSkuCode());
    if (sku == null) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“SKU编号”不存在");
    }
    data.setSkuId(sku.getId());
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
      vo.setSkuIds(CollectionUtil.toList(data.getSkuId()));

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
