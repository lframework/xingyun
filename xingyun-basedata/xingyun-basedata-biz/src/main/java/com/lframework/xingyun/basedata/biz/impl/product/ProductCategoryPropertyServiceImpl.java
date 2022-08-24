package com.lframework.xingyun.basedata.biz.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.basedata.biz.mappers.ProductCategoryPropertyMapper;
import com.lframework.xingyun.basedata.biz.service.product.IProductCategoryPropertyService;
import com.lframework.xingyun.basedata.facade.entity.ProductCategoryProperty;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductCategoryPropertyServiceImpl
    extends BaseMpServiceImpl<ProductCategoryPropertyMapper, ProductCategoryProperty>
    implements IProductCategoryPropertyService {

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

    Wrapper<ProductCategoryProperty> deleteWrapper = Wrappers.lambdaQuery(
            ProductCategoryProperty.class)
        .eq(ProductCategoryProperty::getPropertyId, propertyId);
    getBaseMapper().delete(deleteWrapper);
  }

  @Override
  public List<ProductCategoryProperty> getByPropertyId(String propertyId) {

    return getBaseMapper().getByPropertyId(propertyId);
  }
}
