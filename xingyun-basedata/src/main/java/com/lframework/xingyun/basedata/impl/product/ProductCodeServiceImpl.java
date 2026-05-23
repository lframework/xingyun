package com.lframework.xingyun.basedata.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.xingyun.basedata.entity.ProductCode;
import com.lframework.xingyun.basedata.mappers.ProductCodeMapper;
import com.lframework.xingyun.basedata.service.product.ProductCodeService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ProductCodeServiceImpl extends
    BaseMpServiceImpl<ProductCodeMapper, ProductCode> implements ProductCodeService {

  @Cacheable(value = ProductCode.CACHE_NAME, key = "@cacheVariables.tenantId() + #productId", unless = "#result == null")
  @Override
  public List<ProductCode> findByProductId(String productId) {
    Wrapper<ProductCode> queryWrapper = Wrappers.lambdaQuery(ProductCode.class)
        .inSql(ProductCode::getProductId,
            "SELECT id FROM base_data_product_sku WHERE product_id = '" + productId + "'");
    return getBaseMapper().selectList(queryWrapper);
  }

  @Override
  public List<String> checkMultiCodes(List<String> codes, String productId) {
    if (CollectionUtil.isEmpty(codes)) {
      return Collections.emptyList();
    }

    // 先判断codes是否存在重复项，如果存在则返回重复的元素
    if (codes.size() != CollectionUtil.distinct(codes).size()) {
      return new ArrayList<>(codes.stream().collect(Collectors.groupingBy(code -> code)).keySet());
    }
    return getBaseMapper().checkMultiCodes(codes, productId);
  }

  @CacheEvict(value = ProductCode.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
