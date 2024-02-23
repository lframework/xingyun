package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.basedata.dto.product.property.ProductPropertyModelorDto;
import com.lframework.xingyun.basedata.entity.ProductProperty;
import com.lframework.xingyun.basedata.vo.product.property.QueryProductPropertyVo;
import com.lframework.xingyun.template.core.annotations.sort.Sort;
import com.lframework.xingyun.template.core.annotations.sort.Sorts;
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
    @Sorts({
        @Sort(value = "code", alias = "p", autoParse = true),
        @Sort(value = "name", alias = "p", autoParse = true),
    })
    List<ProductProperty> query(@Param("vo") QueryProductPropertyVo vo);

    /**
     * 根据商品类目查询
     *
     * @param categoryIds
     * @return
     */
    List<ProductPropertyModelorDto> getModelorByCategoryId(@Param("categoryIds") Collection<String> categoryIds);
}
