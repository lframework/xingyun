package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.xingyun.basedata.entity.ProductSalePropertyItem;
import com.lframework.xingyun.basedata.vo.product.saleproperty.item.QueryProductSalePropertyItemVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductSalePropertyItemMapper extends BaseMapper<ProductSalePropertyItem> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<ProductSalePropertyItem> query(@Param("vo") QueryProductSalePropertyItemVo vo);

  /**
   * 根据销售属性ID查询
   *
   * @param propertyId
   * @return
   */
  List<ProductSalePropertyItem> getByPropertyId(String propertyId);
}
