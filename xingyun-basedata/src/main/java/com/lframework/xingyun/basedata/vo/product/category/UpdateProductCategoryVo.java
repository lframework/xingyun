package com.lframework.xingyun.basedata.vo.product.category;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateProductCategoryVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotBlank(message = "ID不能为空！")
  private String id;

  /**
   * 编号
   */
  @ApiModelProperty(value = "编号", required = true)
  @NotBlank(message = "编号不能为空！")
  private String code;

  /**
   * 名称
   */
  @ApiModelProperty(value = "名称", required = true)
  @NotBlank(message = "名称不能为空！")
  private String name;

  /**
   * 状态
   */
  @ApiModelProperty(value = "状态", required = true)
  @NotNull(message = "请选择状态！")
  private Boolean available;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;
}
