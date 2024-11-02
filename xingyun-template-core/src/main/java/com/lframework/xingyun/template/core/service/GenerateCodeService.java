package com.lframework.xingyun.template.core.service;

import com.lframework.starter.web.components.generator.rule.GenerateCodeRule;
import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.template.core.dto.GenerateCodeDto;
import java.util.List;

/**
 * 生成单号Service
 *
 * @author zmj
 */
public interface GenerateCodeService extends BaseService {

  /**
   * 生成code
   *
   * @param id
   * @return
   */
  String generate(Integer id);

  /**
   * 根据实体类获取编号规则
   *
   * @param entity
   * @return
   */
  List<GenerateCodeRule> getRules(GenerateCodeDto entity);
}
