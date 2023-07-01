package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.basedata.entity.ProductRetail;
import com.lframework.xingyun.basedata.vo.product.retail.CreateProductRetailVo;
import com.lframework.xingyun.basedata.vo.product.retail.UpdateProductRetailVo;

public interface ProductRetailService extends BaseMpService<ProductRetail> {

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateProductRetailVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateProductRetailVo vo);
}
