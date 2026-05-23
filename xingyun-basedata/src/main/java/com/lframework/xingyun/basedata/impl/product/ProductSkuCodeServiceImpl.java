package com.lframework.xingyun.basedata.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.xingyun.basedata.entity.ProductSku;
import com.lframework.xingyun.basedata.entity.ProductSkuCode;
import com.lframework.xingyun.basedata.enums.ProductSkuCodeType;
import com.lframework.xingyun.basedata.mappers.ProductSkuCodeMapper;
import com.lframework.xingyun.basedata.service.product.ProductSkuCodeService;
import com.lframework.xingyun.basedata.service.product.ProductSkuService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductSkuCodeServiceImpl
    extends BaseMpServiceImpl<ProductSkuCodeMapper, ProductSkuCode>
    implements ProductSkuCodeService {

  @Autowired
  private ProductSkuService productSkuService;

  @Override
  public List<String> checkCodes(List<String> codes, String productId, String skuId) {

    if (CollectionUtil.isEmpty(codes)) {
      return Collections.emptyList();
    }

    List<String> filteredCodes = codes.stream().filter(StringUtil::isNotBlank).toList();
    if (CollectionUtil.isEmpty(filteredCodes)) {
      return Collections.emptyList();
    }

    if (filteredCodes.size() != CollectionUtil.distinct(filteredCodes).size()) {
      return new ArrayList<>(
          filteredCodes.stream().collect(Collectors.groupingBy(code -> code, Collectors.counting()))
              .entrySet().stream().filter(t -> t.getValue() > 1).map(t -> t.getKey()).toList());
    }

    return getBaseMapper().checkCodes(filteredCodes, productId, skuId);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void rebuildSkuCodes(ProductSku sku, String productCode, List<String> extraCodes) {

    this.removeBySkuId(sku.getId());

    List<ProductSkuCode> codes = new ArrayList<>();
    codes.add(this.createCode(sku.getId(), sku.getCode(), ProductSkuCodeType.SKU_CODE, true));

    if (CollectionUtil.isNotEmpty(extraCodes)) {
      List<String> filteredCodes = extraCodes.stream().filter(StringUtil::isNotBlank)
          .filter(t -> !StringUtil.equals(t, sku.getCode()))
          .filter(t -> !StringUtil.equals(t, productCode))
          .distinct().toList();
      for (String extraCode : filteredCodes) {
        codes.add(this.createCode(sku.getId(), extraCode, ProductSkuCodeType.EXTRA_CODE, false));
      }
    }

    this.saveBatch(codes);
  }

  @Override
  public List<String> getExtraCodes(String skuId) {

    return this.list(Wrappers.lambdaQuery(ProductSkuCode.class)
            .select(ProductSkuCode::getCode)
            .eq(ProductSkuCode::getSkuId, skuId)
            .eq(ProductSkuCode::getCodeType, ProductSkuCodeType.EXTRA_CODE)).stream()
        .map(ProductSkuCode::getCode).collect(Collectors.toList());
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void rebuildProductCode(String productId, String productCode) {

    List<ProductSku> skus = productSkuService.list(Wrappers.lambdaQuery(ProductSku.class)
        .select(ProductSku::getId)
        .eq(ProductSku::getProductId, productId));
    for (ProductSku sku : skus) {
      Wrapper<ProductSkuCode> deleteWrapper = Wrappers.lambdaQuery(ProductSkuCode.class)
          .eq(ProductSkuCode::getSkuId, sku.getId())
          .eq(ProductSkuCode::getCodeType, ProductSkuCodeType.PRODUCT_CODE);
      this.remove(deleteWrapper);
    }
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void removeByProductId(String productId) {

    List<String> skuIds = productSkuService.list(Wrappers.lambdaQuery(ProductSku.class)
            .select(ProductSku::getId)
            .eq(ProductSku::getProductId, productId)).stream()
        .map(ProductSku::getId).collect(Collectors.toList());
    if (CollectionUtil.isEmpty(skuIds)) {
      return;
    }

    Wrapper<ProductSkuCode> deleteWrapper = Wrappers.lambdaQuery(ProductSkuCode.class)
        .in(ProductSkuCode::getSkuId, skuIds);
    this.remove(deleteWrapper);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void removeBySkuId(String skuId) {

    Wrapper<ProductSkuCode> deleteWrapper = Wrappers.lambdaQuery(ProductSkuCode.class)
        .eq(ProductSkuCode::getSkuId, skuId);
    this.remove(deleteWrapper);
  }

  private ProductSkuCode createCode(String skuId, String code, ProductSkuCodeType codeType,
      Boolean isMain) {

    ProductSkuCode skuCode = new ProductSkuCode();
    skuCode.setId(IdUtil.getId());
    skuCode.setSkuId(skuId);
    skuCode.setCode(code);
    skuCode.setCodeType(codeType);
    skuCode.setIsMain(isMain);

    return skuCode;
  }

  @CacheEvict(value = ProductSkuCode.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
