package com.lframework.xingyun.template.gen.impl;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.xingyun.template.gen.dto.gen.GenCreateColumnConfigDto;
import com.lframework.xingyun.template.gen.entity.GenCreateColumnConfig;
import com.lframework.xingyun.template.gen.entity.GenDataEntityDetail;
import com.lframework.xingyun.template.gen.mappers.GenCreateColumnConfigMapper;
import com.lframework.xingyun.template.gen.service.GenCreateColumnConfigService;
import com.lframework.xingyun.template.gen.service.GenDataEntityDetailService;
import com.lframework.xingyun.template.gen.vo.gen.UpdateCreateColumnConfigVo;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenCreateColumnConfigServiceImpl
    extends BaseMpServiceImpl<GenCreateColumnConfigMapper, GenCreateColumnConfig>
    implements GenCreateColumnConfigService {


  @Autowired
  private GenDataEntityDetailService genDataEntityDetailService;

  @Override
  public List<GenCreateColumnConfigDto> getByDataEntityId(String entityId) {

    List<GenDataEntityDetail> columns = genDataEntityDetailService.getByEntityId(entityId);
    if (CollectionUtil.isEmpty(columns)) {
      return CollectionUtil.emptyList();
    }

    return getBaseMapper().getByIds(
        columns.stream().map(GenDataEntityDetail::getId).collect(Collectors.toList()));
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void updateGenerate(String entityId, List<UpdateCreateColumnConfigVo> vo) {

    List<GenDataEntityDetail> columns = genDataEntityDetailService.getByEntityId(entityId);
    if (!CollectionUtil.isEmpty(columns)) {
      getBaseMapper().deleteBatchIds(
          columns.stream().map(GenDataEntityDetail::getId).collect(Collectors.toList()));
    }

    if (!CollectionUtil.isEmpty(vo)) {
      int orderNo = 1;
      for (UpdateCreateColumnConfigVo updateCreateColumnConfigVo : vo) {
        GenCreateColumnConfig record = new GenCreateColumnConfig();
        record.setId(updateCreateColumnConfigVo.getId());
        record.setRequired(updateCreateColumnConfigVo.getRequired());
        record.setOrderNo(orderNo++);

        getBaseMapper().insert(record);
      }
    }
  }

  @Override
  public GenCreateColumnConfigDto findById(String id) {

    return getBaseMapper().findById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {

    getBaseMapper().deleteById(id);
  }
}
