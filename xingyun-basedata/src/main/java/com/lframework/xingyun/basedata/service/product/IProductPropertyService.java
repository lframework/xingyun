package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.basedata.dto.product.property.ProductPropertyDto;
import com.lframework.xingyun.basedata.dto.product.property.ProductPropertyModelorDto;
import com.lframework.xingyun.basedata.vo.product.property.CreateProductPropertyVo;
import com.lframework.xingyun.basedata.vo.product.property.QueryProductPropertyVo;
import com.lframework.xingyun.basedata.vo.product.property.UpdateProductPropertyVo;

import java.util.Collection;
import java.util.List;

public interface IProductPropertyService extends BaseService {

    /**
     * 查询列表
     * @return
     */
    PageResult<ProductPropertyDto> query(Integer pageIndex, Integer pageSize, QueryProductPropertyVo vo);

    /**
     * 查询列表
     * @param vo
     * @return
     */
    List<ProductPropertyDto> query(QueryProductPropertyVo vo);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    ProductPropertyDto getById(String id);

    /**
     * 根据ID停用
     * @param ids
     */
    void batchUnable(Collection<String> ids);

    /**
     * 根据ID启用
     * @param ids
     */
    void batchEnable(Collection<String> ids);

    /**
     * 创建
     * @param vo
     * @return
     */
    String create(CreateProductPropertyVo vo);

    /**
     * 修改
     * @param vo
     */
    void update(UpdateProductPropertyVo vo);

    /**
     * 根据类目ID查询
     * @param categoryId
     * @return
     */
    List<ProductPropertyModelorDto> getModelorByCategoryId(String categoryId);
}
