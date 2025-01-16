package com.lframework.xingyun.template.gen.mappers;

import com.lframework.xingyun.template.gen.dto.gen.GenGenerateInfoDto;
import com.lframework.xingyun.template.gen.entity.GenGenerateInfo;
import com.lframework.starter.web.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-12-08
 */
public interface GenGenerateInfoMapper extends BaseMapper<GenGenerateInfo> {

  /**
   * 根据数据对象ID查询
   *
   * @param dataObjId
   * @return
   */
  GenGenerateInfoDto getByDataObjId(String dataObjId);
}
