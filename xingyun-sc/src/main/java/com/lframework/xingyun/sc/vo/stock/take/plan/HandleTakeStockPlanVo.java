package com.lframework.xingyun.sc.vo.stock.take.plan;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.xingyun.sc.entity.TakeStockConfig;
import com.lframework.xingyun.sc.service.stock.take.TakeStockConfigService;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class HandleTakeStockPlanVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "ID不能为空！")
  private String id;

  /**
   * 商品信息
   */
  @Schema(description = "商品信息", requiredMode = Schema.RequiredMode.REQUIRED)
  @Valid
  @NotEmpty(message = "商品信息不能为空！")
  private List<ProductVo> products;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  /**
   * 是否允许修改数量
   */
  @Schema(description = "是否允许修改数量")
  private Boolean allowChangeNum;

  /**
   * 是否自动计算数量
   */
  @Schema(description = "是否自动计算数量")
  private Boolean autoChangeStock;

  @Data
  public static class ProductVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    @Schema(description = "商品ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "商品ID不能为空！")
    private String productId;

    /**
     * 修改后盘点数量
     */
    @Schema(description = "修改后盘点数量")
    private BigDecimal takeNum;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String description;
  }

  public void validate() {
    TakeStockConfigService takeStockConfigService = ApplicationUtil.getBean(
        TakeStockConfigService.class);
    TakeStockConfig config = takeStockConfigService.get();

    if (config.getAllowChangeNum()) {
      int orderNo = 1;
      for (ProductVo product : this.products) {
        if (product.getTakeNum() == null) {
          throw new DefaultClientException("第" + orderNo + "行商品修改后盘点数量不能为空！");
        }

        if (NumberUtil.lt(product.getTakeNum(), BigDecimal.ZERO)) {
          throw new DefaultClientException("第" + orderNo + "行商品修改后盘点数量不能小于0！");
        }

        if (!NumberUtil.isNumberPrecision(product.getTakeNum(), 8)) {
          throw new DefaultClientException("第" + orderNo + "行商品修改后盘点数量最多允许8位小数！");
        }

        orderNo++;
      }
    }
  }
}
