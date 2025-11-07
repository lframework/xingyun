package com.lframework.xingyun.sc.vo.stock.take.plan;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.xingyun.sc.entity.TakeStockConfig;
import com.lframework.xingyun.sc.service.stock.take.TakeStockConfigService;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class HandleTakeStockPlanVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotBlank(message = "ID不能为空！")
  private String id;

  /**
   * 商品信息
   */
  @ApiModelProperty(value = "商品信息", required = true)
  @Valid
  @NotEmpty(message = "商品信息不能为空！")
  private List<ProductVo> products;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 是否允许修改数量
   */
  @ApiModelProperty("是否允许修改数量")
  private Boolean allowChangeNum;

  /**
   * 是否自动计算数量
   */
  @ApiModelProperty("是否自动计算数量")
  private Boolean autoChangeStock;

  @Data
  public static class ProductVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID", required = true)
    @NotBlank(message = "商品ID不能为空！")
    private String productId;

    /**
     * 修改后盘点数量
     */
    @ApiModelProperty(value = "修改后盘点数量")
    private BigDecimal takeNum;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
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
