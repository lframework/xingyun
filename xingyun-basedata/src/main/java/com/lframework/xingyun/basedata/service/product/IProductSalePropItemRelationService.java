package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.basedata.vo.product.info.saleprop.CreateProductSalePropItemRelationVo;

public interface IProductSalePropItemRelationService extends BaseService {

  /**
   * 创建
   *
   * @param vo
   */
  void create(CreateProductSalePropItemRelationVo vo);
}
