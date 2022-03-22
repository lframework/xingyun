package com.lframework.xingyun.sc.dto.purchase.receive;

import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.sc.enums.ReceiveSheetStatus;
import com.lframework.xingyun.sc.enums.SettleStatus;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class ReceiveSheetFullDto implements BaseDto, Serializable {

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
   * 仓库ID
   */
  private String scId;

  /**
   * 供应商ID
   */
  private String supplierId;

  /**
   * 采购员ID
   */
  private String purchaserId;

  /**
   * 采购订单ID
   */
  private String purchaseOrderId;

  /**
   * 付款日期
   */
  private LocalDate paymentDate;

  /**
   * 到货日期
   */
  private LocalDate receiveDate;

  /**
   * 采购数量
   */
  private Integer totalNum;

  /**
   * 赠品数量
   */
  private Integer totalGiftNum;

  /**
   * 采购金额
   */
  private BigDecimal totalAmount;

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
   * 状态
   */
  private ReceiveSheetStatus status;

  /**
   * 拒绝原因
   */
  private String refuseReason;

  /**
   * 结算状态
   */
  private SettleStatus settleStatus;

  /**
   * 订单明细
   */
  private List<OrderDetailDto> details;

  @Data
  public static class OrderDetailDto implements BaseDto, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 明细ID
     */
    private String id;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 收货数量
     */
    private Integer orderNum;

    /**
     * 采购价
     */
    private BigDecimal taxPrice;

    /**
     * 是否赠品
     */
    private Boolean isGift;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 备注
     */
    private String description;

    /**
     * 排序编号
     */
    private Integer orderNo;

    /**
     * 采购订单明细ID
     */
    private String purchaseOrderDetailId;
  }
}
