package com.lframework.xingyun.sc.facade.dto.stock.take.pre;

import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.sc.facade.enums.PreTakeStockSheetStatus;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

/**
 * <p>
 * 预先盘点单 Dto
 * </p>
 *
 * @author zmj
 */
@Data
public class PreTakeStockSheetFullDto implements BaseDto, Serializable {

  public static final String CACHE_NAME = "PreTakeStockSheetFullDto";
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
   * 盘点状态
   */
  private PreTakeStockSheetStatus takeStatus;

  /**
   * 备注
   */
  private String description;

  /**
   * 操作人ID
   */
  private String updateBy;

  /**
   * 操作时间
   */
  private LocalDateTime updateTime;

  /**
   * 明细
   */
  private List<SheetDetailDto> details;

  @Data
  public static class SheetDetailDto implements BaseDto, Serializable {

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
     * 初盘数量
     */
    private Integer firstNum;

    /**
     * 复盘数量
     */
    private Integer secondNum;

    /**
     * 抽盘数量
     */
    private Integer randNum;
  }
}
