package com.lframework.xingyun.basedata.excel.product.saleproperty;

import com.alibaba.excel.context.AnalysisContext;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.constants.PatternPool;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.RegUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.components.excel.ExcelImportListener;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.xingyun.basedata.entity.ProductSalePropertyDefinition;
import com.lframework.xingyun.basedata.entity.ProductSalePropertyItem;
import com.lframework.xingyun.basedata.service.product.ProductSalePropertyDefinitionService;
import com.lframework.xingyun.basedata.service.product.ProductSalePropertyItemService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductSalePropertyImportListener extends
    ExcelImportListener<ProductSalePropertyImportModel> {

  private final Map<String, Integer> propertyRowMap = new HashMap<>();
  private final Map<String, Integer> propertyNameRowMap = new HashMap<>();
  private final Map<String, ProductSalePropertyImportModel> propertyMap = new HashMap<>();
  private final Map<String, Integer> itemCodeRowMap = new HashMap<>();
  private final Map<String, Integer> itemNameRowMap = new HashMap<>();

  @Override
  protected void doInvoke(ProductSalePropertyImportModel data, AnalysisContext context) {

    int rowIndex = context.readRowHolder().getRowIndex();

    checkProperty(data, rowIndex);
    checkItemRequired(data, rowIndex);
    checkItemDuplicate(data, rowIndex);
  }

  private void checkProperty(ProductSalePropertyImportModel data, int rowIndex) {

    if (StringUtil.isBlank(data.getCode())) {
      throw new DefaultClientException("第" + rowIndex + "行“属性编号”不能为空");
    }
    if (!RegUtil.isMatch(PatternPool.PATTERN_CODE, data.getCode())) {
      throw new DefaultClientException(
          "第" + rowIndex + "行“属性编号”必须由字母、数字、“-_.”组成，长度不能超过20位");
    }
    if (StringUtil.isBlank(data.getName())) {
      throw new DefaultClientException("第" + rowIndex + "行“属性名称”不能为空");
    }

    ProductSalePropertyDefinitionService propertyService = ApplicationUtil.getBean(
        ProductSalePropertyDefinitionService.class);
    ProductSalePropertyDefinition property = getProperty(propertyService, data.getCode());
    if (property != null) {
      if (!StringUtil.equals(property.getName(), data.getName())) {
        throw new DefaultClientException("第" + rowIndex + "行“属性名称”与已存在的属性不一致");
      }
      data.setId(property.getId());
      return;
    }

    checkNewPropertyName(propertyService, data, rowIndex);

    ProductSalePropertyImportModel firstData = propertyMap.get(data.getCode());
    if (firstData == null) {
      propertyMap.put(data.getCode(), data);
      propertyRowMap.put(data.getCode(), rowIndex);
      return;
    }

    if (!StringUtil.equals(firstData.getName(), data.getName())) {
      throw new DefaultClientException(
          "第" + rowIndex + "行“属性编号”与第" + propertyRowMap.get(data.getCode())
              + "行重复，但属性信息不一致");
    }
  }

  private void checkNewPropertyName(ProductSalePropertyDefinitionService propertyService,
      ProductSalePropertyImportModel data, int rowIndex) {

    Wrapper<ProductSalePropertyDefinition> checkNameWrapper = Wrappers.lambdaQuery(
            ProductSalePropertyDefinition.class)
        .eq(ProductSalePropertyDefinition::getName, data.getName())
        .eq(ProductSalePropertyDefinition::getAvailable, Boolean.TRUE);
    if (propertyService.count(checkNameWrapper) > 0) {
      throw new DefaultClientException("第" + rowIndex + "行“属性名称”已存在");
    }

    ProductSalePropertyImportModel firstData = propertyMap.get(data.getCode());
    Integer firstRowIndex = propertyNameRowMap.get(data.getName());
    if (firstRowIndex != null && firstData == null) {
      throw new DefaultClientException(
          "第" + rowIndex + "行“属性名称”与第" + firstRowIndex + "行重复");
    }
    propertyNameRowMap.putIfAbsent(data.getName(), rowIndex);
  }

  private void checkItemRequired(ProductSalePropertyImportModel data, int rowIndex) {

    if (StringUtil.isBlank(data.getItemCode())) {
      throw new DefaultClientException("第" + rowIndex + "行“属性值编号”不能为空");
    }
    checkItemCode(rowIndex, data.getItemCode());
    if (StringUtil.isBlank(data.getItemName())) {
      throw new DefaultClientException("第" + rowIndex + "行“属性值名称”不能为空");
    }
  }

  private static void checkItemCode(int rowIndex, String itemCode) {

    if (!RegUtil.isMatch(PatternPool.PATTERN_CODE, itemCode)) {
      throw new DefaultClientException(
          "第" + rowIndex + "行“属性值编号”必须由字母、数字、“-_.”组成，长度不能超过20位");
    }
  }

  private void checkItemDuplicate(ProductSalePropertyImportModel data, int rowIndex) {

    ProductSalePropertyItemService itemService = ApplicationUtil.getBean(
        ProductSalePropertyItemService.class);
    if (!StringUtil.isBlank(data.getId())) {
      Wrapper<ProductSalePropertyItem> checkCodeWrapper = Wrappers.lambdaQuery(
              ProductSalePropertyItem.class)
          .eq(ProductSalePropertyItem::getPropertyId, data.getId())
          .eq(ProductSalePropertyItem::getCode, data.getItemCode())
          .eq(ProductSalePropertyItem::getAvailable, Boolean.TRUE);
      if (itemService.count(checkCodeWrapper) > 0) {
        throw new DefaultClientException("第" + rowIndex + "行“属性值编号”已存在");
      }

      Wrapper<ProductSalePropertyItem> checkNameWrapper = Wrappers.lambdaQuery(
              ProductSalePropertyItem.class)
          .eq(ProductSalePropertyItem::getPropertyId, data.getId())
          .eq(ProductSalePropertyItem::getName, data.getItemName())
          .eq(ProductSalePropertyItem::getAvailable, Boolean.TRUE);
      if (itemService.count(checkNameWrapper) > 0) {
        throw new DefaultClientException("第" + rowIndex + "行“属性值名称”已存在");
      }
    }

    String itemCodeKey = data.getCode() + "@" + data.getItemCode();
    if (itemCodeRowMap.containsKey(itemCodeKey)) {
      throw new DefaultClientException(
          "第" + rowIndex + "行“属性值编号”与第" + itemCodeRowMap.get(itemCodeKey) + "行重复");
    }
    itemCodeRowMap.put(itemCodeKey, rowIndex);

    String itemNameKey = data.getCode() + "@" + data.getItemName();
    if (itemNameRowMap.containsKey(itemNameKey)) {
      throw new DefaultClientException(
          "第" + rowIndex + "行“属性值名称”与第" + itemNameRowMap.get(itemNameKey) + "行重复");
    }
    itemNameRowMap.put(itemNameKey, rowIndex);
  }

  @Override
  protected void afterAllAnalysed(AnalysisContext context) {

    ProductSalePropertyDefinitionService propertyService = ApplicationUtil.getBean(
        ProductSalePropertyDefinitionService.class);
    ProductSalePropertyItemService itemService = ApplicationUtil.getBean(
        ProductSalePropertyItemService.class);

    List<ProductSalePropertyImportModel> datas = this.getDatas();
    for (int i = 0; i < datas.size(); i++) {
      ProductSalePropertyImportModel data = datas.get(i);

      ProductSalePropertyDefinition property = getProperty(propertyService, data.getCode());
      if (property == null) {
        property = createProperty(data);
        propertyService.save(property);
      }
      data.setId(property.getId());

      ProductSalePropertyItem item = new ProductSalePropertyItem();
      item.setId(IdUtil.getId());
      item.setCode(data.getItemCode());
      item.setName(data.getItemName());
      item.setPropertyId(property.getId());
      item.setAvailable(Boolean.TRUE);
      item.setDescription(StringPool.EMPTY_STR);
      itemService.save(item);

      this.setSuccessProcessByIndex(i);
    }
  }

  private ProductSalePropertyDefinition createProperty(ProductSalePropertyImportModel data) {

    ProductSalePropertyDefinition record = new ProductSalePropertyDefinition();
    record.setId(IdUtil.getId());
    record.setCode(data.getCode());
    record.setName(data.getName());
    record.setAvailable(Boolean.TRUE);
    record.setDescription(StringPool.EMPTY_STR);
    return record;
  }

  private ProductSalePropertyDefinition getProperty(
      ProductSalePropertyDefinitionService propertyService, String code) {

    Wrapper<ProductSalePropertyDefinition> queryWrapper = Wrappers.lambdaQuery(
            ProductSalePropertyDefinition.class)
        .eq(ProductSalePropertyDefinition::getCode, code)
        .eq(ProductSalePropertyDefinition::getAvailable, Boolean.TRUE);
    return propertyService.getOne(queryWrapper);
  }

  @Override
  protected void doComplete() {
  }
}
