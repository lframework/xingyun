package com.lframework.xingyun.sc.vo.stock.log;

import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.PageVo;
import com.lframework.xingyun.sc.enums.ProductStockBizType;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryProductStockLogVo extends PageVo {

  private static final long serialVersionUID = 1L;

  /**
   * 仓库ID
   */
  private String scId;

  /**
   * 商品编号
   */
  private String productCode;

  /**
   * 商品名称
   */
  private String productName;

  /**
   * 商品类目ID
   */
  private String categoryId;

  /**
   * 商品品牌ID
   */
  private String brandId;

  /**
   * 供应商ID
   */
  private String supplierId;

  /**
   * 批次号
   */
  private String lotCode;

  /**
   * 创建起始时间
   */
  private LocalDateTime createStartTime;

  /**
   * 创建截止时间
   */
  private LocalDateTime createEndTime;

  /**
   * 业务类型
   */
  @IsEnum(message = "业务类型不存在！", enumClass = ProductStockBizType.class)
  private Integer bizType;
}
