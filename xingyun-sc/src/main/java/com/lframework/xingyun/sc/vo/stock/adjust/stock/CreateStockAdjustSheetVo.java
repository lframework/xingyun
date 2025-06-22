package com.lframework.xingyun.sc.vo.stock.adjust.stock;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.xingyun.sc.enums.StockAdjustSheetBizType;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateStockAdjustSheetVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 仓库ID
   */
  @ApiModelProperty(value = "仓库ID", required = true)
  @NotBlank(message = "请输入仓库ID！")
  private String scId;

  /**
   * 业务类型
   */
  @ApiModelProperty(value = "业务类型", required = true)
  @NotNull(message = "业务类型不能为空！")
  @IsEnum(message = "业务类型格式错误！", enumClass = StockAdjustSheetBizType.class)
  private Integer bizType;

  /**
   * 调整原因ID
   */
  @ApiModelProperty(value = "调整原因ID", required = true)
  @NotBlank(message = "调整原因ID不能为空！")
  private String reasonId;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 商品信息
   */
  @ApiModelProperty(value = "商品信息", required = true)
  @Valid
  @NotEmpty(message = "请录入商品！")
  private List<StockAdjustProductVo> products;

  public void validate() {

    int orderNo = 1;
    for (StockAdjustProductVo product : this.products) {
      if (NumberUtil.le(product.getStockNum(), BigDecimal.ZERO)) {
        throw new DefaultClientException("第" + orderNo + "行商品的调整库存数量必须大于0！");
      }
    }
  }
}
