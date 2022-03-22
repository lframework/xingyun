package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.basedata.dto.product.purchase.ProductPurchaseDto;
import com.lframework.xingyun.basedata.vo.product.purchase.CreateProductPurchaseVo;
import com.lframework.xingyun.basedata.vo.product.purchase.UpdateProductPurchaseVo;

public interface IProductPurchaseService extends BaseService {

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  ProductPurchaseDto getById(String id);

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
