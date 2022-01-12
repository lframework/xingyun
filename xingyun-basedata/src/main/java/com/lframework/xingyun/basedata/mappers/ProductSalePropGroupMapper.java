package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.basedata.dto.product.saleprop.ProductSalePropGroupDto;
import com.lframework.xingyun.basedata.entity.ProductSalePropGroup;
import com.lframework.xingyun.basedata.vo.product.saleprop.QueryProductSalePropGroupSelectorVo;
import com.lframework.xingyun.basedata.vo.product.saleprop.QueryProductSalePropGroupVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-07-13
 */
public interface ProductSalePropGroupMapper extends BaseMapper<ProductSalePropGroup> {

    /**
     * 查询列表
     * @param vo
     * @return
     */
    List<ProductSalePropGroupDto> query(@Param("vo") QueryProductSalePropGroupVo vo);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    ProductSalePropGroupDto getById(String id);

    /**
     * 选择器
     * @param vo
     * @return
     */
    List<ProductSalePropGroupDto> selector(@Param("vo") QueryProductSalePropGroupSelectorVo vo);
}
