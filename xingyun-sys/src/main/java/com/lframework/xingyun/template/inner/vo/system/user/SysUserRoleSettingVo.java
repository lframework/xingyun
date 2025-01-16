package com.lframework.xingyun.template.inner.vo.system.user;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SysUserRoleSettingVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 用户ID
   */
  @ApiModelProperty(value = "用户ID", required = true)
  @NotEmpty(message = "用户ID不能为空！")
  private List<String> userIds;

  /**
   * 角色ID
   */
  @ApiModelProperty("角色ID")
  private List<String> roleIds;
}
