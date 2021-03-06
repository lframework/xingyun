package com.lframework.xingyun.basedata.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.exceptions.impl.InputErrorException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.system.IRecursionMappingService;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.basedata.dto.product.property.ProductPropertyModelorDto;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.entity.ProductProperty;
import com.lframework.xingyun.basedata.enums.ColumnDataType;
import com.lframework.xingyun.basedata.enums.ColumnType;
import com.lframework.xingyun.basedata.enums.ProductCategoryNodeType;
import com.lframework.xingyun.basedata.enums.PropertyType;
import com.lframework.xingyun.basedata.mappers.ProductPropertyMapper;
import com.lframework.xingyun.basedata.service.product.IProductCategoryPropertyService;
import com.lframework.xingyun.basedata.service.product.IProductCategoryService;
import com.lframework.xingyun.basedata.service.product.IProductPolyPropertyService;
import com.lframework.xingyun.basedata.service.product.IProductPropertyService;
import com.lframework.xingyun.basedata.vo.product.property.CreateProductPropertyVo;
import com.lframework.xingyun.basedata.vo.product.property.QueryProductPropertyVo;
import com.lframework.xingyun.basedata.vo.product.property.UpdateProductPropertyVo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductPropertyServiceImpl extends
    BaseMpServiceImpl<ProductPropertyMapper, ProductProperty>
    implements IProductPropertyService {

  @Autowired
  private IProductPolyPropertyService productPolyPropertyService;

  @Autowired
  private IProductCategoryService productCategoryService;

  @Autowired
  private IRecursionMappingService recursionMappingService;

  @Autowired
  private IProductCategoryPropertyService productCategoryPropertyService;

  @Override
  public PageResult<ProductProperty> query(Integer pageIndex, Integer pageSize,
      QueryProductPropertyVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<ProductProperty> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<ProductProperty> query(QueryProductPropertyVo vo) {

    return getBaseMapper().query(vo);
  }

  @Cacheable(value = ProductProperty.CACHE_NAME, key = "#id", unless = "#result == null")
  @Override
  public ProductProperty findById(String id) {

    return getBaseMapper().selectById(id);
  }

  @OpLog(type = OpLogType.OTHER, name = "?????????????????????ID???{}", params = "#ids", loopFormat = true)
  @Transactional
  @Override
  public void batchUnable(Collection<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    Wrapper<ProductProperty> updateWrapper = Wrappers.lambdaUpdate(ProductProperty.class)
        .set(ProductProperty::getAvailable, Boolean.FALSE).in(ProductProperty::getId, ids);
    getBaseMapper().update(updateWrapper);
  }

  @OpLog(type = OpLogType.OTHER, name = "?????????????????????ID???{}", params = "#ids", loopFormat = true)
  @Transactional
  @Override
  public void batchEnable(Collection<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    Wrapper<ProductProperty> updateWrapper = Wrappers.lambdaUpdate(ProductProperty.class)
        .set(ProductProperty::getAvailable, Boolean.TRUE).in(ProductProperty::getId, ids);
    getBaseMapper().update(updateWrapper);
  }

  private List<String> calcCategoryIds(List<String> categoryIds) {

    if (CollectionUtil.isEmpty(categoryIds)) {
      return Collections.EMPTY_LIST;
    }

    //?????????categoryId?????????????????????????????????????????????????????????
    Set<String> childCategoryIds = new HashSet<>();

    for (String categoryId : categoryIds) {

      List<String> children = recursionMappingService.getNodeChildIds(categoryId,
          ApplicationUtil.getBean(ProductCategoryNodeType.class));
      if (!CollectionUtil.isEmpty(children)) {
        childCategoryIds.addAll(children);
      }
    }

    //??????????????????categoryId???childCategoryIds???????????????categoryId??????????????????????????????????????????????????????
    List<String> results = categoryIds.stream().filter(t -> !childCategoryIds.contains(t))
        .collect(Collectors.toList());

    return results;
  }

  @OpLog(type = OpLogType.OTHER, name = "?????????????????????ID???{}, ?????????{}", params = {"#id", "#code"})
  @Transactional
  @Override
  public String create(CreateProductPropertyVo vo) {

    Wrapper<ProductProperty> checkCodeWrapper = Wrappers.lambdaQuery(ProductProperty.class)
        .eq(ProductProperty::getCode, vo.getCode());
    if (getBaseMapper().selectCount(checkCodeWrapper) > 0) {
      throw new DefaultClientException("?????????????????????????????????");
    }

    Wrapper<ProductProperty> checkNameWrapper = Wrappers.lambdaQuery(ProductProperty.class)
        .eq(ProductProperty::getName, vo.getName());
    if (getBaseMapper().selectCount(checkNameWrapper) > 0) {
      throw new DefaultClientException("?????????????????????????????????");
    }

    if (vo.getPropertyType() == PropertyType.APPOINT.getCode().intValue()) {
      //?????????????????????
      if (CollectionUtil.isEmpty(vo.getCategoryIds())) {
        throw new InputErrorException("????????????????????????");
      }

      for (String categoryId : vo.getCategoryIds()) {
        ProductCategory productCategory = productCategoryService.findById(categoryId);
        if (productCategory == null) {
          throw new InputErrorException("???????????????????????????????????????");
        }
      }

      vo.setCategoryIds(this.calcCategoryIds(vo.getCategoryIds()));
    }

    ProductProperty data = new ProductProperty();
    data.setId(IdUtil.getId());
    data.setCode(vo.getCode());
    data.setName(vo.getName());
    data.setIsRequired(vo.getIsRequired());
    data.setColumnType(EnumUtil.getByCode(ColumnType.class, vo.getColumnType()));
    if (data.getColumnType() == ColumnType.CUSTOM) {
      if (vo.getColumnDataType() == null) {
        throw new InputErrorException("???????????????????????????");
      }

      data.setColumnDataType(EnumUtil.getByCode(ColumnDataType.class, vo.getColumnDataType()));
    }
    data.setPropertyType(EnumUtil.getByCode(PropertyType.class, vo.getPropertyType()));
    data.setAvailable(Boolean.TRUE);
    data.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    getBaseMapper().insert(data);

    if (data.getPropertyType() == PropertyType.APPOINT) {
      for (String categoryId : vo.getCategoryIds()) {
        productCategoryPropertyService.create(categoryId, data.getId());
      }
    }

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = OpLogType.OTHER, name = "?????????????????????ID???{}, ?????????{}", params = {"#id", "#code"})
  @Transactional
  @Override
  public void update(UpdateProductPropertyVo vo) {

    ProductProperty data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("??????????????????");
    }

    Wrapper<ProductProperty> checkWrapper = Wrappers.lambdaQuery(ProductProperty.class)
        .eq(ProductProperty::getCode, vo.getCode()).ne(ProductProperty::getId, vo.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("?????????????????????????????????");
    }

    Wrapper<ProductProperty> checkNameWrapper = Wrappers.lambdaQuery(ProductProperty.class)
        .eq(ProductProperty::getName, vo.getName()).ne(ProductProperty::getId, vo.getId());
    if (getBaseMapper().selectCount(checkNameWrapper) > 0) {
      throw new DefaultClientException("?????????????????????????????????");
    }

    //?????????????????????????????????????????????????????????????????????
    if (data.getColumnType() == ColumnType.CUSTOM) {
      if (vo.getColumnType() != ColumnType.CUSTOM.getCode().intValue()) {
        throw new InputErrorException("??????????????????????????????" + ColumnType.CUSTOM.getDesc() + "????????????????????????");
      }

      if (vo.getColumnDataType() == null) {
        throw new InputErrorException("????????????????????????");
      }

      if (data.getColumnDataType().getCode().intValue() != vo.getColumnDataType()) {
        throw new InputErrorException("??????????????????????????????");
      }

      if (vo.getPropertyType() != data.getPropertyType().getCode().intValue()) {
        throw new InputErrorException("???????????????????????????????????????");
      }
    }

    if (data.getColumnType() != ColumnType.CUSTOM
        && vo.getColumnType() == ColumnType.CUSTOM.getCode().intValue()) {
      //????????????????????????????????????
      throw new InputErrorException("?????????????????????????????????????????????" + ColumnType.CUSTOM.getDesc() + "??????");
    }

    if (data.getPropertyType() != PropertyType.NONE
        && vo.getPropertyType() == PropertyType.NONE.getCode()
        .intValue()) {
      throw new InputErrorException(
          "??????????????????????????????" + data.getPropertyType().getDesc() + "???????????????????????????"
              + PropertyType.NONE.getDesc() + "???");
    }

    // List<ProductCategoryProperty> oldProductCategoryPropertyList = new ArrayList<>();

    if (vo.getPropertyType() == PropertyType.APPOINT.getCode().intValue()) {
      //?????????????????????
      if (CollectionUtil.isEmpty(vo.getCategoryIds())) {
        throw new InputErrorException("????????????????????????");
      }

      for (String categoryId : vo.getCategoryIds()) {
        ProductCategory productCategory = productCategoryService.findById(categoryId);
        if (productCategory == null) {
          throw new InputErrorException("???????????????????????????????????????");
        }
      }

      vo.setCategoryIds(this.calcCategoryIds(vo.getCategoryIds()));
    }

    if (data.getPropertyType() == PropertyType.APPOINT) {
      //????????????
      // oldProductCategoryPropertyList = productCategoryPropertyService.getByPropertyId(data.getId());
      productCategoryPropertyService.deleteByPropertyId(data.getId());
    }

    LambdaUpdateWrapper<ProductProperty> updateWrapper = Wrappers.lambdaUpdate(
            ProductProperty.class)
        .set(ProductProperty::getCode, vo.getCode()).set(ProductProperty::getName, vo.getName())
        .set(ProductProperty::getIsRequired, vo.getIsRequired())
        .set(ProductProperty::getColumnType, vo.getColumnType())
        .set(ProductProperty::getPropertyType, vo.getPropertyType())
        .set(ProductProperty::getAvailable, vo.getAvailable()).set(ProductProperty::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .eq(ProductProperty::getId, vo.getId());
    if (vo.getColumnType() != ColumnType.CUSTOM.getCode().intValue()) {
      updateWrapper.set(ProductProperty::getColumnDataType, null);
    } else {
      updateWrapper.set(ProductProperty::getColumnDataType, vo.getColumnDataType());
    }

    if (vo.getPropertyType() == PropertyType.APPOINT.getCode().intValue()) {
      for (String categoryId : vo.getCategoryIds()) {
        productCategoryPropertyService.create(categoryId, data.getId());
      }
    }

    getBaseMapper().update(updateWrapper);

    /*if (vo.getPropertyType() != PropertyType.NONE.getCode().intValue()) {
      if (data.getColumnType() == ColumnType.MULTIPLE
          && vo.getColumnType() == ColumnType.SINGLE.getCode()
          .intValue()) {
        //????????????????????????
        productPolyPropertyService.setMultipleToSimple(data.getId());
      }

      if (data.getPropertyType() == PropertyType.COMMON
          && vo.getPropertyType() == PropertyType.APPOINT.getCode()
          .intValue()) {
        //???????????????????????????
        productPolyPropertyService.setCommonToAppoint(data.getId());

      } else if (data.getPropertyType() == PropertyType.APPOINT
          && vo.getPropertyType() == PropertyType.COMMON.getCode().intValue()) {
        //???????????????????????????
        productPolyPropertyService.setAppointToCommon(data.getId());
      } else if (data.getPropertyType() == PropertyType.APPOINT
          && vo.getPropertyType() == PropertyType.APPOINT.getCode().intValue()) {
        List<String> oldCategoryIds = oldProductCategoryPropertyList.stream()
            .map(ProductCategoryProperty::getCategoryId).collect(Collectors.toList());

        boolean isUpdateCategory = CollectionUtil.isEqualList(oldCategoryIds, vo.getCategoryIds());
        if (isUpdateCategory) {
          //???????????????ID
          productPolyPropertyService.updateAppointCategoryId(data.getId());
        }
      }
    }*/

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);
  }

  @Override
  public List<ProductPropertyModelorDto> getModelorByCategoryId(String categoryId) {

    List<String> parentCategoryIds = recursionMappingService.getNodeParentIds(categoryId,
        ApplicationUtil.getBean(ProductCategoryNodeType.class));
    List<String> categoryIds = new ArrayList<>(parentCategoryIds);
    categoryIds.add(categoryId);
    List<ProductPropertyModelorDto> datas = getBaseMapper().getModelorByCategoryId(categoryIds);
    return datas;
  }

  @CacheEvict(value = ProductProperty.CACHE_NAME, key = "#key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
