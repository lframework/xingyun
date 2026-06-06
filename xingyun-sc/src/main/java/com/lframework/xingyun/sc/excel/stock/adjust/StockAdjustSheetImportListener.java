package com.lframework.xingyun.sc.excel.stock.adjust;

import com.alibaba.excel.context.AnalysisContext;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.components.excel.ExcelImportListener;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.ProductSku;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.product.ProductSkuService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.sc.entity.StockAdjustReason;
import com.lframework.xingyun.sc.enums.StockAdjustSheetBizType;
import com.lframework.xingyun.sc.service.stock.adjust.StockAdjustReasonService;
import com.lframework.xingyun.sc.service.stock.adjust.StockAdjustSheetService;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.CreateStockAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.StockAdjustProductVo;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StockAdjustSheetImportListener extends
    ExcelImportListener<StockAdjustSheetImportModel> {

  @Override
  protected void doInvoke(StockAdjustSheetImportModel data, AnalysisContext context) {

    int rowIndex = context.readRowHolder().getRowIndex();

    if (StringUtil.isBlank(data.getScCode())) {
      throw new DefaultClientException("第" + rowIndex + "行“仓库编号”不能为空");
    }
    StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
    Wrapper<StoreCenter> queryScWrapper = Wrappers.lambdaQuery(StoreCenter.class)
        .eq(StoreCenter::getCode, data.getScCode())
        .eq(StoreCenter::getAvailable, Boolean.TRUE);
    StoreCenter sc = storeCenterService.getOne(queryScWrapper);
    if (sc == null) {
      throw new DefaultClientException("第" + rowIndex + "行“仓库编号”不存在");
    }
    data.setScId(sc.getId());

    if ("入库".equals(data.getBizTypeName())) {
      data.setBizType(StockAdjustSheetBizType.IN.getCode());
    } else if ("出库".equals(data.getBizTypeName())) {
      data.setBizType(StockAdjustSheetBizType.OUT.getCode());
    } else {
      throw new DefaultClientException("第" + rowIndex + "行“业务类型”只能填“入库”或“出库”");
    }

    if (StringUtil.isBlank(data.getReasonCode())) {
      throw new DefaultClientException("第" + rowIndex + "行“调整原因编号”不能为空");
    }
    StockAdjustReasonService stockAdjustReasonService = ApplicationUtil.getBean(
        StockAdjustReasonService.class);
    Wrapper<StockAdjustReason> queryReasonWrapper = Wrappers.lambdaQuery(StockAdjustReason.class)
        .eq(StockAdjustReason::getCode, data.getReasonCode())
        .eq(StockAdjustReason::getAvailable, Boolean.TRUE);
    StockAdjustReason reason = stockAdjustReasonService.getOne(queryReasonWrapper);
    if (reason == null) {
      throw new DefaultClientException("第" + rowIndex + "行“调整原因编号”不存在");
    }
    data.setReasonId(reason.getId());

    if (StringUtil.isBlank(data.getSkuCode())) {
      throw new DefaultClientException("第" + rowIndex + "行“SKU编号”不能为空");
    }
    ProductSkuService productSkuService = ApplicationUtil.getBean(ProductSkuService.class);
    ProductSku sku = productSkuService.findAvailableByCode(data.getSkuCode());
    if (sku == null) {
      throw new DefaultClientException("第" + rowIndex + "行“SKU编号”不存在");
    }
    data.setProductId(sku.getProductId());
    data.setSkuId(sku.getId());

    if (data.getStockNum() == null) {
      throw new DefaultClientException("第" + rowIndex + "行“调整库存数量”不能为空");
    }
    if (NumberUtil.le(data.getStockNum(), BigDecimal.ZERO)) {
      throw new DefaultClientException("第" + rowIndex + "行“调整库存数量”必须大于0");
    }
    if (!NumberUtil.isNumberPrecision(data.getStockNum(), 8)) {
      throw new DefaultClientException("第" + rowIndex + "行“调整库存数量”最多允许8位小数");
    }
  }

  @Override
  protected void afterAllAnalysed(AnalysisContext context) {

    StockAdjustSheetService stockAdjustSheetService = ApplicationUtil.getBean(
        StockAdjustSheetService.class);

    int index = 0;
    for (CreateStockAdjustSheetVo vo : buildCreateVos(this.getDatas())) {
      stockAdjustSheetService.create(vo);
      index += vo.getProducts().size();
      this.setSuccessProcessByIndex(index);
    }
  }

  private List<CreateStockAdjustSheetVo> buildCreateVos(List<StockAdjustSheetImportModel> datas) {

    Map<String, List<StockAdjustSheetImportModel>> groupByMap = datas.stream().collect(
        Collectors.groupingBy(this::buildGroupKey, LinkedHashMap::new,
            Collectors.toList()));

    List<CreateStockAdjustSheetVo> results = new ArrayList<>();
    for (List<StockAdjustSheetImportModel> value : groupByMap.values()) {
      StockAdjustSheetImportModel first = value.get(0);
      CreateStockAdjustSheetVo vo = new CreateStockAdjustSheetVo();
      vo.setScId(first.getScId());
      vo.setBizType(first.getBizType());
      vo.setReasonId(first.getReasonId());
      vo.setDescription(first.getDescription());

      List<StockAdjustProductVo> products = new ArrayList<>();
      for (StockAdjustSheetImportModel data : value) {
        StockAdjustProductVo product = new StockAdjustProductVo();
        product.setProductId(data.getProductId());
        product.setSkuId(data.getSkuId());
        product.setStockNum(data.getStockNum());
        product.setDescription(data.getDetailDescription());

        products.add(product);
      }
      vo.setProducts(products);
      results.add(vo);
    }

    return results;
  }

  private String buildGroupKey(StockAdjustSheetImportModel data) {

    return data.getScCode() + "_" + data.getBizType() + "_" + data.getReasonCode() + "_"
        + (StringUtil.isBlank(data.getDescription()) ? StringPool.EMPTY_STR
            : data.getDescription());
  }

  @Override
  protected void doComplete() {

  }
}
