package com.lframework.xingyun.sc.impl.purchase;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.sc.entity.ReceiveSheetDetail;
import com.lframework.xingyun.sc.mappers.ReceiveSheetDetailMapper;
import com.lframework.xingyun.sc.service.purchase.ReceiveSheetDetailService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReceiveSheetDetailServiceImpl extends
    BaseMpServiceImpl<ReceiveSheetDetailMapper, ReceiveSheetDetail>
    implements ReceiveSheetDetailService {

  @Autowired
  private ProductService productService;

  @Override
  public List<ReceiveSheetDetail> getBySheetId(String sheetId) {

    return getBaseMapper().getBySheetId(sheetId);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void addReturnNum(String id, BigDecimal num) {

    Assert.notBlank(id);
    Assert.greaterThanZero(num);

    ReceiveSheetDetail detail = getBaseMapper().selectById(id);

    BigDecimal remainNum = NumberUtil.sub(detail.getOrderNum(), detail.getReturnNum());
    if (NumberUtil.lt(remainNum, num)) {
      Product product = productService.findById(detail.getProductId());

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "剩余退货数量为" + remainNum
              + "个，本次退货数量不允许大于"
              + remainNum + "个！");
    }

    if (getBaseMapper().addReturnNum(detail.getId(), num) != 1) {
      Product product = productService.findById(detail.getProductId());

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "剩余退货数量不足，不允许继续退货！");
    }
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void subReturnNum(String id, BigDecimal num) {

    Assert.notBlank(id);
    Assert.greaterThanZero(num);

    ReceiveSheetDetail orderDetail = getBaseMapper().selectById(id);

    if (NumberUtil.lt(orderDetail.getReturnNum(), num)) {
      Product product = productService.findById(orderDetail.getProductId());

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "已退货数量为" + orderDetail.getReturnNum()
              + "个，本次取消退货数量不允许大于" + orderDetail.getReturnNum() + "个！");
    }

    if (getBaseMapper().subReturnNum(orderDetail.getId(), num) != 1) {
      Product product = productService.findById(orderDetail.getProductId());

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "已退货数量不足，不允许取消退货！");
    }
  }
}
