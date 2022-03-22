package com.lframework.xingyun.sc.dto.sale.out;

import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.sc.enums.SettleStatus;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class SaleOutSheetDetailDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 出库单ID
   */
  private String sheetId;

  /**
   * 商品ID
   */
  private String productId;

  /**
   * 出库数量
   */
  private Integer orderNum;

  /**
   * 原价
   */
  private BigDecimal oriPrice;

  /**
   * 现价
   */
  private BigDecimal taxPrice;

  /**
   * 折扣率（%）
   */
  private BigDecimal discountRate;

  /**
   * 是否赠品
   */
  private Boolean isGift;

  /**
   * 税率（%）
   */
  private BigDecimal taxRate;

  /**
   * 备注
   */
  private String description;

  /**
   * 排序编号
   */
  private Integer orderNo;

  /**
   * 结算状态
   */
  private SettleStatus settleStatus;

  /**
   * 销售订单明细ID
   */
  private String saleOrderDetailId;

  /**
   * 已退货数量
   */
  private Integer returnNum;
}
