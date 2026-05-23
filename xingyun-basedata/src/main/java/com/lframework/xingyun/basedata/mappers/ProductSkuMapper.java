package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.xingyun.basedata.dto.product.ProductSkuSelectorDto;
import com.lframework.xingyun.basedata.entity.ProductSku;
import com.lframework.xingyun.basedata.vo.product.info.QueryProductSelectorVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 商品SKU Mapper
 */
public interface ProductSkuMapper extends BaseMapper<ProductSku> {

  List<ProductSku> getAvailableByProductId(@Param("productId") String productId);

  List<ProductSkuSelectorDto> selector(@Param("vo") QueryProductSelectorVo vo);

  List<ProductSkuSelectorDto> loadSelector(@Param("ids") List<String> ids);

  ProductSku findAvailableByCode(@Param("code") String code);

  List<ProductSku> getAvailableByCode(@Param("code") String code);

  void refreshSalePropertyTextByPropertyId(@Param("propertyId") String propertyId);
}
