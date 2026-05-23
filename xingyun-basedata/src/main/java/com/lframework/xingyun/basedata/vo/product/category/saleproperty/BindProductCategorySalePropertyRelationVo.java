package com.lframework.xingyun.basedata.vo.product.category.saleproperty;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class BindProductCategorySalePropertyRelationVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 分类ID
   */
  @Schema(description = "分类ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "商品分类不能为空！")
  private String categoryId;

  /**
   * 销售属性ID
   */
  @Schema(description = "销售属性ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotEmpty(message = "请选择销售属性！")
  private List<String> propertyIds;
}
