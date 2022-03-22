package com.lframework.xingyun.basedata.dto.product.info;

import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.basedata.dto.product.poly.ProductPolyDto;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ProductDto implements BaseDto, Serializable {

  public static final String CACHE_NAME = "ProductDto";

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 编号
   */
  private String code;

  /**
   * 名称
   */
  private String name;

  /**
   * 聚合ID
   */
  private ProductPolyDto poly;

  /**
   * SKU
   */
  private String skuCode;

  /**
   * 外部编号
   */
  private String externalCode;

  /**
   * 规格
   */
  private String spec;

  /**
   * 单位
   */
  private String unit;

  /**
   * 状态
   */
  private Boolean available;

  /**
   * 创建人ID
   */
  private String createBy;

  /**
   * 创建时间
   */
  private LocalDateTime createTime;

  /**
   * 修改人ID
   */
  private String updateBy;

  /**
   * 修改时间
   */
  private LocalDateTime updateTime;
}
