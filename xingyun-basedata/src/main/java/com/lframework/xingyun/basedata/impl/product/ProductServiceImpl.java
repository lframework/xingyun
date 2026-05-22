package com.lframework.xingyun.basedata.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
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
import com.lframework.xingyun.basedata.entity.ProductCode;
import com.lframework.xingyun.basedata.entity.ProductProperty;
import com.lframework.xingyun.basedata.entity.ProductPropertyItem;
import com.lframework.xingyun.basedata.enums.BaseDataOpLogType;
import com.lframework.xingyun.basedata.enums.ColumnType;
import com.lframework.xingyun.basedata.enums.ProductCategoryNodeType;
import com.lframework.xingyun.basedata.enums.ProductType;
import com.lframework.xingyun.basedata.events.DeleteProductEvent;
import com.lframework.xingyun.basedata.mappers.ProductMapper;
import com.lframework.xingyun.basedata.service.product.ProductBundleService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryService;
import com.lframework.xingyun.basedata.service.product.ProductCodeService;
import com.lframework.xingyun.basedata.service.product.ProductPropertyItemService;
import com.lframework.xingyun.basedata.service.product.ProductPropertyRelationService;
import com.lframework.xingyun.basedata.service.product.ProductPropertyService;
import com.lframework.xingyun.basedata.service.product.ProductPurchaseService;
import com.lframework.xingyun.basedata.service.product.ProductRetailService;
import com.lframework.xingyun.basedata.service.product.ProductSaleService;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.basedata.vo.product.info.CreateProductVo;
import com.lframework.xingyun.basedata.vo.product.info.ProductPropertyRelationVo;
import com.lframework.xingyun.basedata.vo.product.info.QueryProductSelectorVo;
import com.lframework.xingyun.basedata.vo.product.info.QueryProductVo;
import com.lframework.xingyun.basedata.vo.product.info.UpdateProductVo;
import com.lframework.xingyun.basedata.vo.product.property.realtion.CreateProductPropertyRelationVo;
import com.lframework.xingyun.basedata.vo.product.purchase.CreateProductPurchaseVo;
import com.lframework.xingyun.basedata.vo.product.purchase.UpdateProductPurchaseVo;
import com.lframework.xingyun.basedata.vo.product.retail.CreateProductRetailVo;
import com.lframework.xingyun.basedata.vo.product.retail.UpdateProductRetailVo;
import com.lframework.xingyun.basedata.vo.product.sale.CreateProductSaleVo;
import com.lframework.xingyun.basedata.vo.product.sale.UpdateProductSaleVo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
  private ProductPropertyService productPropertyService;

  @Autowired
  private ProductPropertyItemService productPropertyItemService;

  @Autowired
  private ProductPropertyRelationService productPropertyRelationService;

  @Autowired
  private ProductBundleService productBundleService;

  @Autowired
  private ProductCategoryService productCategoryService;

  @Autowired
  private ProductCodeService productCodeService;

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
    Wrapper<Product> queryWrapper = new MPJLambdaWrapper<Product>().selectAll(Product.class)
        .leftJoin(ProductCode.class, ProductCode::getProductId, Product::getId)
        .eq(ProductCode::getCode, code)
        .eq(Product::getAvailable, true);
    return getOne(queryWrapper);
  }

  @Override
  public List<String> getIdNotInProductProperty(String propertyId) {

    return getBaseMapper().getIdNotInProductProperty(propertyId);
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

    List<String> allCodes = new ArrayList<>();
    allCodes.add(vo.getCode());
    if (CollectionUtil.isNotEmpty(vo.getMultiCodes())) {
      allCodes.addAll(vo.getMultiCodes());
    }
    List<String> conflictCodes = productCodeService.checkMultiCodes(allCodes, null);
    if (CollectionUtil.isNotEmpty(conflictCodes)) {
      throw new DefaultClientException(
          "编号【" + CollectionUtil.join(conflictCodes, StringPool.STR_SPLIT_CN)
              + "】重复，请重新输入！");
    }

    Wrapper<Product> checkWrapper = Wrappers.lambdaQuery(Product.class)
        .eq(Product::getCode, vo.getCode()).eq(Product::getAvailable, Boolean.TRUE);
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    // 校验扩展编号
    if (CollectionUtil.isNotEmpty(vo.getMultiCodes())) {
      List<String> checkList = new ArrayList<>(vo.getMultiCodes());
      checkList.add(vo.getCode());
      List<String> checkResult = productCodeService.checkMultiCodes(
          checkList, null);
      if (CollectionUtil.isNotEmpty(checkResult)) {
        throw new DefaultClientException(
            "编号【" + CollectionUtil.join(checkResult, StringPool.STR_SPLIT_CN)
                + "】重复，请重新输入！");
      }
    }
    Product data = new Product();
    data.setId(IdUtil.getId());
    data.setCode(vo.getCode());
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

    data.setMultiCode(CollectionUtil.isNotEmpty(vo.getMultiCodes())); // 是否一品多码
    data.setProductType(EnumUtil.getByCode(ProductType.class, vo.getProductType()));
    data.setTaxRate(vo.getTaxRate() == null ? BigDecimal.ZERO : vo.getTaxRate());
    data.setSaleTaxRate(vo.getSaleTaxRate() == null ? BigDecimal.ZERO : vo.getSaleTaxRate());
    data.setWeight(vo.getWeight());
    data.setVolume(vo.getVolume());

    data.setAvailable(Boolean.TRUE);

    getBaseMapper().insert(data);

    recordMultiCodes(data.getId(), vo.getMultiCodes());

    // 组合商品
    if (data.getProductType() == ProductType.BUNDLE) {
      if (CollectionUtil.isEmpty(vo.getProductBundles())) {
        throw new DefaultClientException("单品数据不能为空！");
      }

      BigDecimal purchasePrice = vo.getProductBundles().stream().map(
          productBundleVo -> NumberUtil.mul(productBundleVo.getBundleNum(),
              productBundleVo.getPurchasePrice())).reduce(NumberUtil::add).orElse(BigDecimal.ZERO);
      if (!NumberUtil.equal(vo.getPurchasePrice(), purchasePrice)) {
        throw new DefaultClientException("单品的采购价设置错误！");
      }

      BigDecimal salePrice = vo.getProductBundles().stream().map(
          productBundleVo -> NumberUtil.mul(productBundleVo.getBundleNum(),
              productBundleVo.getSalePrice())).reduce(NumberUtil::add).orElse(BigDecimal.ZERO);
      if (!NumberUtil.equal(vo.getSalePrice(), salePrice)) {
        throw new DefaultClientException("单品的销售价设置错误！");
      }

      BigDecimal retailPrice = vo.getProductBundles().stream().map(
          productBundleVo -> NumberUtil.mul(productBundleVo.getBundleNum(),
              productBundleVo.getRetailPrice())).reduce(NumberUtil::add).orElse(BigDecimal.ZERO);
      if (!NumberUtil.equal(vo.getRetailPrice(), retailPrice)) {
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

    if (vo.getPurchasePrice() == null) {
      throw new DefaultClientException("采购价不能为空！");
    }

    if (NumberUtil.lt(vo.getPurchasePrice(), 0)) {
      throw new DefaultClientException("采购价不允许小于0！");
    }

    CreateProductPurchaseVo createProductPurchaseVo = new CreateProductPurchaseVo();
    createProductPurchaseVo.setId(data.getId());
    createProductPurchaseVo.setPrice(vo.getPurchasePrice());

    productPurchaseService.create(createProductPurchaseVo);

    if (vo.getSalePrice() == null) {
      throw new DefaultClientException("销售价不能为空！");
    }

    if (NumberUtil.lt(vo.getSalePrice(), 0)) {
      throw new DefaultClientException("销售价不允许小于0！");
    }

    CreateProductSaleVo createProductSaleVo = new CreateProductSaleVo();
    createProductSaleVo.setId(data.getId());
    createProductSaleVo.setPrice(vo.getSalePrice());

    productSaleService.create(createProductSaleVo);

    if (vo.getRetailPrice() == null) {
      throw new DefaultClientException("零售价不能为空！");
    }

    if (NumberUtil.lt(vo.getRetailPrice(), 0D)) {
      throw new DefaultClientException("零售价不允许小于0！");
    }

    CreateProductRetailVo createProductRetailVo = new CreateProductRetailVo();
    createProductRetailVo.setId(data.getId());
    createProductRetailVo.setPrice(vo.getRetailPrice());

    productRetailService.create(createProductRetailVo);

    if (!CollectionUtil.isEmpty(vo.getProperties())) {
      // 商品和分类属性的关系
      for (ProductPropertyRelationVo property : vo.getProperties()) {
        ProductProperty productProperty = productPropertyService.findById(property.getId());
        if (productProperty == null) {
          throw new DefaultClientException("分类属性不存在！");
        }
        if (productProperty.getColumnType() == ColumnType.SINGLE) {
          ProductPropertyItem propertyItem = productPropertyItemService.findById(
              property.getText());
          if (propertyItem == null) {
            throw new DefaultClientException("分类属性值不存在！");
          }

          CreateProductPropertyRelationVo createProductPropertyRelationVo = new CreateProductPropertyRelationVo();
          createProductPropertyRelationVo.setProductId(data.getId());
          createProductPropertyRelationVo.setPropertyId(productProperty.getId());
          createProductPropertyRelationVo.setPropertyItemId(propertyItem.getId());

          productPropertyRelationService.create(createProductPropertyRelationVo);
        } else if (productProperty.getColumnType() == ColumnType.MULTIPLE) {

          List<String> propertyItemIds = JsonUtil.parseList(property.getText(), String.class);
          for (String propertyItemId : propertyItemIds) {
            CreateProductPropertyRelationVo createProductPropertyRelationVo = new CreateProductPropertyRelationVo();
            createProductPropertyRelationVo.setProductId(data.getId());
            createProductPropertyRelationVo.setPropertyId(productProperty.getId());
            createProductPropertyRelationVo.setPropertyItemId(propertyItemId);

            productPropertyRelationService.create(createProductPropertyRelationVo);
          }

        } else if (productProperty.getColumnType() == ColumnType.CUSTOM) {

          CreateProductPropertyRelationVo createProductPropertyRelationVo = new CreateProductPropertyRelationVo();
          createProductPropertyRelationVo.setProductId(data.getId());
          createProductPropertyRelationVo.setPropertyId(productProperty.getId());
          createProductPropertyRelationVo.setPropertyText(property.getText());
          productPropertyRelationService.create(createProductPropertyRelationVo);
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

    List<String> allCodes = new ArrayList<>();
    allCodes.add(vo.getCode());
    if (CollectionUtil.isNotEmpty(vo.getMultiCodes())) {
      allCodes.addAll(vo.getMultiCodes());
    }
    List<String> conflictCodes = productCodeService.checkMultiCodes(allCodes, data.getId());
    if (CollectionUtil.isNotEmpty(conflictCodes)) {
      throw new DefaultClientException(
          "编号【" + CollectionUtil.join(conflictCodes, StringPool.STR_SPLIT_CN)
              + "】重复，请重新输入！");
    }

    Wrapper<Product> checkWrapper = Wrappers.lambdaQuery(Product.class)
        .eq(Product::getCode, vo.getCode()).eq(Product::getAvailable, Boolean.TRUE)
        .ne(Product::getId, vo.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    // 校验扩展编号
    if (CollectionUtil.isNotEmpty(vo.getMultiCodes())) {
      List<String> checkList = new ArrayList<>(vo.getMultiCodes());
      checkList.add(vo.getCode());
      List<String> checkResult = productCodeService.checkMultiCodes(
          checkList, data.getId());
      if (CollectionUtil.isNotEmpty(checkResult)) {
        throw new DefaultClientException(
            "编号【" + CollectionUtil.join(checkResult, StringPool.STR_SPLIT_CN)
                + "】重复，请重新输入！");
      }
    }

    ProductCategory productCategory = productCategoryService.findById(vo.getCategoryId());
    Wrapper<ProductCategory> checkCategoryWrapper = Wrappers.lambdaQuery(
            ProductCategory.class).eq(ProductCategory::getParentId, productCategory.getId())
        .eq(ProductCategory::getAvailable, Boolean.TRUE);
    if (productCategoryService.count(checkCategoryWrapper) > 0) {
      throw new DefaultClientException(
          "“商品分类”不是末级分类，请选择末级分类");
    }

    LambdaUpdateWrapper<Product> updateWrapper = Wrappers.lambdaUpdate(Product.class)
        .set(Product::getCode, vo.getCode()).set(Product::getName, vo.getName())
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
        .set(Product::getMultiCode, CollectionUtil.isNotEmpty(vo.getMultiCodes()))
        .eq(Product::getId, vo.getId());

    getBaseMapper().update(updateWrapper);

    recordMultiCodes(data.getId(), vo.getMultiCodes());

    // 组合商品
    if (data.getProductType() == ProductType.BUNDLE) {
      if (CollectionUtil.isEmpty(vo.getProductBundles())) {
        throw new DefaultClientException("单品数据不能为空！");
      }

      BigDecimal purchasePrice = vo.getProductBundles().stream().map(
          productBundleVo -> NumberUtil.mul(productBundleVo.getBundleNum(),
              productBundleVo.getPurchasePrice())).reduce(NumberUtil::add).orElse(BigDecimal.ZERO);
      if (!NumberUtil.equal(vo.getPurchasePrice(), purchasePrice)) {
        throw new DefaultClientException("单品的采购价设置错误！");
      }

      BigDecimal salePrice = vo.getProductBundles().stream().map(
          productBundleVo -> NumberUtil.mul(productBundleVo.getBundleNum(),
              productBundleVo.getSalePrice())).reduce(NumberUtil::add).orElse(BigDecimal.ZERO);
      if (!NumberUtil.equal(vo.getSalePrice(), salePrice)) {
        throw new DefaultClientException("单品的销售价设置错误！");
      }

      BigDecimal retailPrice = vo.getProductBundles().stream().map(
          productBundleVo -> NumberUtil.mul(productBundleVo.getBundleNum(),
              productBundleVo.getRetailPrice())).reduce(NumberUtil::add).orElse(BigDecimal.ZERO);
      if (!NumberUtil.equal(vo.getRetailPrice(), retailPrice)) {
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

    productPropertyRelationService.deleteByProductId(data.getId());
    if (!CollectionUtil.isEmpty(vo.getProperties())) {
      // 商品和分类属性的关系
      for (ProductPropertyRelationVo property : vo.getProperties()) {
        ProductProperty productProperty = productPropertyService.findById(property.getId());
        if (productProperty == null) {
          throw new DefaultClientException("分类属性不存在！");
        }
        if (productProperty.getColumnType() == ColumnType.SINGLE) {
          ProductPropertyItem propertyItem = productPropertyItemService.findById(
              property.getText());
          if (propertyItem == null) {
            throw new DefaultClientException("分类属性值不存在！");
          }

          CreateProductPropertyRelationVo createProductPropertyRelationVo = new CreateProductPropertyRelationVo();
          createProductPropertyRelationVo.setProductId(data.getId());
          createProductPropertyRelationVo.setPropertyId(productProperty.getId());
          createProductPropertyRelationVo.setPropertyItemId(propertyItem.getId());

          productPropertyRelationService.create(createProductPropertyRelationVo);
        } else if (productProperty.getColumnType() == ColumnType.MULTIPLE) {

          List<String> propertyItemIds = JsonUtil.parseList(property.getText(), String.class);
          for (String propertyItemId : propertyItemIds) {
            CreateProductPropertyRelationVo createProductPropertyRelationVo = new CreateProductPropertyRelationVo();
            createProductPropertyRelationVo.setProductId(data.getId());
            createProductPropertyRelationVo.setPropertyId(productProperty.getId());
            createProductPropertyRelationVo.setPropertyItemId(propertyItemId);

            productPropertyRelationService.create(createProductPropertyRelationVo);
          }

        } else if (productProperty.getColumnType() == ColumnType.CUSTOM) {

          CreateProductPropertyRelationVo createProductPropertyRelationVo = new CreateProductPropertyRelationVo();
          createProductPropertyRelationVo.setProductId(data.getId());
          createProductPropertyRelationVo.setPropertyId(productProperty.getId());
          createProductPropertyRelationVo.setPropertyText(property.getText());
          productPropertyRelationService.create(createProductPropertyRelationVo);
        } else {
          throw new DefaultClientException("分类属性字段类型不存在！");
        }
      }
    }

    productPurchaseService.removeById(data.getId());

    if (vo.getPurchasePrice() != null) {

      UpdateProductPurchaseVo updateProductPurchaseVo = new UpdateProductPurchaseVo();
      updateProductPurchaseVo.setId(data.getId());
      updateProductPurchaseVo.setPrice(vo.getPurchasePrice());

      productPurchaseService.update(updateProductPurchaseVo);
    }

    productSaleService.removeById(data.getId());

    if (vo.getSalePrice() != null) {
      UpdateProductSaleVo updateProductSaleVo = new UpdateProductSaleVo();
      updateProductSaleVo.setId(data.getId());
      updateProductSaleVo.setPrice(vo.getSalePrice());

      productSaleService.update(updateProductSaleVo);
    }

    productRetailService.removeById(data.getId());
    if (vo.getRetailPrice() != null) {
      UpdateProductRetailVo updateProductRetailVo = new UpdateProductRetailVo();
      updateProductRetailVo.setId(data.getId());
      updateProductRetailVo.setPrice(vo.getRetailPrice());

      productRetailService.update(updateProductRetailVo);
    }

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
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

    Wrapper<ProductCode> deleteWrapper = Wrappers.lambdaQuery(ProductCode.class)
        .eq(ProductCode::getProductId, productId);
    if (productCodeService.count(deleteWrapper) > 0) {
      productCodeService.remove(deleteWrapper);
    }

    List<ProductCode> codes = new ArrayList<>();
    if (CollectionUtil.isNotEmpty(multiCodes)) {
      if (multiCodes.contains(product.getCode())) {
        multiCodes.remove(product.getCode());
      }
      for (String code : multiCodes) {
        ProductCode productCode = new ProductCode();
        productCode.setProductId(productId);
        productCode.setCode(code);
        codes.add(productCode);
      }
    }

    ProductCode code = new ProductCode();
    code.setProductId(productId);
    code.setCode(product.getCode());
    code.setIsMain(Boolean.TRUE);
    codes.add(code);

    productCodeService.saveBatch(codes);
  }

  @CacheEvict(value = Product.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
