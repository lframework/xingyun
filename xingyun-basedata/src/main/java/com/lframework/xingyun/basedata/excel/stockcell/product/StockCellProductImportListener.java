package com.lframework.xingyun.basedata.excel.stockcell.product;

import com.alibaba.excel.context.AnalysisContext;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.constants.PatternPool;
import com.lframework.starter.common.exceptions.ClientException;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.RegUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.components.excel.ExcelImportListener;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.utils.EnumUtil;
import com.lframework.xingyun.basedata.entity.ProductSku;
import com.lframework.xingyun.basedata.entity.StockCell;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.enums.StockCellType;
import com.lframework.xingyun.basedata.service.product.ProductSkuService;
import com.lframework.xingyun.basedata.service.stockcell.StockCellProductService;
import com.lframework.xingyun.basedata.service.stockcell.StockCellService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.basedata.vo.stockcell.CreateStockCellVo;
import com.lframework.xingyun.basedata.vo.stockcell.product.CreateStockCellProductVo;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StockCellProductImportListener extends
    ExcelImportListener<StockCellProductImportModel> {

  private final Boolean autoCreateStockCell;

  private final Integer stockCellType;

  public StockCellProductImportListener() {
    this(false, null);
  }

  public StockCellProductImportListener(Boolean autoCreateStockCell, Integer stockCellType) {
    this.autoCreateStockCell = autoCreateStockCell;
    this.stockCellType = stockCellType;
  }

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
    this.resolveStockCell(data, stockCellService, stockCell, context.readRowHolder().getRowIndex());

    if (StringUtil.isBlank(data.getProductCode())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“SKU编号”不能为空");
    }

    ProductSkuService productSkuService = ApplicationUtil.getBean(ProductSkuService.class);
    ProductSku sku = productSkuService.findAvailableByCode(data.getProductCode());
    if (sku == null) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“SKU编号”不存在");
    }
    data.setSkuId(sku.getId());
  }

  private void resolveStockCell(StockCellProductImportModel data, StockCellService stockCellService,
      StockCell stockCell, int rowIndex) {

    if (stockCell != null) {
      data.setStockCellId(stockCell.getId());
      data.setCellType(stockCell.getCellType().getCode());
      return;
    }

    if (!Boolean.TRUE.equals(this.autoCreateStockCell)) {
      throw new DefaultClientException("第" + rowIndex + "行“仓位编号”不存在");
    }

    if (this.stockCellType == null) {
      throw new DefaultClientException("第" + rowIndex + "行“仓位类别”不能为空");
    }

    if (!RegUtil.isMatch(PatternPool.PATTERN_CODE, data.getStockCellCode())) {
      throw new DefaultClientException(
          "第" + rowIndex + "行“仓位编号”必须由字母、数字、“-_.”组成，长度不能超过20位");
    }

    if (EnumUtil.getByCode(StockCellType.class, this.stockCellType) == null) {
      throw new DefaultClientException("第" + rowIndex + "行“仓位类别”格式不正确");
    }

    CreateStockCellVo vo = new CreateStockCellVo();
    vo.setScId(data.getScId());
    vo.setCode(data.getStockCellCode());
    vo.setName(data.getStockCellCode());
    vo.setCellType(this.stockCellType);

    data.setStockCellId(stockCellService.create(vo));
    data.setCellType(this.stockCellType);
  }

  @Override
  protected void afterAllAnalysed(AnalysisContext context) {

    StockCellProductService service = ApplicationUtil.getBean(StockCellProductService.class);
    List<StockCellProductImportModel> datas = this.getDatas();

    for (int i = 0; i < datas.size(); i++) {
      StockCellProductImportModel data = datas.get(i);

      CreateStockCellProductVo vo = new CreateStockCellProductVo();
      vo.setStockCellId(data.getStockCellId());
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
