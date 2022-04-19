package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.basedata.entity.ProductSalePropItemRelation;
import com.lframework.xingyun.basedata.vo.product.info.saleprop.CreateProductSalePropItemRelationVo;

public interface IProductSalePropItemRelationService extends
    BaseMpService<ProductSalePropItemRelation> {

  /**
   * 创建
   *
   * @param vo
   */
  void create(CreateProductSalePropItemRelationVo vo);
}
