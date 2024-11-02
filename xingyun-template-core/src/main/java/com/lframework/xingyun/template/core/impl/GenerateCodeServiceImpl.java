package com.lframework.xingyun.template.core.impl;

import cn.hutool.json.JSONArray;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.starter.web.components.generator.GenerateCodeFactory;
import com.lframework.starter.web.components.generator.handler.GenerateCodeRuleHandler;
import com.lframework.starter.web.components.generator.rule.GenerateCodeRule;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.xingyun.template.core.dto.GenerateCodeDto;
import com.lframework.xingyun.template.core.mappers.GenerateCodeMapper;
import com.lframework.xingyun.template.core.service.GenerateCodeService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class GenerateCodeServiceImpl implements GenerateCodeService {

  @Autowired
  private GenerateCodeMapper generateCodeMapper;

  @Override
  public String generate(Integer id) {
    GenerateCodeDto sysGenerateCode = generateCodeMapper.findById(id);
    List<GenerateCodeRuleHandler> ruleHandlerList = GenerateCodeFactory.getInstance(
        sysGenerateCode.getConfigStr());

    GenerateCodeService thisService = ApplicationUtil.getBean(this.getClass());
    List<GenerateCodeRule> ruleList = thisService.getRules(sysGenerateCode);

    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < ruleHandlerList.size(); i++) {
      GenerateCodeRuleHandler ruleHandler = ruleHandlerList.get(i);
      builder.append(ruleHandler.generate(ruleList.get(i)));
    }

    return builder.toString();
  }

  @Cacheable(value = GenerateCodeDto.CACHE_NAME, key = "@cacheVariables.tenantId() + #entity.id", unless = "#result == null")
  @Override
  public List<GenerateCodeRule> getRules(GenerateCodeDto entity) {
    return this.getRules(entity.getConfigStr());
  }

  @Override
  public List<GenerateCodeRule> getRules(String configStr) {
    List<GenerateCodeRuleHandler> ruleHandlerList = GenerateCodeFactory.getInstance(
        configStr);
    JSONArray configArr = JsonUtil.parseArray(configStr);

    List<GenerateCodeRule> results = new ArrayList<>();

    for (int i = 0; i < ruleHandlerList.size(); i++) {
      GenerateCodeRuleHandler ruleHandler = ruleHandlerList.get(i);
      results.add(ruleHandler.parseRule(configArr.getStr(i)));
    }

    return results;
  }
}
