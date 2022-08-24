package com.lframework.xingyun.basedata.biz.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.basedata.facade.entity.ProductPropertyItem;
import com.lframework.xingyun.basedata.facade.vo.product.property.item.QueryProductPropertyItemVo;
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
public interface ProductPropertyItemMapper extends BaseMapper<ProductPropertyItem> {

    /**
     * 查询里诶包
     *
     * @param vo
     * @return
     */
    List<ProductPropertyItem> query(@Param("vo") QueryProductPropertyItemVo vo);

    /**
     * 根据属性ID查询
     *
     * @param propertyId
     * @return
     */
    List<ProductPropertyItem> getByPropertyId(String propertyId);
}
