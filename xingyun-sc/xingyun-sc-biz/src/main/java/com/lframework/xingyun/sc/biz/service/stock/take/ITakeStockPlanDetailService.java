package com.lframework.xingyun.sc.biz.service.stock.take;

import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.sc.facade.dto.stock.take.plan.GetTakeStockPlanDetailProductDto;
import com.lframework.xingyun.sc.facade.entity.TakeStockPlanDetail;
import java.util.List;

public interface ITakeStockPlanDetailService extends BaseMpService<TakeStockPlanDetail> {

  /**
   * 根据盘点任务ID、商品ID查询
   *
   * @param planId
   * @param productId
   * @return
   */
  GetTakeStockPlanDetailProductDto getByPlanIdAndProductId(String planId, String productId);

  /**
   * 单品盘点-保存盘点任务明细
   *
   * @param planId
   * @param productIds
   */
  void savePlanDetailBySimple(String planId, List<String> productIds);

  /**
   * 根据盘点任务ID查询
   *
   * @param planId
   * @return
   */
  List<TakeStockPlanDetail> getDetailsByPlanId(String planId);

  /**
   * 更新盘点数量
   *
   * @param planId
   * @param productId
   * @param num
   */
  void updateOriTakeNum(String planId, String productId, Integer num);
}
