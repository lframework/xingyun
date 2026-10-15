package com.lframework.xingyun.sc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 采购建议单明细
 */
@Data
@TableName("tbl_purchase_requisition_detail")
public class PurchaseRequisitionDetail extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  /** ID */
  private String id;

  /** 建议单ID */
  private String requisitionId;

  /** 商品ID */
  private String productId;

  /** SKU ID */
  private String skuId;

  /** 需求数量（来自工单明细 requireNum） */
  private BigDecimal requireNum;

  /** 建议采购数量（可由操作员调整） */
  private BigDecimal suggestNum;

  /** 排序号 */
  private Integer orderNo;
}
