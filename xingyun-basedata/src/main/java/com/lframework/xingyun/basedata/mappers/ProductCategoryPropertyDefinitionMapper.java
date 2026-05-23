package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.xingyun.basedata.dto.product.property.ProductCategoryPropertyDefinitionModelorDto;
import com.lframework.xingyun.basedata.entity.ProductCategoryPropertyDefinition;
import com.lframework.xingyun.basedata.vo.product.property.QueryProductCategoryPropertyDefinitionVo;
import com.lframework.starter.web.core.annotations.sort.Sort;
import com.lframework.starter.web.core.annotations.sort.Sorts;
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
public interface ProductCategoryPropertyDefinitionMapper extends BaseMapper<ProductCategoryPropertyDefinition> {

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
    List<ProductCategoryPropertyDefinition> query(@Param("vo") QueryProductCategoryPropertyDefinitionVo vo);

    /**
     * 根据商品分类查询
     *
     * @param categoryId
     * @return
     */
    List<ProductCategoryPropertyDefinitionModelorDto> getModelorByCategoryId(@Param("categoryId") String categoryId);
}
