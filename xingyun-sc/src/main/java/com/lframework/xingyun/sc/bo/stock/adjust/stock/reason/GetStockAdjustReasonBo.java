package com.lframework.xingyun.sc.bo.stock.adjust.stock.reason;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.sc.entity.StockAdjustReason;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GetStockAdjustReasonBo extends BaseBo<StockAdjustReason> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 编号
   */
  @Schema(description = "编号")
  private String code;

  /**
   * 名称
   */
  @Schema(description = "名称")
  private String name;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  public GetStockAdjustReasonBo() {

  }

  public GetStockAdjustReasonBo(StockAdjustReason dto) {

    super(dto);
  }
}
