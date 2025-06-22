package com.lframework.xingyun.sc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import java.math.BigDecimal;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-10-09
 */
@Data
@TableName("tbl_receive_sheet_detail")
public class ReceiveSheetDetail extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 收货单ID
   */
  private String sheetId;

  /**
   * 商品ID
   */
  private String productId;

  /**
   * 采购数量
   */
  private Integer orderNum;

  /**
   * 采购价
   */
  private BigDecimal taxPrice;

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
   * 采购订单明细ID
   */
  private String purchaseOrderDetailId;

  /**
   * 已退货数量
   */
  private Integer returnNum;


}
