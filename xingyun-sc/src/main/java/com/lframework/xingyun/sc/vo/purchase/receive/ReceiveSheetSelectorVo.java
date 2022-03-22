package com.lframework.xingyun.sc.vo.purchase.receive;

import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.PageVo;
import com.lframework.xingyun.sc.enums.ReceiveSheetStatus;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReceiveSheetSelectorVo extends PageVo {

  private static final long serialVersionUID = 1L;

  /**
   * 单号
   */
  private String code;

  /**
   * 供应商ID
   */
  private String supplierId;

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

  /**
   * 状态
   */
  @IsEnum(message = "状态格式不正确！", enumClass = ReceiveSheetStatus.class)
  private Integer status;
}
