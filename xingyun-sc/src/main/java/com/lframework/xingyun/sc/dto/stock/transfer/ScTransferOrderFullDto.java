package com.lframework.xingyun.sc.dto.stock.transfer;

import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.xingyun.sc.enums.ScTransferOrderStatus;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

/**
 * <p>
 * 仓库调拨单详情 Dto
 * </p>
 *
 * @author zmj
 */
@Data
public class ScTransferOrderFullDto implements BaseDto, Serializable {

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
   * 转出仓库ID
   */
  private String sourceScId;

  /**
   * 转入仓库ID
   */
  private String targetScId;

  /**
   * 调拨数量
   */
  private Integer totalNum;

  /**
   * 调拨成本金额
   */
  private BigDecimal totalAmount;

  /**
   * 状态
   */
  private ScTransferOrderStatus status;

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
     * 调拨数量
     */
    private Integer transferNum;

    /**
     * 已收货数量
     */
    private Integer receiveNum;

    /**
     * 备注
     */
    private String description;
  }

}
