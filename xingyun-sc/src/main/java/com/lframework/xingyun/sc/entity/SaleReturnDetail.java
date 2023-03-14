package com.lframework.xingyun.sc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.sc.enums.SettleStatus;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-11-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tbl_sale_return_detail")
public class SaleReturnDetail extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 退货单ID
   */
  private String returnId;

  /**
   * 商品ID
   */
  private String productId;

  /**
   * 退货数量
   */
  private Integer returnNum;

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
   * 出库单明细ID
   */
  private String outSheetDetailId;


}
