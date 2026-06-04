package com.lframework.xingyun.sc.dto.sale;

import com.lframework.starter.web.core.dto.BaseDto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * 销售订单在出库业务的Dto
 */
@Data
public class SaleOrderWithOutDto implements BaseDto, Serializable {

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
   * 客户ID
   */
  private String customerId;

  /**
   * 销售员ID
   */
  private String salerId;

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
     * 销售数量
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
     * 已出库数量
     */
    private BigDecimal outNum;
  }
}
