package com.lframework.xingyun.template.inner.vo.system.user;

import com.lframework.starter.common.constants.PatternPool;
import com.lframework.starter.web.components.validation.Pattern;
import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 手机号登录请求参数
 */
@Data
public class TelephoneLoginVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 手机号
   */
  @ApiModelProperty(value = "手机号", required = true)
  @Pattern(regexp = PatternPool.PATTERN_STR_CN_TEL, message = "手机号格式不正确！")
  @NotBlank(message = "手机号不能为空！")
  private String telephone;

  /**
   * 验证码
   */
  @ApiModelProperty(value = "验证码", required = true)
  @NotBlank(message = "验证码不能为空！")
  private String captcha;
}
