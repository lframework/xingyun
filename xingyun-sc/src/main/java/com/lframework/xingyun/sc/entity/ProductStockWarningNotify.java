package com.lframework.xingyun.sc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.dto.BaseDto;
import com.lframework.starter.web.entity.BaseEntity;
import lombok.Data;

/**
 * <p>
 * 库存预警通知组
 * </p>
 *
 * @author zmj
 */
@Data
@TableName("tbl_product_stock_warning_notify")
public class ProductStockWarningNotify extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 通知组ID
   */
  private String notifyGroupId;
}
