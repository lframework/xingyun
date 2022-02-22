package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.dto.stock.take.config.TakeStockConfigDto;
import com.lframework.xingyun.sc.entity.TakeStockConfig;

import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 盘点参数 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface TakeStockConfigMapper extends BaseMapper<TakeStockConfig> {

    /**
     * 根据ID查询
     */
    TakeStockConfigDto get();
}
