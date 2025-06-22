package com.lframework.xingyun.sc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-09-12
 */
@Data
@TableName("tbl_purchase_order_form")
public class PurchaseOrderForm extends PurchaseOrder implements BaseDto {

  private static final long serialVersionUID = 1L;
}
