package com.lframework.xingyun.sc.facade.vo.stock;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StockCostAdjustVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 商品ID
   */
  @ApiModelProperty(value = "商品ID", required = true)
  @NotBlank(message = "商品ID不能为空！")
  private String productId;

  /**
   * 仓库ID
   */
  @ApiModelProperty(value = "仓库ID", required = true)
  @NotBlank(message = "仓库ID不能为空！")
  private String scId;

  /**
   * 含税成本价
   */
  @ApiModelProperty(value = "含税成本价", required = true)
  @NotNull(message = "含税成本价不能为空！")
  @Min(message = "含税成本价不能小于0！", value = 0)
  private BigDecimal taxPrice;

  /**
   * 税率（%）
   */
  @ApiModelProperty(value = "税率（%）", required = true)
  @NotNull(message = "税率（%）不能为空！")
  @Min(message = "税率（%）不能小于0！", value = 0)
  private BigDecimal taxRate;

  /**
   * 出库时间
   */
  @ApiModelProperty("出库时间")
  private LocalDateTime createTime = LocalDateTime.now();

  /**
   * 业务单据ID
   */
  @ApiModelProperty("业务单据ID")
  private String bizId;

  /**
   * 业务单据明细ID
   */
  @ApiModelProperty("业务单据明细ID")
  private String bizDetailId;

  /**
   * 业务单据号
   */
  @ApiModelProperty("业务单据号")
  private String bizCode;
}
