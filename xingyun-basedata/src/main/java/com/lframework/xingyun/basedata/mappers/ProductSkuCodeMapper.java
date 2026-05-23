package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.xingyun.basedata.entity.ProductSkuCode;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * SKU检索码 Mapper
 */
public interface ProductSkuCodeMapper extends BaseMapper<ProductSkuCode> {

  List<String> checkCodes(@Param("codes") List<String> codes, @Param("productId") String productId,
      @Param("skuId") String skuId);
}
