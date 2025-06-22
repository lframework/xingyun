package com.lframework.xingyun.sc.dto.retail.out;

import com.lframework.starter.web.core.dto.BaseDto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class RetailOutSheetWithReturnDto implements BaseDto, Serializable {

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
   * 会员ID
   */
  private String memberId;

  /**
   * 销售员ID
   */
  private String salerId;

  /**
   * 订单明细
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
     * 商品ID
     */
    private String productId;

    /**
     * 出库数量
     */
    private Integer orderNum;

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
     * 已退货数量
     */
    private Integer returnNum;
  }
}
