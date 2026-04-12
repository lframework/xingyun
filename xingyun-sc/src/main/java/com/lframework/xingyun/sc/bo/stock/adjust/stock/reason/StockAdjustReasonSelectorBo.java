package com.lframework.xingyun.sc.bo.stock.adjust.stock.reason;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.sc.entity.StockAdjustReason;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class StockAdjustReasonSelectorBo extends BaseBo<StockAdjustReason> {

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

  public StockAdjustReasonSelectorBo() {

  }

  public StockAdjustReasonSelectorBo(StockAdjustReason dto) {

    super(dto);
  }
}
