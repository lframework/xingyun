package com.lframework.xingyun.sc.vo.purchase;

import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.PageVo;
import com.lframework.xingyun.sc.enums.PurchaseOrderStatus;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PurchaseOrderSelectorVo extends PageVo {

  private static final long serialVersionUID = 1L;

  /**
   * 单号
   */
  @ApiModelProperty("单号")
  private String code;

  /**
   * 供应商ID
   */
  @ApiModelProperty("供应商ID")
  private String supplierId;

  /**
   * 仓库ID
   */
  @ApiModelProperty("仓库ID")
  private String scId;

  /**
   * 操作人ID
   */
  @ApiModelProperty("操作人ID")
  private String createBy;

  /**
   * 操作起始时间
   */
  @ApiModelProperty("操作起始时间")
  private LocalDateTime createStartTime;

  /**
   * 操作截止时间
   */
  @ApiModelProperty("操作截止时间")
  private LocalDateTime createEndTime;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  @IsEnum(message = "状态格式不正确！", enumClass = PurchaseOrderStatus.class)
  private Integer status;
}
