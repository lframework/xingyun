package com.lframework.xingyun.produce.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 组装单明细（子件）
 *
 * @author zmj
 */
@Data
@TableName("tbl_assemble_sheet_detail")
public class AssembleSheetDetail extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  public static final String CACHE_NAME = "AssembleSheetDetail";

  /**
   * ID
   */
  private String id;

  /**
   * 单据ID
   */
  private String sheetId;

  /**
   * 子件商品ID
   */
  private String productId;

  /**
   * 子件SKU ID
   */
  private String skuId;

  /**
   * 单位用量（自BOM带出）
   */
  private BigDecimal num;

  /**
   * 本次消耗数量（= 单位用量 × 组装数量）
   */
  private BigDecimal stockNum;

  /**
   * 备注
   */
  private String description;

  /**
   * 排序
   */
  private Integer orderNo;
}
