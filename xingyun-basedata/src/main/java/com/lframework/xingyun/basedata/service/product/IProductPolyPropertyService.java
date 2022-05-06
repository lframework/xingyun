package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.basedata.dto.product.poly.ProductPolyPropertyDto;
import com.lframework.xingyun.basedata.entity.ProductPolyProperty;
import com.lframework.xingyun.basedata.vo.product.poly.property.CreateProductPolyPropertyVo;
import java.util.List;

public interface IProductPolyPropertyService extends BaseMpService<ProductPolyProperty> {

  /**
   * 根据polyId查询
   *
   * @param polyId
   * @return
   */
  List<ProductPolyPropertyDto> getByPolyId(String polyId);

  /**
   * 将多选属性更改为单选属性
   *
   * @param propertyId
   */
  void setMultipleToSimple(String propertyId);

  /**
   * 从通用改为指定类目
   *
   * @param propertyId
   */
  void setCommonToAppoint(String propertyId);

  /**
   * 从指定类目改为通用
   *
   * @param propertyId
   */
  void setAppointToCommon(String propertyId);

  /**
   * 修改指定类目的类目ID
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
  String create(CreateProductPolyPropertyVo vo);

  /**
   * 根据SPU Id删除
   * @param polyId
   */
  void deleteByPolyId(String polyId);
}
