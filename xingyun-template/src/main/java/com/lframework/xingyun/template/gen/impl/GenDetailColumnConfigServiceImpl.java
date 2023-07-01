package com.lframework.xingyun.template.gen.impl;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.xingyun.template.gen.dto.gen.GenDetailColumnConfigDto;
import com.lframework.xingyun.template.gen.entity.GenDataEntityDetail;
import com.lframework.xingyun.template.gen.entity.GenDetailColumnConfig;
import com.lframework.xingyun.template.gen.mappers.GenDetailColumnConfigMapper;
import com.lframework.xingyun.template.gen.service.GenDataEntityDetailService;
import com.lframework.xingyun.template.gen.service.GenDetailColumnConfigService;
import com.lframework.xingyun.template.gen.vo.gen.UpdateDetailColumnConfigVo;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenDetailColumnConfigServiceImpl
    extends BaseMpServiceImpl<GenDetailColumnConfigMapper, GenDetailColumnConfig>
    implements GenDetailColumnConfigService {

  @Autowired
  private GenDataEntityDetailService genDataEntityDetailService;

  @Override
  public List<GenDetailColumnConfigDto> getByDataEntityId(String entityId) {

    List<GenDataEntityDetail> columns = genDataEntityDetailService.getByEntityId(entityId);
    if (CollectionUtil.isEmpty(columns)) {
      return CollectionUtil.emptyList();
    }

    return getBaseMapper().getByIds(
        columns.stream().map(GenDataEntityDetail::getId).collect(Collectors.toList()));
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void updateGenerate(String entityId, List<UpdateDetailColumnConfigVo> vo) {

    List<GenDataEntityDetail> columns = genDataEntityDetailService.getByEntityId(entityId);
    if (!CollectionUtil.isEmpty(columns)) {
      getBaseMapper().deleteBatchIds(
          columns.stream().map(GenDataEntityDetail::getId).collect(Collectors.toList()));
    }

    if (!CollectionUtil.isEmpty(vo)) {
      int orderNo = 1;
      for (UpdateDetailColumnConfigVo updateDetailColumnConfigVo : vo) {
        GenDetailColumnConfig record = new GenDetailColumnConfig();
        record.setId(updateDetailColumnConfigVo.getId());
        record.setSpan(updateDetailColumnConfigVo.getSpan());
        record.setOrderNo(orderNo++);

        getBaseMapper().insert(record);
      }
    }
  }

  @Override
  public GenDetailColumnConfigDto findById(String id) {

    return getBaseMapper().findById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {

    getBaseMapper().deleteById(id);
  }
}
