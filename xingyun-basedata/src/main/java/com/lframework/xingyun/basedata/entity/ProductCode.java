package com.lframework.xingyun.basedata.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import lombok.Data;

/**
 * <p>
 * Product
 * </p>
 *
 * @author lframework
 */
@Data
@TableName("base_data_product_code")
public class ProductCode extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  public static final String CACHE_NAME = "ProductCode";

  /**
   * ID
   */
  private String id;

  /**
   * 商品ID
   */
  private String productId;

  /**
   * 编号
   */
  private String code;

  /**
   * 是否主编号
   */
  private Boolean isMain;
}
