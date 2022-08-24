package com.lframework.xingyun.basedata.biz.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.basedata.facade.dto.product.saleprop.item.SalePropItemByProductDto;
import com.lframework.xingyun.basedata.facade.entity.ProductSalePropItemRelation;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-08-11
 */
public interface ProductSalePropItemRelationMapper extends BaseMapper<ProductSalePropItemRelation> {

  /**
   * 根据商品ID查询
   *
   * @param productId
   * @return
   */
  SalePropItemByProductDto getByProductId(String productId);

  /**
   * 根据ID查询商品ID
   *
   * @param id
   * @return
   */
  List<String> getProductIdById(String id);
}
