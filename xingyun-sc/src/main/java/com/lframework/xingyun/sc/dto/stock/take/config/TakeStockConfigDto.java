package com.lframework.xingyun.sc.dto.stock.take.config;

import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;

/**
 * <p>
 * 盘点参数 Dto
 * </p>
 *
 * @author zmj
 */
@Data
public class TakeStockConfigDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  public static final String CACHE_NAME = "TakeStockConfigDto";

  /**
   * ID
   */
  private String id;

  /**
   * 库存盘点单关联盘点任务后，是否显示盘点任务中的商品数据
   */
  private Boolean showProduct;

  /**
   * 库存盘点单是否显示盘点任务创建时商品的系统库存数量
   */
  private Boolean showStock;

  /**
   * 盘点差异生成时是否自动调整盘点任务中商品的系统库存数量
   */
  private Boolean autoChangeStock;

  /**
   * 盘点差异单中的盘点数量是否允许手动修改
   */
  private Boolean allowChangeNum;

  /**
   * 盘点任务创建后多少小时内未完成，则自动作废
   */
  private Integer cancelHours;

}
