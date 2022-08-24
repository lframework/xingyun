package com.lframework.xingyun.basedata.biz.service.product;

import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.basedata.facade.entity.ProductRetail;
import com.lframework.xingyun.basedata.facade.vo.product.retail.CreateProductRetailVo;
import com.lframework.xingyun.basedata.facade.vo.product.retail.UpdateProductRetailVo;

public interface IProductRetailService extends BaseMpService<ProductRetail> {

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
