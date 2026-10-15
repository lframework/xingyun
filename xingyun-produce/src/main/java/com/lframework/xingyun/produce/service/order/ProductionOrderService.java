package com.lframework.xingyun.produce.service.order;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.produce.entity.ProductionOrder;
import com.lframework.xingyun.produce.vo.order.CompleteProductionOrderVo;
import com.lframework.xingyun.produce.vo.order.CreateProductionOrderVo;
import com.lframework.xingyun.produce.vo.order.QueryProductionOrderVo;
import com.lframework.xingyun.produce.vo.order.UpdateProductionOrderVo;

public interface ProductionOrderService extends BaseMpService<ProductionOrder> {

  PageResult<ProductionOrder> query(Integer pageIndex, Integer pageSize, QueryProductionOrderVo vo);

  /** 新建工单，自动展开 BOM 生成明细 */
  String create(CreateProductionOrderVo vo);

  /** 修改排产日期和负责人（仅待排产/已排产状态允许） */
  void update(UpdateProductionOrderVo vo);

  /** 开始生产：待排产→生产中 */
  void start(String id);

  /** 完工：生产中→已完工，填写实际数量 */
  void complete(CompleteProductionOrderVo vo);

  /** 取消工单 */
  void cancel(String id);

  void deleteById(String id);
}
