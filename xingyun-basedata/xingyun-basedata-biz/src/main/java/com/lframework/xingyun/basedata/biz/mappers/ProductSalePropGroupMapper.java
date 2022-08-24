package com.lframework.xingyun.basedata.biz.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.basedata.facade.entity.ProductSalePropGroup;
import com.lframework.xingyun.basedata.facade.vo.product.saleprop.QueryProductSalePropGroupSelectorVo;
import com.lframework.xingyun.basedata.facade.vo.product.saleprop.QueryProductSalePropGroupVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-07-13
 */
public interface ProductSalePropGroupMapper extends BaseMapper<ProductSalePropGroup> {

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    List<ProductSalePropGroup> query(@Param("vo") QueryProductSalePropGroupVo vo);

    /**
     * 选择器
     *
     * @param vo
     * @return
     */
    List<ProductSalePropGroup> selector(@Param("vo") QueryProductSalePropGroupSelectorVo vo);
}
