package com.lframework.xingyun.template.gen.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.xingyun.template.gen.entity.GenDataObjDetail;
import com.lframework.xingyun.template.gen.mappers.GenDataObjDetailMapper;
import com.lframework.xingyun.template.gen.service.GenDataObjDetailService;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenDataObjDetailServiceImpl extends
    BaseMpServiceImpl<GenDataObjDetailMapper, GenDataObjDetail> implements
    GenDataObjDetailService {

  @Override
  public List<GenDataObjDetail> getByObjId(String objId) {
    Wrapper<GenDataObjDetail> queryWrapper = Wrappers.lambdaQuery(GenDataObjDetail.class)
        .eq(GenDataObjDetail::getDataObjId, objId).orderByAsc(GenDataObjDetail::getOrderNo);
    return this.list(queryWrapper);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteByObjId(String objId) {
    Wrapper<GenDataObjDetail> deleteWrapper = Wrappers.lambdaQuery(GenDataObjDetail.class)
        .eq(GenDataObjDetail::getDataObjId, objId);
    getBaseMapper().delete(deleteWrapper);
  }

  @Override
  public Boolean entityDetailIsRela(String entityDetailId) {
    return getBaseMapper().entityDetailIsRela(entityDetailId);
  }
}
