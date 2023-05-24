package com.lframework.xingyun.sc.vo.stock;

import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
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
public class AddProductStockVo implements BaseVo, Serializable {

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
   * 含税成本总金额，如果为null则代表不计算均价入库
   */
  @ApiModelProperty("含税成本价，如果为null则代表不计算均价入库")
  @Min(message = "含税成本价不能小于0！", value = 0)
  private BigDecimal taxPrice;

  /**
   * 默认的含税成本价，如果不为null则代表：入库时商品没有库存（没有均价），按照此成本价入库 如果与taxPrice同时为null，那么当入库时没有库存就会报错
   */
  @ApiModelProperty("默认的含税成本价，如果不为null则代表：入库时商品没有库存（没有均价），按照此成本价入库 如果与taxPrice同时为null，那么当入库时没有库存就会报错")
  @Min(message = "默认的含税成本价不能小于0！", value = 0)
  private BigDecimal defaultTaxPrice;

  /**
   * 入库时间
   */
  @ApiModelProperty("入库时间")
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

  /**
   * 业务类型
   */
  @ApiModelProperty(value = "业务类型", required = true)
  @NotNull(message = "业务类型不能为空！")
  @IsEnum(message = "业务类型不正确！", enumClass = ProductStockBizType.class)
  private Integer bizType;
}
