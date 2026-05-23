package com.lframework.xingyun.basedata.vo.product.category.saleproperty;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;

@Data
public class CreateProductCategorySalePropertyRelationVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 分类ID
   */
  @Schema(description = "分类ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "商品分类不能为空！")
  private String categoryId;

  /**
   * 编号
   */
  @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "编号不能为空！")
  @Size(max = 20, message = "编号长度不能超过20个字符！")
  private String code;

  /**
   * 名称
   */
  @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "名称不能为空！")
  @Size(max = 20, message = "名称长度不能超过20个字符！")
  private String name;

  /**
   * 备注
   */
  @Schema(description = "备注")
  @Size(max = 200, message = "备注长度不能超过200个字符！")
  private String description;
}
