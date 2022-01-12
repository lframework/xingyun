package com.lframework.xingyun.chart.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.chart.dto.OrderChartSameMonthDto;
import com.lframework.xingyun.chart.dto.OrderChartTodayDto;
import com.lframework.xingyun.chart.entity.OrderChart;
import com.lframework.xingyun.chart.enums.OrderChartBizType;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-11-19
 */
public interface OrderChartMapper extends BaseMapper<OrderChart> {

    /**
     * 查询列表
     * @param bizType
     * @param startTime
     * @param endTime
     * @return
     */
    List<OrderChartSameMonthDto> querySameMonth(@Param("bizType") OrderChartBizType bizType,
            @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    /**
     * 查询当日数据列表
     * @param bizType
     * @param startTime
     * @param endTime
     * @return
     */
    List<OrderChartTodayDto> queryToday(@Param("bizType") OrderChartBizType bizType,
            @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
