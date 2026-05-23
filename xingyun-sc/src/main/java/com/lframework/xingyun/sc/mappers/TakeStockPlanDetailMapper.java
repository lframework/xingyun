package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.xingyun.sc.dto.stock.take.plan.GetTakeStockPlanDetailProductDto;
import com.lframework.xingyun.sc.entity.TakeStockPlanDetail;
import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 盘点任务详情 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface TakeStockPlanDetailMapper extends BaseMapper<TakeStockPlanDetail> {

  /**
   * 根据盘点任务ID、SKU ID查询
   *
   * @param planId
   * @param skuId
   * @return
   */
  GetTakeStockPlanDetailProductDto getByPlanIdAndProductId(@Param("planId") String planId,
      @Param("skuId") String skuId);

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
   * @param skuId
   * @param num
   */
  void updateOriTakeNum(@Param("planId") String planId, @Param("skuId") String skuId,
      @Param("num") BigDecimal num);

  /**
   * 增加进项数量
   *
   * @param scId
   * @param skuId
   * @param num
   */
  void addTotalInNum(@Param("scId") String scId, @Param("skuId") String skuId,
      @Param("num") BigDecimal num);

  /**
   * 增加出项数量
   *
   * @param scId
   * @param skuId
   * @param num
   */
  void addTotalOutNum(@Param("scId") String scId, @Param("skuId") String skuId,
      @Param("num") BigDecimal num);

  /**
   * 根据盘点任务ID调整库存数量
   * @param planId
   */
  void adjustStockNum(@Param("planId") String planId);
}
