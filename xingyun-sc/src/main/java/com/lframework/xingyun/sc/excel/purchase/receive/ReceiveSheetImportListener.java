package com.lframework.xingyun.sc.excel.purchase.receive;

import com.alibaba.excel.context.AnalysisContext;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.components.excel.ExcelImportListener;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.entity.Supplier;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.basedata.service.supplier.SupplierService;
import com.lframework.starter.web.inner.entity.SysUser;
import com.lframework.xingyun.sc.service.purchase.ReceiveSheetService;
import com.lframework.xingyun.sc.vo.purchase.receive.CreateReceiveSheetVo;
import com.lframework.xingyun.sc.vo.purchase.receive.ReceiveProductVo;
import com.lframework.starter.web.inner.service.system.SysUserService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReceiveSheetImportListener extends ExcelImportListener<ReceiveSheetImportModel> {

  @Override
  protected void doInvoke(ReceiveSheetImportModel data, AnalysisContext context) {
    if (StringUtil.isBlank(data.getScCode())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“仓库编号”不能为空");
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
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“供应商编号”不能为空");
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
      SysUserService userService = ApplicationUtil.getBean(SysUserService.class);
      SysUser purchaser = userService.findByCode(data.getPurchaserCode());
      if (purchaser == null) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“采购员编号”不存在");
      }
      data.setPurchaserId(purchaser.getId());
    }
    if (data.getPaymentDate() == null) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“付款日期”不存在");
    }
    if (data.getReceiveDate() == null) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“实际到货日期”不存在");
    }
    if (StringUtil.isBlank(data.getProductCode())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“商品编号”不能为空");
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
      if (NumberUtil.le(data.getPurchasePrice(), BigDecimal.ZERO)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“采购价”必须大于0");
      }
      if (!NumberUtil.isNumberPrecision(data.getPurchasePrice(), 6)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“采购价”最多允许6位小数");
      }
    } else {
      // 赠品
      data.setPurchasePrice(BigDecimal.ZERO);
    }
    if (data.getReceiveNum() == null) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“收货数量”不能为空");
    }
    if (NumberUtil.le(data.getReceiveNum(), BigDecimal.ZERO)) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“收货数量”必须大于0");
    }
    if (!NumberUtil.isNumberPrecision(data.getReceiveNum(), 8)) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“收货数量”最多允许8位小数");
    }
  }

  @Override
  protected void afterAllAnalysed(AnalysisContext context) {

    // 根据仓库、供应商、采购员、预计到货日期 分组
    Map<Object, List<ReceiveSheetImportModel>> groupByMap = this.getDatas().stream().collect(
        Collectors.groupingBy(
            t -> t.getScCode() + "_" + t.getSupplierCode() + "_" + t.getPurchaserCode() + "_"
                + DateUtil.toLocalDate(t.getPaymentDate()) + "_" + DateUtil.toLocalDate(
                t.getReceiveDate())));

    ReceiveSheetService receiveSheetService = ApplicationUtil.getBean(ReceiveSheetService.class);

    int index = 0;
    for (List<ReceiveSheetImportModel> value : groupByMap.values()) {
      ReceiveSheetImportModel valueObj = value.get(0);
      CreateReceiveSheetVo createReceiveSheetVo = new CreateReceiveSheetVo();
      createReceiveSheetVo.setScId(valueObj.getScId());
      createReceiveSheetVo.setSupplierId(valueObj.getSupplierId());
      createReceiveSheetVo.setPurchaserId(valueObj.getPurchaserId());
      createReceiveSheetVo.setPaymentDate(DateUtil.toLocalDate(valueObj.getPaymentDate()));
      createReceiveSheetVo.setAllowModifyPaymentDate(Boolean.TRUE);
      createReceiveSheetVo.setReceiveDate(DateUtil.toLocalDate(valueObj.getReceiveDate()));
      createReceiveSheetVo.setDescription(valueObj.getDescription());
      createReceiveSheetVo.setRequired(Boolean.FALSE);

      List<ReceiveProductVo> products = new ArrayList<>();
      for (ReceiveSheetImportModel data : value) {
        ReceiveProductVo purchaseProductVo = new ReceiveProductVo();
        purchaseProductVo.setProductId(data.getProductId());
        purchaseProductVo.setPurchasePrice(data.getPurchasePrice());
        purchaseProductVo.setReceiveNum(data.getReceiveNum());
        purchaseProductVo.setDescription(data.getDetailDescription());

        products.add(purchaseProductVo);

        index++;
        this.setSuccessProcessByIndex(index);
      }
      createReceiveSheetVo.setProducts(products);

      receiveSheetService.create(createReceiveSheetVo);
    }
  }

  @Override
  protected void doComplete() {

  }
}