package com.lframework.xingyun.core.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.xingyun.core.entity.OrderTimeLine;
import com.lframework.xingyun.core.mappers.OrderTimeLineMapper;
import com.lframework.xingyun.core.service.OrderTimeLineService;
import java.io.Serializable;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zmj
 * @since 2022/8/10
 */
@Service
public class OrderTimeLineServiceImpl extends BaseMpServiceImpl<OrderTimeLineMapper, OrderTimeLine> implements
    OrderTimeLineService {

  @Cacheable(value = OrderTimeLine.CACHE_NAME, key = "@cacheVariables.tenantId() + #orderId", unless = "#result == null")
  @Override
  public List<OrderTimeLine> getByOrder(String orderId) {
    Wrapper<OrderTimeLine> queryWrapper = Wrappers.lambdaQuery(OrderTimeLine.class).eq(OrderTimeLine::getOrderId, orderId).orderByAsc(OrderTimeLine::getCreateTime);

    return this.list(queryWrapper);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteByOrder(String orderId) {
    Wrapper<OrderTimeLine> deleteWrapper = Wrappers.lambdaQuery(OrderTimeLine.class).eq(OrderTimeLine::getOrderId, orderId);
    this.remove(deleteWrapper);
  }

  @CacheEvict(value = OrderTimeLine.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {
    super.cleanCacheByKey(key);
  }
}
