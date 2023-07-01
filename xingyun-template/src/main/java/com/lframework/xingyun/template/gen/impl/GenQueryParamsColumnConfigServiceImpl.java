package com.lframework.xingyun.template.gen.impl;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.xingyun.template.gen.dto.gen.GenQueryParamsColumnConfigDto;
import com.lframework.xingyun.template.gen.entity.GenDataEntityDetail;
import com.lframework.xingyun.template.gen.entity.GenQueryParamsColumnConfig;
import com.lframework.xingyun.template.gen.enums.GenQueryType;
import com.lframework.xingyun.template.gen.mappers.GenQueryParamsColumnConfigMapper;
import com.lframework.xingyun.template.gen.service.GenDataEntityDetailService;
import com.lframework.xingyun.template.gen.service.GenQueryParamsColumnConfigService;
import com.lframework.xingyun.template.gen.vo.gen.UpdateQueryParamsColumnConfigVo;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.utils.EnumUtil;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenQueryParamsColumnConfigServiceImpl
    extends BaseMpServiceImpl<GenQueryParamsColumnConfigMapper, GenQueryParamsColumnConfig>
    implements GenQueryParamsColumnConfigService {

  @Autowired
  private GenDataEntityDetailService genDataEntityDetailService;

  @Override
  public List<GenQueryParamsColumnConfigDto> getByDataEntityId(String entityId) {

    List<GenDataEntityDetail> columns = genDataEntityDetailService.getByEntityId(entityId);
    if (CollectionUtil.isEmpty(columns)) {
      return CollectionUtil.emptyList();
    }

    return getBaseMapper().getByIds(
        columns.stream().map(GenDataEntityDetail::getId).collect(Collectors.toList()));
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void updateGenerate(String entityId, List<UpdateQueryParamsColumnConfigVo> vo) {

    List<GenDataEntityDetail> columns = genDataEntityDetailService.getByEntityId(entityId);
    if (!CollectionUtil.isEmpty(columns)) {
      getBaseMapper().deleteBatchIds(
          columns.stream().map(GenDataEntityDetail::getId).collect(Collectors.toList()));
    }

    if (!CollectionUtil.isEmpty(vo)) {
      int orderNo = 1;
      for (UpdateQueryParamsColumnConfigVo updateQueryParamsColumnConfigVo : vo) {
        GenQueryParamsColumnConfig record = new GenQueryParamsColumnConfig();
        record.setId(updateQueryParamsColumnConfigVo.getId());
        record.setQueryType(
            EnumUtil.getByCode(GenQueryType.class, updateQueryParamsColumnConfigVo.getQueryType()));
        record.setOrderNo(orderNo++);

        getBaseMapper().insert(record);
      }
    }
  }

  @Override
  public GenQueryParamsColumnConfigDto findById(String id) {

    return getBaseMapper().findById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {

    getBaseMapper().deleteById(id);
  }
}
