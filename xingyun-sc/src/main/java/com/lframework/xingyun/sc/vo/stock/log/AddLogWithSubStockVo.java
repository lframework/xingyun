package com.lframework.xingyun.sc.vo.stock.log;

import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import com.lframework.xingyun.sc.enums.ProductStockBizType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddLogWithSubStockVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 批次ID
   */
  @NotBlank(message = "批次ID不能为空！")
  private String lotId;

  /**
   * 商品ID
   */
  @NotBlank(message = "商品ID不能为空！")
  private String productId;

  /**
   * 仓库ID
   */
  @NotBlank(message = "仓库ID不能为空！")
  private String scId;

  /**
   * 出库数量
   */
  @NotNull(message = "出库数量不能为空！")
  @Min(message = "出库数量必须大于0！", value = 1)
  private Integer stockNum;

  /**
   * 含税成本总金额
   */
  @NotNull(message = "含税成本总金额不能为空！")
  @Min(message = "含税成本总金额不能小于0！", value = 0)
  private BigDecimal taxAmount;

  /**
   * 无税成本总金额
   */
  @NotNull(message = "无税成本总金额不能为空！")
  @Min(message = "无税成本总金额不能小于0！", value = 0)
  private BigDecimal unTaxAmount;

  /**
   * 原库存数量
   */
  @NotNull(message = "原库存数量不能为空！")
  private Integer oriStockNum;

  /**
   * 现库存数量
   */
  @NotNull(message = "现库存数量不能为空！")
  private Integer curStockNum;

  /**
   * 原含税成本价
   */
  @NotNull(message = "原含税成本价不能为空！")
  private BigDecimal oriTaxPrice;

  /**
   * 现含税成本价
   */
  @NotNull(message = "现含税成本价不能为空！")
  private BigDecimal curTaxPrice;

  /**
   * 原无税成本价
   */
  @NotNull(message = "原无税成本价不能为空！")
  private BigDecimal oriUnTaxPrice;

  /**
   * 现无税成本价
   */
  @NotNull(message = "现无税成本价不能为空！")
  private BigDecimal curUnTaxPrice;

  /**
   * 创建人ID
   */
  private String createBy;

  /**
   * 出库时间
   */
  @NotNull(message = "出库时间不能为空！")
  private LocalDateTime createTime;

  /**
   * 业务单据ID
   */
  private String bizId;

  /**
   * 业务单据明细ID
   */
  private String bizDetailId;

  /**
   * 业务单据号
   */
  private String bizCode;

  /**
   * 业务类型
   */
  @NotNull(message = "业务类型不能为空！")
  @IsEnum(message = "业务类型不正确！", enumClass = ProductStockBizType.class)
  private Integer bizType;
}
