package com.lframework.xingyun.basedata.impl.product;

import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.xingyun.basedata.entity.ProductSkuBundle;
import com.lframework.xingyun.basedata.mappers.ProductSkuBundleMapper;
import com.lframework.xingyun.basedata.service.product.ProductSkuBundleService;
import org.springframework.stereotype.Service;

@Service
public class ProductSkuBundleServiceImpl
    extends BaseMpServiceImpl<ProductSkuBundleMapper, ProductSkuBundle>
    implements ProductSkuBundleService {

}
