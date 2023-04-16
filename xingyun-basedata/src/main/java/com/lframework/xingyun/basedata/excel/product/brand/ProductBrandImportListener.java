package com.lframework.xingyun.basedata.excel.product.brand;

import com.alibaba.excel.context.AnalysisContext;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.constants.PatternPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.RegUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.mybatis.components.excel.ExcelImportListener;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.basedata.entity.ProductBrand;
import com.lframework.xingyun.basedata.service.product.ProductBrandService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductBrandImportListener extends ExcelImportListener<ProductBrandImportModel> {

  @Override
  protected void doInvoke(ProductBrandImportModel data, AnalysisContext context) {
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
  }

  @Override
  protected void afterAllAnalysed(AnalysisContext context) {

    ProductBrandService productBrandService = ApplicationUtil.getBean(ProductBrandService.class);

    List<ProductBrandImportModel> datas = this.getDatas();
    for (int i = 0; i < datas.size(); i++) {
      ProductBrandImportModel data = datas.get(i);

      Wrapper<ProductBrand> checkNameWrapper = Wrappers.lambdaQuery(ProductBrand.class)
          .eq(ProductBrand::getName, data.getName()).ne(ProductBrand::getCode, data.getCode());
      if (productBrandService.count(checkNameWrapper) > 0) {
        throw new DefaultClientException(
            "第" + (i + 1) + "行“名称”重复，请重新输入");
      }

      boolean isInsert = false;
      Wrapper<ProductBrand> queryWrapper = Wrappers.lambdaQuery(ProductBrand.class)
          .eq(ProductBrand::getCode, data.getCode());
      ProductBrand record = productBrandService.getOne(queryWrapper);
      if (record == null) {
        record = new ProductBrand();

        record.setId(IdUtil.getId());
        isInsert = true;
      }

      record.setCode(data.getCode());
      record.setName(data.getName());
      record.setShortName(data.getShortName());
      record.setIntroduction(data.getIntroduction());
      record.setDescription(data.getDescription());

      if (isInsert) {
        record.setAvailable(Boolean.TRUE);
      }

      productBrandService.saveOrUpdateAllColumn(record);
      data.setId(record.getId());

      this.setSuccessProcess(i);
    }
  }

  @Override
  protected void doComplete() {
    ProductBrandService productBrandService = ApplicationUtil.getBean(ProductBrandService.class);
    for (ProductBrandImportModel data : this.getDatas()) {
      productBrandService.cleanCacheByKey(data.getId());
    }
  }
}
