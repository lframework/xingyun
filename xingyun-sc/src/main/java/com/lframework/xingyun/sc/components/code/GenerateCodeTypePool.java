package com.lframework.xingyun.sc.components.code;

import com.lframework.starter.web.components.code.GenerateCodeType;

public interface GenerateCodeTypePool {

  /**
   * 采购订单
   */
  GenerateCodeType PURCHASE_ORDER = GenerateCodeType.FLOW;

  /**
   * 采购收货单
   */
  GenerateCodeType RECEIVE_SHEET = GenerateCodeType.FLOW;

  /**
   * 采购退单
   */
  GenerateCodeType PURCHASE_RETURN = GenerateCodeType.FLOW;

  /**
   * 销售订单
   */
  GenerateCodeType SALE_ORDER = GenerateCodeType.FLOW;

  /**
   * 销售出库单
   */
  GenerateCodeType SALE_OUT_SHEET = GenerateCodeType.FLOW;

  /**
   * 销售退货单
   */
  GenerateCodeType SALE_RETURN = GenerateCodeType.FLOW;

  /**
   * 零售出库单
   */
  GenerateCodeType RETAIL_OUT_SHEET = GenerateCodeType.FLOW;

  /**
   * 零售退货单
   */
  GenerateCodeType RETAIL_RETURN = GenerateCodeType.FLOW;

  /**
   * 预先盘点单
   */
  GenerateCodeType PRE_TAKE_STOCK_SHEET = GenerateCodeType.FLOW;

  /**
   * 盘点任务
   */
  GenerateCodeType TAKE_STOCK_PLAN = GenerateCodeType.FLOW;

  /**
   * 盘点单
   */
  GenerateCodeType TAKE_STOCK_SHEET = GenerateCodeType.FLOW;

  /**
   * 库存成本调整单
   */
  GenerateCodeType STOCK_COST_ADJUST_SHEET = GenerateCodeType.FLOW;

  /**
   * 库存调整单
   */
  GenerateCodeType STOCK_ADJUST_SHEET = GenerateCodeType.FLOW;
}
