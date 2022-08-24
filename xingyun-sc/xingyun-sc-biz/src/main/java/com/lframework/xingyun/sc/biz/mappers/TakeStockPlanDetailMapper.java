package com.lframework.xingyun.sc.biz.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.facade.dto.stock.take.plan.GetTakeStockPlanDetailProductDto;
import com.lframework.xingyun.sc.facade.entity.TakeStockPlanDetail;
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
   * 根据盘点任务ID、商品ID查询
   *
   * @param planId
   * @param productId
   * @return
   */
  GetTakeStockPlanDetailProductDto getByPlanIdAndProductId(@Param("planId") String planId,
      @Param("productId") String productId);

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
  void updateOriTakeNum(@Param("planId") String planId, @Param("productId") String productId,
      @Param("num") Integer num);

  /**
   * 增加进项数量
   *
   * @param scId
   * @param productId
   * @param num
   */
  void addTotalInNum(@Param("scId") String scId, @Param("productId") String productId,
      @Param("num") Integer num);

  /**
   * 增加出项数量
   *
   * @param scId
   * @param productId
   * @param num
   */
  void addTotalOutNum(@Param("scId") String scId, @Param("productId") String productId,
      @Param("num") Integer num);
}
