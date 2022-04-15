package com.lframework.xingyun.api.bo.basedata.product.saleprop.item;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.basedata.dto.product.saleprop.item.ProductSalePropItemDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetEnableSalePropItemBo extends BaseBo<ProductSalePropItemDto> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  public GetEnableSalePropItemBo() {

  }

  public GetEnableSalePropItemBo(ProductSalePropItemDto dto) {

    super(dto);
  }
}
