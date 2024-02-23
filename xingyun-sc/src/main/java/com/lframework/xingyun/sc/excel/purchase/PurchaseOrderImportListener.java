package com.lframework.xingyun.sc.excel.purchase;

import com.alibaba.excel.context.AnalysisContext;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.starter.web.components.excel.ExcelImportListener;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.entity.Supplier;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.basedata.service.supplier.SupplierService;
import com.lframework.xingyun.sc.service.purchase.PurchaseOrderService;
import com.lframework.xingyun.sc.vo.purchase.CreatePurchaseOrderVo;
import com.lframework.xingyun.sc.vo.purchase.PurchaseProductVo;
import com.lframework.xingyun.template.core.dto.UserDto;
import com.lframework.xingyun.template.core.service.UserService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PurchaseOrderImportListener extends ExcelImportListener<PurchaseOrderImportModel> {

  @Override
  protected void doInvoke(PurchaseOrderImportModel data, AnalysisContext context) {
    if (StringUtil.isBlank(data.getScCode())) {
      throw new DefaultClientException("第" + context.readRowHolder().getRowIndex() + "行“仓库编号”不能为空");
    } else {
      StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
      Wrapper<StoreCenter> queryWrapper = Wrappers.lambdaQuery(StoreCenter.class)
          .eq(StoreCenter::getCode, data.getScCode());
      StoreCenter sc = storeCenterService.getOne(queryWrapper);
      if (sc == null) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“仓库编号”不存在");
      }

      data.setScId(sc.getId());
    }
    if (StringUtil.isBlank(data.getSupplierCode())) {
      throw new DefaultClientException("第" + context.readRowHolder().getRowIndex() + "行“仓库编号”不能为空");
    } else {
      SupplierService supplierService = ApplicationUtil.getBean(SupplierService.class);
      Wrapper<Supplier> queryWrapper = Wrappers.lambdaQuery(Supplier.class)
          .eq(Supplier::getCode, data.getSupplierCode());
      Supplier supplier = supplierService.getOne(queryWrapper);
      if (supplier == null) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“供应商编号”不存在");
      }

      data.setSupplierId(supplier.getId());
    }
    if (!StringUtil.isBlank(data.getPurchaserCode())) {
      UserService userService = ApplicationUtil.getBean(UserService.class);
      UserDto purchaser = userService.findByCode(data.getPurchaserCode());
      if (purchaser == null) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“采购员编号”不存在");
      }
      data.setPurchaserId(purchaser.getId());
    }
    if (data.getExpectArriveDate() == null) {
      data.setExpectArriveDate(new Date());
    }
    if (StringUtil.isBlank(data.getProductCode())) {
      throw new DefaultClientException("第" + context.readRowHolder().getRowIndex() + "行“商品编号”不能为空");
    } else {
      ProductService productService = ApplicationUtil.getBean(ProductService.class);
      Wrapper<Product> queryWrapper = Wrappers.lambdaQuery(Product.class)
          .eq(Product::getCode, data.getProductCode());
      Product product = productService.getOne(queryWrapper);
      if (product == null) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“商品编号”不存在");
      }
      data.setProductId(product.getId());
    }

    if (!"是".equals(data.getGift()) && !"否".equals(data.getGift())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“是否赠品”只能填“是”或“否”");
    }

    boolean isGift = "是".equals(data.getGift());
    if (!isGift) {
      // 非赠品
      if (data.getPurchasePrice() == null) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“采购价”不能为空");
      }
      if (NumberUtil.le(data.getPurchasePrice(), 0)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“采购价”必须大于0");
      }
      if (!NumberUtil.isNumberPrecision(data.getPurchasePrice(), 2)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“采购价”最多允许2位小数");
      }
    } else {
      // 赠品
      data.setPurchasePrice(BigDecimal.ZERO);
    }
    if (data.getPurchaseNum() == null) {
      throw new DefaultClientException("第" + context.readRowHolder().getRowIndex() + "行“采购数量”不能为空");
    }
    if (data.getPurchaseNum() <= 0) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“采购数量”必须大于0");
    }
  }

  @Override
  protected void afterAllAnalysed(AnalysisContext context) {

    // 根据仓库、供应商、采购员、预计到货日期 分组
    Map<Object, List<PurchaseOrderImportModel>> groupByMap = this.getDatas().stream().collect(
        Collectors.groupingBy(
            t -> t.getScCode() + "_" + t.getSupplierCode() + "_" + t.getPurchaserCode() + "_"
                + DateUtil.toLocalDate(t.getExpectArriveDate())));

    PurchaseOrderService purchaseOrderService = ApplicationUtil.getBean(
        PurchaseOrderService.class);

    int index = 0;
    for (List<PurchaseOrderImportModel> value : groupByMap.values()) {
      PurchaseOrderImportModel valueObj = value.get(0);
      CreatePurchaseOrderVo createPurchaseOrderVo = new CreatePurchaseOrderVo();
      createPurchaseOrderVo.setScId(valueObj.getScId());
      createPurchaseOrderVo.setSupplierId(valueObj.getSupplierId());
      createPurchaseOrderVo.setPurchaserId(valueObj.getPurchaserId());
      createPurchaseOrderVo.setExpectArriveDate(
          DateUtil.toLocalDate(valueObj.getExpectArriveDate()));
      createPurchaseOrderVo.setDescription(valueObj.getDescription());

      List<PurchaseProductVo> products = new ArrayList<>();
      for (PurchaseOrderImportModel data : value) {
        PurchaseProductVo purchaseProductVo = new PurchaseProductVo();
        purchaseProductVo.setProductId(data.getProductId());
        purchaseProductVo.setPurchasePrice(data.getPurchasePrice());
        purchaseProductVo.setPurchaseNum(data.getPurchaseNum());
        purchaseProductVo.setDescription(data.getDetailDescription());

        products.add(purchaseProductVo);

        index++;
        this.setSuccessProcessByIndex(index);
      }
      createPurchaseOrderVo.setProducts(products);

      purchaseOrderService.create(createPurchaseOrderVo);
    }
  }

  @Override
  protected void doComplete() {

  }
}
