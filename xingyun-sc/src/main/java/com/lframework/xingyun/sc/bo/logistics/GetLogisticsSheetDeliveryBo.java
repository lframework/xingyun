package com.lframework.xingyun.sc.bo.logistics;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.sc.entity.LogisticsSheet;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class GetLogisticsSheetDeliveryBo extends BaseBo<LogisticsSheet> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 物流单号
   */
  @ApiModelProperty("物流单号")
  private String logisticsNo;

  /**
   * 物流费
   */
  @ApiModelProperty("物流费")
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
