package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.basedata.dto.product.property.ProductCategoryPropertyDefinitionModelorDto;
import com.lframework.xingyun.basedata.entity.ProductCategoryPropertyDefinition;
import com.lframework.xingyun.basedata.vo.product.property.CreateProductCategoryPropertyDefinitionVo;
import com.lframework.xingyun.basedata.vo.product.property.QueryProductCategoryPropertyDefinitionVo;
import com.lframework.xingyun.basedata.vo.product.property.UpdateProductCategoryPropertyDefinitionVo;
import java.util.List;

public interface ProductCategoryPropertyDefinitionService extends BaseMpService<ProductCategoryPropertyDefinition> {

    /**
     * 查询列表
     *
     * @return
     */
    PageResult<ProductCategoryPropertyDefinition> query(Integer pageIndex, Integer pageSize, QueryProductCategoryPropertyDefinitionVo vo);

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    List<ProductCategoryPropertyDefinition> query(QueryProductCategoryPropertyDefinitionVo vo);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    ProductCategoryPropertyDefinition findById(String id);

    /**
     * 根据ID删除
     *
     * @param id
     */
    void deleteById(String id);

    /**
     * 创建
     *
     * @param vo
     * @return
     */
    String create(CreateProductCategoryPropertyDefinitionVo vo);

    /**
     * 修改
     *
     * @param vo
     */
    void update(UpdateProductCategoryPropertyDefinitionVo vo);

    /**
     * 根据分类ID查询
     *
     * @param categoryId
     * @return
     */
    List<ProductCategoryPropertyDefinitionModelorDto> getModelorByCategoryId(String categoryId);
}
