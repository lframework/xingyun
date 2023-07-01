package com.lframework.xingyun.template.gen.mappers;

import com.lframework.xingyun.template.gen.dto.gen.GenDetailColumnConfigDto;
import com.lframework.xingyun.template.gen.entity.GenDetailColumnConfig;
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
public interface GenDetailColumnConfigMapper extends BaseMapper<GenDetailColumnConfig> {

  /**
   * 根据数据对象ID查询
   *
   * @param ids
   * @return
   */
  List<GenDetailColumnConfigDto> getByIds(@Param("ids") List<String> ids);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  GenDetailColumnConfigDto findById(String id);
}
