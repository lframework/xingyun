package com.lframework.xingyun.template.gen.listeners;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.xingyun.template.gen.entity.GenDataObj;
import com.lframework.xingyun.template.gen.entity.GenDataObjDetail;
import com.lframework.xingyun.template.gen.events.DataEntityDeleteEvent;
import com.lframework.xingyun.template.gen.events.DataEntityDetailDeleteEvent;
import com.lframework.xingyun.template.gen.service.GenDataObjDetailService;
import com.lframework.xingyun.template.gen.service.GenDataObjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

public class GenDataObjListener {

  @Component
  public static class DataEntityDeleteListener implements
      ApplicationListener<DataEntityDeleteEvent> {

    @Autowired
    private GenDataObjService genDataObjService;

    @Autowired
    private GenDataObjDetailService genDataObjDetailService;

    @Override
    public void onApplicationEvent(DataEntityDeleteEvent event) {

      Wrapper<GenDataObj> queryWrapper = Wrappers.lambdaQuery(GenDataObj.class)
          .eq(GenDataObj::getMainTableId, event.getId());
      if (genDataObjService.count(queryWrapper) > 0) {
        throw new DefaultClientException("数据实体【" + event.getName() + "】已关联数据对象，无法删除！");
      }

      Wrapper<GenDataObjDetail> queryDetailWrapper = Wrappers.lambdaQuery(GenDataObjDetail.class)
          .eq(GenDataObjDetail::getSubTableId, event.getId());
      if (genDataObjDetailService.count(queryDetailWrapper) > 0) {
        throw new DefaultClientException("数据实体【" + event.getName() + "】已关联数据对象，无法删除！");
      }
    }
  }

  @Component
  public static class DataEntityDetailDeleteListener implements
      ApplicationListener<DataEntityDetailDeleteEvent> {

    @Autowired
    private GenDataObjDetailService genDataObjDetailService;

    @Override
    public void onApplicationEvent(DataEntityDetailDeleteEvent event) {
      if (genDataObjDetailService.entityDetailIsRela(event.getId())) {
        throw new DefaultClientException("字段【" + event.getName() + "】已关联数据对象，无法删除！");
      }
    }
  }
}
