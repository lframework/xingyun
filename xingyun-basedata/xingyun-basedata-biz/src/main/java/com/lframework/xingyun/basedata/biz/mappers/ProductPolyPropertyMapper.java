package com.lframework.xingyun.basedata.biz.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.basedata.facade.dto.product.poly.ProductPolyPropertyDto;
import com.lframework.xingyun.basedata.facade.entity.ProductPolyProperty;
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
public interface ProductPolyPropertyMapper extends BaseMapper<ProductPolyProperty> {

    /**
     * 根据polyId查询
     *
     * @param polyId
     * @return
     */
    List<ProductPolyPropertyDto> getByPolyId(String polyId);

    /**
     * 根据属性ID查询
     *
     * @param propertyId
     * @return
     */
    List<ProductPolyPropertyDto> getByPropertyId(String propertyId);

    /**
     * 将通用更改为指定类目
     *
     * @param propertyId
     * @param categoryId
     */
    void setCommonToAppoint(@Param("propertyId") String propertyId, @Param("categoryId") String categoryId);

}
