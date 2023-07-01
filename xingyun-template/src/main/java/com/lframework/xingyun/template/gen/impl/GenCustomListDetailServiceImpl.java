package com.lframework.xingyun.template.gen.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.xingyun.template.gen.entity.GenCustomListDetail;
import com.lframework.xingyun.template.gen.mappers.GenCustomListDetailMapper;
import com.lframework.xingyun.template.gen.service.GenCustomListDetailService;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenCustomListDetailServiceImpl extends
    BaseMpServiceImpl<GenCustomListDetailMapper, GenCustomListDetail> implements
    GenCustomListDetailService {

  @Override
  public List<GenCustomListDetail> getByCustomListId(String customListId) {
    Wrapper<GenCustomListDetail> queryWrapper = Wrappers.lambdaQuery(GenCustomListDetail.class)
        .eq(GenCustomListDetail::getCustomListId, customListId)
        .orderByAsc(GenCustomListDetail::getOrderNo);
    return this.list(queryWrapper);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteByCustomListId(String customListId) {
    Wrapper<GenCustomListDetail> deleteWrapper = Wrappers.lambdaQuery(GenCustomListDetail.class)
        .eq(GenCustomListDetail::getCustomListId, customListId);
    this.remove(deleteWrapper);
  }
}
