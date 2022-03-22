package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.basedata.dto.product.saleprop.item.ProductSalePropItemDto;
import com.lframework.xingyun.basedata.dto.product.saleprop.item.SalePropItemByProductDto;
import com.lframework.xingyun.basedata.entity.ProductSalePropItem;
import com.lframework.xingyun.basedata.vo.product.saleprop.item.QueryProductSalePropItemVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-07-13
 */
public interface ProductSalePropItemMapper extends BaseMapper<ProductSalePropItem> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<ProductSalePropItemDto> query(@Param("vo") QueryProductSalePropItemVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  ProductSalePropItemDto getById(String id);

  /**
   * 根据GroupId查询
   *
   * @param groupId
   * @return
   */
  List<ProductSalePropItemDto> getByGroupId(String groupId);

  /**
   * 根据销售属性组ID查询启用的销售属性
   *
   * @param groupId
   * @return
   */
  List<ProductSalePropItemDto> getEnablesByGroupId(String groupId);

  /**
   * 根据商品ID查询
   *
   * @param productId
   * @return
   */
  List<SalePropItemByProductDto> getByProductId(String productId);

  /**
   * 根据ID查询商品ID
   *
   * @param id
   * @return
   */
  List<String> getProductIdById(String id);
}
