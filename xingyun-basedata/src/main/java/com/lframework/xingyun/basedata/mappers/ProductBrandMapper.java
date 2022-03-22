package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.basedata.dto.product.brand.ProductBrandDto;
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
  List<ProductBrandDto> query(@Param("vo") QueryProductBrandVo vo);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<ProductBrandDto> selector(@Param("vo") QueryProductBrandSelectorVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  ProductBrandDto getById(String id);
}
