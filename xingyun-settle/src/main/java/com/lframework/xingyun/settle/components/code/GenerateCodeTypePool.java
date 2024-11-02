package com.lframework.xingyun.settle.components.code;

public interface GenerateCodeTypePool {

  /**
   * 对账单
   */
  Integer SETTLE_CHECK_SHEET = 300;

  /**
   * 费用单
   */
  Integer SETTLE_FEE_SHEET = 301;

  /**
   * 预付款单
   */
  Integer SETTLE_PRE_SHEET = 302;

  /**
   * 结算单
   */
  Integer SETTLE_SHEET = 303;

  /**
   * 客户对账单
   */
  Integer CUSTOMER_SETTLE_CHECK_SHEET = 304;

  /**
   * 客户费用单
   */
  Integer CUSTOMER_SETTLE_FEE_SHEET = 305;

  /**
   * 客户预付款单
   */
  Integer CUSTOMER_SETTLE_PRE_SHEET = 306;

  /**
   * 客户结算单
   */
  Integer CUSTOMER_SETTLE_SHEET = 307;
}
