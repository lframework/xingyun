package com.lframework.xingyun.template.gen.listeners;

import com.lframework.xingyun.template.gen.events.DataEntityDeleteEvent;
import com.lframework.xingyun.template.gen.events.DataEntityDetailDeleteEvent;
import com.lframework.xingyun.template.gen.service.GenCreateColumnConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

public class CreateColumnConfigListener {

  @Component
  public static class DeleteEntityListener implements ApplicationListener<DataEntityDeleteEvent> {

    @Autowired
    private GenCreateColumnConfigService genCreateColumnConfigService;

    @Override
    public void onApplicationEvent(DataEntityDeleteEvent event) {

      for (String columnId : event.getColumnIds()) {
        genCreateColumnConfigService.deleteById(columnId);
      }
    }
  }

  @Component
  public static class DeleteEntityDetailListener implements
      ApplicationListener<DataEntityDetailDeleteEvent> {

    @Autowired
    private GenCreateColumnConfigService genCreateColumnConfigService;

    @Override
    public void onApplicationEvent(DataEntityDetailDeleteEvent event) {

      genCreateColumnConfigService.deleteById(event.getId());
    }
  }
}
