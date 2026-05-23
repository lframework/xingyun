package com.lframework.xingyun.basedata.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.xingyun.basedata.entity.ProductSkuSalePropertyRelation;
import com.lframework.xingyun.basedata.mappers.ProductSkuSalePropertyRelationMapper;
import com.lframework.xingyun.basedata.service.product.ProductSkuSalePropertyRelationService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductSkuSalePropertyRelationServiceImpl
    extends BaseMpServiceImpl<ProductSkuSalePropertyRelationMapper, ProductSkuSalePropertyRelation>
    implements ProductSkuSalePropertyRelationService {

  @Override
  public List<ProductSkuSalePropertyRelation> getBySkuId(String skuId) {

    return this.list(Wrappers.lambdaQuery(ProductSkuSalePropertyRelation.class)
        .eq(ProductSkuSalePropertyRelation::getSkuId, skuId));
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteBySkuId(String skuId) {

    Wrapper<ProductSkuSalePropertyRelation> deleteWrapper = Wrappers.lambdaQuery(
        ProductSkuSalePropertyRelation.class).eq(ProductSkuSalePropertyRelation::getSkuId, skuId);
    this.remove(deleteWrapper);
  }
}
