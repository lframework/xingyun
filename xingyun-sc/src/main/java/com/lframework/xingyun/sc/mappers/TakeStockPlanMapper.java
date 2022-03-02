package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.dto.stock.take.plan.QueryTakeStockPlanProductDto;
import com.lframework.xingyun.sc.dto.stock.take.plan.TakeStockPlanDto;
import com.lframework.xingyun.sc.dto.stock.take.plan.TakeStockPlanFullDto;
import com.lframework.xingyun.sc.dto.stock.take.plan.TakeStockPlanSelectorDto;
import com.lframework.xingyun.sc.entity.TakeStockPlan;

import com.lframework.xingyun.sc.vo.stock.take.plan.TakeStockPlanSelectorVo;
import com.lframework.xingyun.sc.vo.stock.take.plan.QueryTakeStockPlanVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 盘点任务 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface TakeStockPlanMapper extends BaseMapper<TakeStockPlan> {

    /**
     * 查询列表
     * @param vo
     * @return
     */
    List<TakeStockPlanDto> query(@Param("vo") QueryTakeStockPlanVo vo);

    /**
     * 根据ID查询
     */
    TakeStockPlanDto getById(@Param("id") String id);

    /**
     * 选择器
     * @param vo
     * @return
     */
    List<TakeStockPlanSelectorDto> selector(@Param("vo") TakeStockPlanSelectorVo vo);

    /**
     * 根据盘点任务ID查询商品信息
     * @param planId
     * @return
     */
    List<QueryTakeStockPlanProductDto> getProducts(@Param("planId") String planId);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    TakeStockPlanFullDto getDetail(String id);
}
