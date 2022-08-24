package com.lframework.xingyun.sc.biz.service.stock.take;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.sc.facade.dto.stock.take.plan.QueryTakeStockPlanProductDto;
import com.lframework.xingyun.sc.facade.dto.stock.take.plan.TakeStockPlanFullDto;
import com.lframework.xingyun.sc.facade.dto.stock.take.plan.TakeStockPlanSelectorDto;
import com.lframework.xingyun.sc.facade.entity.TakeStockPlan;
import com.lframework.xingyun.sc.facade.vo.stock.take.plan.CancelTakeStockPlanVo;
import com.lframework.xingyun.sc.facade.vo.stock.take.plan.CreateTakeStockPlanVo;
import com.lframework.xingyun.sc.facade.vo.stock.take.plan.HandleTakeStockPlanVo;
import com.lframework.xingyun.sc.facade.vo.stock.take.plan.QueryTakeStockPlanVo;
import com.lframework.xingyun.sc.facade.vo.stock.take.plan.TakeStockPlanSelectorVo;
import com.lframework.xingyun.sc.facade.vo.stock.take.plan.UpdateTakeStockPlanVo;
import java.util.List;

/**
 * 盘点任务 Service
 *
 * @author zmj
 */
public interface ITakeStockPlanService extends BaseMpService<TakeStockPlan> {

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<TakeStockPlan> query(Integer pageIndex, Integer pageSize, QueryTakeStockPlanVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<TakeStockPlan> query(QueryTakeStockPlanVo vo);

  /**
   * 选择器
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<TakeStockPlanSelectorDto> selector(Integer pageIndex, Integer pageSize,
      TakeStockPlanSelectorVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  TakeStockPlanFullDto getDetail(String id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateTakeStockPlanVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateTakeStockPlanVo vo);

  /**
   * 根据盘点任务ID查询商品信息
   *
   * @param planId
   * @return
   */
  List<QueryTakeStockPlanProductDto> getProducts(String planId);

  /**
   * 差异生成
   *
   * @param id
   */
  void createDiff(String id);

  /**
   * 差异处理
   *
   * @param vo
   */
  void handleDiff(HandleTakeStockPlanVo vo);

  /**
   * 作废
   *
   * @param vo
   */
  void cancel(CancelTakeStockPlanVo vo);

  /**
   * 根据ID删除
   *
   * @param id
   */
  void deleteById(String id);

}
