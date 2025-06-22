package com.lframework.xingyun.sc.impl.stock.transfer;

import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.xingyun.sc.entity.ScTransferOrderDetail;
import com.lframework.xingyun.sc.mappers.ScTransferOrderDetailMapper;
import com.lframework.xingyun.sc.service.stock.transfer.ScTransferOrderDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScTransferOrderDetailServiceImpl
    extends BaseMpServiceImpl<ScTransferOrderDetailMapper, ScTransferOrderDetail>
    implements ScTransferOrderDetailService {

  @Transactional(rollbackFor = Exception.class)
  @Override
  public int receive(String orderId, String productId, Integer receiveNum) {
    return getBaseMapper().receive(orderId, productId, receiveNum);
  }

  @Override
  public int countUnReceive(String orderId) {
    return getBaseMapper().countUnReceive(orderId);
  }
}
