package com.lframework.xingyun.sc.dto.purchase.receive;

import com.lframework.starter.web.core.dto.BaseDto;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;

@Data
public class GetPaymentDateDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 是否允许修改付款日期
   */
  private Boolean allowModify;

  /**
   * 默认付款日期
   */
  private LocalDate paymentDate;
}
