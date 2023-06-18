package com.lframework.xingyun.sc.bo.stock.adjust.stock.reason;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.sc.entity.StockAdjustReason;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetStockAdjustReasonBo extends BaseBo<StockAdjustReason> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 岗位编号
   */
  @ApiModelProperty("岗位编号")
  private String code;

  /**
   * 岗位名称
   */
  @ApiModelProperty("岗位名称")
  private String name;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  public GetStockAdjustReasonBo() {

  }

  public GetStockAdjustReasonBo(StockAdjustReason dto) {

    super(dto);
  }
}
