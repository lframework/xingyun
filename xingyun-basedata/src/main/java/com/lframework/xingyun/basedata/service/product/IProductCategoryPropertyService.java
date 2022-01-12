package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.basedata.dto.product.category.property.ProductCategoryPropertyDto;

import java.util.List;

public interface IProductCategoryPropertyService extends BaseService {

    /**
     * 创建
     * @param categoryId
     * @param propertyId
     * @return
     */
    String create(String categoryId, String propertyId);

    /**
     * 根据属性ID删除
     * @param propertyId
     */
    void deleteByPropertyId(String propertyId);

    /**
     * 根据属性ID查询
     * @param propertyId
     * @return
     */
    List<ProductCategoryPropertyDto> getByPropertyId(String propertyId);
}
