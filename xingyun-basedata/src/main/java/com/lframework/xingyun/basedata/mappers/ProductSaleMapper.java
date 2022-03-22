package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.basedata.dto.product.sale.ProductSaleDto;
import com.lframework.xingyun.basedata.entity.ProductSale;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-07-11
 */
public interface ProductSaleMapper extends BaseMapper<ProductSale> {

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  ProductSaleDto getById(String id);
}
