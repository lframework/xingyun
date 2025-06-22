package com.lframework.xingyun.sc.vo.stock.log;

import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.xingyun.sc.enums.ProductStockBizType;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddLogWithAddStockVo implements BaseVo, Serializable {

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
   * 入库数量
   */
  @ApiModelProperty(value = "入库数量", required = true)
  @NotNull(message = "入库数量不能为空！")
  @Min(message = "入库数量必须大于0！", value = 1)
  private Integer stockNum;

  /**
   * 含税成本总金额
   */
  @ApiModelProperty(value = "含税成本总金额", required = true)
  @NotNull(message = "含税成本总金额不能为空！")
  @Min(message = "含税成本总金额不能小于0！", value = 0)
  private BigDecimal taxAmount;

  /**
   * 原库存数量
   */
  @ApiModelProperty(value = "原库存数量", required = true)
  @NotNull(message = "原库存数量不能为空！")
  private Integer oriStockNum;

  /**
   * 现库存数量
   */
  @ApiModelProperty(value = "现库存数量", required = true)
  @NotNull(message = "现库存数量不能为空！")
  private Integer curStockNum;

  /**
   * 原含税成本价
   */
  @ApiModelProperty(value = "原含税成本价", required = true)
  @NotNull(message = "原含税成本价不能为空！")
  private BigDecimal oriTaxPrice;

  /**
   * 现含税成本价
   */
  @ApiModelProperty(value = "现含税成本价", required = true)
  @NotNull(message = "现含税成本价不能为空！")
  private BigDecimal curTaxPrice;

  /**
   * 创建人ID
   */
  @ApiModelProperty("创建人ID")
  private String createById;

  /**
   * 创建人
   */
  @ApiModelProperty("创建人")
  private String createBy;

  /**
   * 入库时间
   */
  @ApiModelProperty(value = "入库时间", required = true)
  @NotNull(message = "入库时间不能为空！")
  private LocalDateTime createTime;

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

  /**
   * 业务类型
   */
  @ApiModelProperty(value = "业务类型", required = true)
  @NotNull(message = "业务类型不能为空！")
  @IsEnum(message = "业务类型不正确！", enumClass = ProductStockBizType.class)
  private Integer bizType;
}
