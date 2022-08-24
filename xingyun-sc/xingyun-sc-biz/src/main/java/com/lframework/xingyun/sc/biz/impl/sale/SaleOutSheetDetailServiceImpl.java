package com.lframework.xingyun.sc.biz.impl.sale;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.NumberUtil;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.basedata.facade.ProductFeignClient;
import com.lframework.xingyun.basedata.facade.dto.product.info.ProductDto;
import com.lframework.xingyun.sc.biz.mappers.SaleOutSheetDetailMapper;
import com.lframework.xingyun.sc.biz.service.sale.ISaleOutSheetDetailService;
import com.lframework.xingyun.sc.facade.entity.SaleOutSheetDetail;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleOutSheetDetailServiceImpl extends
    BaseMpServiceImpl<SaleOutSheetDetailMapper, SaleOutSheetDetail>
    implements ISaleOutSheetDetailService {

  @Autowired
  private ProductFeignClient productFeignClient;

  @Override
  public List<SaleOutSheetDetail> getBySheetId(String sheetId) {

    return getBaseMapper().getBySheetId(sheetId);
  }

  @Transactional
  @Override
  public void addReturnNum(String id, Integer num) {

    Assert.notBlank(id);
    Assert.greaterThanZero(num);

    SaleOutSheetDetail detail = getBaseMapper().selectById(id);

    Integer remainNum = NumberUtil.sub(detail.getOrderNum(), detail.getReturnNum()).intValue();
    if (NumberUtil.lt(remainNum, num)) {
      ProductDto product = productFeignClient.findById(detail.getProductId()).getData();

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "剩余退货数量为" + remainNum
              + "个，本次退货数量不允许大于"
              + remainNum + "个！");
    }

    if (getBaseMapper().addReturnNum(detail.getId(), num) != 1) {
      ProductDto product = productFeignClient.findById(detail.getProductId()).getData();

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "剩余退货数量不足，不允许继续退货！");
    }
  }

  @Transactional
  @Override
  public void subReturnNum(String id, Integer num) {

    Assert.notBlank(id);
    Assert.greaterThanZero(num);

    SaleOutSheetDetail orderDetail = getBaseMapper().selectById(id);

    if (NumberUtil.lt(orderDetail.getReturnNum(), num)) {
      ProductDto product = productFeignClient.findById(orderDetail.getProductId()).getData();

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "已退货数量为" + orderDetail.getReturnNum()
              + "个，本次取消退货数量不允许大于" + orderDetail.getReturnNum() + "个！");
    }

    if (getBaseMapper().subReturnNum(orderDetail.getId(), num) != 1) {
      ProductDto product = productFeignClient.findById(orderDetail.getProductId()).getData();

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "已退货数量不足，不允许取消退货！");
    }
  }
}
