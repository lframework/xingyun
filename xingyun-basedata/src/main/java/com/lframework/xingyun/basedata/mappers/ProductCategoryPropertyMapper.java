package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.basedata.entity.ProductCategoryProperty;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-07-05
 */
public interface ProductCategoryPropertyMapper extends BaseMapper<ProductCategoryProperty> {

  /**
   * 根据属性ID查询
   *
   * @param propertyId
   * @return
   */
  List<ProductCategoryProperty> getByPropertyId(String propertyId);
}
