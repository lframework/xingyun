package com.lframework.xingyun.basedata.biz.service.product;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.basedata.facade.dto.product.poly.ProductPolyDto;
import com.lframework.xingyun.basedata.facade.entity.ProductPoly;
import com.lframework.xingyun.basedata.facade.vo.product.poly.CreateProductPolyVo;
import com.lframework.xingyun.basedata.facade.vo.product.poly.QueryProductPolyVo;
import com.lframework.xingyun.basedata.facade.vo.product.poly.UpdateProductPolyVo;
import java.util.List;

public interface IProductPolyService extends BaseMpService<ProductPoly> {

  /**
   * 查询列表
   * @return
   */
  PageResult<ProductPoly> query(Integer pageIndex, Integer pageSize, QueryProductPolyVo vo);

  /**
   * 查询列表
   * @param vo
   * @return
   */
  List<ProductPoly> query(QueryProductPolyVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  ProductPolyDto findById(String id);

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

  /**
   * 修改
   * @param vo
   */
  void update(UpdateProductPolyVo vo);
}
