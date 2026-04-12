package com.lframework.xingyun.basedata.vo.product.poly.property;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateProductPolyPropertyVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * polyID
   */
  @Schema(description = "polyID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "polyId不能为空！")
  private String polyId;

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
