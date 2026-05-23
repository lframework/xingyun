package com.lframework.xingyun.basedata.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.annotations.oplog.OpLog;
import com.lframework.starter.web.core.event.DataChangeEventBuilder;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.starter.web.core.utils.OpLogUtil;
import com.lframework.starter.web.inner.service.RecursionMappingService;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.entity.ProductCategoryPropertyRelation;
import com.lframework.xingyun.basedata.entity.ProductCategorySalePropertyRelation;
import com.lframework.xingyun.basedata.enums.BaseDataOpLogType;
import com.lframework.xingyun.basedata.enums.ProductCategoryNodeType;
import com.lframework.xingyun.basedata.events.DeleteProductCategoryEvent;
import com.lframework.xingyun.basedata.mappers.ProductCategoryMapper;
import com.lframework.xingyun.basedata.mappers.ProductCategoryPropertyRelationMapper;
import com.lframework.xingyun.basedata.mappers.ProductCategorySalePropertyRelationMapper;
import com.lframework.xingyun.basedata.service.product.ProductCategoryService;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.basedata.vo.product.category.CreateProductCategoryVo;
import com.lframework.xingyun.basedata.vo.product.category.QueryProductCategorySelectorVo;
import com.lframework.xingyun.basedata.vo.product.category.UpdateProductCategoryVo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductCategoryServiceImpl extends
    BaseMpServiceImpl<ProductCategoryMapper, ProductCategory>
    implements ProductCategoryService {

  @Autowired
  private RecursionMappingService recursionMappingService;

  @Autowired
  private ProductService productService;

  @Autowired
  private ProductCategoryPropertyRelationMapper ProductCategoryPropertyRelationMapper;

  @Autowired
  private ProductCategorySalePropertyRelationMapper ProductCategorySalePropertyRelationMapper;

  @Override
  public List<ProductCategory> getAllProductCategories() {

    return getBaseMapper().getAllProductCategories();
  }

  @Cacheable(value = ProductCategory.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public ProductCategory findById(String id) {

    return getBaseMapper().selectById(id);
  }

  @Override
  public List<ProductCategory> selector(QueryProductCategorySelectorVo vo) {

    return getBaseMapper().selector(vo);
  }

  @OpLog(type = BaseDataOpLogType.class, name = "删除商品分类，ID：{}", params = "#id")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {

    List<String> batchIds = new ArrayList<>();
    batchIds.add(id);
    List<String> nodeChildIds = recursionMappingService.getNodeChildIds(id,
        ProductCategoryNodeType.class);
    if (CollectionUtil.isNotEmpty(nodeChildIds)) {
      batchIds.addAll(nodeChildIds);
    }

    Wrapper<ProductCategory> updateWrapper = Wrappers.lambdaUpdate(ProductCategory.class)
        .set(ProductCategory::getAvailable, Boolean.FALSE).in(ProductCategory::getId, batchIds);
    getBaseMapper().update(updateWrapper);

    for (String categoryId : batchIds) {
      ProductCategory category = this.findById(categoryId);

      DataChangeEventBuilder.publishLogicDelete(this, DeleteProductCategoryEvent.class, category);
    }
  }

  @OpLog(type = BaseDataOpLogType.class, name = "新增商品分类，ID：{}, 编号：{}", params = {"#id",
      "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateProductCategoryVo vo) {

    //查询Code是否重复
    Wrapper<ProductCategory> checkCodeWrapper = Wrappers.lambdaQuery(ProductCategory.class)
        .eq(ProductCategory::getCode, vo.getCode()).eq(ProductCategory::getAvailable, Boolean.TRUE);
    if (getBaseMapper().selectCount(checkCodeWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    //查询Name是否重复
    Wrapper<ProductCategory> checkNameWrapper = Wrappers.lambdaQuery(ProductCategory.class)
        .eq(ProductCategory::getName, vo.getName()).eq(ProductCategory::getAvailable, Boolean.TRUE);
    if (getBaseMapper().selectCount(checkNameWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    //如果parentId不为空，查询上级分类是否存在
    if (!StringUtil.isBlank(vo.getParentId())) {
      this.checkAllowCreateChild(vo.getParentId());
    }

    ProductCategory data = new ProductCategory();
    data.setId(IdUtil.getId());
    data.setCode(vo.getCode());
    data.setName(vo.getName());
    if (!StringUtil.isBlank(vo.getParentId())) {
      data.setParentId(vo.getParentId());
    }
    data.setAvailable(Boolean.TRUE);
    data.setDescription(vo.getDescription());

    getBaseMapper().insert(data);

    this.saveRecursion(true, data.getId(), data.getParentId());

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = BaseDataOpLogType.class, name = "修改商品分类，ID：{}, 编号：{}", params = {"#id",
      "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateProductCategoryVo vo) {

    ProductCategory data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("分类不存在！");
    }

    //查询Code是否重复
    Wrapper<ProductCategory> checkCodeWrapper = Wrappers.lambdaQuery(ProductCategory.class)
        .eq(ProductCategory::getCode, vo.getCode()).eq(ProductCategory::getAvailable, Boolean.TRUE)
        .ne(ProductCategory::getId, data.getId());
    if (getBaseMapper().selectCount(checkCodeWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    //查询Name是否重复
    Wrapper<ProductCategory> checkNameWrapper = Wrappers.lambdaQuery(ProductCategory.class)
        .eq(ProductCategory::getName, vo.getName()).eq(ProductCategory::getAvailable, Boolean.TRUE)
        .ne(ProductCategory::getId, data.getId());
    if (getBaseMapper().selectCount(checkNameWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    Wrapper<ProductCategory> updateWrapper = Wrappers.lambdaUpdate(ProductCategory.class)
        .set(ProductCategory::getCode, vo.getCode()).set(ProductCategory::getName, vo.getName())
        .set(ProductCategory::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .eq(ProductCategory::getId, data.getId());

    getBaseMapper().update(updateWrapper);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);
  }

  @Override
  public void checkAllowCreateChild(String parentId) {

    if (StringUtil.isBlank(parentId)) {
      return;
    }

    Wrapper<ProductCategory> checkParentWrapper = Wrappers.lambdaQuery(ProductCategory.class)
        .eq(ProductCategory::getId, parentId)
        .eq(ProductCategory::getAvailable, Boolean.TRUE);
    if (getBaseMapper().selectCount(checkParentWrapper) == 0) {
      throw new DefaultClientException("上级分类不存在，请检查！");
    }

    // 然后判断上级分类下是否有商品，如果有商品不允许新增子分类
    Wrapper<Product> checkProductWrapper = Wrappers.lambdaQuery(Product.class)
        .eq(Product::getCategoryId, parentId)
        .eq(Product::getAvailable, Boolean.TRUE);
    if (productService.count(checkProductWrapper) > 0) {
      throw new DefaultClientException("上级分类已关联商品，不允许新增子分类！");
    }

    Wrapper<ProductCategoryPropertyRelation> checkPropertyWrapper = Wrappers.lambdaQuery(
            ProductCategoryPropertyRelation.class)
        .eq(ProductCategoryPropertyRelation::getCategoryId, parentId);
    if (ProductCategoryPropertyRelationMapper.selectCount(checkPropertyWrapper) > 0) {
      throw new DefaultClientException("上级分类已配置商品分类属性，不允许新增子分类！");
    }

    Wrapper<ProductCategorySalePropertyRelation> checkSalePropertyWrapper = Wrappers.lambdaQuery(
            ProductCategorySalePropertyRelation.class)
        .eq(ProductCategorySalePropertyRelation::getCategoryId, parentId);
    if (ProductCategorySalePropertyRelationMapper.selectCount(checkSalePropertyWrapper) > 0) {
      throw new DefaultClientException("上级分类已配置商品销售属性，不允许新增子分类！");
    }
  }

  /**
   * 保存递归信息
   *
   * @param categoryId
   * @param parentId
   */
  @Override
  public void saveRecursion(Boolean isCreate, String categoryId, String parentId) {

    if (!isCreate) {
      recursionMappingService.deleteNode(categoryId, ProductCategoryNodeType.class);
    }

    if (!StringUtil.isBlank(parentId)) {
      List<String> parentIds = recursionMappingService.getNodeParentIds(parentId,
          ProductCategoryNodeType.class);
      if (CollectionUtil.isEmpty(parentIds)) {
        parentIds = new ArrayList<>();
      }
      parentIds.add(parentId);

      recursionMappingService.saveNode(categoryId, ProductCategoryNodeType.class,
          parentIds);
    } else {
      recursionMappingService.saveNode(categoryId, ProductCategoryNodeType.class);
    }

    // 还要更新这个节点的子节点
    List<String> childIds = recursionMappingService.getNodeChildIds(categoryId,
        ProductCategoryNodeType.class);

    for (String childId : childIds) {
      List<ProductCategory> parentDeptList = new ArrayList<>();
      ProductCategory productCategory = this.findById(childId);

      while (StringUtil.isNotBlank(productCategory.getParentId())) {
        productCategory = this.findById(productCategory.getParentId());
        if (productCategory == null) {
          break;
        }
        parentDeptList.add(productCategory);
      }

      parentDeptList = CollectionUtil.reverse(parentDeptList);
      recursionMappingService.deleteNode(childId, ProductCategoryNodeType.class);
      recursionMappingService.saveNode(childId, ProductCategoryNodeType.class,
          parentDeptList.stream().map(ProductCategory::getId).collect(Collectors.toList()));
    }
  }

  @CacheEvict(value = ProductCategory.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
