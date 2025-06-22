package com.lframework.xingyun.settle.dto.sheet;

import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.xingyun.settle.enums.SettleSheetStatus;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class SettleSheetFullDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 单号
   */
  private String code;

  /**
   * 供应商ID
   */
  private String supplierId;

  /**
   * 总金额
   */
  private BigDecimal totalAmount;

  /**
   * 已优惠金额
   */
  private BigDecimal totalDiscountAmount;

  /**
   * 起始日期
   */
  private LocalDate startDate;

  /**
   * 截止日期
   */
  private LocalDate endDate;

  /**
   * 备注
   */
  private String description;

  /**
   * 创建人ID
   */
  private String createBy;

  /**
   * 创建时间
   */
  private LocalDateTime createTime;

  /**
   * 修改人ID
   */
  private String updateBy;

  /**
   * 修改时间
   */
  private LocalDateTime updateTime;

  /**
   * 审核人
   */
  private String approveBy;

  /**
   * 审核时间
   */
  private LocalDateTime approveTime;

  /**
   * 状态
   */
  private SettleSheetStatus status;

  /**
   * 拒绝原因
   */
  private String refuseReason;

  /**
   * 明细
   */
  private List<SheetDetailDto> details;

  @Data
  public static class SheetDetailDto implements BaseDto, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 明细ID
     */
    private String id;

    /**
     * 单据ID
     */
    private String bizId;

    /**
     * 实付金额
     */
    private BigDecimal payAmount;

    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;

    /**
     * 备注
     */
    private String description;
  }
}
