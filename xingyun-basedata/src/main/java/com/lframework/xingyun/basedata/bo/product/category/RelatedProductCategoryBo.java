package com.lframework.xingyun.basedata.bo.product.category;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RelatedProductCategoryBo extends BaseBo<ProductCategory> {

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

  public RelatedProductCategoryBo() {

  }

  public RelatedProductCategoryBo(ProductCategory dto) {

    super(dto);
  }
}
