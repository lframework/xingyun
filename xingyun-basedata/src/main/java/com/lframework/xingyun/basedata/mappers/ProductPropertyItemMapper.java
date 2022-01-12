package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.basedata.dto.product.property.item.ProductPropertyItemDto;
import com.lframework.xingyun.basedata.entity.ProductPropertyItem;
import com.lframework.xingyun.basedata.vo.product.property.item.QueryProductPropertyItemVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-08-08
 */
public interface ProductPropertyItemMapper extends BaseMapper<ProductPropertyItem> {

    /**
     * 查询里诶包
     * @param vo
     * @return
     */
    List<ProductPropertyItemDto> query(@Param("vo") QueryProductPropertyItemVo vo);

    /**
     * 根据属性ID查询
     * @param propertyId
     * @return
     */
    List<ProductPropertyItemDto> getByPropertyId(String propertyId);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    ProductPropertyItemDto getById(String id);
}
