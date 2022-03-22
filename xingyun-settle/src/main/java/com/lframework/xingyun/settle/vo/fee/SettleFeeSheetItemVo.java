package com.lframework.xingyun.settle.vo.fee;

import com.lframework.starter.web.vo.BaseVo;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class SettleFeeSheetItemVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 项目ID
   */
  private String id;

  /**
   * 金额
   */
  private BigDecimal amount;
}
