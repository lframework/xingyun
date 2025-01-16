package com.lframework.xingyun.template.gen.builders;

import com.lframework.xingyun.template.gen.components.DataEntity;
import com.lframework.xingyun.template.gen.components.DataEntityColumn;
import com.lframework.xingyun.template.gen.dto.gen.GenGenerateInfoDto;
import com.lframework.xingyun.template.gen.entity.GenDataEntity;
import com.lframework.xingyun.template.gen.entity.GenDataEntityDetail;
import com.lframework.xingyun.template.gen.service.GenCreateColumnConfigService;
import com.lframework.xingyun.template.gen.service.GenDataEntityDetailService;
import com.lframework.xingyun.template.gen.service.GenDataEntityService;
import com.lframework.xingyun.template.gen.service.GenDetailColumnConfigService;
import com.lframework.xingyun.template.gen.service.GenQueryColumnConfigService;
import com.lframework.xingyun.template.gen.service.GenQueryParamsColumnConfigService;
import com.lframework.xingyun.template.gen.service.GenUpdateColumnConfigService;
import com.lframework.xingyun.template.gen.service.GenerateInfoService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 数据实体Builder
 */
@Component
public class DataEntityBuilder {

  @Autowired
  private GenDataEntityService genDataEntityService;

  @Autowired
  private GenerateInfoService generateInfoService;

  @Autowired
  private GenDataEntityDetailService genDataEntityDetailService;

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

  /**
   * 根据数据对象ID构建
   *
   * @param id
   * @return
   */
  public DataEntity build(String id) {

    // 根据ID查询数据对象
    GenDataEntity dataEntity = genDataEntityService.findById(id);
    DataEntity result = new DataEntity();
    result.setId(dataEntity.getId());
    result.setName(dataEntity.getName());
    result.setDescription(dataEntity.getDescription());
    result.setTable(dataEntity);
    result.setColumns(this.buildColumns(dataEntity.getId()));
    result.setGenerateInfo(this.buildGenerateInfo(dataEntity.getId()));

    return result;
  }

  private GenGenerateInfoDto buildGenerateInfo(String dataObjId) {

    return generateInfoService.getByEntityId(dataObjId);
  }

  private List<DataEntityColumn> buildColumns(String entityId) {

    List<DataEntityColumn> results = new ArrayList<>();

    List<GenDataEntityDetail> columns = genDataEntityDetailService.getByEntityId(entityId);
    for (GenDataEntityDetail column : columns) {
      DataEntityColumn result = new DataEntityColumn();
      result.setId(column.getId());
      result.setName(column.getName());
      result.setColumnName(column.getColumnName());
      result.setIsKey(column.getIsKey());
      result.setDataType(column.getDataType());
      result.setColumnOrder(column.getColumnOrder());
      result.setDescription(column.getDescription());
      result.setViewType(column.getViewType());
      result.setTableColumn(column);
      result.setFixEnum(column.getFixEnum());
      result.setEnumBack(column.getEnumBack());
      result.setEnumFront(column.getEnumFront());
      result.setRegularExpression(column.getRegularExpression());
      result.setIsOrder(column.getIsOrder());
      if (result.getIsOrder()) {
        result.setOrderType(column.getOrderType());
      }

      result.setCreateConfig(genCreateColumnConfigService.findById(column.getId()));
      result.setUpdateConfig(genUpdateColumnConfigService.findById(column.getId()));
      result.setQueryConfig(genQueryColumnConfigService.findById(column.getId()));
      result.setQueryParamsConfig(genQueryParamsColumnConfigService.findById(column.getId()));
      result.setDetailConfig(genDetailColumnConfigService.findById(column.getId()));
      result.setLen(column.getLen());
      result.setDataDicId(column.getDataDicId());
      result.setCustomSelectorId(column.getCustomSelectorId());
      result.setDecimals(column.getDecimals());

      results.add(result);
    }

    return results;
  }
}
