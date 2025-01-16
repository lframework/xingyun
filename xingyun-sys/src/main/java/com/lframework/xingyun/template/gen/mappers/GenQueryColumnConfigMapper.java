package com.lframework.xingyun.template.gen.mappers;

import com.lframework.xingyun.template.gen.dto.gen.GenQueryColumnConfigDto;
import com.lframework.xingyun.template.gen.entity.GenQueryColumnConfig;
import com.lframework.starter.web.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-12-10
 */
public interface GenQueryColumnConfigMapper extends BaseMapper<GenQueryColumnConfig> {

  /**
   * 根据数据对象ID查询
   *
   * @param ids
   * @return
   */
  List<GenQueryColumnConfigDto> getByIds(@Param("ids") List<String> ids);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  GenQueryColumnConfigDto findById(String id);
}
