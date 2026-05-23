package com.lframework.xingyun.basedata.impl.product;

import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.xingyun.basedata.entity.ProductSkuPurchase;
import com.lframework.xingyun.basedata.mappers.ProductSkuPurchaseMapper;
import com.lframework.xingyun.basedata.service.product.ProductSkuPurchaseService;
import org.springframework.stereotype.Service;

@Service
public class ProductSkuPurchaseServiceImpl
    extends BaseMpServiceImpl<ProductSkuPurchaseMapper, ProductSkuPurchase>
    implements ProductSkuPurchaseService {

}
