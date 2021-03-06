package com.lframework.xingyun.basedata.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.service.system.IRecursionMappingService;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.enums.ProductCategoryNodeType;
import com.lframework.xingyun.basedata.mappers.ProductCategoryMapper;
import com.lframework.xingyun.basedata.service.product.IProductCategoryService;
import com.lframework.xingyun.basedata.vo.product.category.CreateProductCategoryVo;
import com.lframework.xingyun.basedata.vo.product.category.QueryProductCategorySelectorVo;
import com.lframework.xingyun.basedata.vo.product.category.UpdateProductCategoryVo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductCategoryServiceImpl extends
    BaseMpServiceImpl<ProductCategoryMapper, ProductCategory>
    implements IProductCategoryService {

  @Autowired
  private IRecursionMappingService recursionMappingService;

  @Override
  public List<ProductCategory> getAllProductCategories() {

    return getBaseMapper().getAllProductCategories();
  }

  @Cacheable(value = ProductCategory.CACHE_NAME, key = "#id", unless = "#result == null")
  @Override
  public ProductCategory findById(String id) {

    return getBaseMapper().selectById(id);
  }

  @Override
  public List<ProductCategory> selector(QueryProductCategorySelectorVo vo) {

    return getBaseMapper().selector(vo);
  }

  @OpLog(type = OpLogType.OTHER, name = "?????????????????????ID???{}", params = "#ids", loopFormat = true)
  @Transactional
  @Override
  public void batchUnable(Collection<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    List<String> batchIds = new ArrayList<>();
    for (String id : ids) {
      List<String> nodeChildIds = recursionMappingService.getNodeChildIds(id,
          ApplicationUtil.getBean(ProductCategoryNodeType.class));
      if (CollectionUtil.isEmpty(nodeChildIds)) {
        continue;
      }

      batchIds.addAll(nodeChildIds);
    }

    batchIds.addAll(ids);

    Wrapper<ProductCategory> updateWrapper = Wrappers.lambdaUpdate(ProductCategory.class)
        .set(ProductCategory::getAvailable, Boolean.FALSE).in(ProductCategory::getId, batchIds);
    getBaseMapper().update(updateWrapper);
  }

  @OpLog(type = OpLogType.OTHER, name = "?????????????????????ID???{}", params = "#ids", loopFormat = true)
  @Transactional
  @Override
  public void batchEnable(Collection<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    List<String> batchIds = new ArrayList<>();
    for (String id : ids) {
      List<String> nodeParentIds = recursionMappingService.getNodeParentIds(id,
          ApplicationUtil.getBean(ProductCategoryNodeType.class));
      if (CollectionUtil.isEmpty(nodeParentIds)) {
        continue;
      }

      batchIds.addAll(nodeParentIds);
    }

    batchIds.addAll(ids);

    Wrapper<ProductCategory> updateWrapper = Wrappers.lambdaUpdate(ProductCategory.class)
        .set(ProductCategory::getAvailable, Boolean.TRUE).in(ProductCategory::getId, batchIds);
    getBaseMapper().update(updateWrapper);
  }

  @OpLog(type = OpLogType.OTHER, name = "?????????????????????ID???{}, ?????????{}", params = {"#id", "#code"})
  @Transactional
  @Override
  public String create(CreateProductCategoryVo vo) {

    //??????Code????????????
    Wrapper<ProductCategory> checkCodeWrapper = Wrappers.lambdaQuery(ProductCategory.class)
        .eq(ProductCategory::getCode, vo.getCode());
    if (getBaseMapper().selectCount(checkCodeWrapper) > 0) {
      throw new DefaultClientException("?????????????????????????????????");
    }

    //??????Name????????????
    Wrapper<ProductCategory> checkNameWrapper = Wrappers.lambdaQuery(ProductCategory.class)
        .eq(ProductCategory::getName, vo.getName());
    if (getBaseMapper().selectCount(checkNameWrapper) > 0) {
      throw new DefaultClientException("?????????????????????????????????");
    }

    //??????parentId??????????????????????????????????????????
    if (!StringUtil.isBlank(vo.getParentId())) {
      Wrapper<ProductCategory> checkParentWrapper = Wrappers.lambdaQuery(ProductCategory.class)
          .eq(ProductCategory::getId, vo.getParentId());
      if (getBaseMapper().selectCount(checkParentWrapper) == 0) {
        throw new DefaultClientException("????????????????????????????????????");
      }
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

    this.saveRecursion(data.getId(), data.getParentId());

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = OpLogType.OTHER, name = "?????????????????????ID???{}, ?????????{}", params = {"#id", "#code"})
  @Transactional
  @Override
  public void update(UpdateProductCategoryVo vo) {

    ProductCategory data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("??????????????????");
    }

    //??????Code????????????
    Wrapper<ProductCategory> checkCodeWrapper = Wrappers.lambdaQuery(ProductCategory.class)
        .eq(ProductCategory::getCode, vo.getCode()).ne(ProductCategory::getId, data.getId());
    if (getBaseMapper().selectCount(checkCodeWrapper) > 0) {
      throw new DefaultClientException("?????????????????????????????????");
    }

    //??????Name????????????
    Wrapper<ProductCategory> checkNameWrapper = Wrappers.lambdaQuery(ProductCategory.class)
        .eq(ProductCategory::getName, vo.getName()).ne(ProductCategory::getId, data.getId());
    if (getBaseMapper().selectCount(checkNameWrapper) > 0) {
      throw new DefaultClientException("?????????????????????????????????");
    }

    Wrapper<ProductCategory> updateWrapper = Wrappers.lambdaUpdate(ProductCategory.class)
        .set(ProductCategory::getCode, vo.getCode()).set(ProductCategory::getName, vo.getName())
        .set(ProductCategory::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .set(ProductCategory::getAvailable, vo.getAvailable())
        .eq(ProductCategory::getId, data.getId());

    getBaseMapper().update(updateWrapper);

    if (!vo.getAvailable()) {
      if (data.getAvailable()) {
        //??????????????? ?????????????????????
        List<String> childrenIds = recursionMappingService.getNodeChildIds(data.getId(),
            ApplicationUtil.getBean(ProductCategoryNodeType.class));
        if (!CollectionUtil.isEmpty(childrenIds)) {
          this.batchUnable(childrenIds);
        }
      }
    } else {
      if (!data.getAvailable()) {
        //??????????????? ?????????????????????
        List<String> parentIds = recursionMappingService.getNodeParentIds(data.getId(),
            ApplicationUtil.getBean(ProductCategoryNodeType.class));
        if (!CollectionUtil.isEmpty(parentIds)) {
          this.batchEnable(parentIds);
        }
      }
    }

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);
  }

  /**
   * ??????????????????
   *
   * @param categoryId
   * @param parentId
   */
  @Override
  public void saveRecursion(String categoryId, String parentId) {

    if (!StringUtil.isBlank(parentId)) {
      List<String> parentIds = recursionMappingService.getNodeParentIds(parentId,
          ApplicationUtil.getBean(ProductCategoryNodeType.class));
      if (CollectionUtil.isEmpty(parentIds)) {
        parentIds = new ArrayList<>();
      }
      parentIds.add(parentId);

      recursionMappingService.saveNode(categoryId,
          ApplicationUtil.getBean(ProductCategoryNodeType.class),
          parentIds);
    } else {
      recursionMappingService.saveNode(categoryId,
          ApplicationUtil.getBean(ProductCategoryNodeType.class));
    }
  }

  @CacheEvict(value = ProductCategory.CACHE_NAME, key = "#key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
