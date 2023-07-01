package com.lframework.xingyun.template.gen.builders;

import cn.hutool.json.JSONObject;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.xingyun.template.gen.components.custom.page.CustomPageConfig;
import com.lframework.xingyun.template.gen.service.GenCustomPageService;
import com.lframework.xingyun.template.gen.entity.GenCustomPage;
import com.lframework.starter.web.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomPageBuilder {

  @Autowired
  private GenCustomPageService genCustomPageService;

  //@Cacheable(value = CustomPageConfig.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  public CustomPageConfig buildConfig(Integer id) {

    GenCustomPage page = genCustomPageService.findById(id);
    if (page == null) {
      throw new DefaultClientException("自定义页面不存在！");
    }

    CustomPageConfig config = new CustomPageConfig();
    config.setId(page.getId());
    JSONObject obj = new JSONObject();
    obj.set("template", page.getPageCode());
    String templateStr = JsonUtil.toJsonStr(obj);
    templateStr = templateStr.substring("{\"template\":".length(), templateStr.length() - 1);

    String scriptStr = page.getScriptCode();
    String componentConfig = scriptStr.substring(0, scriptStr.indexOf("{") + 1) + "template:" + templateStr + "," + scriptStr.substring(scriptStr.indexOf("{") + 1);
    componentConfig = "return " + componentConfig.substring("export default".length());
    config.setComponentConfig(componentConfig);

    return config;
  }
}
