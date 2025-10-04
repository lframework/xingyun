package com.lframework.xingyun.sc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import com.lframework.xingyun.sc.enums.SettleStatus;
import java.math.BigDecimal;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-10-26
 */
@Data
@TableName("tbl_retail_out_sheet_detail")
public class RetailOutSheetDetail extends BaseEntity implements BaseDto {

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
  private BigDecimal orderNum;

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
   * 已退货数量
   */
  private BigDecimal returnNum;

  /**
   * 组合商品原始明细ID
   */
  private String oriBundleDetailId;

  /**
   * 总金额
   */
  private BigDecimal taxAmount;
}
