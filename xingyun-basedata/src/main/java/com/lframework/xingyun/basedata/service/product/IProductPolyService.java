package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.basedata.dto.product.poly.ProductPolyDto;
import com.lframework.xingyun.basedata.vo.product.poly.CreateProductPolyVo;
import java.util.List;

public interface IProductPolyService extends BaseService {

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  ProductPolyDto getById(String id);

  /**
   * 查询没有属性的ID
   *
   * @param propertyId
   * @return
   */
  List<String> getIdNotInPolyProperty(String propertyId);

  /**
   * 根据类目ID查询
   *
   * @param categoryId
   * @return
   */
  List<String> getIdByCategoryId(String categoryId);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateProductPolyVo vo);
}
