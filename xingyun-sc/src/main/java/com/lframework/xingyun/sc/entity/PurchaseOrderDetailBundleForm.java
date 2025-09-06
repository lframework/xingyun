package com.lframework.xingyun.sc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import java.math.BigDecimal;
import lombok.Data;

@Data
@TableName("tbl_purchase_order_detail_bundle_form")
public class PurchaseOrderDetailBundleForm extends PurchaseOrderDetailBundle implements BaseDto {

  private static final long serialVersionUID = 1L;
}
