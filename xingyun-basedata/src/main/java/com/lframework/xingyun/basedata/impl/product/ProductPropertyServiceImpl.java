package com.lframework.xingyun.basedata.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.exceptions.impl.InputErrorException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.xingyun.core.annotations.OpLog;
import com.lframework.xingyun.basedata.enums.BaseDataOpLogType;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.xingyun.core.service.RecursionMappingService;
import com.lframework.xingyun.core.utils.OpLogUtil;
import com.lframework.starter.web.utils.PageHelperUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.basedata.dto.product.property.ProductPropertyModelorDto;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.entity.ProductCategoryProperty;
import com.lframework.xingyun.basedata.entity.ProductProperty;
import com.lframework.xingyun.basedata.enums.ColumnDataType;
import com.lframework.xingyun.basedata.enums.ColumnType;
import com.lframework.xingyun.basedata.enums.ProductCategoryNodeType;
import com.lframework.xingyun.basedata.enums.PropertyType;
import com.lframework.xingyun.basedata.mappers.ProductPropertyMapper;
import com.lframework.xingyun.basedata.service.product.ProductCategoryPropertyService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryService;
import com.lframework.xingyun.basedata.service.product.ProductPropertyRelationService;
import com.lframework.xingyun.basedata.service.product.ProductPropertyService;
import com.lframework.xingyun.basedata.vo.product.property.CreateProductPropertyVo;
import com.lframework.xingyun.basedata.vo.product.property.QueryProductPropertyVo;
import com.lframework.xingyun.basedata.vo.product.property.UpdateProductPropertyVo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
    implements ProductPropertyService {

  @Autowired
  private ProductCategoryService productCategoryService;

  @Autowired
  private RecursionMappingService recursionMappingService;

  @Autowired
  private ProductCategoryPropertyService productCategoryPropertyService;

  @Autowired
  private ProductPropertyRelationService productPropertyRelationService;

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

  @Cacheable(value = ProductProperty.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public ProductProperty findById(String id) {

    return getBaseMapper().selectById(id);
  }

  @OpLog(type = BaseDataOpLogType.BASE_DATA, name = "停用商品属性，ID：{}", params = "#ids", loopFormat = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void batchUnable(Collection<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    Wrapper<ProductProperty> updateWrapper = Wrappers.lambdaUpdate(ProductProperty.class)
        .set(ProductProperty::getAvailable, Boolean.FALSE).in(ProductProperty::getId, ids);
    getBaseMapper().update(updateWrapper);
  }

  @OpLog(type = BaseDataOpLogType.BASE_DATA, name = "启用商品属性，ID：{}", params = "#ids", loopFormat = true)
  @Transactional(rollbackFor = Exception.class)
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
      return CollectionUtil.emptyList();
    }

    //先整理categoryId，因为可能父级分类和子级分类全传过来了
    Set<String> childCategoryIds = new HashSet<>();

    for (String categoryId : categoryIds) {

      List<String> children = recursionMappingService.getNodeChildIds(categoryId,
          ApplicationUtil.getBean(ProductCategoryNodeType.class));
      if (!CollectionUtil.isEmpty(children)) {
        childCategoryIds.addAll(children);
      }
    }

    //如果传过来的categoryId在childCategoryIds中，则表示categoryId和他的父级节点一起传过来了，需要去除
    List<String> results = categoryIds.stream().filter(t -> !childCategoryIds.contains(t))
        .collect(Collectors.toList());

    return results;
  }

  @OpLog(type = BaseDataOpLogType.BASE_DATA, name = "新增商品属性，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateProductPropertyVo vo) {

    Wrapper<ProductProperty> checkCodeWrapper = Wrappers.lambdaQuery(ProductProperty.class)
        .eq(ProductProperty::getCode, vo.getCode());
    if (getBaseMapper().selectCount(checkCodeWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    Wrapper<ProductProperty> checkNameWrapper = Wrappers.lambdaQuery(ProductProperty.class)
        .eq(ProductProperty::getName, vo.getName());
    if (getBaseMapper().selectCount(checkNameWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    if (vo.getPropertyType() == PropertyType.APPOINT.getCode().intValue()) {
      //如果是指定分类
      if (CollectionUtil.isEmpty(vo.getCategoryIds())) {
        throw new InputErrorException("请选择商品分类！");
      }

      for (String categoryId : vo.getCategoryIds()) {
        ProductCategory productCategory = productCategoryService.findById(categoryId);
        if (productCategory == null) {
          throw new InputErrorException("商品分类数据有误，请检查！");
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
        throw new InputErrorException("数据类型不能为空！");
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

  @OpLog(type = BaseDataOpLogType.BASE_DATA, name = "修改商品属性，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateProductPropertyVo vo) {

    ProductProperty data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("属性不存在！");
    }

    Wrapper<ProductProperty> checkWrapper = Wrappers.lambdaQuery(ProductProperty.class)
        .eq(ProductProperty::getCode, vo.getCode()).ne(ProductProperty::getId, vo.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    Wrapper<ProductProperty> checkNameWrapper = Wrappers.lambdaQuery(ProductProperty.class)
        .eq(ProductProperty::getName, vo.getName()).ne(ProductProperty::getId, vo.getId());
    if (getBaseMapper().selectCount(checkNameWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    //如果字段类型是手动录入，那么不允许修改字段类型
    if (data.getColumnType() == ColumnType.CUSTOM) {
      if (vo.getColumnType() != ColumnType.CUSTOM.getCode().intValue()) {
        throw new InputErrorException("该属性的字段类型为“" + ColumnType.CUSTOM.getDesc() + "”，不允许修改！");
      }

      if (vo.getColumnDataType() == null) {
        throw new InputErrorException("请选择数据类型！");
      }

      if (data.getColumnDataType().getCode().intValue() != vo.getColumnDataType()) {
        throw new InputErrorException("数据类型不允许修改！");
      }

      if (vo.getPropertyType() != data.getPropertyType().getCode().intValue()) {
        throw new InputErrorException("该属性不允许修改属性类别！");
      }
    }

    if (data.getColumnType() != ColumnType.CUSTOM
        && vo.getColumnType() == ColumnType.CUSTOM.getCode().intValue()) {
      //从其他类型更改为手动录入
      throw new InputErrorException("该属性不允许将字段类型修改为“" + ColumnType.CUSTOM.getDesc() + "”！");
    }

    if (data.getPropertyType() != PropertyType.NONE
        && vo.getPropertyType() == PropertyType.NONE.getCode()
        .intValue()) {
      throw new InputErrorException(
          "该属性的属性类别为“" + data.getPropertyType().getDesc() + "”，不允许修改为“"
              + PropertyType.NONE.getDesc() + "”");
    }

    List<ProductCategoryProperty> oldProductCategoryPropertyList = new ArrayList<>();

    if (vo.getPropertyType() == PropertyType.APPOINT.getCode().intValue()) {
      //如果是指定分类
      if (CollectionUtil.isEmpty(vo.getCategoryIds())) {
        throw new InputErrorException("请选择商品分类！");
      }

      for (String categoryId : vo.getCategoryIds()) {
        ProductCategory productCategory = productCategoryService.findById(categoryId);
        if (productCategory == null) {
          throw new InputErrorException("商品分类数据有误，请检查！");
        }
      }

      vo.setCategoryIds(this.calcCategoryIds(vo.getCategoryIds()));
    }

    if (data.getPropertyType() == PropertyType.APPOINT) {
      //删除关系
      oldProductCategoryPropertyList = productCategoryPropertyService.getByPropertyId(data.getId());
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

    if (vo.getPropertyType() != PropertyType.NONE.getCode().intValue()) {
      if (data.getColumnType() == ColumnType.MULTIPLE
          && vo.getColumnType() == ColumnType.SINGLE.getCode()
          .intValue()) {
        //从多选更改为单选
        productPropertyRelationService.setMultipleToSimple(data.getId());
      }

      if (data.getPropertyType() == PropertyType.COMMON
          && vo.getPropertyType() == PropertyType.APPOINT.getCode()
          .intValue()) {
        //从通用改成指定分类
        productPropertyRelationService.setCommonToAppoint(data.getId());

      } else if (data.getPropertyType() == PropertyType.APPOINT
          && vo.getPropertyType() == PropertyType.COMMON.getCode().intValue()) {
        //从指定分类改成通用
        productPropertyRelationService.setAppointToCommon(data.getId());
      } else if (data.getPropertyType() == PropertyType.APPOINT
          && vo.getPropertyType() == PropertyType.APPOINT.getCode().intValue()) {
        List<String> oldCategoryIds = oldProductCategoryPropertyList.stream()
            .map(ProductCategoryProperty::getCategoryId).collect(Collectors.toList());

        boolean isUpdateCategory = CollectionUtil.isEqualList(oldCategoryIds, vo.getCategoryIds());
        if (isUpdateCategory) {
          //更改了分类ID
          productPropertyRelationService.updateAppointCategoryId(data.getId());
        }
      }
    }

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

  @CacheEvict(value = ProductProperty.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
