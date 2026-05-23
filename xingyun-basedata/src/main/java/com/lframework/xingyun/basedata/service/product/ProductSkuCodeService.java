package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.basedata.entity.ProductSku;
import com.lframework.xingyun.basedata.entity.ProductSkuCode;
import java.util.List;

public interface ProductSkuCodeService extends BaseMpService<ProductSkuCode> {

  /**
   * 校验编号是否冲突
   */
  List<String> checkCodes(List<String> codes, String productId, String skuId);

  /**
   * 重建单个SKU的检索码
   */
  void rebuildSkuCodes(ProductSku sku, String productCode, List<String> extraCodes);

  /**
   * 查询SKU扩展编号
   */
  List<String> getExtraCodes(String skuId);

  /**
   * 清理商品编号展开检索码
   */
  void rebuildProductCode(String productId, String productCode);

  /**
   * 清空商品下所有SKU检索码
   */
  void removeByProductId(String productId);

  /**
   * 清空SKU检索码
   */
  void removeBySkuId(String skuId);
}
