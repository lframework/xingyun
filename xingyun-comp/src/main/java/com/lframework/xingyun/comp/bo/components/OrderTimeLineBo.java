package com.lframework.xingyun.comp.bo.components;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.core.entity.OrderTimeLine;
import io.swagger.annotations.ApiModelProperty;
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
  @ApiModelProperty("ID")
  private String id;

  /**
   * 订单ID
   */
  @ApiModelProperty("订单ID")
  private String orderId;

  /**
   * 描述内容
   */
  @ApiModelProperty("描述内容")
  private String content;

  /**
   * 创建人
   */
  @ApiModelProperty("创建人")
  private String createBy;

  /**
   * 创建时间
   */
  @ApiModelProperty("创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  /**
   * 业务类型
   */
  @ApiModelProperty("业务类型")
  private Integer bizType;

  public OrderTimeLineBo() {
  }

  public OrderTimeLineBo(OrderTimeLine dto) {
    super(dto);
  }

  @Override
  protected void afterInit(OrderTimeLine dto) {

    this.bizType = dto.getBizType().getCode();
  }
}
