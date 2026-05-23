package com.lframework.xingyun.basedata.excel.product;

import com.alibaba.excel.context.AnalysisContext;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.constants.PatternPool;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.RegUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.components.excel.ExcelImportListener;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.ProductBrand;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.enums.ProductType;
import com.lframework.xingyun.basedata.service.product.ProductBrandService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryService;
import com.lframework.xingyun.basedata.service.product.ProductCodeService;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.basedata.vo.product.info.CreateProductVo;
import com.lframework.xingyun.basedata.vo.product.info.ProductSkuVo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductImportListener extends ExcelImportListener<ProductImportModel> {

  private List<String> rowCodeList = new ArrayList<>();

  @Override
  protected void doInvoke(ProductImportModel data, AnalysisContext context) {
    if (StringUtil.isNotBlank(data.getCode()) && !RegUtil.isMatch(PatternPool.PATTERN_CODE,
        data.getCode())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex()
              + "行“商品编号”必须由字母、数字、“-_.”组成，长度不能超过20位");
    }
    if (StringUtil.isNotBlank(data.getCode()) && rowCodeList.contains(data.getCode())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“商品编号”与其他行重复");
    }
    if (StringUtil.isNotBlank(data.getCode())) {
      rowCodeList.add(data.getCode());
    }
    if (StringUtil.isBlank(data.getSkuCode())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“SKU编号”不能为空");
    }
    if (!RegUtil.isMatch(PatternPool.PATTERN_CODE, data.getSkuCode())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex()
              + "行“SKU编号”必须由字母、数字、“-_.”组成，长度不能超过20位");
    }
    List<String> currentRowCodes = new ArrayList<>();
    if (StringUtil.isNotBlank(data.getCode())) {
      currentRowCodes.add(data.getCode());
    }
    if (currentRowCodes.contains(data.getSkuCode())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“SKU编号”与商品编号重复");
    }
    if (rowCodeList.contains(data.getSkuCode())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“SKU编号”与其他行重复");
    }
    rowCodeList.add(data.getSkuCode());
    currentRowCodes.add(data.getSkuCode());
    String[] multiCodeArr =
        StringUtil.isBlank(data.getMultiCode()) ? new String[0] : data.getMultiCode()
            .split(",");

    List<String> multiCodes = new ArrayList<>();
    for (int i = 0; i < multiCodeArr.length; i++) {
      String multiCode = multiCodeArr[i];
      if (StringUtil.isBlank(multiCode)) {
        continue;
      }

      if (!RegUtil.isMatch(PatternPool.PATTERN_CODE, multiCode)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex()
                + "行“SKU扩展编号" + (i + 1) + "”必须由字母、数字、“-_.”组成，长度不能超过20位");
      }

      if (currentRowCodes.contains(multiCode)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“SKU扩展编号" + (i + 1)
                + "”与本行其他编号重复");
      }
      if (rowCodeList.contains(multiCode)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“SKU扩展编号" + (i + 1)
                + "”与其他行重复");
      }

      rowCodeList.add(multiCode);
      currentRowCodes.add(multiCode);

      multiCodes.add(multiCode);
    }

    data.setMultiCodes(multiCodes);

    if (StringUtil.isBlank(data.getName())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“名称”不能为空");
    }

    if (StringUtil.isBlank(data.getCategoryCode())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“商品分类编号”不能为空");
    }

    ProductCategoryService productCategoryService = ApplicationUtil.getBean(
        ProductCategoryService.class);
    Wrapper<ProductCategory> queryProductCategoryWrapper = Wrappers.lambdaQuery(
            ProductCategory.class).eq(ProductCategory::getCode, data.getCategoryCode())
        .eq(ProductCategory::getAvailable, Boolean.TRUE);
    ProductCategory productCategory = productCategoryService.getOne(queryProductCategoryWrapper);
    if (productCategory == null) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“商品分类编号”不存在，请检查");
    }

    data.setCategoryId(productCategory.getId());

    if (!StringUtil.isBlank(data.getBrandCode())) {
      ProductBrandService productBrandService = ApplicationUtil.getBean(
          ProductBrandService.class);
      Wrapper<ProductBrand> queryProductBrandWrapper = Wrappers.lambdaQuery(
              ProductBrand.class).eq(ProductBrand::getCode, data.getBrandCode())
          .eq(ProductBrand::getAvailable, Boolean.TRUE);
      ProductBrand productBrand = productBrandService.getOne(queryProductBrandWrapper);
      if (productBrand == null) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“商品品牌编号”不存在，请检查");
      }
      data.setBrandId(productBrand.getId());
    }

    if (data.getWeight() != null) {
      if (NumberUtil.lt(data.getWeight(), 0)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“重量（kg）”不允许小于0");
      }

      if (!NumberUtil.isNumberPrecision(data.getWeight(), 2)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“重量（kg）”最多允许2位小数");
      }
    }

    if (data.getVolume() != null) {
      if (NumberUtil.lt(data.getVolume(), 0)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“体积（cm³）”不允许小于0");
      }

      if (!NumberUtil.isNumberPrecision(data.getVolume(), 2)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“体积（cm³）”最多允许2位小数");
      }
    }

    if (data.getTaxRate() != null) {
      if (NumberUtil.lt(data.getTaxRate(), 0)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“进项税率（%）”不允许小于0");
      }

      if (!NumberUtil.isNumberPrecision(data.getTaxRate(), 2)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“进项税率（%）”最多允许2位小数");
      }
    }

    if (data.getSaleTaxRate() != null) {
      if (NumberUtil.lt(data.getSaleTaxRate(), 0)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“销项税率（%）”不允许小于0");
      }

      if (!NumberUtil.isNumberPrecision(data.getSaleTaxRate(), 2)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“销项税率（%）”最多允许2位小数");
      }
    }

    if (data.getPurchasePrice() == null) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“采购价（元）”不能为空");
    }

    if (!NumberUtil.isNumberPrecision(data.getPurchasePrice(), 6)) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“采购价（元）”最多允许6位小数");
    }
    if (NumberUtil.lt(data.getPurchasePrice(), 0)) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“采购价（元）”不允许小于0");
    }

    if (data.getSalePrice() == null) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“销售价（元）”不能为空");
    }

    if (!NumberUtil.isNumberPrecision(data.getSalePrice(), 6)) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“销售价（元）”最多允许6位小数");
    }
    if (NumberUtil.lt(data.getSalePrice(), 0)) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“销售价（元）”不允许小于0");
    }

    if (data.getRetailPrice() == null) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“零售价（元）”不能为空");
    }

    if (!NumberUtil.isNumberPrecision(data.getRetailPrice(), 6)) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“零售价（元）”最多允许6位小数");
    }
    if (NumberUtil.lt(data.getRetailPrice(), 0)) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“零售价（元）”不允许小于0");
    }
  }

  @Override
  protected void afterAllAnalysed(AnalysisContext context) {

    ProductService productService = ApplicationUtil.getBean(ProductService.class);

    List<ProductImportModel> datas = this.getDatas();
    for (int i = 0; i < datas.size(); i++) {
      ProductImportModel data = datas.get(i);

      List<String> multiCodes = new ArrayList<>(data.getMultiCodes());
      multiCodes.add(data.getSkuCode());

      ProductCodeService productCodeService = ApplicationUtil.getBean(ProductCodeService.class);
      List<String> conflictCodes = productCodeService.checkMultiCodes(multiCodes, null);
      if (CollectionUtil.isNotEmpty(conflictCodes)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行编号【" + StringUtil.join(
                StringPool.STR_SPLIT_CN, conflictCodes) + "】已存在");
      }

      // 判断分类是否是末级分类
      ProductCategoryService productCategoryService = ApplicationUtil.getBean(
          ProductCategoryService.class);
      ProductCategory productCategory = productCategoryService.findById(data.getCategoryId());
      Wrapper<ProductCategory> checkCategoryWrapper = Wrappers.lambdaQuery(
              ProductCategory.class).eq(ProductCategory::getParentId, productCategory.getId())
          .eq(ProductCategory::getAvailable, Boolean.TRUE);
      if (productCategoryService.count(checkCategoryWrapper) > 0) {
        throw new DefaultClientException(
            "第" + (i + 1) + "行“商品分类”不是末级分类，请使用末级分类");
      }
      data.setId(productService.create(toCreateVo(data)));

      this.setSuccessProcess(i);
    }
  }

  private CreateProductVo toCreateVo(ProductImportModel data) {

    CreateProductVo vo = new CreateProductVo();
    vo.setCode(data.getCode());
    vo.setName(data.getName());
    vo.setShortName(data.getShortName());
    vo.setCategoryId(data.getCategoryId());
    vo.setBrandId(data.getBrandId());
    vo.setTaxRate(data.getTaxRate());
    vo.setSaleTaxRate(data.getSaleTaxRate());
    vo.setSpec(data.getSpec());
    vo.setUnit(data.getUnit());
    vo.setWeight(data.getWeight());
    vo.setVolume(data.getVolume());
    vo.setProductType(ProductType.NORMAL.getCode());
    vo.setSkus(Collections.singletonList(toSkuVo(data)));

    return vo;
  }

  private ProductSkuVo toSkuVo(ProductImportModel data) {

    ProductSkuVo skuVo = new ProductSkuVo();
    skuVo.setCode(data.getSkuCode());
    skuVo.setMultiCodes(data.getMultiCodes());
    skuVo.setPurchasePrice(data.getPurchasePrice());
    skuVo.setSalePrice(data.getSalePrice());
    skuVo.setRetailPrice(data.getRetailPrice());

    return skuVo;
  }

  @Override
  protected void doComplete() {
  }
}
