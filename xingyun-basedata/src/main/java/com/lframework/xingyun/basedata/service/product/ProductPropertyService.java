package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.basedata.dto.product.property.ProductPropertyModelorDto;
import com.lframework.xingyun.basedata.entity.ProductProperty;
import com.lframework.xingyun.basedata.vo.product.property.CreateProductPropertyVo;
import com.lframework.xingyun.basedata.vo.product.property.QueryProductPropertyVo;
import com.lframework.xingyun.basedata.vo.product.property.UpdateProductPropertyVo;
import java.util.Collection;
import java.util.List;

public interface ProductPropertyService extends BaseMpService<ProductProperty> {

    /**
     * 查询列表
     *
     * @return
     */
    PageResult<ProductProperty> query(Integer pageIndex, Integer pageSize, QueryProductPropertyVo vo);

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    List<ProductProperty> query(QueryProductPropertyVo vo);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    ProductProperty findById(String id);

    /**
     * 根据ID停用
     *
     * @param ids
     */
    void batchUnable(Collection<String> ids);

    /**
     * 根据ID启用
     *
     * @param ids
     */
    void batchEnable(Collection<String> ids);

    /**
     * 创建
     *
     * @param vo
     * @return
     */
    String create(CreateProductPropertyVo vo);

    /**
     * 修改
     *
     * @param vo
     */
    void update(UpdateProductPropertyVo vo);

    /**
     * 根据分类ID查询
     *
     * @param categoryId
     * @return
     */
    List<ProductPropertyModelorDto> getModelorByCategoryId(String categoryId);
}
