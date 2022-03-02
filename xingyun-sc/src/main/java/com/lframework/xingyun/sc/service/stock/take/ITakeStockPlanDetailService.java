package com.lframework.xingyun.sc.service.stock.take;

import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.sc.dto.stock.take.plan.GetTakeStockPlanDetailProductDto;
import com.lframework.xingyun.sc.dto.stock.take.plan.TakeStockPlanDetailDto;

import java.util.List;

public interface ITakeStockPlanDetailService extends BaseService {

    /**
     * 根据盘点任务ID、商品ID查询
     * @param planId
     * @param productId
     * @return
     */
    GetTakeStockPlanDetailProductDto getByPlanIdAndProductId(String planId, String productId);

    /**
     * 单品盘点-保存盘点任务明细
     * @param planId
     * @param productIds
     */
    void savePlanDetailBySimple(String planId, List<String> productIds);

    /**
     * 根据盘点任务ID查询
     * @param planId
     * @return
     */
    List<TakeStockPlanDetailDto> getDetailsByPlanId(String planId);

    /**
     * 更新盘点数量
     * @param planId
     * @param productId
     * @param num
     */
    void updateOriTakeNum(String planId, String productId, Integer num);
}
