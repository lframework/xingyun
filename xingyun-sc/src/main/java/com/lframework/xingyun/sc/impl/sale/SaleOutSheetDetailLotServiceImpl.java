package com.lframework.xingyun.sc.impl.sale;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.sc.dto.sale.out.SaleOutSheetDetailLotDto;
import com.lframework.xingyun.sc.entity.SaleOutSheetDetail;
import com.lframework.xingyun.sc.entity.SaleOutSheetDetailLot;
import com.lframework.xingyun.sc.mappers.SaleOutSheetDetailLotMapper;
import com.lframework.xingyun.sc.service.sale.SaleOutSheetDetailLotService;
import com.lframework.xingyun.sc.service.sale.SaleOutSheetDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleOutSheetDetailLotServiceImpl
    extends BaseMpServiceImpl<SaleOutSheetDetailLotMapper, SaleOutSheetDetailLot>
    implements SaleOutSheetDetailLotService {

  @Autowired
  private ProductService productService;

  @Autowired
  private SaleOutSheetDetailService saleOutSheetDetailService;

  @Override
  public SaleOutSheetDetailLotDto findById(String id) {

    return getBaseMapper().findById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void addReturnNum(String id, Integer num) {

    Assert.notBlank(id);
    Assert.greaterThanZero(num);

    SaleOutSheetDetailLot detail = getBaseMapper().selectById(id);

    Integer remainNum = NumberUtil.sub(detail.getOrderNum(), detail.getReturnNum()).intValue();
    if (NumberUtil.lt(remainNum, num)) {
      SaleOutSheetDetail sheetDetail = saleOutSheetDetailService.getById(detail.getDetailId());

      Product product = productService.findById(sheetDetail.getProductId());

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "剩余退货数量为" + remainNum
              + "个，本次退货数量不允许大于"
              + remainNum + "个！");
    }

    if (getBaseMapper().addReturnNum(detail.getId(), num) != 1) {
      SaleOutSheetDetail sheetDetail = saleOutSheetDetailService.getById(detail.getDetailId());

      Product product = productService.findById(sheetDetail.getProductId());

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "剩余退货数量不足，不允许继续退货！");
    }

    saleOutSheetDetailService.addReturnNum(detail.getDetailId(), num);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void subReturnNum(String id, Integer num) {

    Assert.notBlank(id);
    Assert.greaterThanZero(num);

    SaleOutSheetDetailLot detail = getBaseMapper().selectById(id);

    if (NumberUtil.lt(detail.getReturnNum(), num)) {
      SaleOutSheetDetail sheetDetail = saleOutSheetDetailService.getById(detail.getDetailId());

      Product product = productService.findById(sheetDetail.getProductId());

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "已退货数量为" + detail.getReturnNum()
              + "个，本次取消退货数量不允许大于" + detail.getReturnNum() + "个！");
    }

    if (getBaseMapper().subReturnNum(detail.getId(), num) != 1) {
      SaleOutSheetDetail sheetDetail = saleOutSheetDetailService.getById(detail.getDetailId());

      Product product = productService.findById(sheetDetail.getProductId());

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "已退货数量不足，不允许取消退货！");
    }

    saleOutSheetDetailService.subReturnNum(detail.getDetailId(), num);
  }
}
