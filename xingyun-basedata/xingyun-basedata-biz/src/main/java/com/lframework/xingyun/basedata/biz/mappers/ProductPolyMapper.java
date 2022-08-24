package com.lframework.xingyun.basedata.biz.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.basedata.facade.dto.product.poly.ProductPolyDto;
import com.lframework.xingyun.basedata.facade.entity.ProductPoly;
import com.lframework.xingyun.basedata.facade.vo.product.poly.QueryProductPolyVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-08-05
 */
public interface ProductPolyMapper extends BaseMapper<ProductPoly> {

  /**
   * 查询列表
   * @param vo
   * @return
   */
  List<ProductPoly> query(@Param("vo") QueryProductPolyVo vo);

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
}
