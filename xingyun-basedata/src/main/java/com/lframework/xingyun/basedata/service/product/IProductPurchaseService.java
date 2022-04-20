package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.basedata.entity.ProductPurchase;
import com.lframework.xingyun.basedata.vo.product.purchase.CreateProductPurchaseVo;
import com.lframework.xingyun.basedata.vo.product.purchase.UpdateProductPurchaseVo;

public interface IProductPurchaseService extends BaseMpService<ProductPurchase> {

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateProductPurchaseVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateProductPurchaseVo vo);
}
