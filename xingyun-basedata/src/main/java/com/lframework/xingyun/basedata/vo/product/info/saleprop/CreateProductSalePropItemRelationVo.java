package com.lframework.xingyun.basedata.vo.product.info.saleprop;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateProductSalePropItemRelationVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 商品ID
   */
  @Schema(description = "商品ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "商品ID不能为空！")
  private String productId;

  /**
   * 销售属性值ID
   */
  @Schema(description = "销售属性值ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotEmpty(message = "销售属性值ID不能为空！")
  private List<String> salePropItemIds;
}
