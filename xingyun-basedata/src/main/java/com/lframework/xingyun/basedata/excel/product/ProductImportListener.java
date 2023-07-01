package com.lframework.xingyun.basedata.excel.product;

import com.alibaba.excel.context.AnalysisContext;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.constants.PatternPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.RegUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.components.excel.ExcelImportListener;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.ProductBrand;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.enums.ProductType;
import com.lframework.xingyun.basedata.service.product.ProductBrandService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryService;
import com.lframework.xingyun.basedata.service.product.ProductPropertyRelationService;
import com.lframework.xingyun.basedata.service.product.ProductPurchaseService;
import com.lframework.xingyun.basedata.service.product.ProductRetailService;
import com.lframework.xingyun.basedata.service.product.ProductSaleService;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.basedata.vo.product.purchase.CreateProductPurchaseVo;
import com.lframework.xingyun.basedata.vo.product.purchase.UpdateProductPurchaseVo;
import com.lframework.xingyun.basedata.vo.product.retail.CreateProductRetailVo;
import com.lframework.xingyun.basedata.vo.product.retail.UpdateProductRetailVo;
import com.lframework.xingyun.basedata.vo.product.sale.CreateProductSaleVo;
import com.lframework.xingyun.basedata.vo.product.sale.UpdateProductSaleVo;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductImportListener extends ExcelImportListener<ProductImportModel> {

  @Override
  protected void doInvoke(ProductImportModel data, AnalysisContext context) {
    if (StringUtil.isBlank(data.getCode())) {
      throw new DefaultClientException("第" + context.readRowHolder().getRowIndex() + "行“编号”不能为空");
    }
    if (!RegUtil.isMatch(PatternPool.PATTERN_CODE, data.getCode())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“编号”必须由字母、数字、“-_.”组成，长度不能超过20位");
    }
    if (StringUtil.isBlank(data.getName())) {
      throw new DefaultClientException("第" + context.readRowHolder().getRowIndex() + "行“名称”不能为空");
    }

    ProductService productService = ApplicationUtil.getBean(ProductService.class);
    Wrapper<Product> queryWrapper = Wrappers.lambdaQuery(Product.class)
        .eq(Product::getCode, data.getCode());
    Product product = productService.getOne(queryWrapper);

    if (StringUtil.isBlank(data.getSkuCode())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“SKU编号”不能为空");
    }

    LambdaQueryWrapper<Product> checkSkuCodeWrapper = Wrappers.lambdaQuery(Product.class)
        .eq(Product::getSkuCode, data.getSkuCode());
    if (product != null) {
      checkSkuCodeWrapper.ne(Product::getId, product.getId());
      if (product.getProductType() != ProductType.NORMAL) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行商品类型必须为" + ProductType.NORMAL.getDesc() + "，请检查");
      }
    }

    if (productService.count(checkSkuCodeWrapper) > 0) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“SKU编号”重复，请检查");
    }

    if (StringUtil.isBlank(data.getCategoryCode())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“类目编号”不能为空");
    }

    ProductCategoryService productCategoryService = ApplicationUtil.getBean(
        ProductCategoryService.class);
    Wrapper<ProductCategory> queryProductCategoryWrapper = Wrappers.lambdaQuery(
        ProductCategory.class).eq(ProductCategory::getCode, data.getCategoryCode());
    ProductCategory productCategory = productCategoryService.getOne(queryProductCategoryWrapper);
    if (productCategory == null) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“类目编号”不存在，请检查");
    }

    if (StringUtil.isBlank(data.getBrandCode())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“品牌编号”不能为空");
    }
    data.setCategoryId(productCategory.getId());

    ProductBrandService productBrandService = ApplicationUtil.getBean(
        ProductBrandService.class);
    Wrapper<ProductBrand> queryProductBrandWrapper = Wrappers.lambdaQuery(
        ProductBrand.class).eq(ProductBrand::getCode, data.getBrandCode());
    ProductBrand productBrand = productBrandService.getOne(queryProductBrandWrapper);
    if (productBrand == null) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“品牌编号”不存在，请检查");
    }
    data.setBrandId(productBrand.getId());

    if (data.getTaxRate() != null) {
      if (NumberUtil.lt(data.getTaxRate(), 0)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“进项税率（%）”不允许小于0");
      }

      if (!NumberUtil.isNumberPrecision(data.getTaxRate(), 0)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“进项税率（%）”必须为整数");
      }
    }

    if (data.getSaleTaxRate() != null) {
      if (NumberUtil.lt(data.getSaleTaxRate(), 0)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“销项税率（%）”不允许小于0");
      }

      if (!NumberUtil.isNumberPrecision(data.getSaleTaxRate(), 0)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“销项税率（%）”必须为整数");
      }
    }

    if (data.getPurchasePrice() != null) {
      if (!NumberUtil.isNumberPrecision(data.getPurchasePrice(), 2)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“采购价（元）”最多允许2位小数");
      }
      if (NumberUtil.lt(data.getPurchasePrice(), 0)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“采购价（元）”不允许小于0");
      }
    }

    if (data.getSalePrice() != null) {
      if (!NumberUtil.isNumberPrecision(data.getSalePrice(), 2)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“销售价（元）”最多允许2位小数");
      }
      if (NumberUtil.lt(data.getSalePrice(), 0)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“销售价（元）”不允许小于0");
      }
    }

    if (data.getRetailPrice() != null) {
      if (!NumberUtil.isNumberPrecision(data.getRetailPrice(), 2)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“零售价（元）”最多允许2位小数");
      }
      if (NumberUtil.lt(data.getRetailPrice(), 0)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“零售价（元）”不允许小于0");
      }
    }
  }

  @Override
  protected void afterAllAnalysed(AnalysisContext context) {

    ProductService productService = ApplicationUtil.getBean(ProductService.class);

    ProductPropertyRelationService productRelationService = ApplicationUtil.getBean(ProductPropertyRelationService.class);

    List<ProductImportModel> datas = this.getDatas();
    for (int i = 0; i < datas.size(); i++) {
      ProductImportModel data = datas.get(i);

      Wrapper<Product> checkSkuCodeWrapper = Wrappers.lambdaQuery(Product.class)
          .eq(Product::getSkuCode, data.getSkuCode()).ne(Product::getCode, data.getCode());
      if (productService.count(checkSkuCodeWrapper) > 0) {
        throw new DefaultClientException("第" + (i + 1) + "行“商品SKU编号”重复，请重新输入");
      }

      boolean isInsert = false;
      Wrapper<Product> queryWrapper = Wrappers.lambdaQuery(Product.class)
          .eq(Product::getCode, data.getCode());
      Product record = productService.getOne(queryWrapper);
      if (record == null) {
        record = new Product();

        record.setId(IdUtil.getId());
        isInsert = true;
      }

      record.setCode(data.getCode());
      record.setName(data.getName());
      if (StringUtil.isNotBlank(data.getShortName())) {
        record.setShortName(data.getShortName());
      }
      record.setSkuCode(data.getSkuCode());
      record.setExternalCode(data.getExternalCode());
      if (!isInsert) {
        // 如果是修改商品，类目变了，属性需要清空
        if (!StringUtil.equals(record.getCategoryId(), data.getCategoryId())) {
          productRelationService.deleteByProductId(record.getId());
        }
      }
      record.setCategoryId(data.getCategoryId());
      record.setBrandId(data.getBrandId());
      if (data.getTaxRate() != null) {
        record.setTaxRate(data.getTaxRate());
      }
      if (data.getSaleTaxRate() != null) {
        record.setSaleTaxRate(data.getSaleTaxRate());
      }
      record.setSpec(data.getSpec());
      record.setUnit(data.getUnit());
      record.setProductType(ProductType.NORMAL);

      if (isInsert) {
        record.setAvailable(Boolean.TRUE);
      }

      productService.saveOrUpdateAllColumn(record);
      data.setId(record.getId());

      if (data.getPurchasePrice() != null) {
        ProductPurchaseService productPurchaseService = ApplicationUtil.getBean(
            ProductPurchaseService.class);
        if (isInsert) {
          CreateProductPurchaseVo createProductPurchaseVo = new CreateProductPurchaseVo();
          createProductPurchaseVo.setId(data.getId());
          createProductPurchaseVo.setPrice(data.getPurchasePrice());

          productPurchaseService.create(createProductPurchaseVo);
        } else {
          UpdateProductPurchaseVo updateProductPurchaseVo = new UpdateProductPurchaseVo();
          updateProductPurchaseVo.setId(data.getId());
          updateProductPurchaseVo.setPrice(data.getPurchasePrice());

          productPurchaseService.update(updateProductPurchaseVo);
        }
      }

      if (data.getSalePrice() != null) {
        ProductSaleService productSaleService = ApplicationUtil.getBean(ProductSaleService.class);
        if (isInsert) {
          CreateProductSaleVo createProductSaleVo = new CreateProductSaleVo();
          createProductSaleVo.setId(data.getId());
          createProductSaleVo.setPrice(data.getSalePrice());

          productSaleService.create(createProductSaleVo);
        } else {
          UpdateProductSaleVo updateProductSaleVo = new UpdateProductSaleVo();
          updateProductSaleVo.setId(data.getId());
          updateProductSaleVo.setPrice(data.getSalePrice());

          productSaleService.update(updateProductSaleVo);
        }
      }

      if (data.getRetailPrice() != null) {
        ProductRetailService productRetailService = ApplicationUtil.getBean(
            ProductRetailService.class);
        if (isInsert) {
          CreateProductRetailVo createProductRetailVo = new CreateProductRetailVo();
          createProductRetailVo.setId(data.getId());
          createProductRetailVo.setPrice(data.getRetailPrice());

          productRetailService.create(createProductRetailVo);
        } else {
          UpdateProductRetailVo updateProductRetailVo = new UpdateProductRetailVo();
          updateProductRetailVo.setId(data.getId());
          updateProductRetailVo.setPrice(data.getRetailPrice());

          productRetailService.update(updateProductRetailVo);
        }
      }

      this.setSuccessProcess(i);
    }
  }

  @Override
  protected void doComplete() {
    ProductPropertyRelationService productPropertyRelationService = ApplicationUtil.getBean(
        ProductPropertyRelationService.class);
    ProductService productService = ApplicationUtil.getBean(ProductService.class);
    for (ProductImportModel data : this.getDatas()) {
      productService.cleanCacheByKey(data.getId());
      productPropertyRelationService.cleanCacheByKey(data.getId());
    }
  }
}
