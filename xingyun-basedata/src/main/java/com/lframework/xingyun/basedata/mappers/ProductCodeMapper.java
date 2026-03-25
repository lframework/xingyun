package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.xingyun.basedata.entity.ProductCode;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商品编号 Mapper 接口
 * </p>
 *
 * @author lframework
 * @since 2025-12-30
 */
public interface ProductCodeMapper extends BaseMapper<ProductCode> {

  List<String> checkMultiCodes(@Param("codes") List<String> codes,
      @Param("productId") String productId);
}
