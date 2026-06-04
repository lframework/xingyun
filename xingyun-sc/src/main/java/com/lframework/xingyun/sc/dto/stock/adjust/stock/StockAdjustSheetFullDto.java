package com.lframework.xingyun.sc.dto.stock.adjust.stock;

import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.xingyun.sc.enums.StockAdjustSheetBizType;
import com.lframework.xingyun.sc.enums.StockAdjustSheetStatus;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

/**
 * <p>
 * 库存调整单详情 Dto
 * </p>
 *
 * @author zmj
 */
@Data
public class StockAdjustSheetFullDto implements BaseDto, Serializable {

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
   * 仓库ID
   */
  private String scId;

  /**
   * 业务类型
   */
  private StockAdjustSheetBizType bizType;

  /**
   * 调整原因ID
   */
  private String reasonId;

  /**
   * 状态
   */
  private StockAdjustSheetStatus status;

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
     * 调整库存数量
     */
    private BigDecimal stockNum;

    /**
     * 当前库存数量
     */
    private BigDecimal curStockNum;

    /**
     * 备注
     */
    private String description;
  }

}
