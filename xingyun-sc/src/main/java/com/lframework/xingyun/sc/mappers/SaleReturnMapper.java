package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.dto.sale.returned.SaleReturnDto;
import com.lframework.xingyun.sc.dto.sale.returned.SaleReturnFullDto;
import com.lframework.xingyun.sc.entity.SaleReturn;
import com.lframework.xingyun.sc.vo.sale.returned.QuerySaleReturnVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-11-04
 */
public interface SaleReturnMapper extends BaseMapper<SaleReturn> {

    /**
     * 查询列表
     * @param vo
     * @return
     */
    List<SaleReturnDto> query(@Param("vo") QuerySaleReturnVo vo);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    SaleReturnDto getById(String id);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    SaleReturnFullDto getDetail(String id);

    /**
     * 查询列表
     * @param vo
     * @return
     */
    List<SaleReturnFullDto> queryFulls(@Param("vo") QuerySaleReturnVo vo);
}
