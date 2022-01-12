package com.lframework.xingyun.basedata.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.utils.IdUtil;
import com.lframework.xingyun.basedata.dto.product.category.property.ProductCategoryPropertyDto;
import com.lframework.xingyun.basedata.entity.ProductCategoryProperty;
import com.lframework.xingyun.basedata.mappers.ProductCategoryPropertyMapper;
import com.lframework.xingyun.basedata.service.product.IProductCategoryPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductCategoryPropertyServiceimpl implements IProductCategoryPropertyService {

    @Autowired
    private ProductCategoryPropertyMapper productCategoryPropertyMapper;

    @Transactional
    @Override
    public String create(String categoryId, String propertyId) {

        ProductCategoryProperty record = new ProductCategoryProperty();
        record.setId(IdUtil.getId());
        record.setPropertyId(propertyId);
        record.setCategoryId(categoryId);

        productCategoryPropertyMapper.insert(record);

        return record.getId();
    }

    @Transactional
    @Override
    public void deleteByPropertyId(String propertyId) {

        Wrapper<ProductCategoryProperty> deleteWrapper = Wrappers.lambdaQuery(ProductCategoryProperty.class)
                .eq(ProductCategoryProperty::getPropertyId, propertyId);
        productCategoryPropertyMapper.delete(deleteWrapper);
    }

    @Override
    public List<ProductCategoryPropertyDto> getByPropertyId(String propertyId) {

        return productCategoryPropertyMapper.getByPropertyId(propertyId);
    }
}
