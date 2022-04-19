package com.lframework.xingyun.sc.impl.sale;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.NumberUtil;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.basedata.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.sc.dto.sale.out.SaleOutSheetDetailDto;
import com.lframework.xingyun.sc.dto.sale.out.SaleOutSheetDetailLotDto;
import com.lframework.xingyun.sc.entity.SaleOutSheetDetailLot;
import com.lframework.xingyun.sc.mappers.SaleOutSheetDetailLotMapper;
import com.lframework.xingyun.sc.service.sale.ISaleOutSheetDetailLotService;
import com.lframework.xingyun.sc.service.sale.ISaleOutSheetDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleOutSheetDetailLotServiceImpl extends
    BaseMpServiceImpl<SaleOutSheetDetailLotMapper, SaleOutSheetDetailLot> implements
    ISaleOutSheetDetailLotService {

  @Autowired
  private IProductService productService;

  @Autowired
  private ISaleOutSheetDetailService saleOutSheetDetailService;

  @Override
  public SaleOutSheetDetailLotDto getById(String id) {

    return getBaseMapper().getById(id);
  }

  @Transactional
  @Override
  public void addReturnNum(String id, Integer num) {

    Assert.notBlank(id);
    Assert.greaterThanZero(num);

    SaleOutSheetDetailLot detail = getBaseMapper().selectById(id);

    Integer remainNum = NumberUtil.sub(detail.getOrderNum(), detail.getReturnNum()).intValue();
    if (NumberUtil.lt(remainNum, num)) {
      SaleOutSheetDetailDto sheetDetail = saleOutSheetDetailService.getById(detail.getDetailId());

      ProductDto product = productService.getById(sheetDetail.getProductId());

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "剩余退货数量为" + remainNum
              + "个，本次退货数量不允许大于"
              + remainNum + "个！");
    }

    if (getBaseMapper().addReturnNum(detail.getId(), num) != 1) {
      SaleOutSheetDetailDto sheetDetail = saleOutSheetDetailService.getById(detail.getDetailId());

      ProductDto product = productService.getById(sheetDetail.getProductId());

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "剩余退货数量不足，不允许继续退货！");
    }

    saleOutSheetDetailService.addReturnNum(detail.getDetailId(), num);
  }

  @Transactional
  @Override
  public void subReturnNum(String id, Integer num) {

    Assert.notBlank(id);
    Assert.greaterThanZero(num);

    SaleOutSheetDetailLot detail = getBaseMapper().selectById(id);

    if (NumberUtil.lt(detail.getReturnNum(), num)) {
      SaleOutSheetDetailDto sheetDetail = saleOutSheetDetailService.getById(detail.getDetailId());

      ProductDto product = productService.getById(sheetDetail.getProductId());

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "已退货数量为" + detail.getReturnNum()
              + "个，本次取消退货数量不允许大于" + detail.getReturnNum() + "个！");
    }

    if (getBaseMapper().subReturnNum(detail.getId(), num) != 1) {
      SaleOutSheetDetailDto sheetDetail = saleOutSheetDetailService.getById(detail.getDetailId());

      ProductDto product = productService.getById(sheetDetail.getProductId());

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "已退货数量不足，不允许取消退货！");
    }

    saleOutSheetDetailService.subReturnNum(detail.getDetailId(), num);
  }
}
