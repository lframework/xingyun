package com.lframework.xingyun.sc.service.purchase;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.sc.entity.PurchaseRequisition;
import com.lframework.xingyun.sc.vo.purchase.requisition.AdjustRequisitionDetailVo;
import com.lframework.xingyun.sc.vo.purchase.requisition.QueryPurchaseRequisitionVo;

public interface PurchaseRequisitionService extends BaseMpService<PurchaseRequisition> {

  PageResult<PurchaseRequisition> query(Integer pageIndex, Integer pageSize,
      QueryPurchaseRequisitionVo vo);

  /**
   * 由生产工单 ID 自动生成采购建议单（展开工单明细 → 建议单明细）
   * 若该工单已存在未取消的建议单则抛出异常
   */
  String generateFromProductionOrder(String productionOrderId);

  /** 确认建议单：待确认 → 已确认 */
  void confirm(String id);

  /** 调整明细建议采购数量 */
  void adjustDetail(AdjustRequisitionDetailVo vo);

  /** 转采购订单（标记状态，前端跳转到采购订单新建页预填） */
  String convertToPurchaseOrder(String id);

  /** 取消建议单 */
  void cancel(String id);
}
