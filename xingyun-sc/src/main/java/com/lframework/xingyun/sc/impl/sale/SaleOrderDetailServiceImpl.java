package com.lframework.xingyun.sc.impl.sale;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.sc.entity.SaleOrderDetail;
import com.lframework.xingyun.sc.mappers.SaleOrderDetailMapper;
import com.lframework.xingyun.sc.service.sale.SaleOrderDetailService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleOrderDetailServiceImpl extends
    BaseMpServiceImpl<SaleOrderDetailMapper, SaleOrderDetail>
    implements SaleOrderDetailService {

  @Autowired
  private ProductService productService;

  @Override
  public List<SaleOrderDetail> getByOrderId(String orderId) {

    return getBaseMapper().getByOrderId(orderId);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void addOutNum(String id, Integer num) {

    Assert.notBlank(id);
    Assert.greaterThanZero(num);

    SaleOrderDetail orderDetail = getBaseMapper().selectById(id);

    Integer remainNum = NumberUtil.sub(orderDetail.getOrderNum(), orderDetail.getOutNum())
        .intValue();
    if (NumberUtil.lt(remainNum, num)) {
      Product product = productService.findById(orderDetail.getProductId());

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "剩余出库数量为" + remainNum
              + "个，本次出库数量不允许大于"
              + remainNum + "个！");
    }

    if (getBaseMapper().addOutNum(orderDetail.getId(), num) != 1) {
      Product product = productService.findById(orderDetail.getProductId());

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "剩余出库数量不足，不允许继续出库！");
    }
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void subOutNum(String id, Integer num) {

    Assert.notBlank(id);
    Assert.greaterThanZero(num);

    SaleOrderDetail orderDetail = getBaseMapper().selectById(id);

    if (NumberUtil.lt(orderDetail.getOutNum(), num)) {
      Product product = productService.findById(orderDetail.getProductId());

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "已出库数量为" + orderDetail.getOutNum()
              + "个，本次取消出库数量不允许大于" + orderDetail.getOutNum() + "个！");
    }

    if (getBaseMapper().subOutNum(orderDetail.getId(), num) != 1) {
      Product product = productService.findById(orderDetail.getProductId());

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "已出库数量不足，不允许取消出库！");
    }
  }
}
