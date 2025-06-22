package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.basedata.entity.ProductPropertyItem;
import com.lframework.xingyun.basedata.vo.product.property.item.CreateProductPropertyItemVo;
import com.lframework.xingyun.basedata.vo.product.property.item.QueryProductPropertyItemVo;
import com.lframework.xingyun.basedata.vo.product.property.item.UpdateProductPropertyItemVo;
import java.util.List;

public interface ProductPropertyItemService extends BaseMpService<ProductPropertyItem> {

    /**
     * 查询列表
     *
     * @param pageIndex
     * @param pageSize
     * @param vo
     * @return
     */
    PageResult<ProductPropertyItem> query(Integer pageIndex, Integer pageSize, QueryProductPropertyItemVo vo);

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    List<ProductPropertyItem> query(QueryProductPropertyItemVo vo);

    /**
     * 根据属性ID查询
     *
     * @param propertyId
     * @return
     */
    List<ProductPropertyItem> getByPropertyId(String propertyId);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    ProductPropertyItem findById(String id);

    /**
     * 新增
     *
     * @param vo
     * @return
     */
    String create(CreateProductPropertyItemVo vo);

    /**
     * 修改
     *
     * @param vo
     */
    void update(UpdateProductPropertyItemVo vo);
}
