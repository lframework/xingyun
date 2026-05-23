package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.basedata.entity.ProductSkuSalePropertyRelation;
import java.util.List;

public interface ProductSkuSalePropertyRelationService extends BaseMpService<ProductSkuSalePropertyRelation> {

  List<ProductSkuSalePropertyRelation> getBySkuId(String skuId);

  void deleteBySkuId(String skuId);
}
