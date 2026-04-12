package com.lframework.xingyun.basedata.bo.product.brand;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProductCategorySelectorBo extends BaseBo<ProductCategory> {

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
   * 父级ID
   */
    @Schema(description = "父级ID")
    private String parentId;

    public ProductCategorySelectorBo() {

    }

    public ProductCategorySelectorBo(ProductCategory dto) {

        super(dto);
    }
}
