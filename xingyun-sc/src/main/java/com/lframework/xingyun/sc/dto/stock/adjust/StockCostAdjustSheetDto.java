package com.lframework.xingyun.sc.dto.stock.adjust;

import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.sc.enums.StockCostAdjustSheetStatus;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 库存成本调整单 Dto
 * </p>
 *
 * @author zmj
 */
@Data
public class StockCostAdjustSheetDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 业务单据号
   */
  private String code;

  /**
   * 仓库ID
   */
  private String scId;

  /**
   * 调价品种数
   */
  private Integer productNum;

  /**
   * 库存调价差额
   */
  private BigDecimal diffAmount;

  /**
   * 状态
   */
  private StockCostAdjustSheetStatus status;

  /**
   * 备注
   */
  private String description;

  /**
   * 创建人
   */
  private String createBy;

  /**
   * 创建时间
   */
  private LocalDateTime createTime;

  /**
   * 修改人
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
   * 拒绝原因
   */
  private String refuseReason;

}
