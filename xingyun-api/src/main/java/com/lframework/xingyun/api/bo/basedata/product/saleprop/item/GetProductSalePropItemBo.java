package com.lframework.xingyun.api.bo.basedata.product.saleprop.item;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.basedata.dto.product.saleprop.item.ProductSalePropItemDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetProductSalePropItemBo extends BaseBo<ProductSalePropItemDto> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 编号
   */
  @ApiModelProperty("编号")
  private String code;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  public GetProductSalePropItemBo() {

  }

  public GetProductSalePropItemBo(ProductSalePropItemDto dto) {

    super(dto);
  }
}
