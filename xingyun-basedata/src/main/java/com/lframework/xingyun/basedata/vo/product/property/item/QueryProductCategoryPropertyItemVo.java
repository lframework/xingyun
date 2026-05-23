package com.lframework.xingyun.basedata.vo.product.property.item;

import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.starter.web.core.vo.PageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QueryProductCategoryPropertyItemVo extends PageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

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
   * 属性ID
   */
  @Schema(description = "属性ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "属性ID不能为空！")
  private String propertyId;
}
