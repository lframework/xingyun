package com.lframework.xingyun.template.gen.listeners;

import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.lframework.starter.web.utils.DataSourceUtil;
import com.lframework.starter.web.common.event.ReloadTenantEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.ssssssss.magicapi.datasource.model.MagicDynamicDataSource;

public class GenTenantListener {

  @Component
  public static class ReloadTenantListener implements ApplicationListener<ReloadTenantEvent>,
      Ordered {

    @Autowired
    private MagicDynamicDataSource magicDynamicDataSource;

    @Autowired
    private DynamicDataSourceProperties dynamicDataSourceProperties;

    @Override
    public void onApplicationEvent(ReloadTenantEvent event) {
      DataSourceProperty dataSourceProperty = dynamicDataSourceProperties.getDatasource()
          .get("master");
      magicDynamicDataSource.add(String.valueOf(event.getTenantId()),
          DataSourceUtil.createDataSource(dataSourceProperty, event.getJdbcUrl(),
              event.getJdbcUsername(), event.getJdbcPassword(), event.getDriver()));
    }

    @Override
    public int getOrder() {
      return Integer.MAX_VALUE;
    }
  }
}
