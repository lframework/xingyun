package com.lframework.xingyun.produce.mappers;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.xingyun.produce.dto.bom.BomDetailDto;
import com.lframework.xingyun.produce.entity.BomDetail;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * BOM明细 Mapper
 *
 * @author zmj
 */
public interface BomDetailMapper extends BaseMapper<BomDetail> {

  /**
   * 根据BOM ID查询明细（关联商品信息）
   */
  List<BomDetailDto> listByBomId(@Param("bomId") String bomId);
}
