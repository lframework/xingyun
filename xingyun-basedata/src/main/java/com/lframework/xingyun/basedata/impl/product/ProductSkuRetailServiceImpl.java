package com.lframework.xingyun.basedata.impl.product;

import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.xingyun.basedata.entity.ProductSkuRetail;
import com.lframework.xingyun.basedata.mappers.ProductSkuRetailMapper;
import com.lframework.xingyun.basedata.service.product.ProductSkuRetailService;
import org.springframework.stereotype.Service;

@Service
public class ProductSkuRetailServiceImpl
    extends BaseMpServiceImpl<ProductSkuRetailMapper, ProductSkuRetail>
    implements ProductSkuRetailService {

}
