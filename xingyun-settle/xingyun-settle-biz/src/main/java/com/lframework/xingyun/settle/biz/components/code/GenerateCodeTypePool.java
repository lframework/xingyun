package com.lframework.xingyun.settle.biz.components.code;

import com.lframework.starter.web.components.code.GenerateCodeType;

public interface GenerateCodeTypePool {

    /**
     * 对账单
     */
    GenerateCodeType SETTLE_CHECK_SHEET = GenerateCodeType.DEFAULT;

    /**
     * 费用单
     */
    GenerateCodeType SETTLE_FEE_SHEET = GenerateCodeType.DEFAULT;

  /**
   * 预付款单
   */
  GenerateCodeType SETTLE_PRE_SHEET = GenerateCodeType.DEFAULT;

  /**
   * 结算单
   */
  GenerateCodeType SETTLE_SHEET = GenerateCodeType.DEFAULT;

  /**
   * 客户对账单
   */
  GenerateCodeType CUSTOMER_SETTLE_CHECK_SHEET = GenerateCodeType.DEFAULT;

  /**
   * 客户费用单
   */
  GenerateCodeType CUSTOMER_SETTLE_FEE_SHEET = GenerateCodeType.DEFAULT;

  /**
   * 客户预付款单
   */
  GenerateCodeType CUSTOMER_SETTLE_PRE_SHEET = GenerateCodeType.DEFAULT;

  /**
   * 客户结算单
   */
  GenerateCodeType CUSTOMER_SETTLE_SHEET = GenerateCodeType.DEFAULT;
}
