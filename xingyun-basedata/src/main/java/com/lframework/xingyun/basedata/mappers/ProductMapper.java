package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.vo.product.info.QueryProductSelectorVo;
import com.lframework.xingyun.basedata.vo.product.info.QueryProductVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-07-11
 */
public interface ProductMapper extends BaseMapper<Product> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<Product> query(@Param("vo") QueryProductVo vo,
      @Param("dataPermission") String dataPermission);

  /**
   * 查询商品品种数
   *
   * @param vo
   * @return
   */
  Integer queryCount(@Param("vo") QueryProductVo vo);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<Product> selector(@Param("vo") QueryProductSelectorVo vo,
      @Param("dataPermission") String dataPermission);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  Product findById(String id);

  /**
   * 查询没有属性的ID
   *
   * @param propertyId
   * @return
   */
  List<String> getIdNotInProductProperty(String propertyId);

  /**
   * 根据类目ID查询
   *
   * @param categoryId
   * @return
   */
  List<String> getIdByCategoryId(String categoryId);

  /**
   * 根据类目ID查询
   *
   * @param categoryIds
   * @return
   */
  List<Product> getByCategoryIds(@Param("categoryIds") List<String> categoryIds,
      @Param("productType") Integer productType);

  /**
   * 根据品牌ID查询
   *
   * @param brandIds
   * @return
   */
  List<Product> getByBrandIds(@Param("brandIds") List<String> brandIds,
      @Param("productType") Integer productType);
}
