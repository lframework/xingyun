package com.lframework.xingyun.sc.vo.stock.adjust.stock;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.xingyun.sc.enums.StockAdjustSheetBizType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateStockAdjustSheetVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 仓库ID
   */
  @Schema(description = "仓库ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "请输入仓库ID！")
  private String scId;

  /**
   * 业务类型
   */
  @Schema(description = "业务类型", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "业务类型不能为空！")
  @IsEnum(message = "业务类型格式错误！", enumClass = StockAdjustSheetBizType.class)
  private Integer bizType;

  /**
   * 调整原因ID
   */
  @Schema(description = "调整原因ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "调整原因ID不能为空！")
  private String reasonId;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  /**
   * 商品信息
   */
  @Schema(description = "商品信息", requiredMode = Schema.RequiredMode.REQUIRED)
  @Valid
  @NotEmpty(message = "请录入商品！")
  private List<StockAdjustProductVo> products;

  public void validate() {

    int orderNo = 1;
    for (StockAdjustProductVo product : this.products) {
      if (NumberUtil.le(product.getStockNum(), BigDecimal.ZERO)) {
        throw new DefaultClientException("第" + orderNo + "行商品的调整库存数量必须大于0！");
      }
      if (!NumberUtil.isNumberPrecision(product.getStockNum(), 8)) {
        throw new DefaultClientException("第" + orderNo + "行商品的调整库存数量最多允许8位小数！");
      }
    }
  }
}
