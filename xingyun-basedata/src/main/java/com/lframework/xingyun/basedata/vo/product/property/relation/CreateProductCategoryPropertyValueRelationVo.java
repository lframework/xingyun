package com.lframework.xingyun.basedata.vo.product.property.relation;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateProductCategoryPropertyValueRelationVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 商品ID
   */
  @Schema(description = "商品ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "商品ID不能为空！")
  private String productId;

  /**
   * 属性ID
   */
  @Schema(description = "属性ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "属性ID不能为空！")
  private String propertyId;

  /**
   * 属性值ID
   */
  @Schema(description = "属性值ID")
  private String propertyItemId;

  /**
   * 属性值文本
   */
  @Schema(description = "属性值文本")
  private String propertyText;
}
