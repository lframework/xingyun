package com.lframework.xingyun.template.gen.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.xingyun.template.gen.entity.GenDataEntityDetail;
import com.lframework.xingyun.template.gen.mappers.GenDataEntityDetailMapper;
import com.lframework.xingyun.template.gen.service.GenDataEntityDetailService;
import com.lframework.xingyun.template.gen.vo.data.entity.GenDataEntityDetailSelectorVo;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenDataEntityDetailServiceImpl extends
    BaseMpServiceImpl<GenDataEntityDetailMapper, GenDataEntityDetail> implements
    GenDataEntityDetailService {

  @Override
  public List<GenDataEntityDetail> getByEntityId(String entityId) {
    Wrapper<GenDataEntityDetail> queryWrapper = Wrappers.lambdaQuery(GenDataEntityDetail.class)
        .eq(GenDataEntityDetail::getEntityId, entityId)
        .orderByAsc(GenDataEntityDetail::getColumnOrder);

    return getBaseMapper().selectList(queryWrapper);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteByEntityId(String entityId) {
    Wrapper<GenDataEntityDetail> queryWrapper = Wrappers.lambdaQuery(GenDataEntityDetail.class)
        .eq(GenDataEntityDetail::getEntityId, entityId);
    getBaseMapper().delete(queryWrapper);
  }

  @Override
  public List<GenDataEntityDetail> selector(GenDataEntityDetailSelectorVo vo) {
    Wrapper<GenDataEntityDetail> queryWrapper = Wrappers.lambdaQuery(GenDataEntityDetail.class)
        .eq(GenDataEntityDetail::getEntityId, vo.getEntityId())
        .orderByAsc(GenDataEntityDetail::getColumnOrder);

    return getBaseMapper().selectList(queryWrapper);
  }
}
