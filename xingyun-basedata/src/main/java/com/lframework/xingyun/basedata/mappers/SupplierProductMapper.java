package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.xingyun.basedata.entity.SupplierProduct;
import com.lframework.xingyun.basedata.vo.supplier.product.QuerySupplierProductVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 供应商商品关联 Mapper
 */
public interface SupplierProductMapper extends BaseMapper<SupplierProduct> {

  /**
   * 查询列表
   */
  List<SupplierProduct> query(@Param("vo") QuerySupplierProductVo vo, @Param("supplierId") String supplierId);
}