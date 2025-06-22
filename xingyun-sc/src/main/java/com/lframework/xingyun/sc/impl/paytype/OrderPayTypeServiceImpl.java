package com.lframework.xingyun.sc.impl.paytype;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.xingyun.sc.entity.OrderPayType;
import com.lframework.xingyun.sc.mappers.OrderPayTypeMapper;
import com.lframework.xingyun.sc.service.paytype.OrderPayTypeService;
import com.lframework.xingyun.sc.vo.paytype.OrderPayTypeVo;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderPayTypeServiceImpl extends
    BaseMpServiceImpl<OrderPayTypeMapper, OrderPayType> implements OrderPayTypeService {

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void create(String orderId, List<OrderPayTypeVo> data) {
    Wrapper<OrderPayType> deleteOrderPayTypeWrapper = Wrappers.lambdaQuery(OrderPayType.class)
        .eq(OrderPayType::getOrderId, orderId);
    this.remove(deleteOrderPayTypeWrapper);
    if (CollectionUtil.isNotEmpty(data)) {
      List<OrderPayType> records = data.stream().map(payTypeVo -> {
        OrderPayType orderPayType = new OrderPayType();
        orderPayType.setId(IdUtil.getId());
        orderPayType.setOrderId(orderId);
        orderPayType.setPayTypeId(payTypeVo.getId());
        orderPayType.setPayAmount(payTypeVo.getPayAmount());
        if (StringUtil.isNotBlank(payTypeVo.getText())) {
          orderPayType.setText(payTypeVo.getText());
        }

        return orderPayType;
      }).collect(Collectors.toList());
      this.saveBatch(records);
    }
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteByOrderId(String orderId) {
    Wrapper<OrderPayType> deleteOrderPayTypeWrapper = Wrappers.lambdaQuery(OrderPayType.class)
        .eq(OrderPayType::getOrderId, orderId);
    this.remove(deleteOrderPayTypeWrapper);
  }

  @Override
  public List<OrderPayType> findByOrderId(String orderId) {
    Wrapper<OrderPayType> queryWrapper = Wrappers.lambdaQuery(OrderPayType.class)
        .eq(OrderPayType::getOrderId, orderId);
    return this.list(queryWrapper);
  }
}
