package com.lframework.xingyun.template.inner.vo.system.user;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 用户登录请求参数
 */
@Data
public class GetLoginCaptchaRequieVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 租户名称
   */
  @ApiModelProperty("租户名称")
  private String tenantName;

  /**
   * 用户名
   */
  @ApiModelProperty(value = "用户名", required = true)
  @NotBlank(message = "用户名不能为空！")
  private String username;
}
