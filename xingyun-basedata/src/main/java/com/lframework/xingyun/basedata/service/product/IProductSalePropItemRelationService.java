package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.basedata.dto.product.saleprop.item.SalePropItemByProductDto;
import com.lframework.xingyun.basedata.entity.ProductSalePropItemRelation;
import com.lframework.xingyun.basedata.vo.product.info.saleprop.CreateProductSalePropItemRelationVo;
import java.util.List;

public interface IProductSalePropItemRelationService extends
    BaseMpService<ProductSalePropItemRelation> {

  /**
   * 根据商品ID查询
   *
   * @param productId
   * @return
   */
  SalePropItemByProductDto getByProductId(String productId);

  /**
   * 根据ID查询商品ID
   *
   * @param id
   * @return
   */
  List<String> getProductIdById(String id);

  /**
   * 创建
   *
   * @param vo
   */
  void create(CreateProductSalePropItemRelationVo vo);
}
