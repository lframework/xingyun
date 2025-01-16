package com.lframework.xingyun.core.impl;

import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.components.generator.GenerateCodeFactory;
import com.lframework.starter.web.components.generator.rule.GenerateCodeRule;
import com.lframework.xingyun.core.dto.GenerateCodeDto;
import com.lframework.xingyun.core.mappers.GenerateCodeMapper;
import com.lframework.xingyun.core.service.GenerateCodeService;
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
