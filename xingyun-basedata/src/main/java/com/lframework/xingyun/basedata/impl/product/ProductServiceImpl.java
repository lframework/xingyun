package com.lframework.xingyun.basedata.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.DefaultOpLogType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.system.RecursionMappingService;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.ProductProperty;
import com.lframework.xingyun.basedata.entity.ProductPropertyItem;
import com.lframework.xingyun.basedata.enums.ColumnType;
import com.lframework.xingyun.basedata.enums.ProductCategoryNodeType;
import com.lframework.xingyun.basedata.mappers.ProductMapper;
import com.lframework.xingyun.basedata.service.product.ProductPropertyItemService;
import com.lframework.xingyun.basedata.service.product.ProductPropertyRelationService;
import com.lframework.xingyun.basedata.service.product.ProductPropertyService;
import com.lframework.xingyun.basedata.service.product.ProductPurchaseService;
import com.lframework.xingyun.basedata.service.product.ProductRetailService;
import com.lframework.xingyun.basedata.service.product.ProductSaleService;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.basedata.vo.product.info.CreateProductVo;
import com.lframework.xingyun.basedata.vo.product.info.ProductPropertyRelationVo;
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
import java.util.ArrayList;
import java.util.Collection;
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
  public Integer queryCount(QueryProductVo vo) {

    return getBaseMapper().queryCount(vo);
  }

  @Cacheable(value = Product.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public Product findById(String id) {

    return getBaseMapper().selectById(id);
  }

  @Override
  public List<String> getIdNotInProductProperty(String propertyId) {

    return getBaseMapper().getIdNotInProductProperty(propertyId);
  }

  @Override
  public List<String> getIdByCategoryId(String categoryId) {

    return getBaseMapper().getIdByCategoryId(categoryId);
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "停用商品，ID：{}", params = "#ids", loopFormat = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void batchUnable(Collection<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    Wrapper<Product> updateWrapper = Wrappers.lambdaUpdate(Product.class)
        .set(Product::getAvailable, Boolean.FALSE).in(Product::getId, ids);
    getBaseMapper().update(updateWrapper);
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "启用商品，ID：{}", params = "#ids", loopFormat = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void batchEnable(Collection<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    Wrapper<Product> updateWrapper = Wrappers.lambdaUpdate(Product.class)
        .set(Product::getAvailable, Boolean.TRUE).in(Product::getId, ids);
    getBaseMapper().update(updateWrapper);
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "新增商品，ID：{}, 编号：{}", params = {"#_result",
      "#vo.code"}, autoSaveParams = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateProductVo vo) {

    Wrapper<Product> checkWrapper = Wrappers.lambdaQuery(Product.class)
        .eq(Product::getCode, vo.getCode());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(Product.class).eq(Product::getSkuCode, vo.getSkuCode());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("商品SKU编号重复，请重新输入！");
    }

    Product data = new Product();
    data.setId(IdUtil.getId());
    data.setCode(vo.getCode());
    data.setName(vo.getName());
    if (StringUtil.isNotBlank(vo.getShortName())) {
      data.setShortName(vo.getShortName());
    }
    data.setSkuCode(vo.getSkuCode());
    if (StringUtil.isNotBlank(vo.getExternalCode())) {
      data.setExternalCode(vo.getExternalCode());
    }

    data.setBrandId(vo.getBrandId());
    data.setCategoryId(vo.getCategoryId());

    if (StringUtil.isNotBlank(vo.getSpec())) {
      data.setSpec(vo.getSpec());
    }

    if (StringUtil.isNotBlank(vo.getUnit())) {
      data.setUnit(vo.getUnit());
    }

    data.setTaxRate(vo.getTaxRate());
    data.setSaleTaxRate(vo.getSaleTaxRate());

    data.setAvailable(Boolean.TRUE);

    getBaseMapper().insert(data);

    if (vo.getPurchasePrice() == null) {
      throw new DefaultClientException("采购价不能为空！");
    }

    if (vo.getPurchasePrice().doubleValue() < 0D) {
      throw new DefaultClientException("采购价不允许小于0！");
    }

    CreateProductPurchaseVo createProductPurchaseVo = new CreateProductPurchaseVo();
    createProductPurchaseVo.setId(data.getId());
    createProductPurchaseVo.setPrice(vo.getPurchasePrice());

    productPurchaseService.create(createProductPurchaseVo);

    if (vo.getSalePrice() == null) {
      throw new DefaultClientException("销售价不能为空！");
    }

    if (vo.getSalePrice().doubleValue() < 0D) {
      throw new DefaultClientException("销售价不允许小于0！");
    }

    CreateProductSaleVo createProductSaleVo = new CreateProductSaleVo();
    createProductSaleVo.setId(data.getId());
    createProductSaleVo.setPrice(vo.getSalePrice());

    productSaleService.create(createProductSaleVo);

    if (vo.getRetailPrice() == null) {
      throw new DefaultClientException("零售价不能为空！");
    }

    if (vo.getRetailPrice().doubleValue() < 0D) {
      throw new DefaultClientException("零售价不允许小于0！");
    }

    CreateProductRetailVo createProductRetailVo = new CreateProductRetailVo();
    createProductRetailVo.setId(data.getId());
    createProductRetailVo.setPrice(vo.getRetailPrice());

    productRetailService.create(createProductRetailVo);

    if (!CollectionUtil.isEmpty(vo.getProperties())) {
      // 商品和商品属性的关系
      for (ProductPropertyRelationVo property : vo.getProperties()) {
        ProductProperty productProperty = productPropertyService.findById(property.getId());
        if (productProperty == null) {
          throw new DefaultClientException("商品属性不存在！");
        }
        if (productProperty.getColumnType() == ColumnType.SINGLE) {
          ProductPropertyItem propertyItem = productPropertyItemService.findById(
              property.getText());
          if (propertyItem == null) {
            throw new DefaultClientException("商品属性值不存在！");
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
          throw new DefaultClientException("商品属性字段类型不存在！");
        }
      }
    }

    return data.getId();
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "修改商品，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateProductVo vo) {

    Product data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("商品不存在！");
    }

    Wrapper<Product> checkWrapper = Wrappers.lambdaQuery(Product.class)
        .eq(Product::getCode, vo.getCode()).ne(Product::getId, vo.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(Product.class).eq(Product::getSkuCode, vo.getSkuCode())
        .ne(Product::getId, vo.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("商品SKU编号重复，请重新输入！");
    }

    LambdaUpdateWrapper<Product> updateWrapper = Wrappers.lambdaUpdate(Product.class)
        .set(Product::getCode, vo.getCode()).set(Product::getName, vo.getName())
        .set(Product::getAvailable, vo.getAvailable()).set(Product::getSkuCode, vo.getSkuCode())
        .set(Product::getExternalCode,
            StringUtil.isBlank(vo.getExternalCode()) ? null : vo.getExternalCode())
        .set(Product::getSpec, StringUtil.isBlank(vo.getSpec()) ? null : vo.getSpec())
        .set(Product::getUnit, StringUtil.isBlank(vo.getUnit()) ? null : vo.getUnit())
        .set(Product::getShortName,
            StringUtil.isBlank(vo.getShortName()) ? null : vo.getShortName())
        .set(Product::getCategoryId,
            StringUtil.isBlank(vo.getCategoryId()) ? null : vo.getCategoryId())
        .set(Product::getBrandId, StringUtil.isBlank(vo.getBrandId()) ? null : vo.getBrandId())
        .set(Product::getTaxRate, vo.getTaxRate())
        .set(Product::getSaleTaxRate, vo.getSaleTaxRate())
        .eq(Product::getId, vo.getId());

    getBaseMapper().update(updateWrapper);

    productPropertyRelationService.deleteByProductId(data.getId());
    if (!CollectionUtil.isEmpty(vo.getProperties())) {
      // 商品和商品属性的关系
      for (ProductPropertyRelationVo property : vo.getProperties()) {
        ProductProperty productProperty = productPropertyService.findById(property.getId());
        if (productProperty == null) {
          throw new DefaultClientException("商品属性不存在！");
        }
        if (productProperty.getColumnType() == ColumnType.SINGLE) {
          ProductPropertyItem propertyItem = productPropertyItemService.findById(
              property.getText());
          if (propertyItem == null) {
            throw new DefaultClientException("商品属性值不存在！");
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
          throw new DefaultClientException("商品属性字段类型不存在！");
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
  public List<Product> getByCategoryIds(List<String> categoryIds) {

    if (CollectionUtil.isEmpty(categoryIds)) {
      return CollectionUtil.emptyList();
    }

    // 根据categoryIds查询所有叶子节点
    List<String> children = new ArrayList<>();
    for (String categoryId : categoryIds) {
      children.addAll(recursionMappingService.getNodeChildIds(categoryId,
          ApplicationUtil.getBean(ProductCategoryNodeType.class)));
    }

    children.addAll(categoryIds);

    children = children.stream().distinct().collect(Collectors.toList());

    List<Product> datas = getBaseMapper().getByCategoryIds(children);

    return datas;
  }

  @Override
  public List<Product> getByBrandIds(List<String> brandIds) {

    if (CollectionUtil.isEmpty(brandIds)) {
      return CollectionUtil.emptyList();
    }

    return getBaseMapper().getByBrandIds(brandIds);
  }

  @CacheEvict(value = Product.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
