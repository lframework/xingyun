package com.lframework.xingyun.sc.vo.stock.lot;

import com.lframework.starter.web.core.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryProductLotVo extends PageVo {

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
   * 商品分类ID
   */
  @ApiModelProperty("商品分类ID")
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
   * 生成起始时间
   */
  @ApiModelProperty("生成起始时间")
  private LocalDateTime createStartTime;

  /**
   * 生成截止时间
   */
  @ApiModelProperty("生成截止时间")
  private LocalDateTime createEndTime;
}
