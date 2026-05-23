package com.lframework.xingyun.basedata.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.event.DataChangeEventBuilder;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.PageHelperUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.xingyun.basedata.dto.product.ProductSkuSelectorDto;
import com.lframework.xingyun.basedata.entity.ProductSku;
import com.lframework.xingyun.basedata.events.DeleteProductSkuEvent;
import com.lframework.xingyun.basedata.mappers.ProductSkuMapper;
import com.lframework.xingyun.basedata.service.product.ProductSkuCodeService;
import com.lframework.xingyun.basedata.service.product.ProductSkuService;
import com.lframework.xingyun.basedata.vo.product.info.QueryProductSelectorVo;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductSkuServiceImpl extends BaseMpServiceImpl<ProductSkuMapper, ProductSku>
    implements ProductSkuService {

  @Autowired
  private ProductSkuCodeService productSkuCodeService;

  @Cacheable(value = ProductSku.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public ProductSku findById(String id) {

    return getBaseMapper().selectById(id);
  }

  @Override
  public List<ProductSku> getAvailableByProductId(String productId) {

    return getBaseMapper().getAvailableByProductId(productId);
  }

  @Override
  public PageResult<ProductSkuSelectorDto> selector(Integer pageIndex, Integer pageSize,
      QueryProductSelectorVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<ProductSkuSelectorDto> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<ProductSkuSelectorDto> loadSelector(List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return CollectionUtil.emptyList();
    }

    List<String> filteredIds = ids.stream().filter(StringUtil::isNotBlank).distinct().toList();
    if (CollectionUtil.isEmpty(filteredIds)) {
      return CollectionUtil.emptyList();
    }

    return getBaseMapper().loadSelector(filteredIds);
  }

  @Override
  public ProductSku findAvailableByCode(String code) {

    if (StringUtil.isBlank(code)) {
      return null;
    }

    return getBaseMapper().findAvailableByCode(code);
  }

  @Override
  public List<ProductSku> getAvailableByCode(String code) {

    if (StringUtil.isBlank(code)) {
      return CollectionUtil.emptyList();
    }

    return getBaseMapper().getAvailableByCode(code);
  }

  @CacheEvict(value = ProductSku.CACHE_NAME, allEntries = true)
  @Override
  public void refreshSalePropertyTextByPropertyId(String propertyId) {

    if (StringUtil.isBlank(propertyId)) {
      return;
    }

    getBaseMapper().refreshSalePropertyTextByPropertyId(propertyId);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {
    Wrapper<ProductSku> updateWrapper = Wrappers.lambdaUpdate(ProductSku.class)
        .set(ProductSku::getAvailable, Boolean.FALSE)
        .eq(ProductSku::getId, id);
    this.update(updateWrapper);

    ProductSku sku = this.findById(id);

    DataChangeEventBuilder.publishLogicDelete(this, DeleteProductSkuEvent.class, sku);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteByProductId(String productId) {

    List<ProductSku> skuList = this.list(Wrappers.lambdaQuery(ProductSku.class)
        .eq(ProductSku::getProductId, productId)
        .eq(ProductSku::getAvailable, Boolean.TRUE)
    );

    for (ProductSku productSku : skuList) {
      this.deleteById(productSku.getId());

      DataChangeEventBuilder.publishLogicDelete(this, DeleteProductSkuEvent.class, productSku);
    }
  }

  @CacheEvict(value = ProductSku.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
