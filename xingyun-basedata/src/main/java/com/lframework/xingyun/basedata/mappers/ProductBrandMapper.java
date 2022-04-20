package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.basedata.entity.ProductBrand;
import com.lframework.xingyun.basedata.vo.product.brand.QueryProductBrandSelectorVo;
import com.lframework.xingyun.basedata.vo.product.brand.QueryProductBrandVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-07-06
 */
public interface ProductBrandMapper extends BaseMapper<ProductBrand> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<ProductBrand> query(@Param("vo") QueryProductBrandVo vo);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<ProductBrand> selector(@Param("vo") QueryProductBrandSelectorVo vo);
}
