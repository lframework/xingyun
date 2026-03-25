package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.basedata.entity.ProductCode;
import java.util.List;

public interface ProductCodeService extends BaseMpService<ProductCode> {

  /**
   * 根据商品ID查询
   *
   * @param productId
   * @return
   */
  List<ProductCode> findByProductId(String productId);

  /**
   * 校验编号是否重复
   *
   * @param codes
   * @param productId
   * @return 重复的编号
   */
  List<String> checkMultiCodes(List<String> codes, String productId);
}
