package com.lframework.xingyun.api.bo.basedata.product.category;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.basedata.dto.product.category.ProductCategoryDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductCategoryTreeBo extends BaseBo<ProductCategoryDto> {

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
   * 父级ID
   */
  @ApiModelProperty("父级ID")
  private String parentId;

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

  public ProductCategoryTreeBo() {

  }

  public ProductCategoryTreeBo(ProductCategoryDto dto) {

    super(dto);
  }
}
