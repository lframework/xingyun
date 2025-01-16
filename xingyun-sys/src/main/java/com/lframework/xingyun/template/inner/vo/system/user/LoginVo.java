package com.lframework.xingyun.template.inner.vo.system.user;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 用户登录请求参数
 */
@Data
public class LoginVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 用户名
   */
  @ApiModelProperty(value = "用户名", required = true)
  @NotBlank(message = "用户名不能为空！")
  private String username;

  /**
   * 密码
   */
  @ApiModelProperty(value = "密码", required = true)
  @NotBlank(message = "密码不能为空！")
  private String password;

  /**
   * sn
   */
  @ApiModelProperty(value = "sn，验证码流水号", required = true)
  private String sn;

  /**
   * 验证码
   */
  @ApiModelProperty(value = "验证码", required = true)
  private String captcha;
}
