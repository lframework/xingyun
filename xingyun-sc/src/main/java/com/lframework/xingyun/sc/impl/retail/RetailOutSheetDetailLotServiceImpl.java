package com.lframework.xingyun.sc.impl.retail;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.NumberUtil;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.basedata.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.sc.dto.retail.out.RetailOutSheetDetailDto;
import com.lframework.xingyun.sc.dto.retail.out.RetailOutSheetDetailLotDto;
import com.lframework.xingyun.sc.entity.RetailOutSheetDetailLot;
import com.lframework.xingyun.sc.mappers.RetailOutSheetDetailLotMapper;
import com.lframework.xingyun.sc.service.retail.IRetailOutSheetDetailLotService;
import com.lframework.xingyun.sc.service.retail.IRetailOutSheetDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RetailOutSheetDetailLotServiceImpl extends
    BaseMpServiceImpl<RetailOutSheetDetailLotMapper, RetailOutSheetDetailLot> implements
    IRetailOutSheetDetailLotService {

  @Autowired
  private IProductService productService;

  @Autowired
  private IRetailOutSheetDetailService retailOutSheetDetailService;

  @Override
  public RetailOutSheetDetailLotDto getById(String id) {

    return getBaseMapper().getById(id);
  }

  @Transactional
  @Override
  public void addReturnNum(String id, Integer num) {

    Assert.notBlank(id);
    Assert.greaterThanZero(num);

    RetailOutSheetDetailLot detail = getBaseMapper().selectById(id);

    Integer remainNum = NumberUtil.sub(detail.getOrderNum(), detail.getReturnNum()).intValue();
    if (NumberUtil.lt(remainNum, num)) {
      RetailOutSheetDetailDto sheetDetail = retailOutSheetDetailService
          .getById(detail.getDetailId());

      ProductDto product = productService.getById(sheetDetail.getProductId());

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "剩余退货数量为" + remainNum
              + "个，本次退货数量不允许大于"
              + remainNum + "个！");
    }

    if (getBaseMapper().addReturnNum(detail.getId(), num) != 1) {
      RetailOutSheetDetailDto sheetDetail = retailOutSheetDetailService
          .getById(detail.getDetailId());

      ProductDto product = productService.getById(sheetDetail.getProductId());

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "剩余退货数量不足，不允许继续退货！");
    }

    retailOutSheetDetailService.addReturnNum(detail.getDetailId(), num);
  }

  @Transactional
  @Override
  public void subReturnNum(String id, Integer num) {

    Assert.notBlank(id);
    Assert.greaterThanZero(num);

    RetailOutSheetDetailLot detail = getBaseMapper().selectById(id);

    if (NumberUtil.lt(detail.getReturnNum(), num)) {
      RetailOutSheetDetailDto sheetDetail = retailOutSheetDetailService
          .getById(detail.getDetailId());

      ProductDto product = productService.getById(sheetDetail.getProductId());

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "已退货数量为" + detail.getReturnNum()
              + "个，本次取消退货数量不允许大于" + detail.getReturnNum() + "个！");
    }

    if (getBaseMapper().subReturnNum(detail.getId(), num) != 1) {
      RetailOutSheetDetailDto sheetDetail = retailOutSheetDetailService
          .getById(detail.getDetailId());

      ProductDto product = productService.getById(sheetDetail.getProductId());

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "已退货数量不足，不允许取消退货！");
    }

    retailOutSheetDetailService.subReturnNum(detail.getDetailId(), num);
  }
}
