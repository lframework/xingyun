package com.lframework.xingyun.basedata.excel.product.property;

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
import com.lframework.xingyun.basedata.entity.ProductCategoryPropertyDefinition;
import com.lframework.xingyun.basedata.entity.ProductCategoryPropertyItem;
import com.lframework.xingyun.basedata.enums.ColumnDataType;
import com.lframework.xingyun.basedata.enums.ColumnType;
import com.lframework.xingyun.basedata.enums.PropertyType;
import com.lframework.xingyun.basedata.service.product.ProductCategoryPropertyDefinitionService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryPropertyItemService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductCategoryPropertyImportListener extends
    ExcelImportListener<ProductCategoryPropertyImportModel> {

  private final Map<String, Integer> propertyRowMap = new HashMap<>();
  private final Map<String, Integer> propertyNameRowMap = new HashMap<>();
  private final Map<String, ProductCategoryPropertyImportModel> propertyMap = new HashMap<>();
  private final Map<String, Integer> itemCodeRowMap = new HashMap<>();
  private final Map<String, Integer> itemNameRowMap = new HashMap<>();

  @Override
  protected void doInvoke(ProductCategoryPropertyImportModel data, AnalysisContext context) {

    int rowIndex = context.readRowHolder().getRowIndex();

    checkProperty(data, rowIndex);

    ColumnType columnType = parseColumnType(data.getColumnType());
    if (columnType == ColumnType.CUSTOM) {
      if (hasItem(data)) {
        throw new DefaultClientException("第" + rowIndex + "行“手动录入”的属性不能导入属性值");
      }
    } else {
      checkItemRequired(data, rowIndex);
      checkItemDuplicate(data, rowIndex);
    }
  }

  private void checkProperty(ProductCategoryPropertyImportModel data, int rowIndex) {

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
    Boolean isRequired = parseRequired(data.getIsRequired());
    ColumnType columnType = parseColumnType(data.getColumnType());

    ProductCategoryPropertyDefinitionService propertyService = ApplicationUtil.getBean(
        ProductCategoryPropertyDefinitionService.class);
    ProductCategoryPropertyDefinition property = getProperty(propertyService, data.getCode());
    if (property != null) {
      if (!StringUtil.equals(property.getName(), data.getName())) {
        throw new DefaultClientException("第" + rowIndex + "行“属性名称”与已存在的属性不一致");
      }
      if (!isRequired.equals(property.getIsRequired())) {
        throw new DefaultClientException("第" + rowIndex + "行“是否必填”与已存在的属性不一致");
      }
      if (property.getColumnType() != columnType) {
        throw new DefaultClientException("第" + rowIndex + "行“字段类型”与已存在的属性不一致");
      }
      data.setId(property.getId());
      return;
    }

    checkNewPropertyName(propertyService, data, rowIndex);

    ProductCategoryPropertyImportModel firstData = propertyMap.get(data.getCode());
    if (firstData == null) {
      propertyMap.put(data.getCode(), data);
      propertyRowMap.put(data.getCode(), rowIndex);
      return;
    }

    if (!StringUtil.equals(firstData.getName(), data.getName())
        || !StringUtil.equals(firstData.getIsRequired(), data.getIsRequired())
        || !StringUtil.equals(firstData.getColumnType(), data.getColumnType())) {
      throw new DefaultClientException(
          "第" + rowIndex + "行“属性编号”与第" + propertyRowMap.get(data.getCode())
              + "行重复，但属性信息不一致");
    }
  }

  private void checkNewPropertyName(ProductCategoryPropertyDefinitionService propertyService,
      ProductCategoryPropertyImportModel data, int rowIndex) {

    Wrapper<ProductCategoryPropertyDefinition> checkNameWrapper = Wrappers.lambdaQuery(
            ProductCategoryPropertyDefinition.class)
        .eq(ProductCategoryPropertyDefinition::getName, data.getName())
        .eq(ProductCategoryPropertyDefinition::getAvailable, Boolean.TRUE);
    if (propertyService.count(checkNameWrapper) > 0) {
      throw new DefaultClientException("第" + rowIndex + "行“属性名称”已存在");
    }

    ProductCategoryPropertyImportModel firstData = propertyMap.get(data.getCode());
    Integer firstRowIndex = propertyNameRowMap.get(data.getName());
    if (firstRowIndex != null && firstData == null) {
      throw new DefaultClientException(
          "第" + rowIndex + "行“属性名称”与第" + firstRowIndex + "行重复");
    }
    propertyNameRowMap.putIfAbsent(data.getName(), rowIndex);
  }

  private void checkItemRequired(ProductCategoryPropertyImportModel data, int rowIndex) {

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

  private void checkItemDuplicate(ProductCategoryPropertyImportModel data, int rowIndex) {

    ProductCategoryPropertyItemService itemService = ApplicationUtil.getBean(
        ProductCategoryPropertyItemService.class);
    if (!StringUtil.isBlank(data.getId())) {
      Wrapper<ProductCategoryPropertyItem> checkCodeWrapper = Wrappers.lambdaQuery(
              ProductCategoryPropertyItem.class)
          .eq(ProductCategoryPropertyItem::getPropertyId, data.getId())
          .eq(ProductCategoryPropertyItem::getCode, data.getItemCode())
          .eq(ProductCategoryPropertyItem::getAvailable, Boolean.TRUE);
      if (itemService.count(checkCodeWrapper) > 0) {
        throw new DefaultClientException("第" + rowIndex + "行“属性值编号”已存在");
      }

      Wrapper<ProductCategoryPropertyItem> checkNameWrapper = Wrappers.lambdaQuery(
              ProductCategoryPropertyItem.class)
          .eq(ProductCategoryPropertyItem::getPropertyId, data.getId())
          .eq(ProductCategoryPropertyItem::getName, data.getItemName())
          .eq(ProductCategoryPropertyItem::getAvailable, Boolean.TRUE);
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

    ProductCategoryPropertyDefinitionService propertyService = ApplicationUtil.getBean(
        ProductCategoryPropertyDefinitionService.class);
    ProductCategoryPropertyItemService itemService = ApplicationUtil.getBean(
        ProductCategoryPropertyItemService.class);

    List<ProductCategoryPropertyImportModel> datas = this.getDatas();
    for (int i = 0; i < datas.size(); i++) {
      ProductCategoryPropertyImportModel data = datas.get(i);

      ProductCategoryPropertyDefinition property = getProperty(propertyService, data.getCode());
      if (property == null) {
        property = createProperty(data);
        propertyService.save(property);
      }
      data.setId(property.getId());

      if (property.getColumnType() != ColumnType.CUSTOM) {
        ProductCategoryPropertyItem item = new ProductCategoryPropertyItem();
        item.setId(IdUtil.getId());
        item.setCode(data.getItemCode());
        item.setName(data.getItemName());
        item.setPropertyId(property.getId());
        item.setAvailable(Boolean.TRUE);
        item.setDescription(StringPool.EMPTY_STR);
        itemService.save(item);
      }

      this.setSuccessProcessByIndex(i);
    }
  }

  private ProductCategoryPropertyDefinition createProperty(ProductCategoryPropertyImportModel data) {

    ProductCategoryPropertyDefinition record = new ProductCategoryPropertyDefinition();
    record.setId(IdUtil.getId());
    record.setCode(data.getCode());
    record.setName(data.getName());
    record.setIsRequired(parseRequired(data.getIsRequired()));
    record.setColumnType(parseColumnType(data.getColumnType()));
    if (record.getColumnType() == ColumnType.CUSTOM) {
    record.setColumnDataType(ColumnDataType.STRING);
    }
    record.setPropertyType(PropertyType.APPOINT);
    record.setAvailable(Boolean.TRUE);
    record.setDescription(StringPool.EMPTY_STR);
    return record;
  }

  private ProductCategoryPropertyDefinition getProperty(
      ProductCategoryPropertyDefinitionService propertyService, String code) {

    Wrapper<ProductCategoryPropertyDefinition> queryWrapper = Wrappers.lambdaQuery(
            ProductCategoryPropertyDefinition.class)
        .eq(ProductCategoryPropertyDefinition::getCode, code)
        .eq(ProductCategoryPropertyDefinition::getAvailable, Boolean.TRUE);
    return propertyService.getOne(queryWrapper);
  }

  static Boolean parseRequired(String desc) {

    if (StringUtil.equals(desc, "是")) {
      return Boolean.TRUE;
    }
    if (StringUtil.equals(desc, "否")) {
      return Boolean.FALSE;
    }
    throw new DefaultClientException("是否必填只能为“是”或“否”");
  }

  static ColumnType parseColumnType(String desc) {

    for (ColumnType columnType : ColumnType.values()) {
      if (StringUtil.equals(columnType.getDesc(), desc)) {
        return columnType;
      }
    }
    throw new DefaultClientException("字段类型只能为“多选”、“单选”、“手动录入”");
  }

  static boolean hasItem(ProductCategoryPropertyImportModel data) {

    return !StringUtil.isBlank(data.getItemCode()) || !StringUtil.isBlank(data.getItemName());
  }

  @Override
  protected void doComplete() {
  }
}
