package com.lframework.xingyun.sc.impl.purchase;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.NumberUtil;
import com.lframework.xingyun.basedata.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.sc.dto.purchase.receive.ReceiveSheetDetailDto;
import com.lframework.xingyun.sc.entity.ReceiveSheetDetail;
import com.lframework.xingyun.sc.mappers.ReceiveSheetDetailMapper;
import com.lframework.xingyun.sc.service.purchase.IReceiveSheetDetailService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReceiveSheetDetailServiceImpl implements IReceiveSheetDetailService {

  @Autowired
  private ReceiveSheetDetailMapper receiveSheetDetailMapper;

  @Autowired
  private IProductService productService;

  @Override
  public ReceiveSheetDetailDto getById(String id) {

    return receiveSheetDetailMapper.getById(id);
  }

  @Override
  public List<ReceiveSheetDetailDto> getBySheetId(String sheetId) {

    return receiveSheetDetailMapper.getBySheetId(sheetId);
  }

  @Transactional
  @Override
  public void addReturnNum(String id, Integer num) {

    Assert.notBlank(id);
    Assert.greaterThanZero(num);

    ReceiveSheetDetail detail = receiveSheetDetailMapper.selectById(id);

    Integer remainNum = NumberUtil.sub(detail.getOrderNum(), detail.getReturnNum()).intValue();
    if (NumberUtil.lt(remainNum, num)) {
      ProductDto product = productService.getById(detail.getProductId());

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "剩余退货数量为" + remainNum
              + "个，本次退货数量不允许大于"
              + remainNum + "个！");
    }

    if (receiveSheetDetailMapper.addReturnNum(detail.getId(), num) != 1) {
      ProductDto product = productService.getById(detail.getProductId());

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "剩余退货数量不足，不允许继续退货！");
    }
  }

  @Transactional
  @Override
  public void subReturnNum(String id, Integer num) {

    Assert.notBlank(id);
    Assert.greaterThanZero(num);

    ReceiveSheetDetail orderDetail = receiveSheetDetailMapper.selectById(id);

    if (NumberUtil.lt(orderDetail.getReturnNum(), num)) {
      ProductDto product = productService.getById(orderDetail.getProductId());

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "已退货数量为" + orderDetail.getReturnNum()
              + "个，本次取消退货数量不允许大于" + orderDetail.getReturnNum() + "个！");
    }

    if (receiveSheetDetailMapper.subReturnNum(orderDetail.getId(), num) != 1) {
      ProductDto product = productService.getById(orderDetail.getProductId());

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "已退货数量不足，不允许取消退货！");
    }
  }
}
