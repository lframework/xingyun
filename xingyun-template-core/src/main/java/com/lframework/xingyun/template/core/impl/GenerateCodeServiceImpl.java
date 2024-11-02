package com.lframework.xingyun.template.core.impl;

import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.starter.web.components.generator.GenerateCodeFactory;
import com.lframework.starter.web.components.generator.rule.GenerateCodeRule;
import com.lframework.xingyun.template.core.dto.GenerateCodeDto;
import com.lframework.xingyun.template.core.mappers.GenerateCodeMapper;
import com.lframework.xingyun.template.core.service.GenerateCodeService;
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

    GenerateCodeService thisService = ApplicationUtil.getBean(this.getClass());
    List<GenerateCodeRule> ruleList = thisService.getRules(sysGenerateCode);

    return GenerateCodeFactory.generate(ruleList);
  }

  @Cacheable(value = GenerateCodeDto.CACHE_NAME, key = "@cacheVariables.tenantId() + #entity.id", unless = "#result == null")
  @Override
  public List<GenerateCodeRule> getRules(GenerateCodeDto entity) {
    return GenerateCodeFactory.getRules(entity.getConfigStr());
  }
}
