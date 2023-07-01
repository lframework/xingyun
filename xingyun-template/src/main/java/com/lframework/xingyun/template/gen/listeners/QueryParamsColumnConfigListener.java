package com.lframework.xingyun.template.gen.listeners;

import com.lframework.xingyun.template.gen.events.DataEntityDeleteEvent;
import com.lframework.xingyun.template.gen.events.DataEntityDetailDeleteEvent;
import com.lframework.xingyun.template.gen.service.GenQueryParamsColumnConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

public class QueryParamsColumnConfigListener {

  @Component
  public static class DeleteEntityListener implements ApplicationListener<DataEntityDeleteEvent> {

    @Autowired
    private GenQueryParamsColumnConfigService genQueryParamsColumnConfigService;

    @Override
    public void onApplicationEvent(DataEntityDeleteEvent event) {

      for (String columnId : event.getColumnIds()) {
        genQueryParamsColumnConfigService.deleteById(columnId);
      }
    }
  }

  @Component
  public static class DeleteEntityDetailListener implements
      ApplicationListener<DataEntityDetailDeleteEvent> {

    @Autowired
    private GenQueryParamsColumnConfigService genQueryParamsColumnConfigService;

    @Override
    public void onApplicationEvent(DataEntityDetailDeleteEvent event) {

      genQueryParamsColumnConfigService.deleteById(event.getId());
    }
  }
}
