package com.lframework.xingyun.basedata.impl.product;

import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.xingyun.basedata.entity.ProductSkuSale;
import com.lframework.xingyun.basedata.mappers.ProductSkuSaleMapper;
import com.lframework.xingyun.basedata.service.product.ProductSkuSaleService;
import org.springframework.stereotype.Service;

@Service
public class ProductSkuSaleServiceImpl
    extends BaseMpServiceImpl<ProductSkuSaleMapper, ProductSkuSale>
    implements ProductSkuSaleService {

}
