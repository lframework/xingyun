package com.lframework.xingyun.sc.biz.impl.sale;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.NumberUtil;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.basedata.facade.ProductFeignClient;
import com.lframework.xingyun.basedata.facade.dto.product.info.ProductDto;
import com.lframework.xingyun.sc.biz.mappers.SaleOrderDetailMapper;
import com.lframework.xingyun.sc.biz.service.sale.ISaleOrderDetailService;
import com.lframework.xingyun.sc.facade.entity.SaleOrderDetail;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleOrderDetailServiceImpl extends
    BaseMpServiceImpl<SaleOrderDetailMapper, SaleOrderDetail>
    implements ISaleOrderDetailService {

  @Autowired
  private ProductFeignClient productFeignClient;

  @Override
  public List<SaleOrderDetail> getByOrderId(String orderId) {

    return getBaseMapper().getByOrderId(orderId);
  }

  @Transactional
  @Override
  public void addOutNum(String id, Integer num) {

    Assert.notBlank(id);
    Assert.greaterThanZero(num);

    SaleOrderDetail orderDetail = getBaseMapper().selectById(id);

    Integer remainNum = NumberUtil.sub(orderDetail.getOrderNum(), orderDetail.getOutNum())
        .intValue();
    if (NumberUtil.lt(remainNum, num)) {
      ProductDto product = productFeignClient.findById(orderDetail.getProductId()).getData();

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "剩余出库数量为" + remainNum
              + "个，本次出库数量不允许大于"
              + remainNum + "个！");
    }

    if (getBaseMapper().addOutNum(orderDetail.getId(), num) != 1) {
      ProductDto product = productFeignClient.findById(orderDetail.getProductId()).getData();

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "剩余出库数量不足，不允许继续出库！");
    }
  }

  @Transactional
  @Override
  public void subOutNum(String id, Integer num) {

    Assert.notBlank(id);
    Assert.greaterThanZero(num);

    SaleOrderDetail orderDetail = getBaseMapper().selectById(id);

    if (NumberUtil.lt(orderDetail.getOutNum(), num)) {
      ProductDto product = productFeignClient.findById(orderDetail.getProductId()).getData();

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "已出库数量为" + orderDetail.getOutNum()
              + "个，本次取消出库数量不允许大于" + orderDetail.getOutNum() + "个！");
    }

    if (getBaseMapper().subOutNum(orderDetail.getId(), num) != 1) {
      ProductDto product = productFeignClient.findById(orderDetail.getProductId()).getData();

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "已出库数量不足，不允许取消出库！");
    }
  }
}
