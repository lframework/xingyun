package com.lframework.xingyun.template.gen.vo.custom.page;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateGenCustomPageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotNull(message = "ID不能为空！")
  private Integer id;

  /**
   * 分类ID
   */
  @ApiModelProperty("分类ID")
  private String categoryId;

  /**
   * 页面代码
   */
  @ApiModelProperty(value = "页面代码", required = true)
  @NotBlank(message = "页面代码不能为空！")
  private String pageCode;

  /**
   * 脚本代码
   */
  @ApiModelProperty(value = "脚本代码", required = true)
  @NotBlank(message = "脚本代码不能为空！")
  private String scriptCode;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;
}
