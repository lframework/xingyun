package com.lframework.xingyun.sc.impl.retail;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.NumberUtil;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.basedata.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.sc.dto.retail.out.RetailOutSheetDetailLotDto;
import com.lframework.xingyun.sc.entity.RetailOutSheetDetail;
import com.lframework.xingyun.sc.entity.RetailOutSheetDetailLot;
import com.lframework.xingyun.sc.mappers.RetailOutSheetDetailLotMapper;
import com.lframework.xingyun.sc.service.retail.IRetailOutSheetDetailLotService;
import com.lframework.xingyun.sc.service.retail.IRetailOutSheetDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RetailOutSheetDetailLotServiceImpl
    extends BaseMpServiceImpl<RetailOutSheetDetailLotMapper, RetailOutSheetDetailLot>
    implements IRetailOutSheetDetailLotService {

  @Autowired
  private IProductService productService;

  @Autowired
  private IRetailOutSheetDetailService retailOutSheetDetailService;

  @Override
  public RetailOutSheetDetailLotDto findById(String id) {

    return getBaseMapper().findById(id);
  }

  @Transactional
  @Override
  public void addReturnNum(String id, Integer num) {

    Assert.notBlank(id);
    Assert.greaterThanZero(num);

    RetailOutSheetDetailLot detail = getBaseMapper().selectById(id);

    Integer remainNum = NumberUtil.sub(detail.getOrderNum(), detail.getReturnNum()).intValue();
    if (NumberUtil.lt(remainNum, num)) {
      RetailOutSheetDetail sheetDetail = retailOutSheetDetailService.getById(detail.getDetailId());

      ProductDto product = productService.findById(sheetDetail.getProductId());

      throw new DefaultClientException(
          "???" + product.getCode() + "???" + product.getName() + "?????????????????????" + remainNum
              + "???????????????????????????????????????"
              + remainNum + "??????");
    }

    if (getBaseMapper().addReturnNum(detail.getId(), num) != 1) {
      RetailOutSheetDetail sheetDetail = retailOutSheetDetailService.getById(detail.getDetailId());

      ProductDto product = productService.findById(sheetDetail.getProductId());

      throw new DefaultClientException(
          "???" + product.getCode() + "???" + product.getName() + "???????????????????????????????????????????????????");
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
      RetailOutSheetDetail sheetDetail = retailOutSheetDetailService.getById(detail.getDetailId());

      ProductDto product = productService.findById(sheetDetail.getProductId());

      throw new DefaultClientException(
          "???" + product.getCode() + "???" + product.getName() + "??????????????????" + detail.getReturnNum()
              + "?????????????????????????????????????????????" + detail.getReturnNum() + "??????");
    }

    if (getBaseMapper().subReturnNum(detail.getId(), num) != 1) {
      RetailOutSheetDetail sheetDetail = retailOutSheetDetailService.getById(detail.getDetailId());

      ProductDto product = productService.findById(sheetDetail.getProductId());

      throw new DefaultClientException(
          "???" + product.getCode() + "???" + product.getName() + "????????????????????????????????????????????????");
    }

    retailOutSheetDetailService.subReturnNum(detail.getDetailId(), num);
  }
}
