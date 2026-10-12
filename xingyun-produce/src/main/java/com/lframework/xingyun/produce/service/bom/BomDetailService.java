package com.lframework.xingyun.produce.service.bom;

import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.produce.dto.bom.BomDetailDto;
import com.lframework.xingyun.produce.entity.BomDetail;
import java.util.List;

public interface BomDetailService extends BaseMpService<BomDetail> {

  /**
   * 根据BOM ID查询明细（关联商品信息）
   */
  List<BomDetailDto> listByBomId(String bomId);

  /**
   * 根据BOM ID删除所有明细
   */
  void deleteByBomId(String bomId);
}
