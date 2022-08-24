package com.lframework.xingyun.basedata.biz.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.basedata.biz.mappers.ProductPolyPropertyMapper;
import com.lframework.xingyun.basedata.biz.service.product.IProductCategoryPropertyService;
import com.lframework.xingyun.basedata.biz.service.product.IProductPolyPropertyService;
import com.lframework.xingyun.basedata.biz.service.product.IProductPolyService;
import com.lframework.xingyun.basedata.biz.service.product.IProductPropertyItemService;
import com.lframework.xingyun.basedata.biz.service.product.IProductPropertyService;
import com.lframework.xingyun.basedata.facade.dto.product.poly.ProductPolyPropertyDto;
import com.lframework.xingyun.basedata.facade.entity.ProductCategoryProperty;
import com.lframework.xingyun.basedata.facade.entity.ProductPolyProperty;
import com.lframework.xingyun.basedata.facade.entity.ProductProperty;
import com.lframework.xingyun.basedata.facade.entity.ProductPropertyItem;
import com.lframework.xingyun.basedata.facade.enums.ColumnType;
import com.lframework.xingyun.basedata.facade.vo.product.poly.property.CreateProductPolyPropertyVo;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductPolyPropertyServiceImpl extends
    BaseMpServiceImpl<ProductPolyPropertyMapper, ProductPolyProperty>
    implements IProductPolyPropertyService {

  @Autowired
  private IProductPropertyService productPropertyService;

  @Autowired
  private IProductPropertyItemService productPropertyItemService;

  @Autowired
  private IProductPolyService productPolyService;

  @Autowired
  private IProductCategoryPropertyService productCategoryPropertyService;

  @Cacheable(value = ProductPolyPropertyDto.CACHE_NAME, key = "#polyId", unless = "#result == null or #result.size() == 0")
  @Override
  public List<ProductPolyPropertyDto> getByPolyId(String polyId) {

    return getBaseMapper().getByPolyId(polyId);
  }

  @Transactional
  @Override
  public void setMultipleToSimple(String propertyId) {

    List<ProductPolyPropertyDto> datas = getBaseMapper().getByPropertyId(propertyId);
    if (!CollectionUtil.isEmpty(datas)) {

      Set<ProductPolyPropertyDto> checkSet = new HashSet<>();
      for (ProductPolyPropertyDto data : datas) {
        if (checkSet.stream()
            .anyMatch(t -> t.getPolyId().equals(data.getPolyId()) && t.getPropertyId()
                .equals(data.getPropertyId()))) {
          getBaseMapper().deleteById(data.getId());
        } else {
          checkSet.add(data);
        }
      }
    }
  }

  @Transactional
  @Override
  public void setCommonToAppoint(String propertyId) {

    List<ProductCategoryProperty> categoryList = productCategoryPropertyService.getByPropertyId(
        propertyId);
    for (ProductCategoryProperty productCategoryPropertyDto : categoryList) {
      getBaseMapper().setCommonToAppoint(propertyId, productCategoryPropertyDto.getCategoryId());
    }
  }

  @Transactional
  @Override
  public void setAppointToCommon(String propertyId) {

    List<ProductPropertyItem> propertyItems = productPropertyItemService.getByPropertyId(
        propertyId);
    if (!CollectionUtil.isEmpty(propertyItems)) {
      ProductPropertyItem propertyItem = propertyItems.get(0);

      List<String> polyIds = productPolyService.getIdNotInPolyProperty(propertyId);
      if (!CollectionUtil.isEmpty(polyIds)) {
        for (String polyId : polyIds) {
          ProductPolyProperty data = new ProductPolyProperty();
          data.setId(IdUtil.getId());
          data.setPolyId(polyId);
          data.setPropertyId(propertyItem.getPropertyId());
          data.setPropertyItemId(propertyItem.getId());

          getBaseMapper().insert(data);
        }
      }
    }
  }

  @Transactional
  @Override
  public void updateAppointCategoryId(String propertyId) {

    List<ProductPropertyItem> propertyItems = productPropertyItemService.getByPropertyId(
        propertyId);
    if (!CollectionUtil.isEmpty(propertyItems)) {
      ProductPropertyItem propertyItem = propertyItems.get(0);

      Wrapper<ProductPolyProperty> deleteWrapper = Wrappers.lambdaQuery(ProductPolyProperty.class)
          .eq(ProductPolyProperty::getPropertyId, propertyId);
      getBaseMapper().delete(deleteWrapper);

      List<ProductCategoryProperty> categoryList = productCategoryPropertyService.getByPropertyId(
          propertyId);
      for (ProductCategoryProperty productCategoryPropertyDto : categoryList) {
        List<String> polyIds = productPolyService.getIdByCategoryId(
            productCategoryPropertyDto.getCategoryId());
        if (!CollectionUtil.isEmpty(polyIds)) {
          for (String polyId : polyIds) {
            ProductPolyProperty data = new ProductPolyProperty();
            data.setId(IdUtil.getId());
            data.setPolyId(polyId);
            data.setPropertyId(propertyItem.getPropertyId());
            data.setPropertyItemId(propertyItem.getId());

            getBaseMapper().insert(data);
          }
        }
      }
    }
  }

  @Transactional
  @Override
  public String create(CreateProductPolyPropertyVo vo) {

    ProductProperty productProperty = productPropertyService.findById(vo.getPropertyId());
    if (productProperty == null) {
      throw new DefaultClientException("商品属性不存在！");
    }

    ProductPolyProperty data = new ProductPolyProperty();
    data.setId(IdUtil.getId());
    data.setPolyId(vo.getPolyId());
    data.setPropertyId(productProperty.getId());

    if (productProperty.getColumnType() != ColumnType.CUSTOM) {
      ProductPropertyItem propertyItem = productPropertyItemService.findById(
          vo.getPropertyItemId());

      if (propertyItem == null) {
        throw new DefaultClientException("属性值不存在！");
      }
      Assert.isTrue(propertyItem.getPropertyId().equals(productProperty.getId()));

      data.setPropertyItemId(propertyItem.getId());

    } else {
      if (StringUtil.isBlank(vo.getPropertyText())) {
        throw new DefaultClientException("属性值文本不存在！");
      }

      data.setPropertyText(vo.getPropertyText());
    }

    getBaseMapper().insert(data);

    return data.getId();
  }

  @Transactional
  @Override
  public void deleteByPolyId(String polyId) {
    Wrapper<ProductPolyProperty> deleteWrapper = Wrappers.lambdaQuery(ProductPolyProperty.class)
        .eq(ProductPolyProperty::getPolyId, polyId);
    getBaseMapper().delete(deleteWrapper);
  }

  @CacheEvict(value = ProductPolyPropertyDto.CACHE_NAME, key = "#key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
