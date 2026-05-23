package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.basedata.dto.product.ProductCategoryPropertyValueRelationDto;
import com.lframework.xingyun.basedata.entity.ProductCategoryPropertyValueRelation;
import com.lframework.xingyun.basedata.vo.product.property.relation.CreateProductCategoryPropertyValueRelationVo;
import java.util.List;

public interface ProductCategoryPropertyValueRelationService extends BaseMpService<ProductCategoryPropertyValueRelation> {

  /**
   * 根据商品Id查询
   *
   * @param productId
   * @return
   */
  List<ProductCategoryPropertyValueRelationDto> getByProductId(String productId);

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
  String create(CreateProductCategoryPropertyValueRelationVo vo);

  /**
   * 根据商品Id删除
   *
   * @param productId
   */
  void deleteByProductId(String productId);

  /**
   * 根据属性ID和分类ID删除
   *
   * @param propertyId
   * @param categoryId
   */
  void deleteByPropertyIdAndCategoryId(String propertyId, String categoryId);
}
