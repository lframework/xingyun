package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.xingyun.basedata.entity.ProductCategoryPropertyItem;
import com.lframework.xingyun.basedata.vo.product.property.item.QueryProductCategoryPropertyItemVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-08-08
 */
public interface ProductCategoryPropertyItemMapper extends BaseMapper<ProductCategoryPropertyItem> {

    /**
     * 查询里诶包
     *
     * @param vo
     * @return
     */
    List<ProductCategoryPropertyItem> query(@Param("vo") QueryProductCategoryPropertyItemVo vo);

    /**
     * 根据属性ID查询
     *
     * @param propertyId
     * @return
     */
    List<ProductCategoryPropertyItem> getByPropertyId(String propertyId);
}
