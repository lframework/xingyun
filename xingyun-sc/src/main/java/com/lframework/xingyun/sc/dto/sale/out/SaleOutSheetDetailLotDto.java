package com.lframework.xingyun.sc.dto.sale.out;

import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.xingyun.sc.enums.SettleStatus;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class SaleOutSheetDetailLotDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 明细ID
   */
  private String detailId;

  /**
   * 出库数量
   */
  private Integer orderNum;

  /**
   * 已退货数量
   */
  private Integer returnNum;

  /**
   * 含税成本金额
   */
  private BigDecimal costTaxAmount;

  /**
   * 结算状态
   */
  private SettleStatus settleStatus;

  /**
   * 排序编号
   */
  private Integer orderNo;
}
