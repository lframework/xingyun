package com.lframework.xingyun.sc.dto.purchase.receive;

import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * 采购收货单在采购退货业务的Dto
 */
@Data
public class ReceiveSheetWithReturnDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

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
   * 订单明细
   */
  private List<DetailDto> details;

  @Data
  public static class DetailDto implements BaseDto, Serializable {

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
     * 已退货数量
     */
    private Integer returnNum;
  }
}
