package com.lframework.xingyun.sc.service.stock.take;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.sc.dto.stock.take.plan.*;
import com.lframework.xingyun.sc.vo.stock.take.plan.*;

import java.util.List;

/**
 * 盘点任务 Service
 * @author zmj
 */
public interface ITakeStockPlanService extends BaseService {

    /**
     * 查询列表
     * @return
     */
    PageResult<TakeStockPlanDto> query(Integer pageIndex, Integer pageSize, QueryTakeStockPlanVo vo);

    /**
     * 查询列表
     * @param vo
     * @return
     */
    List<TakeStockPlanDto> query(QueryTakeStockPlanVo vo);

    /**
     * 选择器
     * @param pageIndex
     * @param pageSize
     * @param vo
     * @return
     */
    PageResult<TakeStockPlanSelectorDto> selector(Integer pageIndex, Integer pageSize, TakeStockPlanSelectorVo vo);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    TakeStockPlanDto getById(String id);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    TakeStockPlanFullDto getDetail(String id);

    /**
     * 创建
     * @param vo
     * @return
     */
    String create(CreateTakeStockPlanVo vo);

    /**
     * 修改
     * @param vo
     */
    void update(UpdateTakeStockPlanVo vo);

    /**
     * 根据盘点任务ID查询商品信息
     * @param planId
     * @return
     */
    List<QueryTakeStockPlanProductDto> getProducts(String planId);

    /**
     * 差异生成
     * @param id
     */
    void createDiff(String id);

    /**
     * 差异处理
     * @param vo
     */
    void handleDiff(HandleTakeStockPlanVo vo);

    /**
     * 作废
     * @param vo
     */
    void cancel(CancelTakeStockPlanVo vo);

    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);

}
