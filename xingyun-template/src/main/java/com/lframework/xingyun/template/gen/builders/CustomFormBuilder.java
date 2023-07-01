package com.lframework.xingyun.template.gen.builders;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.xingyun.template.gen.components.custom.form.CustomFormConfig;
import com.lframework.xingyun.template.gen.entity.GenCustomForm;
import com.lframework.xingyun.template.gen.service.GenCustomFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class CustomFormBuilder {

  @Autowired
  private GenCustomFormService genCustomFormService;

  @Cacheable(value = CustomFormConfig.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  public CustomFormConfig buildConfig(String id) {

    GenCustomForm form = genCustomFormService.findById(id);
    if (form == null) {
      throw new DefaultClientException("自定义表单不存在！");
    }

    CustomFormConfig config = new CustomFormConfig();
    config.setIsDialog(form.getIsDialog());
    config.setDialogWidth(form.getDialogWidth());
    config.setDialogTittle(form.getDialogTittle());
    config.setFormConfig(form.getFormConfig());
    config.setPrefixSubmit(form.getPrefixSubmit());
    config.setSuffixSubmit(form.getSuffixSubmit());
    config.setRequireQuery(form.getRequireQuery());
    config.setHandleBean(form.getHandleBean());

    return config;
  }
}
