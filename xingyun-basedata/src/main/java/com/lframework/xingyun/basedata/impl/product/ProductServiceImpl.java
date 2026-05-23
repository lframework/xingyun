package com.lframework.xingyun.basedata.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.annotations.oplog.OpLog;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.event.DataChangeEventBuilder;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.EnumUtil;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.starter.web.core.utils.JsonUtil;
import com.lframework.starter.web.core.utils.OpLogUtil;
import com.lframework.starter.web.core.utils.PageHelperUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.web.inner.service.RecursionMappingService;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.ProductBundle;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.dto.product.category.saleproperty.ProductCategorySalePropertyRelationDto;
import com.lframework.xingyun.basedata.entity.ProductCategoryPropertyDefinition;
import com.lframework.xingyun.basedata.entity.ProductCategoryPropertyItem;
import com.lframework.xingyun.basedata.entity.ProductSalePropertyDefinition;
import com.lframework.xingyun.basedata.entity.ProductSalePropertyItem;
import com.lframework.xingyun.basedata.entity.ProductSku;
import com.lframework.xingyun.basedata.entity.ProductSkuCode;
import com.lframework.xingyun.basedata.entity.ProductSkuSalePropertyRelation;
import com.lframework.xingyun.basedata.enums.BaseDataOpLogType;
import com.lframework.xingyun.basedata.enums.ColumnType;
import com.lframework.xingyun.basedata.enums.ProductCategoryNodeType;
import com.lframework.xingyun.basedata.enums.ProductSkuType;
import com.lframework.xingyun.basedata.enums.ProductType;
import com.lframework.xingyun.basedata.events.DeleteProductEvent;
import com.lframework.xingyun.basedata.mappers.ProductMapper;
import com.lframework.xingyun.basedata.service.product.ProductBundleService;
import com.lframework.xingyun.basedata.service.product.ProductCategorySalePropertyRelationService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryPropertyItemService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryPropertyValueRelationService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryPropertyDefinitionService;
import com.lframework.xingyun.basedata.service.product.ProductPurchaseService;
import com.lframework.xingyun.basedata.service.product.ProductRetailService;
import com.lframework.xingyun.basedata.service.product.ProductSaleService;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.basedata.service.product.ProductSkuCodeService;
import com.lframework.xingyun.basedata.service.product.ProductSkuSalePropertyRelationService;
import com.lframework.xingyun.basedata.service.product.ProductSkuService;
import com.lframework.xingyun.basedata.service.product.ProductSalePropertyDefinitionService;
import com.lframework.xingyun.basedata.service.product.ProductSalePropertyItemService;
import com.lframework.xingyun.basedata.vo.product.info.CreateProductVo;
import com.lframework.xingyun.basedata.vo.product.info.ProductSkuVo;
import com.lframework.xingyun.basedata.vo.product.info.ProductCategoryPropertyValueRelationVo;
import com.lframework.xingyun.basedata.vo.product.info.QueryProductSelectorVo;
import com.lframework.xingyun.basedata.vo.product.info.QueryProductVo;
import com.lframework.xingyun.basedata.vo.product.info.UpdateProductVo;
import com.lframework.xingyun.basedata.vo.product.property.relation.CreateProductCategoryPropertyValueRelationVo;
import com.lframework.xingyun.basedata.vo.product.purchase.CreateProductPurchaseVo;
import com.lframework.xingyun.basedata.vo.product.retail.CreateProductRetailVo;
import com.lframework.xingyun.basedata.vo.product.sale.CreateProductSaleVo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl extends BaseMpServiceImpl<ProductMapper, Product> implements
    ProductService {

  @Autowired
  private ProductPurchaseService productPurchaseService;

  @Autowired
  private ProductSaleService productSaleService;

  @Autowired
  private ProductRetailService productRetailService;

  @Autowired
  private RecursionMappingService recursionMappingService;

  @Autowired
  private ProductCategoryPropertyDefinitionService productCategoryPropertyDefinitionService;

  @Autowired
  private ProductCategoryPropertyItemService productCategoryPropertyItemService;

  @Autowired
  private ProductCategoryPropertyValueRelationService productCategoryPropertyValueRelationService;

  @Autowired
  private ProductBundleService productBundleService;

  @Autowired
  private ProductCategoryService productCategoryService;

  @Autowired
  private ProductCategorySalePropertyRelationService productCategorySalePropertyRelationService;

  @Autowired
  private ProductSkuService productSkuService;

  @Autowired
  private ProductSkuCodeService productSkuCodeService;

  @Autowired
  private ProductSkuSalePropertyRelationService productSkuSalePropertyRelationService;

  @Autowired
  private ProductSalePropertyDefinitionService productSalePropertyDefinitionService;

  @Autowired
  private ProductSalePropertyItemService productSalePropertyItemService;

  @Override
  public PageResult<Product> query(Integer pageIndex, Integer pageSize, QueryProductVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<Product> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<Product> query(QueryProductVo vo) {

    return getBaseMapper().query(vo);
  }

  @Override
  public PageResult<Product> selector(Integer pageIndex, Integer pageSize,
      QueryProductSelectorVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<Product> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public Integer queryCount(QueryProductVo vo) {

    return getBaseMapper().queryCount(vo);
  }

  @Cacheable(value = Product.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public Product findById(String id) {

    return getBaseMapper().selectById(id);
  }

  @Override
  public Product findByCode(String code) {

    return getBaseMapper().findByCode(code);
  }

  @Override
  public List<String> getIdNotInProductCategoryPropertyDefinition(String propertyId) {

    return getBaseMapper().getIdNotInProductCategoryPropertyDefinition(propertyId);
  }

  @Override
  public List<String> getIdByCategoryId(String categoryId) {

    return getBaseMapper().getIdByCategoryId(categoryId);
  }

  @OpLog(type = BaseDataOpLogType.class, name = "删除商品，ID：{}", params = "#id")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {

    Wrapper<Product> updateWrapper = Wrappers.lambdaUpdate(Product.class)
        .set(Product::getAvailable, Boolean.FALSE).eq(Product::getId, id);
    getBaseMapper().update(updateWrapper);

    Product product = this.findById(id);

    DataChangeEventBuilder.publishLogicDelete(this, DeleteProductEvent.class, product);
  }

  @OpLog(type = BaseDataOpLogType.class, name = "新增商品，ID：{}, 编号：{}", params = {"#_result",
      "#vo.code"}, autoSaveParams = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateProductVo vo) {

    ProductType productType = EnumUtil.getByCode(ProductType.class, vo.getProductType());
    ProductSkuType skuType = this.getCreateSkuType(productType, vo.getSkus());
    List<ProductSkuVo> skuVos = this.getSkuVosForCreate(skuType, vo);
    String productCode = this.getProductCodeForCreate(vo.getSkus(), vo.getCode());
    this.checkSkuCodes(productCode, skuVos, null);
    this.checkSkuSaleProperties(vo.getCategoryId(), skuType, skuVos);

    Product data = new Product();
    data.setId(IdUtil.getId());
    data.setCode(productCode);
    data.setName(vo.getName());
    if (StringUtil.isNotBlank(vo.getShortName())) {
      data.setShortName(vo.getShortName());
    }

    if (StringUtil.isNotBlank(vo.getBrandId())) {
      data.setBrandId(vo.getBrandId());
    }
    data.setCategoryId(vo.getCategoryId());

    ProductCategory productCategory = productCategoryService.findById(data.getCategoryId());
    Wrapper<ProductCategory> checkCategoryWrapper = Wrappers.lambdaQuery(
            ProductCategory.class).eq(ProductCategory::getParentId, productCategory.getId())
        .eq(ProductCategory::getAvailable, Boolean.TRUE);
    if (productCategoryService.count(checkCategoryWrapper) > 0) {
      throw new DefaultClientException(
          "“商品分类”不是末级分类，请选择末级分类");
    }

    if (StringUtil.isNotBlank(vo.getSpec())) {
      data.setSpec(vo.getSpec());
    }

    if (StringUtil.isNotBlank(vo.getUnit())) {
      data.setUnit(vo.getUnit());
    }

    data.setProductType(productType);
    data.setSkuType(skuType);
    data.setDetailImages(JsonUtil.toJsonString(
        CollectionUtil.isEmpty(vo.getDetailImages()) ? CollectionUtil.emptyList()
            : vo.getDetailImages()));
    data.setMainImage(JsonUtil.toJsonString(
        CollectionUtil.isEmpty(vo.getMainImage()) ? CollectionUtil.emptyList()
            : vo.getMainImage()));
    data.setTaxRate(vo.getTaxRate() == null ? BigDecimal.ZERO : vo.getTaxRate());
    data.setSaleTaxRate(vo.getSaleTaxRate() == null ? BigDecimal.ZERO : vo.getSaleTaxRate());
    data.setWeight(vo.getWeight());
    data.setVolume(vo.getVolume());

    data.setAvailable(Boolean.TRUE);

    getBaseMapper().insert(data);

    this.checkSkuPrices(skuVos);

    // 组合商品
    if (data.getProductType() == ProductType.BUNDLE) {
      ProductSkuVo skuVo = skuVos.get(0);
      if (CollectionUtil.isEmpty(vo.getProductBundles())) {
        throw new DefaultClientException("单品数据不能为空！");
      }

      BigDecimal purchasePrice = vo.getProductBundles().stream().map(
          productBundleVo -> NumberUtil.mul(productBundleVo.getBundleNum(),
              productBundleVo.getPurchasePrice())).reduce(NumberUtil::add).orElse(BigDecimal.ZERO);
      if (!NumberUtil.equal(skuVo.getPurchasePrice(), purchasePrice)) {
        throw new DefaultClientException("单品的采购价设置错误！");
      }

      BigDecimal salePrice = vo.getProductBundles().stream().map(
          productBundleVo -> NumberUtil.mul(productBundleVo.getBundleNum(),
              productBundleVo.getSalePrice())).reduce(NumberUtil::add).orElse(BigDecimal.ZERO);
      if (!NumberUtil.equal(skuVo.getSalePrice(), salePrice)) {
        throw new DefaultClientException("单品的销售价设置错误！");
      }

      BigDecimal retailPrice = vo.getProductBundles().stream().map(
          productBundleVo -> NumberUtil.mul(productBundleVo.getBundleNum(),
              productBundleVo.getRetailPrice())).reduce(NumberUtil::add).orElse(BigDecimal.ZERO);
      if (!NumberUtil.equal(skuVo.getRetailPrice(), retailPrice)) {
        throw new DefaultClientException("单品的零售价设置错误！");
      }

      List<ProductBundle> productBundles = vo.getProductBundles().stream().map(productBundleVo -> {
        ProductBundle productBundle = new ProductBundle();
        productBundle.setId(IdUtil.getId());
        productBundle.setMainProductId(data.getId());
        productBundle.setProductId(productBundleVo.getProductId());
        productBundle.setBundleNum(productBundleVo.getBundleNum());
        productBundle.setPurchasePrice(productBundleVo.getPurchasePrice());
        productBundle.setSalePrice(productBundleVo.getSalePrice());
        productBundle.setRetailPrice(productBundleVo.getRetailPrice());

        return productBundle;
      }).collect(Collectors.toList());

      productBundleService.saveBatch(productBundles);
    }

    this.createSkus(data.getId(), productCode, skuVos, skuType);

    if (!CollectionUtil.isEmpty(vo.getProperties())) {
      // 商品和分类属性的关系
      for (ProductCategoryPropertyValueRelationVo property : vo.getProperties()) {
        ProductCategoryPropertyDefinition productCategoryPropertyDefinition = productCategoryPropertyDefinitionService.findById(property.getId());
        if (productCategoryPropertyDefinition == null) {
          throw new DefaultClientException("分类属性不存在！");
        }
        if (productCategoryPropertyDefinition.getColumnType() == ColumnType.SINGLE) {
          ProductCategoryPropertyItem propertyItem = productCategoryPropertyItemService.findById(
              property.getText());
          if (propertyItem == null) {
            throw new DefaultClientException("分类属性值不存在！");
          }

          CreateProductCategoryPropertyValueRelationVo createProductCategoryPropertyValueRelationVo = new CreateProductCategoryPropertyValueRelationVo();
          createProductCategoryPropertyValueRelationVo.setProductId(data.getId());
          createProductCategoryPropertyValueRelationVo.setPropertyId(productCategoryPropertyDefinition.getId());
          createProductCategoryPropertyValueRelationVo.setPropertyItemId(propertyItem.getId());

          productCategoryPropertyValueRelationService.create(createProductCategoryPropertyValueRelationVo);
        } else if (productCategoryPropertyDefinition.getColumnType() == ColumnType.MULTIPLE) {

          List<String> propertyItemIds = JsonUtil.parseList(property.getText(), String.class);
          for (String propertyItemId : propertyItemIds) {
            CreateProductCategoryPropertyValueRelationVo createProductCategoryPropertyValueRelationVo = new CreateProductCategoryPropertyValueRelationVo();
            createProductCategoryPropertyValueRelationVo.setProductId(data.getId());
            createProductCategoryPropertyValueRelationVo.setPropertyId(productCategoryPropertyDefinition.getId());
            createProductCategoryPropertyValueRelationVo.setPropertyItemId(propertyItemId);

            productCategoryPropertyValueRelationService.create(createProductCategoryPropertyValueRelationVo);
          }

        } else if (productCategoryPropertyDefinition.getColumnType() == ColumnType.CUSTOM) {

          CreateProductCategoryPropertyValueRelationVo createProductCategoryPropertyValueRelationVo = new CreateProductCategoryPropertyValueRelationVo();
          createProductCategoryPropertyValueRelationVo.setProductId(data.getId());
          createProductCategoryPropertyValueRelationVo.setPropertyId(productCategoryPropertyDefinition.getId());
          createProductCategoryPropertyValueRelationVo.setPropertyText(property.getText());
          productCategoryPropertyValueRelationService.create(createProductCategoryPropertyValueRelationVo);
        } else {
          throw new DefaultClientException("分类属性字段类型不存在！");
        }
      }
    }

    return data.getId();
  }

  @OpLog(type = BaseDataOpLogType.class, name = "修改商品，ID：{}, 编号：{}", params = {"#id",
      "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateProductVo vo) {

    Product data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("商品不存在！");
    }

    ProductSkuType skuType = this.getUpdateSkuType(data, vo.getSkus());
    List<ProductSkuVo> skuVos = this.getSkuVosForUpdate(data, skuType, vo);
    String productCode = this.getProductCodeForUpdate(vo.getSkus(), vo.getCode(), data.getCode());
    this.checkSkuCodes(productCode, skuVos, data.getId());
    this.checkSkuSaleProperties(vo.getCategoryId(), skuType, skuVos);
    this.checkSkuPrices(skuVos);

    ProductCategory productCategory = productCategoryService.findById(vo.getCategoryId());
    Wrapper<ProductCategory> checkCategoryWrapper = Wrappers.lambdaQuery(
            ProductCategory.class).eq(ProductCategory::getParentId, productCategory.getId())
        .eq(ProductCategory::getAvailable, Boolean.TRUE);
    if (productCategoryService.count(checkCategoryWrapper) > 0) {
      throw new DefaultClientException(
          "“商品分类”不是末级分类，请选择末级分类");
    }

    LambdaUpdateWrapper<Product> updateWrapper = Wrappers.lambdaUpdate(Product.class)
        .set(Product::getCode, StringUtil.isBlank(productCode) ? null : productCode)
        .set(Product::getName, vo.getName())
        .set(Product::getSpec, StringUtil.isBlank(vo.getSpec()) ? null : vo.getSpec())
        .set(Product::getUnit, StringUtil.isBlank(vo.getUnit()) ? null : vo.getUnit())
        .set(Product::getShortName,
            StringUtil.isBlank(vo.getShortName()) ? null : vo.getShortName())
        .set(Product::getCategoryId,
            StringUtil.isBlank(vo.getCategoryId()) ? null : vo.getCategoryId())
        .set(Product::getBrandId, StringUtil.isBlank(vo.getBrandId()) ? null : vo.getBrandId())
        .set(Product::getTaxRate, vo.getTaxRate() == null ? BigDecimal.ZERO : vo.getTaxRate())
        .set(Product::getSaleTaxRate,
            vo.getSaleTaxRate() == null ? BigDecimal.ZERO : vo.getSaleTaxRate())
        .set(Product::getWeight, vo.getWeight())
        .set(Product::getVolume, vo.getVolume())
        .set(Product::getSkuType, skuType)
        .set(Product::getDetailImages, JsonUtil.toJsonString(
            CollectionUtil.isEmpty(vo.getDetailImages()) ? CollectionUtil.emptyList()
                : vo.getDetailImages()))
        .set(Product::getMainImage, JsonUtil.toJsonString(
            CollectionUtil.isEmpty(vo.getMainImage()) ? CollectionUtil.emptyList()
                : vo.getMainImage()))
        .eq(Product::getId, vo.getId());

    getBaseMapper().update(updateWrapper);

    this.replaceSkus(data.getId(), productCode, skuVos, skuType);

    // 组合商品
    if (data.getProductType() == ProductType.BUNDLE) {
      ProductSkuVo skuVo = skuVos.get(0);
      if (CollectionUtil.isEmpty(vo.getProductBundles())) {
        throw new DefaultClientException("单品数据不能为空！");
      }

      BigDecimal purchasePrice = vo.getProductBundles().stream().map(
          productBundleVo -> NumberUtil.mul(productBundleVo.getBundleNum(),
              productBundleVo.getPurchasePrice())).reduce(NumberUtil::add).orElse(BigDecimal.ZERO);
      if (!NumberUtil.equal(skuVo.getPurchasePrice(), purchasePrice)) {
        throw new DefaultClientException("单品的采购价设置错误！");
      }

      BigDecimal salePrice = vo.getProductBundles().stream().map(
          productBundleVo -> NumberUtil.mul(productBundleVo.getBundleNum(),
              productBundleVo.getSalePrice())).reduce(NumberUtil::add).orElse(BigDecimal.ZERO);
      if (!NumberUtil.equal(skuVo.getSalePrice(), salePrice)) {
        throw new DefaultClientException("单品的销售价设置错误！");
      }

      BigDecimal retailPrice = vo.getProductBundles().stream().map(
          productBundleVo -> NumberUtil.mul(productBundleVo.getBundleNum(),
              productBundleVo.getRetailPrice())).reduce(NumberUtil::add).orElse(BigDecimal.ZERO);
      if (!NumberUtil.equal(skuVo.getRetailPrice(), retailPrice)) {
        throw new DefaultClientException("单品的零售价设置错误！");
      }

      Wrapper<ProductBundle> deleteBundleWrapper = Wrappers.lambdaQuery(ProductBundle.class)
          .eq(ProductBundle::getMainProductId, data.getId());
      productBundleService.remove(deleteBundleWrapper);

      List<ProductBundle> productBundles = vo.getProductBundles().stream().map(productBundleVo -> {
        ProductBundle productBundle = new ProductBundle();
        productBundle.setId(IdUtil.getId());
        productBundle.setMainProductId(data.getId());
        productBundle.setProductId(productBundleVo.getProductId());
        productBundle.setBundleNum(productBundleVo.getBundleNum());
        productBundle.setPurchasePrice(productBundleVo.getPurchasePrice());
        productBundle.setSalePrice(productBundleVo.getSalePrice());
        productBundle.setRetailPrice(productBundleVo.getRetailPrice());

        return productBundle;
      }).collect(Collectors.toList());

      productBundleService.saveBatch(productBundles);
    }

    productCategoryPropertyValueRelationService.deleteByProductId(data.getId());
    if (!CollectionUtil.isEmpty(vo.getProperties())) {
      // 商品和分类属性的关系
      for (ProductCategoryPropertyValueRelationVo property : vo.getProperties()) {
        ProductCategoryPropertyDefinition productCategoryPropertyDefinition = productCategoryPropertyDefinitionService.findById(property.getId());
        if (productCategoryPropertyDefinition == null) {
          throw new DefaultClientException("分类属性不存在！");
        }
        if (productCategoryPropertyDefinition.getColumnType() == ColumnType.SINGLE) {
          ProductCategoryPropertyItem propertyItem = productCategoryPropertyItemService.findById(
              property.getText());
          if (propertyItem == null) {
            throw new DefaultClientException("分类属性值不存在！");
          }

          CreateProductCategoryPropertyValueRelationVo createProductCategoryPropertyValueRelationVo = new CreateProductCategoryPropertyValueRelationVo();
          createProductCategoryPropertyValueRelationVo.setProductId(data.getId());
          createProductCategoryPropertyValueRelationVo.setPropertyId(productCategoryPropertyDefinition.getId());
          createProductCategoryPropertyValueRelationVo.setPropertyItemId(propertyItem.getId());

          productCategoryPropertyValueRelationService.create(createProductCategoryPropertyValueRelationVo);
        } else if (productCategoryPropertyDefinition.getColumnType() == ColumnType.MULTIPLE) {

          List<String> propertyItemIds = JsonUtil.parseList(property.getText(), String.class);
          for (String propertyItemId : propertyItemIds) {
            CreateProductCategoryPropertyValueRelationVo createProductCategoryPropertyValueRelationVo = new CreateProductCategoryPropertyValueRelationVo();
            createProductCategoryPropertyValueRelationVo.setProductId(data.getId());
            createProductCategoryPropertyValueRelationVo.setPropertyId(productCategoryPropertyDefinition.getId());
            createProductCategoryPropertyValueRelationVo.setPropertyItemId(propertyItemId);

            productCategoryPropertyValueRelationService.create(createProductCategoryPropertyValueRelationVo);
          }

        } else if (productCategoryPropertyDefinition.getColumnType() == ColumnType.CUSTOM) {

          CreateProductCategoryPropertyValueRelationVo createProductCategoryPropertyValueRelationVo = new CreateProductCategoryPropertyValueRelationVo();
          createProductCategoryPropertyValueRelationVo.setProductId(data.getId());
          createProductCategoryPropertyValueRelationVo.setPropertyId(productCategoryPropertyDefinition.getId());
          createProductCategoryPropertyValueRelationVo.setPropertyText(property.getText());
          productCategoryPropertyValueRelationService.create(createProductCategoryPropertyValueRelationVo);
        } else {
          throw new DefaultClientException("分类属性字段类型不存在！");
        }
      }
    }

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", skuVos.get(0).getCode());
    OpLogUtil.setExtra(vo);
  }

  @Override
  public List<Product> getByCategoryIds(List<String> categoryIds, Integer productType) {

    if (CollectionUtil.isEmpty(categoryIds)) {
      return CollectionUtil.emptyList();
    }

    // 根据categoryIds查询所有叶子节点
    List<String> children = new ArrayList<>();
    for (String categoryId : categoryIds) {
      children.addAll(recursionMappingService.getNodeChildIds(categoryId,
          ProductCategoryNodeType.class));
    }

    children.addAll(categoryIds);

    children = children.stream().distinct().collect(Collectors.toList());

    List<Product> datas = getBaseMapper().getByCategoryIds(children, productType);

    return datas;
  }

  @Override
  public List<Product> getByBrandIds(List<String> brandIds, Integer productType) {

    if (CollectionUtil.isEmpty(brandIds)) {
      return CollectionUtil.emptyList();
    }

    return getBaseMapper().getByBrandIds(brandIds, productType);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void recordMultiCodes(String productId, List<String> multiCodes) {
    Product product = this.findById(productId);
    List<ProductSku> skus = productSkuService.getAvailableByProductId(productId);
    if (CollectionUtil.isEmpty(skus)) {
      return;
    }
    ProductSku sku = skus.get(0);
    productSkuCodeService.rebuildSkuCodes(sku, product.getCode(), multiCodes);
  }

  private void createSkus(String productId, String productCode, List<ProductSkuVo> skuVos,
      ProductSkuType skuType) {

    for (ProductSkuVo skuVo : skuVos) {
      this.createSku(productId, productCode, skuVo, skuType);
    }
  }

  private void createSku(String productId, String productCode, ProductSkuVo skuVo,
      ProductSkuType skuType) {

    ProductSku sku = new ProductSku();
    sku.setId(skuType == ProductSkuType.SINGLE ? productId : IdUtil.getId());
    sku.setProductId(productId);
    sku.setCode(skuVo.getCode());
    sku.setMultiCode(CollectionUtil.isNotEmpty(skuVo.getMultiCodes()));
    sku.setSalePropertyText(this.buildSkuSalePropertyText(skuVo.getSaleProperties()));
    sku.setMainImage(skuVo.getMainImage());
    sku.setAvailable(Boolean.TRUE);
    productSkuService.save(sku);

    CreateProductPurchaseVo createProductPurchaseVo = new CreateProductPurchaseVo();
    createProductPurchaseVo.setId(sku.getId());
    createProductPurchaseVo.setPrice(skuVo.getPurchasePrice());
    productPurchaseService.create(createProductPurchaseVo);

    CreateProductSaleVo createProductSaleVo = new CreateProductSaleVo();
    createProductSaleVo.setId(sku.getId());
    createProductSaleVo.setPrice(skuVo.getSalePrice());
    productSaleService.create(createProductSaleVo);

    CreateProductRetailVo createProductRetailVo = new CreateProductRetailVo();
    createProductRetailVo.setId(sku.getId());
    createProductRetailVo.setPrice(skuVo.getRetailPrice());
    productRetailService.create(createProductRetailVo);

    productSkuCodeService.rebuildSkuCodes(sku, productCode, skuVo.getMultiCodes());
    this.saveSkuSaleProperties(sku.getId(), skuVo.getSaleProperties());
  }

  private void replaceSkus(String productId, String productCode, List<ProductSkuVo> skuVos,
      ProductSkuType skuType) {

    List<ProductSku> oldSkus = productSkuService.getAvailableByProductId(productId);
    Map<String, ProductSku> oldSkuMap = oldSkus.stream()
        .collect(Collectors.toMap(ProductSku::getId, t -> t));
    Set<String> newSkuIds = skuVos.stream().map(ProductSkuVo::getId).filter(StringUtil::isNotBlank)
        .collect(Collectors.toSet());

    for (ProductSku sku : oldSkus.stream().filter(t -> !newSkuIds.contains(t.getId())).toList()) {
      productSkuService.deleteById(sku.getId());
    }

    for (ProductSkuVo skuVo : skuVos) {
      ProductSku oldSku = StringUtil.isBlank(skuVo.getId()) ? null : oldSkuMap.get(skuVo.getId());
      if (oldSku == null) {
        this.createSku(productId, productCode, skuVo, skuType);
        continue;
      }

      oldSku.setCode(skuVo.getCode());
      oldSku.setMultiCode(CollectionUtil.isNotEmpty(skuVo.getMultiCodes()));
      oldSku.setSalePropertyText(this.buildSkuSalePropertyText(skuVo.getSaleProperties()));
      oldSku.setMainImage(skuVo.getMainImage());
      oldSku.setAvailable(Boolean.TRUE);
      productSkuService.updateById(oldSku);
      productSkuCodeService.rebuildSkuCodes(oldSku, productCode, skuVo.getMultiCodes());
      productSkuSalePropertyRelationService.deleteBySkuId(oldSku.getId());
      this.saveSkuSaleProperties(oldSku.getId(), skuVo.getSaleProperties());
      this.replaceSkuPrices(oldSku.getId(), skuVo);
    }
  }

  private void replaceSkuPrices(String skuId, ProductSkuVo skuVo) {

    productPurchaseService.removeById(skuId);
    productSaleService.removeById(skuId);
    productRetailService.removeById(skuId);

    CreateProductPurchaseVo createProductPurchaseVo = new CreateProductPurchaseVo();
    createProductPurchaseVo.setId(skuId);
    createProductPurchaseVo.setPrice(skuVo.getPurchasePrice());
    productPurchaseService.create(createProductPurchaseVo);

    CreateProductSaleVo createProductSaleVo = new CreateProductSaleVo();
    createProductSaleVo.setId(skuId);
    createProductSaleVo.setPrice(skuVo.getSalePrice());
    productSaleService.create(createProductSaleVo);

    CreateProductRetailVo createProductRetailVo = new CreateProductRetailVo();
    createProductRetailVo.setId(skuId);
    createProductRetailVo.setPrice(skuVo.getRetailPrice());
    productRetailService.create(createProductRetailVo);
  }

  private ProductSkuType getCreateSkuType(ProductType productType, List<ProductSkuVo> skus) {

    if (productType == ProductType.BUNDLE) {
      return ProductSkuType.SINGLE;
    }

    return CollectionUtil.isNotEmpty(skus) && skus.size() > 1 ? ProductSkuType.MULTI
        : ProductSkuType.SINGLE;
  }

  private ProductSkuType getUpdateSkuType(Product data, List<ProductSkuVo> skus) {

    if (data.getProductType() == ProductType.BUNDLE) {
      return ProductSkuType.SINGLE;
    }

    ProductSkuType oldSkuType = data.getSkuType() == null ? ProductSkuType.SINGLE
        : data.getSkuType();
    ProductSkuType newSkuType = CollectionUtil.isNotEmpty(skus) && skus.size() > 1
        ? ProductSkuType.MULTI : ProductSkuType.SINGLE;

    if (oldSkuType == ProductSkuType.MULTI && newSkuType == ProductSkuType.SINGLE) {
      throw new DefaultClientException("多SKU商品不允许切换为单SKU！");
    }

    return newSkuType;
  }

  private List<ProductSkuVo> getSkuVosForCreate(ProductSkuType skuType, CreateProductVo vo) {

    if (CollectionUtil.isEmpty(vo.getSkus())) {
      throw new DefaultClientException("SKU信息不能为空！");
    }

    if (skuType == ProductSkuType.MULTI) {
      if (vo.getSkus().size() < 2) {
        throw new DefaultClientException("多SKU商品至少需要2个SKU！");
      }
      return vo.getSkus();
    }

    if (vo.getSkus().size() > 1) {
      throw new DefaultClientException("单SKU商品只能设置1个SKU！");
    }

    return vo.getSkus();
  }

  private List<ProductSkuVo> getSkuVosForUpdate(Product data, ProductSkuType skuType,
      UpdateProductVo vo) {

    if (CollectionUtil.isEmpty(vo.getSkus())) {
      throw new DefaultClientException("SKU信息不能为空！");
    }

    if (skuType == ProductSkuType.MULTI) {
      if (vo.getSkus().size() < 2) {
        throw new DefaultClientException("多SKU商品至少需要2个SKU！");
      }
      return vo.getSkus();
    }

    if (vo.getSkus().size() > 1) {
      throw new DefaultClientException("单SKU商品只能设置1个SKU！");
    }

    ProductSkuVo skuVo = vo.getSkus().get(0);

    List<ProductSku> oldSkus = productSkuService.getAvailableByProductId(data.getId());
    if (CollectionUtil.isNotEmpty(oldSkus)) {
      skuVo.setId(oldSkus.get(0).getId());
    }

    return CollectionUtil.toList(skuVo);
  }

  private String getProductCodeForCreate(List<ProductSkuVo> skus, String code) {

    if (CollectionUtil.isEmpty(skus)) {
      return null;
    }

    return StringUtil.isBlank(code) ? null : code;
  }

  private String getProductCodeForUpdate(List<ProductSkuVo> skus, String code,
      String oldProductCode) {

    if (CollectionUtil.isEmpty(skus)) {
      return oldProductCode;
    }

    return StringUtil.isBlank(code) ? null : code;
  }

  private void checkSkuCodes(String productCode, List<ProductSkuVo> skuVos, String productId) {

    List<String> allCodes = new ArrayList<>();
    List<String> distinctSkuIds = skuVos.stream().map(ProductSkuVo::getId)
        .filter(StringUtil::isNotBlank).distinct().toList();
    for (ProductSkuVo skuVo : skuVos) {
      if (StringUtil.isBlank(skuVo.getCode())) {
        throw new DefaultClientException("请输入SKU编号！");
      }
      allCodes.add(skuVo.getCode());
      if (CollectionUtil.isNotEmpty(skuVo.getMultiCodes())) {
        allCodes.addAll(skuVo.getMultiCodes());
      }
    }

    List<String> internalCodes = new ArrayList<>(allCodes);
    if (StringUtil.isNotBlank(productCode)) {
      internalCodes.add(productCode);
    }
    List<String> duplicatedCodes = internalCodes.stream().filter(StringUtil::isNotBlank)
        .collect(Collectors.groupingBy(code -> code, Collectors.counting())).entrySet().stream()
        .filter(t -> t.getValue() > 1).map(t -> t.getKey()).toList();
    if (CollectionUtil.isNotEmpty(duplicatedCodes)) {
      throw new DefaultClientException(
          "编号【" + CollectionUtil.join(duplicatedCodes, StringPool.STR_SPLIT_CN)
              + "】重复，请重新输入！");
    }

    if (StringUtil.isNotBlank(productCode)) {
      allCodes.add(productCode);
    }
    List<String> conflictCodes = productSkuCodeService.checkCodes(allCodes, productId,
        distinctSkuIds.size() == 1 ? distinctSkuIds.get(0) : null);
    if (CollectionUtil.isNotEmpty(conflictCodes)) {
      throw new DefaultClientException(
          "编号【" + CollectionUtil.join(conflictCodes, StringPool.STR_SPLIT_CN)
              + "】重复，请重新输入！");
    }
  }

  private void checkSkuPrices(List<ProductSkuVo> skuVos) {

    for (ProductSkuVo skuVo : skuVos) {
      if (skuVo.getPurchasePrice() == null) {
        throw new DefaultClientException("采购价不能为空！");
      }

      if (NumberUtil.lt(skuVo.getPurchasePrice(), 0)) {
        throw new DefaultClientException("采购价不允许小于0！");
      }

      if (skuVo.getSalePrice() == null) {
        throw new DefaultClientException("销售价不能为空！");
      }

      if (NumberUtil.lt(skuVo.getSalePrice(), 0)) {
        throw new DefaultClientException("销售价不允许小于0！");
      }

      if (skuVo.getRetailPrice() == null) {
        throw new DefaultClientException("零售价不能为空！");
      }

      if (NumberUtil.lt(skuVo.getRetailPrice(), 0D)) {
        throw new DefaultClientException("零售价不允许小于0！");
      }
    }
  }

  private void checkSkuSaleProperties(String categoryId, ProductSkuType skuType,
      List<ProductSkuVo> skuVos) {

    if (skuType == ProductSkuType.SINGLE) {
      boolean hasSaleProperties = skuVos.stream()
          .anyMatch(t -> CollectionUtil.isNotEmpty(t.getSaleProperties()));
      if (hasSaleProperties) {
        throw new DefaultClientException("单SKU商品不能设置销售属性！");
      }
      return;
    }

    List<String> categoryPropertyIds = productCategorySalePropertyRelationService.getByCategoryId(
        categoryId).stream().map(ProductCategorySalePropertyRelationDto::getPropertyId).toList();
    if (CollectionUtil.isEmpty(categoryPropertyIds)) {
      throw new DefaultClientException("多SKU商品分类必须先配置销售属性！");
    }

    Set<String> skuPropertyKeys = new HashSet<>();
    for (ProductSkuVo skuVo : skuVos) {
      if (CollectionUtil.isEmpty(skuVo.getSaleProperties())) {
        throw new DefaultClientException("SKU销售属性不能为空！");
      }

      List<String> propertyIds = skuVo.getSaleProperties().stream()
          .map(ProductSkuVo.SalePropertyVo::getPropertyId).toList();
      if (!CollectionUtil.distinct(propertyIds).containsAll(categoryPropertyIds)
          || propertyIds.size() != categoryPropertyIds.size()) {
        throw new DefaultClientException("SKU销售属性必须与商品分类销售属性一致！");
      }

      String skuPropertyKey = skuVo.getSaleProperties().stream()
          .sorted((o1, o2) -> o1.getPropertyId().compareTo(o2.getPropertyId())).map(item -> {
            if (StringUtil.isBlank(item.getPropertyItemId())) {
              throw new DefaultClientException("SKU销售属性值不能为空！");
            }
            ProductSalePropertyItem propertyItem = productSalePropertyItemService.findById(
                item.getPropertyItemId());
            if (propertyItem == null) {
              throw new DefaultClientException("SKU销售属性值不存在！");
            }
            if (!StringUtil.equals(propertyItem.getPropertyId(), item.getPropertyId())) {
              throw new DefaultClientException("SKU销售属性值与销售属性不匹配！");
            }
            return item.getPropertyId() + ":" + item.getPropertyItemId();
          }).collect(Collectors.joining(StringPool.STR_SPLIT));

      if (!skuPropertyKeys.add(skuPropertyKey)) {
        throw new DefaultClientException("SKU销售属性组合不能重复！");
      }
    }
  }

  private void saveSkuSaleProperties(String skuId,
      List<ProductSkuVo.SalePropertyVo> saleProperties) {

    if (CollectionUtil.isEmpty(saleProperties)) {
      return;
    }

    List<ProductSkuSalePropertyRelation> relations = saleProperties.stream().map(item -> {
      ProductSkuSalePropertyRelation relation = new ProductSkuSalePropertyRelation();
      relation.setId(IdUtil.getId());
      relation.setSkuId(skuId);
      relation.setPropertyId(item.getPropertyId());
      relation.setPropertyItemId(item.getPropertyItemId());
      return relation;
    }).collect(Collectors.toList());
    productSkuSalePropertyRelationService.saveBatch(relations);
  }

  private String buildSkuSalePropertyText(List<ProductSkuVo.SalePropertyVo> saleProperties) {

    if (CollectionUtil.isEmpty(saleProperties)) {
      return StringPool.EMPTY_STR;
    }

    return saleProperties.stream().map(item -> {
      ProductSalePropertyDefinition property = productSalePropertyDefinitionService.findById(item.getPropertyId());
      ProductSalePropertyItem propertyItem = productSalePropertyItemService.findById(
          item.getPropertyItemId());

      return property.getName() + "：" + propertyItem.getName();
    }).collect(Collectors.joining(" / "));
  }

  @CacheEvict(value = Product.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
