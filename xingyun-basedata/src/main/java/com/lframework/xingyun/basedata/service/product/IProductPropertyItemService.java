package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.basedata.dto.product.property.item.ProductPropertyItemDto;
import com.lframework.xingyun.basedata.vo.product.property.item.CreateProductPropertyItemVo;
import com.lframework.xingyun.basedata.vo.product.property.item.QueryProductPropertyItemVo;
import com.lframework.xingyun.basedata.vo.product.property.item.UpdateProductPropertyItemVo;

import java.util.List;

public interface IProductPropertyItemService extends BaseService {

    /**
     * 查询列表
     * @param pageIndex
     * @param pageSize
     * @param vo
     * @return
     */
    PageResult<ProductPropertyItemDto> query(Integer pageIndex, Integer pageSize, QueryProductPropertyItemVo vo);

    /**
     * 查询列表
     * @param vo
     * @return
     */
    List<ProductPropertyItemDto> query(QueryProductPropertyItemVo vo);

    /**
     * 根据属性ID查询
     * @param propertyId
     * @return
     */
    List<ProductPropertyItemDto> getByPropertyId(String propertyId);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    ProductPropertyItemDto getById(String id);

    /**
     * 新增
     * @param vo
     * @return
     */
    String create(CreateProductPropertyItemVo vo);

    /**
     * 修改
     * @param vo
     */
    void update(UpdateProductPropertyItemVo vo);
}
