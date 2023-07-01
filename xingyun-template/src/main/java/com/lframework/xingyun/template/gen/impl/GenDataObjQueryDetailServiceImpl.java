package com.lframework.xingyun.template.gen.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.xingyun.template.gen.entity.GenDataObjQueryDetail;
import com.lframework.xingyun.template.gen.mappers.GenDataObjQueryDetailMapper;
import com.lframework.xingyun.template.gen.service.GenDataObjQueryDetailService;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenDataObjQueryDetailServiceImpl extends
    BaseMpServiceImpl<GenDataObjQueryDetailMapper, GenDataObjQueryDetail> implements
    GenDataObjQueryDetailService {

  @Override
  public List<GenDataObjQueryDetail> getByObjId(String objId) {
    Wrapper<GenDataObjQueryDetail> queryWrapper = Wrappers.lambdaQuery(GenDataObjQueryDetail.class)
        .eq(GenDataObjQueryDetail::getDataObjId, objId)
        .orderByAsc(GenDataObjQueryDetail::getOrderNo);
    return this.list(queryWrapper);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteByObjId(String objId) {
    Wrapper<GenDataObjQueryDetail> deleteWrapper = Wrappers.lambdaQuery(GenDataObjQueryDetail.class)
        .eq(GenDataObjQueryDetail::getDataObjId, objId);
    getBaseMapper().delete(deleteWrapper);
  }
}
