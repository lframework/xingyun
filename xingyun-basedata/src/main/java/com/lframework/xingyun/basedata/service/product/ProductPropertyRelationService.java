package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.basedata.dto.product.ProductPropertyRelationDto;
import com.lframework.xingyun.basedata.entity.ProductPropertyRelation;
import com.lframework.xingyun.basedata.vo.product.property.realtion.CreateProductPropertyRelationVo;
import java.util.List;

public interface ProductPropertyRelationService extends BaseMpService<ProductPropertyRelation> {

  /**
   * 根据商品Id查询
   *
   * @param productId
   * @return
   */
  List<ProductPropertyRelationDto> getByProductId(String productId);

  /**
   * 将多选属性更改为单选属性
   *
   * @param propertyId
   */
  void setMultipleToSimple(String propertyId);

  /**
   * 从通用改为指定分类
   *
   * @param propertyId
   */
  void setCommonToAppoint(String propertyId);

  /**
   * 从指定分类改为通用
   *
   * @param propertyId
   */
  void setAppointToCommon(String propertyId);

  /**
   * 修改指定分类的分类ID
   *
   * @param propertyId
   */
  void updateAppointCategoryId(String propertyId);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateProductPropertyRelationVo vo);

  /**
   * 根据商品Id删除
   *
   * @param productId
   */
  void deleteByProductId(String productId);
}
