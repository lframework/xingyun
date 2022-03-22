package com.lframework.xingyun.settle.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.entity.BaseEntity;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-12-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("settle_pre_sheet_detail")
public class SettlePreSheetDetail extends BaseEntity {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 预付款单ID
   */
  private String sheetId;

  /**
   * 项目ID
   */
  private String itemId;

  /**
   * 金额
   */
  private BigDecimal amount;

  /**
   * 排序编号
   */
  private Integer orderNo;


}
