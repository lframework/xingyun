package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.basedata.dto.product.saleprop.item.ProductSalePropItemDto;
import com.lframework.xingyun.basedata.dto.product.saleprop.item.SalePropItemByProductDto;
import com.lframework.xingyun.basedata.vo.product.saleprop.item.CreateProductSalePropItemVo;
import com.lframework.xingyun.basedata.vo.product.saleprop.item.QueryProductSalePropItemVo;
import com.lframework.xingyun.basedata.vo.product.saleprop.item.UpdateProductSalePropItemVo;

import java.util.List;

public interface IProductSalePropItemService extends BaseService {

    /**
     * 查询列表
     * @return
     */
    PageResult<ProductSalePropItemDto> query(Integer pageIndex, Integer pageSize, QueryProductSalePropItemVo vo);

    /**
     * 查询列表
     * @param vo
     * @return
     */
    List<ProductSalePropItemDto> query(QueryProductSalePropItemVo vo);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    ProductSalePropItemDto getById(String id);

    /**
     * 创建
     * @param vo
     * @return
     */
    String create(CreateProductSalePropItemVo vo);

    /**
     * 修改
     * @param vo
     */
    void update(UpdateProductSalePropItemVo vo);

    /**
     * 根据销售属性组ID查询启用的销售属性
     * @param groupId
     * @return
     */
    List<ProductSalePropItemDto> getEnablesByGroupId(String groupId);

    /**
     * 根据商品ID查询
     * @param productId
     * @return
     */
    List<SalePropItemByProductDto> getByProductId(String productId);
}
