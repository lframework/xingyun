package com.lframework.xingyun.sc.vo.retail.out;

import com.lframework.starter.web.vo.PageVo;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryRetailOutSheetWithReturnVo extends PageVo {

  private static final long serialVersionUID = 1L;

  /**
   * 单号
   */
  private String code;

  /**
   * 会员ID
   */
  private String memberId;

  /**
   * 仓库ID
   */
  private String scId;

  /**
   * 操作人ID
   */
  private String createBy;

  /**
   * 操作起始时间
   */
  private LocalDateTime createStartTime;

  /**
   * 操作截止时间
   */
  private LocalDateTime createEndTime;
}
