package com.lframework.xingyun.template.gen.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.xingyun.template.gen.converters.GenStringConverter;
import com.lframework.xingyun.template.gen.converters.GenViewTypeConverter;
import com.lframework.xingyun.template.gen.dto.data.entity.DataEntityGenerateDto;
import com.lframework.xingyun.template.gen.dto.gen.GenCreateColumnConfigDto;
import com.lframework.xingyun.template.gen.dto.gen.GenDetailColumnConfigDto;
import com.lframework.xingyun.template.gen.dto.gen.GenGenerateInfoDto;
import com.lframework.xingyun.template.gen.dto.gen.GenQueryColumnConfigDto;
import com.lframework.xingyun.template.gen.dto.gen.GenQueryParamsColumnConfigDto;
import com.lframework.xingyun.template.gen.dto.gen.GenUpdateColumnConfigDto;
import com.lframework.xingyun.template.gen.entity.GenDataEntity;
import com.lframework.xingyun.template.gen.entity.GenDataEntityDetail;
import com.lframework.xingyun.template.gen.entity.GenSimpleTableColumn;
import com.lframework.xingyun.template.gen.enums.GenConvertType;
import com.lframework.xingyun.template.gen.enums.GenDataType;
import com.lframework.xingyun.template.gen.enums.GenKeyType;
import com.lframework.xingyun.template.gen.enums.GenOrderType;
import com.lframework.xingyun.template.gen.enums.GenStatus;
import com.lframework.xingyun.template.gen.enums.GenTemplateType;
import com.lframework.xingyun.template.gen.enums.GenViewType;
import com.lframework.xingyun.template.gen.events.DataEntityDeleteEvent;
import com.lframework.xingyun.template.gen.events.DataEntityDetailDeleteEvent;
import com.lframework.xingyun.template.gen.mappers.GenDataEntityMapper;
import com.lframework.xingyun.template.gen.service.GenCreateColumnConfigService;
import com.lframework.xingyun.template.gen.service.GenDataEntityDetailService;
import com.lframework.xingyun.template.gen.service.GenDataEntityService;
import com.lframework.xingyun.template.gen.service.GenDetailColumnConfigService;
import com.lframework.xingyun.template.gen.service.GenQueryColumnConfigService;
import com.lframework.xingyun.template.gen.service.GenQueryParamsColumnConfigService;
import com.lframework.xingyun.template.gen.service.GenUpdateColumnConfigService;
import com.lframework.xingyun.template.gen.service.GenerateInfoService;
import com.lframework.xingyun.template.gen.service.SimpleDBService;
import com.lframework.xingyun.template.gen.vo.data.entity.CreateDataEntityVo;
import com.lframework.xingyun.template.gen.vo.data.entity.GenDataEntityDetailVo;
import com.lframework.xingyun.template.gen.vo.data.entity.GenDataEntitySelectorVo;
import com.lframework.xingyun.template.gen.vo.data.entity.QueryDataEntityVo;
import com.lframework.xingyun.template.gen.vo.data.entity.UpdateDataEntityGenerateVo;
import com.lframework.xingyun.template.gen.vo.data.entity.UpdateDataEntityVo;
import com.lframework.xingyun.template.gen.vo.gen.UpdateGenerateInfoVo;
import com.lframework.xingyun.template.gen.dto.simpledb.OriSimpleTableDto;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageHelperUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.utils.IdUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenDataEntityServiceImpl extends
    BaseMpServiceImpl<GenDataEntityMapper, GenDataEntity> implements GenDataEntityService {

  @Autowired
  private GenerateInfoService generateInfoService;

  @Autowired
  private GenDataEntityDetailService genDataEntityDetailService;

  @Autowired
  private SimpleDBService simpleDBService;

  @Autowired
  private GenCreateColumnConfigService genCreateColumnConfigService;

  @Autowired
  private GenUpdateColumnConfigService genUpdateColumnConfigService;

  @Autowired
  private GenQueryColumnConfigService genQueryColumnConfigService;

  @Autowired
  private GenQueryParamsColumnConfigService genQueryParamsColumnConfigService;

  @Autowired
  private GenDetailColumnConfigService genDetailColumnConfigService;

  @Autowired
  private GenViewTypeConverter genViewTypeConverter;

  @Override
  public PageResult<GenDataEntity> query(Integer pageIndex, Integer pageSize,
      QueryDataEntityVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<GenDataEntity> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<GenDataEntity> query(QueryDataEntityVo vo) {
    return getBaseMapper().query(vo);
  }

  @Override
  public PageResult<GenDataEntity> selector(Integer pageIndex, Integer pageSize,
      GenDataEntitySelectorVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<GenDataEntity> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public GenDataEntity findById(String id) {
    return getBaseMapper().selectById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateDataEntityVo data) {
    GenDataEntity record = new GenDataEntity();
    record.setId(IdUtil.getId());
    record.setName(data.getName());
    if (!StringUtil.isBlank(data.getCategoryId())) {
      record.setCategoryId(data.getCategoryId());
    }
    record.setAvailable(Boolean.TRUE);
    record.setDescription(
        StringUtil.isBlank(data.getDescription()) ? StringPool.EMPTY_STR : data.getDescription());



    OriSimpleTableDto table = simpleDBService.getByTableName(data.getTableName());
    if (ObjectUtil.isNull(table)) {
      throw new DefaultClientException("数据表【" + data.getTableName() + "】不存在！");
    }

    record.setTableSchema(table.getTableSchema());
    record.setTableName(table.getTableName());
    record.setEngine(table.getEngine());
    record.setTableCollation(table.getTableCollation());
    record.setTableComment(table.getTableComment());
    record.setConvertType(GenConvertType.UNDERLINE_TO_CAMEL);

    this.save(record);

    int orderNo = 1;
    for (GenDataEntityDetailVo column : data.getColumns()) {
      GenSimpleTableColumn columnDto = table.getColumns().stream()
          .filter(t -> t.getDbColumnName().equals(column.getId())).findFirst().orElse(null);
      if (columnDto == null) {
        throw new DefaultClientException("字段【" + column.getId() + "】不存在！");
      }

      GenDataEntityDetail detail = this.buildDetail(column, columnDto);
      detail.setId(IdUtil.getId());
      detail.setEntityId(record.getId());
      detail.setColumnOrder(orderNo);

      genDataEntityDetailService.save(detail);

      orderNo++;
    }

    // 设置默认的基础设置
    UpdateGenerateInfoVo updateGenerateInfoVo = new UpdateGenerateInfoVo();
    updateGenerateInfoVo.setTemplateType(GenTemplateType.LIST.getCode());
    updateGenerateInfoVo.setPackageName("com.lframework");
    updateGenerateInfoVo.setModuleName(StringPool.EMPTY_STR);
    updateGenerateInfoVo.setBizName(
        GenStringConverter.convertToNormalLowerCase(GenConvertType.UNDERLINE_TO_CAMEL,
                record.getTableName()));
    // 强制转驼峰并且首字母大写
    String className = GenStringConverter.convertToCamelCase(GenConvertType.UNDERLINE_TO_CAMEL,
            record.getTableName());
    updateGenerateInfoVo.setClassName(
        className.substring(0, 1).toUpperCase() + className.substring(1));
    updateGenerateInfoVo.setClassDescription(
        StringUtil.isEmpty(record.getTableComment()) ? StringPool.EMPTY_STR
            : record.getTableComment());
    updateGenerateInfoVo.setKeyType(GenKeyType.SNOW_FLAKE.getCode());
    updateGenerateInfoVo.setMenuCode(StringPool.EMPTY_STR);
    updateGenerateInfoVo.setMenuName(StringPool.EMPTY_STR);
    updateGenerateInfoVo.setDetailSpan(4);
    updateGenerateInfoVo.setIsCache(true);
    updateGenerateInfoVo.setHasDelete(false);

    generateInfoService.updateGenerate(record.getId(), updateGenerateInfoVo);

    return record.getId();
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateDataEntityVo vo) {
    GenDataEntity record = this.getById(vo.getId());
    if (record == null) {
      throw new DefaultClientException("数据实体不存在！");
    }
    Wrapper<GenDataEntity> updateWrapper = Wrappers.lambdaUpdate(GenDataEntity.class)
        .eq(GenDataEntity::getId, vo.getId()).set(GenDataEntity::getName, vo.getName())
        .set(GenDataEntity::getAvailable, vo.getAvailable()).set(GenDataEntity::getCategoryId,
            StringUtil.isBlank(vo.getCategoryId()) ? null : vo.getCategoryId())
        .set(GenDataEntity::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());
    this.update(updateWrapper);

    List<GenDataEntityDetail> columnDtos = genDataEntityDetailService.getByEntityId(record.getId());

    int orderNo = 1;
    for (GenDataEntityDetailVo column : vo.getColumns()) {
      GenDataEntityDetail detail = columnDtos.stream()
          .filter(t -> t.getId().equals(column.getId())).findFirst().orElse(null);
      detail.setName(column.getName());

      detail.setDataType(EnumUtil.getByCode(GenDataType.class, column.getDataType()));
      detail.setDescription(column.getDescription());
      detail.setViewType(EnumUtil.getByCode(GenViewType.class, column.getViewType()));
      detail.setFixEnum(column.getFixEnum());
      detail.setEnumBack(column.getEnumBack());
      detail.setEnumFront(column.getEnumFront());
      detail.setRegularExpression(column.getRegularExpression());
      detail.setIsOrder(column.getIsOrder());
      detail.setOrderType(EnumUtil.getByCode(GenOrderType.class, column.getOrderType()));
      detail.setLen(column.getLen());
      detail.setDecimals(column.getDecimals());
      detail.setDataDicId(StringPool.EMPTY_STR);
      detail.setCustomSelectorId(StringPool.EMPTY_STR);
      if (!StringUtil.isBlank(column.getDataDicId())
          && detail.getViewType() == GenViewType.DATA_DIC) {
        detail.setDataDicId(column.getDataDicId());
      }
      if (!StringUtil.isBlank(column.getCustomSelectorId())
          && detail.getViewType() == GenViewType.CUSTOM_SELECTOR) {
        detail.setCustomSelectorId(column.getCustomSelectorId());
      }

      if (!genViewTypeConverter.canConvert(detail.getViewType(), detail.getDataType())) {
        List<GenViewType> viewTypes = genViewTypeConverter.convert(detail.getDataType());
        throw new DefaultClientException(
            "字段【" + detail.getName() + "】数据类型和显示类型不匹配，当前数据类型为【" + detail.getDataType().getDesc()
                + "】，" + (!CollectionUtil.isEmpty(viewTypes) ? "显示类型只能为【" + CollectionUtil.join(
                genViewTypeConverter.convert(detail.getDataType()).stream()
                    .map(GenViewType::getDesc)
                    .collect(Collectors.toList()), StringPool.STR_SPLIT_CN) + "】" : "暂不支持显示此数据类型"));
      }
      detail.setColumnOrder(orderNo);

      genDataEntityDetailService.updateById(detail);

      orderNo++;
    }
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void delete(@NonNull String id) {

    GenDataEntity record = getBaseMapper().selectById(id);
    if (record == null) {
      throw new DefaultClientException("数据实体不存在！");
    }

    List<GenDataEntityDetail> columns = genDataEntityDetailService.getByEntityId(id);
    List<String> columnIds = columns.stream().map(GenDataEntityDetail::getId)
        .collect(Collectors.toList());

    getBaseMapper().deleteById(id);

    genDataEntityDetailService.deleteByEntityId(id);

    DataEntityDeleteEvent event = new DataEntityDeleteEvent(this);
    event.setId(id);
    event.setName(record.getName());
    event.setColumnIds(columnIds);

    ApplicationUtil.publishEvent(event);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void enable(String id) {

    Wrapper<GenDataEntity> wrapper = Wrappers.lambdaUpdate(GenDataEntity.class)
        .set(GenDataEntity::getAvailable, Boolean.TRUE).eq(GenDataEntity::getId, id);
    getBaseMapper().update(wrapper);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void unable(String id) {

    Wrapper<GenDataEntity> wrapper = Wrappers.lambdaUpdate(GenDataEntity.class)
        .set(GenDataEntity::getAvailable, Boolean.FALSE).eq(GenDataEntity::getId, id);
    getBaseMapper().update(wrapper);
  }

  @Override
  public DataEntityGenerateDto getGenerateById(String id) {
    DataEntityGenerateDto result = new DataEntityGenerateDto();

    List<GenDataEntityDetail> columns = genDataEntityDetailService.getByEntityId(id);
    result.setColumns(columns);

    GenGenerateInfoDto generateInfo = generateInfoService.getByEntityId(id);
    result.setGenerateInfo(generateInfo);

    List<GenCreateColumnConfigDto> createColumnConfigDtos = genCreateColumnConfigService.getByDataEntityId(
        id);
    result.setCreateConfigs(createColumnConfigDtos);

    List<GenUpdateColumnConfigDto> updateColumnConfigDtos = genUpdateColumnConfigService.getByDataEntityId(
        id);
    result.setUpdateConfigs(updateColumnConfigDtos);

    List<GenQueryColumnConfigDto> queryColumnConfigDtos = genQueryColumnConfigService.getByDataEntityId(
        id);
    result.setQueryConfigs(queryColumnConfigDtos);

    List<GenQueryParamsColumnConfigDto> queryParamsColumnConfigDtos = genQueryParamsColumnConfigService.getByDataEntityId(
        id);
    result.setQueryParamsConfigs(queryParamsColumnConfigDtos);

    List<GenDetailColumnConfigDto> detailColumnConfigDtos = genDetailColumnConfigService.getByDataEntityId(
        id);
    result.setDetailConfigs(detailColumnConfigDtos);

    return result;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void updateGenerate(UpdateDataEntityGenerateVo vo) {

    generateInfoService.updateGenerate(vo.getId(), vo.getGenerateInfo());

    genCreateColumnConfigService.updateGenerate(vo.getId(), vo.getCreateConfigs());

    genUpdateColumnConfigService.updateGenerate(vo.getId(), vo.getUpdateConfigs());

    genQueryColumnConfigService.updateGenerate(vo.getId(), vo.getQueryConfigs());

    genQueryParamsColumnConfigService.updateGenerate(vo.getId(), vo.getQueryParamsConfigs());

    genDetailColumnConfigService.updateGenerate(vo.getId(), vo.getDetailConfigs());

    Wrapper<GenDataEntity> updateWrapper = Wrappers.lambdaUpdate(GenDataEntity.class)
        .set(GenDataEntity::getGenStatus, GenStatus.SET_GEN).eq(GenDataEntity::getId, vo.getId());
    getBaseMapper().update(updateWrapper);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void syncTable(String id) {
    // 查询simpleTable
    GenDataEntity table = this.getById(id);
    if (table == null) {
      throw new DefaultClientException("数据表不存在！");
    }
    List<GenDataEntityDetail> columns = genDataEntityDetailService.getByEntityId(table.getId());

    // 最新的数据库结构
    OriSimpleTableDto oriTable = simpleDBService.getByTableName(table.getTableName());
    List<GenSimpleTableColumn> oriColumns = oriTable.getColumns();

    // 1、列是否匹配
    // db中是否有新增列
    List<GenDataEntityDetail> finalColumns = columns;
    List<GenSimpleTableColumn> newDbColumns = oriColumns.stream().filter(
            t -> finalColumns.stream().noneMatch(c -> c.getDbColumnName().equals(t.getDbColumnName())))
        .collect(Collectors.toList());
    // 类型发生变化的列
    List<GenSimpleTableColumn> changeTypeColumns = oriColumns.stream().filter(
            t -> finalColumns.stream().anyMatch(
                c -> c.getDbColumnName().equals(t.getDbColumnName()) && c.getDbDataType() != t.getDataType()))
        .collect(Collectors.toList());
    if (!CollectionUtil.isEmpty(changeTypeColumns)) {
      newDbColumns.addAll(changeTypeColumns);
    }

    // db中是否有删除列
    List<GenDataEntityDetail> deleteDbColumns = columns.stream().filter(
            t -> oriColumns.stream().noneMatch(c -> c.getDbColumnName().equals(t.getDbColumnName()))
                || changeTypeColumns.stream()
                .anyMatch(c2 -> c2.getDbColumnName().equals(t.getDbColumnName())))
        .collect(Collectors.toList());

    // 先删除、后新增
    if (!CollectionUtil.isEmpty(deleteDbColumns)) {
      for (GenDataEntityDetail deleteDbColumn : deleteDbColumns) {
        genDataEntityDetailService.removeById(deleteDbColumn.getId());
        // 发布删除事件
        DataEntityDetailDeleteEvent event = new DataEntityDetailDeleteEvent(this);
        event.setId(deleteDbColumn.getId());
        event.setName(deleteDbColumn.getDbColumnName());
        ApplicationUtil.publishEvent(event);
      }
    }

    List<GenDataEntityDetail> details = genDataEntityDetailService.getByEntityId(table.getId());
    if (CollectionUtil.isEmpty(details)) {
      details = new ArrayList<>();
    }
    int orderNo = 1;
    for (GenDataEntityDetail detail : details) {
      detail.setColumnOrder(orderNo);
      genDataEntityDetailService.updateById(detail);
      orderNo++;
    }

    table = this.getById(id);
    columns = details;

    orderNo = 1;
    for (GenDataEntityDetail column : columns) {
      column.setOrdinalPosition(orderNo);
      genDataEntityDetailService.updateById(column);
      orderNo++;
    }

    if (!CollectionUtil.isEmpty(newDbColumns)) {
      for (GenSimpleTableColumn columnDto : newDbColumns) {
        GenDataEntityDetail detail = this.buildDetail(columnDto);
        detail.setId(IdUtil.getId());
        detail.setColumnOrder(orderNo);
        detail.setEntityId(table.getId());
        genDataEntityDetailService.save(detail);

        orderNo++;
      }
    }
  }

  private GenDataEntityDetail buildDetail(GenSimpleTableColumn columnDto) {
    GenDataEntityDetail detail = new GenDataEntityDetail();

    detail.setName(columnDto.getColumnComment());
    detail.setColumnName(GenStringConverter.convertToCamelCase(GenConvertType.UNDERLINE_TO_CAMEL,
        columnDto.getDbColumnName()));
    detail.setIsKey(columnDto.getIsKey());
    detail.setDataType(columnDto.getDataType());
    List<GenViewType> viewTypes = genViewTypeConverter.convert(detail.getDataType());
    if (CollectionUtil.isEmpty(viewTypes)) {
      throw new DefaultClientException("字段：" + columnDto.getDbColumnName() + "类型暂不支持！");
    }
    detail.setViewType(viewTypes.get(0));
    detail.setFixEnum(Boolean.FALSE);
    detail.setIsOrder(Boolean.FALSE);

    detail.setDbColumnName(columnDto.getDbColumnName());
    detail.setDbDataType(columnDto.getDataType());
    detail.setIsNullable(columnDto.getIsNullable());
    detail.setIsKey(columnDto.getIsKey());
    detail.setColumnDefault(columnDto.getColumnDefault());
    detail.setOrdinalPosition(columnDto.getOrdinalPosition());
    detail.setColumnComment(columnDto.getColumnComment());
    detail.setDbLen(columnDto.getLen());
    detail.setDbDecimals(columnDto.getDecimals());

    return detail;
  }

  private GenDataEntityDetail buildDetail(GenDataEntityDetailVo column,
      GenSimpleTableColumn columnDto) {
    GenDataEntityDetail detail = new GenDataEntityDetail();
    detail.setColumnName(GenStringConverter.convertToCamelCase(GenConvertType.UNDERLINE_TO_CAMEL,
        columnDto.getDbColumnName()));
    detail.setIsKey(columnDto.getIsKey());

    detail.setName(column.getName());
    detail.setDataType(EnumUtil.getByCode(GenDataType.class, column.getDataType()));
    detail.setDescription(column.getDescription());
    detail.setViewType(EnumUtil.getByCode(GenViewType.class, column.getViewType()));
    detail.setFixEnum(column.getFixEnum());
    detail.setEnumBack(column.getEnumBack());
    detail.setEnumFront(column.getEnumFront());
    detail.setRegularExpression(column.getRegularExpression());
    detail.setIsOrder(column.getIsOrder());
    detail.setOrderType(EnumUtil.getByCode(GenOrderType.class, column.getOrderType()));
    detail.setLen(column.getLen());
    detail.setDecimals(column.getDecimals());
    detail.setDataDicId(StringPool.EMPTY_STR);
    detail.setCustomSelectorId(StringPool.EMPTY_STR);
    if (!StringUtil.isBlank(column.getDataDicId())
        && detail.getViewType() == GenViewType.DATA_DIC) {
      detail.setDataDicId(column.getDataDicId());
    }
    if (!StringUtil.isBlank(column.getCustomSelectorId())
        && detail.getViewType() == GenViewType.CUSTOM_SELECTOR) {
      detail.setCustomSelectorId(column.getCustomSelectorId());
    }
    detail.setDbColumnName(columnDto.getDbColumnName());
    detail.setDbDataType(columnDto.getDataType());
    detail.setIsNullable(columnDto.getIsNullable());
    detail.setColumnDefault(columnDto.getColumnDefault());
    detail.setOrdinalPosition(columnDto.getOrdinalPosition());
    detail.setColumnComment(columnDto.getColumnComment());
    detail.setDbLen(columnDto.getLen());
    detail.setDbDecimals(columnDto.getDecimals());

    if (!genViewTypeConverter.canConvert(detail.getViewType(), detail.getDataType())) {
      List<GenViewType> viewTypes = genViewTypeConverter.convert(detail.getDataType());
      throw new DefaultClientException(
          "字段【" + detail.getName() + "】数据类型和显示类型不匹配，当前数据类型为【" + detail.getDataType().getDesc()
              + "】，" + (!CollectionUtil.isEmpty(viewTypes) ? "显示类型只能为【" + CollectionUtil.join(
              genViewTypeConverter.convert(detail.getDataType()).stream().map(GenViewType::getDesc)
                  .collect(Collectors.toList()), StringPool.STR_SPLIT_CN) + "】" : "暂不支持显示此数据类型"));
    }

    return detail;
  }
}
