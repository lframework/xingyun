package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.basedata.dto.product.ProductSkuSelectorDto;
import com.lframework.xingyun.basedata.entity.ProductSku;
import com.lframework.xingyun.basedata.vo.product.info.QueryProductSelectorVo;
import java.util.List;

public interface ProductSkuService extends BaseMpService<ProductSku> {

  /**
   * 根据ID查询
   */
  ProductSku findById(String id);

  /**
   * 根据商品ID查询可用SKU
   */
  List<ProductSku> getAvailableByProductId(String productId);

  /**
   * SKU选择器
   */
  PageResult<ProductSkuSelectorDto> selector(Integer pageIndex, Integer pageSize,
      QueryProductSelectorVo vo);

  /**
   * 加载SKU选择器数据
   */
  List<ProductSkuSelectorDto> loadSelector(List<String> ids);

  /**
   * 根据检索码查询可用SKU
   */
  ProductSku findAvailableByCode(String code);

  /**
   * 根据检索码查询所有可用SKU
   */
  List<ProductSku> getAvailableByCode(String code);

  /**
   * 刷新销售属性展示文本
   */
  void refreshSalePropertyTextByPropertyId(String propertyId);

  /**
   * 根据ID删除
   *
   * @param id
   */
  void deleteById(String id);

  /**
   * 逻辑删除商品下所有SKU
   */
  void deleteByProductId(String productId);
}
