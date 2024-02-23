package com.lframework.xingyun.sc.dto.stock.adjust.stock;

import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.sc.enums.StockAdjustSheetBizType;
import com.lframework.xingyun.sc.enums.StockAdjustSheetStatus;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

/**
 * <p>
 * 库存成本调整单详情 Dto
 * </p>
 *
 * @author zmj
 */
@Data
public class StockAdjustSheetFullDto implements BaseDto, Serializable {

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
   * 业务类型
   */
  private StockAdjustSheetBizType bizType;

  /**
   * 调整原因ID
   */
  private String reasonId;

  /**
   * 状态
   */
  private StockAdjustSheetStatus status;

  /**
   * 备注
   */
  private String description;

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

  /**
   * 明细
   */
  private List<DetailDto> details;

  @Data
  public static class DetailDto implements BaseDto, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 调整库存数量
     */
    private Integer stockNum;

    /**
     * 备注
     */
    private String description;
  }

}
