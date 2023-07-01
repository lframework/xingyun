package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.basedata.dto.product.ProductPropertyRelationDto;
import com.lframework.xingyun.basedata.entity.ProductPropertyRelation;
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
public interface ProductPropertyRelationMapper extends BaseMapper<ProductPropertyRelation> {

    /**
     * 根据商品ID查询
     *
     * @param productId
     * @return
     */
    List<ProductPropertyRelationDto> getByProductId(String productId);

    /**
     * 根据属性ID查询
     *
     * @param propertyId
     * @return
     */
    List<ProductPropertyRelationDto> getByPropertyId(String propertyId);

    /**
     * 将通用更改为指定类目
     *
     * @param propertyId
     * @param categoryId
     */
    void setCommonToAppoint(@Param("propertyId") String propertyId, @Param("categoryId") String categoryId);

}
