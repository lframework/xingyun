package com.lframework.xingyun.sc.dto.sale;

import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.xingyun.sc.enums.SaleOrderStatus;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class SaleOrderFullDto implements BaseDto, Serializable {

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
   * 客户ID
   */
  private String customerId;

  /**
   * 销售员ID
   */
  private String salerId;

  /**
   * 商品数量
   */
  private BigDecimal totalNum;

  /**
   * 赠品数量
   */
  private BigDecimal totalGiftNum;

  /**
   * 销售总金额
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
  private SaleOrderStatus status;

  /**
   * 拒绝原因
   */
  private String refuseReason;

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
     * 组合商品ID
     */
    private String mainProductId;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * SKU ID
     */
    private String skuId;

    /**
     * SKU编号
     */
    private String skuCode;

    /**
     * 销售属性
     */
    private String salePropertyText;

    /**
     * 商品编号
     */
    private String productCode;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 规格
     */
    private String spec;

    /**
     * 单位
     */
    private String unit;

    /**
     * 库存数量
     */
    private BigDecimal stockNum;

    /**
     * 组合商品名称
     */
    private String mainProductName;

    /**
     * 采购数量
     */
    private BigDecimal orderNum;

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
     * 总金额
     */
    private BigDecimal taxAmount;
  }
}
