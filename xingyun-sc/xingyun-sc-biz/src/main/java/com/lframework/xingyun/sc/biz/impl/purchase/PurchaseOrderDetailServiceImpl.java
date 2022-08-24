package com.lframework.xingyun.sc.biz.impl.purchase;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.NumberUtil;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.basedata.facade.ProductFeignClient;
import com.lframework.xingyun.basedata.facade.dto.product.info.ProductDto;
import com.lframework.xingyun.sc.biz.mappers.PurchaseOrderDetailMapper;
import com.lframework.xingyun.sc.biz.service.purchase.IPurchaseOrderDetailService;
import com.lframework.xingyun.sc.facade.entity.PurchaseOrderDetail;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseOrderDetailServiceImpl extends
    BaseMpServiceImpl<PurchaseOrderDetailMapper, PurchaseOrderDetail>
    implements IPurchaseOrderDetailService {

  @Autowired
  private ProductFeignClient productFeignClient;

  @Override
  public List<PurchaseOrderDetail> getByOrderId(String orderId) {

    return getBaseMapper().getByOrderId(orderId);
  }

  @Transactional
  @Override
  public void addReceiveNum(String id, Integer num) {

    Assert.notBlank(id);
    Assert.greaterThanZero(num);

    PurchaseOrderDetail orderDetail = getBaseMapper().selectById(id);

    Integer remainNum = NumberUtil.sub(orderDetail.getOrderNum(), orderDetail.getReceiveNum())
        .intValue();
    if (NumberUtil.lt(remainNum, num)) {
      ProductDto product = productFeignClient.findById(orderDetail.getProductId()).getData();

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "剩余收货数量为" + remainNum
              + "个，本次收货数量不允许大于"
              + remainNum + "个！");
    }

    if (getBaseMapper().addReceiveNum(orderDetail.getId(), num) != 1) {
      ProductDto product = productFeignClient.findById(orderDetail.getProductId()).getData();

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "剩余收货数量不足，不允许继续收货！");
    }
  }

  @Transactional
  @Override
  public void subReceiveNum(String id, Integer num) {

    Assert.notBlank(id);
    Assert.greaterThanZero(num);

    PurchaseOrderDetail orderDetail = getBaseMapper().selectById(id);

    if (NumberUtil.lt(orderDetail.getReceiveNum(), num)) {
      ProductDto product = productFeignClient.findById(orderDetail.getProductId()).getData();

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "已收货数量为" + orderDetail.getReceiveNum()
              + "个，本次取消收货数量不允许大于" + orderDetail.getReceiveNum() + "个！");
    }

    if (getBaseMapper().subReceiveNum(orderDetail.getId(), num) != 1) {
      ProductDto product = productFeignClient.findById(orderDetail.getProductId()).getData();

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "已收货数量不足，不允许取消收货！");
    }
  }
}
