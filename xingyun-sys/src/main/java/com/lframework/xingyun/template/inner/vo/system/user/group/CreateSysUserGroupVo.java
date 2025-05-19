package com.lframework.xingyun.template.inner.vo.system.user.group;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateSysUserGroupVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

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
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 用户ID
   */
  @ApiModelProperty("用户ID")
  private List<String> userIds;
}
