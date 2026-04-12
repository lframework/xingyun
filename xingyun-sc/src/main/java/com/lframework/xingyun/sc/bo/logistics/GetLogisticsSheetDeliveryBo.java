package com.lframework.xingyun.sc.bo.logistics;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.sc.entity.LogisticsSheet;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class GetLogisticsSheetDeliveryBo extends BaseBo<LogisticsSheet> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 物流单号
   */
  @Schema(description = "物流单号")
  private String logisticsNo;

  /**
   * 物流费
   */
  @Schema(description = "物流费")
  private BigDecimal totalAmount;

  public GetLogisticsSheetDeliveryBo() {

  }

  public GetLogisticsSheetDeliveryBo(LogisticsSheet dto) {

    super(dto);
  }

  @Override
  public BaseBo<LogisticsSheet> convert(LogisticsSheet dto) {

    return super.convert(dto);
  }
}
