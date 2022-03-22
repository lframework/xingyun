package com.lframework.xingyun.sc.dto.retail.returned;

import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.sc.enums.RetailReturnStatus;
import com.lframework.xingyun.sc.enums.SettleStatus;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class RetailReturnFullDto implements BaseDto, Serializable {

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
   * 会员ID
   */
  private String memberId;

  /**
   * 销售员ID
   */
  private String salerId;

  /**
   * 付款日期
   */
  private LocalDate paymentDate;

  /**
   * 商品数量
   */
  private Integer totalNum;

  /**
   * 赠品数量
   */
  private Integer totalGiftNum;

  /**
   * 退货总金额
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
  private RetailReturnStatus status;

  /**
   * 拒绝原因
   */
  private String refuseReason;

  /**
   * 零售出库单ID
   */
  private String outSheetId;

  /**
   * 结算状态
   */
  private SettleStatus settleStatus;

  /**
   * 订单明细
   */
  private List<ReturnDetailDto> details;

  @Data
  public static class ReturnDetailDto implements BaseDto, Serializable {

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
     * 供应商ID
     */
    private String supplierId;

    /**
     * 退货数量
     */
    private Integer returnNum;

    /**
     * 原价
     */
    private BigDecimal oriPrice;

    /**
     * 现价
     */
    private BigDecimal taxPrice;

    /**
     * 折扣（%）
     */
    private BigDecimal discountRate;

    /**
     * 是否赠品
     */
    private Boolean isGift;

    /**
     * 税率（%）
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
     * 结算状态
     */
    private SettleStatus settleStatus;

    /**
     * 零售出库单明细ID
     */
    private String outSheetDetailId;
  }
}
