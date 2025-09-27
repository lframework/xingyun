package com.lframework.xingyun.sc.impl.sale;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.enums.ProductType;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.sc.entity.SaleOutSheetDetail;
import com.lframework.xingyun.sc.entity.SaleOutSheetDetailBundle;
import com.lframework.xingyun.sc.mappers.SaleOutSheetDetailMapper;
import com.lframework.xingyun.sc.service.sale.SaleOutSheetDetailBundleService;
import com.lframework.xingyun.sc.service.sale.SaleOutSheetDetailService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleOutSheetDetailServiceImpl extends
    BaseMpServiceImpl<SaleOutSheetDetailMapper, SaleOutSheetDetail>
    implements SaleOutSheetDetailService {

  @Autowired
  private ProductService productService;

  @Autowired
  private SaleOutSheetDetailBundleService saleOutSheetDetailBundleService;

  @Override
  public List<SaleOutSheetDetail> getBySheetId(String sheetId) {

    return getBaseMapper().getBySheetId(sheetId);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void addReturnNum(String id, BigDecimal num) {

    Assert.notBlank(id);
    Assert.greaterThanZero(num);

    SaleOutSheetDetail detail = getBaseMapper().selectById(id);

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

    SaleOutSheetDetail orderDetail = getBaseMapper().selectById(id);

    if (NumberUtil.lt(orderDetail.getReturnNum(), num)) {
      Product product = productService.findById(orderDetail.getProductId());

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "已退货数量为"
              + orderDetail.getReturnNum()
              + "个，本次取消退货数量不允许大于" + orderDetail.getReturnNum() + "个！");
    }

    if (getBaseMapper().subReturnNum(orderDetail.getId(), num) != 1) {
      Product product = productService.findById(orderDetail.getProductId());

      throw new DefaultClientException(
          "（" + product.getCode() + "）" + product.getName() + "已退货数量不足，不允许取消退货！");
    }
  }

  @Override
  public BigDecimal getTotalWeightBySheetIds(List<String> sheetIds) {
    if (CollectionUtil.isEmpty(sheetIds)) {
      return BigDecimal.ZERO;
    }

    Wrapper<SaleOutSheetDetail> queryWrapper = Wrappers.lambdaQuery(SaleOutSheetDetail.class)
        .in(SaleOutSheetDetail::getSheetId, sheetIds);
    List<SaleOutSheetDetail> details = this.list(queryWrapper);
    BigDecimal sumWeight = details.stream().map(t -> {
      Product product = productService.findById(t.getProductId());
      if (product.getProductType() == ProductType.BUNDLE) {
        Wrapper<SaleOutSheetDetailBundle> detailBundleWrapper = Wrappers.lambdaQuery(
            SaleOutSheetDetailBundle.class).eq(SaleOutSheetDetailBundle::getDetailId, t.getId());
        List<SaleOutSheetDetailBundle> detailBundles = saleOutSheetDetailBundleService.list(
            detailBundleWrapper);
        return detailBundles.stream().map(b -> {
          Product targetProduct = productService.findById(b.getProductId());
          if (targetProduct.getWeight() == null) {
            throw new DefaultClientException(
                "商品（" + targetProduct.getCode() + "）" + targetProduct.getName()
                    + "尚未设置重量，请检查！");
          }

          return NumberUtil.mul(targetProduct.getWeight(), b.getProductOrderNum());
        }).reduce(NumberUtil::add).orElse(BigDecimal.ZERO);
      } else {
        if (product.getWeight() == null) {
          throw new DefaultClientException(
              "商品（" + product.getCode() + "）" + product.getName() + "尚未设置重量，请检查！");
        }
        return NumberUtil.mul(t.getOrderNum(), product.getWeight());
      }
    }).reduce(NumberUtil::add).orElse(BigDecimal.ZERO);

    return sumWeight;
  }

  @Override
  public BigDecimal getTotalVolumeBySheetIds(List<String> sheetIds) {
    if (CollectionUtil.isEmpty(sheetIds)) {
      return BigDecimal.ZERO;
    }

    Wrapper<SaleOutSheetDetail> queryWrapper = Wrappers.lambdaQuery(SaleOutSheetDetail.class)
        .in(SaleOutSheetDetail::getSheetId, sheetIds);
    List<SaleOutSheetDetail> details = this.list(queryWrapper);
    BigDecimal sumVolume = details.stream().map(t -> {
      Product product = productService.findById(t.getProductId());
      if (product.getProductType() == ProductType.BUNDLE) {
        Wrapper<SaleOutSheetDetailBundle> detailBundleWrapper = Wrappers.lambdaQuery(
            SaleOutSheetDetailBundle.class).eq(SaleOutSheetDetailBundle::getDetailId, t.getId());
        List<SaleOutSheetDetailBundle> detailBundles = saleOutSheetDetailBundleService.list(
            detailBundleWrapper);
        return detailBundles.stream().map(b -> {
          Product targetProduct = productService.findById(b.getProductId());
          if (targetProduct.getVolume() == null) {
            throw new DefaultClientException(
                "商品（" + targetProduct.getCode() + "）" + targetProduct.getName()
                    + "尚未设置体积，请检查！");
          }

          return NumberUtil.mul(targetProduct.getVolume(), b.getProductOrderNum());
        }).reduce(NumberUtil::add).orElse(BigDecimal.ZERO);
      } else {
        if (product.getVolume() == null) {
          throw new DefaultClientException(
              "商品（" + product.getCode() + "）" + product.getName() + "尚未设置体积，请检查！");
        }
        return NumberUtil.mul(t.getOrderNum(), product.getVolume());
      }
    }).reduce(NumberUtil::add).orElse(BigDecimal.ZERO);

    return sumVolume;
  }
}
