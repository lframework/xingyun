package com.lframework.xingyun.sc.dto.stock.take.plan;

import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.sc.enums.TakeStockPlanStatus;
import com.lframework.xingyun.sc.enums.TakeStockPlanType;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 盘点任务选择器 Dto
 * </p>
 *
 * @author zmj
 */
@Data
public class TakeStockPlanSelectorDto implements BaseDto, Serializable {

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
   * 盘点类别
   */
  private TakeStockPlanType takeType;

  /**
   * 盘点状态
   */
  private TakeStockPlanStatus takeStatus;

  /**
   * 创建时间
   */
  private LocalDateTime createTime;

  /**
   * 修改时间
   */
  private LocalDateTime updateTime;
}
