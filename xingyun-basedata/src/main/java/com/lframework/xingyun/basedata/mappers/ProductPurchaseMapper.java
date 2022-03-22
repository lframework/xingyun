package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.basedata.dto.product.purchase.ProductPurchaseDto;
import com.lframework.xingyun.basedata.entity.ProductPurchase;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-07-11
 */
public interface ProductPurchaseMapper extends BaseMapper<ProductPurchase> {

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  ProductPurchaseDto getById(String id);
}
