package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.dto.stock.ProductLotDto;
import com.lframework.xingyun.sc.dto.stock.ProductLotWithStockDto;
import com.lframework.xingyun.sc.entity.ProductLot;
import com.lframework.xingyun.sc.vo.stock.lot.QueryProductLotVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-10-12
 */
public interface ProductLotMapper extends BaseMapper<ProductLot> {

    /**
     * 查询列表
     * @param vo
     * @return
     */
    List<ProductLotWithStockDto> query(@Param("vo") QueryProductLotVo vo);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    ProductLotDto getById(String id);
}
