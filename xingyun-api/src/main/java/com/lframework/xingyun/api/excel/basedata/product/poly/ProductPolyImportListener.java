package com.lframework.xingyun.api.excel.basedata.product.poly;

import com.alibaba.excel.context.AnalysisContext;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.constants.PatternPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.IdUtil;
import com.lframework.common.utils.NumberUtil;
import com.lframework.common.utils.RegUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.components.excel.ExcelImportListener;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.ProductBrand;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.entity.ProductPoly;
import com.lframework.xingyun.basedata.entity.ProductSalePropGroup;
import com.lframework.xingyun.basedata.service.product.IProductBrandService;
import com.lframework.xingyun.basedata.service.product.IProductCategoryService;
import com.lframework.xingyun.basedata.service.product.IProductPolySalePropGroupService;
import com.lframework.xingyun.basedata.service.product.IProductPolyService;
import com.lframework.xingyun.basedata.service.product.IProductSalePropGroupService;
import com.lframework.xingyun.basedata.vo.product.poly.saleprop.CreateProductPolySalePropGroupVo;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductPolyImportListener extends ExcelImportListener<ProductPolyImportModel> {

  @Override
  protected void doInvoke(ProductPolyImportModel data, AnalysisContext context) {
    if (StringUtil.isBlank(data.getCode())) {
      throw new DefaultClientException("第" + context.readRowHolder().getRowIndex() + "行“商品货号”不能为空");
    }
    if (!RegUtil.isMatch(PatternPool.PATTERN_CODE, data.getCode())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“商品货号”必须由字母、数字、“-_.”组成，长度不能超过20位");
    }
    if (StringUtil.isBlank(data.getName())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“SPU名称”不能为空");
    }

    if (StringUtil.isBlank(data.getCategoryCode())) {
      throw new DefaultClientException("第" + context.readRowHolder().getRowIndex() + "行“类目编号”不能为空");
    }

    IProductCategoryService productCategoryService = ApplicationUtil.getBean(
        IProductCategoryService.class);
    Wrapper<ProductCategory> queryProductCategoryWrapper = Wrappers.lambdaQuery(
        ProductCategory.class).eq(ProductCategory::getCode, data.getCategoryCode());
    ProductCategory productCategory = productCategoryService.getOne(queryProductCategoryWrapper);
    if (productCategory == null) {
      throw new DefaultClientException("第" + context.readRowHolder().getRowIndex() + "行“类目编号”不存在");
    }
    data.setCategoryId(productCategory.getId());

    if (StringUtil.isBlank(data.getBrandCode())) {
      throw new DefaultClientException("第" + context.readRowHolder().getRowIndex() + "行“品牌编号”不能为空");
    }

    IProductBrandService productBrandService = ApplicationUtil.getBean(IProductBrandService.class);
    Wrapper<ProductBrand> queryProductBrandWrapper = Wrappers.lambdaQuery(ProductBrand.class)
        .eq(ProductBrand::getCode, data.getBrandCode());
    ProductBrand productBrand = productBrandService.getOne(queryProductBrandWrapper);
    if (productBrand == null) {
      throw new DefaultClientException("第" + context.readRowHolder().getRowIndex() + "行“品牌编号”不存在");
    }
    data.setBrandId(productBrand.getId());

    if (data.getTaxRate() == null) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“进项税率（%）”不能为空");
    }
    if (NumberUtil.lt(data.getTaxRate(), 0)) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“进项税率（%）”不允许小于0");
    }
    if (!NumberUtil.isNumberPrecision(data.getTaxRate(), 0)) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“进项税率（%）”必须为整数");
    }

    if (data.getSaleTaxRate() == null) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“销项税率（%）”不能为空");
    }
    if (NumberUtil.lt(data.getSaleTaxRate(), 0)) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“销项税率（%）”不允许小于0");
    }
    if (!NumberUtil.isNumberPrecision(data.getSaleTaxRate(), 0)) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“销项税率（%）”必须为整数");
    }

    if (!StringUtil.isBlank(data.getSalePropGroupCode1())) {
      IProductSalePropGroupService productSalePropGroupService = ApplicationUtil.getBean(
          IProductSalePropGroupService.class);
      Wrapper<ProductSalePropGroup> querySalePropGroupWrapper = Wrappers.lambdaQuery(
              ProductSalePropGroup.class)
          .eq(ProductSalePropGroup::getCode, data.getSalePropGroupCode1());
      ProductSalePropGroup salePropGroup = productSalePropGroupService.getOne(
          querySalePropGroupWrapper);
      if (salePropGroup == null) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“销售属性组1编号”不存在");
      }
      data.setSalePropGroupId1(salePropGroup.getId());
    }

    if (!StringUtil.isBlank(data.getSalePropGroupCode2())) {
      IProductSalePropGroupService productSalePropGroupService = ApplicationUtil.getBean(
          IProductSalePropGroupService.class);
      Wrapper<ProductSalePropGroup> querySalePropGroupWrapper = Wrappers.lambdaQuery(
              ProductSalePropGroup.class)
          .eq(ProductSalePropGroup::getCode, data.getSalePropGroupCode2());
      ProductSalePropGroup salePropGroup = productSalePropGroupService.getOne(
          querySalePropGroupWrapper);
      if (salePropGroup == null) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“销售属性组2编号”不存在");
      }
      data.setSalePropGroupId2(salePropGroup.getId());
    }

    data.setMultiSaleprop(!StringUtil.isBlank(data.getSalePropGroupId1()) || !StringUtil.isBlank(
        data.getSalePropGroupId2()));
  }

  @Override
  protected void afterAllAnalysed(AnalysisContext context) {

    IProductPolySalePropGroupService productPolySalePropGroupService = ApplicationUtil.getBean(
        IProductPolySalePropGroupService.class);

    IProductPolyService productPolyService = ApplicationUtil.getBean(IProductPolyService.class);

    List<ProductPolyImportModel> datas = this.getDatas();
    for (int i = 0; i < datas.size(); i++) {
      ProductPolyImportModel data = datas.get(i);
      Wrapper<ProductPoly> checkNameWrapper = Wrappers.lambdaQuery(ProductPoly.class)
          .eq(ProductPoly::getName, data.getName()).ne(ProductPoly::getCode, data.getCode());
      if (productPolyService.count(checkNameWrapper) > 0) {
        throw new DefaultClientException(
            "第" + (i + 1) + "行“SPU名称”重复，请重新输入");
      }

      boolean isInsert = false;
      Wrapper<ProductPoly> queryWrapper = Wrappers.lambdaQuery(ProductPoly.class)
          .eq(ProductPoly::getCode, data.getCode());
      ProductPoly record = productPolyService.getOne(queryWrapper);
      if (record == null) {
        record = new ProductPoly();

        record.setId(IdUtil.getId());
        isInsert = true;
      }

      record.setCode(data.getCode());
      record.setName(data.getName());
      record.setShortName(data.getShortName());
      record.setBrandId(data.getBrandId());
      if (isInsert) {
        record.setCategoryId(data.getCategoryId());
        record.setMultiSaleprop(data.getMultiSaleprop());
      }

      record.setTaxRate(data.getTaxRate());
      record.setSaleTaxRate(data.getSaleTaxRate());

      productPolyService.saveOrUpdate(record);
      data.setId(record.getId());

      if (isInsert) {
        if (data.getMultiSaleprop()) {
          CreateProductPolySalePropGroupVo createProductPolySalePropGroupVo = new CreateProductPolySalePropGroupVo();
          createProductPolySalePropGroupVo.setPolyId(data.getId());
          List<String> salePropGroupIds = new ArrayList<>();
          if (!StringUtil.isBlank(data.getSalePropGroupId1())) {
            salePropGroupIds.add(data.getSalePropGroupId1());
          }
          if (!StringUtil.isBlank(data.getSalePropGroupId2())) {
            salePropGroupIds.add(data.getSalePropGroupId2());
          }
          createProductPolySalePropGroupVo.setSalePropGroupIds(salePropGroupIds);

          productPolySalePropGroupService.create(createProductPolySalePropGroupVo);
        }
      }

      this.setSuccessProcess(i);
    }
  }

  @Override
  protected void doComplete() {
    IProductPolyService productPolyService = ApplicationUtil.getBean(IProductPolyService.class);
    for (ProductPolyImportModel data : this.getDatas()) {
      productPolyService.cleanCacheByKey(data.getId());
    }
  }
}
