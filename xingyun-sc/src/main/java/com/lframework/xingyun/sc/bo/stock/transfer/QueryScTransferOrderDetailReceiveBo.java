package com.lframework.xingyun.sc.bo.stock.transfer;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.sc.entity.ScTransferOrderDetailReceive;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryScTransferOrderDetailReceiveBo extends BaseBo<ScTransferOrderDetailReceive> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 收货数量
   */
  @Schema(description = "收货数量")
  private BigDecimal receiveNum;

  /**
   * 创建人
   */
  @Schema(description = "创建人")
  private String createBy;

  /**
   * 创建时间
   */
  @Schema(description = "创建时间")
  private LocalDateTime createTime;

  public QueryScTransferOrderDetailReceiveBo() {
  }

  public QueryScTransferOrderDetailReceiveBo(ScTransferOrderDetailReceive dto) {
    super(dto);
  }
}
