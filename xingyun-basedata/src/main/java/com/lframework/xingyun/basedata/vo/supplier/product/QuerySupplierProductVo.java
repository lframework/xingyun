package com.lframework.xingyun.basedata.vo.supplier.product;

import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.starter.web.core.vo.PageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;

@Data
public class QuerySupplierProductVo implements PageVo, BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  @Schema(description = \"供应商ID\")
  private String supplierId;

  @Schema(description = \"商品ID\")
  private String productId;

  @Schema(description = \"状态\")
  private Boolean available;
}