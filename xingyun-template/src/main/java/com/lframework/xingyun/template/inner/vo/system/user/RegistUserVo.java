package com.lframework.xingyun.template.inner.vo.system.user;

import com.lframework.starter.common.constants.PatternPool;
import com.lframework.starter.web.components.validation.Pattern;
import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistUserVo implements BaseVo, Serializable {

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
  @Pattern(regexp = PatternPool.PATTERN_STR_PASSWORD, message = "密码长度必须为5-16位，只允许包含大写字母、小写字母、数字、下划线")
  @NotBlank(message = "密码不能为空！")
  private String password;

  /**
   * 姓名
   */
  @ApiModelProperty(value = "姓名", required = true)
  @NotBlank(message = "姓名不能为空！")
  private String name;

  /**
   * 邮箱
   */
  @ApiModelProperty("邮箱")
  @Pattern(regexp = PatternPool.PATTERN_STR_EMAIL, message = "邮箱地址格式不正确！")
  private String email;

  /**
   * 联系电话
   */
  @ApiModelProperty("联系电话")
  @Pattern(regexp = PatternPool.PATTERN_STR_CN_TEL, message = "联系电话格式不正确！")
  private String telephone;
}
