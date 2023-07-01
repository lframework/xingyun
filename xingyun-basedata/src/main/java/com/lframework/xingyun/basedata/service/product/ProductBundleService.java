package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.basedata.entity.ProductBundle;
import java.util.List;

public interface ProductBundleService extends BaseMpService<ProductBundle> {

  /**
   * 根据组合商品ID查询
   * @param id
   * @return
   */
  List<ProductBundle> getByMainProductId(String id);
}
