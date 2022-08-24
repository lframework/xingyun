package com.lframework.xingyun.sc.facade.vo.stock.log;

import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.PageVo;
import com.lframework.xingyun.sc.facade.enums.ProductStockBizType;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryProductStockLogVo extends PageVo {

  private static final long serialVersionUID = 1L;

  /**
   * 仓库ID
   */
  @ApiModelProperty("仓库ID")
  private String scId;

  /**
   * 商品编号
   */
  @ApiModelProperty("商品编号")
  private String productCode;

  /**
   * 商品名称
   */
  @ApiModelProperty("商品名称")
  private String productName;

  /**
   * 商品类目ID
   */
  @ApiModelProperty("商品类目ID")
  private String categoryId;

  /**
   * 商品品牌ID
   */
  @ApiModelProperty("商品品牌ID")
  private String brandId;

  /**
   * 供应商ID
   */
  @ApiModelProperty("供应商ID")
  private String supplierId;

  /**
   * 批次号
   */
  @ApiModelProperty("批次号")
  private String lotCode;

  /**
   * 创建起始时间
   */
  @ApiModelProperty("创建起始时间")
  private LocalDateTime createStartTime;

  /**
   * 创建截止时间
   */
  @ApiModelProperty("创建截止时间")
  private LocalDateTime createEndTime;

  /**
   * 业务类型
   */
  @ApiModelProperty("业务类型")
  @IsEnum(message = "业务类型不存在！", enumClass = ProductStockBizType.class)
  private Integer bizType;
}
