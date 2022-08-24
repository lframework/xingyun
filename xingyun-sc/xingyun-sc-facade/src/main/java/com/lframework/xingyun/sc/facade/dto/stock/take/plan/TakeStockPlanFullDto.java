package com.lframework.xingyun.sc.facade.dto.stock.take.plan;

import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.sc.facade.enums.TakeStockPlanStatus;
import com.lframework.xingyun.sc.facade.enums.TakeStockPlanType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class TakeStockPlanFullDto implements BaseDto, Serializable {

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
   * 盘点类别
   */
  private TakeStockPlanType takeType;

  /**
   * 备注
   */
  private String description;

  /**
   * 盘点状态
   */
  private TakeStockPlanStatus takeStatus;

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
   * 明细
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
     * 库存数量
     */
    private Integer stockNum;

    /**
     * 盘点数量
     */
    private Integer oriTakeNum;

    /**
     * 修改后的盘点数量
     */
    private Integer takeNum;

    /**
     * 出项数量
     */
    private Integer totalOutNum;

    /**
     * 进项数量
     */
    private Integer totalInNum;

    /**
     * 成本价
     */
    private BigDecimal taxPrice;

    /**
     * 备注
     */
    private String description;

  }
}
