package com.lframework.xingyun.template.gen.service;

import com.lframework.xingyun.template.gen.dto.gen.GenQueryColumnConfigDto;
import com.lframework.xingyun.template.gen.entity.GenQueryColumnConfig;
import com.lframework.xingyun.template.gen.vo.gen.UpdateQueryColumnConfigVo;
import com.lframework.starter.web.service.BaseMpService;
import java.util.List;

public interface GenQueryColumnConfigService extends BaseMpService<GenQueryColumnConfig> {

  /**
   * 根据数据对象ID查询
   *
   * @param entityId
   * @return
   */
  List<GenQueryColumnConfigDto> getByDataEntityId(String entityId);

  /**
   * 修改生成器配置信息
   *
   * @param vo
   */
  void updateGenerate(String entityId, List<UpdateQueryColumnConfigVo> vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  GenQueryColumnConfigDto findById(String id);

  /**
   * 根据ID查询
   *
   * @param id
   */
  void deleteById(String id);
}
