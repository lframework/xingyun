package com.lframework.xingyun.produce.mappers;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.xingyun.produce.entity.ProductionOrder;
import com.lframework.xingyun.produce.vo.order.QueryProductionOrderVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductionOrderMapper extends BaseMapper<ProductionOrder> {

  List<ProductionOrder> query(@Param("vo") QueryProductionOrderVo vo);
}
