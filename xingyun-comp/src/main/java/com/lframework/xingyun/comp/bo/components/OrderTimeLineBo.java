package com.lframework.xingyun.comp.bo.components;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.inner.entity.OrderTimeLine;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author zmj
 * @since 2022/8/10
 */
@Data
public class OrderTimeLineBo extends BaseBo<OrderTimeLine> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 订单ID
   */
  @Schema(description = "订单ID")
  private String orderId;

  /**
   * 描述内容
   */
  @Schema(description = "描述内容")
  private String content;

  /**
   * 创建人
   */
  @Schema(description = "创建人")
  private String createBy;

  /**
   * 创建时间
   */
  @Schema(description = "创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  /**
   * 业务类型
   */
  @Schema(description = "业务类型")
  private Integer bizType;

  public OrderTimeLineBo() {
  }

  public OrderTimeLineBo(OrderTimeLine dto) {
    super(dto);
  }

  @Override
  protected void afterInit(OrderTimeLine dto) {

    this.bizType = dto.getBizType();
  }
}
