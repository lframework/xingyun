package com.lframework.xingyun.basedata.vo.stockcell.product;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateStockCellProductVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 仓位ID
   */
  @Schema(description = "仓位ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "仓位ID不能为空！")
  private String stockCellId;

  /**
   * SKU ID
   */
  @Schema(description = "SKU ID")
  @NotEmpty(message = "请选择商品！")
  private List<String> skuIds;
}
