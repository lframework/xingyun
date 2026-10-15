package com.lframework.xingyun.sc.bo.purchase.requisition;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.sc.entity.PurchaseRequisition;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryPurchaseRequisitionBo extends BaseBo<PurchaseRequisition> {

  @Schema(description = "ID")
  private String id;

  @Schema(description = "单号")
  private String code;

  @Schema(description = "来源生产工单ID")
  private String productionOrderId;

  @Schema(description = "仓库ID")
  private String scId;

  @Schema(description = "状态")
  private Integer status;

  @Schema(description = "备注")
  private String description;

  @Schema(description = "创建人")
  private String createBy;

  @Schema(description = "创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  public QueryPurchaseRequisitionBo(PurchaseRequisition dto) {
    super(dto);
  }

  @Override
  public BaseBo<PurchaseRequisition> convert(PurchaseRequisition dto) {
    return super.convert(dto, QueryPurchaseRequisitionBo::getStatus);
  }

  @Override
  protected void afterInit(PurchaseRequisition dto) {
    this.status = dto.getStatus().getCode();
  }
}
