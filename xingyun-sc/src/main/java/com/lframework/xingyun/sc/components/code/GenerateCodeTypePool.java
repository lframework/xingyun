package com.lframework.xingyun.sc.components.code;

public interface GenerateCodeTypePool {

  /**
   * 采购订单
   */
  Integer PURCHASE_ORDER = 200;

  /**
   * 采购收货单
   */
  Integer RECEIVE_SHEET = 201;

  /**
   * 采购退单
   */
  Integer PURCHASE_RETURN = 202;

  /**
   * 销售订单
   */
  Integer SALE_ORDER = 203;

  /**
   * 销售出库单
   */
  Integer SALE_OUT_SHEET = 204;

  /**
   * 销售退货单
   */
  Integer SALE_RETURN = 205;

  /**
   * 零售出库单
   */
  Integer RETAIL_OUT_SHEET = 206;

  /**
   * 零售退货单
   */
  Integer RETAIL_RETURN = 207;

  /**
   * 预先盘点单
   */
  Integer PRE_TAKE_STOCK_SHEET = 208;

  /**
   * 盘点任务
   */
  Integer TAKE_STOCK_PLAN = 209;

  /**
   * 盘点单
   */
  Integer TAKE_STOCK_SHEET = 210;

  /**
   * 库存调整单
   */
  Integer STOCK_ADJUST_SHEET = 212;

  /**
   * 仓库调拨单
   */
  Integer SC_TRANSFER_ORDER = 213;

  /**
   * 物流单
   */
  Integer LOGISTICS_SHEET = 214;
}
