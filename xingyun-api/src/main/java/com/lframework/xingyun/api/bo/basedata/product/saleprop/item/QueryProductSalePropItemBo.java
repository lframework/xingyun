package com.lframework.xingyun.api.bo.basedata.product.saleprop.item;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.basedata.dto.product.saleprop.item.ProductSalePropItemDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryProductSalePropItemBo extends BaseBo<ProductSalePropItemDto> {

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
   * 状态
   */
  private Boolean available;

  /**
   * 备注
   */
  private String description;

  public QueryProductSalePropItemBo() {

  }

  public QueryProductSalePropItemBo(ProductSalePropItemDto dto) {

    super(dto);
  }
}
