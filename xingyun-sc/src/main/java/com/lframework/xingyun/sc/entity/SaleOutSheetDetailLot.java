package com.lframework.xingyun.sc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.sc.enums.SettleStatus;
import java.math.BigDecimal;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-11-03
 */
@Data
@TableName("tbl_sale_out_sheet_detail_lot")
public class SaleOutSheetDetailLot extends BaseEntity implements BaseDto {

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
