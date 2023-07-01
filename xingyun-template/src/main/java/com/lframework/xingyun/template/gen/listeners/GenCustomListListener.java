package com.lframework.xingyun.template.gen.listeners;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.xingyun.template.gen.entity.GenCustomList;
import com.lframework.xingyun.template.gen.entity.GenCustomListDetail;
import com.lframework.xingyun.template.gen.entity.GenCustomListQueryParams;
import com.lframework.xingyun.template.gen.events.DataEntityDetailDeleteEvent;
import com.lframework.xingyun.template.gen.events.DataObjDeleteEvent;
import com.lframework.xingyun.template.gen.events.DataObjQueryDetailDeleteEvent;
import com.lframework.xingyun.template.gen.service.GenCustomListDetailService;
import com.lframework.xingyun.template.gen.service.GenCustomListQueryParamsService;
import com.lframework.xingyun.template.gen.service.GenCustomListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

public class GenCustomListListener {

  @Component
  public static class DataObjDeleteListener implements ApplicationListener<DataObjDeleteEvent> {

    @Autowired
    private GenCustomListService genCustomListService;

    @Override
    public void onApplicationEvent(DataObjDeleteEvent event) {

      Wrapper<GenCustomList> queryWrapper = Wrappers.lambdaQuery(GenCustomList.class)
          .eq(GenCustomList::getDataObjId, event.getId());
      if (genCustomListService.count(queryWrapper) > 0) {
        throw new DefaultClientException("数据对象【" + event.getName() + "】已关联自定义列表，无法删除！");
      }
    }
  }

  @Component
  public static class DataEntityDetailDeleteListener implements
      ApplicationListener<DataEntityDetailDeleteEvent> {

    @Autowired
    private GenCustomListDetailService genCustomListDetailService;

    @Autowired
    private GenCustomListQueryParamsService genCustomListQueryParamsService;

    @Override
    public void onApplicationEvent(DataEntityDetailDeleteEvent event) {

      Wrapper<GenCustomListDetail> queryDetailWrapper = Wrappers.lambdaQuery(
          GenCustomListDetail.class).eq(GenCustomListDetail::getDataEntityId, event.getId());
      if (genCustomListDetailService.count(queryDetailWrapper) > 0) {
        throw new DefaultClientException("字段【" + event.getName() + "】已关联自定义列表，无法删除！");
      }

      Wrapper<GenCustomListQueryParams> queryQueryDetailWrapper = Wrappers.lambdaQuery(
          GenCustomListQueryParams.class).eq(GenCustomListQueryParams::getRelaId, event.getId());
      if (genCustomListQueryParamsService.count(queryQueryDetailWrapper) > 0) {
        throw new DefaultClientException("字段【" + event.getName() + "】已关联自定义列表，无法删除！");
      }
    }
  }

  @Component
  public static class DataObjQueryDetailDeleteListener implements
      ApplicationListener<DataObjQueryDetailDeleteEvent> {

    @Autowired
    private GenCustomListDetailService genCustomListDetailService;

    @Autowired
    private GenCustomListQueryParamsService genCustomListQueryParamsService;

    @Override
    public void onApplicationEvent(DataObjQueryDetailDeleteEvent event) {
      Wrapper<GenCustomListDetail> queryDetailWrapper = Wrappers.lambdaQuery(
          GenCustomListDetail.class).eq(GenCustomListDetail::getDataEntityId, event.getId());
      if (genCustomListDetailService.count(queryDetailWrapper) > 0) {
        throw new DefaultClientException("字段【" + event.getName() + "】已关联自定义列表，无法删除！");
      }

      Wrapper<GenCustomListQueryParams> queryQueryDetailWrapper = Wrappers.lambdaQuery(
          GenCustomListQueryParams.class).eq(GenCustomListQueryParams::getRelaId, event.getId());
      if (genCustomListQueryParamsService.count(queryQueryDetailWrapper) > 0) {
        throw new DefaultClientException("字段【" + event.getName() + "】已关联自定义列表，无法删除！");
      }
    }
  }
}
