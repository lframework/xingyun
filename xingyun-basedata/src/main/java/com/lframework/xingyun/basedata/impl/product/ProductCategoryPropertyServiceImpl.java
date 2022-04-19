package com.lframework.xingyun.basedata.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.utils.IdUtil;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.basedata.dto.product.category.property.ProductCategoryPropertyDto;
import com.lframework.xingyun.basedata.entity.ProductCategoryProperty;
import com.lframework.xingyun.basedata.mappers.ProductCategoryPropertyMapper;
import com.lframework.xingyun.basedata.service.product.IProductCategoryPropertyService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductCategoryPropertyServiceImpl extends
    BaseMpServiceImpl<ProductCategoryPropertyMapper, ProductCategoryProperty> implements
    IProductCategoryPropertyService {

  @Transactional
  @Override
  public String create(String categoryId, String propertyId) {

    ProductCategoryProperty record = new ProductCategoryProperty();
    record.setId(IdUtil.getId());
    record.setPropertyId(propertyId);
    record.setCategoryId(categoryId);

    getBaseMapper().insert(record);

    return record.getId();
  }

  @Transactional
  @Override
  public void deleteByPropertyId(String propertyId) {

    Wrapper<ProductCategoryProperty> deleteWrapper = Wrappers
        .lambdaQuery(ProductCategoryProperty.class)
        .eq(ProductCategoryProperty::getPropertyId, propertyId);
    getBaseMapper().delete(deleteWrapper);
  }

  @Override
  public List<ProductCategoryPropertyDto> getByPropertyId(String propertyId) {

    return getBaseMapper().getByPropertyId(propertyId);
  }
}
