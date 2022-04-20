package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.basedata.entity.ProductSale;
import com.lframework.xingyun.basedata.vo.product.sale.CreateProductSaleVo;
import com.lframework.xingyun.basedata.vo.product.sale.UpdateProductSaleVo;

public interface IProductSaleService extends BaseMpService<ProductSale> {

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateProductSaleVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateProductSaleVo vo);
}
