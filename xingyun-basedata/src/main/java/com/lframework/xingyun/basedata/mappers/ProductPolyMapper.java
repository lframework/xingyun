package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.basedata.dto.product.poly.ProductPolyDto;
import com.lframework.xingyun.basedata.entity.ProductPoly;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-08-05
 */
public interface ProductPolyMapper extends BaseMapper<ProductPoly> {

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  ProductPolyDto getById(String id);

  /**
   * 查询没有属性的ID
   *
   * @param propertyId
   * @return
   */
  List<String> getIdNotInPolyProperty(String propertyId);

  /**
   * 根据类目ID查询
   *
   * @param categoryId
   * @return
   */
  List<String> getIdByCategoryId(String categoryId);
}
