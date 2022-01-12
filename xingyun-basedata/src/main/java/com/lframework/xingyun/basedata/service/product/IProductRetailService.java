package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.basedata.dto.product.retail.ProductRetailDto;
import com.lframework.xingyun.basedata.vo.product.retail.CreateProductRetailVo;
import com.lframework.xingyun.basedata.vo.product.retail.UpdateProductRetailVo;

public interface IProductRetailService extends BaseService {

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    ProductRetailDto getById(String id);

    /**
     * 创建
     * @param vo
     * @return
     */
    String create(CreateProductRetailVo vo);

    /**
     * 修改
     * @param vo
     */
    void update(UpdateProductRetailVo vo);
}
