package com.lframework.xingyun.sc.dto.logistics;

import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.sc.enums.LogisticsSheetDetailBizType;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class LogisticsSheetBizOrderDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 业务单据ID
   */
  private String bizId;

  /**
   * 业务单据号
   */
  private String bizCode;

  /**
   * 仓库ID
   */
  private String scId;

  /**
   * 收货人ID
   */
  private String receiverId;

  /**
   * 业务类型
   */
  private LogisticsSheetDetailBizType bizType;

  /**
   * 创建人
   */
  private String createBy;

  /**
   * 创建时间
   */
  private LocalDateTime createTime;
}
