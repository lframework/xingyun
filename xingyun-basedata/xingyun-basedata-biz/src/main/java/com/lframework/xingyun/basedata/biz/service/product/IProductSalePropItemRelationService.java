package com.lframework.xingyun.basedata.biz.service.product;

import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.basedata.facade.dto.product.saleprop.item.SalePropItemByProductDto;
import com.lframework.xingyun.basedata.facade.entity.ProductSalePropItemRelation;
import com.lframework.xingyun.basedata.facade.vo.product.info.saleprop.CreateProductSalePropItemRelationVo;
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
