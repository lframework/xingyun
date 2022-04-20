package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.basedata.dto.product.property.ProductPropertyModelorDto;
import com.lframework.xingyun.basedata.entity.ProductProperty;
import com.lframework.xingyun.basedata.vo.product.property.QueryProductPropertyVo;
import java.util.Collection;
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
public interface ProductPropertyMapper extends BaseMapper<ProductProperty> {

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    List<ProductProperty> query(@Param("vo") QueryProductPropertyVo vo);

    /**
     * 根据商品类目查询
     *
     * @param categoryIds
     * @return
     */
    List<ProductPropertyModelorDto> getModelorByCategoryId(@Param("categoryIds") Collection<String> categoryIds);
}
