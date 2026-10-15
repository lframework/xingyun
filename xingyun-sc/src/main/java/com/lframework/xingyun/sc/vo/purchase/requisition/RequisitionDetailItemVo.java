package com.lframework.xingyun.sc.vo.purchase.requisition;

import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequisitionDetailItemVo implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotBlank(message = "商品ID不能为空！")
  private String productId;

  @NotBlank(message = "SKU ID不能为空！")
  private String skuId;

  @NotNull(message = "需求数量不能为空！")
  private BigDecimal requireNum;
}
