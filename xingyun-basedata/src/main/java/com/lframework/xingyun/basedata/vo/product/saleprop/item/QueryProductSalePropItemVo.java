package com.lframework.xingyun.basedata.vo.product.saleprop.item;

import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.starter.web.core.vo.PageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QueryProductSalePropItemVo extends PageVo implements BaseVo, Serializable {

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
   * 销售属性组ID
   */
  @Schema(description = "销售属性组ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "GroupID不能为空！")
  private String groupId;

  /**
   * 状态
   */
  @Schema(description = "状态")
  private Boolean available;
}
