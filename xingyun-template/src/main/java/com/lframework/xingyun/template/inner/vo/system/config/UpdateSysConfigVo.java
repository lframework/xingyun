package com.lframework.xingyun.template.inner.vo.system.config;

import com.lframework.starter.web.components.validation.TypeMismatch;
import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateSysConfigVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 是否允许注册
   */
  @ApiModelProperty(value = "是否允许注册", required = true)
  @NotNull(message = "请选择是否允许注册！")
  @TypeMismatch(message = "是否允许注册格式错误！")
  private Boolean allowRegist;

  /**
   * 是否允许手机号登录
   */
  @ApiModelProperty(value = "是否允许手机号登录", required = true)
  @NotNull(message = "请选择是否允许手机号登录！")
  @TypeMismatch(message = "是否允许手机号登录格式错误！")
  private Boolean allowTelephoneLogin;

  /**
   * 手机号登录时的signName
   */
  private String telephoneLoginSignName;

  /**
   * 手机号登录时的templateCode
   */
  private String telephoneLoginTemplateCode;

  /**
   * 是否允许锁定用户
   */
  @ApiModelProperty(value = "是否允许锁定用户", required = true)
  @NotNull(message = "请选择是否允许锁定用户！")
  @TypeMismatch(message = "是否允许锁定用户格式错误！")
  private Boolean allowLock;

  /**
   * 登录失败次数
   */
  @ApiModelProperty(value = "登录失败次数")
  private Integer failNum;

  /**
   * 是否允许验证码
   */
  @ApiModelProperty(value = "是否允许验证码", required = true)
  @NotNull(message = "请选择是否允许验证码！")
  @TypeMismatch(message = "是否允许验证码格式错误！")
  private Boolean allowCaptcha;

  /**
   * 是否开启忘记密码
   */
  @ApiModelProperty(value = "是否开启忘记密码", required = true)
  @NotNull(message = "请选择是否开启忘记密码！")
  @TypeMismatch(message = "是否开启忘记密码格式错误！")
  private Boolean allowForgetPsw;

  /**
   * 忘记密码是否使用邮箱
   */
  @ApiModelProperty(value = "忘记密码是否使用邮箱，allowForgetPsw == true时必填")
  @TypeMismatch(message = "忘记密码是否使用邮箱格式错误！")
  private Boolean forgetPswRequireMail;

  /**
   * 忘记密码是否使用短信
   */
  @ApiModelProperty(value = "忘记密码是否使用短信，allowForgetPsw == true时必填")
  @TypeMismatch(message = "忘记密码是否使用短信格式错误！")
  private Boolean forgetPswRequireSms;

  /**
   * signName
   */
  @ApiModelProperty(value = "signName，forgetPswRequireSms == true时必填")
  private String signName;

  /**
   * templateCode
   */
  @ApiModelProperty(value = "templateCode，forgetPswRequireSms == true时必填")
  private String templateCode;
}
