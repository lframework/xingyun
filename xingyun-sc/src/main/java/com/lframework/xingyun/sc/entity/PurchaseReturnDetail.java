package com.lframework.xingyun.sc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import java.math.BigDecimal;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-10-16
 */
@Data
@TableName("tbl_purchase_return_detail")
public class PurchaseReturnDetail extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 收货单ID
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
   * 收货单明细ID
   */
  private String receiveSheetDetailId;


}
