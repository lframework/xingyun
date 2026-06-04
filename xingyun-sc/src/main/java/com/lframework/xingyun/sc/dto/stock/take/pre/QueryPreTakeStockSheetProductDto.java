package com.lframework.xingyun.sc.dto.stock.take.pre;

import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.xingyun.sc.enums.PreTakeStockSheetStatus;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class QueryPreTakeStockSheetProductDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 商品ID
   */
  private String productId;

  /**
   * SKU ID
   */
  private String skuId;

  /**
   * 商品编号
   */
  private String productCode;

  /**
   * SKU编号
   */
  private String skuCode;

  /**
   * 商品名称
   */
  private String productName;

  /**
   * 销售属性
   */
  private String salePropertyText;

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
   * 初盘数量
   */
  private BigDecimal firstNum;

  /**
   * 复盘数量
   */
  private BigDecimal secondNum;

  /**
   * 抽盘数量
   */
  private BigDecimal randNum;

  /**
   * 预先盘点单状态
   */
  private PreTakeStockSheetStatus takeStatus;
}
