package com.lframework.xingyun.basedata.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.xingyun.basedata.entity.ProductBundle;
import com.lframework.xingyun.basedata.mappers.ProductBundleMapper;
import com.lframework.xingyun.basedata.service.product.ProductBundleService;
import java.io.Serializable;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ProductBundleServiceImpl extends BaseMpServiceImpl<ProductBundleMapper, ProductBundle>
    implements ProductBundleService {

  @Cacheable(value = ProductBundle.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null or #result.isEmpty()")
  @Override
  public List<ProductBundle> getByMainProductId(String id) {
    Wrapper<ProductBundle> queryWrapper = Wrappers.lambdaQuery(ProductBundle.class)
        .eq(ProductBundle::getMainProductId, id);
    return this.list(queryWrapper);
  }

  @CacheEvict(value = ProductBundle.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
