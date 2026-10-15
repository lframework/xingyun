package com.lframework.xingyun.produce.impl.order;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.xingyun.produce.entity.ProductionOrderDetail;
import com.lframework.xingyun.produce.mappers.ProductionOrderDetailMapper;
import com.lframework.xingyun.produce.service.order.ProductionOrderDetailService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductionOrderDetailServiceImpl
    extends BaseMpServiceImpl<ProductionOrderDetailMapper, ProductionOrderDetail>
    implements ProductionOrderDetailService {

  @Override
  public List<ProductionOrderDetail> listByOrderId(String orderId) {
    return getBaseMapper().listByOrderId(orderId);
  }

  @Transactional
  @Override
  public void deleteByOrderId(String orderId) {
    LambdaQueryWrapper<ProductionOrderDetail> wrapper = Wrappers.<ProductionOrderDetail>lambdaQuery()
        .eq(ProductionOrderDetail::getOrderId, orderId);
    remove(wrapper);
  }
}
