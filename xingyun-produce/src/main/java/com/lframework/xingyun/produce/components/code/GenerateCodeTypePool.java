package com.lframework.xingyun.produce.components.code;

/**
 * 生产管理单据编号类型
 *
 * @author zmj
 */
public interface GenerateCodeTypePool {

  /**
   * BOM编号
   */
  Integer BOM = 400;

  /**
   * 组装单号
   */
  Integer ASSEMBLE_SHEET = 215;

  /**
   * 生产工单号
   */
  Integer PRODUCTION_ORDER = 216;
}
