package com.lframework.xingyun.basedata.bo.product.category.saleproperty;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.basedata.dto.product.category.saleproperty.ProductCategorySalePropertyDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProductCategorySalePropertyBo extends BaseBo<ProductCategorySalePropertyDto> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 编号
   */
  @Schema(description = "编号")
  private String code;

  /**
   * 名称
   */
  @Schema(description = "名称")
  private String name;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  public ProductCategorySalePropertyBo() {

  }

  public ProductCategorySalePropertyBo(ProductCategorySalePropertyDto dto) {

    super(dto);
  }

  @Override
  protected void afterInit(ProductCategorySalePropertyDto dto) {

    this.id = dto.getPropertyId();
  }
}
