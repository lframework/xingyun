package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.basedata.dto.product.brand.ProductBrandDto;
import com.lframework.xingyun.basedata.vo.product.brand.CreateProductBrandVo;
import com.lframework.xingyun.basedata.vo.product.brand.QueryProductBrandSelectorVo;
import com.lframework.xingyun.basedata.vo.product.brand.QueryProductBrandVo;
import com.lframework.xingyun.basedata.vo.product.brand.UpdateProductBrandVo;

import java.util.Collection;
import java.util.List;

public interface IProductBrandService extends BaseService {

    /**
     * 查询列表
     * @return
     */
    PageResult<ProductBrandDto> query(Integer pageIndex, Integer pageSize, QueryProductBrandVo vo);

    /**
     * 查询列表
     * @param vo
     * @return
     */
    List<ProductBrandDto> query(QueryProductBrandVo vo);

    /**
     * 选择器
     * @param vo
     * @return
     */
    PageResult<ProductBrandDto> selector(Integer pageIndex, Integer pageSize, QueryProductBrandSelectorVo vo);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    ProductBrandDto getById(String id);

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
    String create(CreateProductBrandVo vo);

    /**
     * 修改
     * @param vo
     */
    void update(UpdateProductBrandVo vo);
}
