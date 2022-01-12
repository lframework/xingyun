package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.dto.stock.ProductStockLogDto;
import com.lframework.xingyun.sc.entity.ProductStockLog;
import com.lframework.xingyun.sc.vo.stock.log.QueryProductStockLogVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-10-14
 */
public interface ProductStockLogMapper extends BaseMapper<ProductStockLog> {

    /**
     * 查询列表
     * @param vo
     * @return
     */
    List<ProductStockLogDto> query(@Param("vo") QueryProductStockLogVo vo);
}
