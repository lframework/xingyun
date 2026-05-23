package com.lframework.xingyun.basedata.dto.product.saleproperty;

import com.lframework.starter.web.core.dto.BaseDto;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class ProductSalePropertyDefinitionModelorDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 名称
   */
  private String name;

  /**
   * 可选项
   */
  private List<ProductSalePropertyItemModelorDto> items;

  @Data
  public static class ProductSalePropertyItemModelorDto implements BaseDto, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 名称
     */
    private String name;
  }
}
