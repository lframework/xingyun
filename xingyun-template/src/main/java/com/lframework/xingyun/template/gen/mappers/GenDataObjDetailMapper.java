package com.lframework.xingyun.template.gen.mappers;

import com.lframework.xingyun.template.gen.entity.GenDataObjDetail;
import com.lframework.starter.web.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 数据对象明细 Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2022-09-17
 */
public interface GenDataObjDetailMapper extends BaseMapper<GenDataObjDetail> {

  /**
   * 实体明细ID是否已关联数据对象
   *
   * @param entityDetailId
   * @return
   */
  Boolean entityDetailIsRela(@Param("entityDetailId") String entityDetailId);
}
