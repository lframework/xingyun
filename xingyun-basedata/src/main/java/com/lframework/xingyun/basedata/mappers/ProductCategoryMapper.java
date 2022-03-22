package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.basedata.dto.product.category.ProductCategoryDto;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.vo.product.category.QueryProductCategorySelectorVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-07-05
 */
public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {

  /**
   * 查询全部类目信息
   *
   * @return
   */
  List<ProductCategoryDto> getAllProductCategories();

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  ProductCategoryDto getById(String id);

  /**
   * 选择器
   *
   * @return
   */
  List<ProductCategoryDto> selector(@Param("vo") QueryProductCategorySelectorVo vo);
}
