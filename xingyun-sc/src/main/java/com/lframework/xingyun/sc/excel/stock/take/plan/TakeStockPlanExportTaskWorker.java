package com.lframework.xingyun.sc.excel.stock.take.plan;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.utils.JsonUtil;
import com.lframework.starter.mq.core.components.export.ExportTaskWorker;
import com.lframework.xingyun.sc.entity.TakeStockPlan;
import com.lframework.xingyun.sc.service.stock.take.TakeStockPlanService;
import com.lframework.xingyun.sc.vo.stock.take.plan.QueryTakeStockPlanVo;

public class TakeStockPlanExportTaskWorker implements
    ExportTaskWorker<QueryTakeStockPlanVo, TakeStockPlan, TakeStockPlanExportModel> {

  @Override
  public QueryTakeStockPlanVo parseParams(String json) {
    return JsonUtil.parseObject(json, QueryTakeStockPlanVo.class);
  }

  @Override
  public PageResult<TakeStockPlan> getDataList(int pageIndex, int pageSize,
      QueryTakeStockPlanVo params) {

    TakeStockPlanService takeStockPlanService = ApplicationUtil.getBean(
        TakeStockPlanService.class);

    return takeStockPlanService.query(pageIndex, pageSize, params);
  }

  @Override
  public TakeStockPlanExportModel exportData(TakeStockPlan data) {
    return new TakeStockPlanExportModel(data);
  }

  @Override
  public Class<TakeStockPlanExportModel> getModelClass() {
    return TakeStockPlanExportModel.class;
  }
}
