package com.lframework.xingyun.basedata.bo.product.saleproperty;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.basedata.entity.ProductSalePropertyDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class QueryProductSalePropertyDefinitionBo extends BaseBo<ProductSalePropertyDefinition> {

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
   * 已关联商品分类数量
   */
  @Schema(description = "已关联商品分类数量")
  private Integer categoryCount;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  public QueryProductSalePropertyDefinitionBo() {

  }

  public QueryProductSalePropertyDefinitionBo(ProductSalePropertyDefinition dto) {

    super(dto);
  }
}
