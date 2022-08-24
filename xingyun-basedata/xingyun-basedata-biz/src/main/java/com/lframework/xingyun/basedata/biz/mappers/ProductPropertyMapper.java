package com.lframework.xingyun.basedata.biz.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.basedata.facade.dto.product.property.ProductPropertyModelorDto;
import com.lframework.xingyun.basedata.facade.entity.ProductProperty;
import com.lframework.xingyun.basedata.facade.vo.product.property.QueryProductPropertyVo;
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
