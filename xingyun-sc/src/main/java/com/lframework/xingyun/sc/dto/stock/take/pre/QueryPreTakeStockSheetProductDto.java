package com.lframework.xingyun.sc.dto.stock.take.pre;

import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.xingyun.sc.enums.PreTakeStockSheetStatus;
import java.io.Serializable;
import lombok.Data;

@Data
public class QueryPreTakeStockSheetProductDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

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

  /**
   * 预先盘点单状态
   */
  private PreTakeStockSheetStatus takeStatus;
}
