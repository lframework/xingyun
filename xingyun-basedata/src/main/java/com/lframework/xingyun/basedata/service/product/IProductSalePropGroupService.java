package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.basedata.dto.product.saleprop.ProductSalePropGroupDto;
import com.lframework.xingyun.basedata.vo.product.saleprop.CreateProductSalePropGroupVo;
import com.lframework.xingyun.basedata.vo.product.saleprop.QueryProductSalePropGroupSelectorVo;
import com.lframework.xingyun.basedata.vo.product.saleprop.QueryProductSalePropGroupVo;
import com.lframework.xingyun.basedata.vo.product.saleprop.UpdateProductSalePropGroupVo;

import java.util.Collection;
import java.util.List;

public interface IProductSalePropGroupService extends BaseService {

    /**
     * 查询列表
     * @return
     */
    PageResult<ProductSalePropGroupDto> query(Integer pageIndex, Integer pageSize, QueryProductSalePropGroupVo vo);

    /**
     * 查询列表
     * @param vo
     * @return
     */
    List<ProductSalePropGroupDto> query(QueryProductSalePropGroupVo vo);

    /**
     * 选择器
     * @return
     */
    PageResult<ProductSalePropGroupDto> selector(Integer pageIndex, Integer pageSize,
            QueryProductSalePropGroupSelectorVo vo);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    ProductSalePropGroupDto getById(String id);

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
    String create(CreateProductSalePropGroupVo vo);

    /**
     * 修改
     * @param vo
     */
    void update(UpdateProductSalePropGroupVo vo);
}
