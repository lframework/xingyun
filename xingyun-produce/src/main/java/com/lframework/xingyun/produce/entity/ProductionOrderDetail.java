package com.lframework.xingyun.produce.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 生产工单明细（BOM 子件快照）
 *
 * @author xingyun
 */
@Data
@TableName("tbl_production_order_detail")
public class ProductionOrderDetail extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  /** ID */
  private String id;

  /** 工单ID */
  private String orderId;

  /** 子件商品ID */
  private String productId;

  /** 子件 SKU ID */
  private String skuId;

  /** BOM 单位用量 */
  private BigDecimal bomNum;

  /** 计划消耗数量（= bomNum × planNum） */
  private BigDecimal requireNum;

  /** 排序号 */
  private Integer orderNo;
}
