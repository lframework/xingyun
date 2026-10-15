package com.lframework.xingyun.produce.mappers;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.xingyun.produce.entity.ProductionOrderDetail;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductionOrderDetailMapper extends BaseMapper<ProductionOrderDetail> {

  List<ProductionOrderDetail> listByOrderId(@Param("orderId") String orderId);
}
